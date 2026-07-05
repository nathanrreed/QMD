package lach_01298.qmd.datagen.recipe;

import com.nred.nuclearcraft.recipe.ProcessorRecipeBuilder;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.recipe.types.IrradiatorRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

import static com.nred.nuclearcraft.registration.BlockRegistration.GLOWING_MUSHROOM;

public class IrradiatorProvider {
    public IrradiatorProvider(RecipeOutput recipeOutput) {
        new ProcessorRecipeBuilder(IrradiatorRecipe.class, 1.0, 0.0).addItemInput(Items.ROTTEN_FLESH, 1).addItemResult(QMDItems.flesh, 1).save(recipeOutput);
        new ProcessorRecipeBuilder(IrradiatorRecipe.class, 4.0, 0.0).addItemInput(Items.BROWN_MUSHROOM, 1).addItemResult(GLOWING_MUSHROOM, 1).save(recipeOutput, "glowing_mushroom_from_brown");
        new ProcessorRecipeBuilder(IrradiatorRecipe.class, 4.0, 0.0).addItemInput(Items.RED_MUSHROOM, 1).addItemResult(GLOWING_MUSHROOM, 1).save(recipeOutput, "glowing_mushroom_from_red");
        new ProcessorRecipeBuilder(IrradiatorRecipe.class, 1.0, 0.0).addItemInput(Items.SHORT_GRASS, 1).addItemResult(Items.DEAD_BUSH, 1).save(recipeOutput);
        new ProcessorRecipeBuilder(IrradiatorRecipe.class, 1.0, 0.0).addItemInput(Items.MOSSY_COBBLESTONE, 1).addItemResult(Items.COBBLESTONE, 1).save(recipeOutput);
        new ProcessorRecipeBuilder(IrradiatorRecipe.class, 1.0, 0.0).addItemInput(Items.MOSSY_STONE_BRICKS, 1).addItemResult(Items.STONE_BRICKS, 1).save(recipeOutput);
        new ProcessorRecipeBuilder(IrradiatorRecipe.class, 1.0, 0.0).addItemInput(Items.MOSSY_COBBLESTONE_WALL, 1).addItemResult(Items.COBBLESTONE_WALL, 1).save(recipeOutput);
    }
}