//package lach_01298.qmd.recipe.ingredient;
//
//import com.nred.nuclearcraft.recipe.IngredientMatchResult;
//import com.nred.nuclearcraft.recipe.IngredientSorption;
//import lach_01298.qmd.particle.ParticleStack;
//
//import java.util.List;
//
//public interface IParticleIngredient extends IIngredient<ParticleStack> {
//    @Override
//    public default ParticleStack getNextStack(int ingredientNumber) {
//        ParticleStack nextStack = getStack();
//        nextStack.setAmount(getNextStackSize(ingredientNumber));
//        return nextStack;
//    }
//
//    @Override
//    public default List<ParticleStack> getInputStackHashingList() {
//        return getInputStackList();
//    }
//
//    @Override
//    public IParticleIngredient getFactoredIngredient(int factor);
//
//    IngredientMatchResult match(Object object, IngredientSorption sorption);
//
//    IngredientMatchResult matchWithData(Object object, IngredientSorption type, List extras);
//}
