package lach_01298.qmd.pipe;

import com.nred.nuclearcraft.NuclearcraftNeohaul;
import com.nred.nuclearcraft.multiblock.ILogicMultiblock;
import com.nred.nuclearcraft.multiblock.Multiblock;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.multiblock.IQMDPacketMultiblock;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.UnaryOperator;

public class Pipe extends PipeMultiblock<Pipe> implements ILogicMultiblock<Pipe, PipeLogic>, IQMDPacketMultiblock<Pipe, PipeUpdatePacket> {
    protected @Nonnull PipeLogic logic = new PipeLogic(this);
    public IPipeController<?> controller;

    protected final Set<Player> updatePacketListeners;

    public Pipe(Level level) {
        super(level);
        updatePacketListeners = new ObjectOpenHashSet<>();
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
    public @Nonnull PipeLogic getLogic() {
        return logic;
    }

    @Override
    public void setLogic(String logicID) {
        if (logicID.equals(logic.getID())) return;

        UnaryOperator<PipeLogic> constructor = switch (logicID) {
            case "beamline" -> BeamlineLogic::new;
            default -> throw new IllegalStateException("Unexpected logicID: " + logicID);
        };

        logic = getNewLogic(constructor);
    }

    @Override
    protected void onPartAdded(IMultiblockPart<Pipe> newPart) {
        super.onPartAdded(newPart);
        logic.onBlockAdded(newPart);

    }

    @Override
    protected void onPartRemoved(IMultiblockPart<Pipe> oldPart) {
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
    protected void onAssimilate(IMultiblockController<Pipe> assimilated) {
        logic.onAssimilate(assimilated);
    }

    @Override
    protected void onAssimilated(IMultiblockController<Pipe> assimilator) {
        logic.onAssimilated(assimilator);
    }

    @Override
    protected boolean updateServer() {
        if (logic.onUpdateServer()) {
            return true;
        }

        sendMultiblockUpdatePacketToListeners();

        return true;
    }

    @Override
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        return setLogic(this) && super.isMachineWhole(validatorCallback) && logic.isMachineWhole();
    }

    @Override
    protected void updateClient() {
        logic.onUpdateClient();
    }

    @Override
    protected boolean isBlockGoodForInterior(Level level, int x, int y, int z, IMultiblockValidator validatorCallback) {
        return logic.isBlockGoodForInterior(level, x, y, z);
    }

    @Override
    public void syncDataFrom(CompoundTag data, HolderLookup.Provider registries, SyncReason syncReason) {
        logic.readFromLogicTag(data, registries, syncReason);
    }

    @Override
    public CompoundTag syncDataTo(CompoundTag data, HolderLookup.Provider registries, SyncReason syncReason) {
        logic.writeToLogicTag(data, registries, syncReason);
        return data;
    }

    @Override
    public Set<Player> getMultiblockUpdatePacketListeners() {
        return updatePacketListeners;
    }

    @Override
    public PipeUpdatePacket getMultiblockUpdatePacket() {
        return logic.getMultiblockUpdatePacket();
    }

    @Override
    public void onMultiblockUpdatePacket(PipeUpdatePacket message) {
        logic.onMultiblockUpdatePacket(message);
    }

    public boolean setLogic(Multiblock<?> multiblock) {
        if (getPartMap(IPipeController.class).isEmpty()) {
            multiblock.setLastError(NuclearcraftNeohaul.MODID + ".multiblock_validation.no_controller");
            return false;
        }

        for (IPipeController<?> contr : getPartMap(IPipeController.class).values()) {
            controller = contr;
        }

        setLogic(controller.getLogicID());

        return true;
    }
}