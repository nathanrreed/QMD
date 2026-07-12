package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileRingAcceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import static lach_01298.qmd.menu.QMDMenus.RING_ACCELERATOR_CONTROLLER_MENU;

public class ContainerRingAcceleratorController extends MultiblockControllerMenu<Accelerator, AcceleratorUpdatePacket, TileRingAcceleratorController, BlockEntityMenuInfo<TileRingAcceleratorController>> {
    public ContainerRingAcceleratorController(int containerId, Inventory inventory, final TileRingAcceleratorController controller) {
        super(RING_ACCELERATOR_CONTROLLER_MENU.get(), containerId, inventory, controller);
    }

    // Client Constructor
    public ContainerRingAcceleratorController(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, AbstractModBlockEntity.getGuiClientBlockEntity(extraData));
    }
}
