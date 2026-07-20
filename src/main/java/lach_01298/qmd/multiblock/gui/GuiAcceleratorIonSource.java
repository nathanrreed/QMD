package lach_01298.qmd.multiblock.gui;

import com.nred.nuclearcraft.gui.NCButton;
import com.nred.nuclearcraft.screen.NCScreen;
import com.nred.nuclearcraft.util.NCUtil;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.TileAcceleratorIonSource;
import lach_01298.qmd.multiblock.container.ContainerAcceleratorIonSource;
import lach_01298.qmd.multiblock.network.QMDClearTankPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiAcceleratorIonSource extends NCScreen<ContainerAcceleratorIonSource> {
    protected static final ResourceLocation gui_texture = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "screen/accelerator_source");

    public GuiAcceleratorIonSource(ContainerAcceleratorIonSource menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void drawForegroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.drawCenteredString(FONT, this.title, this.leftPos + this.imageWidth / 2, this.topPos + 6, -1);
    }

    @Override
    protected void drawBackgroundLayer(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blitSprite(gui_texture, 256, 256, 0, 0, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
        renderGuiTank(guiGraphics, ((TileAcceleratorIonSource) menu.tile).getTanks().get(0), leftPos + 80, topPos + 43, 16, 16, 1);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        drawFluidTooltip(guiGraphics, ((TileAcceleratorIonSource) menu.tile).getTanks().get(0), mouseX, mouseY, 80, 43, 16, 16);
    }

    @Override
    public void init() {
        super.init();
        this.addWidget(new NCButton.ClearTank(0, leftPos + 80, topPos + 43, 16, 16, this::clearTank));
    }

    protected void clearTank(NCButton guiButton, int button) {
        if (menu.tile.getLevel().isClientSide()) {
            if (NCUtil.isModifierKeyDown()) {
                new QMDClearTankPacket(menu.tile.getBlockPos(), 2).sendToServer();
            }
        }
    }
}