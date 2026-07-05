package lach_01298.qmd.tile;

import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.recipe.types.AtmosphereCollectorRecipe;
import lach_01298.qmd.recipe.types.FluidCollectorInput;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Optional;

import static lach_01298.qmd.recipe.RecipeTypeRegistration.ATMOSPHERE_COLLECTOR_RECIPE_TYPE;
import static lach_01298.qmd.tile.QMDTiles.ATMOSPHERE_COLLECTOR_ENTITY_TYPE;

public class TileAtmosphereCollector extends TileFluidCollector {
    public TileAtmosphereCollector(BlockPos pos, BlockState blockState) {
        super(ATMOSPHERE_COLLECTOR_ENTITY_TYPE.get(), pos, blockState, "atmosphere_collector", -QMDStartupConfig.processor_power[1] * 20);
    }

    @Override
    public void process() {
        if (outputFluid != null && !getTanks().get(0).isFull()) {
            getEnergyStorage().changeEnergyStored(-QMDStartupConfig.processor_power[1]);

            if (getTanks().get(0).isEmpty()) {
                getTanks().get(0).changeFluidStored(outputFluid.getFluid(), (int) (outputFluid.getAmount() * efficiency));
            } else if (FluidStack.isSameFluidSameComponents(getTanks().get(0).getFluid(), outputFluid)) {
                getTanks().get(0).changeFluidAmount((int) (outputFluid.getAmount() * efficiency));
            }
        }
    }

    @Override
    public void checkEfficiency() {
        Iterable<BlockPos> checkArea = BlockPos.betweenClosed(this.worldPosition.offset(-2, 0, -2), this.worldPosition.offset(2, 4, 2));
        int occlusiveBlocks = 0;
        int checkedBlocks = 0;
        for (BlockPos otherPos : checkArea) {
            checkedBlocks++;
            BlockState state = level.getBlockState(otherPos);
            if (!state.isAir() && (state.isSolid() || state.isCollisionShapeFullBlock(level, otherPos))) {
                occlusiveBlocks++;
            }
        }
        efficiency = 1 - (occlusiveBlocks - 1) / (double) (checkedBlocks - 1);
    }

    @Override
    public void checkInputs() {
        ResourceLocation biome = level.getBiome(this.worldPosition).getKey().location();
        ResourceLocation dimension = level.dimension().location();

        Optional<RecipeHolder<AtmosphereCollectorRecipe>> recipe = level.getRecipeManager().getRecipeFor(ATMOSPHERE_COLLECTOR_RECIPE_TYPE.get(), new FluidCollectorInput(null, dimension, biome), level);
        if (recipe.isPresent()) {
            outputFluid = recipe.get().value().getOutputFluid();
        } else {
            outputFluid = FluidStack.EMPTY;
        }
    }
}