package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank.TankInfo;
import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.util.ByteUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public class AcceleratorUpdatePacket extends QMDMultiblockUpdatePacket {
    public boolean isAcceleratorOn;
    public long cooling, rawHeating, currentHeating;
    public int maxCoolantIn, maxCoolantOut;
    public int maxOperatingTemp;
    public int requiredEnergy;
    public double efficiency, quadrupoleStrength, dipoleStrength;
    public int quadrupoleNumber, RFCavityNumber, acceleratingVoltage, dipoleNumber;
    public HeatBuffer heatBuffer;
    public EnergyStorage energyStorage;
    public List<TankInfo> tanksInfo;
    public List<ParticleStorageAccelerator> beams;
    public int errorCode;

    public AcceleratorUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating, long currentHeating, int maxCoolantIn, int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, double efficiency, int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength, int dipoleNumber, double dipoleStrength, int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
        super(pos);
        this.isAcceleratorOn = isAcceleratorOn;
        this.cooling = cooling;
        this.rawHeating = rawHeating;
        this.currentHeating = currentHeating;
        this.maxCoolantIn = maxCoolantIn;
        this.maxCoolantOut = maxCoolantOut;
        this.maxOperatingTemp = maxOperatingTemp;
        this.requiredEnergy = requiredEnergy;
        this.efficiency = efficiency;
        this.acceleratingVoltage = acceleratingVoltage;
        this.RFCavityNumber = RFCavityNumber;
        this.quadrupoleNumber = quadrupoleNumber;
        this.quadrupoleStrength = quadrupoleStrength;
        this.dipoleNumber = dipoleNumber;
        this.dipoleStrength = dipoleStrength;
        this.errorCode = errorCode;

        this.heatBuffer = heatBuffer;
        this.energyStorage = energyStorage;

        tanksInfo = TankInfo.getInfoList(tanks);

        this.beams = beams;
    }

    public AcceleratorUpdatePacket(AcceleratorUpdatePacket packet) {
        super(packet.pos);
        this.isAcceleratorOn = packet.isAcceleratorOn;
        this.cooling = packet.cooling;
        this.rawHeating = packet.rawHeating;
        this.currentHeating = packet.currentHeating;
        this.maxCoolantIn = packet.maxCoolantIn;
        this.maxCoolantOut = packet.maxCoolantOut;
        this.maxOperatingTemp = packet.maxOperatingTemp;
        this.requiredEnergy = packet.requiredEnergy;
        this.efficiency = packet.efficiency;
        this.acceleratingVoltage = packet.acceleratingVoltage;
        this.RFCavityNumber = packet.RFCavityNumber;
        this.quadrupoleNumber = packet.quadrupoleNumber;
        this.quadrupoleStrength = packet.quadrupoleStrength;
        this.dipoleNumber = packet.dipoleNumber;
        this.dipoleStrength = packet.dipoleStrength;
        this.errorCode = packet.errorCode;

        this.heatBuffer = packet.heatBuffer;
        this.energyStorage = packet.energyStorage;

        this.tanksInfo = packet.tanksInfo;

        this.beams = packet.beams;
    }

    public AcceleratorUpdatePacket(MultiblockUpdatePacket packet, boolean isAcceleratorOn, long cooling, long rawHeating, long currentHeating, int maxCoolantIn, int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, double efficiency, int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength, int dipoleNumber, double dipoleStrength, int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<TankInfo> tanks, List<ParticleStorageAccelerator> beams) {
        super(packet);
        this.isAcceleratorOn = isAcceleratorOn;
        this.cooling = cooling;
        this.rawHeating = rawHeating;
        this.currentHeating = currentHeating;
        this.maxCoolantIn = maxCoolantIn;
        this.maxCoolantOut = maxCoolantOut;
        this.maxOperatingTemp = maxOperatingTemp;
        this.requiredEnergy = requiredEnergy;
        this.efficiency = efficiency;
        this.acceleratingVoltage = acceleratingVoltage;
        this.RFCavityNumber = RFCavityNumber;
        this.quadrupoleNumber = quadrupoleNumber;
        this.quadrupoleStrength = quadrupoleStrength;
        this.dipoleNumber = dipoleNumber;
        this.dipoleStrength = dipoleStrength;
        this.errorCode = errorCode;

        this.heatBuffer = heatBuffer;
        this.energyStorage = energyStorage;

        tanksInfo = tanks;

        this.beams = beams;
    }

    public static AcceleratorUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        MultiblockUpdatePacket packet = MultiblockUpdatePacket.fromBytes(buf);
        boolean isAcceleratorOn = buf.readBoolean();
        long cooling = buf.readLong();
        long rawHeating = buf.readLong();
        long currentHeating = buf.readLong();
        int maxCoolantIn = buf.readInt();
        int maxCoolantOut = buf.readInt();
        int maxOperatingTemp = buf.readInt();
        int requiredEnergy = buf.readInt();
        double efficiency = buf.readDouble();
        int acceleratingVoltage = buf.readInt();
        int RFCavityNumber = buf.readInt();
        int quadrupoleNumber = buf.readInt();
        double quadrupoleStrength = buf.readDouble();
        int dipoleNumber = buf.readInt();
        double dipoleStrength = buf.readDouble();
        int errorCode = buf.readInt();

        HeatBuffer heatBuffer = ByteUtil.readBufHeat(buf);
        EnergyStorage energyStorage = ByteUtil.readBufEnergy(buf);
        List<TankInfo> tanksInfo = readTankInfos(buf);

        List<ParticleStorageAccelerator> beams = new ArrayList<>();

        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            ParticleStorage storage = ByteUtil.readBufBeam(buf);
            ParticleStorageAccelerator beam = new ParticleStorageAccelerator();
            beam.setParticleStack(storage.getParticleStack());
            beam.setMaxEnergy(storage.getMaxEnergy());
            beam.setMinEnergy(storage.getMinEnergy());
            beam.setCapacity(storage.getCapacity());
            beams.add(beam);
        }
        return new AcceleratorUpdatePacket(packet, isAcceleratorOn, cooling, rawHeating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy, efficiency, acceleratingVoltage, RFCavityNumber, quadrupoleNumber, quadrupoleStrength, dipoleNumber, dipoleStrength, errorCode, heatBuffer, energyStorage, tanksInfo, beams);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);

        buf.writeBoolean(isAcceleratorOn);
        buf.writeLong(cooling);
        buf.writeLong(rawHeating);
        buf.writeLong(currentHeating);
        buf.writeInt(maxCoolantIn);
        buf.writeInt(maxCoolantOut);
        buf.writeInt(maxOperatingTemp);
        buf.writeInt(requiredEnergy);
        buf.writeDouble(efficiency);
        buf.writeInt(acceleratingVoltage);
        buf.writeInt(RFCavityNumber);
        buf.writeInt(quadrupoleNumber);
        buf.writeDouble(quadrupoleStrength);
        buf.writeInt(dipoleNumber);
        buf.writeDouble(dipoleStrength);
        buf.writeInt(errorCode);

        ByteUtil.writeBufHeat(heatBuffer, buf);
        ByteUtil.writeBufEnergy(energyStorage, buf);
        writeTankInfos(buf, tanksInfo);

        buf.writeInt(beams.size());
        for (ParticleStorageAccelerator beam : beams) {
            ByteUtil.writeBufBeam(beam, buf);
        }
    }
}