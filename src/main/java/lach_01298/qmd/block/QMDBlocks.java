package lach_01298.qmd.block;

import com.nred.nuclearcraft.block.item.NCItemBlock;
import com.nred.nuclearcraft.block.processor.ProcessorBlock;
import com.nred.nuclearcraft.multiblock.fisson.FissionPartType;
import com.nred.nuclearcraft.multiblock.rtg.RTGPartType;
import com.nred.nuclearcraft.multiblock.turbine.TurbinePartType;
import com.nred.nuclearcraft.util.InfoHelper;
import com.nred.nuclearcraft.util.UnitHelper;
import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.enums.MaterialTypes.LuminousPaintType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.nred.nuclearcraft.compat.kubejs.BlockEntityTypeAddBlocksForKubeJS.BLOCK_ENTITY_TYPES_MAP;
import static com.nred.nuclearcraft.registration.BlockEntityRegistration.*;
import static com.nred.nuclearcraft.registration.BlockRegistration.*;
import static lach_01298.qmd.enums.BlockTypes.*;
import static lach_01298.qmd.item.QMDItems.ITEMS;

public class QMDBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(QMD.MOD_ID);

    //    public static Block beamline;
//
//    public static Block linearAcceleratorController;
//    public static Block ringAcceleratorController;
//    public static Block acceleratorBeam;
//    public static Block acceleratorCasing;
//    public static Block acceleratorGlass;
//    public static Block acceleratorVent;
//    public static Block acceleratorBeamPort;
//    public static Block acceleratorSynchrotronPort;
//    public static Block RFCavity;
//    public static Block acceleratorMagnet;
//    public static Block acceleratorYoke;
//    public static Map<CoolerType, Block> acceleratorCoolers = new HashMap<>();
//    public static Block acceleratorSource;
//    public static Block acceleratorEnergyPort;
//    public static Block beamDiverterController;
//    public static Block beamSplitterController;
//    public static Block deceleratorController;
//    public static Block acceleratorComputerPort;
//    public static Block acceleratorPort;
//    public static Block acceleratorRedstonePort;
//    public static Block massSpectrometerController;
//    public static Block acceleratorLaserIonSource;
//    public static Block acceleratorIonCollector;
//
//    public static Block targetChamberController;
//    public static Block decayChamberController;
//    public static Block beamDumpController;
//    public static Block collisionChamberController;
//    public static Block particleChamberBeam;
//    public static Block particleChamberCasing;
//    public static Block particleChamberGlass;
//    public static Block particleChamberBeamPort;
//    public static Block particleChamberDetector;
//    public static Block particleChamberEnergyPort;
//    public static Block particleChamber;
//    public static Block particleChamberPort;
//    public static Block particleChamberFluidPort;
//
    public static DeferredBlock<Block> oreLeacher;
    public static DeferredBlock<Block> irradiator;
    public static DeferredBlock<Block> atmosphereCollector;
    public static DeferredBlock<Block> liquidCollector;

    public static DeferredBlock<Block> fissionReflector;
    public static DeferredBlock<Block> fissionShield;
    public static DeferredBlock<Block> turbineBladeSuperAlloy;
    public static DeferredBlock<Block> rtgStrontium;

    public static Map<LampType, DeferredBlock<Block>> dischargeLamps = new HashMap<>();

    //    public static Block exoticContainmentController;
//    public static Block nucleosynthesisChamberController;
//    public static Block vacuumChamberCasing;
//    public static Block vacuumChamberGlass;
//    public static Block vacuumChamberPort;
//    public static Block vacuumChamberVent;
//    public static Block vacuumChamberBeamPort;
//    public static Block vacuumChamberEnergyPort;
//    public static Block vacuumChamberCoil;
//    public static Block vacuumChamberLaser;
//    public static Block vacuumChamberFluidPort;
//    public static Block vacuumChamberBeam;
//    public static Block vacuumChamberPlasmaNozzle;
//    public static Block vacuumChamberPlasmaGlass;
//    public static Block vacuumChamberHeater;
//    public static Block vacuumChamberHeaterVent;
//    public static Block vacuumChamberRedstonePort;
//
//    public static Block liquefierController;
//    public static Block liquefierNozzle;
//    public static Block liquefierCompressor;
//    public static Block liquefierPort;
//    public static Block liquefierEnergyPort;

    public static DeferredBlock<Block> creativeParticleSource;

    public static DeferredBlock<Block> strontium90;
    public static DeferredBlock<Block> greenLuminousPaint;
    public static DeferredBlock<Block> blueLuminousPaint;
    public static DeferredBlock<Block> orangeLuminousPaint;

    public static void init() {
//        beamline = withName(new BlockBeamline(), "beamline");
//
//        linearAcceleratorController = withName(new BlockLinearAcceleratorController(), "linear_accelerator_controller");
//        ringAcceleratorController = withName(new BlockRingAcceleratorController(), "ring_accelerator_controller");
//        acceleratorBeam = withName(new BlockAcceleratorBeam(), "accelerator_beam");
//        acceleratorCasing = withName(new BlockAcceleratorCasing(), "accelerator_casing");
//        acceleratorGlass = withName(new BlockAcceleratorGlass(), "accelerator_glass");
//        acceleratorVent = withName(new BlockAcceleratorVent(), "accelerator_vent");
//        acceleratorComputerPort = withName(new BlockAcceleratorComputerPort(), "accelerator_computer_port");
//        acceleratorPort = withName(new BlockAcceleratorPort(), "accelerator_port");
//        acceleratorRedstonePort = withName(new BlockAcceleratorRedstonePort(), "accelerator_redstone_port");
//
//        acceleratorBeamPort = withName(new BlockAcceleratorBeamPort(), "accelerator_beam_port");
//        acceleratorSynchrotronPort = withName(new BlockAcceleratorSynchrotronPort(), "accelerator_synchrotron_port");
//        RFCavity = withName(new BlockRFCavity(), "accelerator_cavity");
//        acceleratorMagnet = withName(new BlockAcceleratorMagnet(), "accelerator_magnet");
//        acceleratorYoke = withName(new BlockAcceleratorYoke(), "accelerator_yoke");
//
//        for (CoolerType type : CoolerType.values()) {
//            acceleratorCoolers.put(type, withName(new BlockAcceleratorCooler(), type.getName().toLowerCase() + "_accelerator_cooler"));
//        }
//
//        acceleratorSource = withName(new BlockAcceleratorSource(), "accelerator_source");
//        acceleratorEnergyPort = withName(new BlockAcceleratorEnergyPort(), "accelerator_energy_port");
//        beamDiverterController = withName(new BlockBeamDiverterController(), "beam_diverter_controller");
//        beamSplitterController = withName(new BlockBeamSplitterController(), "beam_splitter_controller");
//        deceleratorController = withName(new BlockDeceleratorController(), "decelerator_controller");
//        massSpectrometerController = withName(new BlockMassSpectrometerController(), "mass_spectrometer_controller");
//        acceleratorLaserIonSource = withName(new BlockAcceleratorLaserIonSource(), "accelerator_laser_ion_source");
//        acceleratorIonCollector = withName(new BlockAcceleratorIonCollector(), "accelerator_ion_collector");
//
//        targetChamberController = withName(new BlockTargetChamberController(), "target_chamber_controller");
//        decayChamberController = withName(new BlockDecayChamberController(), "decay_chamber_controller");
//        beamDumpController = withName(new BlockBeamDumpController(), "beam_dump_controller");
//        collisionChamberController = withName(new BlockCollisionChamberController(), "collision_chamber_controller");
//        particleChamberBeam = withName(new BlockParticleChamberBeam(), "particle_chamber_beam");
//        particleChamberCasing = withName(new BlockParticleChamberCasing(), "particle_chamber_casing");
//        particleChamberGlass = withName(new BlockParticleChamberGlass(), "particle_chamber_glass");
//        particleChamberBeamPort = withName(new BlockParticleChamberBeamPort(), "particle_chamber_beam_port");
//        particleChamberDetector = withName(new BlockParticleChamberDetector(), "particle_chamber_detector");
//        particleChamberEnergyPort = withName(new BlockParticleChamberEnergyPort(), "particle_chamber_energy_port");
//        particleChamber = withName(new BlockParticleChamber(), "particle_chamber");
//        particleChamberPort = withName(new BlockParticleChamberPort(), "particle_chamber_port");
//        particleChamberFluidPort = withName(new BlockParticleChamberFluidPort(), "particle_chamber_fluid_port");
//
        oreLeacher = registerBlockItem("ore_leacher", () -> new ProcessorBlock<>("ore_leacher"));
        irradiator = registerBlockItem("irradiator", () -> new ProcessorBlock<>("irradiator"));

        atmosphereCollector = registerBlockItemWithTooltip("atmosphere_collector", () -> new BlockFluidCollector("atmosphere_collector"), false, Component.translatable("info.qmd.item.energy_used", UnitHelper.prefix(QMDStartupConfig.processor_power[1], 5, "FE/t")), Component.translatable("block.qmd.atmosphere_collector.desc").withStyle(ChatFormatting.GRAY));
        liquidCollector = registerBlockItemWithTooltip("liquid_collector", () -> new BlockFluidCollector("liquid_collector"), false, Component.translatable("info.qmd.item.energy_used", UnitHelper.prefix(QMDStartupConfig.processor_power[2], 5, "FE/t")), Component.translatable("block.qmd.liquid_collector.desc").withStyle(ChatFormatting.GRAY));

        fissionReflector = registerBlockItem("tungsten_carbide_reflector", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

        fissionShield = registerBlockItemWithTooltip("hafnium_shield", () -> FissionPartType.Shield.createBlock(HAFNIUM), x -> SHIELD_FUNCTION.apply(x, HAFNIUM));
        BLOCK_ENTITY_TYPES_MAP.computeIfAbsent(FISSION_ENTITY_TYPE.get("shield").getId(), e -> new ArrayList<>()).add(fissionShield.getId());

        turbineBladeSuperAlloy = registerBlockItemWithTooltip("turbine_blade_super_alloy", () -> TurbinePartType.RotorBlade.createBlock(SUPER_ALLOY), x -> BLADE_FUNCTION.apply(x, SUPER_ALLOY));
        BLOCK_ENTITY_TYPES_MAP.computeIfAbsent(TURBINE_ENTITY_TYPE.get("rotor_blade").getId(), e -> new ArrayList<>()).add(turbineBladeSuperAlloy.getId());

        rtgStrontium = registerBlockItemWithTooltip("rtg_strontium", () -> RTGPartType.RTG.createBlock(STRONTIUM_RTG), x -> RTG_FUNCTION.apply(x, STRONTIUM_RTG));
        BLOCK_ENTITY_TYPES_MAP.computeIfAbsent(RTG_ENTITY_TYPE.getId(), e -> new ArrayList<>()).add(rtgStrontium.getId());

        for (LampType type : LampType.values()) {
            dischargeLamps.put(type, registerBlockItem(type.getSerializedName().toLowerCase() + "_discharge_lamp", BlockLamp::new));
        }

//        exoticContainmentController = withName(new BlockExoticContainmentController(), "neutral_containment_controller");
//        nucleosynthesisChamberController = withName(new BlockNucleosynthesisChamberController(), "nucleosynthesis_chamber_controller");
//        vacuumChamberCasing = withName(new BlockVacuumChamberCasing(), "containment_casing");
//        vacuumChamberGlass = withName(new BlockVacuumChamberGlass(), "containment_glass");
//        vacuumChamberPort = withName(new BlockVacuumChamberPort(), "containment_port");
//        vacuumChamberBeamPort = withName(new BlockVacuumChamberBeamPort(), "containment_beam_port");
//        vacuumChamberVent = withName(new BlockVacuumChamberVent(), "containment_vent");
//        vacuumChamberEnergyPort = withName(new BlockVacuumChamberEnergyPort(), "containment_energy_port");
//        vacuumChamberCoil = withName(new BlockVacuumChamberCoil(), "containment_coil");
//        vacuumChamberLaser = withName(new BlockVacuumChamberLaser(), "containment_laser");
//        vacuumChamberFluidPort = withName(new BlockVacuumChamberFluidPort(), "vacuum_chamber_fluid_port");
//        vacuumChamberBeam = withName(new BlockVacuumChamberBeam(), "vacuum_chamber_beam");
//        vacuumChamberPlasmaNozzle = withName(new BlockVacuumChamberPlasmaNozzle(), "vacuum_chamber_plasma_nozzle");
//        vacuumChamberPlasmaGlass = withName(new BlockVacuumChamberPlasmaGlass(), "vacuum_chamber_plasma_glass");
//        vacuumChamberHeater = withName(new BlockVacuumChamberHeater(), "vacuum_chamber_heater");
//        vacuumChamberHeaterVent = withName(new BlockVacuumChamberHeaterVent(), "vacuum_chamber_heater_vent");
//        vacuumChamberRedstonePort = withName(new BlockVacuumChamberRedstonePort(), "vacuum_chamber_redstone_port");
//
//        liquefierController = withName(new BlockLiquefierController(), "liquefier_controller");
//        liquefierNozzle = withName(new BlockLiquefierNozzle(), "liquefier_nozzle");
//        liquefierCompressor = withName(new BlockLiquefierCompressor(), "liquefier_compressor");
//        liquefierPort = withName(new BlockLiquefierPort(), "liquefier_fluid_port");
//        liquefierEnergyPort = withName(new BlockLiquefierEnergyPort(), "liquefier_energy_port");

        strontium90 = registerBlockItem("strontium_90_block", () -> new BlockQMD(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));

        greenLuminousPaint = registerBlockItem("block_green_luminous_paint", () -> new BlockLuminousPaint(LuminousPaintType.GREEN));
        blueLuminousPaint = registerBlockItem("block_blue_luminous_paint", () -> new BlockLuminousPaint(LuminousPaintType.BLUE));
        orangeLuminousPaint = registerBlockItem("block_orange_luminous_paint", () -> new BlockLuminousPaint(LuminousPaintType.ORANGE));

        creativeParticleSource = registerBlockItem("creative_particle_source", BlockCreativeParticleSource::new);
    }

    public static <T extends Block> DeferredBlock<Block> registerBlockItem(String name, Supplier<T> block) {
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    private static DeferredBlock<Block> registerBlockItemWithTooltip(String name, Supplier<Block> block, Function<Block, ? extends BlockItem> itemBlockFunction) {
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> itemBlockFunction.apply(toReturn.get()));
        return toReturn;
    }

    public static <T extends Block> DeferredBlock<Block> registerBlockItemWithTooltip(String name, Supplier<T> block, boolean hasFixed, Component... tooltip) {
        DeferredBlock<Block> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new NCItemBlock(toReturn.get(), ChatFormatting.RED, InfoHelper.EMPTY_ARRAY, hasFixed, ChatFormatting.AQUA, tooltip));
        return toReturn;
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }

    public static String fixedLine(String name) {
        return "tile." + QMD.MOD_ID + "." + name + ".fixd";
    }

    public static String infoLine(String name) {
        return "tile." + QMD.MOD_ID + "." + name + ".desc";
    }
}