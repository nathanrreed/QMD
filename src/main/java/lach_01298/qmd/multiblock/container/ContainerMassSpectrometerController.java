package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.helpers.MenuHelper;
import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileMassSpectrometerController;
import lach_01298.qmd.container.slot.SlotDisabled;
import lach_01298.qmd.container.slot.SlotQMDProcessorInput;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.recipe.types.MassSpectrometerRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static lach_01298.qmd.menu.QMDMenus.MASS_SPECTROMETER_CONTROLLER_MENU;

public class ContainerMassSpectrometerController extends MultiblockControllerMenu<Accelerator, AcceleratorUpdatePacket, TileMassSpectrometerController, BlockEntityMenuInfo<TileMassSpectrometerController>> {
    protected final QMDRecipeHandler<MassSpectrometerRecipe> recipeHandler;

    protected static final int inputSlotsSize = 1;
    protected static final int outputSlotsSize = 4;
    protected static final int otherSlotsSize = 1;

    public ContainerMassSpectrometerController(int containerId, Inventory inventory, final TileMassSpectrometerController controller) {
        super(MASS_SPECTROMETER_CONTROLLER_MENU.get(), containerId, inventory, controller);
        this.recipeHandler = QMDRecipes.mass_spectrometer;

        addSlot(new SlotQMDProcessorInput<>(tile, recipeHandler, 0, 46, 14));
        addSlot(new SlotDisabled(tile, 1));
        addSlot(new FurnaceResultSlot(inventory.player, tile, 2, 82, 14));
        addSlot(new FurnaceResultSlot(inventory.player, tile, 3, 101, 14));
        addSlot(new FurnaceResultSlot(inventory.player, tile, 4, 120, 14));
        addSlot(new FurnaceResultSlot(inventory.player, tile, 5, 139, 14));

        addPlayerInventory(inventory, 8, 119);
    }

    // Client Constructor
    public ContainerMassSpectrometerController(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, AbstractModBlockEntity.getGuiClientBlockEntity(extraData));
    }

    @Override
    public boolean stillValid(Player player) {
        return tile.isUsableByPlayer(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        return MenuHelper.quickMoveStack(player, index, slots, this::moveItemStackTo, inputSlotsSize + outputSlotsSize + otherSlotsSize);
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