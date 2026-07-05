package lach_01298.qmd.menu;

import lach_01298.qmd.QMD;
import lach_01298.qmd.machine.container.MachineMenuImpl;
import lach_01298.qmd.machine.container.MachineMenuImpl.IrradiatorMenu;
import lach_01298.qmd.machine.container.MachineMenuImpl.CreativeParticleSourceMenu;
import lach_01298.qmd.machine.container.MachineMenuImpl.OreLeacherMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class QMDMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, QMD.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<IrradiatorMenu>> IRRADIATOR_MENU = MENUS.register("irradiator", () -> IMenuTypeExtension.create(IrradiatorMenu::new));
    public static final DeferredHolder<MenuType<?>, MenuType<OreLeacherMenu>> ORE_LEACHER_MENU = MENUS.register("ore_leacher", () -> IMenuTypeExtension.create(OreLeacherMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<CreativeParticleSourceMenu>> CREATIVE_PARTICLE_SOURCE = MENUS.register("creative_particle_source", () -> IMenuTypeExtension.create(CreativeParticleSourceMenu::new));

    public static void init() {
    }

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}
