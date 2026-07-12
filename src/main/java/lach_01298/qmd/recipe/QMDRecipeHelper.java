package lach_01298.qmd.recipe;

import com.nred.nuclearcraft.recipe.IngredientMatchResult;
import com.nred.nuclearcraft.recipe.IngredientSorption;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import com.nred.nuclearcraft.util.CollectionHelper;
import it.unimi.dsi.fastutil.ints.IntList;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.types.QMDParticleRecipe;

import java.util.ArrayList;
import java.util.List;

import static com.nred.nuclearcraft.recipe.RecipeHelper.matchFluidIngredient;
import static com.nred.nuclearcraft.recipe.RecipeHelper.matchIngredient;

public class QMDRecipeHelper {
//    public static boolean containsItemIngredient(List<SizedChanceItemIngredient> list, SizedChanceItemIngredient ingredient) { TODO
//        for (SizedChanceItemIngredient i : list) {
//            if (i == null)
//                continue;
//            if (i.match(ingredient, IngredientSorption.NEUTRAL).matches())
//                return true;
//        }
//        return false;
//    }
//
//    public static boolean containsFluidIngredient(List<SizedChanceFluidIngredient> list, SizedChanceFluidIngredient ingredient) {
//        for (SizedChanceFluidIngredient i : list) {
//            if (i == null)
//                continue;
//            if (i.match(ingredient, IngredientSorption.NEUTRAL).matches())
//                return true;
//        }
//        return false;
//    }
//
//    public static boolean containsParticleIngredient(List<ParticleStack> list, ParticleStack ingredient) {
//        for (ParticleStack i : list) {
//            if (i == null)
//                continue;
//            if (i.match(ingredient, IngredientSorption.NEUTRAL).matches())
//                return true;
//        }
//        return false;
//    }
//
//    public static ItemStack fixItemStack(Object object) {
//        if (object == null) {
//            return ItemStack.EMPTY;
//        } else if (object instanceof ItemStack) {
//            ItemStack stack = ((ItemStack) object).copy();
//            if (stack.getCount() <= 0) {
//                stack.setCount(1);
//            }
//            return stack;
//        } else if (object instanceof Item) {
//            return new ItemStack((Item) object, 1);
//        } else {
//            if (!(object instanceof Block)) {
//                throw new RuntimeException(String.format("Invalid ItemStack: %s", object));
//            }
//            return new ItemStack((Block) object, 1);
//        }
//    }
//
//    public static FluidStack fixFluidStack(Object object) {
//        if (object == null) {
//            return FluidStack.EMPTY;
//        } else if (object instanceof FluidStack) {
//            FluidStack fluidstack = ((FluidStack) object).copy();
//            if (fluidstack.getAmount() <= 0) {
//                fluidstack.setAmount(1000);
//            }
//            return fluidstack;
//        } else {
//            if (!(object instanceof Fluid)) {
//                throw new RuntimeException(String.format("Invalid FluidStack: %s", object));
//            }
//            return new FluidStack((Fluid) object, 1000);
//        }
//    }
//
//    public static ParticleStack fixParticleStack(Object object) {
//        if (object == null)
//            return null;
//
//        else if (object instanceof ParticleStack) {
//            ParticleStack stack = ((ParticleStack) object).copy();
//            if (stack.getAmount() <= 0) {
//                stack.setAmount(1);
//            }
//            return stack;
//        } else {
//            if (!(object instanceof Particle)) {
//                throw new RuntimeException(String.format("Invalid ParticleStack: %s", object));
//            }
//            return new ParticleStack((Particle) object, 0, 1, 0);
//        }
//    }
//
//    public static OreIngredient oreStackFromString(String name) {
//        if (OreDictHelper.oreExists(name))
//            return new OreIngredient(name, 1);
//        return null;
//    }
//
//    public static FluidIngredient fluidStackFromString(String name) {
//        if (FluidRegHelper.fluidExists(name))
//            return new FluidIngredient(name, 1000);
//        return null;
//    }
//
//    public static ParticleIngredient particleStackFromString(String name) {
//        if (Particles.getParticleFromName(name) != null)
//            return new ParticleIngredient(name, 0, 1, 0);
//        return null;
//    }
//
//    public static List<List<ItemStack>> getItemInputLists(List<SizedChanceItemIngredient> ingredientList) {
//        List<List<ItemStack>> values = new ArrayList<List<ItemStack>>();
//        ingredientList.forEach(ingredient -> values.add(ingredient.getInputStackList()));
//        return values;
//    }
//
//    public static List<List<FluidStack>> getFluidInputLists(List<SizedChanceFluidIngredient> ingredientList) {
//        List<List<FluidStack>> values = new ArrayList<List<FluidStack>>();
//        ingredientList.forEach(ingredient -> values.add(ingredient.getInputStackList()));
//        return values;
//    }
//
//    public static List<List<ParticleStack>> getParticleInputLists(List<ParticleStack> ingredientList) {
//        List<List<ParticleStack>> values = new ArrayList<List<ParticleStack>>();
//        ingredientList.forEach(ingredient -> values.add(ingredient.getInputStackList()));
//        return values;
//    }
//
//    public static List<List<ItemStack>> getItemOutputLists(List<SizedChanceItemIngredient> ingredientList) {
//        List<List<ItemStack>> values = new ArrayList<List<ItemStack>>();
//        ingredientList.forEach(ingredient -> values.add(getItemOutputStackList(ingredient)));
//        return values;
//    }
//
//    public static List<List<FluidStack>> getFluidOutputLists(List<SizedChanceFluidIngredient> ingredientList) {
//        List<List<FluidStack>> values = new ArrayList<List<FluidStack>>();
//        ingredientList.forEach(ingredient -> values.add(getFluidOutputStackList(ingredient)));
//        return values;
//    }
//
//    public static List<List<ParticleStack>> getParticleOutputLists(List<ParticleStack> ingredientList) {
//        List<List<ParticleStack>> values = new ArrayList<List<ParticleStack>>();
//        ingredientList.forEach(ingredient -> values.add(getParticleOutputStackList(ingredient)));
//        return values;
//    }
//
//    public static List<ItemStack> getItemOutputStackList(SizedChanceItemIngredient ingredient) {
//        if (ingredient instanceof ChanceItemIngredient)
//            return ingredient.getOutputStackList();
//        else
//            return Lists.newArrayList(ingredient.getStack());
//    }
//
//    public static List<FluidStack> getFluidOutputStackList(SizedChanceFluidIngredient ingredient) {
//        if (ingredient instanceof ChanceFluidIngredient)
//            return ingredient.getOutputStackList();
//        else
//            return Lists.newArrayList(ingredient.getStack());
//    }
//
//    public static List<ParticleStack> getParticleOutputStackList(ParticleStack ingredient) {
//        return Lists.newArrayList(ingredient.getStack());
//    }
//
//    @Nullable
//    public static List<ItemStack> getItemOutputList(List<SizedChanceItemIngredient> list) {
//        if (list.contains(null))
//            return new ArrayList<ItemStack>();
//        List stacks = new ArrayList<ItemStack>();
//        list.forEach(ingredient -> stacks.add(ingredient.getStack()));
//        if (stacks.contains(null))
//            return new ArrayList<ItemStack>();
//        return stacks;
//    }
//
//    @Nullable
//    public static List<FluidStack> getFluidOutputList(List<SizedChanceFluidIngredient> list) {
//        if (list.contains(null))
//            return new ArrayList<FluidStack>();
//        List stacks = new ArrayList<FluidStack>();
//        list.forEach(ingredient -> stacks.add(ingredient.getStack()));
//        if (stacks.contains(null))
//            return new ArrayList<FluidStack>();
//        return stacks;
//    }
//
//    @Nullable
//    public static List<ParticleStack> getParticleOutputList(List<ParticleStack> list) {
//        if (list.contains(null))
//            return new ArrayList<ParticleStack>();
//        List stacks = new ArrayList<ParticleStack>();
//        list.forEach(ingredient -> stacks.add(ingredient.getStack()));
//        if (stacks.contains(null))
//            return new ArrayList<ParticleStack>();
//        return stacks;
//    }
//
//    @Nullable
//    public static ItemStack getItemStackFromIngredientList(List<SizedChanceItemIngredient> list, int pos) {
//        if (!list.isEmpty() && pos < list.size()) {
//            SizedChanceItemIngredient object = list.get(pos);
//            return object.getStack();
//        }
//        return null;
//    }
//
//    @Nullable
//    public static FluidStack getFluidStackFromIngredientList(List<SizedChanceFluidIngredient> list, int pos) {
//        if (!list.isEmpty() && pos < list.size()) {
//            SizedChanceFluidIngredient object = list.get(pos);
//            return object.getStack();
//        }
//        return null;
//    }
//
//    @Nullable
//    public static ParticleStack getParticleStackFromIngredientList(List<ParticleStack> list, int pos) {
//        if (!list.isEmpty() && pos < list.size()) {
//            ParticleStack object = list.get(pos);
//            return object.getStack();
//        }
//        return null;
//    }
//
//    @Nullable
//    public static SizedChanceItemIngredient buildItemIngredient(Object object) {
//        if (AbstractQMDRecipeHandler.requiresItemFixing(object)) {
//            object = QMDRecipeHelper.fixItemStack(object);
//        }
//        if (object instanceof SizedChanceItemIngredient) {
//            return checkedItemIngredient((SizedChanceItemIngredient) object);
//        } else if (object instanceof List) {
//            List list = (List) object;
//            List<SizedChanceItemIngredient> buildList = new ArrayList<SizedChanceItemIngredient>();
//            if (!list.isEmpty()) {
//                for (Object listObject : list) {
//                    if (listObject instanceof SizedChanceItemIngredient) {
//                        buildList.add((SizedChanceItemIngredient) listObject);
//                    } else if (listObject != null) {
//                        SizedChanceItemIngredient recipeObject = checkedItemIngredient(buildItemIngredient(listObject));
//                        if (recipeObject != null) {
//                            buildList.add(recipeObject);
//                        }
//                    }
//                }
//                if (buildList.isEmpty())
//                    return null;
//                return checkedItemIngredient(new ItemArrayIngredient(buildList));
//            } else {
//                return null;
//            }
//        } else if (object instanceof String) {
//            return checkedItemIngredient(QMDRecipeHelper.oreStackFromString((String) object));
//        }
//        if (object instanceof ItemStack) {
//            return checkedItemIngredient(new ItemIngredient((ItemStack) object));
//        }
//        return null;
//    }
//
//    @Nullable
//    public static SizedChanceItemIngredient checkedItemIngredient(SizedChanceItemIngredient ingredient) {
//        return ingredient == null || !ingredient.isValid() ? null : ingredient;
//    }
//
//    @Nullable
//    public static SizedChanceFluidIngredient buildFluidIngredient(Object object) {
//        if (AbstractQMDRecipeHandler.requiresFluidFixing(object)) {
//            object = QMDRecipeHelper.fixFluidStack(object);
//        }
//        if (needsExpanding() && object instanceof FluidIngredient) {
//            return checkedFluidIngredient(buildFluidIngredient(expandedFluidStackList((FluidIngredient) object)));
//        }
//        if (object instanceof SizedChanceFluidIngredient) {
//            return checkedFluidIngredient((SizedChanceFluidIngredient) object);
//        } else if (object instanceof List) {
//            List list = (List) object;
//            List<SizedChanceFluidIngredient> buildList = new ArrayList<SizedChanceFluidIngredient>();
//            if (!list.isEmpty()) {
//                for (Object listObject : list) {
//                    if (listObject instanceof SizedChanceFluidIngredient) {
//                        buildList.add((SizedChanceFluidIngredient) listObject);
//                    } else if (listObject != null) {
//                        SizedChanceFluidIngredient recipeObject = checkedFluidIngredient(buildFluidIngredient(listObject));
//                        if (recipeObject != null)
//                            buildList.add(recipeObject);
//                    }
//                }
//                if (buildList.isEmpty())
//                    return null;
//                return checkedFluidIngredient(new FluidArrayIngredient(buildList));
//            } else {
//                return null;
//            }
//        } else if (object instanceof String) {
//            return checkedFluidIngredient(QMDRecipeHelper.fluidStackFromString((String) object));
//        }
//        if (object instanceof FluidStack) {
//            return checkedFluidIngredient(new FluidIngredient((FluidStack) object));
//        }
//        return null;
//    }
//
//    @Nullable
//    public static SizedChanceFluidIngredient checkedFluidIngredient(SizedChanceFluidIngredient ingredient) {
//        return ingredient == null || !ingredient.isValid() ? null : ingredient;
//    }
//
//    @Nullable
//    public static ParticleStack buildParticleIngredient(Object object) {
//        if (AbstractQMDRecipeHandler.requiresParticleFixing(object)) {
//            object = QMDRecipeHelper.fixParticleStack(object);
//        }
//        if (object instanceof ParticleStack) {
//            return checkedParticleIngredient((ParticleStack) object);
//        } else if (object instanceof List) {
//            List list = (List) object;
//            List<ParticleStack> buildList = new ArrayList<ParticleStack>();
//            if (!list.isEmpty()) {
//                for (Object listObject : list) {
//                    if (listObject instanceof ParticleStack) {
//                        buildList.add((ParticleStack) listObject);
//                    } else if (listObject != null) {
//                        ParticleStack recipeObject = checkedParticleIngredient(
//                                buildParticleIngredient(listObject));
//                        if (recipeObject != null)
//                            buildList.add(recipeObject);
//                    }
//                }
//                if (buildList.isEmpty())
//                    return null;
//                return checkedParticleIngredient(new ParticleArrayIngredient(buildList));
//            } else {
//                return null;
//            }
//        } else if (object instanceof String) {
//            return checkedParticleIngredient(QMDRecipeHelper.particleStackFromString((String) object));
//        }
//        if (object instanceof ParticleStack) {
//            return checkedParticleIngredient(new ParticleIngredient((ParticleStack) object));
//        }
//        return null;
//    }
//
//    @Nullable
//    public static ParticleStack checkedParticleIngredient(ParticleStack ingredient) {
//        return ingredient == null || !ingredient.isValid() ? null : ingredient;
//    }
//
//    public static boolean needsExpanding() {
//        return ModCheck.mekanismLoaded() || ModCheck.techRebornLoaded();
//    }
//
//    /**
//     * For Mekanism and Tech Reborn fluids
//     */
//    public static List<FluidIngredient> expandedFluidStackList(FluidIngredient stack) {
//        List<FluidIngredient> fluidStackList = Lists.newArrayList(stack);
//
//        if (ModCheck.mekanismLoaded() && !stack.fluidName.equals("helium")) {
//            if (GasHelper.TRANSLATION_MAP.containsKey(stack.fluidName)) {
//                fluidStackList.add(AbstractQMDRecipeHandler.fluidStack(GasHelper.TRANSLATION_MAP.get(stack.fluidName),
//                        stack.stack.amount));
//            } else {
//                fluidStackList.add(AbstractQMDRecipeHandler.fluidStack("liquid" + stack.fluidName, stack.stack.amount));
//            }
//        }
//
//        if (ModCheck.techRebornLoaded()) {
//            fluidStackList.add(AbstractQMDRecipeHandler.fluidStack("fluid" + stack.fluidName, stack.stack.amount));
//        }
//
//        return fluidStackList;
//    }

    public static QMDRecipeMatchResult matchIngredients(IngredientSorption sorption, List<SizedChanceItemIngredient> itemIngredients, List<SizedChanceFluidIngredient> fluidIngredients, List<ParticleStack> particleIngredients, List<SizedChanceItemIngredient> items, List<SizedChanceFluidIngredient> fluids, List<ParticleStack> particles, QMDRecipe recipe) {
        int itemCount = items.size(), fluidCount = fluids.size(), particleCount = particles.size();
        if (itemIngredients.size() != items.size() || fluidIngredients.size() != fluids.size() || particleIngredients.size() != particles.size()) {
            items = items.stream().filter(itemIngredient -> !itemIngredient.isEmpty()).toList();
            fluids = fluids.stream().filter(fluidIngredient -> !fluidIngredient.isEmpty()).toList();
            itemCount = items.size();
            fluidCount = fluids.size();
            if (itemIngredients.size() != items.size() || fluidIngredients.size() != fluids.size() || particleIngredients.size() != particles.size()) {
                return QMDRecipeMatchResult.FAIL;
            }
        }

        IntList itemInputOrder = CollectionHelper.increasingList(itemCount);
        IntList fluidInputOrder = CollectionHelper.increasingList(fluidCount);
        IntList particleInputOrder = CollectionHelper.increasingList(particleCount);

        List<SizedChanceItemIngredient> itemIngredientsRemaining = new ArrayList<>(itemIngredients);
        itemInputs:
        for (int i = 0; i < items.size(); i++) {
            SizedChanceItemIngredient item = items.get(i);
            for (int j = 0; j < itemIngredients.size(); j++) {
                SizedChanceItemIngredient itemIngredient = itemIngredientsRemaining.get(j);
                if (itemIngredient == null)
                    continue;
                IngredientMatchResult matchResult = matchIngredient(itemIngredient, item, sorption);
                if (matchResult.matches()) {
                    itemIngredientsRemaining.set(j, null);
                    itemInputOrder.set(i, j);
                    continue itemInputs;
                }
            }
            return QMDRecipeMatchResult.FAIL;
        }
        List<SizedChanceFluidIngredient> fluidIngredientsRemaining = new ArrayList<>(fluidIngredients);
        fluidInputs:
        for (int i = 0; i < fluids.size(); i++) {
            SizedChanceFluidIngredient fluid = fluids.get(i);
            for (int j = 0; j < fluidIngredients.size(); j++) {
                SizedChanceFluidIngredient fluidIngredient = fluidIngredientsRemaining.get(j);
                if (fluidIngredient == null)
                    continue;
                IngredientMatchResult matchResult = matchFluidIngredient(fluidIngredient, fluid, sorption);
                if (matchResult.matches()) {
                    fluidIngredientsRemaining.set(j, null);
                    fluidInputOrder.set(i, j);
                    continue fluidInputs;
                }
            }
            return QMDRecipeMatchResult.FAIL;
        }
        List<ParticleStack> particleIngredientsRemaining = new ArrayList<>(particleIngredients);
        particleInputs:
        for (int i = 0; i < particles.size(); i++) {
            for (int j = 0; j < particleIngredients.size(); j++) {
                ParticleStack particleIngredient = particleIngredientsRemaining.get(j);
                if (particleIngredient == null)
                    continue;
                IngredientMatchResult matchResult = matchParticleStack(particleIngredient, particles.get(i), sorption, recipe);
                if (matchResult.matches()) {
                    particleIngredientsRemaining.set(j, null);
                    particleInputOrder.set(i, j);
                    continue particleInputs;
                }
            }
            return QMDRecipeMatchResult.FAIL;
        }
        return new QMDRecipeMatchResult(true, itemInputOrder, fluidInputOrder, particleInputOrder);
    }

    public static IngredientMatchResult matchParticleStack(ParticleStack stack, ParticleStack other, IngredientSorption type, QMDRecipe recipe) {
        if (!(recipe instanceof QMDParticleRecipe particleRecipe) || !stack.isInRange(other, particleRecipe.getMaxEnergy())) {
            return IngredientMatchResult.FAIL;
        }
        return new IngredientMatchResult(type.checkStackSize(stack.getAmount(), stack.getAmount()));
    }

//    public static List<String> getItemIngredientNames(List<SizedChanceItemIngredient> ingredientList) {
//        List<String> ingredientNames = new ArrayList<String>();
//        for (SizedChanceItemIngredient ingredient : ingredientList) {
//            if (ingredient == null || ingredient instanceof EmptyItemIngredient)
//                ingredientNames.add("null");
//            else if (ingredient instanceof ItemArrayIngredient)
//                ingredientNames.add(((ItemArrayIngredient) ingredient).getIngredientRecipeString());
//            else
//                ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
//        }
//        return ingredientNames;
//    }
//
//    public static List<String> getFluidIngredientNames(List<SizedChanceFluidIngredient> ingredientList) {
//        List<String> ingredientNames = new ArrayList<String>();
//        for (SizedChanceFluidIngredient ingredient : ingredientList) {
//            if (ingredient == null || ingredient instanceof EmptyFluidIngredient)
//                ingredientNames.add("null");
//            else if (ingredient instanceof FluidArrayIngredient)
//                ingredientNames.add(((FluidArrayIngredient) ingredient).getIngredientRecipeString());
//            else
//                ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
//        }
//        return ingredientNames;
//    }
//
//    public static List<String> getParticleIngredientNames(List<ParticleStack> ingredientList) {
//        List<String> ingredientNames = new ArrayList<String>();
//        for (ParticleStack ingredient : ingredientList) {
//            if (ingredient == null || ingredient instanceof EmptyParticleIngredient)
//                ingredientNames.add("null");
//            else if (ingredient instanceof ParticleArrayIngredient)
//                ingredientNames.add(((ParticleArrayIngredient) ingredient).getIngredientRecipeString());
//            else
//                ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
//        }
//        return ingredientNames;
//    }
//
//    public static String getAllIngredientNamesConcat(List<SizedChanceItemIngredient> itemIngredientList,
//                                                     List<SizedChanceFluidIngredient> fluidIngredientList, List<ParticleStack> particleIngredientList) {
//        return StringHelper.stringListConcat(getItemIngredientNames(itemIngredientList),
//                getFluidIngredientNames(fluidIngredientList), getParticleIngredientNames(particleIngredientList));
//    }
//
//    public static String getRecipeString(List<SizedChanceItemIngredient> itemIngredientList,
//                                         List<SizedChanceFluidIngredient> fluidIngredientList, List<ParticleStack> particleIngredientList,
//                                         List<SizedChanceItemIngredient> itemProductList, List<SizedChanceFluidIngredient> fluidProductList,
//                                         List<ParticleStack> particleProductList) {
//        return getAllIngredientNamesConcat(itemIngredientList, fluidIngredientList, particleIngredientList) + " -> "
//                + getAllIngredientNamesConcat(itemProductList, fluidProductList, particleProductList);
//    }
//
//    public static String getRecipeString(IQMDRecipe recipe) {
//        if (recipe == null)
//            return "nullRecipe";
//        return getRecipeString(recipe.getItemIngredients(), recipe.getFluidIngredients(), recipe.getParticleIngredients(),
//                recipe.getItemProducts(), recipe.getFluidProducts(), recipe.getParticleProducts());
//    }
//
//    public static List<String> buildItemIngredientNames(List ingredientList) {
//        List<String> ingredientNames = new ArrayList<String>();
//        for (Object obj : ingredientList) {
//            if (obj == null)
//                ingredientNames.add("null");
//            else {
//                if (!(obj instanceof SizedChanceItemIngredient))
//                    obj = buildItemIngredient(obj);
//                SizedChanceItemIngredient ingredient = (SizedChanceItemIngredient) obj;
//                if (ingredient instanceof ItemArrayIngredient)
//                    ingredientNames.add(((ItemArrayIngredient) ingredient).getIngredientRecipeString());
//                else
//                    ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
//            }
//        }
//        return ingredientNames;
//    }
//
//    public static List<String> buildFluidIngredientNames(List ingredientList) {
//        List<String> ingredientNames = new ArrayList<String>();
//        for (Object obj : ingredientList) {
//            if (obj == null)
//                ingredientNames.add("null");
//            else {
//                if (!(obj instanceof SizedChanceFluidIngredient))
//                    obj = buildFluidIngredient(obj);
//                SizedChanceFluidIngredient ingredient = (SizedChanceFluidIngredient) obj;
//                if (ingredient instanceof FluidArrayIngredient)
//                    ingredientNames.add(((FluidArrayIngredient) ingredient).getIngredientRecipeString());
//                else
//                    ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
//            }
//        }
//        return ingredientNames;
//    }
//
//    public static List<Set<String>> validFluids(QMDRecipeHandler recipes) {
//        return validFluids(recipes, new ArrayList<String>());
//    }
//
//    public static List<Set<String>> validFluids(QMDRecipeHandler recipes, List<String> exceptions) {
//        int fluidInputSize = recipes.getFluidInputSize();
//        int fluidOutputSize = recipes.getFluidOutputSize();
//
//        List<FluidStack> fluidStackList = new ArrayList<FluidStack>();
//        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values())
//            fluidStackList.add(new FluidStack(fluid, 1000));
//
//        Set<String> fluidNameSet = new ObjectOpenHashSet<String>();
//        for (FluidStack fluidStack : fluidStackList) {
//            String fluidName = fluidStack.getFluid().getName();
//            if (recipes.isValidFluidInput(fluidStack) && !exceptions.contains(fluidName))
//                fluidNameSet.add(fluidName);
//        }
//
//        List<Set<String>> allowedFluidLists = new ArrayList<Set<String>>();
//        for (int i = 0; i < fluidInputSize; i++)
//            allowedFluidLists.add(fluidNameSet);
//        for (int i = fluidInputSize; i < fluidInputSize + fluidOutputSize; i++)
//            allowedFluidLists.add(null);
//
//        return allowedFluidLists;
//    }
//
//    public static @Nullable OreIngredient getOreStackFromItems(List<ItemStack> stackList, int stackSize) {
//        if (stackList == null || stackList.isEmpty())
//            return null;
//        List<String> oreNames = OreDictHelper.getOreNamesFromStacks(stackList);
//        return oreNames.size() == 1 ? new OreIngredient(oreNames.get(0), stackSize) : null;
//    }
//
//    public static long hashMaterialsRaw(List<ItemStack> items, List<Tank> fluids, List<ParticleStack> particles) {
//        long hash = 1L;
//        Iterator<ItemStack> itemIter = items.iterator();
//        while (itemIter.hasNext()) {
//            ItemStack stack = itemIter.next();
//            hash = 31L * hash + (stack == null ? 0L : RecipeItemHelper.pack(stack));
//        }
//        Iterator<Tank> fluidIter = fluids.iterator();
//        while (fluidIter.hasNext()) {
//            Tank tank = fluidIter.next();
//            hash = 31L * hash + (tank == null ? 0L : tank.getFluid() == null ? 0L : tank.getFluid().getFluid().getName().hashCode());
//        }
//        Iterator<ParticleStack> particleIter = particles.iterator();
//        while (particleIter.hasNext()) {
//            ParticleStack stack = particleIter.next();
//            hash = 31L * hash + (stack == null ? 0L : stack.getParticle().getName().hashCode());
//        }
//        return hash;
//    }
//
//    public static long hashMaterials(List<ItemStack> items, List<FluidStack> fluids, List<ParticleStack> particles) {
//        long hash = 1L;
//        Iterator<ItemStack> itemIter = items.iterator();
//        while (itemIter.hasNext()) {
//            ItemStack stack = itemIter.next();
//            hash = 31L * hash + (stack == null ? 0L : RecipeItemHelper.pack(stack));
//        }
//        Iterator<FluidStack> fluidIter = fluids.iterator();
//        while (fluidIter.hasNext()) {
//            FluidStack stack = fluidIter.next();
//            hash = 31L * hash + (stack == null ? 0L : stack.getFluid().getName().hashCode());
//        }
//        Iterator<ParticleStack> particleIter = particles.iterator();
//        while (particleIter.hasNext()) {
//            ParticleStack stack = particleIter.next();
//            hash = 31L * hash + (stack == null ? 0L : stack.getParticle().getName().hashCode());
//        }
//        return hash;
//    }
//
//
//    public static InventoryCrafting fakeCrafter(int width, int height) {
//        return new FakeCrafting(width, height);
//    }
//
//    private static class FakeCrafting extends InventoryCrafting {
//
//        private static final FakeCraftingContainer FAKE_CONTAINER = new FakeCraftingContainer();
//
//        private static class FakeCraftingContainer extends Container {
//
//            @Override
//            public void onCraftMatrixChanged(IInventory inventory) {
//
//            }
//
//            @Override
//            public boolean canInteractWith(EntityPlayer player) {
//                return false;
//            }
//        }
//
//        private FakeCrafting(int width, int height) {
//            super(FAKE_CONTAINER, width, height);
//        }
//    }
}