package lach_01298.qmd.particleChamber;

import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.multiblock.IPacketMultiblockLogic;
import com.nred.nuclearcraft.multiblock.MultiblockLogic;
import it.zerono.mods.zerocore.lib.data.nbt.ISyncableEntity;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ParticleChamberLogic extends MultiblockLogic<ParticleChamber, ParticleChamberLogic> implements IPacketMultiblockLogic<ParticleChamber, ParticleChamberLogic, ParticleChamberUpdatePacket> {
    public static final int maxSize = 7;
    public static final int minSize = 1;

    public ParticleChamberLogic(ParticleChamber multiblock) {
        super(multiblock);
    }

    public ParticleChamberLogic(ParticleChamberLogic oldLogic) {
        super(oldLogic.multiblock);
    }

    @Override
    public String getID() {
        return "";
    }

    @Override
    public int getMinimumInteriorLength() {
        return minSize;
    }

    @Override
    public int getMaximumInteriorLength() {
        return maxSize;
    }

    @Override
    public void onMachineAssembled() {
        onChamberFormed();
    }

    public int getBeamLength() {
        return multiblock.getExteriorLengthX();
    }

    public void onChamberFormed() {
        for (IParticleChamberController<?> contr : getPartMap(IParticleChamberController.class).values()) {
            multiblock.controller = contr;
        }

        multiblock.energyStorage.setStorageCapacity(QMDServerConfig.particle_chamber_base_energy_capacity * getCapacityMultiplier());
        multiblock.energyStorage.setMaxTransfer(QMDServerConfig.particle_chamber_base_energy_capacity * getCapacityMultiplier());

        if (!getWorld().isClientSide()) {
            refreshChamber();
            multiblock.updateActivity();
        }
    }

    @Override
    public void onMachineRestored() {
        onChamberFormed();
    }

    public int getCapacityMultiplier() {
        return multiblock.getExteriorVolume();
    }

    @Override
    public void onMachinePaused() {
        onChamberBroken();
    }

    @Override
    public void onMachineDisassembled() {
        onChamberBroken();
    }

    public void onChamberBroken() {
        if (!getWorld().isClientSide()) {
            multiblock.updateActivity();
        }
    }

    @Override
    public boolean isMachineWhole() {
        multiblock.setLastError("zerocore.api.nc.multiblock.validation.invalid_logic");
        return false;
    }

    @Override
    public ParticleChamberUpdatePacket getMultiblockUpdatePacket() {
        return null;
    }

    @Override
    public void onMultiblockUpdatePacket(ParticleChamberUpdatePacket message) {
    }

    @Override
    public void onAssimilate(IMultiblockController<ParticleChamber> assimilated) {
        if (assimilated instanceof ParticleChamber assimilatedAccelerator) {
            multiblock.energyStorage.mergeEnergyStorage(assimilatedAccelerator.energyStorage);
        }

        if (multiblock.isAssembled()) {
            onChamberFormed();
        } else {
            onChamberBroken();
        }
    }

    @Override
    public void onAssimilated(IMultiblockController<ParticleChamber> iMultiblockController) {
    }

    public void refreshChamber() {
    }

    public boolean onUpdateServer() {
        return true;
    }

    public void onUpdateClient() {
        // TODO Auto-generated method stub
    }

    public void refreshChamberStats() {
        multiblock.resetStats();
    }

    public boolean isChamberOn() {

        return multiblock.beams.get(0).getParticleStack() != null;
    }

	/*public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		return null;
	}*/

    protected void pull() {
        for (TileParticleChamberBeamPort port : getPartMap(TileParticleChamberBeamPort.class).values()) {
            if (port.getIOType() == IOType.INPUT) {
                if (port.getOutwardDirection().isPresent()) {
                    Direction face = port.getOutwardDirection().get();
                    BlockEntity tile = port.getLevel().getBlockEntity(port.getBlockPos().relative(face));
                    if (tile != null) {
                        IParticleStackHandler otherStorage = getWorld().getCapability(CapabilityParticleStackHandler.BLOCK, tile.getBlockPos(), face.getOpposite());
                        if (otherStorage != null) {
                            multiblock.beams.get(port.getIONumber()).setParticleStack(otherStorage.extractParticle(face.getOpposite()));
                        }
                    }
                }
            }
        }
    }

    protected void push() {
        for (TileParticleChamberBeamPort port : getPartMap(TileParticleChamberBeamPort.class).values()) {
            if (port.getIOType() == IOType.OUTPUT) {
                if (port.getOutwardDirection().isPresent()) {
                    Direction face = port.getOutwardDirection().get();
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

    public boolean toggleSetting(BlockPos pos, int ioNumber) {
        return false;
    }

    @Override
    public List<Pair<Class<? extends IMultiblockPart<ParticleChamber>>, String>> getPartBlacklist() {
        return new ArrayList<>();
    }

    @Override
    public void writeToLogicTag(CompoundTag compoundTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
    }

    @Override
    public void readFromLogicTag(CompoundTag compoundTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
    }

    public void clearAllMaterial() {
        for (Tank tank : multiblock.tanks) {
            tank.setFluidStored(null);
        }
    }

    public @Nonnull List<Tank> getTanks(List<Tank> backupTanks) {
        return multiblock.isAssembled() ? multiblock.tanks : backupTanks;
    }
}