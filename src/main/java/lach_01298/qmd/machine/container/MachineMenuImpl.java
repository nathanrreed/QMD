package lach_01298.qmd.machine.container;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.menu.InfoTileMenu;
import com.nred.nuclearcraft.menu.processor.ProcessorMenuImpl.BasicEnergyProcessorMenu;
import com.nred.nuclearcraft.menu.processor.ProcessorMenuImpl.BasicUpgradableEnergyProcessorMenu;
import com.nred.nuclearcraft.menu.slot.ProcessorInputSlot;
import com.nred.nuclearcraft.recipe.ProcessorRecipe;
import it.zerono.mods.zerocore.lib.world.WorldHelper;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.machine.network.CreativeParticleSourceUpdatePacket;
import lach_01298.qmd.machine.tile.TileQMDProcessors.IrradiatorEntity;
import lach_01298.qmd.machine.tile.TileQMDProcessors.OreLeacherEntity;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static lach_01298.qmd.datamap.QMDDatamaps.IRRADIATOR_FUELS;
import static lach_01298.qmd.menu.QMDMenus.*;

public class MachineMenuImpl {
    public static class IrradiatorMenu extends BasicEnergyProcessorMenu<IrradiatorEntity, ProcessorRecipe> {
        public IrradiatorMenu(int containerId, Inventory inventory, IrradiatorEntity tile) {
            super(IRRADIATOR_MENU.get(), containerId, inventory, tile);
        }

        // Client Constructor
        public IrradiatorMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
            this(containerId, inventory, (IrradiatorEntity) WorldHelper.getClientTile(extraData.readBlockPos()).orElseThrow(NullPointerException::new));
        }

        @Override
        protected void addMachineSlots(Player player) {
            int[] stackXY = info.itemInputStackXY.get(0);
            addInputSlot(player, 0, stackXY[0], stackXY[1]);
            stackXY = info.itemInputStackXY.get(1);
            addSlot(new ProcessorInputSlot(tile, recipeHandler, 1, stackXY[0], stackXY[1]) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getCount() == 1 && stack.getItem() instanceof IItemParticleAmount && BuiltInRegistries.ITEM.wrapAsHolder(stack.getItem()).getData(IRRADIATOR_FUELS) != null;
                }
            });

            for (int i = 0; i < info.itemOutputSize; ++i) {
                stackXY = info.itemOutputStackXY.get(i);
                addOutputSlot(player, i + info.itemInputSize, stackXY[0], stackXY[1]);
            }
        }
    }

    public static class OreLeacherMenu extends BasicUpgradableEnergyProcessorMenu<OreLeacherEntity, ProcessorRecipe> {
        public OreLeacherMenu(int containerId, Inventory inventory, OreLeacherEntity tile) {
            super(ORE_LEACHER_MENU.get(), containerId, inventory, tile);
        }

        // Client Constructor
        public OreLeacherMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
            this(containerId, inventory, (OreLeacherEntity) WorldHelper.getClientTile(extraData.readBlockPos()).orElseThrow(NullPointerException::new));
        }
    }

    public static class CreativeParticleSourceMenu extends InfoTileMenu<TileCreativeParticleSource, CreativeParticleSourceUpdatePacket, BlockEntityMenuInfo<TileCreativeParticleSource>> {
        public CreativeParticleSourceMenu(int containerId, Inventory inventory, TileCreativeParticleSource tile) {
            super(CREATIVE_PARTICLE_SOURCE_MENU.get(), containerId, inventory, tile);
        }

        // Client Constructor
        public CreativeParticleSourceMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
            this(containerId, inventory, (TileCreativeParticleSource) WorldHelper.getClientTile(extraData.readBlockPos()).orElseThrow(NullPointerException::new));
        }
    }
}