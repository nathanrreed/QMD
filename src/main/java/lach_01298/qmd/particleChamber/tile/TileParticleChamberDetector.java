package lach_01298.qmd.particleChamber.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_PARTICLE_CHAMBER_DETECTOR;

public class TileParticleChamberDetector extends TileParticleChamberPart {
    public final DetectorType detectorType;

    public TileParticleChamberDetector(BlockPos pos, BlockState state, DetectorType type) {
        super(TILE_PARTICLE_CHAMBER_DETECTOR.get(), pos, state);
        this.detectorType = type;
    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return position == PartPosition.Interior;
    }

    public boolean isValidPostion(BlockPos target) {
        if (detectorType.within) {
            return Util.getTaxiDistance(target, worldPosition) <= detectorType.taxiDistance;
        } else {
            return Util.getTaxiDistance(target, worldPosition) >= detectorType.taxiDistance;
        }
    }
}