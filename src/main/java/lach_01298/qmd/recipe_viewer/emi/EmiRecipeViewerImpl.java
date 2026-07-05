package lach_01298.qmd.recipe_viewer.emi;

import com.nred.nuclearcraft.compat.emi.EmiRecipeViewerRecipe;
import lach_01298.qmd.recipe.types.IrradiatorRecipe;
import lach_01298.qmd.recipe.types.OreLeacherRecipe;
import lach_01298.qmd.recipe_viewer.RecipeViewerImpl.IrradiatorRecipeViewer;
import lach_01298.qmd.recipe_viewer.RecipeViewerImpl.OreLeacherRecipeViewer;
import net.minecraft.resources.ResourceLocation;

import static lach_01298.qmd.recipe_viewer.emi.QMDEmiPlugin.EMI_IRRADIATOR_CATEGORY;
import static lach_01298.qmd.recipe_viewer.emi.QMDEmiPlugin.EMI_ORE_LEACHER_CATEGORY;

public class EmiRecipeViewerImpl {
    public static class EmiIrradiatorRecipe extends EmiRecipeViewerRecipe {
        public EmiIrradiatorRecipe(ResourceLocation id, IrradiatorRecipe recipe) {
            super("irradiator", EMI_IRRADIATOR_CATEGORY, id, new IrradiatorRecipeViewer(recipe));
        }
    }

    public static class EmiOreLeacherRecipe extends EmiRecipeViewerRecipe {
        public EmiOreLeacherRecipe(ResourceLocation id, OreLeacherRecipe recipe) {
            super("ore_leacher", EMI_ORE_LEACHER_CATEGORY, id, new OreLeacherRecipeViewer(recipe));
        }
    }
}