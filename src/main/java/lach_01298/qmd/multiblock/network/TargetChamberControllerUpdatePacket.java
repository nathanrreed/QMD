//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.ITilePacket;
//import com.nred.nuclearcraft.multiblock.fisson.FissionCluster;
//import com.nred.nuclearcraft.payload.TileUpdatePacket;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.NonNullList;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//import net.minecraft.world.item.ItemStack;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class TargetChamberControllerUpdatePacket extends TileUpdatePacket {
//    public static final CustomPacketPayload.Type<TargetChamberControllerUpdatePacket> TYPE = new CustomPacketPayload.Type<>(ncLoc("target_chamber_controller_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, TargetChamberControllerUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            TargetChamberControllerUpdatePacket::toBytes, TargetChamberControllerUpdatePacket::fromBytes
//    );
//    public BlockPos masterPortPos;
//    public ItemStack filterStack;
//    public long clusterHeatStored, clusterHeatCapacity;
//    public boolean isProcessing;
//    public double time, baseProcessTime;
//
//    public TargetChamberControllerUpdatePacket(BlockPos pos, BlockPos masterPortPos,
//                                               NonNullList<ItemStack> filterStacks, FissionCluster cluster, boolean isProcessing, double time,
//                                               double baseProcessTime) {
//        super(pos);
//        this.masterPortPos = masterPortPos;
//        filterStack = filterStacks.get(0);
//        clusterHeatStored = cluster == null ? -1L : cluster.heatBuffer.getHeatStored();
//        clusterHeatCapacity = cluster == null ? -1L : cluster.heatBuffer.getHeatCapacity();
//        this.isProcessing = isProcessing;
//        this.time = time;
//        this.baseProcessTime = baseProcessTime;
//
//    }
//
//    @Override
//    public void fromBytes(RegistryFriendlyByteBuf buf) {
//        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
//        masterPortPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
//        filterStack = ByteBufUtils.readItemStack(buf);
//        clusterHeatStored = buf.readLong();
//        clusterHeatCapacity = buf.readLong();
//        isProcessing = buf.readBoolean();
//        time = buf.readDouble();
//        baseProcessTime = buf.readDouble();
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        buf.writeInt(pos.getX());
//        buf.writeInt(pos.getY());
//        buf.writeInt(pos.getZ());
//        buf.writeInt(masterPortPos.getX());
//        buf.writeInt(masterPortPos.getY());
//        buf.writeInt(masterPortPos.getZ());
//        ByteBufUtils.writeItemStack(buf, filterStack);
//        buf.writeLong(clusterHeatStored);
//        buf.writeLong(clusterHeatCapacity);
//        buf.writeBoolean(isProcessing);
//        buf.writeDouble(time);
//        buf.writeDouble(baseProcessTime);
//    }
//
//    @Override
//    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler extends TileUpdatePacket.Handler<TargetChamberControllerUpdatePacket, ITilePacket<TargetChamberControllerUpdatePacket>> {
//    }
//}