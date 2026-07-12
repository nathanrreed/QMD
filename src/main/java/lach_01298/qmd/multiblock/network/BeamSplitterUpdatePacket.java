package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileBeamSplitterController;
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

public class BeamSplitterUpdatePacket extends AcceleratorUpdatePacket {
    public static final Type<BeamSplitterUpdatePacket> TYPE = new Type<>(ncLoc("beam_splitter_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BeamSplitterUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            BeamSplitterUpdatePacket::toBytes, BeamSplitterUpdatePacket::fromBytes
    );

    public BeamSplitterUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating, long currentHeating,
                                    int maxCoolantIn, int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, double efficiency,
                                    int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength,
                                    int dipoleNumber, double dipoleStrength, int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage,
                                    List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
        super(pos, isAcceleratorOn, cooling, rawHeating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy,
                efficiency, acceleratingVoltage, RFCavityNumber, quadrupoleNumber, quadrupoleStrength, dipoleNumber,
                dipoleStrength, errorCode, heatBuffer, energyStorage, tanks, beams);

    }

    public BeamSplitterUpdatePacket(AcceleratorUpdatePacket packet) {
        super(packet);
    }

    public static BeamSplitterUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        return new BeamSplitterUpdatePacket(AcceleratorUpdatePacket.fromBytes(buf));
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler extends MultiblockUpdatePacket.Handler<Accelerator, AcceleratorUpdatePacket, TileBeamSplitterController, BlockEntityMenuInfo<TileBeamSplitterController>, BeamSplitterUpdatePacket> {
        public static void handleOnClient(BeamSplitterUpdatePacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                BlockEntity tile = context.player().level().getBlockEntity(payload.pos);
                if (tile instanceof TileBeamSplitterController entity) {
                    Optional<Accelerator> multiblock = entity.getMultiblockController();
                    multiblock.ifPresent((accelerator) -> onPacket(payload, accelerator));
                }

            });
        }

        protected static void onPacket(BeamSplitterUpdatePacket message, Accelerator multiblock) {
            multiblock.onMultiblockUpdatePacket(message);
        }
    }
}
