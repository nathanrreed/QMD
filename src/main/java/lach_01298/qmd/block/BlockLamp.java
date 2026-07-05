package lach_01298.qmd.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import javax.annotation.Nullable;

import static com.nred.nuclearcraft.registration.BlockRegistration.ACTIVE;

public class BlockLamp extends net.minecraft.world.level.block.Block {
    public BlockLamp() {
        super(BlockBehaviour.Properties.of().strength(2, 15).lightLevel(s -> s.getValue(ACTIVE) ? 15 : 0).strength(0.3F).sound(SoundType.GLASS).isValidSpawn(Blocks::always));
        registerDefaultState(defaultBlockState().setValue(ACTIVE, true));

        //        @Override TODO
//        public int getHarvestLevel() {
//            return 0;
//        }
//
//        @Override
//        public String getHarvestTool() {
//            return "pickaxe";
//        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(ACTIVE, !context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide()) {
            boolean isOn = state.getValue(ACTIVE);
            if (isOn == level.hasNeighborSignal(pos)) {
                level.setBlock(pos, state.cycle(ACTIVE), 2);
            }
        }
    }
}