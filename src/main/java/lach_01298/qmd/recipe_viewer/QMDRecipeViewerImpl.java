package lach_01298.qmd.recipe_viewer;

import com.nred.nuclearcraft.NuclearcraftNeohaul;
import com.nred.nuclearcraft.compat.recipe_viewer.RecipeViewerImpl.ProcessorRecipeViewer;
import com.nred.nuclearcraft.util.UnitHelper;
import lach_01298.qmd.QMDConstants;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.types.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public class QMDRecipeViewerImpl {
    public abstract static class RecipeViewer<R extends QMDRecipe> {
        public final R recipe;

        public RecipeViewer(R recipe) {
            this.recipe = recipe;
        }

        public abstract List<Component> progressTooltips(int var1, int var2);

        public abstract int getProgressArrowTime();

        public int[] getProgressArrowUVWH(int arrowU, int arrowV, int arrowW, int arrowH) {
            return new int[]{arrowU, arrowV, arrowW, arrowH};
        }
    }

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

    public static class AcceleratorCoolingRecipeViewer extends RecipeViewer<AcceleratorCoolingRecipe> {
        public AcceleratorCoolingRecipeViewer(AcceleratorCoolingRecipe recipe) {
            super(recipe);
        }

        @Override
        public int getProgressArrowTime() {
            return Math.max((recipe.getHeatRequired() / 100), 5);
        }

        @Override
        public List<Component> progressTooltips(int var1, int var2) {
            return List.of(
                    Component.translatable("qmd.gui.recipe_viewer.accelerator_cooling.heat_required", Component.literal(recipe.getHeatRequired() + " H").withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.YELLOW),
                    Component.translatable("qmd.gui.recipe_viewer.accelerator_cooling.temperature", Component.literal(recipe.getCoolantTemperature() + " K").withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.AQUA)
            );
        }
    }

    public static class AcceleratorSourceRecipeViewer extends RecipeViewer<AcceleratorSourceRecipe> {
        public AcceleratorSourceRecipeViewer(AcceleratorSourceRecipe recipe) {
            super(recipe);
        }

        @Override
        public int getProgressArrowTime() {
            return 0;
        }

        @Override
        public List<Component> progressTooltips(int var1, int var2) {
            return List.of();
        }
    }

    public static class MassSpectrometerRecipeViewer extends RecipeViewer<MassSpectrometerRecipe> {
        public MassSpectrometerRecipeViewer(MassSpectrometerRecipe recipe) {
            super(recipe);
        }

        @Override
        public int getProgressArrowTime() {
            return (int) recipe.getBaseProcessTime(QMDServerConfig.processor_time[2]);
        }

        @Override
        public List<Component> progressTooltips(int var1, int var2) {
            return List.of(
                    Component.translatable(NuclearcraftNeohaul.MODID + ".tooltip.process_time", Component.literal(UnitHelper.applyTimeUnitShort(recipe.getBaseProcessTime(QMDServerConfig.processor_time[2]), 3)).withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GREEN)
            );
        }
    }

    public static class TargetChamberRecipeViewer extends RecipeViewer<TargetChamberRecipe> {
        public TargetChamberRecipeViewer(TargetChamberRecipe recipe) {
            super(recipe);
        }

        @Override
        public int getProgressArrowTime() {
            return Math.max(recipe.getParticleIngredient().getAmount() / (QMDConstants.ionSourceOutput * 10), 5);
        }

        @Override
        public List<Component> progressTooltips(int var1, int var2) {
            return List.of();
        }
    }

    public static class DecayChamberRecipeViewer extends RecipeViewer<DecayChamberRecipe> {
        public DecayChamberRecipeViewer(DecayChamberRecipe recipe) {
            super(recipe);
        }

        @Override
        public int getProgressArrowTime() {
            return 0;
        }

        @Override
        public List<Component> progressTooltips(int var1, int var2) {
            return List.of();
        }
    }

    public static class BeamDumpRecipeViewer extends RecipeViewer<BeamDumpRecipe> {
        public BeamDumpRecipeViewer(BeamDumpRecipe recipe) {
            super(recipe);
        }

        @Override
        public int getProgressArrowTime() {
            return Math.max(recipe.getParticleIngredient().getAmount() / Math.max(QMDConstants.ionSourceOutput / 200, 1), 5);

        }

        @Override
        public List<Component> progressTooltips(int var1, int var2) {
            return List.of();
        }
    }

    public static class CollisionChamberRecipeViewer extends RecipeViewer<CollisionChamberRecipe> {
        public CollisionChamberRecipeViewer(CollisionChamberRecipe recipe) {
            super(recipe);
        }

        @Override
        public int getProgressArrowTime() {
            return 0;
        }

        @Override
        public List<Component> progressTooltips(int var1, int var2) {
            return List.of();
        }
    }
}