package lach_01298.qmd.container.slot;

import com.nred.nuclearcraft.block_entity.inventory.ITileInventory;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotAmountFuel extends Slot {
    protected final QMDRecipeHandler<?> recipeHandler;

    public SlotAmountFuel(ITileInventory tile, QMDRecipeHandler<?> recipeHandler, int index, int xPosition, int yPosition) {
        super(tile, index, xPosition, yPosition);
        this.recipeHandler = recipeHandler;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.getCount() != 1) {
            return false;
        }

        if (stack.getItem() instanceof IItemParticleAmount) {
            return recipeHandler.isValidItemInput(IItemParticleAmount.cleanNBT(stack), ((ITileInventory) container).getTileWorld());
        }

        return false;
    }

    public int getSlotStackLimit() {
        return 1;
    }
}