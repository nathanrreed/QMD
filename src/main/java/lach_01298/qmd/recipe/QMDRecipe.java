package lach_01298.qmd.recipe;

import com.nred.nuclearcraft.recipe.IngredientSorption;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public class QMDRecipe implements IQMDRecipe, Recipe<QMDRecipeInput> {
    protected List<SizedChanceItemIngredient> itemIngredients, itemProducts;
    protected List<SizedChanceFluidIngredient> fluidIngredients, fluidProducts;
    protected List<ParticleStack> particleIngredients, particleProducts;

    public QMDRecipe(List<SizedChanceItemIngredient> itemIngredientsList, List<SizedChanceFluidIngredient> fluidIngredientsList, List<ParticleStack> particleIngredientsList, List<SizedChanceItemIngredient> itemProductsList, List<SizedChanceFluidIngredient> fluidProductsList, List<ParticleStack> particleProductsList) {
        itemIngredients = itemIngredientsList;
        fluidIngredients = fluidIngredientsList;
        particleIngredients = particleIngredientsList;
        itemProducts = itemProductsList;
        fluidProducts = fluidProductsList;
        particleProducts = particleProductsList;
    }

    @Override
    public List<SizedChanceItemIngredient> getItemIngredients() {
        return itemIngredients;
    }

    @Override
    public List<SizedChanceFluidIngredient> getFluidIngredients() {
        return fluidIngredients;
    }

    public SizedChanceFluidIngredient getFluidIngredient() {
        return fluidIngredients.getFirst();
    }

    @Override
    public List<ParticleStack> getParticleIngredients() {
        return particleIngredients;
    }

    @Override
    public List<SizedChanceItemIngredient> getItemProducts() {
        return itemProducts;
    }

    @Override
    public List<SizedChanceFluidIngredient> getFluidProducts() {
        return fluidProducts;
    }

    public SizedChanceFluidIngredient getFluidProduct() {
        return fluidProducts.getFirst();
    }

    @Override
    public List<ParticleStack> getParticleProducts() {
        return particleProducts;
    }

    public ParticleStack getParticleProduct() {
        return particleProducts.getFirst();
    }
//
//    @Override
//    public QMDRecipeMatchResult matchInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs, List<ParticleStack> particleInputs) {
//        return QMDRecipeHelper.matchIngredients(IngredientSorption.INPUT, itemIngredients, fluidIngredients, particleIngredients, itemInputs, fluidInputs, particleInputs);
//    }
//
//    @Override
//    public QMDRecipeMatchResult matchOutputs(List<ItemStack> itemOutputs, List<Tank> fluidOutputs, List<ParticleStack> particleOutputs) {
//        return QMDRecipeHelper.matchIngredients(IngredientSorption.OUTPUT, itemProducts, fluidProducts, particleIngredients, itemOutputs, fluidOutputs, particleOutputs);
//    }
//
//    @Override
//    public QMDRecipeMatchResult matchIngredients(List<SizedChanceItemIngredient> itemIngredients, List<SizedChanceFluidIngredient> fluidIngredients, List<ParticleStack> particleIngredients) {
//        return QMDRecipeHelper.matchIngredients(IngredientSorption.INPUT, this.itemIngredients, this.fluidIngredients, this.particleIngredients, itemIngredients, fluidIngredients, particleIngredients, isShapeless, extras);
//    }
//
//    @Override
//    public QMDRecipeMatchResult matchProducts(List<SizedChanceItemIngredient> itemProducts, List<SizedChanceFluidIngredient> fluidProducts, List<ParticleStack> particleProducts) {
//        return QMDRecipeHelper.matchIngredients(IngredientSorption.OUTPUT, this.itemProducts, this.fluidProducts, this.particleProducts, itemProducts, fluidProducts, particleProducts, isShapeless, extras);
//    }


//    /* ================================== Recipe Extras ===================================== */ TODO
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

    @Override
    public boolean matches(QMDRecipeInput input, Level level) {
        return QMDRecipeHelper.matchIngredients(IngredientSorption.INPUT, this.itemIngredients, this.fluidIngredients, this.particleIngredients, input.itemIngredients(), input.fluidIngredients(), input.particleStacks(), this).isMatch;
    }

    @Override
    public ItemStack assemble(QMDRecipeInput input, HolderLookup.Provider registries) {
        return itemProducts.isEmpty() ? ItemStack.EMPTY : itemProducts.getFirst().getStack().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return itemProducts.isEmpty() ? ItemStack.EMPTY : itemProducts.getFirst().getStack().copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        throw new RuntimeException("Basic Recipe Serializer Used!");
    }

    @Override
    public RecipeType<?> getType() {
        throw new RuntimeException("Basic Recipe Type Used!");
    }
}
