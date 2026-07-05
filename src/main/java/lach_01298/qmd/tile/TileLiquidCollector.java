package lach_01298.qmd.tile;

import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.recipe.types.FluidCollectorInput;
import lach_01298.qmd.recipe.types.LiquidCollectorRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Optional;

import static lach_01298.qmd.recipe.RecipeTypeRegistration.LIQUID_COLLECTOR_RECIPE_TYPE;
import static lach_01298.qmd.tile.QMDTiles.LIQUID_COLLECTOR_ENTITY_TYPE;

public class TileLiquidCollector extends TileFluidCollector {
    public TileLiquidCollector(BlockPos pos, BlockState blockState) {
        super(LIQUID_COLLECTOR_ENTITY_TYPE.get(), pos, blockState, "liquid_collector", -QMDStartupConfig.processor_power[2] * 20);
    }

    @Override
    public void process() {
        if (outputFluid != null && !getTanks().get(0).isFull()) {
            getEnergyStorage().changeEnergyStored(-QMDStartupConfig.processor_power[2]);

            if (getTanks().get(0).isEmpty()) {
                getTanks().get(0).changeFluidStored(outputFluid.getFluid(), (int) (outputFluid.getAmount() * efficiency));
            } else if (FluidStack.isSameFluidSameComponents(getTanks().get(0).getFluid(), outputFluid)) {
                getTanks().get(0).changeFluidAmount((int) (outputFluid.getAmount() * efficiency));
            }
        }
    }

    @Override
    public void checkEfficiency() {
        Iterable<BlockPos> checkArea = BlockPos.betweenClosed(this.worldPosition.offset(-2, -4, -2), this.worldPosition.offset(2, 0, 2));
        int fluidBlocks = 0;
        int checkedBlocks = 0;
        Block fluidBlock = level.getBlockState(this.worldPosition.below()).getBlock();
        for (BlockPos otherPos : checkArea) {
            checkedBlocks++;
            Block block = level.getBlockState(otherPos).getBlock();
            if (fluidBlock == block) {
                fluidBlocks++;
            }
        }
        efficiency = (fluidBlocks) / (double) (checkedBlocks - 1);
    }

    @Override
    public void checkInputs() {
        BlockState blockState = level.getBlockState(this.worldPosition.below());
        ResourceLocation biome = level.getBiome(this.worldPosition).getKey().location();
        ResourceLocation dimension = level.dimension().location();

        Optional<RecipeHolder<LiquidCollectorRecipe>> recipe = level.getRecipeManager().getRecipeFor(LIQUID_COLLECTOR_RECIPE_TYPE.get(), new FluidCollectorInput(blockState.getBlock(), dimension, biome), level);
        if (recipe.isPresent()) {
            outputFluid = recipe.get().value().getOutputFluid();
        } else {
            outputFluid = FluidStack.EMPTY;
        }
    }


}
