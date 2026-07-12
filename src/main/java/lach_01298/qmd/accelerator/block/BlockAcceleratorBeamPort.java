package lach_01298.qmd.accelerator.block;

import com.nred.nuclearcraft.block.GenericTooltipDeviceBlock;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorPartType;
import lach_01298.qmd.enums.EnumTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import static lach_01298.qmd.block.BlockProperties.IO;

public class BlockAcceleratorBeamPort extends GenericTooltipDeviceBlock<Accelerator, IAcceleratorPartType> {
    public BlockAcceleratorBeamPort(MultiblockPartProperties<IAcceleratorPartType> properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState().setValue(IO, EnumTypes.IOType.DISABLED));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IO);
    }
}