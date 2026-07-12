package lach_01298.qmd.accelerator.block;

import com.nred.nuclearcraft.block.GenericActiveDirectionalTooltipDeviceBlock;
import com.nred.nuclearcraft.util.BlockHelper;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorPartType;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRedstonePort;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;

public class BlockAcceleratorRedstonePort extends GenericActiveDirectionalTooltipDeviceBlock<Accelerator, IAcceleratorPartType> {

    public BlockAcceleratorRedstonePort(MultiblockPartProperties<IAcceleratorPartType> properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        BlockHelper.setDefaultFacing(level, pos, state, FACING_ALL);
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) { // TODO check
        if (level.getBlockEntity(pos) instanceof TileAcceleratorRedstonePort port) {
            return port.getRedstoneLevel();
        }
        return 0;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return direction != null;
    }
}