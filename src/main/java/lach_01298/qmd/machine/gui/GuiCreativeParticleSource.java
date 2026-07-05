package lach_01298.qmd.machine.gui;

import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.screen.InfoTileScreen;
import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.machine.container.MachineMenuImpl.CreativeParticleSourceMenu;
import lach_01298.qmd.machine.network.CreativeParticleSourceGuiPacket;
import lach_01298.qmd.machine.network.CreativeParticleSourceUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

import java.util.regex.Pattern;

public class GuiCreativeParticleSource extends InfoTileScreen<CreativeParticleSourceMenu, TileCreativeParticleSource, CreativeParticleSourceUpdatePacket, BlockEntityMenuInfo<TileCreativeParticleSource>> {
    private EditBox particleNameField;
    private EditBox amountField;
    private EditBox energyField;
    private EditBox focusField;

    private String particleName = "";
    private int amount = 0;
    private long energy = 0;
    private double focus = 0;

    private final GuiParticle guiParticle;

    public GuiCreativeParticleSource(CreativeParticleSourceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/creative_particle_source"));
        imageWidth = 176;
        imageHeight = 115;
        guiParticle = new GuiParticle(this);
        particleName = tile.getParticleName();
        amount = tile.getParticleAmount();
        energy = tile.getParticleEnergy();
        focus = tile.getParticleFocus();
    }

    @Override
    protected void drawBackgroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.drawBackgroundLayer(guiGraphics, partialTicks, mouseX, mouseY);
        guiGraphics.blitSprite(guiTextures, 256, 256, 0, 0, leftPos, topPos, imageWidth, imageHeight);

        guiParticle.drawParticleStack(guiGraphics, tile.getParticleBeams().get(0).getParticleStack(), leftPos + 134, topPos + 43);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);

        guiParticle.drawToolTipBoxWithFocus(guiGraphics, tile.getParticleBeams().get(0).getParticleStack(), leftPos + 134, topPos + 43, x, y);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.imageWidth / 2 - font.width(this.title) / 2, this.titleLabelY, 4210752, false);

        guiGraphics.drawString(font, Component.translatable("gui.qmd.container.creative_particle_source.particle_name"), 10, 15, 4210752, false);
        guiGraphics.drawString(font, Component.translatable("gui.qmd.container.creative_particle_source.particle_amount"), 10, 40, 4210752, false);
        guiGraphics.drawString(font, Component.translatable("gui.qmd.container.creative_particle_source.particle_energy"), 10, 65, 4210752, false);
        guiGraphics.drawString(font, Component.translatable("gui.qmd.container.creative_particle_source.particle_focus"), 10, 90, 4210752, false);
    }

    private static final Pattern INT_LONG = Pattern.compile("^-?[0-9]*$");
    private static final Pattern DOUBLE = Pattern.compile("^-?[0-9]*\\.?[0-9]*$");

    @Override
    public void init() {
        super.init();

        addRenderableWidget(new Button.Builder(Component.translatable("gui.qmd.container.creative_particle_source.set"), this::setButton).bounds(leftPos + 118, topPos + 16, 50, 20).build());

        this.particleNameField = new EditBox(font, leftPos + 10, topPos + 25, 100, 12, Component.empty());
        this.particleNameField.insertText(particleName);
        addRenderableWidget(particleNameField);

        this.amountField = new EditBox(font, leftPos + 10, topPos + 50, 100, 12, Component.empty());
        this.amountField.insertText(Integer.toString(amount));
        this.amountField.setFilter((string) -> INT_LONG.matcher(string).matches());
        addRenderableWidget(amountField);

        this.energyField = new EditBox(font, leftPos + 10, topPos + 75, 100, 12, Component.empty());
        this.energyField.insertText(Long.toString(energy));
        this.energyField.setFilter((string) -> INT_LONG.matcher(string).matches());
        addRenderableWidget(energyField);

        this.focusField = new EditBox(font, leftPos + 10, topPos + 100, 100, 12, Component.empty());
        this.focusField.insertText(Double.toString(focus));
        this.focusField.setFilter((string) -> DOUBLE.matcher(string).matches());
        addRenderableWidget(focusField);
    }

    protected void setButton(Button guiButton) {
        if (tile.getLevel().isClientSide()) {
            int amount = amountField.getValue().isEmpty() ? 0 : Integer.parseInt(amountField.getValue());
            long energy = energyField.getValue().isEmpty() ? 0 : Long.parseLong(energyField.getValue());
            double focus = focusField.getValue().isEmpty() ? 0 : Double.parseDouble(focusField.getValue());

            ParticleStack stack = new ParticleStack(Particles.getParticleFromName(particleNameField.getValue()), amount, energy, focus);
            if (stack.getParticle() != null) {
                tile.getParticleBeams().get(0).setParticleStack(stack);
            }
            new CreativeParticleSourceGuiPacket(tile).sendToServer();
        }
    }
}