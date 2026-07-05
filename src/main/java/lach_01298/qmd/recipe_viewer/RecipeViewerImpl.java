package lach_01298.qmd.recipe_viewer;

import com.nred.nuclearcraft.compat.recipe_viewer.RecipeViewerImpl.ProcessorRecipeViewer;
import lach_01298.qmd.recipe.types.IrradiatorRecipe;
import lach_01298.qmd.recipe.types.OreLeacherRecipe;

public class RecipeViewerImpl {
    public static class IrradiatorRecipeViewer extends ProcessorRecipeViewer<IrradiatorRecipe> {
        public IrradiatorRecipeViewer(IrradiatorRecipe recipe) {
            super(recipe, "irradiator");
        }
    }

    public static class OreLeacherRecipeViewer extends ProcessorRecipeViewer<OreLeacherRecipe> {
        public OreLeacherRecipeViewer(OreLeacherRecipe recipe) {
            super(recipe, "ore_leacher");
        }
    }
}