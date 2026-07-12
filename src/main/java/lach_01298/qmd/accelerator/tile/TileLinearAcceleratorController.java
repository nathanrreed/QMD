package lach_01298.qmd.accelerator.tile;

import com.nred.nuclearcraft.block_entity.internal.inventory.InventoryConnection;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemOutputSetting;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemSorption;
import com.nred.nuclearcraft.block_entity.inventory.ITileInventory;
import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.render.BlockHighlightTracker;
import com.nred.nuclearcraft.util.NBTHelper;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.multiblock.container.ContainerLinearAcceleratorController;
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
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;
import static lach_01298.qmd.tile.QMDTiles.TILE_LINEAR_ACCELERATOR_CONTROLLER;

public class TileLinearAcceleratorController extends TileAcceleratorPart implements IAcceleratorController<TileLinearAcceleratorController>, ITileInventory {
    protected final BlockEntityMenuInfo<TileLinearAcceleratorController> info = BlockEntityInfoHandler.getTileContainerInfo("linear_accelerator_controller");

    private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Arrays.asList(ItemSorption.BOTH, ItemSorption.BOTH));
    private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(2, ItemStack.EMPTY);

    public TileLinearAcceleratorController(final BlockPos position, final BlockState blockState) {
        super(TILE_LINEAR_ACCELERATOR_CONTROLLER.get(), position, blockState, PartPosition.Type.Face);
    }

    @Override
    public String getLogicID() {
        return "linear_accelerator";
    }

    @Override
    public BlockEntityMenuInfo<TileLinearAcceleratorController> getContainerInfo() {
        return info;
    }

    @Override
    public void onPreMachineAssembled(Accelerator controller) {
        super.onPreMachineAssembled(controller);
        if (!level.isClientSide() && getPartPosition().getDirection().isPresent()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FACING_ALL, getPartPosition().getDirection().get()), 2);
        }
    }

    //items

    @Override
    public NonNullList<ItemStack> getInventoryStacks() {
        return inventoryStacks;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(QMD.MOD_ID + ".menu.title.linear_accelerator_controller");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ContainerLinearAcceleratorController(containerId, inventory, this);
    }

    public boolean canOpenGui(Level world, BlockPos position, BlockState state) {
        return this.isMachineAssembled();
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
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void onBlockNeighborChanged(BlockState state, Level world, BlockPos pos, BlockPos fromPos) {
        super.onBlockNeighborChanged(state, world, pos, fromPos);
        if (getMultiblockController().isPresent()) getMultiblockController().get().updateActivity();
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
        return QMDRecipes.accelerator_source.isValidItemInput(IItemParticleAmount.cleanNBT(stack), level);
    }

    @Override
    public boolean onUseMultitool(ItemStack multitool, ServerPlayer player, Level level, Direction facing, BlockPos hitPos) {
        if (player.isCrouching() && this.isMachineAssembled()) {
            int invalidAmount = 0;
            for (TileAcceleratorCooler cooler : getMultiblockController().get().getPartMap(TileAcceleratorCooler.class).values()) {
                if (!cooler.isFunctional()) {
                    BlockHighlightTracker.sendPacket(player, cooler.getBlockPos(), 10000);
                    invalidAmount++;
                }
            }
            player.sendSystemMessage(Component.translatable("qmd.multiblock_validation.accelerator.invalid_coolers", invalidAmount));
            return true;
        }
        return false;
    }
}