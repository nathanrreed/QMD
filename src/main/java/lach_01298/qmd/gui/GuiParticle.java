package lach_01298.qmd.gui;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuiParticle {
    private int width = 16;
    private int height = 16;
    private final Screen screen;

    public GuiParticle(Screen screen) {
        this.screen = screen;
    }

    public void drawParticleStack(GuiGraphics guiGraphics, ParticleStack particleStack, int x, int y) {
        if (particleStack == null) {
            return;
        }
        if (particleStack.getParticle() == null) {
            return;
        }

        guiGraphics.blitSprite(particleStack.getParticle().getTexture(), width, height, 0, 0, x, y, width, height);
    }

    private void drawToolTip(GuiGraphics guiGraphics, ParticleStack stack, int mouseX, int mouseY, boolean showFocus) {
        List<Component> text = new ArrayList<>();
        text.add(Component.translatable(stack.getParticle().getUnlocalizedName()).withStyle(ChatFormatting.WHITE));
        text.add(Component.translatable("gui.qmd.particlestack.amount", Units.getSIFormat(stack.getAmount(), "pu")).withStyle(ChatFormatting.GRAY));
        text.add(Component.translatable("gui.qmd.particlestack.mean_energy", Units.getParticleEnergy(stack.getMeanEnergy())).withStyle(ChatFormatting.GRAY));
        if (showFocus) {
            text.add(Component.translatable("gui.qmd.particlestack.focus", Units.formatFocus(stack.getFocus())).withStyle(ChatFormatting.GRAY));
        }
        guiGraphics.renderTooltip(screen.getMinecraft().font, text, Optional.empty(), mouseX, mouseY);
    }

    public void drawToolTipBox(GuiGraphics guiGraphics, ParticleStack particleStack, int x, int y, int mouseX, int mouseY) {
        if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height) {

            drawToolTip(guiGraphics, particleStack, mouseX, mouseY, false);
        }
    }

    public void drawToolTipBoxWithFocus(GuiGraphics guiGraphics, ParticleStack particleStack, int x, int y, int mouseX, int mouseY) {
        if (particleStack != null) {
            if (particleStack.getParticle() != null) {
                if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height) {

                    drawToolTip(guiGraphics, particleStack, mouseX, mouseY, true);
                }
            }
        }
    }
}