package lach_01298.qmd.recipe_viewer;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.handler.MenuInfoBuilder;
import com.nred.nuclearcraft.util.MinMax;
import lach_01298.qmd.recipe.QMDRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeViewerQMDCategoryInfoBuilder<RECIPE extends QMDRecipe> extends MenuInfoBuilder<RecipeViewerQMDCategoryInfoBuilder<RECIPE>> {
    public List<int[]> particleOutputGuiXYWH = new ArrayList<>();
    public List<int[]> particleInputGuiXYWH = new ArrayList<>();

    public RecipeViewerQMDCategoryInfoBuilder(String name) {
        super(name);
    }

    public RecipeViewerQMDCategoryInfo<RECIPE> buildCategoryInfo() {
        return new RecipeViewerQMDCategoryInfo<>(this);
    }

    @Override
    public RecipeViewerQMDCategoryInfoBuilder<RECIPE> setRecipeHandlerName(String recipeHandlerName) {
        this.recipeViewerTexture = "qmd:textures/gui/sprites/recipe_viewer/" + recipeHandlerName + ".png";
        return this.getThis();
    }

    @Override
    protected void autoRecipeViewerBackgroundXY() {
        MinMax.MinMaxInt minMaxCenterX = new MinMax.MinMaxInt();
        MinMax.MinMaxInt minMaxCenterY = new MinMax.MinMaxInt();

        for(List<int[]> xywhList : Arrays.asList(this.itemInputGuiXYWH, this.fluidInputGuiXYWH, this.itemOutputGuiXYWH, this.fluidOutputGuiXYWH, this.particleInputGuiXYWH, this.particleOutputGuiXYWH)) {
            for(int[] xywh : xywhList) {
                minMaxCenterX.update(xywh[0] - 4);
                minMaxCenterY.update(xywh[1] - 4);
                minMaxCenterX.update(xywh[0] + xywh[2] + 4);
                minMaxCenterY.update(xywh[1] + xywh[3] + 4);
            }
        }

        this.recipeViewerBackgroundXYWH = new int[]{minMaxCenterX.getMin(), minMaxCenterY.getMin(), minMaxCenterX.getDiff(), minMaxCenterY.getDiff()};
    }

    public RecipeViewerQMDCategoryInfoBuilder<RECIPE> setParticleInputSlots(int[]... slots) {
        this.particleInputGuiXYWH = Lists.newArrayList(slots);
        this.autoRecipeViewerBackgroundXY();
        return this.getThis();
    }

    public RecipeViewerQMDCategoryInfoBuilder<RECIPE> setParticleOutputSlots(int[]... slots) {
        this.particleOutputGuiXYWH = Lists.newArrayList(slots);
        this.autoRecipeViewerBackgroundXY();
        return this.getThis();
    }
}
