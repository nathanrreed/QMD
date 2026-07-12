package lach_01298.qmd.pipe;


import com.nred.nuclearcraft.block.GenericAxisTooltipDeviceBlock;
import it.zerono.mods.zerocore.base.multiblock.part.INeverCauseRenderingSkip;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.nred.nuclearcraft.registration.BlockRegistration.AXIS_ALL;


public class BlockBeamline extends GenericAxisTooltipDeviceBlock<Pipe, IPipePartType> implements INeverCauseRenderingSkip {
    protected static final AABB[] BOUNDING_BOXES = new AABB[]{
            new AABB(0.0D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D),
            new AABB(0.1875D, 0D, 0.1875D, 0.8125D, 1D, 0.8125D),
            new AABB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125d, 1.0D)};

    public BlockBeamline(@NotNull MultiblockPartProperties<IPipePartType> properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Axis state = null;
        for (Direction face : Direction.values()) {
            if (context.getLevel().getBlockEntity(context.getClickedPos().relative(face)) instanceof TileBeamline) {
                state = context.getLevel().getBlockState(context.getClickedPos().relative(face)).getValue(AXIS_ALL);
            }
        }
        if (state != null) {
            return defaultBlockState().setValue(AXIS_ALL, state);
        }

        return defaultBlockState().setValue(AXIS_ALL, context.getNearestLookingDirection().getAxis());
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
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.create(BOUNDING_BOXES[getBoundingBoxIdx(state)]);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        int amount = 0;
        int facingCorrect = 0;
        for (Direction face : Direction.values()) {
            if (level.getBlockEntity(pos.relative(face)) instanceof TileBeamline) {
                amount++;
            }
        }
        if (amount <= 2) {
            for (Direction face : Direction.values()) {
                Axis axis = null;
                if (level.getBlockEntity(pos.relative(face)) instanceof TileBeamline) {
                    if (axis == null) {
                        if (level.getBlockState(pos.relative(face)).getValue(AXIS_ALL) == face.getAxis()) {
                            axis = level.getBlockState(pos.relative(face)).getValue(AXIS_ALL);
                        }
                    }
                    if (axis == level.getBlockState(pos.relative(face)).getValue(AXIS_ALL)) {
                        facingCorrect++;
                    }
                }
            }
        }
        return facingCorrect == amount;
    }

    private static int getBoundingBoxIdx(BlockState state) {
        return switch (state.getValue(AXIS_ALL)) {
            case X -> 0;
            case Y -> 1;
            case Z -> 2;
        };
    }
}