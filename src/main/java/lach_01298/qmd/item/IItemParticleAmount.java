package lach_01298.qmd.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public interface IItemParticleAmount {

    /**
     * gets the items hardcoded capacity
     */
    int getItemCapacity(ItemStack stack);

    /**
     * gets the items capacity by reading its nbt data
     */
    static int getCapacity(ItemStack stack) {
        CompoundTag nbt = getStorageNBT(stack);
        if (nbt == null) {
            return 0;
        }

        return nbt.getInt("particle_capacity");
    }


    default int getAmountStored(ItemStack stack) {
        CompoundTag nbt = getStorageNBT(stack);
        if (nbt == null) {
            return 0;
        }
        return nbt.getInt("particle_amount");
    }

    default void setAmountStored(ItemStack stack, int amount) {
        if (stack.getItem() instanceof IItemParticleAmount) {
            CompoundTag nbt = getStorageNBT(stack);
            if (nbt != null) {
                if (amount < getCapacity(stack)) {
                    nbt.putInt("particle_amount", amount);
                } else {
                    nbt.putInt("particle_amount", getCapacity(stack));
                }
            }
        }
    }

    default ItemStack fill(ItemStack stack, int amount, String type) {
        if (getAmountStored(stack) + amount <= getCapacity(stack)) {
            setAmountStored(stack, getAmountStored(stack) + amount);
        }

        return stack;
    }

    default ItemStack use(ItemStack stack, int amount) {
        if (getAmountStored(stack) > amount) {
            setAmountStored(stack, getAmountStored(stack) - amount);
        } else if (getAmountStored(stack) == amount) {
            return getEmptyItem();
        }

        return stack;
    }

    default ItemStack getEmptyItem() {
        return ItemStack.EMPTY;
    }

    default boolean isEmptyItem(ItemStack stack) {
        return stack == getEmptyItem() || getAmountStored(stack) <= 0;
    }


    static ItemStack cleanNBT(ItemStack stack) {
        ItemStack newStack = stack.copy();
        newStack.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        return newStack;
    }

    static ItemStack fullItem(ItemStack stack) {
        if (stack.getItem() instanceof IItemParticleAmount) {
            setStorageNBT(stack);
            IItemParticleAmount item = (IItemParticleAmount) stack.getItem();
            item.setAmountStored(stack, item.getItemCapacity(stack));
        }
        return stack;
    }


    static CompoundTag getStorageNBT(ItemStack stack) {
        CustomData nbt = stack.get(DataComponents.CUSTOM_DATA);
        if (nbt == null || !nbt.contains("particle_storage")) {
            return null;
        }
        return nbt.getUnsafe().getCompound("particle_storage");
    }

    static void setStorageNBT(ItemStack stack) {
        if (!(stack.getItem() instanceof IItemParticleAmount)) {
            return;
        }

        IItemParticleAmount item = (IItemParticleAmount) stack.getItem();

        CompoundTag nbt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();

        if (!nbt.contains("particle_storage")) {
            CompoundTag storage = new CompoundTag();
            storage.putInt("particle_amount", 0);
            storage.putInt("particle_capacity", item.getItemCapacity(stack));
            nbt.put("particle_storage", storage);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        } else if (!nbt.getCompound("particle_storage").contains("particle_capacity")) {
            nbt.getCompound("particle_storage").putInt("particle_capacity", item.getItemCapacity(stack));
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        }
    }
}