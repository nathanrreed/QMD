package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileDecayChamberController;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import static lach_01298.qmd.menu.QMDMenus.DECAY_CHAMBER_CONTROLLER_MENU;

public class ContainerDecayChamberController extends MultiblockControllerMenu<ParticleChamber, ParticleChamberUpdatePacket, TileDecayChamberController, BlockEntityMenuInfo<TileDecayChamberController>> {
    public ContainerDecayChamberController(int containerId, Inventory inventory, final TileDecayChamberController controller) {
        super(DECAY_CHAMBER_CONTROLLER_MENU.get(), containerId, inventory, controller);
    }

    // Client Constructor
    public ContainerDecayChamberController(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, AbstractModBlockEntity.getGuiClientBlockEntity(extraData));
    }
}