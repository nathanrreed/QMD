package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import net.minecraft.core.BlockPos;

public abstract class QMDMultiblockUpdatePacket extends MultiblockUpdatePacket {
    public QMDMultiblockUpdatePacket(BlockPos pos) {
        super(pos);
    }

    public QMDMultiblockUpdatePacket(MultiblockUpdatePacket packet) {
        super(packet);
    }
}