package lach_01298.qmd.accelerator.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.accelerator.Accelerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.nred.nuclearcraft.registration.BlockRegistration.FRAME;
import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_CASING;

public class TileAcceleratorCasing extends TileAcceleratorPart {
    public TileAcceleratorCasing(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_CASING.get(), position, blockState, PartPosition.Type.Frame); // TODO check that frame works!
    }

    @Override
    public void onPreMachineAssembled(Accelerator multiblock) {
        super.onPreMachineAssembled(multiblock);
        if (!level.isClientSide() && getPartPosition().isFrame()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FRAME, true), 2);
        }
    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return position.isFace() || super.isGoodForPosition(position, validatorCallback);
    }

    public boolean isGoodForSides() {
        return true;
    }

    public boolean isGoodForTop() {
        return true;
    }

    public boolean isGoodForBottom() {
        return true;
    }

    @Override
    public void onPreMachineBroken() {
        if (!level.isClientSide() && getPartPosition().isFrame()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FRAME, false), 2);
        }
        super.onPreMachineBroken();
    }
}