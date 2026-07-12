package lach_01298.qmd.accelerator.tile;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.multiblock.ILogicMultiblockController;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IAcceleratorController<CONTROLLER extends BlockEntity & IAcceleratorController<CONTROLLER>> extends IAcceleratorPart, ILogicMultiblockController<Accelerator, AcceleratorUpdatePacket, CONTROLLER, BlockEntityMenuInfo<CONTROLLER>> {
}
