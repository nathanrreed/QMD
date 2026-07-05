package lach_01298.qmd.item;

import com.nred.nuclearcraft.item.NCItem;
import com.nred.nuclearcraft.util.InfoHelper;
import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;


public class ItemSource extends NCItem implements IItemParticleAmount {
    private final SourceType sourceType;

    public ItemSource(SourceType sourceType) {
        super(new Properties().stacksTo(16));
        this.sourceType = sourceType;
    }

    @Override
    public int getItemCapacity(ItemStack stack) {
        return sourceType.getCapacity();
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
}