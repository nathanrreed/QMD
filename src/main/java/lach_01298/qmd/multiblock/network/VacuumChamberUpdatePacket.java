//package lach_01298.qmd.multiblock.network;
//
//import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank.TankInfo;
//import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
//import lach_01298.qmd.particle.ParticleStorage;
//import lach_01298.qmd.particle.ParticleStorageAccelerator;
//import lach_01298.qmd.util.ByteUtil;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//
//import java.util.List;
//
//public abstract class VacuumChamberUpdatePacket extends QMDMultiblockUpdatePacket {
//
//    public boolean isContainmentOn;
//    public long heating, currentHeating;
//    public int maxCoolantIn, maxCoolantOut;
//    public int maxOperatingTemp;
//    public int requiredEnergy;
//
//    public HeatBuffer heatBuffer;
//    public EnergyStorage energyStorage;
//    public List<TankInfo> tanksInfo;
//    public List<ParticleStorageAccelerator> beams;
//
//    public VacuumChamberUpdatePacket(BlockPos pos, boolean isContainmentOn, long heating, long currentHeating, int maxCoolantIn, int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
//        super(pos);
//        this.isContainmentOn = isContainmentOn;
//
//        this.heating = heating;
//        this.currentHeating = currentHeating;
//        this.maxCoolantIn = maxCoolantIn;
//        this.maxCoolantOut = maxCoolantOut;
//        this.maxOperatingTemp = maxOperatingTemp;
//        this.requiredEnergy = requiredEnergy;
//
//        this.heatBuffer = heatBuffer;
//        this.energyStorage = energyStorage;
//
//        tanksInfo = TankInfo.getInfoList(tanks);
//
//        this.beams = beams;
//    }
//
//    @Override
//    public void fromBytes(RegistryFriendlyByteBuf buf) {
//        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
//        isContainmentOn = buf.readBoolean();
//        heating = buf.readLong();
//        currentHeating = buf.readLong();
//        maxCoolantIn = buf.readInt();
//        maxCoolantOut = buf.readInt();
//        maxOperatingTemp = buf.readInt();
//        requiredEnergy = buf.readInt();
//
//        heatBuffer = ByteUtil.readBufHeat(buf);
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
//        buf.writeBoolean(isContainmentOn);
//        buf.writeLong(heating);
//        buf.writeLong(currentHeating);
//        buf.writeInt(maxCoolantIn);
//        buf.writeInt(maxCoolantOut);
//        buf.writeInt(maxOperatingTemp);
//        buf.writeInt(requiredEnergy);
//
//        ByteUtil.writeBufHeat(heatBuffer, buf);
//        ByteUtil.writeBufEnergy(energyStorage, buf);
//        writeTankInfos(buf, tanksInfo);
//
//        buf.writeInt(beams.size());
//        for (ParticleStorageAccelerator beam : beams) {
//            ByteUtil.writeBufBeam(beam, buf);
//        }
//    }
//}