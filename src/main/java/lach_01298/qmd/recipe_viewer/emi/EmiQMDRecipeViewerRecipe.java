package lach_01298.qmd.recipe_viewer.emi;

import com.nred.nuclearcraft.compat.emi.ModEmiPlugin;
import com.nred.nuclearcraft.compat.emi.part.TankWithAmount;
import com.nred.nuclearcraft.compat.recipe_viewer.info.RecipeViewerCategoryInfo;
import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import com.nred.nuclearcraft.util.NCMath;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe_viewer.QMDRecipeViewerImpl.RecipeViewer;
import lach_01298.qmd.recipe_viewer.RecipeViewerQMDCategoryInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public abstract class EmiQMDRecipeViewerRecipe extends BasicEmiRecipe {
    public final RecipeViewerCategoryInfo categoryInfo;
    private final RecipeViewer<?> recipeViewer;

    public EmiQMDRecipeViewerRecipe(String name, EmiRecipeCategory category, ResourceLocation id, RecipeViewer<? extends QMDRecipe> recipeViewer) {
        super(category, id, 0, 0);
        this.recipeViewer = recipeViewer;
        this.categoryInfo = BlockEntityInfoHandler.RECIPE_VIEWER_CATEGORY_INFO_MAP.get(name);
        QMDRecipe recipe = recipeViewer.recipe;
        if (!recipe.getItemIngredients().isEmpty() && recipe.getItemIngredient().count() > 0) {
            this.inputs.addAll(recipe.getItemIngredients().stream().map(ModEmiPlugin::getEmiItemIngredient).toList());
        }

        if (!recipe.getFluidIngredients().isEmpty() && recipe.getFluidIngredient().amount() > 0) {
            this.inputs.addAll(recipe.getFluidIngredients().stream().map(ModEmiPlugin::getEmiFluidIngredient).toList());
        }

        if (!recipe.getItemProducts().isEmpty() && recipe.getItemProduct().count() > 0) {
            if (recipe.isSpecial()) {
                this.outputs.add(EmiStack.of(recipe.getResultItem((HolderLookup.Provider) null)));
            } else {
                this.outputs.addAll(recipe.getItemProducts().stream().map(ModEmiPlugin::getEmiItemStack).toList());
            }
        }

        if (!recipe.getFluidProducts().isEmpty() && recipe.getFluidProduct().amount() > 0) {
            this.outputs.addAll(recipe.getFluidProducts().stream().map(ModEmiPlugin::getEmiFluidStack).toList());
        }

        if (!recipe.getParticleIngredients().isEmpty()) {
            this.inputs.addAll(recipe.getParticleIngredients().stream().map(ParticleEmiStack::new).toList());
        }
        if (!recipe.getParticleProducts().isEmpty()) {
            this.outputs.addAll(recipe.getParticleProducts().stream().map(ParticleEmiStack::new).toList());
        }
    }

    public int getDisplayWidth() {
        return this.categoryInfo.getRecipeViewerBackgroundW();
    }

    public int getDisplayHeight() {
        return this.categoryInfo.getRecipeViewerBackgroundH();
    }

    public void addWidgets(WidgetHolder widgets) {
        int backgroundX = this.categoryInfo.getRecipeViewerBackgroundX();
        int backgroundY = this.categoryInfo.getRecipeViewerBackgroundY();
        ResourceLocation texture = ResourceLocation.parse(this.categoryInfo.getRecipeViewerTexture());
        widgets.addTexture(texture, 0, 0, this.categoryInfo.getRecipeViewerBackgroundW(), this.categoryInfo.getRecipeViewerBackgroundH(), this.categoryInfo.getRecipeViewerBackgroundX(), this.categoryInfo.getRecipeViewerBackgroundY());

        int i = 0;
        int f = 0;
        int p = 0;
        List<int[]> itemInputStackXY = this.categoryInfo.getItemInputStackXY();
        List<int[]> fluidInputStackXYWH = this.categoryInfo.getFluidInputGuiXYWH();
        List<int[]> particleInputStackXYWH = ((RecipeViewerQMDCategoryInfo<?>) this.categoryInfo).getParticleInputStackXY();
        for (EmiIngredient input : this.inputs) {
            if (input.getEmiStacks().getFirst().getKey() instanceof Particle) {
                int[] stackXYWH = particleInputStackXYWH.get(p++);
                widgets.addSlot(input, stackXYWH[0] - backgroundX - 1, stackXYWH[1] - backgroundY - 1).drawBack(false);
            } else if (input.getEmiStacks().getFirst().getKey() instanceof Fluid) {
                int[] stackXYWH = fluidInputStackXYWH.get(f++);
                widgets.add(new TankWithAmount(input, stackXYWH[0] - backgroundX - 1, stackXYWH[1] - backgroundY - 1)).drawBack(false);
            } else {
                int[] stackXY = itemInputStackXY.get(i++);
                widgets.addSlot(input, stackXY[0] - backgroundX - 1, stackXY[1] - backgroundY - 1).drawBack(false);
            }
        }

        i = 0;
        f = 0;
        p = 0;
        List<int[]> itemOutputStackXY = this.categoryInfo.getItemOutputStackXY();
        List<int[]> fluidOutputStackXYWH = this.categoryInfo.getFluidOutputGuiXYWH();
        List<int[]> particleOutputStackXY = ((RecipeViewerQMDCategoryInfo<?>) this.categoryInfo).getParticleOutputStackXY();
        for (EmiStack output : this.outputs) {
            if (output.getEmiStacks().getFirst().getKey() instanceof Particle) {
                int[] stackXYWH = particleOutputStackXY.get(p++);
                widgets.addSlot(output, stackXYWH[0] - backgroundX - 1, stackXYWH[1] - backgroundY - 1).drawBack(false);
            } else if (output.getKey() instanceof Fluid) {
                SizedChanceFluidIngredient ingredient = this.recipeViewer.recipe.getFluidProducts().get(f);
                int[] stackXYWH = fluidOutputStackXYWH.get(f++);
                SlotWidget slot = widgets.add(new TankWithAmount(output, stackXYWH[0] - backgroundX - 1, stackXYWH[1] - backgroundY - 1, stackXYWH[2] + 2, stackXYWH[3] + 2)).drawBack(false).recipeContext(this);
                if (ingredient.chancePercent() != 100) {
                    slot.appendTooltip(Component.translatable("nuclearcraftneohaul.recipe_viewer.chance_output", new Object[]{ingredient.minStackSize(), ingredient.amount(), NCMath.decimalPlaces(ingredient.getMeanStackSize(), 2)}));
                }
            } else {
                SizedChanceItemIngredient ingredient = this.recipeViewer.recipe.getItemProducts().get(i);
                int[] stackXY = itemOutputStackXY.get(i++);
                SlotWidget slot = widgets.addSlot(output, stackXY[0] - backgroundX - 1, stackXY[1] - backgroundY - 1).drawBack(false).recipeContext(this);
                if (ingredient.chancePercent() != 100) {
                    slot.appendTooltip(Component.translatable("nuclearcraftneohaul.recipe_viewer.chance_output", new Object[]{ingredient.minStackSize(), ingredient.count(), NCMath.decimalPlaces(ingredient.getMeanStackSize(), 2)}));
                }
            }
        }

        widgets.addAnimatedTexture(texture, this.categoryInfo.getProgressBarGuiX() - this.categoryInfo.getRecipeViewerBackgroundX(), this.categoryInfo.getProgressBarGuiY() - this.categoryInfo.getRecipeViewerBackgroundY(), this.categoryInfo.getProgressBarGuiW(), this.categoryInfo.getProgressBarGuiH(), this.categoryInfo.getProgressBarGuiU(), this.categoryInfo.getProgressBarGuiV(), this.getProgressTime(), true, false, false).tooltipText(this::progressTooltips);
    }

    public List<Component> progressTooltips(int x, int y) {
        return this.recipeViewer.progressTooltips(x, y);
    }

    protected int getProgressTime() {
        return Mth.clamp(this.recipeViewer.getProgressArrowTime() * 50, 500, 10000);
    }
}
