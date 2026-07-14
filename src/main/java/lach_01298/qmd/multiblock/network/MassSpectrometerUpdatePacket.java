package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileMassSpectrometerController;
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

public class MassSpectrometerUpdatePacket extends AcceleratorUpdatePacket {
    public static final Type<MassSpectrometerUpdatePacket> TYPE = new Type<>(ncLoc("mass_spectrometer_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MassSpectrometerUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            MassSpectrometerUpdatePacket::toBytes, MassSpectrometerUpdatePacket::fromBytes
    );
    public double workDone, recipeWork, speed;

    public MassSpectrometerUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating, long currentHeating, int maxCoolantIn, int maxCoolantOut,
                                        int maxOperatingTemp, int requiredEnergy, double efficiency, int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber,
                                        double quadrupoleStrength, int dipoleNumber, double dipoleStrength, int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams,
                                        double workDone, double recipeWork, double speed) {
        super(pos, isAcceleratorOn, cooling, rawHeating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy, efficiency, acceleratingVoltage,
                RFCavityNumber, quadrupoleNumber, quadrupoleStrength, dipoleNumber, dipoleStrength, errorCode, heatBuffer, energyStorage, tanks, beams);
        this.workDone = workDone;
        this.recipeWork = recipeWork;
        this.speed = speed;
    }

    public MassSpectrometerUpdatePacket(AcceleratorUpdatePacket packet, double workDone, double recipeWork, double speed) {
        super(packet);
        this.workDone = workDone;
        this.recipeWork = recipeWork;
        this.speed = speed;
    }

    public static MassSpectrometerUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        AcceleratorUpdatePacket packet = AcceleratorUpdatePacket.fromBytes(buf);
        double workDone = buf.readDouble();
        double recipeWork = buf.readDouble();
        double speed = buf.readDouble();
        return new MassSpectrometerUpdatePacket(packet, workDone, recipeWork, speed);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
        buf.writeDouble(workDone);
        buf.writeDouble(recipeWork);
        buf.writeDouble(speed);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler extends MultiblockUpdatePacket.Handler<Accelerator, AcceleratorUpdatePacket, TileMassSpectrometerController, BlockEntityMenuInfo<TileMassSpectrometerController>, MassSpectrometerUpdatePacket> {
        public static void handleOnClient(MassSpectrometerUpdatePacket payload, IPayloadContext context) {
            context.enqueueWork(() -> {
                BlockEntity tile = context.player().level().getBlockEntity(payload.pos);
                if (tile instanceof TileMassSpectrometerController entity) {
                    Optional<Accelerator> multiblock = entity.getMultiblockController();
                    multiblock.ifPresent((accelerator) -> onPacket(payload, accelerator));
                }

            });
        }

        protected static void onPacket(MassSpectrometerUpdatePacket message, Accelerator multiblock) {
            multiblock.onMultiblockUpdatePacket(message);
        }
    }
}