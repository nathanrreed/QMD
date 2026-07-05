package lach_01298.qmd.machine.gui;

import com.nred.nuclearcraft.recipe.ProcessorRecipe;
import com.nred.nuclearcraft.screen.processor.ProcessorScreenImpl.BasicEnergyProcessorScreen;
import com.nred.nuclearcraft.screen.processor.ProcessorScreenImpl.BasicUpgradableEnergyProcessorScreen;
import lach_01298.qmd.QMD;
import lach_01298.qmd.machine.container.MachineMenuImpl.IrradiatorMenu;
import lach_01298.qmd.machine.container.MachineMenuImpl.OreLeacherMenu;
import lach_01298.qmd.machine.tile.TileQMDProcessors.IrradiatorEntity;
import lach_01298.qmd.machine.tile.TileQMDProcessors.OreLeacherEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

public class MachineScreenImpl {
    public static class IrradiatorScreen extends BasicEnergyProcessorScreen<IrradiatorMenu, IrradiatorEntity, ProcessorRecipe> {
        public IrradiatorScreen(IrradiatorMenu menu, Inventory inventory, Component title) {
            super(menu, inventory, title, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/" + "irradiator"));
        }

        @Override
        protected void drawEnergyBar(GuiGraphics guiGraphics) {
        }

        @Override
        protected List<Component> energyInfo(IEnergyStorage energyStorage) {
            return List.of();
        }
    }

    public static class OreLeacherScreen extends BasicUpgradableEnergyProcessorScreen<OreLeacherMenu, OreLeacherEntity, ProcessorRecipe> {
        public OreLeacherScreen(OreLeacherMenu menu, Inventory inventory, Component title) {
            super(menu, inventory, title, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/" + "ore_leacher"));
        }
    }
}
