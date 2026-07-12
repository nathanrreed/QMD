package lach_01298.qmd.screen;

import lach_01298.qmd.QMD;
import lach_01298.qmd.machine.gui.GuiCreativeParticleSource;
import lach_01298.qmd.machine.gui.MachineScreenImpl.IrradiatorScreen;
import lach_01298.qmd.machine.gui.MachineScreenImpl.OreLeacherScreen;
import lach_01298.qmd.multiblock.gui.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static lach_01298.qmd.menu.QMDMenus.*;

@EventBusSubscriber(modid = QMD.MOD_ID, value = Dist.CLIENT)
public class QMDScreens {

    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(IRRADIATOR_MENU.get(), IrradiatorScreen::new);
        event.register(ORE_LEACHER_MENU.get(), OreLeacherScreen::new);

        event.register(CREATIVE_PARTICLE_SOURCE_MENU.get(), GuiCreativeParticleSource::new);

        event.register(BEAM_DIVERTER_CONTROLLER_MENU.get(), GuiBeamDiverterController::new);
        event.register(BEAM_SPLITTER_CONTROLLER_MENU.get(), GuiBeamSplitterController::new);
        event.register(DECELERATOR_CONTROLLER_MENU.get(), GuiDeceleratorController::new);
        event.register(LINEAR_ACCELERATOR_CONTROLLER_MENU.get(), GuiLinearAcceleratorController::new);
        event.register(RING_ACCELERATOR_CONTROLLER_MENU.get(), GuiRingAcceleratorController::new);
        event.register(MASS_SPECTROMETER_CONTROLLER_MENU.get(), GuiMassSpectrometerController::new);
        event.register(ACCELERATOR_ION_SOURCE_MENU.get(), GUIAcceleratorIonSource::new);
    }
}
