package lach_01298.qmd.init;

import lach_01298.qmd.QMD;
import lach_01298.qmd.item.IItemParticleAmount;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.stream.Stream;

import static lach_01298.qmd.block.QMDBlocks.*;
import static lach_01298.qmd.enums.MaterialTypes.SourceType;
import static lach_01298.qmd.item.QMDItems.*;


public class QMDCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, QMD.MOD_ID);

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> ITEMS_TAB = CREATIVE_MODE_TABS.register(QMD.MOD_ID + "_items_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + QMD.MOD_ID + ".items"))
            .icon(() -> sources.get(SourceType.SODIUM_22).get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(Stream.of(dusts, ingots, chemicalDusts, ingotAlloys, isotopes, parts, semiconductors, fissionWastes, spallationWastes, luminousPaints).flatMap(e -> e.values().parallelStream()).map(DeferredItem::toStack).toList());
                output.acceptAll(Stream.of(cells, sources).flatMap(e -> e.values().parallelStream()).map(e -> IItemParticleAmount.fullItem(e.get().getDefaultInstance())).toList()); // Full bar in creative tab
                output.acceptAll(Stream.of(sword_tungsten_carbide, pickaxe_tungsten_carbide, shovel_tungsten_carbide, axe_tungsten_carbide, hoe_tungsten_carbide).map(DeferredItem::toStack).toList());
                output.accept(flesh);
                output.accept(potassiumIodineTablet);
                output.accept(beamMeter);

                output.acceptAll(Stream.of(copernicium, pellet_copernicium, fuel_copernicium, depleted_fuel_copernicium).flatMap(e -> e.values().parallelStream()).map(DeferredItem::toStack).toList());
                // TODO add drills, hev, etc
            }).build());


    public static DeferredHolder<CreativeModeTab, CreativeModeTab> MULTI_BLOCKS_TAB = CREATIVE_MODE_TABS.register(QMD.MOD_ID + "_multiblocks_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + QMD.MOD_ID + ".multiblocks"))
            .icon(() -> linearAcceleratorController.get().asItem().getDefaultInstance())
            .withTabsBefore(ITEMS_TAB.getId())
            .displayItems((parameters, output) -> {
                output.accept(turbineBladeSuperAlloy);
                output.accept(fissionShield);
                output.accept(fissionReflector);

                output.accept(linearAcceleratorController);
                output.accept(ringAcceleratorController);
                output.accept(acceleratorBeam);
                output.accept(acceleratorCasing);
                output.accept(acceleratorGlass);
                output.accept(acceleratorVent);
                output.accept(acceleratorBeamPort);
                output.accept(acceleratorSynchrotronPort);
                output.accept(acceleratorYoke);
                output.accept(acceleratorSource);
                output.accept(acceleratorEnergyPort);
                output.accept(beamDiverterController);
                output.accept(beamSplitterController);
                output.accept(deceleratorController);
                output.accept(acceleratorComputerPort);
                output.accept(acceleratorPort);
                output.accept(acceleratorRedstonePort);
                output.accept(massSpectrometerController);
                output.accept(acceleratorLaserIonSource);
                output.accept(acceleratorIonCollector);
                output.acceptAll(RFCavities.values().stream().map(e -> e.get().asItem().getDefaultInstance()).toList());
                output.acceptAll(acceleratorMagnets.values().stream().map(e -> e.get().asItem().getDefaultInstance()).toList());
                output.acceptAll(acceleratorCoolers.values().stream().map(e -> e.get().asItem().getDefaultInstance()).toList());

                output.accept(targetChamberController);
                output.accept(decayChamberController);
                output.accept(beamDumpController);
                output.accept(collisionChamberController);
                output.accept(particleChamberBeam);
                output.accept(particleChamberCasing);
                output.accept(particleChamberGlass);
                output.accept(particleChamberBeamPort);
                output.accept(particleChamberEnergyPort);
                output.accept(particleChamber);
                output.accept(particleChamberPort);
                output.accept(particleChamberFluidPort);
                output.acceptAll(particleChamberDetectors.values().stream().map(e -> e.get().asItem().getDefaultInstance()).toList());
            }).build());

    public static DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKS_TAB = CREATIVE_MODE_TABS.register(QMD.MOD_ID + "_blocks_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + QMD.MOD_ID + ".blocks"))
            .icon(() -> oreLeacher.asItem().getDefaultInstance())
            .withTabsBefore(MULTI_BLOCKS_TAB.getId())
            .displayItems((parameters, output) -> {
                output.accept(creativeParticleSource);
                output.accept(beamline);
                output.accept(oreLeacher);
                output.accept(irradiator);
                output.accept(atmosphereCollector);
                output.accept(liquidCollector);
                output.accept(rtgStrontium);
                output.acceptAll(Stream.of(dischargeLamps).flatMap(e -> e.values().parallelStream()).map(e -> e.asItem().getDefaultInstance()).toList());
                output.accept(strontium90);
            }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}