package lach_01298.qmd.item;

import com.nred.nuclearcraft.item.NCItem;

import com.nred.nuclearcraft.util.InfoHelper;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.util.Units;
import lach_01298.qmd.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ItemCell extends NCItem implements IItemParticleAmount {
    private final CellType cellType;

    public ItemCell(CellType cellType) {
        super(new Properties().stacksTo(1));
        this.cellType = cellType;
    }

    @Override
    public int getItemCapacity(ItemStack stack) {
        return cellType.getCapacity();
    }

    public CellType getCellType() {
        return cellType;
    }

    @Override
    public ItemStack fill(ItemStack stack, int amount, String type) {
        if (cellType == CellType.EMPTY) {
            CellType newCellType = CellType.EMPTY;
            for (CellType cellType : CellType.values()) {
                if (cellType.getSerializedName().equals(type)) {
                    newCellType = cellType;
                }
            }
            ItemStack newStack = new ItemStack(QMDItems.cells.get(newCellType).get(), 1);
            setAmountStored(newStack, amount);
            return newStack;
        }

        if (getAmountStored(stack) + amount <= getItemCapacity(stack)) {
            setAmountStored(stack, getAmountStored(stack) + amount);
        }

        return stack;
    }

    @Override
    public ItemStack getEmptyItem() {
        return new ItemStack(QMDItems.cells.get(CellType.EMPTY).get());
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Mth.ceil(Mth.clamp((double) getAmountStored(stack) / (float) this.getItemCapacity(stack), 0D, 1D) * 13f);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getAmountStored(stack) > 0;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return FastColor.ARGB32.lerp((float) getAmountStored(stack) / (float) getItemCapacity(stack), ChatFormatting.RED.getColor(), ChatFormatting.GREEN.getColor());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        InfoHelper.infoLine(tooltipComponents, ChatFormatting.DARK_GREEN, Component.translatable("info.qmd.item.amount", Units.getSIFormat(getAmountStored(stack), "pu"), Units.getSIFormat(getItemCapacity(stack), "pu")));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem) {
        if (QMDServerConfig.cell_lifetime > 0) {
            if (cellType == CellType.EMPTY) {
                if (entityItem.tickCount > QMDServerConfig.cell_lifetime || entityItem.isInLava() || entityItem.isOnFire() || !entityItem.isAlive()) {
                    explode(entityItem.level(), entityItem.position(), stack);
                    stack.shrink(stack.getCount());
                }
            }
        }
        return false;
    }

    public void explode(Level world, Vec3 pos, ItemStack stack) {
        if (!world.isClientSide()) {
            double size = cellType.getExplosionSize();

            if (IItemParticleAmount.getCapacity(stack) > 0) {
                size *= getAmountStored(stack) / (double) IItemParticleAmount.getCapacity(stack);
            }

            Util.createGammaFlash(world, pos, size, (float) (size * QMDServerConfig.cell_explosion_size), QMDServerConfig.cell_radiation * size);
        }
    }
}