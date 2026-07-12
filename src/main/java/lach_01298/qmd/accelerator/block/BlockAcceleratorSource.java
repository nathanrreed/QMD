package lach_01298.qmd.accelerator.block;

import com.nred.nuclearcraft.block.GenericDirectionalTooltipDeviceBlock;
import com.nred.nuclearcraft.registration.BlockRegistration;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorPartType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockAcceleratorSource extends GenericDirectionalTooltipDeviceBlock<Accelerator, IAcceleratorPartType> {
    public BlockAcceleratorSource(MultiblockPartProperties<IAcceleratorPartType> properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockRegistration.FACING_ALL, context.getNearestLookingDirection());
    }
}