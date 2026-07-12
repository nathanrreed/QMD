package lach_01298.qmd.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nred.nuclearcraft.util.NCMath;
import lach_01298.qmd.config.QMDServerConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.NonNull;

public class ParticleStack {
    private Particle particle;
    private int amount;
    private long meanEnergy;
    private double focus;            //Basically inverse area of the beam
    public static final Codec<ParticleStack> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.STRING.fieldOf("particle").forGetter(ParticleStack::getParticleString),
            Codec.INT.fieldOf("amount").forGetter(ParticleStack::getAmount),
            Codec.LONG.fieldOf("meanEnergy").forGetter(ParticleStack::getMeanEnergy),
            Codec.DOUBLE.fieldOf("focus").forGetter(ParticleStack::getFocus)
    ).apply(inst, ParticleStack::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ParticleStack> STREAM_CODEC = new StreamCodec<>() {
        public @NonNull ParticleStack decode(RegistryFriendlyByteBuf buf) {
            ParticleStack particleStack = new ParticleStack();

            particleStack.setParticle(Particles.getParticleFromName(buf.readUtf(30)));
            particleStack.setAmount(buf.readInt());
            particleStack.setMeanEnergy(buf.readLong());
            particleStack.setFocus(buf.readDouble());

            return particleStack;
        }

        public void encode(RegistryFriendlyByteBuf buf, ParticleStack particleStack) {
            if (particleStack.getParticle() == null) {
                buf.writeUtf("");
            } else {
                buf.writeUtf(particleStack.getParticle().name);
            }
            buf.writeInt(particleStack.amount);
            buf.writeLong(particleStack.meanEnergy);
            buf.writeDouble(particleStack.focus);
        }
    };

    public ParticleStack() {
        this.particle = null;
        this.amount = 0;
        this.meanEnergy = 0;
        this.focus = 0;
    }

    public ParticleStack(String particle, int amount, long meanEnergy, double focus) {
        this(Particles.getParticleFromName(particle), amount, meanEnergy, focus);
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

    public String getParticleString() {
        return particle.getName();
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