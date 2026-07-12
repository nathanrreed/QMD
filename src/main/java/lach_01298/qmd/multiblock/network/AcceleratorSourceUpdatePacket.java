package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.ITilePacket;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank.TankInfo;
import com.nred.nuclearcraft.payload.TileUpdatePacket;
import lach_01298.qmd.network.QMDTileUpdatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.List;

import static com.nred.nuclearcraft.helpers.Location.ncLoc;

public class AcceleratorSourceUpdatePacket extends QMDTileUpdatePacket {
    public static final Type<AcceleratorSourceUpdatePacket> TYPE = new Type<>(ncLoc("accelerator_source_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AcceleratorSourceUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            AcceleratorSourceUpdatePacket::toBytes, AcceleratorSourceUpdatePacket::fromBytes
    );

    public List<TankInfo> tanksInfo;

    public AcceleratorSourceUpdatePacket(BlockPos pos, List<Tank> tanks) {
        super(pos);
        tanksInfo = TankInfo.getInfoList(tanks);
    }

    public AcceleratorSourceUpdatePacket(TileUpdatePacket tileUpdatePacket, List<TankInfo> tanks) {
        super(tileUpdatePacket);
        tanksInfo = tanks;
    }

    public static AcceleratorSourceUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        TileUpdatePacket packet = TileUpdatePacket.fromBytes(buf);
        List<TankInfo> tanksInfo = readTankInfos(buf);
        return new AcceleratorSourceUpdatePacket(packet, tanksInfo);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
        writeTankInfos(buf, tanksInfo);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler extends TileUpdatePacket.Handler<AcceleratorSourceUpdatePacket, ITilePacket<AcceleratorSourceUpdatePacket>> {
    }
}