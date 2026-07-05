package lach_01298.qmd.item;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface ITickItem {
    void updateTick(ItemStack stack, BlockEntity tile, long worldTime);
}