package lach_01298.qmd.recipe_viewer.emi;

import com.nred.nuclearcraft.util.Lang;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiStack;
import lach_01298.qmd.recipe.types.FluidCollectorRecipe;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class EmiFluidCollectorRecipe extends BasicEmiRecipe {
    @NotNull
    private final FluidCollectorRecipe recipe;

    public EmiFluidCollectorRecipe(EmiRecipeCategory category, ResourceLocation id, FluidCollectorRecipe recipe, int width, int height) {
        super(category, id, width, height);
        this.recipe = recipe;
        this.outputs.add(EmiStack.of(recipe.getOutputFluid().getFluid(), recipe.getOutputFluid().getAmount()));
    }

    protected List<ClientTooltipComponent> getBlocks() {
        List<ClientTooltipComponent> blockComponents;
        if (recipe.getBlocks().isEmpty()) {
            blockComponents = List.of(EmiTooltipComponents.of(Component.translatable("gui.qmd.recipe_viewer.collector.blocks", Component.translatable("gui.qmd.recipe_viewer.collector.any"))));
        } else {
            ArrayList<String> blockList = new ArrayList<>();
            for (ResourceLocation block : recipe.getBlocks()) {
                blockList.add(Lang.localize(BuiltInRegistries.BLOCK.get(block).getDescriptionId()));
            }
            blockComponents = List.of(EmiTooltipComponents.of(Component.translatable("gui.qmd.recipe_viewer.collector.blocks", Component.literal(String.join(", ", blockList)))));
        }
        return blockComponents;
    }

    protected List<ClientTooltipComponent> getBiomes() {
        List<ClientTooltipComponent> biomeComponents;
        if (recipe.getBiomes().isEmpty()) {
            biomeComponents = List.of(EmiTooltipComponents.of(Component.translatable("gui.qmd.recipe_viewer.collector.biomes", Component.translatable("gui.qmd.recipe_viewer.collector.any"))));
        } else {
            ArrayList<String> biomeList = new ArrayList<>();
            for (ResourceLocation biome : recipe.getBiomes()) {
                if (biome.getNamespace().equals("minecraft")) {
                    biomeList.add(biome.getPath());
                } else {
                    biomeList.add(biome.toString());
                }
            }
            biomeComponents = List.of(EmiTooltipComponents.of(Component.translatable("gui.qmd.recipe_viewer.collector.biomes", Component.literal(String.join(", ", biomeList)))));
        }
        return biomeComponents;
    }

    protected List<ClientTooltipComponent> getDimensions() {
        List<ClientTooltipComponent> dimensionComponents;
        if (recipe.getDimensions().isEmpty()) {
            dimensionComponents = List.of(EmiTooltipComponents.of(Component.translatable("gui.qmd.recipe_viewer.collector.dimensions", Component.translatable("gui.qmd.recipe_viewer.collector.any"))));
        } else {
            ArrayList<String> dimensionList = new ArrayList<>();
            for (ResourceLocation dimension : recipe.getDimensions()) {
                if (dimension.getNamespace().equals("minecraft")) {
                    dimensionList.add(dimension.getPath());
                } else {
                    dimensionList.add(dimension.toString());
                }
            }
            dimensionComponents = List.of(EmiTooltipComponents.of(Component.translatable("gui.qmd.recipe_viewer.collector.dimensions", Component.literal(String.join(", ", dimensionList)))));
        }
        return dimensionComponents;
    }
}