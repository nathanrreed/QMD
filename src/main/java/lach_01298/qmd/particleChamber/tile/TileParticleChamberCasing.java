package lach_01298.qmd.particleChamber.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.nred.nuclearcraft.registration.BlockRegistration.FRAME;
import static lach_01298.qmd.tile.QMDTiles.TILE_PARTICLE_CHAMBER_CASING;

public class TileParticleChamberCasing extends TileParticleChamberPart {

    public TileParticleChamberCasing(BlockPos pos, BlockState state) {
        super(TILE_PARTICLE_CHAMBER_CASING.get(), pos, state);
    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return position.isFace() || position.isFrame();
    }

    @Override
    public void onPreMachineAssembled(ParticleChamber controller) {
        super.onPreMachineAssembled(controller);
        if (!level.isClientSide() && getPartPosition().isFrame()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FRAME, true), 2);
        }
    }

    @Override
    public void onPreMachineBroken() {
        if (!level.isClientSide() && getPartPosition().isFrame()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FRAME, false), 2);
        }
        super.onPreMachineBroken();
    }
}