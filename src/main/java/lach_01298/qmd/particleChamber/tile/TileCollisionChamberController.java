package lach_01298.qmd.particleChamber.tile;

import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.render.BlockHighlightTracker;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.QMD;
import lach_01298.qmd.particleChamber.ParticleChamber;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;
import static lach_01298.qmd.tile.QMDTiles.TILE_COLLISION_CHAMBER_CONTROLLER;


public class TileCollisionChamberController extends TileParticleChamberPart implements IParticleChamberController<TileCollisionChamberController> {
    protected final BlockEntityMenuInfo<TileCollisionChamberController> info = BlockEntityInfoHandler.getTileContainerInfo("collision_chamber_controller");

    public TileCollisionChamberController(BlockPos pos, BlockState state) {
        super(TILE_COLLISION_CHAMBER_CONTROLLER.get(), pos, state);
    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return position.isFace();
    }

    @Override
    public String getLogicID() {
        return "collision_chamber";
    }

    @Override
    public BlockEntityMenuInfo<TileCollisionChamberController> getContainerInfo() {
        return info;
    }

    @Override
    public void onPreMachineAssembled(ParticleChamber controller) {
        super.onPreMachineAssembled(controller);
        if (!level.isClientSide() && getPartPosition().getDirection().isPresent()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FACING_ALL, getPartPosition().getDirection().get()), 2);
        }
    }

    @Override
    public void onBlockNeighborChanged(BlockState state, Level level, BlockPos pos, BlockPos fromPos) {
        super.onBlockNeighborChanged(state, level, pos, fromPos);
        if (getMultiblockController().isPresent()) getMultiblockController().get().updateActivity();
    }

    @Override
    public boolean onUseMultitool(ItemStack multitool, ServerPlayer player, Level level, Direction facing, BlockPos hitPos) {
        if (player.isCrouching() && this.isMachineAssembled()) {
            int invalidAmount = 0;
            ParticleChamber multiblock = getMultiblockController().get();
            for (TileParticleChamberDetector detector : multiblock.getPartMap(TileParticleChamberDetector.class).values()) {
                BlockPos chamberPos;
                if (multiblock.getExteriorLengthX() > multiblock.getExteriorLengthZ()) {
                    chamberPos = new BlockPos(detector.getBlockPos().getX(), multiblock.getMiddleY(), multiblock.getMiddleZ());
                } else {
                    chamberPos = new BlockPos(multiblock.getMiddleX(), multiblock.getMiddleY(), detector.getBlockPos().getZ());
                }

                if (!detector.isValidPostion(chamberPos)) {
                    BlockHighlightTracker.sendPacket(player, detector.getBlockPos(), 10000);
                    invalidAmount++;
                }
            }
            player.sendSystemMessage(Component.translatable("qmd.multiblock_validation.chamber.invalid_detectors", invalidAmount));
            return true;
        }

        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(QMD.MOD_ID + ".menu.title.collision_chamber_controller");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return info.createMenu(containerId, inventory, this);
    }

    @Override
    public boolean canOpenGui(Level world, BlockPos position, BlockState state) {
        return isMachineAssembled();
    }
}