package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileDeceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import static lach_01298.qmd.menu.QMDMenus.DECELERATOR_CONTROLLER_MENU;

public class ContainerDeceleratorController extends MultiblockControllerMenu<Accelerator, AcceleratorUpdatePacket, TileDeceleratorController, BlockEntityMenuInfo<TileDeceleratorController>> {
    public ContainerDeceleratorController(int containerId, Inventory inventory, final TileDeceleratorController controller) {
        super(DECELERATOR_CONTROLLER_MENU.get(), containerId, inventory, controller);
    }

    // Client Constructor
    public ContainerDeceleratorController(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, AbstractModBlockEntity.getGuiClientBlockEntity(extraData));
    }
}
