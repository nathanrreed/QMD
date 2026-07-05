package lach_01298.qmd.tile;

import com.nred.nuclearcraft.block_entity.internal.fluid.FluidConnection;
import com.nred.nuclearcraft.block_entity.passive.TilePassiveAbstract;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static com.nred.nuclearcraft.config.NCConfig.passive_push;

public abstract class TileFluidCollector extends TilePassiveAbstract {
    protected double efficiency = 0D;

    protected FluidStack outputFluid;

    public TileFluidCollector(BlockEntityType<?> type, BlockPos pos, BlockState blockState, String collector_type, double energy_rate) {
        super(type, pos, blockState, collector_type, energy_rate, FluidIngredient.of(Fluids.WATER), 1);
    }

    @Override
    protected void initTileEnergyFluid(@NotNull IntList fluidCapacity, List<Set<ResourceLocation>> allowedFluids, @NotNull FluidConnection[] fluidConnections) {
        super.initTileEnergyFluid(IntList.of(32000), null, fluidConnections);
    }

    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        nbt.putDouble("efficiency", efficiency);
        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        efficiency = nbt.getDouble("efficiency");
    }

    @Override
    public void update() {
        if (!level.isClientSide()) {
            if (tickCount <= 0) {
                checkInputs();
                if (outputFluid != null) {
                    checkEfficiency();
                }
            }
            energyBool = changeEnergy(true);
            fluidBool = changeFluid(true);
            boolean wasProcessing = isActive, shouldUpdate = false;
            isActive = isRunning(energyBool, false, fluidBool);
            if (isActive) {
                process();
            }

            if (wasProcessing != isActive) {
                shouldUpdate = true;
                setActivity(isActive);
            }
            tickCount();

            if (tickCount == 0 && passive_push) {
                pushFluid();
            }

            if (shouldUpdate) {
                setChanged();
            }
        }
    }

    public abstract void checkEfficiency();

    public abstract void checkInputs();

    public abstract void process();

    @Override
    protected boolean changeFluid(boolean simulateChange) {
        if (outputFluid.isEmpty()) {
            return false;
        }
        return super.changeFluid(simulateChange);
    }

    public int getCollectionRate() {
        if (changeEnergy(true)) {
            if (outputFluid != null) {
                return (int) (outputFluid.getAmount() * efficiency);
            }
        }
        return 0;
    }

    @Override
    public double getFluidRate() {
        if (changeEnergy(true)) {
            if (outputFluid != null) {
                return (int) (outputFluid.getAmount() * efficiency);
            }
        }
        return 0;
    }

    public Fluid getCollectionFluid() {
        if (changeEnergy(true)) {
            if (outputFluid != null) {
                return outputFluid.getFluid();
            }
        }
        return null;
    }
}