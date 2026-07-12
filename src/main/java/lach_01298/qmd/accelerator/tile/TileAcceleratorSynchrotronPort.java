package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.List;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_SYNCHROTRON_PORT;

public class TileAcceleratorSynchrotronPort extends TileAcceleratorPart implements ITileParticleStorage {
    private final @Nonnull List<ParticleStorageAccelerator> backupTanks = Lists.newArrayList(new ParticleStorageAccelerator());

    public TileAcceleratorSynchrotronPort(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_SYNCHROTRON_PORT.get(), position, blockState, PartPosition.Type.Face);
    }

    public ParticleStorage getCapability(Direction side) {
        if (!getParticleBeams().isEmpty()) {
            return getParticleBeams().get(0);
        }
        return null;
    }

    @Override
    public List<? extends ParticleStorage> getParticleBeams() {
        if (!isMachineAssembled())
            return backupTanks;
        return getMultiblockController().get().beams.subList(2, 3);
    }
}