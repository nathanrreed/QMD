package lach_01298.qmd.screen;

import com.nred.nuclearcraft.screen.processor.ProcessorScreenImpl;
import lach_01298.qmd.QMD;
import lach_01298.qmd.machine.gui.GuiCreativeParticleSource;
import lach_01298.qmd.machine.gui.MachineScreenImpl;
import lach_01298.qmd.machine.gui.MachineScreenImpl.IrradiatorScreen;
import lach_01298.qmd.machine.gui.MachineScreenImpl.OreLeacherScreen;
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

        event.register(CREATIVE_PARTICLE_SOURCE.get(), GuiCreativeParticleSource::new);
    }
}
