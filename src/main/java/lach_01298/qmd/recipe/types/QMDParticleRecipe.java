package lach_01298.qmd.recipe.types;

import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;

import java.util.List;

public class QMDParticleRecipe extends QMDRecipe {
    private final long maxEnergy;
    private final double crossSection;
    private final long energyReleased;

    public QMDParticleRecipe(List<SizedChanceItemIngredient> itemIngredientsList, List<SizedChanceFluidIngredient> fluidIngredientsList, List<ParticleStack> particleIngredientsList, List<SizedChanceItemIngredient> itemProductsList, List<SizedChanceFluidIngredient> fluidProductsList, List<ParticleStack> particleProductsList, long maxEnergy, double crossSection, long energyReleased) {
        super(itemIngredientsList, fluidIngredientsList, particleIngredientsList, itemProductsList, fluidProductsList, particleProductsList);
        this.maxEnergy = maxEnergy;
        this.crossSection = crossSection;
        this.energyReleased = energyReleased;
    }

    public long getEnergyReleased() {
        return energyReleased;
    }

    public double getCrossSection() {
        return crossSection;
    }

    public long getMaxEnergy() {
        return maxEnergy;
    }
}
