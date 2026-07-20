package lach_01298.qmd.particleChamber.tile;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.internal.inventory.InventoryConnection;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemOutputSetting;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemSorption;
import com.nred.nuclearcraft.block_entity.inventory.ITileInventory;
import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.render.BlockHighlightTracker;
import com.nred.nuclearcraft.util.NBTHelper;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.QMD;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.QMDRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Set;

import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;
import static lach_01298.qmd.tile.QMDTiles.TILE_TARGET_CHAMBER_CONTROLLER;


public class TileTargetChamberController extends TileParticleChamberPart implements IParticleChamberController<TileTargetChamberController>, ITileInventory {
    protected final BlockEntityMenuInfo<TileTargetChamberController> info = BlockEntityInfoHandler.getTileContainerInfo("target_chamber_controller");

    private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(2, ItemStack.EMPTY);
    private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.IN, ItemSorption.OUT));


    public QMDRecipeHandler<?> recipe_handler = QMDRecipes.target_chamber;

    protected Set<Player> playersToUpdate;

    public TileTargetChamberController(BlockPos pos, BlockState state) {
        super(TILE_TARGET_CHAMBER_CONTROLLER.get(), pos, state);

        playersToUpdate = new ObjectOpenHashSet<>();
    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return position.isFace();
    }

    @Override
    public String getLogicID() {
        return "target_chamber";
    }

    @Override
    public BlockEntityMenuInfo<TileTargetChamberController> getContainerInfo() {
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
    public void onBlockNeighborChanged(BlockState state, Level world, BlockPos pos, BlockPos fromPos) {
        super.onBlockNeighborChanged(state, world, pos, fromPos);
        if (getMultiblockController().isPresent()) getMultiblockController().get().updateActivity();
    }

    public QMDRecipeHandler<?> getRecipeHandler() {
        return recipe_handler;
    }

    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        writeInventory(nbt, registries);
        writeInventoryConnections(nbt, registries);

        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        readInventory(nbt, registries);
        readInventoryConnections(nbt, registries);
    }

    @Override
    public NonNullList<ItemStack> getInventoryStacks() {
        return inventoryStacks;
    }

    @Override
    public InventoryConnection[] getInventoryConnections() {
        return inventoryConnections;
    }

    @Override
    public void setInventoryConnections(InventoryConnection[] connections) {
        inventoryConnections = connections;
    }

    @Override
    public ItemOutputSetting getItemOutputSetting(int slot) {
        return ItemOutputSetting.DEFAULT;
    }

    @Override
    public void setItemOutputSetting(int slot, ItemOutputSetting setting) {
    }

    @Override
    public CompoundTag writeInventory(CompoundTag nbt, HolderLookup.Provider registries) {
        NBTHelper.writeAllItems(nbt, registries, inventoryStacks);
        return nbt;
    }

    @Override
    public void readInventory(CompoundTag nbt, HolderLookup.Provider registries) {
        NBTHelper.readAllItems(nbt, registries, inventoryStacks);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (getRecipeHandler() == null) {
            return true;
        }
        if (stack == ItemStack.EMPTY || slot >= getRecipeHandler().itemInputSize)
            return false;
        return getRecipeHandler().isValidItemInput(stack, level);
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        return (getRecipeHandler() == null || canPlaceItem(slot, stack));
    }

    @Override
    public boolean onUseMultitool(ItemStack multitool, ServerPlayer player, Level level, Direction facing, BlockPos hitPos) {
        if (player.isCrouching() && this.isMachineAssembled()) {
            int invalidAmount = 0;
            ParticleChamber multiblock = getMultiblockController().get();
            for (TileParticleChamberDetector detector : multiblock.getPartMap(TileParticleChamberDetector.class).values()) {
                BlockPos chamberPos = new BlockPos(multiblock.getMiddleX(), multiblock.getMiddleY(), multiblock.getMiddleZ());
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
        return Component.translatable(QMD.MOD_ID + ".menu.title.target_chamber_controller");
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