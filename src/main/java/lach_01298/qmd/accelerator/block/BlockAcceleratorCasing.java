package lach_01298.qmd.accelerator.block;

import com.nred.nuclearcraft.block.GenericTooltipDeviceBlock;
import com.nred.nuclearcraft.registration.BlockRegistration;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorPartType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;

public class BlockAcceleratorCasing extends GenericTooltipDeviceBlock<Accelerator, IAcceleratorPartType> {
    public BlockAcceleratorCasing(@NotNull MultiblockPartProperties<IAcceleratorPartType> properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState().setValue(BlockRegistration.FRAME, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockRegistration.FRAME);
    }
}