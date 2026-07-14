package lach_01298.qmd.multiblock.gui;

import com.nred.nuclearcraft.gui.MultiblockButton;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.ClearAllMaterialPacket;
import com.nred.nuclearcraft.screen.multiblock.controller.LogicMultiblockControllerScreen;
import com.nred.nuclearcraft.util.NCUtil;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.AcceleratorLogic;
import lach_01298.qmd.accelerator.RingAcceleratorLogic;
import lach_01298.qmd.accelerator.tile.TileRingAcceleratorController;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.container.ContainerRingAcceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiRingAcceleratorController extends LogicMultiblockControllerScreen<Accelerator, AcceleratorLogic, AcceleratorUpdatePacket, TileRingAcceleratorController, BlockEntityMenuInfo<TileRingAcceleratorController>, RingAcceleratorLogic, ContainerRingAcceleratorController> {
    protected static final ResourceLocation gui_texture = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/accelerator_controller");
    private final GuiParticle guiParticle;

    public GuiRingAcceleratorController(ContainerRingAcceleratorController menu, Inventory inventory, Component title) {
        super(menu, inventory, title, gui_texture);
        imageWidth = 196;
        imageHeight = 109;
        guiParticle = new GuiParticle(this);
    }

    @Override
    protected void drawForegroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int offset = leftPos + 40;

        int fontColor = multiblock.isControllorOn ? -1 : 15641088;
        guiGraphics.drawString(font, title, offset, topPos + 5, fontColor);

        Component radius = Component.translatable("gui.qmd.container.accelerator.radius", logic.getBeamRadius());
        guiGraphics.drawString(font, radius, offset + 25, topPos + 20, fontColor);

        Component length = Component.translatable("gui.qmd.container.accelerator.length", logic.getBeamLength());
        guiGraphics.drawString(font, length, offset + 25, topPos + 30, fontColor);

        Component cavitys = Component.translatable("gui.qmd.container.accelerator.cavities", multiblock.RFCavityNumber, Units.getSIFormat(multiblock.acceleratingVoltage, 3, "V"));
        guiGraphics.drawString(font, cavitys, offset, topPos + 40, fontColor);

        Component quadrupoles = Component.translatable("gui.qmd.container.accelerator.quadrupoles", multiblock.quadrupoleNumber, Units.getSIFormat(multiblock.quadrupoleStrength, "T"));
        guiGraphics.drawString(font, quadrupoles, offset, topPos + 50, fontColor);

        Component dipoles = Component.translatable("gui.qmd.container.accelerator.dipoles", multiblock.dipoleNumber, Units.getSIFormat(multiblock.dipoleStrength, "T"));
        guiGraphics.drawString(font, dipoles, offset, topPos + 60, fontColor);

        Component temperature = Component.translatable("gui.qmd.container.temperature", Units.getSIFormat(multiblock.getTemperature(), "K"));
        guiGraphics.drawString(font, temperature, offset, topPos + 70, fontColor);

        Component maxTemperature = Component.translatable("gui.qmd.container.max_temperature", Units.getSIFormat(multiblock.maxOperatingTemp, "K"));
        guiGraphics.drawString(font, maxTemperature, offset, topPos + 80, fontColor);

        if (multiblock.errorCode != Accelerator.errorCode_Nothing) {
            Component error = Component.translatable("gui.qmd.container.accelerator.error." + multiblock.errorCode);
            guiGraphics.drawString(font, error, offset, topPos + 90, 16711680);
        }
    }

    @Override
    protected void drawBackgroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.drawBackgroundLayer(guiGraphics, partialTicks, mouseX, mouseY);

        int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored() / (double) multiblock.energyStorage.getMaxEnergyStored() * 95);

        guiGraphics.blitSprite(guiTextures, 256, 256, 196, 95 - power, leftPos + 8, topPos + 101 - power, 6, power);

        int heat = (int) Math.round((double) multiblock.heatBuffer.getHeatStored() / (double) multiblock.heatBuffer.getHeatCapacity() * 95);
        guiGraphics.blitSprite(guiTextures, 256, 256, 202, 95 - heat, leftPos + 18, topPos + 101 - heat, 6, heat);

        int coolant = (int) Math.round((double) multiblock.tanks.get(0).getFluidAmount() / (double) multiblock.tanks.get(0).getCapacity() * 95);
        guiGraphics.blitSprite(guiTextures, 256, 256, 208, 95 - coolant, leftPos + 28, topPos + 101 - coolant, 6, coolant);

        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(1).getParticleStack(), leftPos + 40, topPos + 21);
    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        drawTooltip(guiGraphics, energyInfo(), mouseX, mouseY, 8, 5, 8, 96);
        drawTooltip(guiGraphics, heatInfo(), mouseX, mouseY, 18, 5, 8, 96);
        drawTooltip(guiGraphics, coolantInfo(), mouseX, mouseY, 28, 5, 8, 96);

        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(1).getParticleStack(), leftPos + 40, topPos + 21, mouseX, mouseY);
    }

    public List<Component> heatInfo() {
        List<Component> info = new ArrayList<>();
        info.add(Component.translatable("gui.qmd.container.heat_stored", Units.getSIFormat(multiblock.heatBuffer.getHeatStored(), "H"), Units.getSIFormat(multiblock.heatBuffer.getHeatCapacity(), "H")).withStyle(ChatFormatting.YELLOW));
        info.add(Component.translatable("gui.qmd.container.cooling", Units.getSIFormat(-multiblock.cooling, "H/t")).withStyle(ChatFormatting.BLUE));
        info.add(Component.translatable("gui.qmd.container.heating", Units.getSIFormat(multiblock.currentHeating, "H/t")).withStyle(ChatFormatting.RED));
        info.add(Component.translatable("gui.qmd.container.max_heating", Units.getSIFormat(multiblock.rawHeating + multiblock.getMaxExternalHeating(), "H/t")).withStyle(ChatFormatting.RED));
        info.add(Component.translatable("gui.qmd.container.external_heating", Units.getSIFormat(multiblock.getMaxExternalHeating(), "H/t")).withStyle(ChatFormatting.RED));
        return info;
    }

    public List<Component> energyInfo() {
        List<Component> info = new ArrayList<>();
        info.add(Component.translatable("gui.qmd.container.energy_stored", Units.getSIFormat(multiblock.energyStorage.getEnergyStored(), "RF"), Units.getSIFormat(multiblock.energyStorage.getMaxEnergyStored(), "RF")).withStyle(ChatFormatting.YELLOW));
        info.add(Component.translatable("nc.sf.two_args",
                Component.translatable("gui.qmd.container.required_energy", Units.getSIFormat(multiblock.requiredEnergy, "RF/t")),
                Component.translatable("gui.qmd.container.accelerator.efficiency", String.format("%.2f", (1 / multiblock.efficiency) * 100))
        ).withStyle(ChatFormatting.RED));
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
    }
}