package lach_01298.qmd.recipe;

import com.nred.nuclearcraft.recipe.*;
import com.nred.nuclearcraft.util.NCUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static com.nred.nuclearcraft.recipe.RecipeHelper.matchFluidIngredient;
import static com.nred.nuclearcraft.recipe.RecipeHelper.matchIngredient;

public abstract class QMDRecipeHandler<RECIPE extends QMDRecipe> extends AbstractQMDRecipeHandler<RECIPE> implements IRecipeHandler {
    private final String name;
    public final int itemInputSize, fluidInputSize, particleInputSize, itemOutputSize, fluidOutputSize, particleOutputSize;
    public final int itemInputLastIndex, fluidInputLastIndex, particleInputLastIndex, itemOutputLastIndex, fluidOutputLastIndex, particleOutputLastIndex;
    public final boolean isShapeless;
    private final List<Set<ResourceLocation>> validFluids = new ArrayList<>();

    public QMDRecipeHandler(@Nonnull String recipeName, int itemInputSize, int fluidInputSize, int particleInputSize, int itemOutputSize, int fluidOutputSize, int ParticleOutputSize) {
        this(recipeName, itemInputSize, fluidInputSize, particleInputSize, itemOutputSize, fluidOutputSize, ParticleOutputSize, true);
    }

    public QMDRecipeHandler(@Nonnull String name, int itemInputSize, int fluidInputSize, int particleInputSize, int itemOutputSize, int fluidOutputSize, int particleOutputSize, boolean isShapeless) {
        this.name = name;
        this.isShapeless = isShapeless;
        this.itemInputSize = itemInputSize;
        this.fluidInputSize = fluidInputSize;
        this.particleInputSize = particleInputSize;
        this.itemOutputSize = itemOutputSize;
        this.fluidOutputSize = fluidOutputSize;
        this.particleOutputSize = particleOutputSize;
        this.itemInputLastIndex = itemInputSize;
        this.fluidInputLastIndex = itemInputSize + fluidInputSize;
        this.particleInputLastIndex = itemInputSize + fluidInputSize + particleInputSize;
        this.itemOutputLastIndex = itemInputSize + fluidInputSize + particleInputSize + itemOutputSize;
        this.fluidOutputLastIndex = itemInputSize + fluidInputSize + particleInputSize + itemOutputSize + fluidOutputSize;
        this.particleOutputLastIndex = itemInputSize + fluidInputSize + particleInputSize + itemOutputSize + fluidOutputSize + particleOutputSize;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static QMDRecipeHandler<?> get(String name) {
        return QMDRecipes.getHandler(name);
    }

    public int getItemInputSize() {
        return itemInputSize;
    }

    @Override
    public boolean isValidFluidInput(FluidStack fluidStack, RecipeManager recipeManager) {
        return isValidInput(fluidStack, QMDRecipe::getFluidIngredients, recipeManager);
    }

    public int getFluidInputSize() {
        return fluidInputSize;
    }

    public int getParticleInputSize() {
        return particleInputSize;
    }

    public int getItemOutputSize() {
        return itemOutputSize;
    }

    public int getFluidOutputSize() {
        return fluidOutputSize;
    }

    public int getParticleOutputSize() {
        return particleOutputSize;
    }

    public boolean isShapeless() {
        return isShapeless;
    }

    protected void setValidFluids() {
        this.validFluids.clear();
    }

    public List<Set<ResourceLocation>> getValidFluids(RecipeManager recipeManager) {
        if (validFluids.isEmpty())
            validFluids.addAll(RecipeHelper.validFluids(this, recipeManager));
        return validFluids;
    }

    public Set<ResourceLocation> getValidFluids(Level level, int index) {
        return getValidFluids(NCUtil.getRecipeManager(level)).get(index);
    }

    public void postInit(RecipeManager recipeManager) {
        super.postInit(recipeManager);
        setValidFluids();
    }

//    @Override TODO
//    protected void fillHashCache() {
//        for (RECIPE recipe : recipeList) {
//            List<Triple<List<ItemStack>, List<FluidStack>, List<ParticleStack>>> materialListTuples = new ArrayList();
//            if (!prepareMaterialListTuples(recipe, materialListTuples)) {
//                continue;
//            }
//            for (Triple<List<ItemStack>, List<FluidStack>, List<ParticleStack>> materials : materialListTuples) {
//                if (isShapeless) {
//                    for (List<ItemStack> items : PermutationHelper.permutations(materials.getLeft())) {
//                        for (List<FluidStack> fluids : PermutationHelper.permutations(materials.getMiddle())) {
//                            for (List<ParticleStack> particles : PermutationHelper.permutations(materials.getRight())) {
//                                addToHashCache(recipe, items, fluids, particles);
//                            }
//                        }
//                    }
//                } else {
//                    addToHashCache(recipe, materials.getLeft(), materials.getMiddle(), materials.getRight());
//                }
//            }
//        }
//    }
//
//    protected void addToHashCache(RECIPE recipe, List<ItemStack> items, List<FluidStack> fluids, List<ParticleStack> particles) {
//        long hash = QMDRecipeHelper.hashMaterials(items, fluids, particles);
//        if (recipeCache.containsKey(hash)) {
//            recipeCache.get(hash).add(recipe);
//        } else {
//            ObjectSet<QMDRecipe> set = new ObjectOpenHashSet<>();
//            set.add(recipe);
//            recipeCache.put(hash, set);
//        }
//    }

//    protected <T, V extends IIngredient<T>> boolean isValidInput(T stack, int index, Function<QMDRecipe, List<V>> ingredientsFunction) {
//        for (QMDRecipe recipe : recipeList) {
//            if (isShapeless) {
//                for (V input : ingredientsFunction.apply(recipe)) {
//                    if (input.match(stack, IngredientSorption.NEUTRAL).matches()) {
//                        return true;
//                    }
//                }
//            } else {
//                if (ingredientsFunction.apply(recipe).get(index).match(stack, IngredientSorption.NEUTRAL).matches()) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public boolean isValidItemInput(ItemStack stack, Level level) {
        return isValidInput(stack, QMDRecipe::getItemIngredients, level.getRecipeManager());
    }

    public boolean isValidInput(ItemStack stack, Function<QMDRecipe, List<SizedChanceItemIngredient>> ingredientsFunction, RecipeManager recipeManager) {
        for (QMDRecipe recipe : getRecipeList(recipeManager)) {
            for (SizedChanceItemIngredient input : ingredientsFunction.apply(recipe)) {
                if (matchIngredient(input, stack, IngredientSorption.NEUTRAL).matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidInput(FluidStack stack, Function<QMDRecipe, List<SizedChanceFluidIngredient>> ingredientsFunction, RecipeManager recipeManager) {
        for (QMDRecipe recipe : getRecipeList(recipeManager)) {
            for (SizedChanceFluidIngredient input : ingredientsFunction.apply(recipe)) {
                if (matchFluidIngredient(input, stack, IngredientSorption.NEUTRAL).matches()) {
                    return true;
                }
            }
        }
        return false;
    }
}
