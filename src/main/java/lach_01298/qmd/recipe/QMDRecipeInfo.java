//package lach_01298.qmd.recipe;
//
//import it.unimi.dsi.fastutil.ints.IntList;
//
//import javax.annotation.Nonnull;
//
//public class QMDRecipeInfo<T extends QMDRecipe> {
//    public final @Nonnull T recipe;
//
//    private final QMDRecipeMatchResult matchResult;
//
//    public QMDRecipeInfo(@Nonnull T recipe, QMDRecipeMatchResult matchResult) {
//        this.recipe = recipe;
//        this.matchResult = matchResult;
//    }
//
//    public IntList getItemInputOrder() {
//        return matchResult.itemInputOrder;
//    }
//
//    public IntList getFluidInputOrder() {
//        return matchResult.fluidInputOrder;
//    }
//
//    public IntList getParticleInputOrder() {
//        return matchResult.particleInputOrder;
//    }
//}