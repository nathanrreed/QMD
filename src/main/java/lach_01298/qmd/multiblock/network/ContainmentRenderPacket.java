//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import io.netty.buffer.ByteBuf;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//
//import java.util.List;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class ContainmentRenderPacket extends QMDMultiblockUpdatePacket {
//    public static final Type<ContainmentRenderPacket> TYPE = new Type<>(ncLoc("containment_render_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, ContainmentRenderPacket> STREAM_CODEC = StreamCodec.ofMember(
//            ContainmentRenderPacket::toBytes, ContainmentRenderPacket::fromBytes
//    );
//
//    public boolean isEmpty;
//    public List<Tank.TankInfo> tanksInfo;
//
//    public ContainmentRenderPacket(BlockPos pos, boolean isEmpty, List<Tank> tanks) {
//        this.pos = pos;
//        this.isEmpty = isEmpty;
//        tanksInfo = Tank.TankInfo.getInfoList(tanks);
//
//    }
//
//    @Override
//    public void fromBytes(ByteBuf buf) {
//        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
//        isEmpty = buf.readBoolean();
//        tanksInfo = readTankInfos(buf);
//    }
//
//    @Override
//    public void toBytes(ByteBuf buf) {
//        buf.writeInt(pos.getX());
//        buf.writeInt(pos.getY());
//        buf.writeInt(pos.getZ());
//        buf.writeBoolean(isEmpty);
//        writeTankInfos(buf, tanksInfo);
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler extends
//            MultiblockUpdatePacket.Handler<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, TileExoticContainmentController, TileContainerInfo<TileExoticContainmentController>, ContainmentRenderPacket> {
//
//        public Handler() {
//            super(TileExoticContainmentController.class);
//        }
//
//        @Override
//        protected void onPacket(ContainmentRenderPacket message, VacuumChamber multiblock) {
//            multiblock.onRenderPacket(message);
//        }
//    }
//}
