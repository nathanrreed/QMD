//package lach_01298.qmd.recipe;
//
//import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
//import com.nred.nuclearcraft.recipe.IRecipe;
//import com.nred.nuclearcraft.recipe.IngredientSorption;
//import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
//import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
//import lach_01298.qmd.particle.ParticleStack;
//import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Recipe;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//import net.minecraft.world.item.crafting.RecipeType;
//import net.minecraft.world.level.Level;
//
//import java.util.List;
//
//public class QMDRecipe implements IQMDRecipe, Recipe<QMDRecipeInput> {
//    protected List<SizedChanceItemIngredient> itemIngredients, itemProducts;
//    protected List<SizedChanceFluidIngredient> fluidIngredients, fluidProducts;
//    protected List<IParticleIngredient> particleIngredients, particleProducts;
//
//    public QMDRecipe(List<SizedChanceItemIngredient> itemIngredientsList, List<SizedChanceFluidIngredient> fluidIngredientsList, List<IParticleIngredient> particleIngredientsList, List<SizedChanceItemIngredient> itemProductsList, List<SizedChanceFluidIngredient> fluidProductsList, List<IParticleIngredient> particleProductsList) {
//        itemIngredients = itemIngredientsList;
//        fluidIngredients = fluidIngredientsList;
//        particleIngredients = particleIngredientsList;
//        itemProducts = itemProductsList;
//        fluidProducts = fluidProductsList;
//        particleProducts = particleProductsList;
//    }
//
//    @Override
//    public List<SizedChanceItemIngredient> getItemIngredients() {
//        return itemIngredients;
//    }
//
//    @Override
//    public List<SizedChanceFluidIngredient> getFluidIngredients() {
//        return fluidIngredients;
//    }
//
//    @Override
//    public List<IParticleIngredient> getParticleIngredients() {
//        return particleIngredients;
//    }
//
//    @Override
//    public List<SizedChanceItemIngredient> getItemProducts() {
//        return itemProducts;
//    }
//
//    @Override
//    public List<SizedChanceFluidIngredient> getFluidProducts() {
//        return fluidProducts;
//    }
//
//    @Override
//    public List<IParticleIngredient> getParticleProducts() {
//        return particleProducts;
//    }
//
//    @Override
//    public QMDRecipeMatchResult matchInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs, List<ParticleStack> particleInputs, List<Object> extras) {
//        return QMDRecipeHelper.matchIngredients(IngredientSorption.INPUT, itemIngredients, fluidIngredients, particleIngredients, itemInputs, fluidInputs, particleInputs, isShapeless, extras);
//    }
//
//    @Override
//    public QMDRecipeMatchResult matchOutputs(List<ItemStack> itemOutputs, List<Tank> fluidOutputs, List<ParticleStack> particleOutputs) {
//        return QMDRecipeHelper.matchIngredients(IngredientSorption.OUTPUT, itemProducts, fluidProducts, particleIngredients, itemOutputs, fluidOutputs, particleOutputs, isShapeless, extras);
//    }
//
//    @Override
//    public QMDRecipeMatchResult matchIngredients(List<SizedChanceItemIngredient> itemIngredients, List<SizedChanceFluidIngredient> fluidIngredients, List<IParticleIngredient> particleIngredients) {
//        return QMDRecipeHelper.matchIngredients(IngredientSorption.INPUT, this.itemIngredients, this.fluidIngredients, this.particleIngredients, itemIngredients, fluidIngredients, particleIngredients, isShapeless, extras);
//    }
//
//    @Override
//    public QMDRecipeMatchResult matchProducts(List<SizedChanceItemIngredient> itemProducts, List<SizedChanceFluidIngredient> fluidProducts, List<IParticleIngredient> particleProducts) {
//        return QMDRecipeHelper.matchIngredients(IngredientSorption.OUTPUT, this.itemProducts, this.fluidProducts, this.particleProducts, itemProducts, fluidProducts, particleProducts, isShapeless, extras);
//    }
//
//
//
//    /* ================================== Recipe Extras ===================================== */
//
//    //most recipes
//    public long getMaxEnergy() {
//        return (long) extras.get(0);
//    }
//
//    public double getCrossSection() {
//        return (double) extras.get(1);
//    }
//
//    public long getEnergyReleased() {
//        return (long) extras.get(2);
//    }
//
//    // nuclearsynthesis chamber
//    public long getHeatReleased() {
//        return (long) extras.get(1);
//    }
//
//    @Override
//    public boolean matches(QMDRecipeInput input, Level level) {
//        return QMDRecipeHelper.matchIngredients(IngredientSorption.INPUT, this.itemIngredients, this.fluidIngredients, this.particleIngredients, itemIngredients, fluidIngredients, particleIngredients).isMatch;
//    }
//
//    @Override
//    public ItemStack assemble(QMDRecipeInput input, HolderLookup.Provider registries) {
//        return itemProducts.isEmpty() ? ItemStack.EMPTY : itemProducts.getFirst().getStack().copy();
//    }
//
//    @Override
//    public boolean canCraftInDimensions(int width, int height) {
//        return true;
//    }
//
//    @Override
//    public ItemStack getResultItem(HolderLookup.Provider registries) {
//        return itemProducts.isEmpty() ? ItemStack.EMPTY : itemProducts.getFirst().getStack().copy();
//    }
//
//    @Override
//    public RecipeSerializer<?> getSerializer() {
//        throw new RuntimeException("Basic Recipe Serializer Used!");
//    }
//
//    @Override
//    public RecipeType<?> getType() {
//        throw new RuntimeException("Basic Recipe Type Used!");
//    }
//}
