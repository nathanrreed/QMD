package lach_01298.qmd.recipe;

import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import lach_01298.qmd.particle.ParticleStack;

import java.util.List;

public interface IQMDRecipe {
    List<SizedChanceItemIngredient> getItemIngredients();

    List<SizedChanceFluidIngredient> getFluidIngredients();

    List<ParticleStack> getParticleIngredients();

    List<SizedChanceItemIngredient> getItemProducts();

    List<SizedChanceFluidIngredient> getFluidProducts();

    List<ParticleStack> getParticleProducts();
}