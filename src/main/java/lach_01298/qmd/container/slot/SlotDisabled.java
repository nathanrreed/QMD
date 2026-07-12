package lach_01298.qmd.container.slot;

import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SlotDisabled extends Slot {
    public SlotDisabled(WorldlyContainer inventoryIn, int index) {
        super(inventoryIn, index, 0, 0);

    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isActive() {
        return false;
    }
}