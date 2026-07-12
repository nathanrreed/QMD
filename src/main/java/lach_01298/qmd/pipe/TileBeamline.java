package lach_01298.qmd.pipe;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageBeamline;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static com.nred.nuclearcraft.registration.BlockRegistration.AXIS_ALL;
import static lach_01298.qmd.tile.QMDTiles.TILE_BEAMLINE;

public class TileBeamline extends TilePipePart implements IPipeController<TileBeamline>, ITileParticleStorage {
    protected final BlockEntityMenuInfo<TileBeamline> info = BlockEntityInfoHandler.getTileContainerInfo("beamline");

    private final @Nonnull List<ParticleStorageBeamline> backupTanks = Lists.newArrayList(new ParticleStorageBeamline(1));

    public TileBeamline(BlockPos position, BlockState blockState) {
        super(TILE_BEAMLINE.get(), position, blockState);
    }


    @Override
    public String getLogicID() {
        return "beamline";
    }

    @Override
    public BlockEntityMenuInfo<TileBeamline> getContainerInfo() {
        return info;
    }

    public ParticleStorage getCapability(@Nullable Direction side) {
        BeamlineLogic logic = null;
        Optional<Pipe> pipe = getMultiblockController();
        if (pipe.isPresent()) {
            if (pipe.get().getLogic() instanceof BeamlineLogic beamlineLogic) {
                logic = beamlineLogic;
            }
        }

        if (logic != null) {
            if (side.getAxis() == logic.getAxis()) {
                if (!getParticleBeams().isEmpty()) {
                    return getParticleBeams().get(0);
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean onUseMultitool(net.minecraft.world.item.ItemStack multitool, ServerPlayer player, Level level, Direction facing, BlockPos hitPos) {
        Optional<Pipe> multiblock = getMultiblockController();
        if (multiblock.isPresent()) {
            if (multiblock.get().getPartsCount() == 1 && multiblock.get().getLogic() instanceof BeamlineLogic) {
                Axis axis = level.getBlockState(worldPosition).getValue(AXIS_ALL);
                axis = switch (axis) {
                    case X -> Axis.Y;
                    case Y -> Axis.Z;
                    case Z -> Axis.X;
                };
                level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(AXIS_ALL, axis));
                ((BeamlineLogic) multiblock.get().getLogic()).setAxis(axis);
                markDirtyAndNotify(true);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<? extends ParticleStorage> getParticleBeams() {
        if (!isMachineAssembled())
            return backupTanks;

        if (getMultiblockController().get().getLogic() instanceof BeamlineLogic logic) {
            return Lists.newArrayList(logic.storage);
        }

        return backupTanks;
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public @org.jspecify.annotations.Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }
}