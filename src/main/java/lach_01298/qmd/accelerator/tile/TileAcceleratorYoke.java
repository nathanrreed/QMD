package lach_01298.qmd.accelerator.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.accelerator.Accelerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_YOKE;

public class TileAcceleratorYoke extends TileAcceleratorPart implements IAcceleratorComponent {
    private boolean isFunctional;

    public TileAcceleratorYoke(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_YOKE.get(), position, blockState, PartPosition.Type.Interior);
        isFunctional = false;
    }

    @Override
    public boolean isFunctional() {
        return isFunctional && this.isMachineAssembled();
    }

    @Override
    public void setFunctional(boolean func) {
        isFunctional = func;
    }

    // NBT

    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        nbt.putBoolean("isFunctional", isFunctional);
        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        isFunctional = nbt.getBoolean("isFunctional");
    }

    @Override
    public int getMaxOperatingTemp() {
        return Accelerator.MAX_TEMP;
    }
}