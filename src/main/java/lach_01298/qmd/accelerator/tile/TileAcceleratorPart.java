package lach_01298.qmd.accelerator.tile;


import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.multiblock.TileCuboidalOrToroidalMultiblockPart;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileAcceleratorPart extends TileCuboidalOrToroidalMultiblockPart<Accelerator> implements IAcceleratorPart {
    public TileAcceleratorPart(final BlockEntityType<?> type, final BlockPos position, final BlockState blockState, PartPosition.Type positionType) {
        super(type, position, blockState, positionType, 5);
    }

    @Override
    public Accelerator createController() {
        final Level myWorld = this.getLevel();

        if (null == myWorld) {
            throw new RuntimeException("Trying to create a Controller from a Part without a Level");
        }

        return new Accelerator(this.getLevel());
    }

    @Override
    public Class<Accelerator> getControllerType() {
        return Accelerator.class;
    }
}