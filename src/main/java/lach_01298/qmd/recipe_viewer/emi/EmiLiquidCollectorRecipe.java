package lach_01298.qmd.recipe_viewer.emi;

import com.nred.nuclearcraft.compat.emi.part.TankWithAmount;
import dev.emi.emi.api.widget.WidgetHolder;
import lach_01298.qmd.QMD;
import lach_01298.qmd.recipe.types.LiquidCollectorRecipe;
import net.minecraft.resources.ResourceLocation;

import static lach_01298.qmd.recipe_viewer.emi.QMDEmiPlugin.EMI_LIQUID_COLLECTOR_CATEGORY;

public class EmiLiquidCollectorRecipe extends EmiFluidCollectorRecipe {
    public EmiLiquidCollectorRecipe(ResourceLocation id, LiquidCollectorRecipe recipe) {
        super(EMI_LIQUID_COLLECTOR_CATEGORY, id, recipe, 100, 60);
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "textures/gui/jei/liquid_collector.png"), 0, 0, 100, 60, 0, 0);

        widgets.add(new TankWithAmount(this.outputs.getFirst(), 73, 7)).drawBack(false);

        widgets.addTooltip(getBlocks(), 21, 7, 18, 18);
        widgets.addTooltip(getBiomes(), 32, 29, 18, 18);
        widgets.addTooltip(getDimensions(), 10, 29, 18, 18);
    }
}