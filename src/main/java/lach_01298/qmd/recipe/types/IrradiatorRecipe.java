package lach_01298.qmd.recipe.types;

import com.nred.nuclearcraft.recipe.ProcessorRecipe;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.IRRADIATOR_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.IRRADIATOR_RECIPE_TYPE;

public class IrradiatorRecipe extends ProcessorRecipe {
    public IrradiatorRecipe(List<SizedChanceItemIngredient> itemInputs, List<SizedChanceItemIngredient> itemResults, List<SizedChanceFluidIngredient> fluidInputs, List<SizedChanceFluidIngredient> fluidResults, double timeModifier, double powerModifier, double radiation) {
        super(itemInputs, itemResults, fluidInputs, fluidResults, timeModifier, powerModifier, radiation);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IRRADIATOR_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return IRRADIATOR_RECIPE_TYPE.get();
    }
}