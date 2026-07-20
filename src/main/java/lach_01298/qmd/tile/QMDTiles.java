package lach_01298.qmd.tile;

import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartBlock;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.enums.BlockTypes.CoolerType;
import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
import lach_01298.qmd.machine.tile.TileQMDProcessors.OreLeacherEntity;
import lach_01298.qmd.particleChamber.tile.*;
import lach_01298.qmd.pipe.TileBeamline;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static lach_01298.qmd.block.QMDBlocks.*;
import static lach_01298.qmd.machine.tile.TileQMDProcessors.IrradiatorEntity;

public class QMDTiles {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, QMD.MOD_ID);

    // TODO irradiator is not a processor!?
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends IrradiatorEntity>> IRRADIATOR_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("irradiator", () -> BlockEntityType.Builder.of(IrradiatorEntity::new, irradiator.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends OreLeacherEntity>> ORE_LEACHER_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("ore_leacher", () -> BlockEntityType.Builder.of(OreLeacherEntity::new, oreLeacher.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAtmosphereCollector>> ATMOSPHERE_COLLECTOR_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("atmosphere_collector", () -> BlockEntityType.Builder.of(TileAtmosphereCollector::new, atmosphereCollector.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileLiquidCollector>> LIQUID_COLLECTOR_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("liquid_collector", () -> BlockEntityType.Builder.of(TileLiquidCollector::new, liquidCollector.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileCreativeParticleSource>> CREATIVE_PARTICLE_SOURCE_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("creative_particle_source", () -> BlockEntityType.Builder.of(TileCreativeParticleSource::new, creativeParticleSource.get()).build(null));

//    private static ResourceLocation acceleratorPath = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "accelerator_");
//    private static ResourceLocation magnetPath = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "accelerator_magnet");
//    private static ResourceLocation cavityPath = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "accelerator_cavity");
//    private static ResourceLocation coolerPath = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "accelerator_cooler");
//
//    private static ResourceLocation chamberPath = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "particle_chamber_");
//    private static ResourceLocation detectorPath = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "particle_chamber_detector_");
//    private static ResourceLocation containmentPath = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "containment_");
//    private static ResourceLocation heaterPath = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "containment_heater");
//
//    private static ResourceLocation compressorPath = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "liquefier_compressor");

    // Accelerator parts
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileLinearAcceleratorController>> TILE_LINEAR_ACCELERATOR_CONTROLLER = BLOCK_ENTITY_TYPES.register("accelerator_linear_controller", () -> BlockEntityType.Builder.of(TileLinearAcceleratorController::new, linearAcceleratorController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileRingAcceleratorController>> TILE_RING_ACCELERATOR_CONTROLLER = BLOCK_ENTITY_TYPES.register("accelerator_ring_controller", () -> BlockEntityType.Builder.of(TileRingAcceleratorController::new, ringAcceleratorController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileBeamDiverterController>> TILE_BEAM_DIVERTER_CONTROLLER = BLOCK_ENTITY_TYPES.register("accelerator_beam_diverter_controller", () -> BlockEntityType.Builder.of(TileBeamDiverterController::new, beamDiverterController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileBeamSplitterController>> TILE_BEAM_SPLITTER_CONTROLLER = BLOCK_ENTITY_TYPES.register("accelerator_beam_splitter_controller", () -> BlockEntityType.Builder.of(TileBeamSplitterController::new, beamSplitterController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileDeceleratorController>> TILE_DECELERATOR_CONTROLLER = BLOCK_ENTITY_TYPES.register("accelerator_decelerator_controller", () -> BlockEntityType.Builder.of(TileDeceleratorController::new, deceleratorController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileMassSpectrometerController>> TILE_MASS_SPECTROMETER_CONTROLLER = BLOCK_ENTITY_TYPES.register("accelerator_mass_spectrometer_controller", () -> BlockEntityType.Builder.of(TileMassSpectrometerController::new, massSpectrometerController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorBeam>> TILE_ACCELERATOR_BEAM = BLOCK_ENTITY_TYPES.register("accelerator_beam", () -> BlockEntityType.Builder.of(TileAcceleratorBeam::new, acceleratorBeam.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorCasing>> TILE_ACCELERATOR_CASING = BLOCK_ENTITY_TYPES.register("accelerator_casing", () -> BlockEntityType.Builder.of(TileAcceleratorCasing::new, acceleratorCasing.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorGlass>> TILE_ACCELERATOR_GLASS = BLOCK_ENTITY_TYPES.register("accelerator_glass", () -> BlockEntityType.Builder.of(TileAcceleratorGlass::new, acceleratorGlass.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorVent>> TILE_ACCELERATOR_VENT = BLOCK_ENTITY_TYPES.register("accelerator_vent", () -> BlockEntityType.Builder.of(TileAcceleratorVent::new, acceleratorVent.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorBeamPort>> TILE_ACCELERATOR_BEAM_PORT = BLOCK_ENTITY_TYPES.register("accelerator_beam_port", () -> BlockEntityType.Builder.of(TileAcceleratorBeamPort::new, acceleratorBeamPort.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorSynchrotronPort>> TILE_ACCELERATOR_SYNCHROTRON_PORT = BLOCK_ENTITY_TYPES.register("accelerator_synchrotron_port", () -> BlockEntityType.Builder.of(TileAcceleratorSynchrotronPort::new, acceleratorSynchrotronPort.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorIonSource.Basic>> TILE_ACCELERATOR_ION_SOURCE_BASIC = BLOCK_ENTITY_TYPES.register("accelerator_basic_ion_source", () -> BlockEntityType.Builder.of(TileAcceleratorIonSource.Basic::new, acceleratorSource.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorIonSource.Laser>> TILE_ACCELERATOR_ION_SOURCE_LASER = BLOCK_ENTITY_TYPES.register("accelerator_laser_ion_source", () -> BlockEntityType.Builder.of(TileAcceleratorIonSource.Laser::new, acceleratorLaserIonSource.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorIonCollector>> TILE_ACCELERATOR_ION_COLLECTOR = BLOCK_ENTITY_TYPES.register("accelerator_ion_collector", () -> BlockEntityType.Builder.of(TileAcceleratorIonCollector::new, acceleratorIonCollector.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorYoke>> TILE_ACCELERATOR_YOKE = BLOCK_ENTITY_TYPES.register("accelerator_yoke", () -> BlockEntityType.Builder.of(TileAcceleratorYoke::new, acceleratorYoke.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorEnergyPort>> TILE_ACCELERATOR_ENERGY_PORT = BLOCK_ENTITY_TYPES.register("accelerator_energy_port", () -> BlockEntityType.Builder.of(TileAcceleratorEnergyPort::new, acceleratorEnergyPort.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorComputerPort>> TILE_ACCELERATOR_COMPUTER_PORT = BLOCK_ENTITY_TYPES.register("accelerator_computer_port", () -> BlockEntityType.Builder.of(TileAcceleratorComputerPort::new, acceleratorComputerPort.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorPort>> TILE_ACCELERATOR_PORT = BLOCK_ENTITY_TYPES.register("accelerator_port", () -> BlockEntityType.Builder.of(TileAcceleratorPort::new, acceleratorPort.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorRedstonePort>> TILE_ACCELERATOR_REDSTONE_PORT = BLOCK_ENTITY_TYPES.register("accelerator_redstone_port", () -> BlockEntityType.Builder.of(TileAcceleratorRedstonePort::new, acceleratorRedstonePort.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorCooler>> TILE_ACCELERATOR_COOLER = BLOCK_ENTITY_TYPES.register("accelerator_cooler", () -> BlockEntityType.Builder.of((pos, state) -> new TileAcceleratorCooler(pos, state, ((CoolerType) ((MultiblockPartBlock<?, ?>) state.getBlock()).getMultiblockVariant().get())), acceleratorCoolers.values().stream().map(DeferredHolder::get).toArray(Block[]::new)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorMagnet>> TILE_ACCELERATOR_MAGNET = BLOCK_ENTITY_TYPES.register("accelerator_magnet", () -> BlockEntityType.Builder.of((pos, state) -> new TileAcceleratorMagnet(pos, state, ((MagnetType) ((MultiblockPartBlock<?, ?>) state.getBlock()).getMultiblockVariant().get())), acceleratorMagnets.values().stream().map(DeferredHolder::get).toArray(Block[]::new)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileAcceleratorRFCavity>> TILE_ACCELERATOR_RF_CAVITY = BLOCK_ENTITY_TYPES.register("accelerator_rf_cavity", () -> BlockEntityType.Builder.of((pos, state) -> new TileAcceleratorRFCavity(pos, state, ((RFCavityType) ((MultiblockPartBlock<?, ?>) state.getBlock()).getMultiblockVariant().get())), RFCavities.values().stream().map(DeferredHolder::get).toArray(Block[]::new)).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileBeamline>> TILE_BEAMLINE = BLOCK_ENTITY_TYPES.register("beamline", () -> BlockEntityType.Builder.of(TileBeamline::new, beamline.get()).build(null));


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileTargetChamberController>> TILE_TARGET_CHAMBER_CONTROLLER = BLOCK_ENTITY_TYPES.register("target_chamber_controller", () -> BlockEntityType.Builder.of(TileTargetChamberController::new, targetChamberController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileDecayChamberController>> TILE_DECAY_CHAMBER_CONTROLLER = BLOCK_ENTITY_TYPES.register("decay_chamber_controller", () -> BlockEntityType.Builder.of(TileDecayChamberController::new, decayChamberController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileBeamDumpController>> TILE_BEAM_DUMP_CONTROLLER = BLOCK_ENTITY_TYPES.register("beam_dump_controller", () -> BlockEntityType.Builder.of(TileBeamDumpController::new, beamDumpController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileCollisionChamberController>> TILE_COLLISION_CHAMBER_CONTROLLER = BLOCK_ENTITY_TYPES.register("collision_chamber_controller", () -> BlockEntityType.Builder.of(TileCollisionChamberController::new, collisionChamberController.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileParticleChamberBeam>> TILE_PARTICLE_CHAMBER_BEAM = BLOCK_ENTITY_TYPES.register("particle_chamber_beam", () -> BlockEntityType.Builder.of(TileParticleChamberBeam::new, particleChamberBeam.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileParticleChamberCasing>> TILE_PARTICLE_CHAMBER_CASING = BLOCK_ENTITY_TYPES.register("particle_chamber_casing", () -> BlockEntityType.Builder.of(TileParticleChamberCasing::new, particleChamberCasing.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileParticleChamberGlass>> TILE_PARTICLE_CHAMBER_GLASS = BLOCK_ENTITY_TYPES.register("particle_chamber_glass", () -> BlockEntityType.Builder.of(TileParticleChamberGlass::new, particleChamberGlass.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileParticleChamberBeamPort>> TILE_PARTICLE_CHAMBER_BEAM_PORT = BLOCK_ENTITY_TYPES.register("particle_chamber_beam_port", () -> BlockEntityType.Builder.of(TileParticleChamberBeamPort::new, particleChamberBeamPort.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileParticleChamber>> TILE_PARTICLE_CHAMBER = BLOCK_ENTITY_TYPES.register("particle_chamber", () -> BlockEntityType.Builder.of(TileParticleChamber::new, particleChamber.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileParticleChamberEnergyPort>> TILE_PARTICLE_CHAMBER_ENERGY_PORT = BLOCK_ENTITY_TYPES.register("particle_chamber_energy_port", () -> BlockEntityType.Builder.of(TileParticleChamberEnergyPort::new, particleChamberEnergyPort.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileParticleChamberPort>> TILE_PARTICLE_CHAMBER_PORT = BLOCK_ENTITY_TYPES.register("particle_chamber_port", () -> BlockEntityType.Builder.of(TileParticleChamberPort::new, particleChamberPort.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileParticleChamberFluidPort>> TILE_PARTICLE_CHAMBER_FLUID_PORT = BLOCK_ENTITY_TYPES.register("particle_chamber_fluid_port", () -> BlockEntityType.Builder.of(TileParticleChamberFluidPort::new, particleChamberFluidPort.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends TileParticleChamberDetector>> TILE_PARTICLE_CHAMBER_DETECTOR = BLOCK_ENTITY_TYPES.register("particle_chamber_detector", () -> BlockEntityType.Builder.of((pos, state) -> new TileParticleChamberDetector(pos, state, ((DetectorType) ((MultiblockPartBlock<?, ?>) state.getBlock()).getMultiblockVariant().get())), particleChamberDetectors.values().stream().map(DeferredHolder::get).toArray(Block[]::new)).build(null));

    public static void init() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);

//		//vacuum chamber parts
//		GameRegistry.registerTileEntity(TileExoticContainmentController.class,Util.appendPath(containmentPath, "neutral_containment_controller"));
//		GameRegistry.registerTileEntity(TileNucleosynthesisChamberController.class,Util.appendPath(containmentPath, "neucleosynthesis_chamber_controller"));
//		GameRegistry.registerTileEntity(TileVacuumChamberCasing.class,Util.appendPath(containmentPath, "casing"));
//		GameRegistry.registerTileEntity(TileVacuumChamberGlass.class,Util.appendPath(containmentPath, "glass"));;
//		GameRegistry.registerTileEntity(TileVacuumChamberBeamPort.class,Util.appendPath(containmentPath, "beam_port"));
//		GameRegistry.registerTileEntity(TileVacuumChamberEnergyPort.class,Util.appendPath(containmentPath, "energy_port"));
//		GameRegistry.registerTileEntity(TileVacuumChamberPort.class,Util.appendPath(containmentPath, "port"));
//		GameRegistry.registerTileEntity(TileVacuumChamberVent.class,Util.appendPath(containmentPath, "vent"));
//		GameRegistry.registerTileEntity(TileVacuumChamberCoil.class,Util.appendPath(containmentPath, "coil"));
//		GameRegistry.registerTileEntity(TileVacuumChamberLaser.class,Util.appendPath(containmentPath, "laser"));
//		GameRegistry.registerTileEntity(TileVacuumChamberBeam.class,Util.appendPath(containmentPath, "beam"));
//		GameRegistry.registerTileEntity(TileVacuumChamberFluidPort.class,Util.appendPath(containmentPath, "fluid_port"));
//		GameRegistry.registerTileEntity(TileVacuumChamberPlasmaNozzle.class,Util.appendPath(containmentPath, "cd_nozzle"));
//		GameRegistry.registerTileEntity(TileVacuumChamberPlasmaGlass.class,Util.appendPath(containmentPath, "plasma_glass"));
//		GameRegistry.registerTileEntity(TileVacuumChamberHeaterVent.class,Util.appendPath(containmentPath, "heater_vent"));
//		GameRegistry.registerTileEntity(TileVacuumChamberRedstonePort.class,Util.appendPath(containmentPath, "redstone_port"));
//
//		//heaters
//		GameRegistry.registerTileEntity(TileVacuumChamberHeater.class, Util.appendPath(heaterPath, "heater"));
//		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Iron.class, Util.appendPath(heaterPath, HeaterType.IRON.getName()));
//		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Redstone.class, Util.appendPath(heaterPath, HeaterType.REDSTONE.getName()));
//		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Quartz.class, Util.appendPath(heaterPath, HeaterType.QUARTZ.getName()));
//		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Obsidian.class, Util.appendPath(heaterPath, HeaterType.OBSIDIAN.getName()));
//		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Glowstone.class,Util.appendPath(heaterPath, HeaterType.GLOWSTONE.getName()));
//		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Lapis.class, Util.appendPath(heaterPath, HeaterType.LAPIS.getName()));
//		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Gold.class,Util.appendPath(heaterPath, HeaterType.GOLD.getName()));
//		GameRegistry.registerTileEntity(TileVacuumChamberHeater.Diamond.class, Util.appendPath(heaterPath, HeaterType.DIAMOND.getName()));
//
//		// liquefier parts
//		GameRegistry.registerTileEntity(TileLiquefierController.class,new ResourceLocation(QMD.MOD_ID,"liquefier_controller"));
//		GameRegistry.registerTileEntity(TileLiquefierNozzle.class,new ResourceLocation(QMD.MOD_ID,"liquefier_nozzle"));
//		GameRegistry.registerTileEntity(TileLiquefierFluidPort.class,new ResourceLocation(QMD.MOD_ID,"liquefier_fluid_port"));
//		GameRegistry.registerTileEntity(TileLiquefierEnergyPort.class,new ResourceLocation(QMD.MOD_ID,"liquefier_energy_port"));
//		GameRegistry.registerTileEntity(TileLiquefierCompressor.Copper.class, Util.appendPath(compressorPath, CompressorType.COPPER.getName()));
//		GameRegistry.registerTileEntity(TileLiquefierCompressor.Neodymium.class, Util.appendPath(compressorPath, CompressorType.NEODYMIUM.getName()));
//		GameRegistry.registerTileEntity(TileLiquefierCompressor.SamariumCobalt.class, Util.appendPath(compressorPath, CompressorType.SAMARIUM_COBALT.getName()));
    }
}