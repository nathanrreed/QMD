//package lach_01298.qmd.recipe;
//
//import it.unimi.dsi.fastutil.ints.IntList;
//
//public class QMDRecipeMatchResult {
//    public static final QMDRecipeMatchResult FAIL = new QMDRecipeMatchResult(false, AbstractQMDRecipeHandler.INVALID, AbstractQMDRecipeHandler.INVALID, AbstractQMDRecipeHandler.INVALID);
//
//    public final boolean isMatch;
//
//    public final IntList itemInputOrder, fluidInputOrder, particleInputOrder;
//
//    public QMDRecipeMatchResult(boolean isMatch, IntList itemInputOrder, IntList fluidInputOrder, IntList particleInputOrder) {
//        this.isMatch = isMatch;
//        this.itemInputOrder = itemInputOrder;
//        this.fluidInputOrder = fluidInputOrder;
//        this.particleInputOrder = particleInputOrder;
//    }
//}