package lach_01298.qmd.accelerator.block;

import com.nred.nuclearcraft.block.GenericActiveDirectionalTooltipDeviceBlock;
import com.nred.nuclearcraft.block.IActivatable;
import com.nred.nuclearcraft.registration.BlockRegistration;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorPartType;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockAcceleratorComputerPort extends GenericActiveDirectionalTooltipDeviceBlock<Accelerator, IAcceleratorPartType> implements IActivatable {
    public BlockAcceleratorComputerPort(@NotNull MultiblockPartProperties<IAcceleratorPartType> properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockRegistration.FACING_ALL, Direction.NORTH).setValue(BlockRegistration.ACTIVE, true));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockRegistration.FACING_ALL, context.getNearestLookingDirection().getOpposite()).setValue(BlockRegistration.ACTIVE, true);
    }
}
