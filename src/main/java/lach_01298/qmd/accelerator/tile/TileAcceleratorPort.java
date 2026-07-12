package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.fluid.ITileFluid;
import com.nred.nuclearcraft.block_entity.internal.fluid.*;
import com.nred.nuclearcraft.block_entity.internal.inventory.InventoryConnection;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemOutputSetting;
import com.nred.nuclearcraft.block_entity.internal.inventory.ItemSorption;
import com.nred.nuclearcraft.block_entity.inventory.ITileInventory;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.accelerator.MassSpectrometerLogic;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.util.InventoryStackList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_PORT;

public class TileAcceleratorPort extends TileAcceleratorPart implements ITileInventory, ITileFluid {
    private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(2, ItemStack.EMPTY);
    private TileAcceleratorIonSource source;
    private IAcceleratorController<?> controller;

    private final @Nonnull List<Tank> backupTanks = Lists.newArrayList(new Tank(QMDServerConfig.accelerator_base_input_tank_capacity * 1000, new HashSet<>()));
    private @Nonnull FluidConnection[] fluidConnections = ITileFluid.fluidConnectionAll(Lists.newArrayList(TankSorption.NON));
    private @Nonnull FluidTileWrapper[] fluidSides;

    private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.accelerator_port";
    private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.NON, ItemSorption.NON));

    public TileAcceleratorPort(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_PORT.get(), position, blockState, PartPosition.Type.Face);

        fluidSides = ITileFluid.getDefaultFluidSides(this);
    }

    @Override
    public void onPreMachineAssembled(Accelerator accelerator) {
        if (accelerator.controller instanceof TileMassSpectrometerController) {
            controller = accelerator.controller;

            for (int i = 0; i < 6; i++) {
                setItemSorption(Direction.from3DDataValue(i), 0, ItemSorption.IN);
                setItemSorption(Direction.from3DDataValue(i), 1, ItemSorption.NON);
                setTankSorption(Direction.from3DDataValue(i), 0, TankSorption.IN);
            }

        } else if (accelerator.controller instanceof TileLinearAcceleratorController) {
            controller = accelerator.controller;
            for (int i = 0; i < 6; i++) {
                setItemSorption(Direction.from3DDataValue(i), 0, ItemSorption.BOTH);
                setItemSorption(Direction.from3DDataValue(i), 1, ItemSorption.BOTH);
                setTankSorption(Direction.from3DDataValue(i), 0, TankSorption.IN);
            }
        } else {
            for (int i = 0; i < 6; i++) {
                setItemSorption(Direction.from3DDataValue(i), 0, ItemSorption.NON);
                setItemSorption(Direction.from3DDataValue(i), 1, ItemSorption.NON);
                setTankSorption(Direction.from3DDataValue(i), 0, TankSorption.NON);
            }
        }

        super.onPreMachineAssembled(accelerator);
    }

    public void setSource(LinearAcceleratorLogic logic) {
        source = logic.getSource();
    }

    @Override
    public void onPreMachineBroken() {
        controller = null;
        source = null;

        for (int i = 0; i < 6; i++) {
            setItemSorption(Direction.from3DDataValue(i), 0, ItemSorption.NON);
            setItemSorption(Direction.from3DDataValue(i), 1, ItemSorption.NON);
            setTankSorption(Direction.from3DDataValue(i), 0, TankSorption.NON);
        }

        super.onPreMachineBroken();
    }

    // Items

    @Override
    public NonNullList<ItemStack> getInventoryStacks() {
        if (controller != null) {
            if (getLogic() instanceof MassSpectrometerLogic && controller instanceof TileMassSpectrometerController) {
                TileMassSpectrometerController massSpec = (TileMassSpectrometerController) controller;
                return new InventoryStackList(massSpec.getInventoryStacks().subList(0, 2));
            }
        }


        return source == null ? inventoryStacks : source.getInventoryStacks();
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

    @Override
    public int getMaxStackSize() {
        if (controller instanceof TileMassSpectrometerController) {
            return 64;
        }

        return 1;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (controller instanceof TileMassSpectrometerController) {
            return QMDRecipes.mass_spectrometer.isValidItemInput(stack, level);
        }

        return QMDRecipes.accelerator_source.isValidItemInput(IItemParticleAmount.cleanNBT(stack), level);
    }

    // Fluids

    @Override
    public @Nonnull List<Tank> getTanks() {
        Accelerator multiblock = getMultiblockController().orElse(null);
        if (multiblock != null) {
            return multiblock.isAssembled() ? multiblock.tanks.subList(2, 3) : backupTanks;
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
    }
}