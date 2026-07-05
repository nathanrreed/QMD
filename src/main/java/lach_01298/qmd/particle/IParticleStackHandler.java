package lach_01298.qmd.particle;

import net.minecraft.core.Direction;

public interface IParticleStackHandler {

    /**
     *
     * @param side
     * @param stack - the ParticleStack to be inputed
     * @return if the stack could be inputed
     */
    boolean reciveParticle(Direction side, ParticleStack stack);


    /**
     *
     * @param side
     * @return the extracted ParticleStack
     */
    ParticleStack extractParticle(Direction side);

    /**
     *
     * @param side
     * @param type the type of particles
     * @return the extracted ParticleStack
     */
    ParticleStack extractParticle(Direction side, Particle type);

    /**
     *
     * @param side
     * @param Amount the amount of particles
     * @return the extracted ParticleStack
     */
    ParticleStack extractParticle(Direction side, int Amount);

    /**
     *
     * @param side
     * @param type   the type of particles
     * @param Amount the amount of particles
     * @return the extracted ParticleStack
     */
    ParticleStack extractParticle(Direction side, Particle type, int Amount);

    /**
     *
     *
     * @return a copy of the ParticleStack
     */
    ParticleStack getParticle();

    /**
     *
     * @param side
     * @param stack - the ParticleStack to be inputed
     * @return if the stack could be inputed
     */
    boolean canReciveParticle(Direction side, ParticleStack stack);

    /**
     *
     * @param side
     * @return if the ParticleStack could be extracted
     */
    boolean canExtractParticle(Direction side);
}