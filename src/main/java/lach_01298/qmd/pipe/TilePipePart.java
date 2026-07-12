package lach_01298.qmd.pipe;

import com.nred.nuclearcraft.block_entity.multiblock.AbstractPartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TilePipePart extends AbstractPartBlockEntity<Pipe> {
    public TilePipePart(final BlockEntityType<?> type, final BlockPos position, final BlockState blockState) {
        super(type, position, blockState);
    }

    @Override
    public Pipe createController() {
        final Level myWorld = this.getLevel();

        if (null == myWorld) {
            throw new RuntimeException("Trying to create a Controller from a Part without a Level");
        }

        return new Pipe(this.getLevel());
    }

    @Override
    public void onAttached(Pipe newMultiblock) {
        super.onAttached(newMultiblock);

    }

    @Override
    public Class<Pipe> getControllerType() {
        return Pipe.class;
    }
}