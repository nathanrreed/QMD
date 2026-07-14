package lach_01298.qmd.datagen.recipe;

import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import lach_01298.qmd.QMDConstants;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeBuilder;
import lach_01298.qmd.recipe.types.AcceleratorSourceRecipe;
import net.minecraft.data.recipes.RecipeOutput;

import java.util.List;

import static com.nred.nuclearcraft.datagen.ModFluidTagProvider.*;
import static com.nred.nuclearcraft.util.FluidStackHelper.BUCKET_VOLUME;
import static lach_01298.qmd.datagen.QMDFluidTagProvider.DIBORANE_TAG;

public class AcceleratorSourceProvider {
    public AcceleratorSourceProvider(RecipeOutput recipeOutput) {
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(), List.of(SizedChanceFluidIngredient.of(HYDROGEN_TAG, BUCKET_VOLUME / 2 * QMDConstants.ionSourceOutput / QMDConstants.moleAmount)), new ParticleStack(Particles.proton, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(), List.of(SizedChanceFluidIngredient.of(DEUTERIUM_TAG, BUCKET_VOLUME / 2 * QMDConstants.ionSourceOutput / QMDConstants.moleAmount)), new ParticleStack(Particles.deuteron, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(), List.of(SizedChanceFluidIngredient.of(TRITIUM_TAG, BUCKET_VOLUME / 2 * QMDConstants.ionSourceOutput / QMDConstants.moleAmount)), new ParticleStack(Particles.triton, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(), List.of(SizedChanceFluidIngredient.of(HELIUM_3_TAG, BUCKET_VOLUME * QMDConstants.ionSourceOutput / QMDConstants.moleAmount)), new ParticleStack(Particles.helion, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(), List.of(SizedChanceFluidIngredient.of(HELIUM_TAG, BUCKET_VOLUME * QMDConstants.ionSourceOutput / QMDConstants.moleAmount)), new ParticleStack(Particles.alpha, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(), List.of(SizedChanceFluidIngredient.of(DIBORANE_TAG, BUCKET_VOLUME / 2 * QMDConstants.ionSourceOutput / QMDConstants.moleAmount)), new ParticleStack(Particles.boron_ion, QMDConstants.ionSourceOutput))).save(recipeOutput);

        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(SizedChanceItemIngredient.of(QMDItems.sources.get(SourceType.TUNGSTEN_FILAMENT), 1)), List.of(), new ParticleStack(Particles.electron, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(SizedChanceItemIngredient.of(QMDItems.sources.get(SourceType.SODIUM_22), 1)), List.of(), new ParticleStack(Particles.positron, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(SizedChanceItemIngredient.of(QMDItems.sources.get(SourceType.CALCIUM_48), 1)), List.of(), new ParticleStack(Particles.calcium_48_ion, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(SizedChanceItemIngredient.of(QMDItems.cells.get(CellType.ANTIHYDROGEN), 1)), List.of(), new ParticleStack(Particles.antiproton, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(SizedChanceItemIngredient.of(QMDItems.cells.get(CellType.ANTIDEUTERIUM), 1)), List.of(), new ParticleStack(Particles.antideuteron, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(SizedChanceItemIngredient.of(QMDItems.cells.get(CellType.ANTITRITIUM), 1)), List.of(), new ParticleStack(Particles.antitriton, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(SizedChanceItemIngredient.of(QMDItems.cells.get(CellType.ANTIHELIUM3), 1)), List.of(), new ParticleStack(Particles.antihelion, QMDConstants.ionSourceOutput))).save(recipeOutput);
        new QMDRecipeBuilder<>(new AcceleratorSourceRecipe(List.of(SizedChanceItemIngredient.of(QMDItems.cells.get(CellType.ANTIHELIUM), 1)), List.of(), new ParticleStack(Particles.antialpha, QMDConstants.ionSourceOutput))).save(recipeOutput);
    }
}