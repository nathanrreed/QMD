package lach_01298.qmd.network;

import com.nred.nuclearcraft.block_entity.ITilePacket;
import com.nred.nuclearcraft.payload.TileUpdatePacket;
import net.minecraft.core.BlockPos;

public abstract class QMDTileUpdatePacket extends TileUpdatePacket {
    public QMDTileUpdatePacket(BlockPos pos) {
        super(pos);
    }

    public QMDTileUpdatePacket(TileUpdatePacket packet) {
        super(packet);
    }

    public static class Handler extends TileUpdatePacket.Handler<QMDTileUpdatePacket, ITilePacket<QMDTileUpdatePacket>> {
    }
}
