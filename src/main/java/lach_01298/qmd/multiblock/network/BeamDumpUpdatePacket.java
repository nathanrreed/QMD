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
//public class BeamDumpUpdatePacket extends ParticleChamberUpdatePacket {
//    public static final Type<BeamDumpUpdatePacket> TYPE = new Type<>(ncLoc("beam_dump_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, BeamDumpUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            BeamDumpUpdatePacket::toBytes, BeamDumpUpdatePacket::fromBytes
//    );
//    public long particleWorkDone, recipeParticleWork;
//
//    public BeamDumpUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
//                                EnergyStorage energyStorage, long particleCount, long particleRecipeCount, List<Tank> tanks,
//                                List<ParticleStorageAccelerator> beams) {
//        super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage, tanks, beams);
//
//        this.particleWorkDone = particleCount;
//        this.recipeParticleWork = particleRecipeCount;
//    }
//
//    public static BeamDumpUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
//        ParticleChamberUpdatePacket.fromBytes(buf);
//
//        particleWorkDone = buf.readLong();
//        recipeParticleWork = buf.readLong();
//
//        return new BeamDumpUpdatePacket();
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        super.toBytes(buf);
//
//        buf.writeLong(particleWorkDone);
//        buf.writeLong(recipeParticleWork);
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler extends MultiblockUpdatePacket.Handler<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileBeamDumpController, TileContainerInfo<TileBeamDumpController>, BeamDumpUpdatePacket> {
//        public Handler() {
//            super(TileBeamDumpController.class);
//        }
//
//        @Override
//        protected void onPacket(BeamDumpUpdatePacket message, ParticleChamber multiblock) {
//            multiblock.onMultiblockUpdatePacket(message);
//        }
//    }
//}
