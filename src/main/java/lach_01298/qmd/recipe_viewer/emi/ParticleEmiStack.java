package lach_01298.qmd.recipe_viewer.emi;

import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiStack;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ParticleEmiStack extends EmiStack {
    private Particle particle;
    private long meanEnergy;
    private double focus;

    public ParticleEmiStack(ParticleStack stack) {
        this.particle = stack.getParticle();
        this.amount = stack.getAmount();
        this.meanEnergy = stack.getMeanEnergy();
        this.focus = stack.getFocus();
    }

    public ParticleEmiStack(Particle particle, long amount) {
        this.particle = particle;
        this.amount = 0;
        this.focus = 0;
        this.meanEnergy = 0;
    }

    @Override
    public EmiStack copy() {
        return new ParticleEmiStack(new ParticleStack(particle, (int) amount, meanEnergy, focus));
    }

    @Override
    public void render(GuiGraphics draw, int x, int y, float delta, int flags) {
        if ((flags & RENDER_ICON) != 0) {
            draw.blitSprite(particle.getTexture(), x, y, 16, 16);
        }
    }

    @Override
    public boolean isEmpty() {
        return amount < 0;
    }

    @Override
    public DataComponentPatch getComponentChanges() {
        return DataComponentPatch.EMPTY;
    }

    @Override
    public Particle getKey() {
        return particle;
    }

    @Override
    public ResourceLocation getId() {
        return particle.getId();
    }

    @Override
    public List<Component> getTooltipText() {
        List<Component> list = new ArrayList<>();
        list.add(particle.getLocalizedName());
        list.add(Component.translatable("gui.qmd.particlestack.amount", ChatFormatting.WHITE + Units.getSIFormat(amount, "pu")).withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("gui.qmd.particlestack.mean_energy", ChatFormatting.WHITE + Units.getParticleEnergy(meanEnergy)).withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("gui.qmd.particlestack.focus", ChatFormatting.WHITE + Units.formatFocus(focus)).withStyle(ChatFormatting.GRAY));

        return list;
    }

    @Override
    public List<ClientTooltipComponent> getTooltip() {
        List<ClientTooltipComponent> tooltips = new ArrayList<>(getTooltipText().stream().map(EmiTooltipComponents::of).toList());

        tooltips.addAll(super.getTooltip());
        return tooltips;
    }

    @Override
    public Component getName() {
        return particle.getLocalizedName();
    }
}