package lach_01298.qmd.accelerator.tile;

import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.multiblock.container.ContainerBeamDiverterController;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;
import static lach_01298.qmd.tile.QMDTiles.TILE_BEAM_DIVERTER_CONTROLLER;

public class TileBeamDiverterController extends TileAcceleratorPart implements IAcceleratorController<TileBeamDiverterController> {
    protected final BlockEntityMenuInfo<TileBeamDiverterController> info = BlockEntityInfoHandler.getTileContainerInfo("beam_diverter_controller");

    public TileBeamDiverterController(final BlockPos position, final BlockState blockState) {
        super(TILE_BEAM_DIVERTER_CONTROLLER.get(), position, blockState, PartPosition.Type.Face);
    }

    @Override
    public String getLogicID() {
        return "beam_diverter";
    }

    @Override
    public BlockEntityMenuInfo<TileBeamDiverterController> getContainerInfo() {
        return info;
    }

    @Override
    public void onPreMachineAssembled(Accelerator multiblock) {
        super.onPreMachineAssembled(multiblock);
        if (!level.isClientSide() && getPartPosition().getDirection().isPresent()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FACING_ALL, getPartPosition().getDirection().get()), 2);
        }
    }

    @Override
    public void onBlockNeighborChanged(BlockState state, Level world, BlockPos pos, BlockPos fromPos) {
        super.onBlockNeighborChanged(state, world, pos, fromPos);
        getMultiblockController().ifPresent(Accelerator::updateActivity);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(QMD.MOD_ID + ".menu.title.beam_diverter_controller");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ContainerBeamDiverterController(containerId, inventory, this);
    }

    public boolean canOpenGui(Level world, BlockPos position, BlockState state) {
        return this.isMachineAssembled();
    }
}