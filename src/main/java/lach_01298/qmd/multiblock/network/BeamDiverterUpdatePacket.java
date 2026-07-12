package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileBeamDiverterController;
import lach_01298.qmd.accelerator.tile.TileRingAcceleratorController;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

import static com.nred.nuclearcraft.helpers.Location.ncLoc;

public class BeamDiverterUpdatePacket extends AcceleratorUpdatePacket {
    public static final Type<BeamDiverterUpdatePacket> TYPE = new Type<>(ncLoc("beam_diverter_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BeamDiverterUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            BeamDiverterUpdatePacket::toBytes, BeamDiverterUpdatePacket::fromBytes
    );

    public BeamDiverterUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating, long currentHeating,
                                    int maxCoolantIn, int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, double efficiency,
                                    int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength,
                                    int dipoleNumber, double dipoleStrength, int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage,
                                    List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
        super(pos, isAcceleratorOn, cooling, rawHeating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy,
                efficiency, acceleratingVoltage, RFCavityNumber, quadrupoleNumber, quadrupoleStrength, dipoleNumber,
                dipoleStrength, errorCode, heatBuffer, energyStorage, tanks, beams);
    }

    public BeamDiverterUpdatePacket(AcceleratorUpdatePacket packet) {
        super(packet);
    }

    public static BeamDiverterUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        return new BeamDiverterUpdatePacket(AcceleratorUpdatePacket.fromBytes(buf));
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
    }

    public static class Handler extends MultiblockUpdatePacket.Handler<Accelerator, AcceleratorUpdatePacket, TileBeamDiverterController, BlockEntityMenuInfo<TileBeamDiverterController>, BeamDiverterUpdatePacket> {
        public static void handleOnClient(BeamDiverterUpdatePacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                BlockEntity tile = context.player().level().getBlockEntity(payload.pos);
                if (tile instanceof TileRingAcceleratorController entity) {
                    Optional<Accelerator> multiblock = entity.getMultiblockController();
                    multiblock.ifPresent((accelerator) -> onPacket(payload, accelerator));
                }

            });
        }

        protected static void onPacket(BeamDiverterUpdatePacket message, Accelerator multiblock) {
            multiblock.onMultiblockUpdatePacket(message);
        }
    }
}
