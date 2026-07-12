package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileLinearAcceleratorController;
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

public class LinearAcceleratorUpdatePacket extends AcceleratorUpdatePacket {
    public static final Type<LinearAcceleratorUpdatePacket> TYPE = new Type<>(ncLoc("linear_accelerator_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LinearAcceleratorUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            LinearAcceleratorUpdatePacket::toBytes, LinearAcceleratorUpdatePacket::fromBytes
    );

    public LinearAcceleratorUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating, long currentHeating, int maxCoolantIn, int maxCoolantOut,
                                         int maxOperatingTemp, int requiredEnergy, double efficiency, int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber,
                                         double quadrupoleStrength, int dipoleNumber, double dipoleStrength, int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
        super(pos, isAcceleratorOn, cooling, rawHeating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy, efficiency, acceleratingVoltage,
                RFCavityNumber, quadrupoleNumber, quadrupoleStrength, dipoleNumber, dipoleStrength, errorCode, heatBuffer, energyStorage, tanks, beams);
    }

    public LinearAcceleratorUpdatePacket(AcceleratorUpdatePacket updatePacket) {
        super(updatePacket);
    }

    public static LinearAcceleratorUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        AcceleratorUpdatePacket updatePacket = AcceleratorUpdatePacket.fromBytes(buf);
        return new LinearAcceleratorUpdatePacket(updatePacket);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler extends MultiblockUpdatePacket.Handler<Accelerator, AcceleratorUpdatePacket, TileLinearAcceleratorController, BlockEntityMenuInfo<TileLinearAcceleratorController>, LinearAcceleratorUpdatePacket> {
        public static void handleOnClient(LinearAcceleratorUpdatePacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                BlockEntity tile = context.player().level().getBlockEntity(payload.pos);
                if (tile instanceof TileLinearAcceleratorController entity) {
                    Optional<Accelerator> multiblock = entity.getMultiblockController();
                    multiblock.ifPresent((accelerator) -> onPacket(payload, accelerator));
                }
            });
        }

        protected static void onPacket(LinearAcceleratorUpdatePacket message, Accelerator multiblock) {
            multiblock.onMultiblockUpdatePacket(message);
        }
    }
}