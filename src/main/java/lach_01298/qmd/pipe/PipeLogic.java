package lach_01298.qmd.pipe;

import com.nred.nuclearcraft.multiblock.IPacketMultiblockLogic;
import com.nred.nuclearcraft.multiblock.MultiblockLogic;
import it.zerono.mods.zerocore.lib.data.nbt.ISyncableEntity;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class PipeLogic extends MultiblockLogic<Pipe, PipeLogic> implements IPacketMultiblockLogic<Pipe, PipeLogic, PipeUpdatePacket> {
    public PipeLogic(PipeLogic oldLogic) {
        super(oldLogic.multiblock);
    }

    public PipeLogic(Pipe multiblock) {
        super(multiblock);
    }

    protected Pipe getPipe() {
        return multiblock;
    }

    @Override
    public String getID() {
        return "";
    }

    @Override
    public int getMinimumInteriorLength() {
        return 0;
    }

    @Override
    public int getMaximumInteriorLength() {
        return 0;
    }

    @Override
    public void onMachineAssembled() {
        onPipeFormed();
    }

    public void onPipeFormed() {
    }

    @Override
    public void onMachineRestored() {
        onPipeFormed();
    }

    @Override
    public void onMachinePaused() {
    }

    @Override
    public void onMachineDisassembled() {
    }

    @Override
    public boolean isMachineWhole() {
        return false;
    }

    @Override
    public void writeToLogicTag(CompoundTag compoundTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
    }

    @Override
    public void readFromLogicTag(CompoundTag compoundTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
    }

    @Override
    public PipeUpdatePacket getMultiblockUpdatePacket() {
        return null;
    }

    @Override
    public void onMultiblockUpdatePacket(PipeUpdatePacket message) {
    }

    public boolean onUpdateServer() {
        return false;
    }

    @Override
    public void onAssimilate(IMultiblockController<Pipe> iMultiblockController) {
    }

    @Override
    public void onAssimilated(IMultiblockController<Pipe> iMultiblockController) {
    }

    @Override
    public void onUpdateClient() {
    }

    @Override
    public List<Pair<Class<? extends IMultiblockPart<Pipe>>, String>> getPartBlacklist() {
        return new ArrayList<>();
    }

    @Override
    public void clearAllMaterial() {

    }
}