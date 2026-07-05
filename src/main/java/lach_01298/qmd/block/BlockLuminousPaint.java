package lach_01298.qmd.block;

import lach_01298.qmd.enums.MaterialTypes.LuminousPaintType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;

public class BlockLuminousPaint extends Block {
    protected static final Map<Direction, AABB> PAINT_AABB = Map.of(
            Direction.DOWN, new AABB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D),
            Direction.UP, new AABB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D),
            Direction.NORTH, new AABB(0.0D, 0.0D, 0.9375D, 1.0D, 1.0D, 1.0D),
            Direction.SOUTH, new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0625D),
            Direction.WEST, new AABB(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
            Direction.EAST, new AABB(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D)
    );
    private final LuminousPaintType paintType;

    public BlockLuminousPaint(LuminousPaintType paintType) {
        super(Properties.of().pushReaction(PushReaction.DESTROY).lightLevel(e -> 13).strength(0.1f, 0.1f).noCollission());
        this.paintType = paintType;
        registerDefaultState(defaultBlockState().setValue(FACING_ALL, Direction.UP));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.create(PAINT_AABB.get(state.getValue(FACING_ALL)));
    }

    @Override
    protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    protected boolean isOcclusionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING_ALL);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING_ALL, context.getClickedFace());
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return WallTorchBlock.canSurvive(level, pos, state.getValue(FACING_ALL));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide()) {
            if (!state.canSurvive(level, pos)) {
                dropResources(state, level, pos);
                level.removeBlock(pos, false);
            }
        }
    }
}