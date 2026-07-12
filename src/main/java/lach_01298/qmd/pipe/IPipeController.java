package lach_01298.qmd.pipe;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.multiblock.ILogicMultiblockController;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IPipeController<CONTROLLER extends BlockEntity & IPipeController<CONTROLLER>> extends IPipePart, ILogicMultiblockController<Pipe, PipeUpdatePacket, CONTROLLER, BlockEntityMenuInfo<CONTROLLER>> {
}