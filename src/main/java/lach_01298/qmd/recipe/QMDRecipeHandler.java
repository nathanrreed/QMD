//package lach_01298.qmd.recipe;
//
//import com.nred.nuclearcraft.util.PermutationHelper;
//import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
//import it.unimi.dsi.fastutil.objects.ObjectSet;
//import lach_01298.qmd.particle.ParticleStack;
//import net.minecraft.world.item.ItemStack;
//import net.neoforged.neoforge.fluids.FluidStack;
//import org.apache.commons.lang3.tuple.Triple;
//
//import javax.annotation.Nonnull;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.function.Function;
//import java.util.function.Predicate;
//
//public abstract class QMDRecipeHandler<RECIPE extends QMDRecipe> extends AbstractQMDRecipeHandler<RECIPE> {
//    private final String name;
//    public final int itemInputSize, fluidInputSize, particleInputSize, itemOutputSize, fluidOutputSize, particleOutputSize;
//    public final int itemInputLastIndex, fluidInputLastIndex, particleInputLastIndex, itemOutputLastIndex, fluidOutputLastIndex, particleOutputLastIndex;
//    public final boolean isShapeless;
//    public final List<Set<String>> validFluids = new ArrayList<>();
//
//    public QMDRecipeHandler(@Nonnull String recipeName, int itemInputSize, int fluidInputSize, int particleInputSize, int itemOutputSize, int fluidOutputSize, int ParticleOutputSize) {
//        this(recipeName, itemInputSize, fluidInputSize, particleInputSize, itemOutputSize, fluidOutputSize, ParticleOutputSize, true);
//    }
//
//    public QMDRecipeHandler(@Nonnull String name, int itemInputSize, int fluidInputSize, int particleInputSize, int itemOutputSize, int fluidOutputSize, int particleOutputSize, boolean isShapeless) {
//        this.name = name;
//        this.isShapeless = isShapeless;
//        this.itemInputSize = itemInputSize;
//        this.fluidInputSize = fluidInputSize;
//        this.particleInputSize = particleInputSize;
//        this.itemOutputSize = itemOutputSize;
//        this.fluidOutputSize = fluidOutputSize;
//        this.particleOutputSize = particleOutputSize;
//        this.itemInputLastIndex = itemInputSize;
//        this.fluidInputLastIndex = itemInputSize + fluidInputSize;
//        this.particleInputLastIndex = itemInputSize + fluidInputSize + particleInputSize;
//        this.itemOutputLastIndex = itemInputSize + fluidInputSize + particleInputSize + itemOutputSize;
//        this.fluidOutputLastIndex = itemInputSize + fluidInputSize + particleInputSize + itemOutputSize + fluidOutputSize;
//        this.particleOutputLastIndex = itemInputSize + fluidInputSize + particleInputSize + itemOutputSize + fluidOutputSize + particleOutputSize;
//        addRecipes();
//    }
//
//    @Override
//    public String getName() {
//        return this.name;
//    }
//
//    public static QMDRecipeHandler<?> get(String name) {
//        return QMDRecipes.getHandler(name);
//    }
//
//    @Override
//    public List<RECIPE> getRecipeList() {
//        return recipeList;
//    }
//
//    public int getItemInputSize() {
//        return itemInputSize;
//    }
//
//    public int getFluidInputSize() {
//        return fluidInputSize;
//    }
//
//    public int getParticleInputSize() {
//        return particleInputSize;
//    }
//
//    public int getItemOutputSize() {
//        return itemOutputSize;
//    }
//
//    public int getFluidOutputSize() {
//        return fluidOutputSize;
//    }
//
//    public int getParticleOutputSize() {
//        return particleOutputSize;
//    }
//
//    public boolean isShapeless() {
//        return isShapeless;
//    }
//
//    protected void setValidFluids() {
//        this.validFluids.clear();
//        this.validFluids.addAll(QMDRecipeHelper.validFluids(this));
//    }
//
//    public void postInit() {
//        super.postInit();
//        setValidFluids();
//    }
//
//    @Override
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
//
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
//
//    public boolean isValidItemInput(ItemStack stack, int slot) {
//        return isValidInput(stack, slot, QMDRecipe::getItemIngredients);
//    }
//
//    public boolean isValidFluidInput(FluidStack stack, int tankNumber) {
//        return isValidInput(stack, tankNumber, QMDRecipe::getFluidIngredients);
//    }
//
//    public boolean isValidParticleInput(ParticleStack stack, int slot) {
//        return isValidInput(stack, slot, QMDRecipe::getParticleIngredients);
//    }
//
//    /**
//     * Smart insertion - don't insert if stack is not valid for any possible recipes
//     */
//    public <T, U, V extends IIngredient<T>, W extends IIngredient<U>> boolean isValidInput(T stack, int index, List<T> inputs, List<U> associatedInputs, QMDRecipeInfo<QMDRecipe> recipeInfo, int inputSize, int associatedInputSize, Predicate<T> isEmptyFunction, Predicate<U> associatedIsEmptyFunction, Predicate<T> isEqualFunction, Function<QMDRecipe, List<V>> ingredientsFunction, Function<QMDRecipe, List<W>> associatedIngredientsFunction) {
//        List<T> otherInputs = inputsExcludingIndex(inputs, index);
//        if ((otherInputs.stream().allMatch(isEmptyFunction) && associatedInputs.stream().allMatch(associatedIsEmptyFunction)) || isEqualFunction.test(inputs.get(index))) {
//            return isValidInput(stack, index, ingredientsFunction);
//        }
//        if (recipeInfo == null) {
//            ObjectSet<QMDRecipe> recipes = new ObjectOpenHashSet<>(recipeList);
//            recipeLoop:
//            for (QMDRecipe recipe : recipeList) {
//                List<V> ingredients = ingredientsFunction.apply(recipe);
//                List<W> associatedIngredients = associatedIngredientsFunction.apply(recipe);
//                if (isShapeless) {
//                    stackLoop:
//                    for (T inputStack : inputs) {
//                        if (!isEmptyFunction.test(inputStack)) {
//                            for (V recipeInput : ingredients) {
//                                if (recipeInput.match(inputStack, IngredientSorption.NEUTRAL).matches()) {
//                                    continue stackLoop;
//                                }
//                            }
//                            recipes.remove(recipe);
//                            continue recipeLoop;
//                        }
//                    }
//                    associatedStackLoop:
//                    for (U inputStack : associatedInputs) {
//                        if (!associatedIsEmptyFunction.test(inputStack)) {
//                            for (W recipeInput : associatedIngredients) {
//                                if (recipeInput.match(inputStack, IngredientSorption.NEUTRAL).matches()) {
//                                    continue associatedStackLoop;
//                                }
//                            }
//                            recipes.remove(recipe);
//                            continue recipeLoop;
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < inputSize; ++i) {
//                        T inputStack = inputs.get(i);
//                        if (!isEmptyFunction.test(inputStack) && !ingredients.get(i).match(inputStack, IngredientSorption.NEUTRAL).matches()) {
//                            recipes.remove(recipe);
//                            continue recipeLoop;
//                        }
//                    }
//                    for (int i = 0; i < associatedInputSize; ++i) {
//                        U inputStack = associatedInputs.get(i);
//                        if (!associatedIsEmptyFunction.test(inputStack) && !associatedIngredients.get(i).match(inputStack, IngredientSorption.NEUTRAL).matches()) {
//                            recipes.remove(recipe);
//                            continue recipeLoop;
//                        }
//                    }
//                }
//            }
//            for (QMDRecipe recipe : recipes) {
//                if (isValidInputInternal(stack, index, otherInputs, ingredientsFunction.apply(recipe), isEmptyFunction)) {
//                    return true;
//                }
//            }
//            return false;
//        } else {
//            return isValidInputInternal(stack, index, otherInputs, ingredientsFunction.apply(recipeInfo.recipe), isEmptyFunction);
//        }
//    }
//
//    protected <T, V extends IIngredient<T>> boolean isValidInputInternal(T stack, int index, List<T> otherInputs, List<V> ingredients, Predicate<T> isEmptyFunction) {
//        if (isShapeless) {
//            for (V input : ingredients) {
//                if (input.match(stack, IngredientSorption.NEUTRAL).matches()) {
//                    for (T other : otherInputs) {
//                        if (!isEmptyFunction.test(other) && input.match(other, IngredientSorption.NEUTRAL).matches()) {
//                            return false;
//                        }
//                    }
//                    return true;
//                }
//            }
//            return false;
//        } else {
//            return ingredients.get(index).match(stack, IngredientSorption.NEUTRAL).matches();
//        }
//    }
//
//    protected static <T> List<T> inputsExcludingIndex(List<T> inputs, int index) {
//        List<T> inputsExcludingIndex = new ArrayList<>(inputs);
//        inputsExcludingIndex.remove(index);
//        return inputsExcludingIndex;
//    }
//}
