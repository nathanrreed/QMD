package lach_01298.qmd.particleChamber;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.NuclearcraftNeohaul;
import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.multiblock.ILogicMultiblock;
import com.nred.nuclearcraft.multiblock.Multiblock;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.multiblock.IMultiBlockTank;
import lach_01298.qmd.multiblock.IQMDPacketMultiblock;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

public class ParticleChamber extends Multiblock<ParticleChamber> implements ILogicMultiblock<ParticleChamber, ParticleChamberLogic>, IQMDPacketMultiblock<ParticleChamber, ParticleChamberUpdatePacket>, IMultiBlockTank {
    protected @Nonnull ParticleChamberLogic logic = new ParticleChamberLogic(this);
    protected @Nonnull CompoundTag cachedData = new CompoundTag();

    public boolean refreshFlag = true, isChamberOn = false;
    public int requiredEnergy;
    public double efficiency = 1;

    public IParticleChamberController<?> controller;

    public final EnergyStorage energyStorage = new EnergyStorage(QMDServerConfig.particle_chamber_base_energy_capacity);

    public List<ParticleStorageAccelerator> beams = Lists.newArrayList(new ParticleStorageAccelerator(), new ParticleStorageAccelerator(), new ParticleStorageAccelerator(), new ParticleStorageAccelerator(), new ParticleStorageAccelerator(), new ParticleStorageAccelerator());
    public List<Tank> tanks = Lists.newArrayList(new Tank(QMDServerConfig.particle_chamber_input_tank_capacity, null), new Tank(QMDServerConfig.particle_chamber_output_tank_capacity, null));

    protected final Set<Player> updatePacketListeners = new ObjectOpenHashSet<>();

    public ParticleChamber(Level level) {
        super(level);
    }

    @Override
    public ParticleChamberLogic getLogic() {
        return logic;
    }

    @Override
    public void setLogic(String logicID) {
        if (logicID.equals(logic.getID())) return;
        UnaryOperator<ParticleChamberLogic> constructor = switch (logicID) {
            case "" -> ParticleChamberLogic::new;
            case "target_chamber" -> TargetChamberLogic::new;
            case "decay_chamber" -> DecayChamberLogic::new;
            case "beam_dump" -> BeamDumpLogic::new;
            case "collision_chamber" -> CollisionChamberLogic::new;
            default -> throw new IllegalStateException("Unexpected logicID: " + logicID);
        };
        logic = getNewLogic(constructor);
    }

    @Override
    public int getMinimumInteriorLength() {
        return logic.getMinimumInteriorLength();
    }

    @Override
    public int getMaximumInteriorLength() {
        return logic.getMaximumInteriorLength();
    }

    @Override
    protected void onPartAdded(IMultiblockPart<ParticleChamber> newPart) {
        super.onPartAdded(newPart);
        logic.onBlockAdded(newPart);
    }

    @Override
    protected void onPartRemoved(IMultiblockPart<ParticleChamber> oldPart) {
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
    protected void onAssimilate(IMultiblockController<ParticleChamber> assimilated) {
        logic.onAssimilate(assimilated);
    }

    @Override
    protected void onAssimilated(IMultiblockController<ParticleChamber> assimilator) {
        super.onAssimilated(assimilator);
        logic.onAssimilated(assimilator);
    }

    @Override
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        return setLogic(this) && super.isMachineWhole(validatorCallback) && logic.isMachineWhole();
    }

    public boolean setLogic(Multiblock multiblock) {
        if (getPartMap(IParticleChamberController.class).isEmpty()) {
            multiblock.setLastError(NuclearcraftNeohaul.MODID + ".multiblock_validation.no_controller");
            return false;
        }
        if (getPartMap(IParticleChamberController.class).size() > 1) {
            multiblock.setLastError(NuclearcraftNeohaul.MODID + ".multiblock_validation.too_many_controllers");
            return false;
        }

        for (IParticleChamberController<?> contr : getPartMap(IParticleChamberController.class).values()) {
            controller = contr;
        }

        setLogic(controller.getLogicID());

        return true;
    }

    public void resetStats() {
        logic.refreshChamberStats();
    }

    @Override
    protected boolean updateServer() {
        boolean flag = refreshFlag;
        if (refreshFlag) {
            logic.refreshChamber();
        }
        updateActivity();

        if (logic.onUpdateServer()) {
            flag = true;
        }

        if (controller != null) {
            sendMultiblockUpdatePacketToListeners();
        }

        return flag;
    }

    public void updateActivity() {
        boolean wasChamberOn = isChamberOn;
        isChamberOn = isAssembled() && logic.isChamberOn();

        if (isChamberOn != wasChamberOn) {
            if (controller != null) {
                controller.setActivity(isChamberOn);
                sendMultiblockUpdatePacketToAll();
            }
        }
    }

    // Client

    @Override
    protected void updateClient() {
        logic.onUpdateClient();
    }


    // NBT


    @Override
    public CompoundTag syncDataTo(CompoundTag data, HolderLookup.Provider registries, SyncReason syncReason) {
        energyStorage.writeToNBT(data, registries, "energyStorage");
        writeTanks(tanks, data, registries, "tanks");
        writeBeams(beams, data);

        data.putBoolean("isChamberOn", isChamberOn);
        data.putInt("requiredEnergy", requiredEnergy);
        data.putDouble("efficiency", efficiency);

        writeLogicNBT(data, registries, syncReason);
        return data;
    }

    @Override
    public void syncDataFrom(CompoundTag data, HolderLookup.Provider registries, SyncReason syncReason) {
        energyStorage.readFromNBT(data, registries, "energyStorage");
        readTanks(tanks, data, registries, "tanks");
        readBeams(beams, data);

        isChamberOn = data.getBoolean("isChamberOn");
        requiredEnergy = data.getInt("requiredEnergy");
        efficiency = data.getDouble("efficiency");

        readLogicNBT(data, registries, syncReason);
    }

    // Packets

    @Override
    public Set<Player> getMultiblockUpdatePacketListeners() {
        return updatePacketListeners;
    }

    @Override
    public ParticleChamberUpdatePacket getMultiblockUpdatePacket() {
        return logic.getMultiblockUpdatePacket();
    }

    @Override
    public void onMultiblockUpdatePacket(ParticleChamberUpdatePacket message) {
        energyStorage.setStorageCapacity(message.energyStorage.getMaxEnergyStored());
        energyStorage.setEnergyStored(message.energyStorage.getEnergyStored());

        for (int i = 0; i < tanks.size(); i++) tanks.get(i).readInfo(message.tanksInfo.get(i));
        beams = message.beams;

        isChamberOn = message.isChamberOn;
        efficiency = message.efficiency;
        requiredEnergy = message.requiredEnergy;

        logic.onMultiblockUpdatePacket(message);
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
        beams.get(0).readFromNBT(data);
    }

	/*public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		return logic.getContainer(player);
	}*/

    public boolean toggleSetting(BlockPos pos, int ioNumber) {
        return logic.toggleSetting(pos, ioNumber);
    }

    // Multiblock Validators

    @Override
    protected boolean isBlockGoodForInterior(Level level, int x, int y, int z, IMultiblockValidator iMultiblockValidator) {
        return logic.isBlockGoodForInterior(level, x, y, z);
    }

    @Override
    public List<Tank> getTanks() {
        return tanks;
    }
}