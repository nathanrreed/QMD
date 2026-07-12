package lach_01298.qmd.accelerator.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_GLASS;

public class TileAcceleratorGlass extends TileAcceleratorPart {
    public TileAcceleratorGlass(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_GLASS.get(), position, blockState, PartPosition.Type.Face);
    }
}