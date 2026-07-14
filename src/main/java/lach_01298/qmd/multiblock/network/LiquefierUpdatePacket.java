//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.hx.IHeatExchangerPart;
//import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank.TankInfo;
//import com.nred.nuclearcraft.multiblock.hx.HeatExchanger;
//import com.nred.nuclearcraft.payload.multiblock.HeatExchangerUpdatePacket;
//import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
//import lach_01298.qmd.util.ByteUtil;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//
//import java.util.List;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class LiquefierUpdatePacket extends HeatExchangerUpdatePacket {
//    public static final CustomPacketPayload.Type<LiquefierUpdatePacket> TYPE = new CustomPacketPayload.Type<>(ncLoc("liquefier_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, LiquefierUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            LiquefierUpdatePacket::toBytes, LiquefierUpdatePacket::fromBytes
//    );
//    public EnergyStorage energyStorage;
//    public List<TankInfo> tanksInfo;
//    public double pressureEfficiency, energyEfficiency, heatEfficiency, pressure, liquidOutFP, coolantOutFP, powerUseFP;
//
//    public LiquefierUpdatePacket(BlockPos pos, boolean isExchangerOn, int totalNetworkCount, int activeNetworkCount, int activeTubeCount, int activeContactCount, double tubeInputRateFP, double shellInputRateFP, double heatTransferRateFP, double totalTempDiff, EnergyStorage energyStorage, List<Tank> tanks, double pressureEfficiency, double energyEfficiency, double heatEfficiency, double pressure, double powerUseFP, double liquidOutFP, double coolantOutFP) {
//        super(pos, isExchangerOn, totalNetworkCount, activeNetworkCount, activeTubeCount, activeContactCount, tubeInputRateFP, shellInputRateFP, heatTransferRateFP, totalTempDiff);
//        this.energyStorage = energyStorage;
//        tanksInfo = TankInfo.getInfoList(tanks);
//        this.pressureEfficiency = pressureEfficiency;
//        this.energyEfficiency = energyEfficiency;
//        this.heatEfficiency = heatEfficiency;
//        this.pressure = pressure;
//        this.powerUseFP = powerUseFP;
//        this.liquidOutFP = liquidOutFP;
//        this.coolantOutFP = coolantOutFP;
//    }
//
//    @Override
//    public void fromBytes(RegistryFriendlyByteBuf buf) {
//        super.fromBytes(buf);
//        energyStorage = ByteUtil.readBufEnergy(buf);
//        tanksInfo = readTankInfos(buf);
//        pressureEfficiency = buf.readDouble();
//        energyEfficiency = buf.readDouble();
//        heatEfficiency = buf.readDouble();
//        pressure = buf.readDouble();
//        powerUseFP = buf.readDouble();
//        liquidOutFP = buf.readDouble();
//        coolantOutFP = buf.readDouble();
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        super.toBytes(buf);
//        ByteUtil.writeBufEnergy(energyStorage, buf);
//        writeTankInfos(buf, tanksInfo);
//        buf.writeDouble(pressureEfficiency);
//        buf.writeDouble(energyEfficiency);
//        buf.writeDouble(heatEfficiency);
//        buf.writeDouble(pressure);
//        buf.writeDouble(powerUseFP);
//        buf.writeDouble(liquidOutFP);
//        buf.writeDouble(coolantOutFP);
//    }
//
//    @Override
//    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler extends MultiblockUpdatePacket.Handler<HeatExchanger, IHeatExchangerPart, HeatExchangerUpdatePacket, TileLiquefierController, TileContainerInfo<TileLiquefierController>, LiquefierUpdatePacket> {
//        public Handler() {
//            super(TileLiquefierController.class);
//        }
//
//        @Override
//        protected void onPacket(LiquefierUpdatePacket message, HeatExchanger multiblock) {
//            multiblock.onMultiblockUpdatePacket(message);
//        }
//    }
//}