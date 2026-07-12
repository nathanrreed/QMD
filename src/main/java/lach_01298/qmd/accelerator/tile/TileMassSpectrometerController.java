package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.internal.inventory.InventoryConnection;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemOutputSetting;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemSorption;
import com.nred.nuclearcraft.block_entity.inventory.ITileInventory;
import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.util.NBTHelper;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.multiblock.container.ContainerMassSpectrometerController;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.recipe.types.MassSpectrometerRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import static lach_01298.qmd.tile.QMDTiles.TILE_MASS_SPECTROMETER_CONTROLLER;

public class TileMassSpectrometerController extends TileAcceleratorPart implements IAcceleratorController<TileMassSpectrometerController>, ITileInventory {
    protected final BlockEntityMenuInfo<TileMassSpectrometerController> info = BlockEntityInfoHandler.getTileContainerInfo("mass_spectrometer_controller");

    private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(6, ItemStack.EMPTY);
    private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.IN, ItemSorption.NON, ItemSorption.OUT, ItemSorption.OUT, ItemSorption.OUT, ItemSorption.OUT));

    public QMDRecipeHandler<MassSpectrometerRecipe> recipe_handler = QMDRecipes.mass_spectrometer;

    protected Set<Player> playersToUpdate;

    public TileMassSpectrometerController(final BlockPos position, final BlockState blockState) {
        super(TILE_MASS_SPECTROMETER_CONTROLLER.get(), position, blockState, PartPosition.Type.Face);

        playersToUpdate = new ObjectOpenHashSet<>();
    }

    @Override
    public String getLogicID() {
        return "mass_spectrometer";
    }

    @Override
    public BlockEntityMenuInfo<TileMassSpectrometerController> getContainerInfo() {
        return info;
    }

    @Override
    public void onPreMachineAssembled(Accelerator multiblock) {
        super.onPreMachineAssembled(multiblock);
        if (!level.isClientSide() && getPartPosition().getDirection().isPresent()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FACING_ALL, getPartPosition().getDirection().get()), 2);
        }
    }

    @Override
    public void onBlockNeighborChanged(BlockState state, Level world, BlockPos pos, BlockPos fromPos) {
        super.onBlockNeighborChanged(state, world, pos, fromPos);
        getMultiblockController().ifPresent(Accelerator::updateActivity);
    }

    public QMDRecipeHandler<MassSpectrometerRecipe> getRecipeHandler() {
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
        if (stack == ItemStack.EMPTY || slot >= getRecipeHandler().getItemInputSize())
            return false;
        return getRecipeHandler().isValidItemInput(stack, level);
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        return (getRecipeHandler() == null || canPlaceItem(slot, stack));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(QMD.MOD_ID + ".menu.title.mass_spectrometer_controller");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new ContainerMassSpectrometerController(containerId, inventory, this);
    }

    public boolean canOpenGui(Level world, BlockPos position, BlockState state) {
        return this.isMachineAssembled();
    }
}