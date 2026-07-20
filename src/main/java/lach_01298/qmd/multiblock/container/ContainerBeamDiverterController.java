package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileBeamDiverterController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import static lach_01298.qmd.menu.QMDMenus.BEAM_DIVERTER_CONTROLLER_MENU;

public class ContainerBeamDiverterController extends MultiblockControllerMenu<Accelerator, AcceleratorUpdatePacket, TileBeamDiverterController, BlockEntityMenuInfo<TileBeamDiverterController>> {
    public ContainerBeamDiverterController(int containerId, Inventory inventory, final TileBeamDiverterController controller) {
        super(BEAM_DIVERTER_CONTROLLER_MENU.get(), containerId, inventory, controller);
    }

    // Client Constructor
    public ContainerBeamDiverterController(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, AbstractModBlockEntity.getGuiClientBlockEntity(extraData));
    }
}