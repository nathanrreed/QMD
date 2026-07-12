package lach_01298.qmd.menu;

import lach_01298.qmd.QMD;
import lach_01298.qmd.machine.container.MachineMenuImpl.CreativeParticleSourceMenu;
import lach_01298.qmd.machine.container.MachineMenuImpl.IrradiatorMenu;
import lach_01298.qmd.machine.container.MachineMenuImpl.OreLeacherMenu;
import lach_01298.qmd.multiblock.container.*;
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

    public static final DeferredHolder<MenuType<?>, MenuType<CreativeParticleSourceMenu>> CREATIVE_PARTICLE_SOURCE_MENU = MENUS.register("creative_particle_source", () -> IMenuTypeExtension.create(CreativeParticleSourceMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<ContainerBeamDiverterController>> BEAM_DIVERTER_CONTROLLER_MENU = MENUS.register("beam_diverter_controller", () -> IMenuTypeExtension.create(ContainerBeamDiverterController::new));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerBeamSplitterController>> BEAM_SPLITTER_CONTROLLER_MENU = MENUS.register("beam_splitter_controller", () -> IMenuTypeExtension.create(ContainerBeamSplitterController::new));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerDeceleratorController>> DECELERATOR_CONTROLLER_MENU = MENUS.register("decelerator_controller", () -> IMenuTypeExtension.create(ContainerDeceleratorController::new));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerLinearAcceleratorController>> LINEAR_ACCELERATOR_CONTROLLER_MENU = MENUS.register("linear_accelerator_controller", () -> IMenuTypeExtension.create(ContainerLinearAcceleratorController::new));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerRingAcceleratorController>> RING_ACCELERATOR_CONTROLLER_MENU = MENUS.register("ring_accelerator_controller", () -> IMenuTypeExtension.create(ContainerRingAcceleratorController::new));
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerMassSpectrometerController>> MASS_SPECTROMETER_CONTROLLER_MENU = MENUS.register("mass_spectrometer_controller", () -> IMenuTypeExtension.create(ContainerMassSpectrometerController::new));

    public static final DeferredHolder<MenuType<?>, MenuType<ContainerAcceleratorIonSource>> ACCELERATOR_ION_SOURCE_MENU = MENUS.register("accelerator_ion_source", () -> IMenuTypeExtension.create(ContainerAcceleratorIonSource::new));

    public static void init() {
    }

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}
