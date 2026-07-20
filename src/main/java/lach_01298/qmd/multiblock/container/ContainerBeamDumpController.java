package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import static lach_01298.qmd.menu.QMDMenus.BEAM_DUMP_CONTROLLER_MENU;

public class ContainerBeamDumpController extends MultiblockControllerMenu<ParticleChamber, ParticleChamberUpdatePacket, TileBeamDumpController, BlockEntityMenuInfo<TileBeamDumpController>> {
    public ContainerBeamDumpController(int containerId, Inventory inventory, final TileBeamDumpController controller) {
        super(BEAM_DUMP_CONTROLLER_MENU.get(), containerId, inventory, controller);
    }

    // Client Constructor
    public ContainerBeamDumpController(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, AbstractModBlockEntity.getGuiClientBlockEntity(extraData));
    }
}