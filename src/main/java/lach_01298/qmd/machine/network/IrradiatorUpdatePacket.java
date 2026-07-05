package lach_01298.qmd.machine.network;

import com.nred.nuclearcraft.block_entity.ITilePacket;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.payload.processor.EnergyProcessorUpdatePacket;
import com.nred.nuclearcraft.payload.processor.ProcessorUpdatePacket;
import lach_01298.qmd.QMD;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class IrradiatorUpdatePacket extends EnergyProcessorUpdatePacket {
    public static final Type<IrradiatorUpdatePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "irradiator_update_packet"));
    public final int fuelAmount;

    public IrradiatorUpdatePacket(BlockPos pos, boolean isProcessing, double time, double baseProcessTime, List<Tank> tanks, int fuelAmount) {
        super(pos, isProcessing, time, baseProcessTime, tanks, 0, 0);
        this.fuelAmount = fuelAmount;
    }

    public IrradiatorUpdatePacket(ProcessorUpdatePacket processorUpdatePacket, int fuelAmount) {
        super(processorUpdatePacket, 0, 0);
        this.fuelAmount = fuelAmount;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, IrradiatorUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(IrradiatorUpdatePacket::toBytes, IrradiatorUpdatePacket::fromBytes);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static IrradiatorUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        EnergyProcessorUpdatePacket processorUpdatePacket = EnergyProcessorUpdatePacket.fromBytes(buf);
        int fuelAmount = buf.readInt();
        return new IrradiatorUpdatePacket(processorUpdatePacket, fuelAmount);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(fuelAmount);
    }

    public static class Handler extends ProcessorUpdatePacket.Handler<IrradiatorUpdatePacket, ITilePacket<IrradiatorUpdatePacket>> {
    }
}
