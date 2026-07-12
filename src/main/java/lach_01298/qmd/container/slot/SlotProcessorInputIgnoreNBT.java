package lach_01298.qmd.container.slot;

import com.nred.nuclearcraft.block_entity.inventory.ITileInventory;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import net.minecraft.world.item.ItemStack;

public class SlotProcessorInputIgnoreNBT<T extends QMDRecipe> extends SlotQMDProcessorInput<T> {
    public SlotProcessorInputIgnoreNBT(ITileInventory tile, QMDRecipeHandler<T> recipeHandler, int index, int xPosition, int yPosition) {
        super(tile, recipeHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        ItemStack copy = stack.copy();
        return true; // recipeHandler.isValidItemInput(IItemAmount.cleanNBT(copy));
    }
}
