package lach_01298.qmd.fluid;

import com.nred.nuclearcraft.block.fluid.NCFluidBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import static lach_01298.qmd.QMDDamageSources.ANTIMATTER_ANNIHLATION;

public class BlockFluidExotic extends NCFluidBlock {
    public BlockFluidExotic(FlowingFluid fluid, BlockBehaviour.Properties properties) {
        super(fluid, properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.hurt(level.damageSources().source(ANTIMATTER_ANNIHLATION), 10000F);
        level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 10f, Level.ExplosionInteraction.BLOCK);
    }

    @Override
    public BlockState getSourceMixingState() {
        return Blocks.OBSIDIAN.defaultBlockState();
    }

    @Override
    public BlockState getFlowingMixingState() {
        return Blocks.COBBLESTONE.defaultBlockState();
    }

    @Override
    protected boolean canSetFireToSurroundings(Level level, BlockPos blockPos, BlockState blockState, RandomSource randomSource) {
        return false;
    }

    @Override
    public BlockState getFlowingIntoWaterState() {
        return null;
    }
}
