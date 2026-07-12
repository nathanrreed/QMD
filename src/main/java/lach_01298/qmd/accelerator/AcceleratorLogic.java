package lach_01298.qmd.accelerator;

import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.multiblock.IPacketMultiblockLogic;
import com.nred.nuclearcraft.multiblock.MultiblockLogic;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.zerono.mods.zerocore.lib.data.nbt.ISyncableEntity;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.enums.BlockTypes;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.*;

import static com.nred.nuclearcraft.registration.BlockRegistration.ACTIVE;
import static lach_01298.qmd.recipe.QMDRecipes.accelerator_cooling;


public class AcceleratorLogic extends MultiblockLogic<Accelerator, AcceleratorLogic> implements IPacketMultiblockLogic<Accelerator, AcceleratorLogic, AcceleratorUpdatePacket> {
    public boolean searchFlag = false;
    public final ObjectSet<TileAcceleratorCooler> coolerCache = new ObjectOpenHashSet<>();
    public final Long2ObjectMap<TileAcceleratorCooler> componentFailCache = new Long2ObjectOpenHashMap<>(), assumedValidCache = new Long2ObjectOpenHashMap<>();

    protected boolean operational = false;
    private double excessCoolingRecipes = 0;
    private double excessHeat = 0;


    // Multiblock logic

    public AcceleratorLogic(Accelerator accelerator) {
        super(accelerator);
    }

    public AcceleratorLogic(AcceleratorLogic oldLogic) {
        super(oldLogic.multiblock);
    }

    @Override
    public String getID() {
        return "";
    }


    // Multiblock methods

    @Override
    public void onMachineAssembled() {
        onAcceleratorFormed();
    }

    @Override
    public void onMachineRestored() {
        onAcceleratorFormed();
    }

    @Override
    public void onMachinePaused() {
        onAcceleratorBroken();
    }

    public void onMachineDisassembled() {
        onAcceleratorBroken();
    }

    @Override
    public void onAssimilate(IMultiblockController<Accelerator> assimilated) {
        multiblock.heatBuffer.mergeHeatBuffers(((Accelerator) assimilated).heatBuffer);
        multiblock.energyStorage.mergeEnergyStorage(((Accelerator) assimilated).energyStorage);

        if (multiblock.isAssembled()) {

            onAcceleratorFormed();
        } else {
            onAcceleratorBroken();
        }
    }

    @Override
    public void onAssimilated(IMultiblockController<Accelerator> iMultiblockController) {
    }

    // Accelerator methods

    public int getBeamLength() {
        return 0;
    }

    public double getBeamRadius() {
        return 0;
    }

    public int getCapacityMultiplier() {
        return multiblock.getInteriorVolume();
    }

    public @Nonnull List<Tank> getTanks(List<Tank> backupTanks) {
        return multiblock.isAssembled() ? multiblock.tanks : backupTanks;
    }

    // Multiblock validation

    public boolean isMachineWhole() {
        // vents
        boolean inlet = false;
        boolean outlet = false;
        for (TileAcceleratorVent vent : getPartMap(TileAcceleratorVent.class).values()) {
            if (!vent.getBlockState(vent.getBlockPos()).getValue(ACTIVE)) {
                inlet = true;
            } else {
                outlet = true;
            }
        }

        if (!inlet) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.no_inlet", Collections.emptyList());
            return false;
        }

        if (!outlet) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.no_outlet", Collections.emptyList());
            return false;
        }

        // Energy Ports
        if (getPartMap(TileAcceleratorEnergyPort.class).isEmpty()) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.need_energy_ports", Collections.emptyList());
            return false;
        }

        return true;
    }

    @Override
    public int getMinimumInteriorLength() {
        return 3;
    }

    @Override
    public int getMaximumInteriorLength() {
        return QMDServerConfig.accelerator_linear_max_size;
    }

    public int getThickness() {
        return Accelerator.thickness;
    }

    @Override
    public List<Pair<Class<? extends IMultiblockPart<Accelerator>>, String>> getPartBlacklist() {
        return new ArrayList<>();
    }

    @Override
    public boolean isBlockGoodForInterior(Level level, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        if (level.getBlockState(pos).canBeReplaced() || level.getBlockEntity(pos) instanceof TileAcceleratorPart) return true;
        else return multiblock.standardLastError(pos);
    }

    // Accelerator formation

    public void onAcceleratorFormed() {
        Accelerator acc = multiblock;
        for (IAcceleratorController contr : getPartMap(IAcceleratorController.class).values()) {
            acc.controller = contr;
        }

        acc.energyStorage.setStorageCapacity((long) QMDServerConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
        acc.energyStorage.setMaxTransfer((long) QMDServerConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
        acc.heatBuffer.setHeatCapacity((long) QMDServerConfig.accelerator_base_heat_capacity * getCapacityMultiplier());
        acc.ambientTemp = 293;
        acc.tanks.get(0).setCapacity(QMDServerConfig.accelerator_base_input_tank_capacity * getCapacityMultiplier());
        acc.tanks.get(1).setCapacity(QMDServerConfig.accelerator_base_output_tank_capacity * getCapacityMultiplier());

        if (!getWorld().isClientSide()) {
            if (acc.isNew) {
                // new accelerators start at ambient temperature
                acc.heatBuffer.setHeatStored(acc.ambientTemp * acc.heatBuffer.getHeatCapacity() / acc.MAX_TEMP);
            }
            acc.isNew = false;
            acc.currentHeating = 0;

            acc.updateActivity();

            for (ParticleStorageAccelerator beam : acc.beams) {
                beam.setMaxEnergy(Long.MAX_VALUE);
            }

            // Coolers
            acc.cooling = 0;
            acc.maxOperatingTemp = acc.MAX_TEMP;

            componentFailCache.clear();
            do {
                assumedValidCache.clear();
                refreshCoolers();
            }
            while (searchFlag);

            for (IAcceleratorComponent part : acc.getPartMap(IAcceleratorComponent.class).values()) {
                if (part instanceof TileAcceleratorCooler) {
                    TileAcceleratorCooler cooler = (TileAcceleratorCooler) part;
                    if (part.isFunctional()) {
                        acc.cooling += cooler.coolerType.getHeatRemoved();
                    }
                } else if (part instanceof TileAcceleratorMagnet || part instanceof TileAcceleratorRFCavity) {
                    if (part.getMaxOperatingTemp() < acc.maxOperatingTemp) {
                        acc.maxOperatingTemp = part.getMaxOperatingTemp();
                    }
                }
            }
        }
    }

    public void setBeamlineFunctional(Set<BlockPos> beamline) {
        for (BlockPos pos : beamline) {
            if (multiblock.getWorld().getBlockEntity(pos) instanceof TileAcceleratorBeam) {
                TileAcceleratorBeam beam = (TileAcceleratorBeam) getWorld().getBlockEntity(pos);
                beam.setFunctional(true);
            }
        }
    }

    public void resetBeams() {
        for (ParticleStorageAccelerator beam : multiblock.beams) {
            beam.setMinEnergy(0);
            beam.setMaxEnergy(Long.MAX_VALUE);
        }
    }

    public void formComponents() {
        Accelerator acc = multiblock;
        // beam
        for (TileAcceleratorBeam beam : acc.getPartMap(TileAcceleratorBeam.class).values()) {
            if (beam.isFunctional()) {
                if (acc.isValidRFCavity(beam.getBlockPos(), Axis.X)) {
                    acc.getRFCavityMap().put(beam.getBlockPos().asLong(), new RFCavity(acc, beam.getBlockPos(), Axis.X));
                } else if (acc.isValidRFCavity(beam.getBlockPos(), Axis.Z)) {
                    acc.getRFCavityMap().put(beam.getBlockPos().asLong(), new RFCavity(acc, beam.getBlockPos(), Axis.Z));
                } else if (acc.isValidQuadrupole(beam.getBlockPos(), Axis.X)) {
                    acc.getQuadrupoleMap().put(beam.getBlockPos().asLong(),
                            new QuadrupoleMagnet(acc, beam.getBlockPos(), Axis.X));
                } else if (acc.isValidQuadrupole(beam.getBlockPos(), Axis.Z)) {
                    acc.getQuadrupoleMap().put(beam.getBlockPos().asLong(),
                            new QuadrupoleMagnet(acc, beam.getBlockPos(), Axis.Z));
                } else if (acc.isValidDipole(beam.getBlockPos(), false)) {
                    acc.getDipoleMap().put(beam.getBlockPos().asLong(), new DipoleMagnet(acc, beam.getBlockPos()));
                } else if (acc.isValidDipole(beam.getBlockPos(), true)) {
                    acc.getDipoleMap().put(beam.getBlockPos().asLong(), new DipoleMagnet(acc, beam.getBlockPos()));
                } else if (acc.isValidDipole(beam.getBlockPos(), true)) {
                    acc.getDipoleMap().put(beam.getBlockPos().asLong(), new DipoleMagnet(acc, beam.getBlockPos()));
                }
            }
        }

        acc.RFCavityNumber = acc.getRFCavityMap().size();
        acc.quadrupoleNumber = acc.getQuadrupoleMap().size();
        acc.dipoleNumber = acc.getDipoleMap().size();

        for (RFCavity cavity : acc.getRFCavityMap().values()) {
            for (IAcceleratorComponent componet : cavity.getComponents().values()) {
                componet.setFunctional(true);
            }
        }

        for (QuadrupoleMagnet quad : acc.getQuadrupoleMap().values()) {
            for (IAcceleratorComponent componet : quad.getComponents().values()) {
                componet.setFunctional(true);
            }
        }

        for (DipoleMagnet dipole : acc.dipoleMap.values()) {
            for (IAcceleratorComponent componet : dipole.getComponents().values()) {
                componet.setFunctional(true);
            }
        }
    }

    public void refreshStats() {
        int energy = 0;
        long heat = 0;
        int parts = 0;
        double efficiency = 0;
        double quadStrength = 0;
        double dipoleStrength = 0;
        int voltage = 0;

        for (DipoleMagnet dipole : multiblock.dipoleMap.values()) {
            for (IAcceleratorComponent componet : dipole.getComponents().values()) {
                if (componet instanceof TileAcceleratorMagnet) {
                    BlockTypes.MagnetType magnet = ((TileAcceleratorMagnet) componet).magnetType;
                    dipoleStrength += magnet.getStrength();
                    heat += magnet.getHeatGenerated();
                    energy += magnet.getBasePower();
                    parts++;
                    efficiency += magnet.getEfficiency();
                    break;
                }
            }
        }

        for (QuadrupoleMagnet quad : multiblock.getQuadrupoleMap().values()) {
            for (IAcceleratorComponent componet : quad.getComponents().values()) {
                if (componet instanceof TileAcceleratorMagnet) {
                    BlockTypes.MagnetType magnet = ((TileAcceleratorMagnet) componet).magnetType;
                    quadStrength += magnet.getStrength();
                    heat += magnet.getHeatGenerated();
                    energy += magnet.getBasePower();
                    parts++;
                    efficiency += magnet.getEfficiency();
                    break;
                }
            }
        }

        for (RFCavity cavity : multiblock.getRFCavityMap().values()) {
            for (IAcceleratorComponent componet : cavity.getComponents().values()) {
                if (componet instanceof TileAcceleratorRFCavity) {
                    BlockTypes.RFCavityType cav = ((TileAcceleratorRFCavity) componet).rfCavityType;
                    voltage += cav.getVoltage();
                    heat += cav.getHeatGenerated();
                    energy += cav.getBasePower();
                    parts++;
                    efficiency += cav.getEfficiency();
                    break;
                }
            }
        }

        for (TileAcceleratorIonSource source : multiblock.getPartMap(TileAcceleratorIonSource.class).values()) {
            energy += source.basePower;
        }

        efficiency /= parts;
        multiblock.requiredEnergy = (int) (energy / efficiency);
        multiblock.rawHeating = heat;
        multiblock.dipoleStrength = dipoleStrength;
        multiblock.quadrupoleStrength = quadStrength;
        multiblock.acceleratingVoltage = voltage;
        multiblock.efficiency = efficiency;

    }

    private void refreshCoolers() {
        searchFlag = false;

        if (getPartMap(TileAcceleratorCooler.class).isEmpty()) {
            return;
        }

        for (TileAcceleratorCooler cooler : getParts(TileAcceleratorCooler.class)) {
            cooler.isSearched = cooler.isInValidPosition = false;
        }

        coolerCache.clear();

        for (TileAcceleratorCooler cooler : getParts(TileAcceleratorCooler.class)) {
            if (cooler.isSearchRoot()) {
                iterateCoolerSearch(cooler, coolerCache);
            }
        }

        for (TileAcceleratorCooler cooler : assumedValidCache.values()) {
            if (!cooler.isInValidPosition) {
                componentFailCache.put(cooler.getBlockPos().asLong(), cooler);
                searchFlag = true;
            }
        }

    }

    private void iterateCoolerSearch(TileAcceleratorCooler rootCooler, ObjectSet<TileAcceleratorCooler> coolerCache) {
        final ObjectSet<TileAcceleratorCooler> searchCache = new ObjectOpenHashSet<>();
        rootCooler.coolerSearch(coolerCache, searchCache, componentFailCache, assumedValidCache);

        do {
            final Iterator<TileAcceleratorCooler> searchIterator = searchCache.iterator();
            final ObjectSet<TileAcceleratorCooler> searchSubCache = new ObjectOpenHashSet<>();
            while (searchIterator.hasNext()) {
                TileAcceleratorCooler component = searchIterator.next();
                searchIterator.remove();
                component.coolerSearch(coolerCache, searchSubCache, componentFailCache, assumedValidCache);
            }
            searchCache.addAll(searchSubCache);
        }
        while (!searchCache.isEmpty());
    }


    // Accelerator disassembly

    public void onAcceleratorBroken() {
        Accelerator acc = multiblock;

        for (RFCavity cavity : acc.getRFCavityMap().values()) {
            for (IAcceleratorComponent componet : cavity.getComponents().values()) {
                componet.setFunctional(false);
            }

        }

        for (QuadrupoleMagnet quad : acc.getQuadrupoleMap().values()) {
            for (IAcceleratorComponent componet : quad.getComponents().values()) {
                componet.setFunctional(false);
            }

        }

        for (DipoleMagnet dipole : acc.dipoleMap.values()) {
            for (IAcceleratorComponent componet : dipole.getComponents().values()) {
                componet.setFunctional(false);
            }

        }

        acc.getRFCavityMap().clear();
        acc.getQuadrupoleMap().clear();
        acc.dipoleMap.clear();

        for (TileAcceleratorBeam beam : acc.getPartMap(TileAcceleratorBeam.class).values()) {
            beam.setFunctional(false);
        }

        for (TileAcceleratorCooler cooler : acc.getPartMap(TileAcceleratorCooler.class).values()) {
            cooler.setFunctional(false);
        }

        for (TileAcceleratorRedstonePort port : getPartMap(TileAcceleratorRedstonePort.class).values()) {
            port.setRedstoneLevel(0);
        }

        for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values()) {
            port.setIONumber(0);
        }

        operational = false;

        if (!getWorld().isClientSide()) {
            acc.updateActivity();
        }
    }

    // Accelerator Operation

    public boolean onUpdateServer() {
        multiblock.errorCode = Accelerator.errorCode_Nothing;

        multiblock.currentHeating = 0;
        operate();

        externalHeating();
        refreshFluidRecipe();

        if (canProcessFluidInputs()) {
            produceFluidProducts();
        }
        updateRedstone();
        multiblock.updateActivity();

        return true;
    }

    public boolean isAcceleratorOn() {
        return operational;
    }

    protected void operate() {
        if ((isRedstonePowered() && !multiblock.computerControlled) || (multiblock.computerControlled && multiblock.energyPercentage > 0)) {
            refreshBeams();
            if (shouldUseEnergy()) {
                if (multiblock.energyStorage.extractEnergy(multiblock.requiredEnergy,
                        true) == multiblock.requiredEnergy) {
                    multiblock.energyStorage.changeEnergyStored(-multiblock.requiredEnergy);
                    internalHeating();
                } else {
                    operational = false;
                    multiblock.errorCode = Accelerator.errorCode_OutOfPower;
                    return;
                }
            }
            if (multiblock.getTemperature() <= multiblock.maxOperatingTemp) {
                operational = true;
                return;
            } else {
                if (operational) {
                    quenchMagnets();
                }
                operational = false;
                multiblock.errorCode = Accelerator.errorCode_ToHot;
                return;

            }
        } else {
            operational = false;
            return;
        }
    }

    protected void refreshBeams() {

    }

    protected boolean shouldUseEnergy() {
        return true;
    }

    public void quenchMagnets() {
        if (QMDServerConfig.accelerator_explosion) {
            List<BlockPos> components = new ArrayList<BlockPos>();

            for (TileAcceleratorMagnet magnet : getPartMap(TileAcceleratorMagnet.class).values()) {
                if (magnet.isToHot()) {
                    components.add(magnet.getBlockPos());
                }

            }
            for (TileAcceleratorRFCavity cavity : getPartMap(TileAcceleratorRFCavity.class).values()) {
                if (cavity.isToHot()) {
                    components.add(cavity.getBlockPos());
                }
            }

            if (!components.isEmpty()) {

                int explosions = 1 + rand.nextInt(1 + components.size() / 10);
                for (int i = 0; i < explosions; i++) {
                    int j = rand.nextInt(components.size());
                    BlockPos component = components.get(j);
                    multiblock.getWorld().explode(null, component.getX(), component.getY(), component.getZ(), 6.0f, Level.ExplosionInteraction.BLOCK);
                    components.remove(j);
                }
            }
        }
    }

    // Beam port IO
    protected void push() {
        for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values()) {
            if (port.getIOType() == IOType.OUTPUT) {
                if (port.getOutwardFacing() != null) {
                    Direction face = port.getOutwardFacing();
                    BlockEntity tile = port.getLevel().getBlockEntity(port.getBlockPos().relative(face));
                    if (tile != null) {
                        IParticleStackHandler otherStorage = getWorld().getCapability(CapabilityParticleStackHandler.BLOCK, tile.getBlockPos(), face.getOpposite());
                        if (otherStorage != null) {
                            otherStorage.reciveParticle(face.getOpposite(), multiblock.beams.get(port.getIONumber()).getParticleStack());
                        }
                    }
                }
            }
        }
    }


    protected void pull() {
        for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values()) {
            if (port.getIOType() == IOType.INPUT) {
                if (port.getOutwardFacing() != null) {
                    Direction face = port.getOutwardFacing();
                    BlockEntity tile = port.getLevel().getBlockEntity(port.getBlockPos().relative(face));
                    if (tile != null) {
                        IParticleStackHandler otherStorage = getWorld().getCapability(CapabilityParticleStackHandler.BLOCK, tile.getBlockPos(),face.getOpposite());
                        if (otherStorage != null) {
                            ParticleStack stack = otherStorage.extractParticle(face.getOpposite());

                            if (!multiblock.beams.get(port.getIONumber()).reciveParticle(face, stack)) {
                                if (stack.getMeanEnergy() > multiblock.beams.get(port.getIONumber()).getMaxEnergy()) {
                                    multiblock.errorCode = Accelerator.errorCode_InputParticleEnergyToHigh;
                                } else {
                                    multiblock.errorCode = Accelerator.errorCode_InputParticleEnergyToLow;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void switchIO() {
        for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values()) {
            if (port.isTriggered()) {
                if (port.getSetting() != port.getIOType()) {
                    port.switchMode();
                    if (port.getIOType() == IOType.INPUT) {
                        port.setIONumber(0);
                        for (TileAcceleratorBeamPort otherPort : getPartMap(TileAcceleratorBeamPort.class).values()) {
                            if (otherPort.getIOType() == IOType.INPUT && otherPort != port) {
                                otherPort.setIOType(IOType.DISABLED);
                                otherPort.setIONumber(0);
                            }
                        }
                    } else if (port.getIOType() == IOType.OUTPUT) {
                        port.setIONumber(1);
                        for (TileAcceleratorBeamPort otherPort : getPartMap(TileAcceleratorBeamPort.class).values()) {
                            if (otherPort.getIOType() == IOType.OUTPUT && otherPort != port) {
                                otherPort.setIOType(IOType.DISABLED);
                                otherPort.setIONumber(0);
                            }
                        }
                    }
                    multiblock.checkIfMachineIsWhole();
                }
                port.resetTrigger();
            }
        }
    }


    // Coolant recipe handling

    protected void refreshFluidRecipe() {
        multiblock.coolingRecipeInfo = accelerator_cooling.getRecipeInfoFromInputs(getWorld(),new ArrayList<>(), multiblock.tanks.subList(0, 1), new ArrayList<>());
        if (multiblock.coolingRecipeInfo != null) {
            multiblock.maxCoolantIn = (int) (1000 * multiblock.coolingRecipeInfo.recipe.getFluidIngredients().get(0).amount() * multiblock.cooling / (double) multiblock.coolingRecipeInfo.recipe.getHeatRequired());
            multiblock.maxCoolantOut = (int) (1000 * multiblock.coolingRecipeInfo.recipe.getFluidProducts().get(0).amount() * multiblock.cooling / (double) multiblock.coolingRecipeInfo.recipe.getHeatRequired());
        }
    }

    protected boolean canProcessFluidInputs() {

        if (multiblock.coolingRecipeInfo == null) {
            return false;
        }

        SizedChanceFluidIngredient fluidInput = multiblock.coolingRecipeInfo.recipe.getFluidIngredients().get(0);
        SizedChanceFluidIngredient fluidOutput = multiblock.coolingRecipeInfo.recipe.getFluidProducts().get(0);
        Tank outputTank = multiblock.tanks.get(1);
        long maximumHeatChange = multiblock.cooling;
        int recipeHeat = multiblock.coolingRecipeInfo.recipe.getHeatRequired();

        if (multiblock.getTemperature() <= multiblock.coolingRecipeInfo.recipe.getCoolantTemperature()) {
            return false;
        }

        if (fluidOutput.amount() <= 0 || fluidOutput.getStack() == null)
            return false;


        double recipesPerTick = maximumHeatChange / (double) (recipeHeat);

        if (!outputTank.isEmpty()) {
            if (!FluidStack.isSameFluidSameComponents(outputTank.getFluid(), fluidOutput.getStack())) {
                return false;
            }
            if (outputTank.getFluidAmount() + (recipesPerTick + excessCoolingRecipes) * fluidOutput.amount() > outputTank.getCapacity()) {
                return false;
            }
        }

        if (multiblock.heatBuffer.getHeatStored() < recipeHeat) {
            return false;
        }

        return true;
    }

    private void produceFluidProducts() {
        SizedChanceFluidIngredient fluidInput = multiblock.coolingRecipeInfo.recipe.getFluidIngredients().get(0);
        SizedChanceFluidIngredient fluidOutput = multiblock.coolingRecipeInfo.recipe.getFluidProducts().get(0);
        Tank inputTank = multiblock.tanks.get(0);
        Tank outputTank = multiblock.tanks.get(1);
        long maximumHeatChange = multiblock.cooling;
        int recipeHeat = multiblock.coolingRecipeInfo.recipe.getHeatRequired();

        double recipesPerTick = maximumHeatChange / (double) (recipeHeat);

        if (recipesPerTick * fluidInput.amount() > inputTank.getFluidAmount()) {
            recipesPerTick = inputTank.getFluidAmount() / (double) fluidInput.amount();
        }

        if (recipesPerTick * recipeHeat > multiblock.heatBuffer.getHeatStored()) {
            recipesPerTick = multiblock.heatBuffer.getHeatStored() / (recipeHeat);
        }


        int recipesThisTick = (int) Math.floor(recipesPerTick);
        excessCoolingRecipes += recipesPerTick - recipesThisTick;

        if (excessCoolingRecipes >= 1) {
            recipesThisTick += (int) Math.floor(excessCoolingRecipes);
            excessCoolingRecipes -= Math.floor(excessCoolingRecipes);
        }


        inputTank.changeFluidAmount(-recipesThisTick * fluidInput.amount());
        if (inputTank.getFluidAmount() <= 0) inputTank.setFluidStored(null);

        if (outputTank.isEmpty()) {
            outputTank.changeFluidStored(fluidOutput.getStack().getFluid(), recipesThisTick * fluidOutput.amount());
        } else {
            outputTank.changeFluidAmount(recipesThisTick * fluidOutput.amount());
        }

        double heatChange = recipesThisTick * recipeHeat;

        excessHeat += heatChange;

        if (excessHeat > 1) {
            long thisTickHeatChange = (long) Math.floor(excessHeat);
            excessHeat -= thisTickHeatChange;
            multiblock.heatBuffer.changeHeatStored(-thisTickHeatChange);
        }
    }

    // Heating

    protected void externalHeating() {
        multiblock.heatBuffer.addHeat(multiblock.getExternalHeating(), false);
        multiblock.currentHeating += multiblock.getExternalHeating();
    }

    protected void internalHeating() {
        multiblock.heatBuffer.addHeat(multiblock.rawHeating, false);
        multiblock.currentHeating += multiblock.rawHeating;
    }

    // Redstone

    protected boolean isRedstonePowered() {
        for (TileAcceleratorRedstonePort port : getPartMap(TileAcceleratorRedstonePort.class).values()) {
            if (!getWorld().getBlockState(port.getBlockPos()).getValue(ACTIVE).booleanValue()) {
                if (port.checkIsRedstonePowered(getWorld(), port.getBlockPos())) {
                    return true;
                }
            }
        }


        if (multiblock.controller != null && multiblock.controller.checkIsRedstonePowered(getWorld(), multiblock.controller.getTilePos())) return true;
        return false;
    }

    protected int getRedstoneLevel() {
        int level = getWorld().getBestNeighborSignal(multiblock.controller.getTilePos());

        for (TileAcceleratorRedstonePort port : getPartMap(TileAcceleratorRedstonePort.class).values()) {
            if (!getWorld().getBlockState(port.getBlockPos()).getValue(ACTIVE).booleanValue()) {
                if (getWorld().getBestNeighborSignal(port.getBlockPos()) > level) {
                    level = getWorld().getBestNeighborSignal(port.getBlockPos());
                }
            }
        }
        return level;
    }

    protected void updateRedstone() {

        for (TileAcceleratorRedstonePort port : getPartMap(TileAcceleratorRedstonePort.class).values()) {
            if (multiblock.getWorld().getBlockState(port.getBlockPos()).getValue(ACTIVE)) {
                port.setRedstoneLevel((int) (15 * (multiblock.getTemperature() / (double) multiblock.maxOperatingTemp)));
            }
        }
    }

    // Client

    public void onUpdateClient() {

    }

    // NBT


    @Override
    public void writeToLogicTag(CompoundTag data, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        data.putDouble("excessCoolingRecipes", excessCoolingRecipes);
        data.putDouble("excessHeat", excessHeat);
    }

    @Override
    public void readFromLogicTag(CompoundTag data, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        excessCoolingRecipes = data.getDouble("excessCoolingRecipes");
        excessHeat = data.getDouble("excessHeat");
    }

    // Packets

    @Override
    public AcceleratorUpdatePacket getMultiblockUpdatePacket() {
        return null;
    }

    @Override
    public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message) {
    }

    public void clearAllMaterial() {
        for (Tank tank : multiblock.tanks) {
            tank.setFluidStored(null);
        }
    }
}