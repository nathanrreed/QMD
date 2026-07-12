package lach_01298.qmd.pipe;

import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class PipeUpdatePacket extends MultiblockUpdatePacket {
    public PipeUpdatePacket(BlockPos pos) {
        super(pos);
    }

    public PipeUpdatePacket(MultiblockUpdatePacket packet) {
        super(packet);
    }

    public static PipeUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        return new PipeUpdatePacket(MultiblockUpdatePacket.fromBytes(buf));
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
    }
}