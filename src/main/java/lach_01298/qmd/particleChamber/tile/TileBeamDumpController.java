package lach_01298.qmd.particleChamber.tile;

import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.QMD;
import lach_01298.qmd.particleChamber.ParticleChamber;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;
import static lach_01298.qmd.tile.QMDTiles.TILE_BEAM_DUMP_CONTROLLER;

public class TileBeamDumpController extends TileParticleChamberPart implements IParticleChamberController<TileBeamDumpController> {
    protected final BlockEntityMenuInfo<TileBeamDumpController> info = BlockEntityInfoHandler.getTileContainerInfo("beam_dump_controller");

    public TileBeamDumpController(BlockPos pos, BlockState state) {
        super(TILE_BEAM_DUMP_CONTROLLER.get(), pos, state);
    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return position.isFace();
    }

    @Override
    public String getLogicID() {
        return "beam_dump";
    }

    @Override
    public BlockEntityMenuInfo<TileBeamDumpController> getContainerInfo() {
        return info;
    }

    @Override
    public void onPreMachineAssembled(ParticleChamber controller) {
        super.onPreMachineAssembled(controller);
        if (!level.isClientSide() && getPartPosition().getDirection().isPresent()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FACING_ALL, getPartPosition().getDirection().get()), 2);
        }
    }

    @Override
    public void onBlockNeighborChanged(BlockState state, Level world, BlockPos pos, BlockPos fromPos) {
        super.onBlockNeighborChanged(state, world, pos, fromPos);
        if (getMultiblockController().isPresent()) getMultiblockController().get().updateActivity();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(QMD.MOD_ID + ".menu.title.beam_dump_controller");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return info.createMenu(containerId, inventory, this);
    }

    @Override
    public boolean canOpenGui(Level world, BlockPos position, BlockState state) {
        return isMachineAssembled();
    }
}