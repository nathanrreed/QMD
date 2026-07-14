//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import io.netty.buffer.ByteBuf;
//import lach_01298.qmd.particle.ParticleStorageAccelerator;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//
//import java.util.List;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class DecayChamberUpdatePacket extends ParticleChamberUpdatePacket {
//    public static final Type<DecayChamberUpdatePacket> TYPE = new Type<>(ncLoc("decay_chamber_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, DecayChamberUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            DecayChamberUpdatePacket::toBytes, DecayChamberUpdatePacket::fromBytes
//    );
//
//    public DecayChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
//        super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage, tanks, beams);
//    }
//
//    @Override
//    public void fromBytes(ByteBuf buf) {
//        super.fromBytes(buf);
//    }
//
//    @Override
//    public void toBytes(ByteBuf buf) {
//        super.toBytes(buf);
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler extends MultiblockUpdatePacket.Handler<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileDecayChamberController, TileContainerInfo<TileDecayChamberController>, DecayChamberUpdatePacket> {
//        public Handler() {
//            super(TileDecayChamberController.class);
//        }
//
//        @Override
//        protected void onPacket(DecayChamberUpdatePacket message, ParticleChamber multiblock) {
//            multiblock.onMultiblockUpdatePacket(message);
//        }
//    }
//}