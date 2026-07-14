package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileDeceleratorController;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

import static com.nred.nuclearcraft.helpers.Location.ncLoc;

public class DeceleratorUpdatePacket extends AcceleratorUpdatePacket {
    public static final Type<DeceleratorUpdatePacket> TYPE = new Type<>(ncLoc("decelerator_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DeceleratorUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            DeceleratorUpdatePacket::toBytes, DeceleratorUpdatePacket::fromBytes
    );

    public DeceleratorUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating, long currentHeating,
                                   int maxCoolantIn, int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, double efficiency,
                                   int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength,
                                   int dipoleNumber, double dipoleStrength, int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage,
                                   List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
        super(pos, isAcceleratorOn, cooling, rawHeating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy,
                efficiency, acceleratingVoltage, RFCavityNumber, quadrupoleNumber, quadrupoleStrength, dipoleNumber,
                dipoleStrength, errorCode, heatBuffer, energyStorage, tanks, beams);
    }

    public DeceleratorUpdatePacket(AcceleratorUpdatePacket packet) {
        super(packet);
    }

    public static DeceleratorUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        return new DeceleratorUpdatePacket(AcceleratorUpdatePacket.fromBytes(buf));
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler extends MultiblockUpdatePacket.Handler<Accelerator, AcceleratorUpdatePacket, TileDeceleratorController, BlockEntityMenuInfo<TileDeceleratorController>, DeceleratorUpdatePacket> {

        public static void handleOnClient(DeceleratorUpdatePacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                BlockEntity tile = context.player().level().getBlockEntity(payload.pos);
                if (tile instanceof TileDeceleratorController entity) {
                    Optional<Accelerator> multiblock = entity.getMultiblockController();
                    multiblock.ifPresent((accelerator) -> onPacket(payload, accelerator));
                }
            });
        }

        protected static void onPacket(DeceleratorUpdatePacket message, Accelerator multiblock) {
            multiblock.onMultiblockUpdatePacket(message);
        }
    }
}