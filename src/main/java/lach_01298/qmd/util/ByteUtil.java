package lach_01298.qmd.util;

import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.heat.HeatBuffer;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.Particles;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class ByteUtil {
    public static void writeBufHeat(HeatBuffer heatBuffer, RegistryFriendlyByteBuf buf) {
        buf.writeLong(heatBuffer.getHeatCapacity());
        buf.writeLong(heatBuffer.getHeatStored());
    }

    public static HeatBuffer readBufHeat(RegistryFriendlyByteBuf buf) {
        long heatCapacity = buf.readLong();
        long heatStored = buf.readLong();
        HeatBuffer buffer = new HeatBuffer(heatCapacity);
        buffer.setHeatStored(heatStored);
        return buffer;
    }

    public static void writeBufEnergy(EnergyStorage energyStorage, RegistryFriendlyByteBuf buf) {
        buf.writeInt(energyStorage.getMaxEnergyStored());
        buf.writeInt(energyStorage.getEnergyStored());
    }

    public static EnergyStorage readBufEnergy(RegistryFriendlyByteBuf buf) {
        int maxEnergy = buf.readInt();
        int energy = buf.readInt();
        EnergyStorage buffer = new EnergyStorage(maxEnergy);
        buffer.setEnergyStored(energy);
        return buffer;
    }

    public static void writeBufBeam(ParticleStorage storage, RegistryFriendlyByteBuf buf) {
        ParticleStack stack = storage.getParticleStack();

        if (stack == null) {
            buf.writeUtf("none");
            buf.writeInt(0);
            buf.writeLong(0);
            buf.writeDouble(0);
        } else {
            if (stack.getParticle() == null) {
                buf.writeUtf("none");
            } else {
                buf.writeUtf(stack.getParticle().getName());
            }

            buf.writeInt(stack.getAmount());
            buf.writeLong(stack.getMeanEnergy());
            buf.writeDouble(stack.getFocus());
        }

        buf.writeLong(storage.getMaxEnergy());
        buf.writeLong(storage.getMinEnergy());
    }

    public static ParticleStorage readBufBeam(RegistryFriendlyByteBuf buf) {
        ParticleStorage storage = new ParticleStorage();

        String string = buf.readUtf();
        if (string.equals("none")) {
            storage.setParticleStack(null);
            buf.readInt();
            buf.readLong();
            buf.readDouble();
        } else {
            Particle p = Particles.getParticleFromName(string);
            int amount = buf.readInt();
            long energy = buf.readLong();
            double focus = buf.readDouble();


            ParticleStack stack = new ParticleStack(p, amount, energy, focus);
            storage.setParticleStack(stack);
        }

        storage.setMaxEnergy(buf.readLong());
        storage.setMinEnergy(buf.readLong());

        return storage;
    }

    public static void writeBufParticleStorage(ParticleStorage storage, RegistryFriendlyByteBuf buf) {
        ParticleStack stack = storage.getParticleStack();

        if (stack == null) {
            buf.writeUtf("none");
            buf.writeInt(0);
            buf.writeLong(0);
            buf.writeDouble(0);
        } else {
            if (stack.getParticle() == null) {
                buf.writeUtf("none");
            } else {
                buf.writeUtf(stack.getParticle().getName());
            }

            buf.writeInt(stack.getAmount());
            buf.writeLong(stack.getMeanEnergy());
            buf.writeDouble(stack.getFocus());
        }

        buf.writeLong(storage.getMaxEnergy());
        buf.writeLong(storage.getMinEnergy());
    }

    public static ParticleStorage readBufParticleStorage(RegistryFriendlyByteBuf buf) {
        ParticleStorage storage = new ParticleStorage();

        String string = buf.readUtf();
        if (string.equals("none")) {
            storage.setParticleStack(null);
            buf.readInt();
            buf.readLong();
            buf.readDouble();
        } else {
            Particle p = Particles.getParticleFromName(string);
            int amount = buf.readInt();
            long energy = buf.readLong();
            double focus = buf.readDouble();

            ParticleStack stack = new ParticleStack(p, amount, energy, focus);
            storage.setParticleStack(stack);
        }

        storage.setMaxEnergy(buf.readLong());
        storage.setMinEnergy(buf.readLong());

        return storage;
    }
}