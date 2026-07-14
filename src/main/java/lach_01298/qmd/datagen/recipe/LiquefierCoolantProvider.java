package lach_01298.qmd.datagen.recipe;

import com.nred.nuclearcraft.info.NCFluid;
import com.nred.nuclearcraft.recipe.BasicRecipeBuilder;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import lach_01298.qmd.recipe.types.LiquefierCoolantRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.material.Fluids;

import static com.nred.nuclearcraft.datagen.ModFluidTagProvider.LIQUID_HELIUM_TAG;
import static com.nred.nuclearcraft.datagen.ModFluidTagProvider.LIQUID_NITROGEN_TAG;
import static com.nred.nuclearcraft.registration.FluidRegistration.CUSTOM_FLUID_MAP;
import static com.nred.nuclearcraft.registration.FluidRegistration.GAS_MAP;
import static lach_01298.qmd.datagen.QMDFluidTagProvider.*;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;

public class LiquefierCoolantProvider {
    public LiquefierCoolantProvider(RecipeOutput recipeOutput) {
        new BasicRecipeBuilder<>(new LiquefierCoolantRecipe(SizedChanceFluidIngredient.of(LIQUID_HELIUM_TAG, 1), NCFluid.sizedIngredient(GAS_MAP.get("helium"), 64), 1000, 4, 300)).save(recipeOutput);
        new BasicRecipeBuilder<>(new LiquefierCoolantRecipe(SizedChanceFluidIngredient.of(LIQUID_NITROGEN_TAG, 1), NCFluid.sizedIngredient(GAS_MAP.get("nitrogen"), 64), 1000, 77, 300)).save(recipeOutput);
        new BasicRecipeBuilder<>(new LiquefierCoolantRecipe(SizedChanceFluidIngredient.of(LIQUID_NEON_TAG, 1), NCFluid.sizedIngredient(QMD_FLUIDS.get("neon"), 64), 1000, 27, 300)).save(recipeOutput);
        new BasicRecipeBuilder<>(new LiquefierCoolantRecipe(SizedChanceFluidIngredient.of(LIQUID_ARGON_TAG, 1), NCFluid.sizedIngredient(QMD_FLUIDS.get("argon"), 64), 1000, 87, 300)).save(recipeOutput);
        new BasicRecipeBuilder<>(new LiquefierCoolantRecipe(SizedChanceFluidIngredient.of(Fluids.WATER, 1), NCFluid.sizedIngredient(CUSTOM_FLUID_MAP.get("condensate_water"), 1), 32, 300, 350)).save(recipeOutput);
    }
}
