package lach_01298.qmd.datagen.recipe;

import com.nred.nuclearcraft.info.NCFluid;
import lach_01298.qmd.QMDConstants;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeBuilder;
import lach_01298.qmd.recipe.types.BeamDumpRecipe;
import net.minecraft.data.recipes.RecipeOutput;

import static com.nred.nuclearcraft.registration.FluidRegistration.GAS_MAP;

public class BeamDumpProvider {
    public BeamDumpProvider(RecipeOutput recipeOutput) {
        //particles per milibucket
        int ppmB = QMDConstants.moleAmount / QMDConstants.bucketAmount;

        new QMDRecipeBuilder<>(new BeamDumpRecipe(new ParticleStack(Particles.proton, 2 * ppmB), NCFluid.sizedIngredient(GAS_MAP.get("hydrogen"), 1), Long.MAX_VALUE)).save(recipeOutput);
        new QMDRecipeBuilder<>(new BeamDumpRecipe(new ParticleStack(Particles.deuteron, 2 * ppmB), NCFluid.sizedIngredient(GAS_MAP.get("deuterium"), 1), Long.MAX_VALUE)).save(recipeOutput);
        new QMDRecipeBuilder<>(new BeamDumpRecipe(new ParticleStack(Particles.triton, 2 * ppmB), NCFluid.sizedIngredient(GAS_MAP.get("tritium"), 1), Long.MAX_VALUE)).save(recipeOutput);
        new QMDRecipeBuilder<>(new BeamDumpRecipe(new ParticleStack(Particles.helion, ppmB), NCFluid.sizedIngredient(GAS_MAP.get("helium_3"), 1), Long.MAX_VALUE)).save(recipeOutput);
        new QMDRecipeBuilder<>(new BeamDumpRecipe(new ParticleStack(Particles.alpha, ppmB), NCFluid.sizedIngredient(GAS_MAP.get("helium"), 1), Long.MAX_VALUE)).save(recipeOutput);
    }
}