package lach_01298.qmd.recipe_viewer.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Units;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

import static lach_01298.qmd.recipe_viewer.emi.QMDEmiPlugin.EMI_PARTICLE_INFO_CATEGORY;

public class EmiParticleInfo implements EmiRecipe {
    private final ParticleEmiStack particleStack;
    private final List<? extends EmiIngredient> components;

    public EmiParticleInfo(ParticleEmiStack stack) {
        this.particleStack = stack;
        this.components = stack.getKey().getComponentParticles().entrySet().stream().map(e -> new ParticleEmiStack(new ParticleStack(e.getKey(), e.getValue()))).toList();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMI_PARTICLE_INFO_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return particleStack.getId();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return (List<EmiIngredient>) components;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(particleStack);
    }

    @Override
    public int getDisplayWidth() {
        return 150;
    }

    @Override
    public int getDisplayHeight() {
        return 140;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        Particle particle = particleStack.getKey();
        Component nameString = particle.getLocalizedName();
        if (!components.isEmpty()) {
            Component componentsString = Component.translatable("gui.qmd.recipe_viewer.particle.components");
            widgets.addText(componentsString, 50, 15, Color.gray.getRGB(), false);
        }

        widgets.addSlot(particleStack, 0, 10).drawBack(false);
        for (int i = 0; i < components.size(); i++) {
            widgets.addSlot(components.get(i), 90 + i * 16, 10).drawBack(false);
        }

        Component massString = Component.translatable("gui.qmd.recipe_viewer.particle.mass", Units.getSIFormat(particle.getMass(), 6, "eV/c^2"));

        DecimalFormat df = new DecimalFormat("#.##");
        Component chargeString = Component.translatable("gui.qmd.recipe_viewer.particle.charge", df.format(particle.getCharge()));
        Component spinString = Component.translatable("gui.qmd.recipe_viewer.particle.spin", particle.getSpin());
        Component colourString = Component.translatable("gui.qmd.recipe_viewer.particle.colour", particle.interactsWithStrong());
        Component weakChargeString = Component.translatable("gui.qmd.recipe_viewer.particle.weak", particle.interactsWithWeak());
        Component descString = Component.translatable("qmd.particle." + particle.getName() + ".desc");

        widgets.addText(nameString, 0, 0, Color.BLACK.getRGB(), false);
        widgets.addText(massString, 0, 27, Color.gray.getRGB(), false);
        widgets.addText(chargeString, 0, 37, Color.gray.getRGB(), false);
        widgets.addText(spinString, 0, 47, Color.gray.getRGB(), false);
        widgets.addText(colourString, 0, 57, Color.gray.getRGB(), false);
        widgets.addText(weakChargeString, 0, 67, Color.gray.getRGB(), false);

        int i = 0;
        Font font = Minecraft.getInstance().font;
        for (FormattedCharSequence line : font.split(descString, 150)) {
            widgets.addText(line, 0, 80 + i++ * font.lineHeight, Color.gray.getRGB(), false);
        }
    }
}