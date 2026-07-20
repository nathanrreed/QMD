package lach_01298.qmd.multiblock.gui;

import com.nred.nuclearcraft.gui.NCButton;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.screen.multiblock.controller.LogicMultiblockControllerScreen;
import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.container.ContainerBeamDumpController;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.QMDClearTankPacket;
import lach_01298.qmd.particleChamber.BeamDumpLogic;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.ParticleChamberLogic;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiBeamDumpController extends LogicMultiblockControllerScreen<ParticleChamber, ParticleChamberLogic, ParticleChamberUpdatePacket, TileBeamDumpController, BlockEntityMenuInfo<TileBeamDumpController>, BeamDumpLogic, ContainerBeamDumpController> {
    protected static final ResourceLocation gui_texture = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/beam_dump_controller");

    private final GuiParticle guiParticle;

    public GuiBeamDumpController(ContainerBeamDumpController menu, Inventory inventory, Component title) {
        super(menu, inventory, title, gui_texture);
        imageWidth = 137;
        imageHeight = 89;
        guiParticle = new GuiParticle(this);
    }

    @Override
    protected void drawForegroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int offset = leftPos + 8;
        int fontColor = multiblock.isChamberOn ? -1 : 15641088;
        guiGraphics.drawString(font, title, offset, topPos + 5, fontColor);
    }

    @Override
    protected void drawBackgroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.drawBackgroundLayer(guiGraphics, partialTicks, mouseX, mouseY);

        int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored() / (double) multiblock.energyStorage.getMaxEnergyStored() * 74);

        guiGraphics.blitSprite(guiTextures, 256, 256, 137, 74 - power, leftPos + 122, topPos + 79 - power, 6, power);

        // input
        if (multiblock.beams.get(0).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 143, 0, leftPos + 20, topPos + 42, 16, 6);
        }

        // draw progress bar
        int progress = Math.min((int) Math.round((double) getLogic().particleWorkDone / (double) getLogic().recipeParticleWork * 26), 26);
        guiGraphics.blitSprite(guiTextures, 256, 256, 143, 6, leftPos + 54, topPos + 38, progress, 14);

        renderGuiTank(guiGraphics, multiblock.tanks.get(1), leftPos + 81, topPos + 37, 16, 16, 1);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(0).getParticleStack(), leftPos + 37, topPos + 37);
    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        drawFluidTooltip(guiGraphics, multiblock.tanks.get(1), mouseX, mouseY, 81, 37, 16, 16);
        drawTooltip(guiGraphics, energyInfo(), mouseX, mouseY, 122, 4, 8, 76);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(0).getParticleStack(), leftPos + 37, topPos + 37, mouseX, mouseY);
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
    public void init() {
        super.init();
        addWidget(new NCButton.ClearTank(0, leftPos + 81, topPos + 37, 16, 16, (btn, button) -> {
            new QMDClearTankPacket(tile.getTilePos(), 1).sendToServer();
        }));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blitSprite(this.guiTextures, 256, 256, 0, 0, this.getGuiLeft(), this.getGuiTop(), this.imageWidth, this.imageHeight);

        this.drawBackgroundLayer(guiGraphics, partialTick, mouseX, mouseY);
        this.drawForegroundLayer(guiGraphics, partialTick, mouseX, mouseY);
    }
}