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
import net.minecraft.util.Mth;
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

        @Override
        protected void drawProgressBar(GuiGraphics guiGraphics) {
            super.drawProgressBar(guiGraphics);
            guiGraphics.blitSprite(this.guiTextures, 256, 256, this.info.progressBarGuiU, this.info.progressBarGuiV + info.progressBarGuiH, this.leftPos + this.info.progressBarGuiX + 6, this.topPos + this.info.progressBarGuiY - 20, 40, (int) Mth.clamp(getProgressBarWidth() / (double) info.progressBarGuiW * 19, 0.0, 19.0));
        }
    }

    public static class OreLeacherScreen extends BasicUpgradableEnergyProcessorScreen<OreLeacherMenu, OreLeacherEntity, ProcessorRecipe> {
        public OreLeacherScreen(OreLeacherMenu menu, Inventory inventory, Component title) {
            super(menu, inventory, title, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/" + "ore_leacher"));
        }
    }
}
