package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.ITickable;
import com.nred.nuclearcraft.block_entity.ITileGui;
import com.nred.nuclearcraft.block_entity.fluid.ITileFluid;
import com.nred.nuclearcraft.block_entity.internal.fluid.*;
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
import lach_01298.qmd.accelerator.MassSpectrometerLogic;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.multiblock.container.ContainerAcceleratorIonSource;
import lach_01298.qmd.multiblock.network.AcceleratorSourceUpdatePacket;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.tile.ITileIONumber;
import lach_01298.qmd.util.InventoryStackList;
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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_ION_SOURCE_BASIC;
import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_ION_SOURCE_LASER;

public abstract class TileAcceleratorIonSource extends TileAcceleratorPart implements ITileInventory, ITileFluid, ITileIONumber, ITileGui<TileAcceleratorIonSource, AcceleratorSourceUpdatePacket, BlockEntityMenuInfo<TileAcceleratorIonSource>>, ITickable {
    private IAcceleratorController<?> controller;

    private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Arrays.asList(ItemSorption.NON, ItemSorption.NON));
    private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(2, ItemStack.EMPTY);

    private final @Nonnull List<Tank> backupTanks = Lists.newArrayList(new Tank(QMDServerConfig.accelerator_base_input_tank_capacity * 1000, new HashSet<>()));
    private @Nonnull FluidConnection[] fluidConnections = ITileFluid.fluidConnectionAll(Lists.newArrayList(TankSorption.NON));
    private @Nonnull FluidTileWrapper[] fluidSides;

    protected Set<Player> playersToUpdate;

    public final int outputParticleMultiplier;
    public final double outputFocus;
    public final int basePower;
    public final String name;

    private int IONumber;

    public TileAcceleratorIonSource(final BlockEntityType<?> type, final BlockPos position, final BlockState blockState, int outputParticleMultipler, double outputFocus, int basePower, String name) {
        super(type, position, blockState, PartPosition.Type.Face);
        this.outputParticleMultiplier = outputParticleMultipler < 1 ? 1 : outputParticleMultipler;
        this.outputFocus = outputFocus;
        this.basePower = basePower;
        this.name = name;

        fluidSides = ITileFluid.getDefaultFluidSides(this);
        this.IONumber = 0;
        playersToUpdate = new ObjectOpenHashSet<>();
    }

    public static class Basic extends TileAcceleratorIonSource {
        public Basic(final BlockPos position, final BlockState blockState) {
            super(TILE_ACCELERATOR_ION_SOURCE_BASIC.get(), position, blockState, QMDServerConfig.ion_source_output_multiplier[0], QMDServerConfig.ion_source_focus[0], QMDServerConfig.ion_source_power[0], "basic");
        }

        @Override
        public Component getDisplayName() {
            return Component.translatable(QMD.MOD_ID + ".menu.title.accelerator_source");
        }
    }

    public static class Laser extends TileAcceleratorIonSource {
        public Laser(final BlockPos position, final BlockState blockState) {
            super(TILE_ACCELERATOR_ION_SOURCE_LASER.get(), position, blockState, QMDServerConfig.ion_source_output_multiplier[1], QMDServerConfig.ion_source_focus[1], QMDServerConfig.ion_source_power[1], "laser");
        }

        @Override
        public Component getDisplayName() {
            return Component.translatable(QMD.MOD_ID + ".menu.title.accelerator_laser_ion_source");
        }
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

    @Override
    public void onPreMachineBroken() {
        controller = null;
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
        if (controller != null && IONumber != 0) {
            if (getLogic() instanceof MassSpectrometerLogic && controller instanceof TileMassSpectrometerController) {
                TileMassSpectrometerController massSpec = (TileMassSpectrometerController) controller;
                return new InventoryStackList(massSpec.getInventoryStacks().subList(0, 2));
            }
        }

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
        if (multiblock != null && IONumber != 0) {
            return multiblock.isAssembled() ? multiblock.tanks.subList(IONumber, IONumber + 1) : backupTanks;
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

    @Override
    public CompoundTag writeInventory(CompoundTag nbt, HolderLookup.Provider registries) {
        NBTHelper.writeAllItems(nbt, registries, inventoryStacks);
        return nbt;
    }

    @Override
    public void readInventory(CompoundTag nbt, HolderLookup.Provider registries) {
        NBTHelper.readAllItems(nbt, registries, inventoryStacks);
    }

    // Gui

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        this.setChanged(); // Fixes tank having wrong capacity
        return new ContainerAcceleratorIonSource(containerId, inventory, this);
    }

    public boolean canOpenGui(Level world, BlockPos position, BlockState state) {
        return this.isMachineAssembled();
    }

    @Override
    public BlockEntityMenuInfo<TileAcceleratorIonSource> getContainerInfo() {
        return BlockEntityInfoHandler.getTileContainerInfo("ion_source");
    }

    @Override
    public Set<Player> getTileUpdatePacketListeners() {
        return playersToUpdate;
    }

    @Override
    public AcceleratorSourceUpdatePacket getTileUpdatePacket() {
        return new AcceleratorSourceUpdatePacket(worldPosition, getTanks());
    }

    @Override
    public void onTileUpdatePacket(AcceleratorSourceUpdatePacket message) {
        for (int i = 0; i < getTanks().size(); ++i) {
            getTanks().get(i).readInfo(message.tanksInfo.get(i));
        }
    }

    @Override
    public void update() {
        if (!level.isClientSide()) {
            sendTileUpdatePacketToListeners();
        }
    }

    // IO setting

    public void setIONumber(int number) {
        if (number >= 0 && number <= 2) {
            IONumber = number;
        }
    }

    public int getIONumber() {
        return IONumber;
    }
}
