package lach_01298.qmd.particle;

import net.minecraft.core.Direction;

public class ParticleStorageSource extends ParticleStorage {
    public ParticleStorageSource() {
        super(null, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public boolean reciveParticle(Direction side, ParticleStack stack) {
        return false;
    }

    @Override
    public boolean canReciveParticle(Direction side, ParticleStack stack) {
        return false;
    }

    @Override
    public ParticleStack extractParticle(Direction side) {
        if (canExtractParticle(side)) {
            ParticleStack stack = this.particleStack;
            return stack;
        }
        return null;
    }
}