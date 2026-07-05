package lach_01298.qmd.machine.network;

import com.nred.nuclearcraft.block_entity.ITilePacket;
import com.nred.nuclearcraft.payload.TileUpdatePacket;
import lach_01298.qmd.QMD;
import lach_01298.qmd.network.QMDTileUpdatePacket;
import lach_01298.qmd.particle.ParticleStorageSource;
import lach_01298.qmd.util.ByteUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CreativeParticleSourceUpdatePacket extends QMDTileUpdatePacket {
    public static final Type<CreativeParticleSourceUpdatePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "creative_particle_source_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CreativeParticleSourceUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            CreativeParticleSourceUpdatePacket::toBytes, CreativeParticleSourceUpdatePacket::fromBytes
    );

    public List<ParticleStorageSource> beams;

    public CreativeParticleSourceUpdatePacket(BlockPos pos, List<ParticleStorageSource> beams) {
        super(pos);
        this.beams = beams;
    }

    public static CreativeParticleSourceUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        BlockPos pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        List<ParticleStorageSource> beams = new ArrayList<>();

        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            beams.add((ParticleStorageSource) ByteUtil.readBufBeam(buf));
        }

        return new CreativeParticleSourceUpdatePacket(pos, beams);
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

    public static class Handler extends TileUpdatePacket.Handler<CreativeParticleSourceUpdatePacket, ITilePacket<CreativeParticleSourceUpdatePacket>> {
    }
}