package lach_01298.qmd.recipe_viewer.emi;

import com.nred.nuclearcraft.NuclearcraftNeohaul;
import com.nred.nuclearcraft.compat.emi.EmiRecipeViewerRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.WidgetHolder;
import lach_01298.qmd.recipe.types.*;
import lach_01298.qmd.recipe_viewer.QMDRecipeViewerImpl.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Objects;

import static lach_01298.qmd.datamap.QMDDatamaps.IRRADIATOR_FUELS;
import static lach_01298.qmd.recipe_viewer.emi.QMDEmiPlugin.*;

public class EmiRecipeViewerImpl {
    public static class EmiIrradiatorRecipe extends EmiRecipeViewerRecipe {
        public EmiIrradiatorRecipe(ResourceLocation id, IrradiatorRecipe recipe) {
            super("irradiator", EMI_IRRADIATOR_CATEGORY, id, new IrradiatorRecipeViewer(recipe));
            inputs.add(EmiIngredient.of(Ingredient.of(BuiltInRegistries.ITEM.getDataMap(IRRADIATOR_FUELS).keySet().stream().map((e) -> Objects.requireNonNull(BuiltInRegistries.ITEM.get(e))).toArray(Item[]::new))));
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            super.addWidgets(widgets);

            widgets.addAnimatedTexture(ResourceLocation.parse(this.categoryInfo.getRecipeViewerTexture()), categoryInfo.getProgressBarGuiX() + 6 - this.categoryInfo.getRecipeViewerBackgroundX(), this.categoryInfo.getProgressBarGuiY() - this.categoryInfo.getRecipeViewerBackgroundY() - 20, 40, 19, this.categoryInfo.getProgressBarGuiU(), this.categoryInfo.getProgressBarGuiV() + categoryInfo.getProgressBarGuiH(), this.getProgressTime(), false, false, false).tooltipText(this::getFuelTooltip);
        }

        private List<Component> getFuelTooltip(int x, int y) {
            int currentItem = (int) (System.currentTimeMillis() / 1000 % inputs.get(1).getEmiStacks().size());
            return List.of(Component.translatable(NuclearcraftNeohaul.MODID + ".tooltip.speed_multiplier", BuiltInRegistries.ITEM.wrapAsHolder((Item) inputs.get(1).getEmiStacks().get(currentItem).getKey()).getData(IRRADIATOR_FUELS).speedMultiplier()).withStyle(ChatFormatting.AQUA));
        }
    }

    public static class EmiOreLeacherRecipe extends EmiRecipeViewerRecipe {
        public EmiOreLeacherRecipe(ResourceLocation id, OreLeacherRecipe recipe) {
            super("ore_leacher", EMI_ORE_LEACHER_CATEGORY, id, new OreLeacherRecipeViewer(recipe));
        }
    }

    public static class EmiAcceleratorCoolingRecipe extends EmiQMDRecipeViewerRecipe {
        public EmiAcceleratorCoolingRecipe(ResourceLocation id, AcceleratorCoolingRecipe recipe) {
            super("accelerator_cooling", EMI_ACCELERATOR_COOLING_CATEGORY, id, new AcceleratorCoolingRecipeViewer(recipe));
        }
    }

    public static class EmiAcceleratorSourceRecipe extends EmiQMDRecipeViewerRecipe {
        public EmiAcceleratorSourceRecipe(ResourceLocation id, AcceleratorSourceRecipe recipe) {
            super("accelerator_source", EMI_ACCELERATOR_SOURCE_CATEGORY, id, new AcceleratorSourceRecipeViewer(recipe));
        }
    }

    public static class EmiMassSpectrometerRecipe extends EmiQMDRecipeViewerRecipe {
        public EmiMassSpectrometerRecipe(ResourceLocation id, MassSpectrometerRecipe recipe) {
            super("mass_spectrometer", EMI_MASS_SPECTROMETER_CATEGORY, id, new MassSpectrometerRecipeViewer(recipe));
        }
    }
}