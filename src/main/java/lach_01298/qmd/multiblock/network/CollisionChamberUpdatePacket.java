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
//public class CollisionChamberUpdatePacket extends ParticleChamberUpdatePacket {
//    public static final Type<CollisionChamberUpdatePacket> TYPE = new Type<>(ncLoc("collision_chamber_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, CollisionChamberUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            CollisionChamberUpdatePacket::toBytes, CollisionChamberUpdatePacket::fromBytes
//    );
//
//    public CollisionChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
//                                        EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
//        super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage, tanks, beams);
//
//    }
//
//    @Override
//    public void fromBytes(ByteBuf buf) {
//        super.fromBytes(buf);
//
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
//    public static class Handler extends
//            MultiblockUpdatePacket.Handler<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileCollisionChamberController, TileContainerInfo<TileCollisionChamberController>, CollisionChamberUpdatePacket> {
//
//        public Handler() {
//            super(TileCollisionChamberController.class);
//        }
//
//        @Override
//        protected void onPacket(CollisionChamberUpdatePacket message, ParticleChamber multiblock) {
//            multiblock.onMultiblockUpdatePacket(message);
//        }
//    }
//}
