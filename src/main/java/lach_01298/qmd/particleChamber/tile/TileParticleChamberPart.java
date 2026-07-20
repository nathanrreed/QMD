package lach_01298.qmd.particleChamber.tile;

import com.nred.nuclearcraft.block_entity.multiblock.AbstractPartBlockEntity;
import lach_01298.qmd.particleChamber.ParticleChamber;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TileParticleChamberPart extends AbstractPartBlockEntity<ParticleChamber> implements IParticleChamberPart {
    public TileParticleChamberPart(BlockEntityType<?> type, BlockPos position, BlockState blockState) {
        super(type, position, blockState);
    }

    @Override
    public ParticleChamber createController() {
        final Level myWorld = this.getLevel();

        if (null == myWorld) {
            throw new RuntimeException("Trying to create a Controller from a Part without a Level");
        }

        return new ParticleChamber(this.getLevel());
    }

    @Override
    public Class<ParticleChamber> getControllerType() {
        return ParticleChamber.class;
    }
}