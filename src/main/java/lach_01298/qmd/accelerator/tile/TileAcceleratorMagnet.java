package lach_01298.qmd.accelerator.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_MAGNET;

public class TileAcceleratorMagnet extends TileAcceleratorPart implements IAcceleratorComponent {
    public final MagnetType magnetType;
    public boolean isFunctional = false;

    public TileAcceleratorMagnet(final BlockPos position, final BlockState blockState, MagnetType magnetType) {
        super(TILE_ACCELERATOR_MAGNET.get(), position, blockState, PartPosition.Type.Interior);
        this.magnetType = magnetType;
    }

    @Override
    public boolean isFunctional() {
        return isFunctional;
    }

    @Override
    public void setFunctional(boolean func) {
        isFunctional = func;
    }

    @Override
    public int getMaxOperatingTemp() {
        return magnetType.getMaxOperatingTemp();
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
}