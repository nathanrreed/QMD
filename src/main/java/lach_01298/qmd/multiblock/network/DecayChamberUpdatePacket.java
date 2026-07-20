package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileDecayChamberController;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

import static com.nred.nuclearcraft.helpers.Location.ncLoc;

public class DecayChamberUpdatePacket extends ParticleChamberUpdatePacket {
    public static final Type<DecayChamberUpdatePacket> TYPE = new Type<>(ncLoc("decay_chamber_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DecayChamberUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            DecayChamberUpdatePacket::toBytes, DecayChamberUpdatePacket::fromBytes
    );

    public DecayChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
        super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage, tanks, beams);
    }

    public DecayChamberUpdatePacket(ParticleChamberUpdatePacket packet) {
        super(packet);
    }


    public static DecayChamberUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        return new DecayChamberUpdatePacket(ParticleChamberUpdatePacket.fromBytes(buf));
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler extends MultiblockUpdatePacket.Handler<ParticleChamber, ParticleChamberUpdatePacket, TileDecayChamberController, BlockEntityMenuInfo<TileDecayChamberController>, DecayChamberUpdatePacket> {
        public static void handleOnClient(DecayChamberUpdatePacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                BlockEntity tile = context.player().level().getBlockEntity(payload.pos);
                if (tile instanceof TileDecayChamberController entity) {
                    Optional<ParticleChamber> multiblock = entity.getMultiblockController();
                    multiblock.ifPresent((accelerator) -> onPacket(payload, accelerator));
                }
            });
        }

        protected static void onPacket(DecayChamberUpdatePacket message, ParticleChamber multiblock) {
            multiblock.onMultiblockUpdatePacket(message);
        }
    }
}