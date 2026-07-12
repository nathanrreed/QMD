package lach_01298.qmd.block;

import com.nred.nuclearcraft.block.tile.BlockTile;
import com.nred.nuclearcraft.block_entity.ITickable;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockCreativeParticleSource extends BlockTile {
    public BlockCreativeParticleSource() {
        super(p -> p.strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn(Blocks::never));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileCreativeParticleSource(pos, state);
    }

    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide ? (level1, pos, state1, blockEntity) -> ((ITickable) blockEntity).update() : null;
    }
}
