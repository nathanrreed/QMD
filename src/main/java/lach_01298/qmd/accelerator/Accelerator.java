package lach_01298.qmd.accelerator;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.NuclearcraftNeohaul;
import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
import com.nred.nuclearcraft.multiblock.ILogicMultiblock;
import com.nred.nuclearcraft.multiblock.Multiblock;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.multiblock.CuboidalOrToroidalMultiblock;
import lach_01298.qmd.multiblock.IMultiBlockTank;
import lach_01298.qmd.multiblock.IQMDPacketMultiblock;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.recipe.types.AcceleratorCoolingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

public class Accelerator extends CuboidalOrToroidalMultiblock<Accelerator> implements ILogicMultiblock<Accelerator, AcceleratorLogic>, IQMDPacketMultiblock<Accelerator, AcceleratorUpdatePacket>, IMultiBlockTank {
    protected @Nonnull AcceleratorLogic logic = new AcceleratorLogic(this);

    protected final Long2ObjectMap<TileAcceleratorBeam> beamMap = new Long2ObjectOpenHashMap<>();
    protected final Long2ObjectMap<RFCavity> rfCavityMap = new Long2ObjectOpenHashMap<>();
    protected final Long2ObjectMap<QuadrupoleMagnet> quadrupoleMap = new Long2ObjectOpenHashMap<>();
    protected final Long2ObjectMap<DipoleMagnet> dipoleMap = new Long2ObjectOpenHashMap<>();

    protected final ObjectSet<TileAcceleratorBeamPort> beamPorts = new ObjectOpenHashSet<>();

    public IAcceleratorController<?> controller;

    public final HeatBuffer heatBuffer = new HeatBuffer(QMDServerConfig.accelerator_base_heat_capacity);
    public final EnergyStorage energyStorage = new EnergyStorage(QMDServerConfig.accelerator_base_energy_capacity);
    public List<Tank> tanks = Lists.newArrayList(
            new Tank(QMDServerConfig.accelerator_base_input_tank_capacity, QMDRecipes.accelerator_cooling.getValidFluids(getWorld(), 0)),
            new Tank(QMDServerConfig.accelerator_base_output_tank_capacity, null),
            new Tank(1, null), new Tank(1, null), new Tank(1, null), new Tank(1, null), new Tank(1, null));
    public final List<ParticleStorageAccelerator> beams = Lists.newArrayList(new ParticleStorageAccelerator(), new ParticleStorageAccelerator(), new ParticleStorageAccelerator());

    public boolean isControllorOn = false; //for controller blockstate
    public boolean isNew = true; // if this accelerator is a new accelerator

    public static final int MAX_TEMP = 400;

    public int ambientTemp = 293, maxOperatingTemp = 0;

    public long cooling = 0L, rawHeating = 0L, currentHeating = 0L;
    public int maxCoolantIn = 0, maxCoolantOut = 0; // micro buckets per tick
    public double efficiency = 0D;
    public int requiredEnergy = 0, acceleratingVoltage = 0;
    public int RFCavityNumber = 0, quadrupoleNumber = 0, dipoleNumber = 0;
    public double quadrupoleStrength = 0, dipoleStrength = 0;

    //OC computer control
    public int energyPercentage = 0;
    public boolean computerControlled = false;

    public int errorCode = 0;
    public static final int errorCode_Nothing = 0;
    public static final int errorCode_ToHot = 1;
    public static final int errorCode_OutOfPower = 2;
    public static final int errorCode_NotEnoughQuadrupoles = 3;
    public static final int errorCode_InputParticleEnergyToLow = 4;
    public static final int errorCode_InputParticleEnergyToHigh = 5;

    public static final int thickness = 5;

    public QMDRecipeInfo<AcceleratorCoolingRecipe> coolingRecipeInfo;

    protected final Set<Player> updatePacketListeners = new ObjectOpenHashSet<>();

    public Accelerator(Level level) {
        super(level, thickness);
    }

    @Override
    public @Nonnull AcceleratorLogic getLogic() {
        return logic;
    }

    @Override
    public void setLogic(String logicID) {
        if (logicID.equals(logic.getID())) return;

        UnaryOperator<AcceleratorLogic> constructor = switch (logicID) {
            case "" -> AcceleratorLogic::new;
            case "linear_accelerator" -> LinearAcceleratorLogic::new;
            case "ring_accelerator" -> RingAcceleratorLogic::new;
            case "beam_diverter" -> BeamDiverterLogic::new;
            case "decelerator" -> DeceleratorLogic::new;
            case "beam_splitter" -> BeamSplitterLogic::new;
            case "mass_spectrometer" -> MassSpectrometerLogic::new;
            default -> throw new IllegalStateException("Unexpected logicID: " + logicID);
        };
        logic = getNewLogic(constructor);
        SetToriodThickness(logic.getThickness());
    }

    // Multiblock Part Getters

    public Long2ObjectMap<TileAcceleratorBeam> getBeamMap() {
        return beamMap;
    }

    public Long2ObjectMap<RFCavity> getRFCavityMap() {
        return rfCavityMap;
    }

    public Long2ObjectMap<QuadrupoleMagnet> getQuadrupoleMap() {
        return quadrupoleMap;
    }

    public Long2ObjectMap<DipoleMagnet> getDipoleMap() {
        return dipoleMap;
    }

    public ObjectSet<TileAcceleratorBeamPort> getValidBeamPorts() {
        return beamPorts;
    }


    public boolean isValidRFCavity(BlockPos center, Axis axis) {
        if (!(this.getWorld().getBlockEntity(center.above()) instanceof TileAcceleratorRFCavity)) {
            return false;
        }

        Class cavityType = this.getWorld().getBlockEntity(center.above()).getClass();

        if (!cavityType.isInstance(this.getWorld().getBlockEntity(center.below()))) {
            return false;
        }

        if (axis == Axis.X) {
            //check no Cavitys next to this one
            if (this.getWorld().getBlockEntity(center.above().east()) instanceof TileAcceleratorRFCavity || this.getWorld().getBlockEntity(center.above().west()) instanceof TileAcceleratorRFCavity) {
                return false;
            }

            if (cavityType.isInstance(this.getWorld().getBlockEntity(center.north()))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.south()))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.offset(0, 1, 1)))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.offset(0, 1, -1)))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.offset(0, -1, 1)))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.offset(0, -1, -1)))) {
                return true;
            }
        }
        if (axis == Axis.Z) {
            //check no Cavitys next to this one
            if (this.getWorld().getBlockEntity(center.above().north()) instanceof TileAcceleratorRFCavity || this.getWorld().getBlockEntity(center.above().south()) instanceof TileAcceleratorRFCavity) {
                return false;
            }

            if (cavityType.isInstance(this.getWorld().getBlockEntity(center.east()))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.west()))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.offset(1, 1, 0)))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.offset(-1, 1, 0)))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.offset(1, -1, 0)))
                    && cavityType.isInstance(this.getWorld().getBlockEntity(center.offset(-1, -1, 0)))) {
                return true;
            }
        }
        return false;
    }


    public boolean isValidQuadrupole(BlockPos center, Axis axis) {
        if (!(this.getWorld().getBlockEntity(center.above()) instanceof TileAcceleratorMagnet)) {
            return false;
        }

        Class<? extends BlockEntity> magnetType = this.getWorld().getBlockEntity(center.above()).getClass();

        if (!magnetType.isInstance(this.getWorld().getBlockEntity(center.below()))) {
            return false;
        }

        if (axis == Axis.X) {
            if (magnetType.isInstance(this.getWorld().getBlockEntity(center.north())) && magnetType.isInstance(this.getWorld().getBlockEntity(center.south()))) {
                return true;
            }
        }
        if (axis == Axis.Z) {
            if (magnetType.isInstance(this.getWorld().getBlockEntity(center.east())) && magnetType.isInstance(this.getWorld().getBlockEntity(center.west()))) {
                return true;
            }
        }
        return false;
    }


    public boolean isValidDipole(BlockPos center, boolean vertical) {
        if (vertical) {
            if (this.getWorld().getBlockEntity(center.north()) instanceof TileAcceleratorMagnet) {
                Class magnetType = this.getWorld().getBlockEntity(center.north()).getClass();
                if (!magnetType.isInstance(this.getWorld().getBlockEntity(center.south()))) {
                    return false;
                }
                if (!(this.getWorld().getBlockEntity(center.north().above()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.north().below()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.north().east()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.north().west()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.north().above().east()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.north().above().west()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.north().below().east()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.north().below().west()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.south().above()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.south().below()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.south().east()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.south().west()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.south().above().east()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.south().above().west()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.south().below().east()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.south().below().west()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.above().east()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.above().west()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.below().east()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.below().west()) instanceof TileAcceleratorYoke)) {
                    return false;
                }
                List<BlockPos> faces = new ArrayList<BlockPos>();
                faces.add(center.above());
                faces.add(center.below());
                faces.add(center.east());
                faces.add(center.west());

                for (BlockPos pos : faces) {
                    if (!(this.getWorld().getBlockEntity(pos) instanceof TileAcceleratorBeam) && !(this.getWorld().getBlockEntity(pos) instanceof TileAcceleratorYoke)) {

                        return false;
                    }
                }
            } else if (this.getWorld().getBlockEntity(center.east()) instanceof TileAcceleratorMagnet) {
                Class magnetType = this.getWorld().getBlockEntity(center.east()).getClass();
                if (!magnetType.isInstance(this.getWorld().getBlockEntity(center.west()))) {
                    return false;
                }

                if (!(this.getWorld().getBlockEntity(center.east().above()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.east().below()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.east().north()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.east().south()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.east().above().north()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.east().above().south()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.east().below().north()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.east().below().south()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.west().above()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.west().below()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.west().north()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.west().south()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.west().above().north()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.west().above().south()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.west().below().north()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.west().below().south()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.above().north()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.above().south()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.below().north()) instanceof TileAcceleratorYoke) ||
                        !(this.getWorld().getBlockEntity(center.below().south()) instanceof TileAcceleratorYoke)) {

                    return false;
                }
                List<BlockPos> faces = new ArrayList<BlockPos>();
                faces.add(center.above());
                faces.add(center.below());
                faces.add(center.north());
                faces.add(center.south());

                for (BlockPos pos : faces) {
                    if (!(this.getWorld().getBlockEntity(pos) instanceof TileAcceleratorBeam) && !(this.getWorld().getBlockEntity(pos) instanceof TileAcceleratorYoke)) {

                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            if (!(this.getWorld().getBlockEntity(center.above()) instanceof TileAcceleratorMagnet)) {
                return false;
            }

            Class magnetType = this.getWorld().getBlockEntity(center.above()).getClass();

            if (!magnetType.isInstance(this.getWorld().getBlockEntity(center.below()))) {
                return false;
            }

            if (!(this.getWorld().getBlockEntity(center.above().north()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.above().south()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.above().east()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.above().west()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.above().north().east()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.above().north().west()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.above().south().east()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.above().south().west()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.below().north()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.below().south()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.below().east()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.below().west()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.below().north().east()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.below().north().west()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.below().south().east()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.below().south().west()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.north().east()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.north().west()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.south().east()) instanceof TileAcceleratorYoke) ||
                    !(this.getWorld().getBlockEntity(center.south().west()) instanceof TileAcceleratorYoke)) {

                return false;
            }
            List<BlockPos> faces = new ArrayList<BlockPos>();
            faces.add(center.north());
            faces.add(center.south());
            faces.add(center.east());
            faces.add(center.west());

            for (BlockPos pos : faces) {
                if (!(this.getWorld().getBlockEntity(pos) instanceof TileAcceleratorBeam) && !(this.getWorld().getBlockEntity(pos) instanceof TileAcceleratorYoke)) {

                    return false;
                }
            }
        }

        return true;
    }

    // Multiblock Methods

    @Override
    protected void onPartAdded(IMultiblockPart<Accelerator> newPart) {
        super.onPartAdded(newPart);
        logic.onBlockAdded(newPart);
    }

    @Override
    protected void onPartRemoved(IMultiblockPart<Accelerator> oldPart) {
        super.onPartRemoved(oldPart);
        logic.onBlockRemoved(oldPart);
    }

    @Override
    protected void onMachineAssembled() {
        logic.onMachineAssembled();
    }

    @Override
    protected void onMachineRestored() {
        logic.onMachineRestored();
    }

    @Override
    protected void onMachinePaused() {
        logic.onMachinePaused();
    }

    @Override
    protected void onMachineDisassembled() {
        logic.onMachineDisassembled();
    }

    @Override
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        return setLogic(this) && super.isMachineWhole(validatorCallback) && logic.isMachineWhole();
    }

    public boolean setLogic(Multiblock<?> multiblock) {
        if (getPartMap(IAcceleratorController.class).isEmpty()) {
            multiblock.setLastError(NuclearcraftNeohaul.MODID + ".multiblock_validation.no_controller", Collections.emptyList());
            return false;
        }
        if (getPartMap(IAcceleratorController.class).size() > 1) {
            multiblock.setLastError(NuclearcraftNeohaul.MODID + ".multiblock_validation.too_many_controllers", Collections.emptyList());
            return false;
        }

        for (IAcceleratorController contr : getPartMap(IAcceleratorController.class).values()) {
            controller = contr;
        }

        setLogic(controller.getLogicID());

        return true;
    }

    @Override
    protected void onAssimilate(IMultiblockController<Accelerator> assimilated) {
        logic.onAssimilate(assimilated);

    }

    @Override
    protected void onAssimilated(IMultiblockController<Accelerator> assimilator) {
        logic.onAssimilated(assimilator);
    }

    // Server

    @Override
    protected boolean updateServer() {
        boolean flag = false;
        updateActivity();

        if (logic.onUpdateServer()) {
            flag = true;
        }

        return flag;
    }


    public void updateActivity() {
        boolean wasControllerOn = isControllorOn;
        isControllorOn = isAssembled() && logic.isAcceleratorOn();
        if (isControllorOn != wasControllerOn) {
            if (controller != null) {

                controller.setActivity(isControllorOn);
                sendMultiblockUpdatePacketToAll();
            }
        }
    }

    public int getTemperature() {
        return Math.round(MAX_TEMP * (float) heatBuffer.getHeatStored() / heatBuffer.getHeatCapacity());
    }

    public long getExternalHeating() {
        return (long) ((ambientTemp - getTemperature()) * QMDServerConfig.accelerator_thermal_conductivity * getExteriorSurfaceArea());
    }

    public long getMaxExternalHeating() {
        return (long) (ambientTemp * QMDServerConfig.accelerator_thermal_conductivity * getExteriorSurfaceArea());
    }

    // Client

    @Override
    protected void updateClient() {
        logic.onUpdateClient();
    }

    // NBT

    @Override
    public CompoundTag syncDataTo(CompoundTag data, HolderLookup.Provider registries, SyncReason syncReason) {
        heatBuffer.writeToNBT(data, "heatBuffer");
        energyStorage.writeToNBT(data, registries, "energyStorage");
        writeTanks(tanks, data, registries, "tanks");
        writeBeams(beams, data);

        data.putBoolean("isAcceleratorOn", isControllorOn);
        data.putLong("cooling", cooling);
        data.putLong("rawHeating", rawHeating);
        data.putInt("coolantIn", maxCoolantIn);
        data.putInt("coolantOut", maxCoolantOut);
        data.putDouble("maxOperatingTemp", maxOperatingTemp);
        data.putLong("requiredEnergy", requiredEnergy);
        data.putDouble("efficiency", efficiency);
        data.putInt("acceleratingVoltage", acceleratingVoltage);
        data.putInt("RFCavityNumber", RFCavityNumber);
        data.putInt("quadrapoleNumber", quadrupoleNumber);
        data.putDouble("quadrupoleStrength", quadrupoleStrength);
        data.putInt("dipoleNumber", dipoleNumber);
        data.putDouble("dipoleStrength", dipoleStrength);
        data.putInt("errorCode", errorCode);
        data.putBoolean("isNew", isNew);
        data.putBoolean("computerControlled", computerControlled);
        data.putInt("energyPercentage", energyPercentage);

        writeLogicNBT(data, registries, syncReason);
        return data;
    }

    @Override
    public void syncDataFrom(CompoundTag data, HolderLookup.Provider registries, SyncReason syncReason) {
        heatBuffer.readFromNBT(data, "heatBuffer");
        energyStorage.readFromNBT(data, registries, "energyStorage");
        readTanks(tanks, data, registries, "tanks");
        readBeams(beams, data);


        isControllorOn = data.getBoolean("isAcceleratorOn");
        cooling = data.getLong("cooling");
        rawHeating = data.getLong("rawHeating");
        maxCoolantIn = data.getInt("coolantIn");
        maxCoolantOut = data.getInt("coolantOut");
        maxOperatingTemp = data.getInt("maxOperatingTemp");
        requiredEnergy = data.getInt("requiredEnergy");
        efficiency = data.getDouble("efficiency");
        acceleratingVoltage = data.getInt("acceleratingVoltage");
        RFCavityNumber = data.getInt("RFCavityNumber");
        quadrupoleNumber = data.getInt("quadrapoleNumber");
        quadrupoleStrength = data.getDouble("quadrupoleStrength");
        dipoleNumber = data.getInt("dipoleNumber");
        dipoleStrength = data.getDouble("dipoleStrength");
        errorCode = data.getInt("errorCode");
        isNew = data.getBoolean("isNew");
        computerControlled = data.getBoolean("computerControlled");
        energyPercentage = data.getInt("energyPercentage");

        readLogicNBT(data, registries, syncReason);
    }


    // Packets

    @Override
    public Set<Player> getMultiblockUpdatePacketListeners() {
        return updatePacketListeners;
    }

    @Override
    public AcceleratorUpdatePacket getMultiblockUpdatePacket() {
        return logic.getMultiblockUpdatePacket();
    }

    @Override
    public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message) {
        heatBuffer.setHeatCapacity(message.heatBuffer.getHeatCapacity());
        heatBuffer.setHeatStored(message.heatBuffer.getHeatStored());
        energyStorage.setStorageCapacity(message.energyStorage.getMaxEnergyStored());
        energyStorage.setEnergyStored(message.energyStorage.getEnergyStored());

        for (int i = 0; i < tanks.size(); i++) tanks.get(i).readInfo(message.tanksInfo.get(i));
        for (int i = 0; i < message.beams.size(); i++) beams.set(i, message.beams.get(i));

        isControllorOn = message.isAcceleratorOn;
        cooling = message.cooling;
        rawHeating = message.rawHeating;
        currentHeating = message.currentHeating;
        maxCoolantIn = message.maxCoolantIn;
        maxCoolantOut = message.maxCoolantOut;
        maxOperatingTemp = message.maxOperatingTemp;
        requiredEnergy = message.requiredEnergy;
        efficiency = message.efficiency;
        acceleratingVoltage = message.acceleratingVoltage;
        RFCavityNumber = message.RFCavityNumber;
        quadrupoleNumber = message.quadrupoleNumber;
        quadrupoleStrength = message.quadrupoleStrength;
        dipoleNumber = message.dipoleNumber;
        dipoleStrength = message.dipoleStrength;
        errorCode = message.errorCode;

        logic.onMultiblockUpdatePacket(message);
    }

	/*public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player)
	{
		return logic.getContainer(player);
	}*/

    @Override
    public void clearAllMaterial() {
        logic.clearAllMaterial();
        super.clearAllMaterial();
    }

    // Multiblock Validators

    @Override
    protected boolean isBlockGoodForInterior(Level level, int x, int y, int z, IMultiblockValidator iMultiblockValidator) {
        return logic.isBlockGoodForInterior(level, x, y, z);
    }

    @Override
    public int getMinimumInteriorLength() {
        return logic.getMinimumInteriorLength();
    }

    @Override
    public int getMaximumInteriorLength() {
        return logic.getMaximumInteriorLength();
    }

    public CompoundTag writeBeams(List<ParticleStorageAccelerator> beams, CompoundTag data) {
        for (int i = 0; i < beams.size(); i++) {
            beams.get(i).writeToNBT(data, i);
        }

        return data;
    }

    public void readBeams(List<ParticleStorageAccelerator> beams, CompoundTag data) {
        for (int i = 0; i < beams.size(); i++) {
            beams.get(i).readFromNBT(data, i);
        }
    }

    @Override
    public List<Tank> getTanks() {
        return tanks;
    }
}