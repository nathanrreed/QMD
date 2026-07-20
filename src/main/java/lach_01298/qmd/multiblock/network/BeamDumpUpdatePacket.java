package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

import static com.nred.nuclearcraft.helpers.Location.ncLoc;

public class BeamDumpUpdatePacket extends ParticleChamberUpdatePacket {
    public static final Type<BeamDumpUpdatePacket> TYPE = new Type<>(ncLoc("beam_dump_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BeamDumpUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            BeamDumpUpdatePacket::toBytes, BeamDumpUpdatePacket::fromBytes
    );
    public long particleWorkDone, recipeParticleWork;

    public BeamDumpUpdatePacket(BlockPos pos, boolean isChamberOn, int requiredEnergy, double efficiency,
                                EnergyStorage energyStorage, long particleCount, long particleRecipeCount, List<Tank> tanks,
                                List<ParticleStorageAccelerator> beams) {
        super(pos, isChamberOn, requiredEnergy, efficiency, energyStorage, tanks, beams);

        this.particleWorkDone = particleCount;
        this.recipeParticleWork = particleRecipeCount;
    }

    public BeamDumpUpdatePacket(ParticleChamberUpdatePacket packet, long particleCount, long particleRecipeCount) {
        super(packet);
        this.particleWorkDone = particleCount;
        this.recipeParticleWork = particleRecipeCount;
    }

    public static BeamDumpUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        ParticleChamberUpdatePacket packet = ParticleChamberUpdatePacket.fromBytes(buf);

        long particleWorkDone = buf.readLong();
        long recipeParticleWork = buf.readLong();

        return new BeamDumpUpdatePacket(packet, particleWorkDone, recipeParticleWork);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);

        buf.writeLong(particleWorkDone);
        buf.writeLong(recipeParticleWork);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler extends MultiblockUpdatePacket.Handler<ParticleChamber, ParticleChamberUpdatePacket, TileBeamDumpController, BlockEntityMenuInfo<TileBeamDumpController>, BeamDumpUpdatePacket> {
        public static void handleOnClient(BeamDumpUpdatePacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                BlockEntity tile = context.player().level().getBlockEntity(payload.pos);
                if (tile instanceof TileBeamDumpController entity) {
                    Optional<ParticleChamber> multiblock = entity.getMultiblockController();
                    multiblock.ifPresent((accelerator) -> onPacket(payload, accelerator));
                }
            });
        }

        protected static void onPacket(BeamDumpUpdatePacket message, ParticleChamber multiblock) {
            multiblock.onMultiblockUpdatePacket(message);
        }
    }
}
