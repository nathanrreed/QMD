package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileCollisionChamberController;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import static lach_01298.qmd.menu.QMDMenus.COLLISION_CHAMBER_CONTROLLER_MENU;

public class ContainerCollisionChamberController extends MultiblockControllerMenu<ParticleChamber, ParticleChamberUpdatePacket, TileCollisionChamberController, BlockEntityMenuInfo<TileCollisionChamberController>> {
    public ContainerCollisionChamberController(int containerId, Inventory inventory, final TileCollisionChamberController controller) {
        super(COLLISION_CHAMBER_CONTROLLER_MENU.get(), containerId, inventory, controller);
    }

    // Client Constructor
    public ContainerCollisionChamberController(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, AbstractModBlockEntity.getGuiClientBlockEntity(extraData));
    }
}
