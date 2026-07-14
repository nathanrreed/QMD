package lach_01298.qmd;

import com.nred.nuclearcraft.util.InfoHelper;
import it.zerono.mods.zerocore.base.multiblock.part.GenericDeviceBlock;
import lach_01298.qmd.enums.BlockTypes.CoolerType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;

public class QMDTooltipHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void addAdditionalTooltips(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        ItemStack stack = event.getItemStack();
        addPlacementRuleTooltip(tooltip, stack, event.getFlags().isAdvanced());
    }

    // Placement Rule Tooltips

    @OnlyIn(Dist.CLIENT)
    private static void addPlacementRuleTooltip(List<Component> tooltip, ItemStack stack, boolean isAdvanced) {
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof GenericDeviceBlock<?, ?> block && block.getMultiblockVariant().isPresent()) {
            String rule = null;
            if (block.getMultiblockVariant().get() instanceof CoolerType cooler) {
                rule = cooler.getTooltipRule();
            }
//            else if (block.getMultiblockVariant().get() instanceof HeaterType heater) { TODO
//                rule = heater.getTooltipRule();
//            }
            if (rule != null) {

                // Fixes shift info being under advanced lines
                List<Component> advanced = new ArrayList<>();
                if (isAdvanced) {
                    advanced.add(tooltip.removeLast());
                }

                InfoHelper.infoFull(tooltip, ChatFormatting.AQUA, Component.literal(rule));

                tooltip.addAll(advanced);
            }
        }
    }
}
