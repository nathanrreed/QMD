package lach_01298.qmd.multiblock.network;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank.TankInfo;
import com.nred.nuclearcraft.payload.multiblock.MultiblockUpdatePacket;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.util.ByteUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ParticleChamberUpdatePacket extends QMDMultiblockUpdatePacket {
    public boolean isChamberOn;
    public int requiredEnergy;
    public double efficiency;
    public EnergyStorage energyStorage;
    public List<TankInfo> tanksInfo;
    public List<ParticleStorageAccelerator> beams;

    public ParticleChamberUpdatePacket(BlockPos pos, boolean isChamberOn, int requiredEnergy, double efficiency, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams) {
        super(pos);
        this.isChamberOn = isChamberOn;
        this.requiredEnergy = requiredEnergy;
        this.efficiency = efficiency;
        this.energyStorage = energyStorage;

        this.tanksInfo = TankInfo.getInfoList(tanks);
        this.beams = beams;
    }

    public ParticleChamberUpdatePacket(ParticleChamberUpdatePacket packet) {
        super(packet.pos);
        this.isChamberOn = packet.isChamberOn;
        this.requiredEnergy = packet.requiredEnergy;
        this.efficiency = packet.efficiency;
        this.energyStorage = packet.energyStorage;

        this.tanksInfo = packet.tanksInfo;
        this.beams = packet.beams;
    }

    public ParticleChamberUpdatePacket(MultiblockUpdatePacket packet, boolean isChamberOn, int requiredEnergy, double efficiency, EnergyStorage energyStorage, List<TankInfo> tanksInfo, List<ParticleStorageAccelerator> beams) {
        super(packet);
        this.isChamberOn = isChamberOn;
        this.requiredEnergy = requiredEnergy;
        this.efficiency = efficiency;
        this.energyStorage = energyStorage;

        this.tanksInfo = tanksInfo;
        this.beams = beams;
    }

    public static ParticleChamberUpdatePacket fromBytes(RegistryFriendlyByteBuf buf) {
        MultiblockUpdatePacket packet = MultiblockUpdatePacket.fromBytes(buf);
        boolean isChamberOn = buf.readBoolean();
        int requiredEnergy = buf.readInt();
        double efficiency = buf.readDouble();
        EnergyStorage energyStorage = ByteUtil.readBufEnergy(buf);
        List<TankInfo> tanksInfo = readTankInfos(buf);

        int size = buf.readInt();
        List<ParticleStorageAccelerator> beams = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ParticleStorage storage = ByteUtil.readBufBeam(buf);
            ParticleStorageAccelerator beam = new ParticleStorageAccelerator();
            beam.setParticleStack(storage.getParticleStack());
            beam.setMaxEnergy(storage.getMaxEnergy());
            beam.setMinEnergy(storage.getMinEnergy());
            beam.setCapacity(storage.getCapacity());
            beams.add(beam);
        }
        return new ParticleChamberUpdatePacket(packet, isChamberOn, requiredEnergy, efficiency, energyStorage, tanksInfo, beams);
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        super.toBytes(buf);
        buf.writeBoolean(isChamberOn);
        buf.writeInt(requiredEnergy);
        buf.writeDouble(efficiency);
        ByteUtil.writeBufEnergy(energyStorage, buf);
        writeTankInfos(buf, tanksInfo);

        buf.writeInt(beams.size());
        for (ParticleStorageAccelerator beam : beams) {
            ByteUtil.writeBufBeam(beam, buf);
        }
    }
}