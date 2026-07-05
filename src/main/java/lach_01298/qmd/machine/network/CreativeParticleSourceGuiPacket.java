package lach_01298.qmd.machine.network;

import lach_01298.qmd.QMD;
import lach_01298.qmd.network.QMDPacket;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageSource;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import lach_01298.qmd.util.ByteUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class CreativeParticleSourceGuiPacket extends QMDPacket {
    public static final Type<CreativeParticleSourceGuiPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "creative_particle_source_gui_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CreativeParticleSourceGuiPacket> STREAM_CODEC = StreamCodec.ofMember(
            CreativeParticleSourceGuiPacket::toBytes, CreativeParticleSourceGuiPacket::fromBytes
    );

    BlockPos pos;
    public List<ParticleStorageSource> beams;

    public CreativeParticleSourceGuiPacket(TileCreativeParticleSource tile) {
        pos = tile.getTilePos();
        beams = (List<ParticleStorageSource>) tile.getParticleBeams();
    }

    public CreativeParticleSourceGuiPacket(BlockPos pos, List<ParticleStorageSource> beams) {
        this.pos = pos;
        this.beams = beams;
    }

    public static CreativeParticleSourceGuiPacket fromBytes(RegistryFriendlyByteBuf buf) {
        BlockPos pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        List<ParticleStorageSource> beams = new ArrayList<>();

        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ParticleStorage storage = ByteUtil.readBufBeam(buf);
            ParticleStorageSource beam = new ParticleStorageSource();
            beam.setParticleStack(storage.getParticleStack());
            beams.add(beam);
        }

        return new CreativeParticleSourceGuiPacket(pos, beams);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());

        buf.writeInt(beams.size());
        for (ParticleStorageSource beam : beams) {
            ByteUtil.writeBufBeam(beam, buf);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public static void handleOnServer(CreativeParticleSourceGuiPacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                ServerPlayer player = (ServerPlayer) context.player();
                Level level = player.level();
                if (!level.isLoaded(payload.pos) || !level.mayInteract(player, payload.pos)) {
                    return;
                }
                onPacket(payload, level.getBlockEntity(payload.pos));
            });
        }

        protected static void onPacket(CreativeParticleSourceGuiPacket message, BlockEntity tile) {
            if (tile instanceof TileCreativeParticleSource source) {
                source.setParticleBeams(message.beams);
                source.markDirtyAndNotify(true);
            }
        }
    }
}