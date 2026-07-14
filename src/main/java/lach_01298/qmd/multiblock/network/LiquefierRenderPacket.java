//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.hx.IHeatExchangerPart;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank.TankInfo;
//import com.nred.nuclearcraft.multiblock.hx.HeatExchanger;
//import com.nred.nuclearcraft.payload.multiblock.HeatExchangerRenderPacket;
//import com.nred.nuclearcraft.payload.multiblock.HeatExchangerUpdatePacket;
//import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//
//import java.util.List;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class LiquefierRenderPacket extends HeatExchangerRenderPacket {
//    public static final CustomPacketPayload.Type<DecayChamberUpdatePacket> TYPE = new CustomPacketPayload.Type<>(ncLoc("liquefier_render_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, DecayChamberUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            DecayChamberUpdatePacket::toBytes, DecayChamberUpdatePacket::fromBytes
//    );
//    public List<TankInfo> tankInfos;
//
//    public LiquefierRenderPacket() {
//        super();
//    }
//
//    public LiquefierRenderPacket(BlockPos pos, List<Tank> shellTanks, List<Tank> tanks) {
//        super(pos, shellTanks);
//        tankInfos = TankInfo.getInfoList(tanks);
//    }
//
//    @Override
//    public void fromBytes(RegistryFriendlyByteBuf buf) {
//        HeatExchangerRenderPacket.fromBytes(buf);
//        tankInfos = readTankInfos(buf);
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        super.toBytes(buf);
//        writeTankInfos(buf, tankInfos);
//    }
//
//    @Override
//    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler extends MultiblockUpdatePacket.Handler<HeatExchanger, IHeatExchangerPart, HeatExchangerUpdatePacket, TileLiquefierController, TileContainerInfo<TileLiquefierController>, LiquefierRenderPacket> {
//
//        public Handler() {
//            super(TileLiquefierController.class);
//        }
//
//        @Override
//        protected void onPacket(LiquefierRenderPacket message, HeatExchanger multiblock) {
//            multiblock.onRenderPacket(message);
//        }
//    }
//}