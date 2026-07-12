package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.ITickable;
import com.nred.nuclearcraft.block_entity.fluid.ITileFluid;
import com.nred.nuclearcraft.block_entity.internal.fluid.*;
import com.nred.nuclearcraft.block_entity.internal.inventory.InventoryConnection;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemOutputSetting;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemSorption;
import com.nred.nuclearcraft.block_entity.inventory.ITileInventory;
import com.nred.nuclearcraft.block_entity.passive.ITilePassive;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.MassSpectrometerLogic;
import lach_01298.qmd.util.InventoryStackList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_ION_COLLECTOR;

public class TileAcceleratorIonCollector extends TileAcceleratorPart implements ITileInventory, ITileFluid, ITickable {
    private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(1, ItemStack.EMPTY);
    private TileMassSpectrometerController controller;

    private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.OUT));

    private final @Nonnull List<Tank> backupTanks = Lists.newArrayList(new Tank(1, new HashSet<>()));

    private @Nonnull FluidConnection[] fluidConnections = ITileFluid.fluidConnectionAll(Lists.newArrayList(TankSorption.OUT));

    private @Nonnull FluidTileWrapper[] fluidSides;

    private int IONumber;

    public TileAcceleratorIonCollector(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_ION_COLLECTOR.get(), position, blockState, PartPosition.Type.Face);
        fluidSides = ITileFluid.getDefaultFluidSides(this);

        this.IONumber = 0;
    }

    @Override
    public void onPreMachineAssembled(Accelerator accelerator) {
        if (accelerator.controller instanceof TileMassSpectrometerController) {
            controller = (TileMassSpectrometerController) accelerator.controller;
        }
        super.onPreMachineAssembled(accelerator);
    }

    @Override
    public void onPreMachineBroken() {
        controller = null;
        super.onPreMachineBroken();
    }

    @Override
    public void update() {
        Direction facing = getPartPosition().getDirection().orElse(null);

        if (!level.isClientSide() && !getTanks().get(0).isEmpty() && facing != null && getTankSorption(facing, 0).canDrain()) {
            pushFluidToSide(facing);
        }
    }

    // Items

    @Override
    public NonNullList<ItemStack> getInventoryStacks() {
        if (controller != null && IONumber != 0) {
            if (getLogic() instanceof MassSpectrometerLogic) {

                return new InventoryStackList(controller.getInventoryStacks().subList(IONumber - 1, IONumber));
            }
        }

        return inventoryStacks;
    }

    @Override
    public @Nonnull InventoryConnection[] getInventoryConnections() {
        return inventoryConnections;
    }

    @Override
    public void setInventoryConnections(@Nonnull InventoryConnection[] connections) {
        inventoryConnections = connections;
    }


    @Override
    public ItemOutputSetting getItemOutputSetting(int slot) {
        return ItemOutputSetting.DEFAULT;
    }

    @Override
    public void setItemOutputSetting(int slot, ItemOutputSetting setting) {
    }

    // Fluids

    @Override
    public @Nonnull List<Tank> getTanks() {
        Accelerator multiblock = getMultiblockController().orElse(null);
        if (multiblock != null && IONumber != 0) {
            if (getLogic() instanceof MassSpectrometerLogic) {
                return multiblock.isAssembled() ? multiblock.tanks.subList(IONumber, IONumber + 1) : backupTanks;
            }
        }

        return backupTanks;
    }

    @Override
    @Nonnull
    public FluidConnection[] getFluidConnections() {
        return fluidConnections;
    }

    @Override
    public void setFluidConnections(@Nonnull FluidConnection[] connections) {
        fluidConnections = connections;
    }

    @Override
    @Nonnull
    public FluidTileWrapper[] getFluidSides() {
        return fluidSides;
    }

    @Override
    public ChemicalTileWrapper[] getChemicalSides() {
        return null;
    }

    @Override
    public void pushFluidToSide(@Nonnull Direction side) {
        BlockEntity tile = getTileWorld().getBlockEntity(getTilePos().relative(side));
        if (tile == null || tile instanceof TileAcceleratorIonCollector || tile instanceof TileAcceleratorIonSource)
            return;

        if (tile instanceof ITilePassive)
            if (!((ITilePassive) tile).canPushFluidsTo())
                return;

        IFluidHandler adjStorage = level.getCapability(Capabilities.FluidHandler.BLOCK, tile.getBlockPos(), side.getOpposite());
        if (adjStorage == null)
            return;

        for (int i = 0; i < getTanks().size(); i++) {
            if (getTanks().get(i).getFluid().isEmpty() || !getTankSorption(side, i).canDrain())
                continue;

            getTanks().get(i).drain(adjStorage.fill(getTanks().get(i).drain(getTanks().get(i).getCapacity(), FluidAction.SIMULATE), FluidAction.EXECUTE), FluidAction.EXECUTE);
        }
    }

    @Override
    public boolean getInputTanksSeparated() {
        return false;
    }

    @Override
    public void setInputTanksSeparated(boolean separated) {
    }

    @Override
    public boolean getVoidUnusableFluidInput(int tankNumber) {
        return false;
    }

    @Override
    public void setVoidUnusableFluidInput(int tankNumber, boolean voidUnusableFluidInput) {
    }

    @Override
    public TankOutputSetting getTankOutputSetting(int tankNumber) {
        return TankOutputSetting.DEFAULT;
    }

    @Override
    public void setTankOutputSetting(int tankNumber, TankOutputSetting setting) {
    }

    @Override
    public boolean hasConfigurableFluidConnections() {
        return true;
    }

    // NBT


    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        writeInventory(nbt, registries);
        writeInventoryConnections(nbt, registries);
        writeTanks(nbt, registries);
        writeFluidConnections(nbt, registries);
        writeTankSettings(nbt, registries);
        nbt.putInt("IONumber", IONumber);

        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        readInventory(nbt, registries);
        readInventoryConnections(nbt, registries);
        readTanks(nbt, registries);
        readFluidConnections(nbt, registries);
        readTankSettings(nbt, registries);
        IONumber = nbt.getInt("IONumber");
    }

    public void setIONumber(int number) {
        if (number >= 0 && number <= 6) {
            IONumber = number;
        }
    }

    public int getIONumber() {
        return IONumber;
    }
}