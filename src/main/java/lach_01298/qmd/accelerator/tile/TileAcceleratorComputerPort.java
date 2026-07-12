package lach_01298.qmd.accelerator.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_COMPUTER_PORT;

public class TileAcceleratorComputerPort extends TileAcceleratorPart {
    public TileAcceleratorComputerPort(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_COMPUTER_PORT.get(), position, blockState, PartPosition.Type.Face);
    }
}