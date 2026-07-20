package lach_01298.qmd.particleChamber.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_PARTICLE_CHAMBER;

public class TileParticleChamber extends TileParticleChamberPart {
    public TileParticleChamber(BlockPos pos, BlockState state) {
        super(TILE_PARTICLE_CHAMBER.get(), pos, state);
    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return position == PartPosition.Interior;
    }
}