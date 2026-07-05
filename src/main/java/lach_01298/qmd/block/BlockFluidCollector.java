package lach_01298.qmd.block;

import com.nred.nuclearcraft.block.tile.SimpleTileBlock;
import com.nred.nuclearcraft.block_entity.passive.TilePassiveAbstract;
import com.nred.nuclearcraft.util.UnitHelper;
import lach_01298.qmd.tile.TileFluidCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class BlockFluidCollector extends SimpleTileBlock<TilePassiveAbstract> {
    public BlockFluidCollector(String type) {
        super(type);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return ItemInteractionResult.FAIL;
        }

        if (player.getItemInHand(hand).isEmpty()) {
            if (!level.isClientSide() && level.getBlockEntity(pos) instanceof TileFluidCollector collector) {
                Fluid fluid = collector.getCollectionFluid();
                if (fluid != null && fluid != Fluids.EMPTY) {
                    Component name = fluid.getFluidType().getDescription();
                    player.sendSystemMessage(Component.translatable("message.qmd.collector", name, UnitHelper.prefix(collector.getCollectionRate(), 5, "B/t", -1)));
                } else {
                    player.sendSystemMessage(Component.translatable("message.qmd.collector_no_fluid"));
                }
            }
            return ItemInteractionResult.CONSUME;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}