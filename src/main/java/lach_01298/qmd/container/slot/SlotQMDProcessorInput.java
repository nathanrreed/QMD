package lach_01298.qmd.container.slot;

import com.nred.nuclearcraft.block_entity.inventory.ITileInventory;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotQMDProcessorInput<T extends QMDRecipe> extends Slot {
    public final QMDRecipeHandler<T> recipeHandler;
    public final int stackSize;

    public SlotQMDProcessorInput(ITileInventory tile, QMDRecipeHandler<T> recipeHandler, int slotIndex, int xPosition, int yPosition) {
        super(tile, slotIndex, xPosition, yPosition);
        this.recipeHandler = recipeHandler;
        stackSize = 64;
    }

    public SlotQMDProcessorInput(ITileInventory tile, QMDRecipeHandler<T> recipeHandler, int slotIndex, int xPosition, int yPosition, int stackSize) {
        super(tile, slotIndex, xPosition, yPosition);
        this.recipeHandler = recipeHandler;
        this.stackSize = stackSize;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        ItemStack item = stack.copy();

        return recipeHandler.isValidItemInput(IItemParticleAmount.cleanNBT(item), ((ITileInventory) container).getTileWorld());
    }

    public int getSlotStackLimit() {
        return stackSize;
    }
}