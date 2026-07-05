package lach_01298.qmd.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.List;

public interface IItemMode {
    public default String getMode(ItemStack stack) {
        CompoundTag nbt = getStorageNBT(stack);
        if (nbt == null) {
            setStorageNBT(stack);
            nbt = getStorageNBT(stack);
        }

        return nbt.getString("mode");
    }


    public String getDefaultMode();

    public List<String> getModes();

    default boolean setMode(ItemStack stack, String mode) {
        if (stack.getItem() instanceof IItemMode) {
            CompoundTag nbt = getStorageNBT(stack);
            if (nbt != null) {
                if (getModes().contains(mode)) {
                    nbt.putString("mode", mode);
                    return true;
                }
            }
        }
        return false;
    }

    static CompoundTag getStorageNBT(ItemStack stack) {
        CompoundTag nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).getUnsafe();
        if (!nbt.contains("qmd_item_mode")) {
            return null;
        }
        return nbt.getCompound("qmd_item_mode");
    }

    static void setStorageNBT(ItemStack stack) {
        if (!(stack.getItem() instanceof IItemMode)) {
            return;
        }

        IItemMode item = (IItemMode) stack.getItem();

        CompoundTag nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        if (!nbt.contains("qmd_item_mode")) {
            CompoundTag storage = new CompoundTag();
            storage.putString("mode", item.getDefaultMode());
            nbt.put("qmd_item_mode", storage);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        } else if (!nbt.getCompound("qmd_item_mode").contains("mode")) {
            nbt.getCompound("qmd_item_mode").putString("mode", item.getDefaultMode());
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        }
    }


}
