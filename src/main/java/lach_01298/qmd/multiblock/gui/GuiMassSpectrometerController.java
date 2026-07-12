package lach_01298.qmd.multiblock.gui;

import com.nred.nuclearcraft.gui.MultiblockButton;
import com.nred.nuclearcraft.gui.NCButton;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.ClearAllMaterialPacket;
import com.nred.nuclearcraft.screen.multiblock.controller.LogicMultiblockControllerScreen;
import com.nred.nuclearcraft.util.NCUtil;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.AcceleratorLogic;
import lach_01298.qmd.accelerator.MassSpectrometerLogic;
import lach_01298.qmd.accelerator.tile.TileMassSpectrometerController;
import lach_01298.qmd.multiblock.container.ContainerMassSpectrometerController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.QMDClearTankPacket;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiMassSpectrometerController extends LogicMultiblockControllerScreen<Accelerator, AcceleratorLogic, AcceleratorUpdatePacket, TileMassSpectrometerController, BlockEntityMenuInfo<TileMassSpectrometerController>, MassSpectrometerLogic, ContainerMassSpectrometerController> {
    protected static final ResourceLocation gui_texture = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/accelerator_controller");

    public GuiMassSpectrometerController(ContainerMassSpectrometerController menu, Inventory inventory, Component title) {
        super(menu, inventory, title, gui_texture);
        imageWidth = 176;
        imageHeight = 201;
    }

    @Override
    protected void drawForegroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int offset = leftPos + 8;

        int fontColor = multiblock.isControllorOn ? -1 : 15641088;
        guiGraphics.drawString(font, title, offset + 40, topPos + 4, fontColor);

        Component speedMultiplier = Component.translatable("gui.qmd.container.speed", String.format("%.2f", getLogic().speed));
        guiGraphics.drawString(font, speedMultiplier, offset, topPos + 108, fontColor);
    }

    @Override
    protected void drawBackgroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.drawBackgroundLayer(guiGraphics, partialTicks, mouseX, mouseY);

        int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored() / (double) multiblock.energyStorage.getMaxEnergyStored() * 92);
        guiGraphics.blitSprite(guiTextures, 256, 256, 176, 92 - power, leftPos + 8, topPos + 105 - power, 4, power);

        int heat = (int) Math.round((double) multiblock.heatBuffer.getHeatStored() / (double) multiblock.heatBuffer.getHeatCapacity() * 92);
        guiGraphics.blitSprite(guiTextures, 256, 256, 180, 92 - heat, leftPos + 15, topPos + 105 - heat, 4, heat);

        int coolant = (int) Math.round((double) multiblock.tanks.get(0).getFluidAmount() / (double) multiblock.tanks.get(0).getCapacity() * 92);
        guiGraphics.blitSprite(guiTextures, 256, 256, 184, 92 - coolant, leftPos + 22, topPos + 105 - coolant, 4, coolant);

        // draw progress bar
        int progress = Math.min((int) Math.round(getLogic().workDone / getLogic().recipeWork * 101.0), 101);
        guiGraphics.blitSprite(guiTextures, 256, 256, 0, 201, leftPos + 52, topPos + 51, progress, 55);

        renderGuiTank(guiGraphics, multiblock.tanks.get(2), leftPos + 46, topPos + 33, 16, 16, 1);
        renderGuiTank(guiGraphics, multiblock.tanks.get(3), leftPos + 82, topPos + 33, 16, 16, 1);
        renderGuiTank(guiGraphics, multiblock.tanks.get(4), leftPos + 101, topPos + 33, 16, 16, 1);
        renderGuiTank(guiGraphics, multiblock.tanks.get(5), leftPos + 120, topPos + 33, 16, 16, 1);
        renderGuiTank(guiGraphics, multiblock.tanks.get(6), leftPos + 139, topPos + 33, 16, 16, 1);
    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        drawFluidTooltip(guiGraphics, multiblock.tanks.get(2), mouseX, mouseY, 46, 33, 16, 16);
        drawFluidTooltip(guiGraphics, multiblock.tanks.get(3), mouseX, mouseY, 82, 33, 16, 16);
        drawFluidTooltip(guiGraphics, multiblock.tanks.get(4), mouseX, mouseY, 101, 33, 16, 16);
        drawFluidTooltip(guiGraphics, multiblock.tanks.get(5), mouseX, mouseY, 120, 33, 16, 16);
        drawFluidTooltip(guiGraphics, multiblock.tanks.get(6), mouseX, mouseY, 139, 33, 16, 16);

        drawTooltip(guiGraphics, energyInfo(), mouseX, mouseY, 7, 12, 6, 94);
        drawTooltip(guiGraphics, heatInfo(), mouseX, mouseY, 14, 12, 6, 94);
        drawTooltip(guiGraphics, coolantInfo(), mouseX, mouseY, 21, 12, 6, 94);
    }

    public List<Component> energyInfo() {
        List<Component> info = new ArrayList<>();
        info.add(Component.translatable("gui.qmd.container.energy_stored", Units.getSIFormat(multiblock.energyStorage.getEnergyStored(), "FE"), Units.getSIFormat(multiblock.energyStorage.getMaxEnergyStored(), "FE")).withStyle(ChatFormatting.YELLOW));
        info.add(Component.translatable("gui.qmd.container.required_energy", Units.getSIFormat(multiblock.requiredEnergy, "FE/t")).withStyle(ChatFormatting.RED));
        return info;
    }

    public List<Component> heatInfo() {
        List<Component> info = new ArrayList<>();
        info.add(Component.translatable("gui.qmd.container.heat_stored", Units.getSIFormat(multiblock.heatBuffer.getHeatStored(), "H"), Units.getSIFormat(multiblock.heatBuffer.getHeatCapacity(), "H")).withStyle(ChatFormatting.YELLOW));
        info.add(Component.translatable("gui.qmd.container.temperature", Units.getSIFormat(multiblock.getTemperature(), "K")));
        info.add(Component.translatable("gui.qmd.container.max_temperature", Units.getSIFormat(multiblock.maxOperatingTemp, "K")));
        info.add(Component.translatable("gui.qmd.container.cooling", Units.getSIFormat(-multiblock.cooling, "H/t")).withStyle(ChatFormatting.BLUE));
        info.add(Component.translatable("gui.qmd.container.heating", Units.getSIFormat(multiblock.currentHeating, "H/t")).withStyle(ChatFormatting.RED));
        info.add(Component.translatable("gui.qmd.container.max_heating", Units.getSIFormat(multiblock.rawHeating + multiblock.getMaxExternalHeating(), "H/t")).withStyle(ChatFormatting.RED));
        info.add(Component.translatable("gui.qmd.container.external_heating", Units.getSIFormat(multiblock.getMaxExternalHeating(), "H/t")).withStyle(ChatFormatting.RED));
        return info;
    }

    public List<Component> coolantInfo() {
        List<Component> info = new ArrayList<>();
        info.add(Component.translatable("gui.qmd.container.coolant_stored", Units.getSIFormat(multiblock.tanks.get(0).getFluidAmount(), -3, "B"), Units.getSIFormat(multiblock.tanks.get(0).getCapacity(), -3, "B")).withStyle(ChatFormatting.YELLOW));
        info.add(Component.translatable("gui.qmd.container.max_coolant_in", Units.getSIFormat(multiblock.maxCoolantIn, -6, "B/t")).withStyle(ChatFormatting.BLUE));
        info.add(Component.translatable("gui.qmd.container.max_coolant_out", Units.getSIFormat(multiblock.maxCoolantOut, -6, "B/t")).withStyle(ChatFormatting.RED));

        return info;
    }

    @Override
    public void init() {
        super.init();
        clearAllButton = this.addRenderableWidget(new MultiblockButton.ClearAllMaterial(getGuiLeft() + 150, getGuiTop() + 20, (btn) -> {
            if (NCUtil.isModifierKeyDown()) new ClearAllMaterialPacket(tile.getBlockPos()).sendToServer();
        }));
        this.addRenderableWidget(new NCButton.ClearTank(1, leftPos + 46, topPos + 33, 16, 16, (btn, button) -> {
            new QMDClearTankPacket(tile.getTilePos(), 2).sendToServer();
        }));
        this.addRenderableWidget(new NCButton.ClearTank(2, leftPos + 82, topPos + 33, 16, 16, (btn, button) -> {
            new QMDClearTankPacket(tile.getTilePos(), 3).sendToServer();
        }));
        this.addRenderableWidget(new NCButton.ClearTank(3, leftPos + 101, topPos + 33, 16, 16, (btn, button) -> {
            new QMDClearTankPacket(tile.getTilePos(), 4).sendToServer();
        }));
        this.addRenderableWidget(new NCButton.ClearTank(4, leftPos + 120, topPos + 33, 16, 16, (btn, button) -> {
            new QMDClearTankPacket(tile.getTilePos(), 5).sendToServer();
        }));
        this.addRenderableWidget(new NCButton.ClearTank(5, leftPos + 139, topPos + 33, 16, 16, (btn, button) -> {
            new QMDClearTankPacket(tile.getTilePos(), 6).sendToServer();
        }));
    }
}