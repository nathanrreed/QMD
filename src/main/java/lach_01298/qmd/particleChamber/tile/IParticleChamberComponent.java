package lach_01298.qmd.particleChamber.tile;

import net.minecraft.core.BlockPos;

public interface IParticleChamberComponent extends IParticleChamberPart {
    boolean isFunctional();

    void setFunctional(boolean func);

    void resetStats();

    // Helper methods

    default boolean isActiveDetector(BlockPos pos, String name) {
        return false;
    }
}