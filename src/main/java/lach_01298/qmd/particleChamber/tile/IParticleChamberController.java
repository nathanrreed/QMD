package lach_01298.qmd.particleChamber.tile;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.multiblock.ILogicMultiblockController;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IParticleChamberController<CONTROLLER extends BlockEntity & IParticleChamberController<CONTROLLER>> extends IParticleChamberPart, ILogicMultiblockController<ParticleChamber, ParticleChamberUpdatePacket, CONTROLLER, BlockEntityMenuInfo<CONTROLLER>> {
}