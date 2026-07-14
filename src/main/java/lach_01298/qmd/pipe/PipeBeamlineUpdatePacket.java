//package lach_01298.qmd.pipe;
//
//import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
//import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
//import lach_01298.qmd.particle.ParticleStorageAccelerator;
//import lach_01298.qmd.util.ByteUtil;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.neoforged.neoforge.network.handling.IPayloadContext;
//
//import java.util.Optional;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class PipeBeamlineUpdatePacket extends PipeUpdatePacket {
//    public static final Type<PipeBeamlineUpdatePacket> TYPE = new Type<>(ncLoc("pipe_beamline_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, PipeBeamlineUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            PipeBeamlineUpdatePacket::toBytes, PipeBeamlineUpdatePacket::fromBytes
//    );
//
//    public ParticleStorageAccelerator storage;
//
//    public PipeBeamlineUpdatePacket(BlockPos pos, ParticleStorageAccelerator storage) {
//        super(pos);
//        this.storage = storage;
//    }
//
//    public PipeBeamlineUpdatePacket(PipeUpdatePacket packet, ParticleStorageAccelerator storage) {
//        super(packet);
//        this.storage = storage;
//    }
//
//    public static PipeBeamlineUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
//        PipeUpdatePacket packet = PipeUpdatePacket.fromBytes(buf);
//        ParticleStorageAccelerator storage = (ParticleStorageAccelerator) ByteUtil.readBufParticleStorage(buf);
//        return new PipeBeamlineUpdatePacket(packet, storage);
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        super.toBytes(buf);
//        ByteUtil.writeBufParticleStorage(storage, buf);
//    }
//
//    public static class Handler extends MultiblockUpdatePacket.Handler<Pipe, PipeUpdatePacket, TileBeamline, BlockEntityMenuInfo<TileBeamline>, PipeBeamlineUpdatePacket> {
//        public static void handleOnClient(PipeBeamlineUpdatePacket payload, IPayloadContext context) {
//            context.enqueueWork(() -> {
//                BlockEntity tile = context.player().level().getBlockEntity(payload.pos);
//                if (tile instanceof TileBeamline entity) {
//                    Optional<Pipe> multiblock = entity.getMultiblockController();
//                    multiblock.ifPresent(machine -> onPacket(payload, machine));
//                }
//            });
//        }
//
//        protected static void onPacket(PipeBeamlineUpdatePacket message, Pipe multiblock) {
//            multiblock.onMultiblockUpdatePacket(message);
//        }
//    }
//}