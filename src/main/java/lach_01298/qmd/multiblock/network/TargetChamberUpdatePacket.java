//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
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
//public class TargetChamberUpdatePacket extends ParticleChamberUpdatePacket {
//    public static final Type<TargetChamberUpdatePacket> TYPE = new Type<>(ncLoc("target_chamber_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, TargetChamberUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            TargetChamberUpdatePacket::toBytes, TargetChamberUpdatePacket::fromBytes
//    );
//    public long particleCount, recipeParticleCount;
//
//    public TargetChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
//                                     EnergyStorage energyStorage, long particleCount, long particleRecipeCount, List<Tank> tanks,
//                                     List<ParticleStorageAccelerator> beams) {
//        super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage, tanks, beams);
//        this.particleCount = particleCount;
//        this.recipeParticleCount = particleRecipeCount;
//    }
//
//    @Override
//    public void fromBytes(RegistryFriendlyByteBuf buf) {
//        super.fromBytes(buf);
//
//        particleCount = buf.readLong();
//        recipeParticleCount = buf.readLong();
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        super.toBytes(buf);
//
//        buf.writeLong(particleCount);
//        buf.writeLong(recipeParticleCount);
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler extends MultiblockUpdatePacket.Handler<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileTargetChamberController, TileContainerInfo<TileTargetChamberController>, TargetChamberUpdatePacket> {
//        public Handler() {
//            super(TileTargetChamberController.class);
//        }
//
//        @Override
//        protected void onPacket(TargetChamberUpdatePacket message, ParticleChamber multiblock) {
//            multiblock.onMultiblockUpdatePacket(message);
//        }
//    }
//}
