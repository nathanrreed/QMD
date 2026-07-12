package lach_01298.qmd.recipe;

import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.recipe.IngredientSorption;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lach_01298.qmd.QMD;
import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public abstract class AbstractQMDRecipeHandler<RECIPE extends QMDRecipe> {
    protected List<RECIPE> recipeList = Collections.emptyList();

    public abstract String getName();

    public static final IntList INVALID = new IntArrayList(new int[]{-1});

    public List<RECIPE> getRecipeList(RecipeManager recipeManager) {
        if (recipeList.isEmpty())
            setRecipes(recipeManager);
        return recipeList;
    }

    public @Nullable QMDRecipeInfo<RECIPE> getRecipeInfoFromInputs(Level level, List<ItemStack> itemInputs, List<Tank> fluidInputs, List<ParticleStack> particleInputs) { // TODO readd caching
        List<SizedChanceItemIngredient> itemIngredients = itemInputs.stream().map(itemStack -> SizedChanceItemIngredient.of(itemStack.getItem(), itemStack.getCount())).toList(); // Tries without filtering
        List<SizedChanceFluidIngredient> fluidIngredients = fluidInputs.stream().map(tank -> tank.isEmpty() ? SizedChanceFluidIngredient.EMPTY : SizedChanceFluidIngredient.of(tank.getFluid())).toList();
        RECIPE recipe = getRecipeFromIngredients(level, itemIngredients, fluidIngredients, particleInputs);
        if (recipe == null) { // Retries after filtering out empty ingredients
            itemIngredients = itemInputs.stream().filter(itemStack -> !itemStack.isEmpty()).map(itemStack -> SizedChanceItemIngredient.of(itemStack.getItem(), itemStack.getCount())).toList();
            fluidIngredients = fluidInputs.stream().filter(tank -> !tank.isEmpty()).map(tank -> tank.isEmpty() ? new SizedChanceFluidIngredient(FluidIngredient.empty(), 1) : SizedChanceFluidIngredient.of(tank.getFluid())).toList();
            recipe = getRecipeFromIngredients(level, itemIngredients, fluidIngredients, particleInputs);
        }

        if (recipe == null)
            return null;

        return new QMDRecipeInfo<>(recipe, QMDRecipeHelper.matchIngredients(IngredientSorption.INPUT, recipe.itemIngredients, recipe.fluidIngredients, recipe.particleIngredients, itemIngredients, fluidIngredients, particleInputs, recipe));
    }

    public static @Nullable QMDRecipe getRecipeFromIngredients(Level level, RecipeType<? extends QMDRecipe> recipeType, List<SizedChanceItemIngredient> itemIngredients, List<SizedChanceFluidIngredient> fluidIngredients, List<ParticleStack> particleStacks) {
        List<? extends RecipeHolder<? extends QMDRecipe>> recipes = level.getRecipeManager().getRecipesFor(recipeType, new QMDRecipeInput(itemIngredients, fluidIngredients, particleStacks), level);
        assert recipes.size() <= 1; // Make sure there is no overlapping recipes
        return recipes.stream().findFirst().map(RecipeHolder::value).orElse(null);
    }

    @SuppressWarnings("unchecked")
    public @Nullable RECIPE getRecipeFromIngredients(Level level, List<SizedChanceItemIngredient> itemIngredients, List<SizedChanceFluidIngredient> fluidIngredients, List<ParticleStack> particleStacks) {
        return (RECIPE) getRecipeFromIngredients(level, getRecipeType(), itemIngredients, fluidIngredients, particleStacks);
    }

    @SuppressWarnings("unchecked")
    public RecipeType<RECIPE> getRecipeType() {
        return (RecipeType<RECIPE>) BuiltInRegistries.RECIPE_TYPE.get(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, getName()));
    }

    public void setRecipes(@NotNull RecipeManager recipeManager) {
        if (recipeList.isEmpty()) {
            recipeList = recipeManager.getAllRecipesFor(getRecipeType()).stream().map(RecipeHolder::value).toList();
        }
    }

    public void init(RecipeManager recipeManager) {
        setRecipes(recipeManager);
    }

    public void postInit(RecipeManager recipeManager) {
    }

    public void postReload(RecipeManager recipeManager) {
        recipeList = Collections.emptyList(); // Void the list
        postInit(recipeManager);
    }
}
