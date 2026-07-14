//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
//import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
//import lach_01298.qmd.particle.ParticleStorageAccelerator;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//
//import java.util.List;
//
//import static com.nred.nuclearcraft.helpers.Location.ncLoc;
//
//public class NeutralContainmentUpdatePacket extends VacuumChamberUpdatePacket {
//    public static final Type<NeutralContainmentUpdatePacket> TYPE = new Type<>(ncLoc("neutral_containment_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, NeutralContainmentUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            NeutralContainmentUpdatePacket::toBytes, NeutralContainmentUpdatePacket::fromBytes
//    );
//    public long particle1WorkDone, particle2WorkDone, recipeParticle1Work, recipeParticle2Work;
//
//    public NeutralContainmentUpdatePacket(BlockPos pos, boolean isContainmentOn, long heating, long currentHeating, int maxCoolantIn,
//                                          int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, HeatBuffer heatBuffer,
//                                          EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams,
//                                          long particle1WorkDone, long particle2WorkDone, long recipeParticle1Work, long recipeParticle2Work) {
//        super(pos, isContainmentOn, heating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy, heatBuffer,
//                energyStorage, tanks, beams);
//        this.particle1WorkDone = particle1WorkDone;
//        this.particle2WorkDone = particle2WorkDone;
//        this.recipeParticle1Work = recipeParticle1Work;
//        this.recipeParticle2Work = recipeParticle2Work;
//    }
//
//    @Override
//    public void fromBytes(RegistryFriendlyByteBuf buf) {
//        MultiblockUpdatePacket.fromBytes(buf);
//        this.particle1WorkDone = buf.readLong();
//        this.particle2WorkDone = buf.readLong();
//        this.recipeParticle1Work = buf.readLong();
//        this.recipeParticle2Work = buf.readLong();
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        super.toBytes(buf);
//        buf.writeLong(particle1WorkDone);
//        buf.writeLong(particle2WorkDone);
//        buf.writeLong(recipeParticle1Work);
//        buf.writeLong(recipeParticle2Work);
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//
//    public static class Handler extends MultiblockUpdatePacket.Handler<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, TileExoticContainmentController, TileContainerInfo<TileExoticContainmentController>, NeutralContainmentUpdatePacket> {
//        public Handler() {
//            super(TileExoticContainmentController.class);
//        }
//
//        @Override
//        protected void onPacket(NeutralContainmentUpdatePacket message, VacuumChamber multiblock) {
//            multiblock.onMultiblockUpdatePacket(message);
//        }
//    }
//}