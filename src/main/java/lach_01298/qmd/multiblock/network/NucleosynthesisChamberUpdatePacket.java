//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
//import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
//import lach_01298.qmd.particle.ParticleStorageAccelerator;
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
//public class NucleosynthesisChamberUpdatePacket extends VacuumChamberUpdatePacket {
//    public static final Type<NucleosynthesisChamberUpdatePacket> TYPE = new Type<>(ncLoc("nucleosynthesis_chamber_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, NucleosynthesisChamberUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            NucleosynthesisChamberUpdatePacket::toBytes, NucleosynthesisChamberUpdatePacket::fromBytes
//    );
//    public long particleWorkDone, recipeParticleWork;
//    public double casingHeating;
//    public long casingCooling;
//    public int maxCasingCoolantIn, maxCasingCoolantOut;
//    public HeatBuffer casingHeatBuffer;
//
//    public NucleosynthesisChamberUpdatePacket() {
//        super();
//    }
//
//    public NucleosynthesisChamberUpdatePacket(BlockPos pos, boolean isContainmentOn, long heating, long currentHeating, int maxCoolantIn,
//                                              int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, HeatBuffer heatBuffer,
//                                              EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams,
//                                              long particleWorkDone, long recipeParticleWork, double casingHeating, long casingCooling, int maxCasingCoolantIn, int maxCasingCoolantOut, HeatBuffer casingHeatBuffer) {
//        super(pos, isContainmentOn, heating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy, heatBuffer,
//                energyStorage, tanks, beams);
//        this.particleWorkDone = particleWorkDone;
//        this.recipeParticleWork = recipeParticleWork;
//        this.casingHeating = casingHeating;
//        this.casingCooling = casingCooling;
//        this.maxCasingCoolantIn = maxCasingCoolantIn;
//        this.maxCasingCoolantOut = maxCasingCoolantOut;
//        this.casingHeatBuffer = casingHeatBuffer;
//
//
//    }
//
//    @Override
//    public void fromBytes(RegistryFriendlyByteBuf buf) {
//        MultiblockUpdatePacket.fromBytes(buf);
//        this.particleWorkDone = buf.readLong();
//        this.recipeParticleWork = buf.readLong();
//        this.casingHeating = buf.readDouble();
//        this.casingCooling = buf.readLong();
//        this.maxCasingCoolantIn = buf.readInt();
//        this.maxCasingCoolantOut = buf.readInt();
//        this.casingHeatBuffer = ByteUtil.readBufHeat(buf);
//
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        super.toBytes(buf);
//        buf.writeLong(particleWorkDone);
//        buf.writeLong(recipeParticleWork);
//        buf.writeDouble(casingHeating);
//        buf.writeLong(casingCooling);
//        buf.writeInt(maxCasingCoolantIn);
//        buf.writeInt(maxCasingCoolantOut);
//        ByteUtil.writeBufHeat(casingHeatBuffer, buf);
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler extends MultiblockUpdatePacket.Handler<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, TileNucleosynthesisChamberController, TileContainerInfo<TileNucleosynthesisChamberController>, NucleosynthesisChamberUpdatePacket> {
//        public Handler() {
//            super(TileNucleosynthesisChamberController.class);
//        }
//
//        @Override
//        protected void onPacket(NucleosynthesisChamberUpdatePacket message, VacuumChamber multiblock) {
//            multiblock.onMultiblockUpdatePacket(message);
//        }
//    }
//}