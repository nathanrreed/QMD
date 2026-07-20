package lach_01298.qmd.multiblock.gui;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.screen.multiblock.controller.LogicMultiblockControllerScreen;
import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.container.ContainerDecayChamberController;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.DecayChamberLogic;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.ParticleChamberLogic;
import lach_01298.qmd.particleChamber.tile.TileDecayChamberController;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiDecayChamberController extends LogicMultiblockControllerScreen<ParticleChamber, ParticleChamberLogic, ParticleChamberUpdatePacket, TileDecayChamberController, BlockEntityMenuInfo<TileDecayChamberController>, DecayChamberLogic, ContainerDecayChamberController> {
    protected static final ResourceLocation gui_texture = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/decay_chamber_controller");
    private final GuiParticle guiParticle;

    public GuiDecayChamberController(ContainerDecayChamberController menu, Inventory inventory, Component title) {
        super(menu, inventory, title, gui_texture);
        imageWidth = 176;
        imageHeight = 113;
        guiParticle = new GuiParticle(this);
    }

    @Override
    protected void drawForegroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int offset = leftPos + 8;

        int fontColor = multiblock.isChamberOn ? -1 : 15641088;
        guiGraphics.drawString(font, title, offset, topPos + 5, fontColor);

        Component efficiency = Component.translatable("gui.qmd.container.particle_chamber.efficiency", String.format("%.2f", multiblock.efficiency * 100));
        guiGraphics.drawString(font, efficiency, offset, topPos + 80, fontColor);

        Component length = Component.translatable("gui.qmd.container.particle_chamber.length", logic.getBeamLength());
        guiGraphics.drawString(font, length, offset, topPos + 90, fontColor);
    }

    @Override
    protected void drawBackgroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.drawBackgroundLayer(guiGraphics, partialTicks, mouseX, mouseY);

        int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored() / (double) multiblock.energyStorage.getMaxEnergyStored() * 74);

        guiGraphics.blitSprite(guiTextures, 256, 256, 176, 74 - power, leftPos + 161, topPos + 79 - power, 6, power);

        // input
        if (multiblock.beams.get(0).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 182, 0, leftPos + 51, topPos + 42, 16, 6);
        }

        // top output
        if (multiblock.beams.get(1).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 182, 6, leftPos + 84, topPos + 20, 16, 16);
        }

        // middle output
        if (multiblock.beams.get(2).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 182, 38, leftPos + 85, topPos + 43, 16, 4);
        }

        // bottom output
        if (multiblock.beams.get(3).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 182, 22, leftPos + 84, topPos + 53, 16, 16);
        }

        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(0).getParticleStack(), leftPos + 68, topPos + 37);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(1).getParticleStack(), leftPos + 101, topPos + 14);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(2).getParticleStack(), leftPos + 101, topPos + 37);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(3).getParticleStack(), leftPos + 101, topPos + 60);
    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        drawTooltip(guiGraphics, energyInfo(), mouseX, mouseY, 160, 4, 8, 76);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(0).getParticleStack(), leftPos + 68, topPos + 37, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(1).getParticleStack(), leftPos + 101, topPos + 15, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(2).getParticleStack(), leftPos + 101, topPos + 37, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(3).getParticleStack(), leftPos + 101, topPos + 60, mouseX, mouseY);

    }

    public List<Component> energyInfo() {
        List<Component> info = new ArrayList<>();
        info.add(Component.translatable("gui.qmd.container.energy_stored",
                Units.getSIFormat(multiblock.energyStorage.getEnergyStored(), "RF"),
                Units.getSIFormat(multiblock.energyStorage.getMaxEnergyStored(), "RF")).withStyle(ChatFormatting.YELLOW));
        info.add(Component.translatable("gui.qmd.container.required_energy", Units.getSIFormat(multiblock.requiredEnergy, "RF/t")).withStyle(ChatFormatting.RED));
        return info;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blitSprite(this.guiTextures, 256, 256, 0, 0, this.getGuiLeft(), this.getGuiTop(), this.imageWidth, this.imageHeight);

        this.drawBackgroundLayer(guiGraphics, partialTick, mouseX, mouseY);
        this.drawForegroundLayer(guiGraphics, partialTick, mouseX, mouseY);
    }
}
