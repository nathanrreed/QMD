package lach_01298.qmd.accelerator.tile;

import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.registration.BlockRegistration;
import com.nred.nuclearcraft.render.BlockHighlightTracker;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.multiblock.container.ContainerDeceleratorController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import static lach_01298.qmd.tile.QMDTiles.TILE_DECELERATOR_CONTROLLER;

public class TileDeceleratorController extends TileAcceleratorPart implements IAcceleratorController<TileDeceleratorController> {
    protected final BlockEntityMenuInfo<TileDeceleratorController> info = BlockEntityInfoHandler.getTileContainerInfo("decelerator_controller");

    public TileDeceleratorController(final BlockPos position, final BlockState blockState) {
        super(TILE_DECELERATOR_CONTROLLER.get(), position, blockState, PartPosition.Type.Face);
    }

    @Override
    public String getLogicID() {
        return "decelerator";
    }

    @Override
    public BlockEntityMenuInfo<TileDeceleratorController> getContainerInfo() {
        return info;
    }

    @Override
    public void onPreMachineAssembled(Accelerator multiblock) {
        super.onPreMachineAssembled(multiblock);
        if (!level.isClientSide() && getPartPosition().getDirection().isPresent()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(BlockRegistration.FACING_ALL, getPartPosition().getDirection().get()), 2);
        }
    }

    @Override
    public boolean onUseMultitool(net.minecraft.world.item.ItemStack multitool, ServerPlayer player, Level level, Direction facing, BlockPos hitPos) {
        if (player.isCrouching() && this.isMachineAssembled()) {
            int invalidAmount = 0;
            for (TileAcceleratorCooler cooler : getMultiblockController().get().getPartMap(TileAcceleratorCooler.class).values()) {
                if (!cooler.isFunctional()) {
                    BlockHighlightTracker.sendPacket(player, cooler.getBlockPos(), 10000);
                    invalidAmount++;
                }
            }
            player.sendSystemMessage(Component.translatable("qmd.multiblock_validation.accelerator.invalid_coolers", invalidAmount));
            return true;
        }

        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(QMD.MOD_ID + ".menu.title.decelerator_controller");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ContainerDeceleratorController(containerId, inventory, this);
    }

    public boolean canOpenGui(Level world, BlockPos position, BlockState state) {
        return this.isMachineAssembled();
    }
}