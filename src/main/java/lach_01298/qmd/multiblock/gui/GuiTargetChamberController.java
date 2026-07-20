package lach_01298.qmd.multiblock.gui;

import com.nred.nuclearcraft.gui.MultiblockButton;
import com.nred.nuclearcraft.gui.NCButton;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.payload.multiblock.ClearAllMaterialPacket;
import com.nred.nuclearcraft.screen.multiblock.controller.LogicMultiblockControllerScreen;
import com.nred.nuclearcraft.util.NCUtil;
import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.container.ContainerTargetChamberController;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.QMDClearTankPacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.ParticleChamberLogic;
import lach_01298.qmd.particleChamber.TargetChamberLogic;
import lach_01298.qmd.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiTargetChamberController extends LogicMultiblockControllerScreen<ParticleChamber, ParticleChamberLogic, ParticleChamberUpdatePacket, TileTargetChamberController, BlockEntityMenuInfo<TileTargetChamberController>, TargetChamberLogic, ContainerTargetChamberController> {
    protected static final ResourceLocation gui_texture = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/target_chamber_controller");
    private final GuiParticle guiParticle;

    public GuiTargetChamberController(ContainerTargetChamberController menu, Inventory inventory, Component title) {
        super(menu, inventory, title, gui_texture);
        imageWidth = 176;
        imageHeight = 200;
        guiParticle = new GuiParticle(this);
    }

    @Override
    protected void drawForegroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int offset = leftPos + 8;

        int fontColor = multiblock.isChamberOn ? -1 : 15641088;
        guiGraphics.drawString(font, title, offset, topPos + 5, fontColor);

        Component efficiency = Component.translatable("gui.qmd.container.particle_chamber.efficiency", String.format("%.2f", multiblock.efficiency * 100));
        guiGraphics.drawString(font, efficiency, offset, topPos + 98, fontColor);

        Component length = Component.translatable("gui.qmd.container.particle_chamber.length", logic.getBeamLength());
        guiGraphics.drawString(font, length, offset, topPos + 108, fontColor);
    }

    @Override
    protected void drawBackgroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.drawBackgroundLayer(guiGraphics, partialTicks, mouseX, mouseY);

        int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored() / (double) multiblock.energyStorage.getMaxEnergyStored() * 74);
        guiGraphics.blitSprite(guiTextures, 256, 256, 176, 74 - power, leftPos + 161, topPos + 87 - power, 6, power);

        // input
        if (multiblock.beams.get(0).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 182, 12, leftPos + 35, topPos + 51, 16, 6);
        }

        // top output
        if (multiblock.beams.get(1).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 182, 18, leftPos + 69, topPos + 22, 16, 16);
        }

        // middle output
        if (multiblock.beams.get(2).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 182, 50, leftPos + 112, topPos + 52, 16, 4);
        }

        // bottom output
        if (multiblock.beams.get(3).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 182, 34, leftPos + 69, topPos + 71, 16, 16);
        }

        // draw progress bar
        int progress = Math.min((int) Math.round((double) getLogic().particleWorkDone / (double) getLogic().recipeParticleWork * 21), 21);
        guiGraphics.blitSprite(guiTextures, 256, 256, 182, 0, leftPos + 71, topPos + 48, progress, 12);


        renderGuiTank(guiGraphics, multiblock.tanks.get(0), leftPos + 53, topPos + 55, 16, 1, 1);
        renderGuiTank(guiGraphics, multiblock.tanks.get(1), leftPos + 94, topPos + 55, 16, 1, 1);

        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(0).getParticleStack(), leftPos + 18, topPos + 46);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(1).getParticleStack(), leftPos + 86, topPos + 15);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(2).getParticleStack(), leftPos + 129, topPos + 46);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(3).getParticleStack(), leftPos + 86, topPos + 78);
    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        drawFluidTooltip(guiGraphics, multiblock.tanks.get(0), mouseX, mouseY, 53, 55, 16, 16);
        drawFluidTooltip(guiGraphics, multiblock.tanks.get(1), mouseX, mouseY, 94, 55, 16, 16);

        drawTooltip(guiGraphics, energyInfo(), mouseX, mouseY, 160, 12, 8, 76);

        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(0).getParticleStack(), leftPos + 18, topPos + 46, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(1).getParticleStack(), leftPos + 86, topPos + 15, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(2).getParticleStack(), leftPos + 129, topPos + 46, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(3).getParticleStack(), leftPos + 86, topPos + 78, mouseX, mouseY);
    }

    public List<Component> energyInfo() {
        List<Component> info = new ArrayList<>();
        info.add(Component.translatable("gui.qmd.container.energy_stored",
                Units.getSIFormat(multiblock.energyStorage.getEnergyStored(), "RF"),
                Units.getSIFormat(multiblock.energyStorage.getMaxEnergyStored(), "RF")).withStyle(ChatFormatting.YELLOW));
        info.add(Component.translatable("gui.qmd.container.required_energy",
                Units.getSIFormat(multiblock.requiredEnergy, "RF/t")).withStyle(ChatFormatting.RED));
        return info;
    }

    @Override
    public void init() {
        super.init();
        clearAllButton = this.addRenderableWidget(new MultiblockButton.ClearAllMaterial(getGuiLeft() + 128, getGuiTop() + 70, (btn) -> {
            if (NCUtil.isModifierKeyDown()) new ClearAllMaterialPacket(tile.getBlockPos()).sendToServer();
        }));
        addWidget(new NCButton.ClearTank(1, leftPos + 53, topPos + 55, 16, 16, (btn, button) -> {
            new QMDClearTankPacket(tile.getTilePos(), 0).sendToServer();
        }));
        addWidget(new NCButton.ClearTank(2, leftPos + 94, topPos + 55, 16, 16, (btn, button) -> {
            new QMDClearTankPacket(tile.getTilePos(), 1).sendToServer();
        }));
    }
}
