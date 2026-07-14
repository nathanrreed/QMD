//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank.TankInfo;
//import lach_01298.qmd.particle.ParticleStorage;
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
//public class ParticleChamberUpdatePacket extends QMDMultiblockUpdatePacket {
//    public static final Type<ParticleChamberUpdatePacket> TYPE = new Type<>(ncLoc("particle_chamber_update_packet"));
//    public static final StreamCodec<RegistryFriendlyByteBuf, ParticleChamberUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
//            ParticleChamberUpdatePacket::toBytes, ParticleChamberUpdatePacket::fromBytes
//    );
//    public boolean isChamberOn;
//    public int requiredEnergy;
//    public double efficiency;
//    public EnergyStorage energyStorage;
//    public List<TankInfo> tanksInfo;
//    public List<ParticleStorageAccelerator> beams;
//
//    public ParticleChamberUpdatePacket(BlockPos pos, boolean isChamberOn, int requiredEnergy, double efficiency, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
//        super(pos);
//        this.isChamberOn = isChamberOn;
//        this.requiredEnergy = requiredEnergy;
//        this.efficiency = efficiency;
//        this.energyStorage = energyStorage;
//
//        tanksInfo = TankInfo.getInfoList(tanks);
//        this.beams = beams;
//    }
//
//    public static ParticleChamberUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
//        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
//        isChamberOn = buf.readBoolean();
//        requiredEnergy = buf.readInt();
//        efficiency = buf.readDouble();
//        energyStorage = ByteUtil.readBufEnergy(buf);
//        tanksInfo = readTankInfos(buf);
//
//        int size = buf.readInt();
//        for (int i = 0; i < size; i++) {
//            ParticleStorage storage = ByteUtil.readBufBeam(buf);
//            ParticleStorageAccelerator beam = new ParticleStorageAccelerator();
//            beam.setParticleStack(storage.getParticleStack());
//            beam.setMaxEnergy(storage.getMaxEnergy());
//            beam.setMinEnergy(storage.getMinEnergy());
//            beam.setCapacity(storage.getCapacity());
//            beams.add(beam);
//        }
//    }
//
//    @Override
//    public void toBytes(RegistryFriendlyByteBuf buf) {
//        buf.writeInt(pos.getX());
//        buf.writeInt(pos.getY());
//        buf.writeInt(pos.getZ());
//        buf.writeBoolean(isChamberOn);
//        buf.writeInt(requiredEnergy);
//        buf.writeDouble(efficiency);
//        ByteUtil.writeBufEnergy(energyStorage, buf);
//        writeTankInfos(buf, tanksInfo);
//
//        buf.writeInt(beams.size());
//        for (ParticleStorageAccelerator beam : beams) {
//            ByteUtil.writeBufBeam(beam, buf);
//        }
//    }
//
//    @Override
//    public Type<? extends CustomPacketPayload> type() {
//        return TYPE;
//    }
//}