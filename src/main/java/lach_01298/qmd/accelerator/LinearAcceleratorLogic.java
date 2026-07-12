package lach_01298.qmd.accelerator;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.LinearAcceleratorUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.recipe.types.AcceleratorSourceRecipe;
import lach_01298.qmd.util.Equations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;
import static lach_01298.qmd.recipe.QMDRecipes.accelerator_source;
import static net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class LinearAcceleratorLogic extends AcceleratorLogic {

    protected TileAcceleratorIonSource source;
    public QMDRecipeInfo<AcceleratorSourceRecipe> recipeInfo;

    // Multiblock logic

    public LinearAcceleratorLogic(AcceleratorLogic oldLogic) {
        super(oldLogic);
		
		/*
		beam 0 = input particle
		beam 1 = output particle
		tank 0 = input coolant
		tank 1 = output coolant
		tank 2 = input fluid
		*/


        //on the rare occasion of changing the multiblock to a different type with the tank full
        if (!(oldLogic instanceof LinearAcceleratorLogic || oldLogic.getID().equals(""))) {
            multiblock.tanks.get(2).setFluidStored(null);
        }
    }

    @Override
    public String getID() {
        return "linear_accelerator";
    }

    // Accelerator methods

    @Override
    public int getBeamLength() {
        return multiblock.getExteriorLengthX() > multiblock.getExteriorLengthZ() ? multiblock.getExteriorLengthX() : multiblock.getExteriorLengthZ();
    }

    public TileAcceleratorIonSource getSource() {
        return source;
    }

    // Multiblock validation

    @Override
    public boolean isMachineWhole() {
        Axis axis;
        Accelerator acc = multiblock;

        if (acc.getExteriorLengthY() != getThickness()) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.wrong_height");
            return false;
        }


        if (acc.getExteriorLengthX() > acc.getExteriorLengthZ()) {
            axis = Axis.X;
            if (acc.getExteriorLengthX() < QMDServerConfig.accelerator_linear_min_size) {
                multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.to_short");
                return false;
            }
            if (acc.getExteriorLengthZ() != getThickness()) {
                multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_be_5_wide");
                return false;
            }

        } else {
            axis = Axis.Z;
            if (acc.getExteriorLengthZ() < QMDServerConfig.accelerator_linear_min_size) {
                multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.to_short");
                return false;
            }
            if (acc.getExteriorLengthX() != getThickness()) {
                multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_be_5_wide");
                return false;
            }
        }

        // Beam
        for (BlockPos pos : getinteriorAxisPositions(axis)) {
            if (!(acc.getWorld().getBlockEntity(pos) instanceof TileAcceleratorBeam)) {
                multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_be_beam");
                return false;
            }
        }

        // Source and beam port
        if (axis == Axis.X) {
            BlockPos end1 = acc.getExtremeCoord(false, false, false).offset(0, getThickness() / 2, getThickness() / 2);
            BlockPos end2 = acc.getExtremeCoord(true, false, false).offset(0, getThickness() / 2, getThickness() / 2);

            if (acc.getWorld().getBlockEntity(end1) instanceof TileAcceleratorBeamPort && acc.getWorld().getBlockEntity(end2) instanceof TileAcceleratorBeamPort) {
                List<TileAcceleratorBeamPort> ports = new ArrayList<>();
                ports.add((TileAcceleratorBeamPort) acc.getWorld().getBlockEntity(end1));
                ports.add((TileAcceleratorBeamPort) acc.getWorld().getBlockEntity(end2));

                int inputs = 0;
                int outputs = 0;
                for (TileAcceleratorBeamPort port : ports) {
                    if (port.getIOType() == IOType.INPUT) {
                        inputs++;
                    }

                    if (port.getIOType() == IOType.OUTPUT) {
                        outputs++;
                    }
                }
                if (inputs != 1 || outputs != 1) {
                    multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_have_io");
                    return false;
                }
            } else {
                if (!(acc.getWorld().getBlockEntity(end1) instanceof TileAcceleratorIonSource && acc.getWorld().getBlockEntity(end2) instanceof TileAcceleratorBeamPort) &&
                        !(acc.getWorld().getBlockEntity(end1) instanceof TileAcceleratorBeamPort && acc.getWorld().getBlockEntity(end2) instanceof TileAcceleratorIonSource)) {
                    multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.have_source_and_beam_port");
                    return false;
                }
                if (acc.getWorld().getBlockEntity(end1) instanceof TileAcceleratorIonSource) {
                    if (acc.getWorld().getBlockState(end1).getValue(FACING_ALL) != Direction.EAST) {
                        multiblock.setLastError(end1, QMD.MOD_ID + ".multiblock_validation.accelerator.linear.source_must_face_in");
                        return false;
                    }
                } else {
                    if (acc.getWorld().getBlockState(end2).getValue(FACING_ALL) != Direction.WEST) {
                        multiblock.setLastError(end2, QMD.MOD_ID + ".multiblock_validation.accelerator.linear.source_must_face_in");
                        return false;
                    }
                }
            }

        }
        if (axis == Axis.Z) {
            BlockPos end1 = acc.getExtremeCoord(false, false, false).offset(getThickness() / 2, getThickness() / 2, 0);
            BlockPos end2 = acc.getExtremeCoord(false, false, true).offset(getThickness() / 2, getThickness() / 2, 0);

            if (acc.getWorld().getBlockEntity(end1) instanceof TileAcceleratorBeamPort && acc.getWorld().getBlockEntity(end2) instanceof TileAcceleratorBeamPort) {
                List<TileAcceleratorBeamPort> ports = new ArrayList<>();
                ports.add((TileAcceleratorBeamPort) acc.getWorld().getBlockEntity(end1));
                ports.add((TileAcceleratorBeamPort) acc.getWorld().getBlockEntity(end2));

                int inputs = 0;
                int outputs = 0;
                for (TileAcceleratorBeamPort port : ports) {
                    if (port.getIOType() == IOType.INPUT) {
                        inputs++;
                    }

                    if (port.getIOType() == IOType.OUTPUT) {
                        outputs++;
                    }
                }
                if (inputs != 1 || outputs != 1) {
                    multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_have_io", Collections.emptyList());
                    return false;
                }
            } else {
                if (!(acc.getWorld().getBlockEntity(end1) instanceof TileAcceleratorIonSource && acc.getWorld().getBlockEntity(end2) instanceof TileAcceleratorBeamPort) &&
                        !(acc.getWorld().getBlockEntity(end1) instanceof TileAcceleratorBeamPort && acc.getWorld().getBlockEntity(end2) instanceof TileAcceleratorIonSource)) {
                    multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.have_source_and_beam_port", Collections.emptyList());
                    return false;
                }
                if (acc.getWorld().getBlockEntity(end1) instanceof TileAcceleratorIonSource) {
                    if (acc.getWorld().getBlockState(end1).getValue(FACING_ALL) != Direction.SOUTH) {
                        multiblock.setLastError(end1, QMD.MOD_ID + ".multiblock_validation.accelerator.linear.source_must_face_in");
                        return false;
                    }
                } else {
                    if (acc.getWorld().getBlockState(end2).getValue(FACING_ALL) != Direction.NORTH) {
                        multiblock.setLastError(end2, QMD.MOD_ID + ".multiblock_validation.accelerator.linear.source_must_face_in");
                        return false;
                    }
                }
            }


        }

        int sources = 0;
        for (TileAcceleratorIonSource port : getPartMap(TileAcceleratorIonSource.class).values()) {
            sources++;
        }
        if (sources > 1) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.only_one_source");
            return false;
        }
        int ports = 0;
        for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values()) {
            ports++;
        }
        if (ports > 2 - sources) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.to_many_beam_ports");
            return false;
        }

        if (containsBlacklistedPart()) {
            return false;
        }

        return super.isMachineWhole();
    }

    public Set<BlockPos> getinteriorAxisPositions(Axis axis) {
        Set<BlockPos> postions = new HashSet<BlockPos>();
        Accelerator acc = multiblock;

        if (axis == Axis.X) {
            for (BlockPos pos : BlockPos.betweenClosed(
                    acc.getExtremeInteriorCoord(false, false, false).offset(0, acc.getInteriorLengthY() / 2, acc.getInteriorLengthZ() / 2),
                    acc.getExtremeInteriorCoord(true, false, false).offset(0, acc.getInteriorLengthY() / 2, acc.getInteriorLengthZ() / 2))) {
                postions.add(pos.immutable());
            }
        }

        if (axis == Axis.Z) {
            for (BlockPos pos : BlockPos.betweenClosed(
                    acc.getExtremeInteriorCoord(false, false, false).offset(acc.getInteriorLengthX() / 2, acc.getInteriorLengthY() / 2, 0),
                    acc.getExtremeInteriorCoord(false, false, true).offset(acc.getInteriorLengthX() / 2, acc.getInteriorLengthY() / 2, 0))) {
                postions.add(pos.immutable());
            }
        }

        return postions;
    }

    public static final List<Pair<Class<? extends IMultiblockPart<Accelerator>>, String>> PART_BLACKLIST = Lists.newArrayList(
            Pair.of(TileAcceleratorYoke.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_yokes"),
            Pair.of(TileAcceleratorSynchrotronPort.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_synch_ports"),
            Pair.of(TileAcceleratorIonCollector.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_ion_collectors"));

    @Override
    public List<Pair<Class<? extends IMultiblockPart<Accelerator>>, String>> getPartBlacklist() {
        return PART_BLACKLIST;
    }


    // Accelerator formation

    @Override
    public void onAcceleratorFormed() {

        Accelerator acc = multiblock;

        if (!getWorld().isClientSide()) {
            resetBeams();

            Axis axis;

            if (multiblock.getExteriorLengthX() > multiblock.getExteriorLengthZ()) {
                axis = Axis.X;
            } else {

                axis = Axis.Z;
            }

            setBeamlineFunctional(getinteriorAxisPositions(axis));
            formComponents();

            // source
            for (TileAcceleratorIonSource source : acc.getPartMap(TileAcceleratorIonSource.class).values()) {
                this.source = source;
            }

            if (source != null) {
                source.setIONumber(2);
            }

            multiblock.tanks.get(2).setCapacity(QMDServerConfig.accelerator_base_input_tank_capacity * 1000);
            multiblock.tanks.get(2).setAllowedFluids(accelerator_source.getValidFluids(getWorld(), 0));

            // source ports
            for (TileAcceleratorPort port : acc.getPartMap(TileAcceleratorPort.class).values()) {
                port.setSource(this);
            }

            for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values()) {
                if (port.getIOType() == IOType.INPUT) {
                    port.setIONumber(0);
                }
                if (port.getIOType() == IOType.OUTPUT) {
                    port.setIONumber(1);
                }
            }
        }

        refreshStats();
        super.onAcceleratorFormed();
    }

    // Accelerator disassembly

    public void onAcceleratorBroken() {
        if (source != null) {
            source.setIONumber(0);
        }
        source = null;
        super.onAcceleratorBroken();
    }

    // Accelerator Operation

    @Override
    public boolean onUpdateServer() {
        super.onUpdateServer();


        if (multiblock.isControllorOn) {
            if (source != null) {
                //refreshRecipe(); called in shouldUseEnergy

                if (recipeInfo != null) {

                    produceSourceBeam();
                } else {
                    resetOutputBeam();
                }
            } else {
                produceBeam();
            }
        } else {
            resetOutputBeam();
        }
        push();

        multiblock.sendMultiblockUpdatePacketToListeners();

        return true;
    }

    @Override
    protected void refreshBeams() {
        multiblock.beams.get(0).setParticleStack(null);
        multiblock.beams.get(1).setParticleStack(null);
        pull();
    }

    @Override
    protected boolean shouldUseEnergy() {
        if (source != null) {
            refreshRecipe();
            if (recipeInfo != null) {
                return true;
            }
        } else if (multiblock.beams.get(0).getParticleStack() != null) {
            return true;
        }

        return false;
    }


    // Recipe handling

    private void resetOutputBeam() {
        multiblock.beams.get(1).setParticleStack(null);
    }

    private void produceSourceBeam() {
        ParticleStack outputStack = recipeInfo.recipe.getParticleProducts().get(0);

        if (outputStack != null) {

            // amount setting
            int outputAmount = outputStack.getAmount() * source.outputParticleMultiplier;
            if (!source.getInventoryStacks().get(0).isEmpty()) {
                if (source.getInventoryStacks().get(0).getItem() instanceof IItemParticleAmount) {
                    IItemParticleAmount item = (IItemParticleAmount) source.getInventoryStacks().get(0).getItem();
                    if (item.getAmountStored(source.getInventoryStacks().get(0)) < outputAmount) {
                        outputAmount = item.getAmountStored(source.getInventoryStacks().get(0));
                    }

                    source.getInventoryStacks().set(0, item.use(source.getInventoryStacks().get(0), outputAmount));

                    // switch slot items
                    if ((item.isEmptyItem(source.getInventoryStacks().get(0)) && !source.getInventoryStacks().get(1).isEmpty())) {
                        ItemStack stack = source.getInventoryStacks().get(1).copy();
                        source.getInventoryStacks().set(1, source.getInventoryStacks().get(0).copy());
                        source.getInventoryStacks().set(0, stack);
                    }
                }
            } else if (!source.getTanks().get(0).isEmpty()) {
                FluidStack fluidStack = recipeInfo.recipe.getFluidIngredients().get(0).getStack();

                Tank tank = source.getTanks().get(0);
                int mBtoDrain = fluidStack.getAmount() * source.outputParticleMultiplier;

                FluidStack mBDrained = tank.drain(mBtoDrain, EXECUTE);
                outputAmount *= mBDrained.getAmount() / mBtoDrain;
            }

            // energy setting
            if (multiblock.computerControlled) {
                outputStack.addMeanEnergy((long) (Equations.linacEnergyGain(multiblock.acceleratingVoltage, outputStack) * (multiblock.energyPercentage / 100d)));
            } else {
                outputStack.addMeanEnergy((long) (Equations.linacEnergyGain(multiblock.acceleratingVoltage, outputStack) * getRedstoneLevel() / 15d));
            }
            outputStack.setAmount(outputAmount);

            // focus setting
            outputStack.addFocus(source.outputFocus);
            outputStack.addFocus(Equations.focusGain(multiblock.quadrupoleStrength, outputStack) - Equations.focusLoss(getBeamLength(), outputStack));
            if (outputStack.getFocus() <= 0) {
                multiblock.errorCode = Accelerator.errorCode_NotEnoughQuadrupoles;
            }

            multiblock.beams.get(1).setParticleStack(outputStack);
        }

    }

    private void produceBeam() {
        ParticleStack inputBeam = multiblock.beams.get(0).getParticleStack();

        if (inputBeam != null) {
            multiblock.beams.get(1).setParticleStack(inputBeam.copy());
            ParticleStack outputBeam = multiblock.beams.get(1).getParticleStack();
            if (outputBeam != null) {
                outputBeam.addFocus(Equations.focusGain(multiblock.quadrupoleStrength, outputBeam) - Equations.focusLoss(getBeamLength(), outputBeam));

                if (multiblock.computerControlled) {
                    outputBeam.addMeanEnergy((long) (Equations.linacEnergyGain(multiblock.acceleratingVoltage, outputBeam) * (multiblock.energyPercentage / 100d)));
                } else {
                    outputBeam.addMeanEnergy((long) (Equations.linacEnergyGain(multiblock.acceleratingVoltage, outputBeam) * getRedstoneLevel() / 15d));
                }


                if (outputBeam.getFocus() <= 0) {
                    outputBeam = null;
                    multiblock.errorCode = Accelerator.errorCode_NotEnoughQuadrupoles;
                }
            }
        }
    }

    protected void refreshRecipe() {
        // switch slot items
        if (source.getInventoryStacks().get(0).isEmpty() && !source.getInventoryStacks().get(1).isEmpty()) {
            ItemStack stack = source.getInventoryStacks().get(1).copy();
            source.getInventoryStacks().set(1, ItemStack.EMPTY);
            source.getInventoryStacks().set(0, stack);
        }

        ArrayList<ItemStack> items = new ArrayList<>();
        ItemStack item = IItemParticleAmount.cleanNBT(source.getInventoryStacks().get(0));
        items.add(item);
        ArrayList<Tank> tanks = new ArrayList<>();
        tanks.add(source.getTanks().get(0));
        recipeInfo = accelerator_source.getRecipeInfoFromInputs(getWorld(), items, tanks, new ArrayList<>());
    }

    // Packets

    @Override
    public AcceleratorUpdatePacket getMultiblockUpdatePacket() {

        return new LinearAcceleratorUpdatePacket(multiblock.controller.getTilePos(),
                multiblock.isControllorOn, multiblock.cooling, multiblock.rawHeating, multiblock.currentHeating, multiblock.maxCoolantIn, multiblock.maxCoolantOut, multiblock.maxOperatingTemp,
                multiblock.requiredEnergy, multiblock.efficiency, multiblock.acceleratingVoltage,
                multiblock.RFCavityNumber, multiblock.quadrupoleNumber, multiblock.quadrupoleStrength, multiblock.dipoleNumber, multiblock.dipoleStrength, multiblock.errorCode,
                multiblock.heatBuffer, multiblock.energyStorage, multiblock.tanks, multiblock.beams);
    }

    @Override
    public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message) {
        super.onMultiblockUpdatePacket(message);
        if (message instanceof LinearAcceleratorUpdatePacket) {
            LinearAcceleratorUpdatePacket packet = (LinearAcceleratorUpdatePacket) message;
        }
    }
}