package lach_01298.qmd.accelerator.tile;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.enums.BlockTypes.CoolerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_COOLER;

public class TileAcceleratorCooler extends TileAcceleratorPart implements IAcceleratorComponent {
    public final CoolerType coolerType;

    public boolean isSearched = false, isInValidPosition = false;

    public TileAcceleratorCooler(final BlockPos position, final BlockState blockState, CoolerType coolerType) {
        super(TILE_ACCELERATOR_COOLER.get(), position, blockState, PartPosition.Type.Interior);
        this.coolerType = coolerType;
    }

    @Override
    public boolean isFunctional() {
        return isInValidPosition;
    }

    @Override
    public void setFunctional(boolean functional) {
        isInValidPosition = false;
    }

    @Override
    public int getMaxOperatingTemp() {
        return Accelerator.MAX_TEMP;
    }

    public void coolerSearch(final ObjectSet<TileAcceleratorCooler> validCache, final ObjectSet<TileAcceleratorCooler> searchCache, final Long2ObjectMap<TileAcceleratorCooler> partFailCache, final Long2ObjectMap<TileAcceleratorCooler> assumedValidCache) {
        if (!isCoolerValid(partFailCache, assumedValidCache)) {
            return;
        }

        if (isSearched) {
            return;
        }

        isSearched = true;
        validCache.add(this);

        for (Direction dir : Direction.values()) {
            TileAcceleratorCooler part = getMultiblockController().get().getPartMap(TileAcceleratorCooler.class).get(getTilePos().relative(dir).asLong());
            if (part != null) {
                searchCache.add(part);
            }
        }
    }

    public boolean isCoolerValid(final Long2ObjectMap<TileAcceleratorCooler> partFailCache, final Long2ObjectMap<TileAcceleratorCooler> assumedValidCache) {
        if (partFailCache.containsKey(worldPosition.asLong())) {
            return isInValidPosition = false;
        } else if (coolerType.getRule().requiresRecheck()) {
            isInValidPosition = coolerType.getRule().satisfied(this, false);
            if (isInValidPosition) {
                assumedValidCache.put(worldPosition.asLong(), this);
            }
            return isInValidPosition;
        } else if (isInValidPosition) {
            return true;
        }
        return isInValidPosition = coolerType.getRule().satisfied(this, false);
    }

    public boolean isSearchRoot() {
        for (String dep : coolerType.getRule().getDependencies()) {
            if (dep.equals("magnet") || dep.equals("cavity") || dep.equals("yoke") || dep.equals("beam"))
                return true;
        }
        return false;
    }
}