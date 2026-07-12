package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.helpers.MenuHelper;
import com.nred.nuclearcraft.menu.InfoTileMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorIonSource;
import lach_01298.qmd.accelerator.tile.TileMassSpectrometerController;
import lach_01298.qmd.container.slot.SlotDisabled;
import lach_01298.qmd.container.slot.SlotQMDProcessorInput;
import lach_01298.qmd.multiblock.network.AcceleratorSourceUpdatePacket;
import lach_01298.qmd.recipe.QMDRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static lach_01298.qmd.menu.QMDMenus.ACCELERATOR_ION_SOURCE_MENU;

public class ContainerAcceleratorIonSource extends InfoTileMenu<TileAcceleratorIonSource, AcceleratorSourceUpdatePacket, BlockEntityMenuInfo<TileAcceleratorIonSource>> {
    protected int inputSlotsSize = 0;
    protected int outputSlotsSize = 0;
    protected int otherSlotsSize = 2;

    public ContainerAcceleratorIonSource(int containerId, Inventory inventory, TileAcceleratorIonSource source) {
        super(ACCELERATOR_ION_SOURCE_MENU.get(), containerId, inventory, source);
        source.addTileUpdatePacketListener(inventory.player);

        if (source.getMultiblockController().get().controller instanceof TileMassSpectrometerController) {
            addSlot(new SlotQMDProcessorInput<>(source, QMDRecipes.mass_spectrometer, 0, 71, 26));
            addSlot(new SlotDisabled(source, 1));
            this.otherSlotsSize = 1;
            this.inputSlotsSize = 1;
        } else {
            addSlot(new SlotQMDProcessorInput<>(source, QMDRecipes.accelerator_source, 0, 71, 26, 1));
            addSlot(new SlotQMDProcessorInput<>(source, QMDRecipes.accelerator_source, 1, 89, 26, 1));
            this.otherSlotsSize = 0;
            this.inputSlotsSize = 2;
        }

        addPlayerInventory(inventory, 8, 84);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        return MenuHelper.quickMoveStack(player, index, slots, this::moveItemStackTo, inputSlotsSize + outputSlotsSize + otherSlotsSize);
    }

    public ContainerAcceleratorIonSource(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, AbstractModBlockEntity.getGuiClientBlockEntity(extraData));
    }

    protected void addPlayerInventory(Inventory inventory, int xOffset, int yOffset) {
        int slotWidth = 18;
        // add player inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, j + 9 * i + 9, xOffset + slotWidth * j, yOffset + slotWidth * i));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, xOffset + slotWidth * i, yOffset + slotWidth * 3 + 4));
        }
    }
}