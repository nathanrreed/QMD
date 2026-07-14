//package lach_01298.qmd.multiblock.container;
//
//import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
//import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
//import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
//import lach_01298.qmd.accelerator.Accelerator;
//import lach_01298.qmd.accelerator.tile.TileBeamDiverterController;
//import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
//import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
//import lach_01298.qmd.particleChamber.ParticleChamber;
//import lach_01298.qmd.particleChamber.tile.*;
//import nc.container.multiblock.controller.ContainerMultiblockController;
//import nc.tile.TileContainerInfo;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.world.entity.player.Inventory;
//
//import static com.nred.nuclearcraft.registration.MenuRegistration.DECAY_POOL_CONTROLLER_MENU_TYPE;
//
//public class ContainerBeamDumpController extends ContainerMultiblockController<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileBeamDumpController, TileContainerInfo<TileBeamDumpController>>
//{
//	public ContainerBeamDumpController(EntityPlayer player, TileBeamDumpController controller)
//	{
//		super(player, controller);
//
//	}
//}