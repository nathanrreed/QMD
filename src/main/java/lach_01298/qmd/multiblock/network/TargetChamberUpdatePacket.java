package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileTargetChamberController;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

import static com.nred.nuclearcraft.helpers.Location.ncLoc;

public class TargetChamberUpdatePacket extends ParticleChamberUpdatePacket {
    public static final Type<TargetChamberUpdatePacket> TYPE = new Type<>(ncLoc("target_chamber_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, TargetChamberUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            TargetChamberUpdatePacket::toBytes, TargetChamberUpdatePacket::fromBytes
    );
    public long particleCount, recipeParticleCount;

    public TargetChamberUpdatePacket(BlockPos pos, boolean isChamberOn, int requiredEnergy, double efficiency,
                                     EnergyStorage energyStorage, long particleCount, long particleRecipeCount, List<Tank> tanks,
                                     List<ParticleStorageAccelerator> beams) {
        super(pos, isChamberOn, requiredEnergy, efficiency, energyStorage, tanks, beams);
        this.particleCount = particleCount;
        this.recipeParticleCount = particleRecipeCount;
    }

    public TargetChamberUpdatePacket(ParticleChamberUpdatePacket packet, long particleCount, long recipeParticleCount) {
        super(packet);
        this.particleCount = particleCount;
        this.recipeParticleCount = recipeParticleCount;
    }

    public static TargetChamberUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        ParticleChamberUpdatePacket packet = ParticleChamberUpdatePacket.fromBytes(buf);

        long particleCount = buf.readLong();
        long recipeParticleCount = buf.readLong();

        return new TargetChamberUpdatePacket(packet, particleCount, recipeParticleCount);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);

        buf.writeLong(particleCount);
        buf.writeLong(recipeParticleCount);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler extends MultiblockUpdatePacket.Handler<ParticleChamber, ParticleChamberUpdatePacket, TileTargetChamberController, BlockEntityMenuInfo<TileTargetChamberController>, TargetChamberUpdatePacket> {
        public static void handleOnClient(TargetChamberUpdatePacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                BlockEntity tile = context.player().level().getBlockEntity(payload.pos);
                if (tile instanceof TileTargetChamberController entity) {
                    Optional<ParticleChamber> multiblock = entity.getMultiblockController();
                    multiblock.ifPresent((accelerator) -> onPacket(payload, accelerator));
                }
            });
        }

        protected static void onPacket(TargetChamberUpdatePacket message, ParticleChamber multiblock) {
            multiblock.onMultiblockUpdatePacket(message);
        }
    }
}
