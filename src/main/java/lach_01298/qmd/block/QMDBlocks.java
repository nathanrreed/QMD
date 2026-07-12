package lach_01298.qmd.block;

import com.nred.nuclearcraft.block.item.NCItemBlock;
import com.nred.nuclearcraft.block.processor.ProcessorBlock;
import com.nred.nuclearcraft.multiblock.fisson.FissionPartType;
import com.nred.nuclearcraft.multiblock.rtg.RTGPartType;
import com.nred.nuclearcraft.multiblock.turbine.TurbinePartType;
import com.nred.nuclearcraft.util.InfoHelper;
import com.nred.nuclearcraft.util.UnitHelper;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.AcceleratorPartType;
import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.enums.MaterialTypes.LuminousPaintType;
import lach_01298.qmd.pipe.BeamLinePartType;
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

    public static DeferredBlock<Block> beamline;

    public static DeferredBlock<Block> linearAcceleratorController;
    public static DeferredBlock<Block> ringAcceleratorController;
    public static DeferredBlock<Block> acceleratorBeam;
    public static DeferredBlock<Block> acceleratorCasing;
    public static DeferredBlock<Block> acceleratorGlass;
    public static DeferredBlock<Block> acceleratorVent;
    public static DeferredBlock<Block> acceleratorBeamPort;
    public static DeferredBlock<Block> acceleratorSynchrotronPort;
    public static Map<RFCavityType, DeferredBlock<Block>> RFCavities = new HashMap<>();
    public static Map<MagnetType, DeferredBlock<Block>> acceleratorMagnets = new HashMap<>();
    public static DeferredBlock<Block> acceleratorYoke;
    public static Map<CoolerType, DeferredBlock<Block>> acceleratorCoolers = new HashMap<>();
    public static DeferredBlock<Block> acceleratorSource;
    public static DeferredBlock<Block> acceleratorEnergyPort;
    public static DeferredBlock<Block> beamDiverterController;
    public static DeferredBlock<Block> beamSplitterController;
    public static DeferredBlock<Block> deceleratorController;
    public static DeferredBlock<Block> acceleratorComputerPort;
    public static DeferredBlock<Block> acceleratorPort;
    public static DeferredBlock<Block> acceleratorRedstonePort;
    public static DeferredBlock<Block> massSpectrometerController;
    public static DeferredBlock<Block> acceleratorLaserIonSource;
    public static DeferredBlock<Block> acceleratorIonCollector;

//    public static Block targetChamberController; TODO
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

//    public static Block exoticContainmentController; TODO
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
//    public static Block liquefierController; TODO
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
        beamline = registerBlockItem("beamline", BeamLinePartType.BeamLine::createBlock);

        linearAcceleratorController = registerBlockItem("linear_accelerator_controller", AcceleratorPartType.LinearAcceleratorController::createBlock);
        ringAcceleratorController = registerBlockItem("ring_accelerator_controller", AcceleratorPartType.RingAcceleratorController::createBlock);
        beamDiverterController = registerBlockItem("beam_diverter_controller", AcceleratorPartType.BeamDiverterController::createBlock);
        beamSplitterController = registerBlockItem("beam_splitter_controller", AcceleratorPartType.BeamSplitterController::createBlock);
        deceleratorController = registerBlockItem("decelerator_controller", AcceleratorPartType.DeceleratorController::createBlock);
        massSpectrometerController = registerBlockItem("mass_spectrometer_controller", AcceleratorPartType.MassSpectrometerController::createBlock);

        acceleratorBeam = registerBlockItem("accelerator_beam", AcceleratorPartType.AcceleratorBeam::createBlock);
        acceleratorCasing = registerBlockItem("accelerator_casing", AcceleratorPartType.AcceleratorCasing::createBlock);
        acceleratorGlass = registerBlockItem("accelerator_glass", AcceleratorPartType.AcceleratorGlass::createBlock);
        acceleratorVent = registerBlockItem("accelerator_vent", AcceleratorPartType.AcceleratorVent::createBlock);
        acceleratorComputerPort = registerBlockItem("accelerator_computer_port", AcceleratorPartType.AcceleratorComputerPort::createBlock);
        acceleratorPort = registerBlockItem("accelerator_port", AcceleratorPartType.AcceleratorPort::createBlock);
        acceleratorRedstonePort = registerBlockItem("accelerator_redstone_port", AcceleratorPartType.AcceleratorRedstonePort::createBlock);

        acceleratorBeamPort = registerBlockItem("accelerator_beam_port", AcceleratorPartType.AcceleratorBeamPort::createBlock);
        acceleratorSynchrotronPort = registerBlockItem("accelerator_synchrotron_port", AcceleratorPartType.AcceleratorSynchrotronPort::createBlock);
        acceleratorYoke = registerBlockItem("accelerator_yoke", AcceleratorPartType.AcceleratorYoke::createBlock);

        for (RFCavityType type : RFCavityType.values()) {
            RFCavities.put(type, registerBlockItem(type.getName().toLowerCase() + "_accelerator_cavity", () -> AcceleratorPartType.RFCavity.createBlock(type)));
        }
        for (MagnetType type : MagnetType.values()) {
            acceleratorMagnets.put(type, registerBlockItem(type.getName().toLowerCase() + "_accelerator_magnet", () -> AcceleratorPartType.AcceleratorMagnet.createBlock(type)));
        }
        for (CoolerType type : CoolerType.values()) {
            acceleratorCoolers.put(type, registerBlockItem(type.getName().toLowerCase() + "_accelerator_cooler", () -> AcceleratorPartType.AcceleratorCooler.createBlock(type)));
        }

        acceleratorSource = registerBlockItem("accelerator_source", AcceleratorPartType.AcceleratorSource::createBlock);
        acceleratorEnergyPort = registerBlockItem("accelerator_energy_port", AcceleratorPartType.AcceleratorEnergyPort::createBlock);
        acceleratorLaserIonSource = registerBlockItem("accelerator_laser_ion_source", AcceleratorPartType.AcceleratorLaserIonSource::createBlock);
        acceleratorIonCollector = registerBlockItem("accelerator_ion_collector", AcceleratorPartType.AcceleratorIonCollector::createBlock);

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