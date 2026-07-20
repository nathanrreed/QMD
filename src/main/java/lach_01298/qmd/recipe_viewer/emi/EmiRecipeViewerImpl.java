package lach_01298.qmd.recipe_viewer.emi;

import com.nred.nuclearcraft.NuclearcraftNeohaul;
import com.nred.nuclearcraft.compat.emi.EmiRecipeViewerRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.WidgetHolder;
import lach_01298.qmd.recipe.types.*;
import lach_01298.qmd.recipe_viewer.QMDRecipeViewerImpl.*;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.awt.*;
import java.text.DecimalFormat;
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

    public static class EmiTargetChamberRecipe extends EmiQMDRecipeViewerRecipe {
        public EmiTargetChamberRecipe(ResourceLocation id, TargetChamberRecipe recipe) {
            super("target_chamber", EMI_TARGET_CHAMBER_CATEGORY, id, new TargetChamberRecipeViewer(recipe));
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            super.addWidgets(widgets);
            TargetChamberRecipe recipe = (TargetChamberRecipe) recipeViewer.recipe;
            Component rangeString = Component.translatable("gui.qmd.recipe_viewer.reaction.range", Units.getSIFormat(recipe.getParticleIngredient().getMeanEnergy(), 3, "eV") + "-" + Units.getSIFormat(recipe.getMaxEnergy(), 3, "eV"));

            DecimalFormat df = new DecimalFormat("#.##");
            Component crossSectionString = Component.translatable("gui.qmd.recipe_viewer.reaction.cross_section", df.format(recipe.getCrossSection() * 100));
            Component energyReleasedString = Component.translatable("gui.qmd.recipe_viewer.reaction.energy_released", Units.getParticleEnergy(recipe.getEnergyReleased()));

            widgets.addText(rangeString, 0, 85, Color.gray.getRGB(), false);
            widgets.addText(crossSectionString, 0, 95, Color.gray.getRGB(), false);
            widgets.addText(energyReleasedString, 0, 105, Color.gray.getRGB(), false);
        }
    }

    public static class EmiDecayChamberRecipe extends EmiQMDRecipeViewerRecipe {
        public EmiDecayChamberRecipe(ResourceLocation id, DecayChamberRecipe recipe) {
            super("decay_chamber", EMI_DECAY_CHAMBER_CATEGORY, id, new DecayChamberRecipeViewer(recipe));
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            super.addWidgets(widgets);
            DecayChamberRecipe recipe = (DecayChamberRecipe) recipeViewer.recipe;

            DecimalFormat df = new DecimalFormat("#.##");
            Component maxEnergyString = Component.translatable("gui.qmd.recipe_viewer.reaction.max_energy", Units.getSIFormat(recipe.getMaxEnergy(), 3, "eV"));
            Component crossSectionString = Component.translatable("gui.qmd.recipe_viewer.reaction.cross_section", df.format(recipe.getCrossSection() * 100));
            Component energyReleasedString = Component.translatable("gui.qmd.recipe_viewer.reaction.energy_released", Units.getParticleEnergy(recipe.getEnergyReleased()));

            widgets.addText(crossSectionString, 0, 72, Color.gray.getRGB(), false);
            widgets.addText(energyReleasedString, 0, 82, Color.gray.getRGB(), false);

            if (recipe.getMaxEnergy() != Long.MAX_VALUE) {
                widgets.addText(maxEnergyString, 0, 92, Color.gray.getRGB(), false);
            }
        }
    }

    public static class EmiBeamDumpRecipe extends EmiQMDRecipeViewerRecipe {
        public EmiBeamDumpRecipe(ResourceLocation id, BeamDumpRecipe recipe) {
            super("beam_dump", EMI_BEAM_DUMP_CATEGORY, id, new BeamDumpRecipeViewer(recipe));
        }
    }

    public static class EmiCollisionChamberRecipe extends EmiQMDRecipeViewerRecipe {
        public EmiCollisionChamberRecipe(ResourceLocation id, CollisionChamberRecipe recipe) {
            super("collision_chamber", EMI_COLLISION_CHAMBER_CATEGORY, id, new CollisionChamberRecipeViewer(recipe));
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            super.addWidgets(widgets);
            CollisionChamberRecipe recipe = (CollisionChamberRecipe) recipeViewer.recipe;

            DecimalFormat df = new DecimalFormat("#.###");

            Component rangeString = Component.translatable("gui.qmd.recipe_viewer.reaction.range", Units.getSIFormat(recipe.getParticleIngredient().getMeanEnergy(), 3, "eV") + "-" + Units.getSIFormat(recipe.getMaxEnergy(), 3, "eV"));
            Component crossSectionString = Component.translatable("gui.qmd.recipe_viewer.reaction.cross_section", df.format(recipe.getCrossSection() * 100));
            Component energyReleasedString = Component.translatable("gui.qmd.recipe_viewer.reaction.energy_released", Units.getParticleEnergy(recipe.getEnergyReleased()));

            widgets.addText(rangeString, 0, 82, Color.gray.getRGB(), false);
            widgets.addText(crossSectionString, 0, 92, Color.gray.getRGB(), false);
            widgets.addText(energyReleasedString, 0, 102, Color.gray.getRGB(), false);
        }
    }
}