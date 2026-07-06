package lach_01298.qmd.recipe_viewer.emi;

import com.nred.nuclearcraft.compat.emi.part.TankWithAmount;
import dev.emi.emi.api.widget.WidgetHolder;
import lach_01298.qmd.QMD;
import lach_01298.qmd.recipe.types.AtmosphereCollectorRecipe;
import net.minecraft.resources.ResourceLocation;

import static lach_01298.qmd.recipe_viewer.emi.QMDEmiPlugin.EMI_ATMOSPHERE_COLLECTOR_CATEGORY;

public class EmiAtmosphereCollectorRecipe extends EmiFluidCollectorRecipe {
    public EmiAtmosphereCollectorRecipe(ResourceLocation id, AtmosphereCollectorRecipe recipe) {
        super(EMI_ATMOSPHERE_COLLECTOR_CATEGORY, id, recipe, 100, 40);
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "textures/gui/jei/atmosphere_collector.png"), 0, 0, 100, 40, 0, 0);

        widgets.add(new TankWithAmount(this.outputs.getFirst(), 73, 7)).drawBack(false);

        widgets.addTooltip(getBiomes(), 25, 8, 18, 18);
        widgets.addTooltip(getDimensions(), 3, 8, 18, 18);
    }
}