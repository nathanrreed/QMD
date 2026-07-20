package lach_01298.qmd.multiblock.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.helpers.MenuHelper;
import com.nred.nuclearcraft.menu.multiblock.controller.MultiblockControllerMenu;
import it.zerono.mods.zerocore.lib.block.AbstractModBlockEntity;
import lach_01298.qmd.container.slot.SlotQMDProcessorInput;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.QMDRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static lach_01298.qmd.menu.QMDMenus.TARGET_CHAMBER_CONTROLLER_MENU;

public class ContainerTargetChamberController extends MultiblockControllerMenu<ParticleChamber, ParticleChamberUpdatePacket, TileTargetChamberController, BlockEntityMenuInfo<TileTargetChamberController>> {
    protected final QMDRecipeHandler<?> recipeHandler;

    protected int inputSlotsSize;
    protected int outputSlotsSize;
    protected int otherSlotsSize;

    public ContainerTargetChamberController(int containerId, Inventory inventory, TileTargetChamberController tile) {
        super(TARGET_CHAMBER_CONTROLLER_MENU.get(), containerId, inventory, tile);
        this.recipeHandler = QMDRecipes.target_chamber;

        this.inputSlotsSize = 1;
        this.outputSlotsSize = 1;
        this.otherSlotsSize = 0;

        addSlot(new SlotQMDProcessorInput<>(tile, recipeHandler, 0, 53, 38));
        addSlot(new FurnaceResultSlot(inventory.player, tile, 1, 94, 38));

        addPlayerInventory(inventory, 8, 118);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        return MenuHelper.quickMoveStack(player, index, slots, this::moveItemStackTo, inputSlotsSize + outputSlotsSize + otherSlotsSize);
    }

    public ContainerTargetChamberController(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
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