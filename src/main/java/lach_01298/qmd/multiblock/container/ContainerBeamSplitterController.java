package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileBeamSplitterController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import static lach_01298.qmd.menu.QMDMenus.BEAM_SPLITTER_CONTROLLER_MENU;

public class ContainerBeamSplitterController extends MultiblockControllerMenu<Accelerator, AcceleratorUpdatePacket, TileBeamSplitterController, BlockEntityMenuInfo<TileBeamSplitterController>> {
    public ContainerBeamSplitterController(int containerId, Inventory inventory, final TileBeamSplitterController controller) {
        super(BEAM_SPLITTER_CONTROLLER_MENU.get(), containerId, inventory, controller);
    }

    // Client Constructor
    public ContainerBeamSplitterController(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, AbstractModBlockEntity.getGuiClientBlockEntity(extraData));
    }
}