package lach_01298.qmd.particle;

import com.nred.nuclearcraft.util.NCMath;
import lach_01298.qmd.config.QMDServerConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class ParticleStack {
    private Particle particle;
    private int amount;
    private long meanEnergy;
    private double focus;            //Basically inverse area of the beam

    public ParticleStack() {
        this.particle = null;
        this.amount = 0;
        this.meanEnergy = 0;
        this.focus = 0;
    }

    public ParticleStack(Particle particle, int amount, long meanEnergy, double focus) {
        this.particle = particle;
        this.amount = (int) NCMath.clamp(amount, 0, Integer.MAX_VALUE);
        this.meanEnergy = NCMath.clamp(meanEnergy, 0, Long.MAX_VALUE);
        this.focus = focus;
    }

    public ParticleStack(Particle particle, int amount, long meanEnergy) {
        this.particle = particle;
        this.meanEnergy = NCMath.clamp(meanEnergy, 0, Long.MAX_VALUE);
        this.amount = (int) NCMath.clamp(amount, 0, Integer.MAX_VALUE);
        this.focus = 0;
    }

    public ParticleStack(Particle particle, int amount) {
        this.particle = particle;
        this.amount = (int) NCMath.clamp(amount, 0, Integer.MAX_VALUE);
        this.meanEnergy = 0;
        this.focus = 0;
    }

    public ParticleStack(Particle particle) {
        this.particle = particle;
        this.amount = 1;
        this.meanEnergy = 0;
        this.focus = 0;
    }

    public Particle getParticle() {
        return particle;
    }

    public long getMeanEnergy() {
        return meanEnergy;
    }

    public int getAmount() {
        return amount;
    }

    public double getFocus() {
        return focus;
    }

    public void setParticle(Particle newParticle) {
        this.particle = newParticle;
    }

    public void setMeanEnergy(long newMeanEnergy) {
        this.meanEnergy = newMeanEnergy;
    }

    public void addMeanEnergy(long add) {
        this.meanEnergy += add;
    }

    public void setAmount(int newAmount) {
        this.amount = newAmount;
    }

    public void addAmount(long add) {
        this.amount += add;
    }

    public void removeAmount(long remove) {
        this.amount -= remove;
        if (amount < 0) {
            amount = 0;
            particle = null;
        }
    }

    public void setFocus(double newFocus) {
        this.focus = newFocus;
    }

    public void addFocus(double add) {
        this.focus += add;
    }


    public CompoundTag writeToNBT(CompoundTag nbt) {
        if (particle != null) {
            nbt.putString("particles", particle.getName());
        }

        nbt.putInt("amount", amount);
        nbt.putLong("meanEnergy", meanEnergy);
        nbt.putDouble("focus", focus);

        return nbt;
    }

    public void readFromNBT(CompoundTag nbt) {
        if (nbt.contains("particles")) {
            this.particle = Particles.getParticleFromName(nbt.getString("particles"));
        } else {
            this.particle = null;
        }

        this.amount = nbt.getInt("amount");
        this.meanEnergy = nbt.getLong("meanEnergy");
        this.focus = nbt.getDouble("focus");
    }

    public ParticleStack copy() {
        return new ParticleStack(particle, amount, meanEnergy, focus);
    }

    public static ParticleStack loadParticleStackFromNBT(CompoundTag nbt) {
        if (nbt == null) {
            return null;
        }
        if (!nbt.contains("particles", Tag.TAG_STRING)) {
            return null;
        }

        String particleName = nbt.getString("particles");
        int amount = nbt.getInt("amount");
        long energy = nbt.getLong("meanEnergy");
        double focus = nbt.getDouble("focus");

        ParticleStack beam = new ParticleStack(Particles.getParticleFromName(particleName), amount, energy, focus);

        return beam;
    }


    public static ParticleStack getParticleStack(String particleName, int amount, long meanEnergy, double focus) {
        return new ParticleStack(Particles.getParticleFromName(particleName), amount, meanEnergy, focus);
    }

    public boolean matchesType(ParticleStack particleStack) {
        if (particleStack != null) {
            if (particleStack.getParticle() == particle) {
                return true;
            }
        }
        return false;
    }

    public boolean isInRange(ParticleStack particleStack, long maxEnergy) {
        if (particleStack != null) {
            if (particleStack.getParticle() == particle) {
                if (particleStack.getFocus() >= focus - QMDServerConfig.beamAttenuationRate / 10d) {
                    if (particleStack.getMeanEnergy() >= meanEnergy && particleStack.getMeanEnergy() <= maxEnergy) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}