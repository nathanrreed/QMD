package lach_01298.qmd.multiblock.gui;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.screen.multiblock.controller.LogicMultiblockControllerScreen;
import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.multiblock.container.ContainerCollisionChamberController;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.CollisionChamberLogic;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.ParticleChamberLogic;
import lach_01298.qmd.particleChamber.tile.TileCollisionChamberController;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiCollisionChamberController extends LogicMultiblockControllerScreen<ParticleChamber, ParticleChamberLogic, ParticleChamberUpdatePacket, TileCollisionChamberController, BlockEntityMenuInfo<TileCollisionChamberController>, CollisionChamberLogic, ContainerCollisionChamberController> {
    protected static final ResourceLocation gui_texture = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/collision_chamber_controller");

    private final GuiParticle guiParticle;

    public GuiCollisionChamberController(ContainerCollisionChamberController menu, Inventory inventory, Component title) {
        super(menu, inventory, title, gui_texture);
        imageWidth = 176;
        imageHeight = 130;
        guiParticle = new GuiParticle(this);
    }

    @Override
    protected void drawForegroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int offset = leftPos + 8;

        int fontColor = multiblock.isChamberOn ? -1 : 15641088;
        guiGraphics.drawString(font, title, leftPos + imageWidth / 2 - font.width(title) / 2, topPos + 4, fontColor);

        Component efficiency = Component.translatable("gui.qmd.container.particle_chamber.efficiency", String.format("%.2f", multiblock.efficiency * 100));
        guiGraphics.drawString(font, efficiency, offset, topPos + 95, fontColor);

        Component length = Component.translatable("gui.qmd.container.particle_chamber.length", logic.getBeamLength());
        guiGraphics.drawString(font, length, offset, topPos + 115, fontColor);

        if (multiblock.beams.get(0).getParticleStack() != null && multiblock.beams.get(1).getParticleStack() != null) {
            Component collsionEnergy = Component.translatable("gui.qmd.container.collison_chamber.energy", Units.getSIFormat(2 * Math.sqrt(multiblock.beams.get(0).getParticleStack().getMeanEnergy() * multiblock.beams.get(1).getParticleStack().getMeanEnergy()), 3, "eV"));
            guiGraphics.drawString(font, collsionEnergy, offset, topPos + 105, fontColor);
        }
    }

    @Override
    protected void drawBackgroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.drawBackgroundLayer(guiGraphics, partialTicks, mouseX, mouseY);

        int power = (int) Math.round((double) multiblock.energyStorage.getEnergyStored() / (double) multiblock.energyStorage.getMaxEnergyStored() * 74);

        guiGraphics.blitSprite(guiTextures, 256, 256, 176, 74 - power, leftPos + 161, topPos + 87 - power, 6, power);

        // input 1
        if (multiblock.beams.get(0).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 182, 19, leftPos + 63, topPos + 50, 16, 6);
        }

        // input 2
        if (multiblock.beams.get(1).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 216, 19, leftPos + 97, topPos + 50, 16, 6);
        }

        // collision
        if (multiblock.beams.get(2).getParticleStack() != null || multiblock.beams.get(3).getParticleStack() != null || multiblock.beams.get(4).getParticleStack() != null || multiblock.beams.get(5).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 198, 21, leftPos + 79, topPos + 52, 18, 2);
            guiGraphics.blitSprite(guiTextures, 256, 256, 206, 19, leftPos + 87, topPos + 50, 2, 6);
            guiGraphics.blitSprite(guiTextures, 256, 256, 205, 20, leftPos + 86, topPos + 51, 4, 4);
        }

        // output 1
        if (multiblock.beams.get(2).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 185, 0, leftPos + 66, topPos + 31, 21, 21);
        }

        // output 2
        if (multiblock.beams.get(3).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 208, 0, leftPos + 89, topPos + 31, 21, 21);
        }

        // output 3
        if (multiblock.beams.get(4).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 185, 23, leftPos + 66, topPos + 54, 21, 21);
        }
        // output 4
        if (multiblock.beams.get(5).getParticleStack() != null) {
            guiGraphics.blitSprite(guiTextures, 256, 256, 208, 23, leftPos + 89, topPos + 54, 21, 21);
        }

        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(0).getParticleStack(), leftPos + 46, topPos + 45);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(1).getParticleStack(), leftPos + 114, topPos + 45);

        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(2).getParticleStack(), leftPos + 49, topPos + 14);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(3).getParticleStack(), leftPos + 111, topPos + 14);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(4).getParticleStack(), leftPos + 49, topPos + 76);
        guiParticle.drawParticleStack(guiGraphics, multiblock.beams.get(5).getParticleStack(), leftPos + 111, topPos + 76);

    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        drawTooltip(guiGraphics, energyInfo(), mouseX, mouseY, 160, 4, 8, 76);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(0).getParticleStack(), leftPos + 46, topPos + 45, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(1).getParticleStack(), leftPos + 114, topPos + 45, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(2).getParticleStack(), leftPos + 49, topPos + 14, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(3).getParticleStack(), leftPos + 111, topPos + 14, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(4).getParticleStack(), leftPos + 49, topPos + 76, mouseX, mouseY);
        guiParticle.drawToolTipBoxWithFocus(guiGraphics, multiblock.beams.get(5).getParticleStack(), leftPos + 111, topPos + 76, mouseX, mouseY);

    }

    public List<Component> energyInfo() {
        List<Component> info = new ArrayList<>();
        info.add(Component.translatable("gui.qmd.container.energy_stored",
                Units.getSIFormat(multiblock.energyStorage.getEnergyStored(), "RF"),
                Units.getSIFormat(multiblock.energyStorage.getMaxEnergyStored(), "RF")).withStyle(ChatFormatting.YELLOW));
        info.add(Component.translatable("gui.qmd.container.required_energy", Units.getSIFormat(multiblock.requiredEnergy, "RF/t")).withStyle(ChatFormatting.RED));
        return info;
    }
}