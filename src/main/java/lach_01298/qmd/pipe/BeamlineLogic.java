package lach_01298.qmd.pipe;

import it.zerono.mods.zerocore.lib.data.nbt.ISyncableEntity;
import lach_01298.qmd.particle.ParticleStorageBeamline;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import static com.nred.nuclearcraft.registration.BlockRegistration.AXIS_ALL;

public class BeamlineLogic extends PipeLogic {
    private int maxBeamStorageTime = 2;
    private int beamStorageTime = 0;

    public final ParticleStorageBeamline storage = new ParticleStorageBeamline(1);
    private Axis axis;

    public BeamlineLogic(PipeLogic oldLogic) {
        super(oldLogic);

        this.axis = getWorld().getBlockState(multiblock.controller.getTilePos()).getValue(AXIS_ALL);
    }

    @Override
    public boolean isMachineWhole() {
        return true;
    }


    @Override
    public void onPipeFormed() {
        storage.setLength(getPipe().length());
    }

    @Override
    public String getID() {
        return "beamline";
    }

    public boolean onUpdateServer() {
        //storage.setParticleStack(null);
        return false;
    }

    @Override
    public void writeToLogicTag(CompoundTag logicTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        storage.writeToNBT(logicTag);
    }

    @Override
    public void readFromLogicTag(CompoundTag logicTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        storage.readFromNBT(logicTag);
    }

    public Axis getAxis() {
        return axis;
    }

    public boolean setAxis(Axis newAxis) {
        if (newAxis != null) {
            axis = newAxis;
            return true;
        }
        return false;
    }
}