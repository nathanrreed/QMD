package lach_01298.qmd.config;

import com.google.common.primitives.Booleans;
import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDRadSources;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Predicate;

@EventBusSubscriber(modid = QMD.MOD_ID)
public class QMDStartupConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final String CATEGORY_PROCESSORS = "processors";
    public static final String CATEGORY_ACCELERATOR = "accelerator";
    public static final String CATEGORY_PARTICLE_CHAMBER = "particle_chamber";
    public static final String CATEGORY_VACUUM_CHAMBER = "vacuum_chamber";
    public static final String CATEGORY_HEAT_EXCHANGER = "heat_exchanger";
    public static final String CATEGORY_FISSION = "fission";
    public static final String CATEGORY_FUSION = "fusion";
    public static final String CATEGORY_TOOLS = "tools";
    public static final String CATEGORY_RECIPES = "recipes";
    public static final String CATEGORY_OTHER = "other";

    public static int accelerator_linear_min_size;
    public static int accelerator_linear_max_size;
    public static int accelerator_ring_min_size;
    public static int accelerator_ring_max_size;

    public static int accelerator_base_heat_capacity;
    public static int accelerator_base_energy_capacity;
    public static int accelerator_base_input_tank_capacity;
    public static int accelerator_base_output_tank_capacity;

    public static double accelerator_thermal_conductivity;
    public static int minimium_accelerator_ring_input_particle_energy;
    public static int[] ion_source_power;
    public static int[] ion_source_output_multiplier;
    public static double[] ion_source_focus;

    public static int[] RF_cavity_voltage; //in keV
    public static double[] RF_cavity_efficiency;
    public static int[] RF_cavity_heat_generated;
    public static int[] RF_cavity_base_power;
    public static int[] RF_cavity_max_temp;

    public static double[] magnet_strength;
    public static double[] magnet_efficiency;
    public static int[] magnet_heat_generated;
    public static int[] magnet_base_power;
    public static int[] magnet_max_temp;

    public static int[] cooler_heat_removed;
    public static String[] cooler_rule;

    public static double beamAttenuationRate;
    public static int beamDiverterRadius;

    public static String[] mass_spectrometer_valid_magnets;
    public static String[] mass_spectrometer_valid_sources;

    public static boolean accelerator_explosion;

    public static int target_chamber_power;
    public static int decay_chamber_power;
    public static int beam_dump_power;
    public static int collision_chamber_power;
    public static int[] detector_base_power;
    public static double[] detector_efficiency;

    public static int particle_chamber_base_energy_capacity;
    public static int particle_chamber_input_tank_capacity;
    public static int particle_chamber_output_tank_capacity;

    public static int[] vacuum_chamber_part_power;
    public static int[] vacuum_chamber_part_heat;
    public static int[] vacuum_chamber_part_max_temp;

    public static int vacuum_chamber_base_energy_capacity;
    public static int vacuum_chamber_input_tank_capacity;
    public static int vacuum_chamber_output_tank_capacity;

    public static boolean exotic_containment_explosion;
    public static boolean exotic_containment_gamma_flash;
    public static double exotic_containment_radiation;
    public static double exotic_containment_explosion_size;

    public static boolean nucleosynthesis_chamber_explosion;

    public static int[] heater_heat_removed;
    public static String[] heater_rule;

    public static int liquefier_base_energy_capacity;
    public static int liquefier_input_tank_capacity;
    public static int liquefier_output_tank_capacity;
    public static double[] liquefier_compressor_energy_efficiency;
    public static double[] liquefier_compressor_heat_efficiency;
    public static double liquefier_nozzle_speed;

    public static int[] processor_power;
    public static int[] processor_time;
    public static double irradiator_rad_res;
    public static int irradiator_fuel_usage;
    public static String[] tool_mining_level;
    public static int[] tool_durability;
    public static double[] tool_speed;
    public static double[] tool_attack_damage;
    public static int[] tool_enchantability;
    public static int drill_energy_usage;
    public static int[] drill_energy_capacity;
    public static int[] drill_radius;

    public static double[] lepton_damage;
    public static double[] lepton_radiation;
    public static double[] lepton_range;
    public static int lepton_cool_down;
    public static int lepton_particle_usage;

    public static double gluon_damage;
    public static double gluon_radiation;
    public static double gluon_range;
    public static int gluon_particle_usage;

    public static double antimatter_launcher_damage;
    public static double antimatter_launcher_radiation;
    public static double antimatter_launcher_explosion_size;
    public static int antimatter_launcher_cool_down;
    public static int antimatter_launcher_particle_usage;

    public static int[] hev_armour;
    public static double[] hev_rad_res;
    public static double[] hev_toughness;
    public static int[] hev_energy;
    public static int[] hev_power;

    public static int ki_time;

    public static double[] fission_shield_heat_per_flux;
    public static double[] fission_shield_efficiency;

    public static int[] copernicium_fuel_time;
    public static int[] copernicium_heat_generation;
    public static double[] copernicium_efficiency;
    public static int[] copernicium_criticality;
    public static int[] copernicium_intrinsic_flux;
    public static double[] copernicium_decay_factor;
    public static boolean[] copernicium_self_priming;
    public static double[] copernicium_radiation;

    public static int[] rtg_power;

    public static int beam_scaling;

    //recipe scale factors
    public static int rsf_target_chamber;
    public static int rsf_nucleosynthesis;

    public static boolean override_nc_recipes;

    //public static int item_ticker_chunks_per_tick;

    public static double[] turbine_blade_efficiency;
    public static double[] turbine_blade_expansion;


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    static void onLoad(final ModConfigEvent event) {
        if (event instanceof ModConfigEvent.Unloading || event.getConfig().getSpec() != SPEC)
            return;
        loadConfig();
    }

    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyProcessorPower = add(CATEGORY_PROCESSORS, "power", List.of(50, 100, 50), 0, 32767, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyProcessorTime = add(CATEGORY_PROCESSORS, "time", List.of(400, 200, 1000), 0, 32767, true);

    private static final ModConfigSpec.DoubleValue propertyIrradiatorRadRes = add(CATEGORY_PROCESSORS, "irradiator_rad_res", 10000.0D, 0.0D, Double.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyIrradiatorFuelUsage = add(CATEGORY_PROCESSORS, "irradiator_fuel_usage", 10, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue propertyAcceleratorLinearMinSize = add(CATEGORY_ACCELERATOR, "accelerator_linear_min_size", 6, 6, 255);
    private static final ModConfigSpec.IntValue propertyAcceleratorLinearMaxSize = add(CATEGORY_ACCELERATOR, "accelerator_linear_max_size", 100, 6, 255);

    private static final ModConfigSpec.IntValue propertyAcceleratorRingMinSize = add(CATEGORY_ACCELERATOR, "accelerator_ring_min_size", 11, 11, 255);
    private static final ModConfigSpec.IntValue propertyAcceleratorRingMaxSize = add(CATEGORY_ACCELERATOR, "accelerator_ring_max_size", 100, 11, 255);

    private static final ModConfigSpec.IntValue propertyAcceleratorBaseHeatCapacity = add(CATEGORY_ACCELERATOR, "accelerator_base_heat_capacity", 25000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyAcceleratorBaseEnergyCapacity = add(CATEGORY_ACCELERATOR, "accelerator_base_energy_capacity", 40000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyAcceleratorBaseInputTankCapacity = add(CATEGORY_ACCELERATOR, "accelerator_base_input_tank_capacity", 10, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyAcceleratorBaseOutputTankCapacity = add(CATEGORY_ACCELERATOR, "accelerator_base_output_tank_capacity", 3200, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.DoubleValue propertyAcceleratorThermalConductivity = add(CATEGORY_ACCELERATOR, "accelerator_thermal_conductivity", 0.0025d, 0d, Double.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyAcceleratorRingInputEnergy = add(CATEGORY_ACCELERATOR, "minimium_accelerator_ring_input_particle_energy", 5000, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyIonSourcePower = add(CATEGORY_ACCELERATOR, "ion_source_power", List.of(500, 2000), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyIonSourceOutputMultiplier = add(CATEGORY_ACCELERATOR, "ion_source_output_multiplier", List.of(1, 2), 1, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyIonSourceFocus = add(CATEGORY_ACCELERATOR, "ion_source_focus", List.of(0.4, 0.2d), 0d, Double.MAX_VALUE, true);


    private static final ModConfigSpec.DoubleValue propertyBeamAttenuationRate = add(CATEGORY_ACCELERATOR, "beam_attenuation_rate", 0.02D, 0.0D, 1000D);
    private static final ModConfigSpec.IntValue propertyBeamDiverterRadius = add(CATEGORY_ACCELERATOR, "beam_diverter_radius", 160, 0, 1000);

    private static final ModConfigSpec.ConfigValue<List<? extends String>> propertyMassSpectrometerValidMagnets = addString(CATEGORY_ACCELERATOR, "mass_spectrometer_valid_magnets", List.of("bscco", "ybco"), true);

    private static final ModConfigSpec.ConfigValue<List<? extends String>> propertyMassSpectrometerValidSources = addString(CATEGORY_ACCELERATOR, "mass_spectrometer_valid_sources", List.of("basic", "laser"), true);

    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyRFCavityVoltage = add(CATEGORY_ACCELERATOR, "RF_cavity_voltage", List.of(200, 500, 1000, 2000, 4000, 100, 1500, 3000), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyRFCavityEfficiency = add(CATEGORY_ACCELERATOR, "RF_cavity_efficiency", List.of(0.75D, 0.8D, 0.90D, 0.95D, 0.99D, 0.5D, 0.95D, 0.99D), 0D, 1D, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyRFCavityHeatGenerated = add(CATEGORY_ACCELERATOR, "RF_cavity_heat_generated", List.of(300, 540, 1020, 1980, 3900, 180, 1500, 2940), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyRFCavityBasePower = add(CATEGORY_ACCELERATOR, "RF_cavity_base_power", List.of(500, 1000, 2000, 4000, 8000, 250, 3000, 6000), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyRFCavityMaxTemp = add(CATEGORY_ACCELERATOR, "RF_cavity_max_temp", List.of(350, 39, 18, 10, 110, 350, 56, 95), 0, 400, true);

    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyMagnetStrength = add(CATEGORY_ACCELERATOR, "magnet_strength", List.of(0.2D, 0.5D, 1D, 2D, 4D, 0.1D, 1.5D, 3D), 0D, 100D, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyMagnetEfficiency = add(CATEGORY_ACCELERATOR, "magnet_efficiency", List.of(0.75D, 0.8D, 0.90D, 0.95D, 0.99D, 0.5D, 0.95D, 0.99D), 0D, 1D, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyMagnetHeatGenerated = add(CATEGORY_ACCELERATOR, "magnet_heat_generated", List.of(300, 540, 1020, 1980, 3900, 180, 1500, 2940), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyMagnetBasePower = add(CATEGORY_ACCELERATOR, "magnet_base_power", List.of(1000, 2000, 4000, 8000, 16000, 500, 6000, 12000), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyMagnetMaxTemp = add(CATEGORY_ACCELERATOR, "magnet_max_temp", List.of(350, 39, 18, 10, 110, 350, 56, 95), 0, Integer.MAX_VALUE, true);

    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyCoolerHeatRemoved = add(CATEGORY_ACCELERATOR, "cooler_heat_removed", List.of(60, 55, 115, 75, 70, 90, 110, 130, 95, 85, 165, 50, 100, 185, 135, 80, 120, 65, 105, 125, 150, 180, 175, 160, 155, 170, 140, 145, 195, 200, 190, 205), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends String>> propertyCoolerRule = addString(CATEGORY_ACCELERATOR, "cooler_rule", List.of("one cavity",
            "one magnet", "one cavity && one magnet", "one redstone cooler", "two glowstone coolers",
            "one obsidian cooler", "two different magnets", "one yoke && one magnet", "two iron coolers", "two water coolers",
            "two lead coolers && one water cooler", "one yoke", "two end_stone coolers", "one gold cooler && one prismarine cooler",
            "one cavity && one prismarine cooler", "one water cooler", "two lapis coolers", "one iron cooler",
            "one yoke && one cavity", "one boron cooler", "one end_stone cooler && one prismarine cooler",
            "one gold cooler && one quartz cooler", "one tin cooler && one quartz cooler", "two arsenic coolers",
            "three gold coolers", "one purpur cooler && one prismarine cooler",
            "one end_stone cooler && one gold cooler", "two different cavity", "one lapis cooler && one gold cooler",
            "one boron cooler && one lapis cooler", "three purpur coolers", "three tin coolers"), true);

    private static final ModConfigSpec.BooleanValue propertyAcceleratorExplosion = add(CATEGORY_ACCELERATOR, "accelerator_explosion", true);

    private static final ModConfigSpec.IntValue propertyTargetChamberPower = add(CATEGORY_PARTICLE_CHAMBER, "target_chamber_power", 5000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyDecayChamberPower = add(CATEGORY_PARTICLE_CHAMBER, "decay_chamber_power", 5000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyBeamDumpPower = add(CATEGORY_PARTICLE_CHAMBER, "beam_dump_power", 5000, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyCollisionChamberPower = add(CATEGORY_PARTICLE_CHAMBER, "collision_chamber_power", 5000, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyDetectorEfficiency = add(CATEGORY_PARTICLE_CHAMBER, "detector_efficiency", List.of(0.075D, 0.15D, 0.1D, 0.05D, 0.025D), 0D, 100D, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyDetectorBasePower = add(CATEGORY_PARTICLE_CHAMBER, "detector_base_power", List.of(200, 2000, 1000, 200, 100), 0, Integer.MAX_VALUE, true);


    private static final ModConfigSpec.IntValue propertyParticleChamberBaseEnergyCapacity = add(CATEGORY_PARTICLE_CHAMBER, "particle_chamber_base_energy_capacity", 40000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyParticleChamberInputTankCapacity = add(CATEGORY_PARTICLE_CHAMBER, "particle_chamber_base_input_tank_capacity", 16000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyParticleChamberOutputTankCapacity = add(CATEGORY_PARTICLE_CHAMBER, "particle_chamber_base_output_tank_capacity", 1000, 1, Integer.MAX_VALUE);


    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyContainmentPartPower = add(CATEGORY_VACUUM_CHAMBER, "part_power", List.of(400, 500, 500, 500, 1000), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyContainmentPartHeat = add(CATEGORY_VACUUM_CHAMBER, "part_heat", List.of(200, 500, 100, 100, 500), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyContainmentMaxTemp = add(CATEGORY_VACUUM_CHAMBER, "part_max_temp", List.of(110, 110, 110, 110, 110), 0, 400, true);

    private static final ModConfigSpec.IntValue propertyVacuumChamberBaseEnergyCapacity = add(CATEGORY_VACUUM_CHAMBER, "vacuum_chamber_base_energy_capacity", 40000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyVacuumChamberInputTankCapacity = add(CATEGORY_VACUUM_CHAMBER, "vacuum_chamber_base_input_tank_capacity", 1000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyVacuumChamberOutputTankCapacity = add(CATEGORY_VACUUM_CHAMBER, "vacuum_chamber_base_output_tank_capacity", 1000, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.BooleanValue propertyExoticContainmentExplosion = add(CATEGORY_VACUUM_CHAMBER, "exotic_containment_explosion", true);
    private static final ModConfigSpec.BooleanValue propertyExoticContainmentGammaFlash = add(CATEGORY_VACUUM_CHAMBER, "exotic_containment_gamma_flash", true);
    private static final ModConfigSpec.DoubleValue propertyExoticContainmentRadiation = add(CATEGORY_VACUUM_CHAMBER, "exotic_containment_radiation", 1024000.0, 0.0, Double.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue propertyExoticContainmentExplosionSize = add(CATEGORY_VACUUM_CHAMBER, "exotic_containment_explosion_size", 50.0, 0.0, 1000.0);

    private static final ModConfigSpec.BooleanValue propertyNucleosynthesisChamberExplosion = add(CATEGORY_VACUUM_CHAMBER, "nucleosynthesis_chamber_explosion", true);

    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyHeaterHeatRemoved = add(CATEGORY_VACUUM_CHAMBER, "heater_heat_removed", List.of(5000, 10000, 20000, 40000, 80000, 160000, 320000, 640000), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends String>> propertyHeaterRule = addString(CATEGORY_VACUUM_CHAMBER, "heater_rule", List.of("one casing", "one beam", "two glass", "exactly one quartz heater && exactly one redstone heater", "two axial obsidian heaters", "exactly one redstone heater && two iron heaters", "one obsidian heater && one quartz heater", "one nozzle"), true);

    private static final ModConfigSpec.IntValue propertyLiquefierBaseEnergyCapacity = add(CATEGORY_HEAT_EXCHANGER, "liquefier_base_energy_capacity", 1000, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyLiquefierInputTankCapacity = add(CATEGORY_HEAT_EXCHANGER, "liquefier_input_tank_capacity", 640, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue propertyLiquefierOutputTankCapacity = add(CATEGORY_HEAT_EXCHANGER, "liquefier_output_tank_capacity", 10, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyLiquefierCompressorEnergyEfficiency = add(CATEGORY_HEAT_EXCHANGER, "liquefier_energy_efficiency", List.of(0.9, 0.95, 0.85), 0.0, Double.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyLiquefierCompressorHeatEfficiency = add(CATEGORY_HEAT_EXCHANGER, "liquefier_heat_efficiency", List.of(0.9, 0.85, 0.95), 0.0, Double.MAX_VALUE, true);
    private static final ModConfigSpec.DoubleValue propertyLiquefierNozzleSpeed = add(CATEGORY_HEAT_EXCHANGER, "liquefier_nozzle_speed", 0.4D, 0.0, Double.MAX_VALUE);

    private static final ModConfigSpec.ConfigValue<List<? extends String>> propertyToolMiningLevel = addString(CATEGORY_TOOLS, "tool_mining_level", List.of("minecraft:incorrect_for_diamond_tool", "minecraft:incorrect_for_diamond_tool", "minecraft:incorrect_for_netherite_tool"), true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyToolDurability = add(CATEGORY_TOOLS, "tool_durability", List.of(1561 * 2), 1, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyToolSpeed = add(CATEGORY_TOOLS, "tool_speed", List.of(8D, 8D, 12D), 1D, 255D, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyToolAttackDamage = add(CATEGORY_TOOLS, "tool_attack_damage", List.of(3D), 0D, 255D, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyToolEnchantability = add(CATEGORY_TOOLS, "tool_enchantability", List.of(12), 1, 255, true);

    private static final ModConfigSpec.IntValue propertyDrillEnergyUsage = add(CATEGORY_TOOLS, "drill_energy_usage", 100, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyDrillEnergyCapacity = add(CATEGORY_TOOLS, "drill_energy_capacity", List.of(250000, 2500000), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyDrillRadius = add(CATEGORY_TOOLS, "drill_radius", List.of(1, 2), 0, 20, true);

    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyLeptonDamage = add(CATEGORY_TOOLS, "lepton_damage", List.of(7.0, 14.0, 28.0), 0, Float.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyLeptonRadiation = add(CATEGORY_TOOLS, "lepton_radiation", List.of(10.0, 20.0, 40.0), 0, Double.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyLeptonRange = add(CATEGORY_TOOLS, "lepton_range", List.of(30.0, 60.0, 90.0), 0, 128.0, true);
    private static final ModConfigSpec.IntValue propertyLeptonCoolDown = add(CATEGORY_TOOLS, "lepton_cool_down", 8, 0, 10000);
    private static final ModConfigSpec.IntValue propertyLeptonParticleUsage = add(CATEGORY_TOOLS, "lepton_particle_usage", 500, 0, 100000);

    private static final ModConfigSpec.DoubleValue propertyGluonDamage = add(CATEGORY_TOOLS, "gluon_damage", 10.0, 0, Float.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue propertyGluonRadiation = add(CATEGORY_TOOLS, "gluon_radiation", 10.0, 0, Double.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue propertyGluonRange = add(CATEGORY_TOOLS, "gluon_range", 40.0, 0, 128.0);
    private static final ModConfigSpec.IntValue propertyGluonParticleUsage = add(CATEGORY_TOOLS, "gluon_particle_usage", 10, 0, 100000);

    private static final ModConfigSpec.DoubleValue propertyAntimatterLauncherDamage = add(CATEGORY_TOOLS, "antimatter_launcher_damage", 20.0, 0, Float.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue propertyAntimatterLauncherRadiation = add(CATEGORY_TOOLS, "antimatter_launcher_radiation", 15360.0, 0, Double.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue propertyAntimatterLauncherExplosionSize = add(CATEGORY_TOOLS, "antimatter_launcher_explosion_size", 2.5, 0.0, 1000.0);
    private static final ModConfigSpec.IntValue propertyAntimatterLauncherCoolDown = add(CATEGORY_TOOLS, "antimatter_launcher_cool_down", 30, 0, 10000);
    private static final ModConfigSpec.IntValue propertyAntimatterLauncherParticleUsage = add(CATEGORY_TOOLS, "antimatter_launcher_usage", 5000, 0, 100000);

    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyHEVArmour = add(CATEGORY_TOOLS, "hev_armour", List.of(4, 7, 9, 4, 12, 1, 3, 4, 1, 3), 1, 25, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyHEVRadRes = add(CATEGORY_TOOLS, "hev_rad_res", List.of(20.0, 30.0, 20.0, 20.0), 0.0, 1000.0, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyHEVToughness = add(CATEGORY_TOOLS, "hev_toughness", List.of(4D, 0D), 0D, 8D, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyHEVEnergy = add(CATEGORY_TOOLS, "hev_energy", List.of(1000000, 1000000, 1000000, 1000000), 0, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyHEVPower = add(CATEGORY_TOOLS, "hev_power", List.of(100, 100, 250, 100, 1000), 0, Integer.MAX_VALUE, true); //damage,jump boost,long jump, fall reduction, posion/wither

    private static final ModConfigSpec.IntValue propertyKITime = add(CATEGORY_TOOLS, "ki_time", 400, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyFissionShieldHeatPerFlux = add(CATEGORY_FISSION, "shield_heat_per_flux", List.of(15D), 0D, 32767D, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyFissionShieldEfficiency = add(CATEGORY_FISSION, "shield_efficiency", List.of(1D), 0D, 255D, true);

    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyCoperniciumFuelTime = add(CATEGORY_FISSION, "copernicium_fuel_time", List.of(10000, 10000, 12004, 9001), 1, Integer.MAX_VALUE, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyCoperniciumHeatGeneration = add(CATEGORY_FISSION, "copernicium_heat_generation", List.of(2000, 2000, 1666, 2222), 0, 32767, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyCoperniciumEfficiency = add(CATEGORY_FISSION, "copernicium_efficiency", List.of(5.0D, 5.0D, 5.0D, 5.0D), 0D, 32767D, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyCoperniciumCriticality = add(CATEGORY_FISSION, "copernicium_criticality", List.of(20, 25, 35, 20), 0, 32767, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyCoperniciumIntrinsicFlux = add(CATEGORY_FISSION, "copernicium_intrinsic_flux", List.of(10, 0, 0, 0), 0, 32767, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyCoperniciumDecayFactor = add(CATEGORY_FISSION, "copernicium_decay_factor", List.of(0.11D, 0.11D, 0.11D, 0.11D), 0, 32767, true);
    private static final ModConfigSpec.ConfigValue<List<? extends Boolean>> propertyCoperniciumSelfPriming = add(CATEGORY_FISSION, "copernicium_self_priming", List.of(true, true, true, true), true);
    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyCoperniciumRadiation = add(CATEGORY_FISSION, "copernicium_radiation", List.of(QMDRadSources.MIX_291, QMDRadSources.MIX_291, QMDRadSources.MIX_291, QMDRadSources.MIX_291), 0D, 1000D, true);

    private static final ModConfigSpec.BooleanValue propertyOverrideNCRecipes = add(CATEGORY_RECIPES, "override_nc_recipes", true);

    private static final ModConfigSpec.IntValue propertyRSFTargetChamber = add(CATEGORY_RECIPES, "rsf_target_chamber", 100, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue propertyRSFNucleosynthesis = add(CATEGORY_RECIPES, "rsf_nucleosynthesis", 100, 1, Integer.MAX_VALUE);


    private static final ModConfigSpec.ConfigValue<List<? extends Integer>> propertyRTGPower = add(CATEGORY_OTHER, "rtg_power", List.of(50), 0, Integer.MAX_VALUE, true);

    private static final ModConfigSpec.IntValue propertyBeamScaling = add(CATEGORY_OTHER, "beam_scaling", 10000, 1, Integer.MAX_VALUE);

    //Property propertyItemTickerChunksPerTick =add(CATEGORY_OTHER, "item_ticker_chunks_per_tick", 5, 0,400);

    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyTurbineBladeEfficiency = add(CATEGORY_OTHER, "turbine_blade_efficiency", List.of(1.1D), 0.01D, 15D, true);

    private static final ModConfigSpec.ConfigValue<List<? extends Double>> propertyTurbineBladeExpansion = add(CATEGORY_OTHER, "turbine_blade_expansion", List.of(1.3D), 1D, 15D, true);

    public static void loadConfig() {
        processor_power = readIntegerArray(propertyProcessorPower);
        processor_time = readIntegerArray(propertyProcessorTime);
        irradiator_rad_res = propertyIrradiatorRadRes.getAsDouble();
        irradiator_fuel_usage = propertyIrradiatorFuelUsage.getAsInt();

        accelerator_linear_min_size = propertyAcceleratorLinearMinSize.getAsInt();
        accelerator_linear_max_size = propertyAcceleratorLinearMaxSize.getAsInt();
        accelerator_ring_min_size = propertyAcceleratorRingMinSize.getAsInt();
        accelerator_ring_max_size = propertyAcceleratorRingMaxSize.getAsInt();

        accelerator_base_heat_capacity = propertyAcceleratorBaseHeatCapacity.getAsInt();
        accelerator_base_energy_capacity = propertyAcceleratorBaseEnergyCapacity.getAsInt();
        accelerator_base_input_tank_capacity = propertyAcceleratorBaseInputTankCapacity.getAsInt();
        accelerator_base_output_tank_capacity = propertyAcceleratorBaseOutputTankCapacity.getAsInt();

        accelerator_thermal_conductivity = propertyAcceleratorThermalConductivity.getAsDouble();
        minimium_accelerator_ring_input_particle_energy = propertyAcceleratorRingInputEnergy.getAsInt();

        ion_source_power = readIntegerArray(propertyIonSourcePower);
        ion_source_output_multiplier = readIntegerArray(propertyIonSourceOutputMultiplier);
        ion_source_focus = readDoubleArray(propertyIonSourceFocus);

        beamAttenuationRate = propertyBeamAttenuationRate.getAsDouble();
        beamDiverterRadius = propertyBeamDiverterRadius.getAsInt();

        mass_spectrometer_valid_magnets = readStringArray(propertyMassSpectrometerValidMagnets);
        mass_spectrometer_valid_sources = readStringArray(propertyMassSpectrometerValidSources);

        RF_cavity_voltage = readIntegerArray(propertyRFCavityVoltage);
        RF_cavity_efficiency = readDoubleArray(propertyRFCavityEfficiency);
        RF_cavity_heat_generated = readIntegerArray(propertyRFCavityHeatGenerated);
        RF_cavity_base_power = readIntegerArray(propertyRFCavityBasePower);
        RF_cavity_max_temp = readIntegerArray(propertyRFCavityMaxTemp);

        magnet_strength = readDoubleArray(propertyMagnetStrength);
        magnet_efficiency = readDoubleArray(propertyMagnetEfficiency);
        magnet_heat_generated = readIntegerArray(propertyMagnetHeatGenerated);
        magnet_base_power = readIntegerArray(propertyMagnetBasePower);
        magnet_max_temp = readIntegerArray(propertyMagnetMaxTemp);

        cooler_heat_removed = readIntegerArray(propertyCoolerHeatRemoved);
        cooler_rule = readStringArray(propertyCoolerRule);

        accelerator_explosion = propertyAcceleratorExplosion.getAsBoolean();

        target_chamber_power = propertyTargetChamberPower.getAsInt();
        decay_chamber_power = propertyDecayChamberPower.getAsInt();
        beam_dump_power = propertyBeamDumpPower.getAsInt();
        collision_chamber_power = propertyCollisionChamberPower.getAsInt();

        detector_efficiency = readDoubleArray(propertyDetectorEfficiency);
        detector_base_power = readIntegerArray(propertyDetectorBasePower);

        particle_chamber_base_energy_capacity = propertyParticleChamberBaseEnergyCapacity.getAsInt();
        particle_chamber_input_tank_capacity = propertyParticleChamberInputTankCapacity.getAsInt();
        particle_chamber_output_tank_capacity = propertyParticleChamberOutputTankCapacity.getAsInt();

        vacuum_chamber_part_power = readIntegerArray(propertyContainmentPartPower);
        vacuum_chamber_part_heat = readIntegerArray(propertyContainmentPartHeat);
        vacuum_chamber_part_max_temp = readIntegerArray(propertyContainmentMaxTemp);

        vacuum_chamber_base_energy_capacity = propertyVacuumChamberBaseEnergyCapacity.getAsInt();
        vacuum_chamber_input_tank_capacity = propertyVacuumChamberInputTankCapacity.getAsInt();
        vacuum_chamber_output_tank_capacity = propertyVacuumChamberOutputTankCapacity.getAsInt();

        exotic_containment_explosion = propertyExoticContainmentExplosion.getAsBoolean();
        exotic_containment_gamma_flash = propertyExoticContainmentGammaFlash.getAsBoolean();
        exotic_containment_radiation = propertyExoticContainmentRadiation.getAsDouble();
        exotic_containment_explosion_size = propertyExoticContainmentExplosionSize.getAsDouble();
        nucleosynthesis_chamber_explosion = propertyNucleosynthesisChamberExplosion.getAsBoolean();

        heater_heat_removed = readIntegerArray(propertyHeaterHeatRemoved);
        heater_rule = readStringArray(propertyHeaterRule);

        liquefier_base_energy_capacity = propertyLiquefierBaseEnergyCapacity.getAsInt();
        liquefier_input_tank_capacity = propertyLiquefierInputTankCapacity.getAsInt();
        liquefier_output_tank_capacity = propertyLiquefierOutputTankCapacity.getAsInt();
        liquefier_compressor_energy_efficiency = readDoubleArray(propertyLiquefierCompressorEnergyEfficiency);
        liquefier_compressor_heat_efficiency = readDoubleArray(propertyLiquefierCompressorHeatEfficiency);
        liquefier_nozzle_speed = propertyLiquefierNozzleSpeed.getAsDouble();

        tool_mining_level = readStringArray(propertyToolMiningLevel);
        tool_durability = readIntegerArray(propertyToolDurability);
        tool_speed = readDoubleArray(propertyToolSpeed);
        tool_attack_damage = readDoubleArray(propertyToolAttackDamage);
        tool_enchantability = readIntegerArray(propertyToolEnchantability);

        drill_energy_usage = propertyDrillEnergyUsage.getAsInt();
        drill_energy_capacity = readIntegerArray(propertyDrillEnergyCapacity);
        drill_radius = readIntegerArray(propertyDrillRadius);

        lepton_damage = readDoubleArray(propertyLeptonDamage);
        lepton_radiation = readDoubleArray(propertyLeptonRadiation);
        lepton_range = readDoubleArray(propertyLeptonRange);
        lepton_cool_down = propertyLeptonCoolDown.getAsInt();
        lepton_particle_usage = propertyLeptonParticleUsage.getAsInt();

        gluon_damage = propertyGluonDamage.getAsDouble();
        gluon_radiation = propertyGluonRadiation.getAsDouble();
        gluon_range = propertyGluonRange.getAsDouble();
        gluon_particle_usage = propertyGluonParticleUsage.getAsInt();

        antimatter_launcher_damage = propertyAntimatterLauncherDamage.getAsDouble();
        antimatter_launcher_radiation = propertyAntimatterLauncherRadiation.getAsDouble();
        antimatter_launcher_explosion_size = propertyAntimatterLauncherExplosionSize.getAsDouble();
        antimatter_launcher_cool_down = propertyAntimatterLauncherCoolDown.getAsInt();
        antimatter_launcher_particle_usage = propertyAntimatterLauncherParticleUsage.getAsInt();

        hev_armour = readIntegerArray(propertyHEVArmour);
        hev_rad_res = readDoubleArray(propertyHEVRadRes);
        hev_toughness = readDoubleArray(propertyHEVToughness);
        hev_energy = readIntegerArray(propertyHEVEnergy);
        hev_power = readIntegerArray(propertyHEVPower);

        ki_time = propertyKITime.getAsInt();

        fission_shield_heat_per_flux = readDoubleArray(propertyFissionShieldHeatPerFlux);
        fission_shield_efficiency = readDoubleArray(propertyFissionShieldEfficiency);

        copernicium_fuel_time = readIntegerArray(propertyCoperniciumFuelTime);
        copernicium_heat_generation = readIntegerArray(propertyCoperniciumHeatGeneration);
        copernicium_efficiency = readDoubleArray(propertyCoperniciumEfficiency);
        copernicium_criticality = readIntegerArray(propertyCoperniciumCriticality);
        copernicium_intrinsic_flux = readIntegerArray(propertyCoperniciumIntrinsicFlux);
        copernicium_decay_factor = readDoubleArray(propertyCoperniciumDecayFactor);
        copernicium_self_priming = readBooleanArray(propertyCoperniciumSelfPriming);
        copernicium_radiation = readDoubleArray(propertyCoperniciumRadiation);

        override_nc_recipes = propertyOverrideNCRecipes.getAsBoolean();
        rsf_target_chamber = propertyRSFTargetChamber.getAsInt();
        rsf_nucleosynthesis = propertyRSFNucleosynthesis.getAsInt();

        rtg_power = readIntegerArray(propertyRTGPower);
        beam_scaling = propertyBeamScaling.getAsInt();
        //item_ticker_chunks_per_tick = propertyItemTickerChunksPerTick.getAsInt();
        turbine_blade_efficiency = readDoubleArray(propertyTurbineBladeEfficiency);
        turbine_blade_expansion = readDoubleArray(propertyTurbineBladeExpansion);
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static ModConfigSpec.ConfigValue<Integer> add(String category, String name, int defaultValue) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).define(List.of(category, name), defaultValue);
    }

    public static ModConfigSpec.IntValue add(String category, String name, int defaultValue, int minValue, int maxValue) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).defineInRange(List.of(category, name), defaultValue, minValue, maxValue);
    }

    public static ModConfigSpec.BooleanValue add(String category, String name, boolean defaultValue) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).define(List.of(category, name), defaultValue);
    }

    public static ModConfigSpec.ConfigValue<Double> add(String category, String name, double defaultValue) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).define(List.of(category, name), defaultValue);
    }

    public static ModConfigSpec.DoubleValue add(String category, String name, double defaultValue, double minValue, double maxValue) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).defineInRange(List.of(category, name), defaultValue, minValue, maxValue);
    }

    public static ModConfigSpec.ConfigValue<String> add(String category, String name, String defaultValue) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).define(List.of(category, name), defaultValue);
    }

    public static ModConfigSpec.ConfigValue<List<? extends Integer>> add(String category, String name, List<Integer> defaultValue) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).defineList(List.of(category, name), defaultValue, () -> 0, e -> e instanceof Integer);
    }

    public static ModConfigSpec.ConfigValue<List<? extends Integer>> add(String category, String name, List<Integer> defaultValue, int minValue, int maxValue, boolean fixedArray) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).defineList(List.of(category, name), defaultValue, fixedArray ? null : () -> 0, e -> range(e, minValue, maxValue));
    }

    public static ModConfigSpec.ConfigValue<List<? extends Boolean>> add(String category, String name, List<Boolean> defaultValue, boolean fixedArray) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).defineList(List.of(category, name), defaultValue, fixedArray ? null : () -> true, e -> e instanceof Boolean);
    }

    public static ModConfigSpec.ConfigValue<List<? extends String>> addString(String category, String name, List<String> defaultValue, boolean fixedArray) {
        return addString(category, name, defaultValue, e -> e instanceof String, fixedArray);
    }

    public static ModConfigSpec.ConfigValue<List<? extends String>> addString(String category, String name, List<String> defaultValue, Predicate<Object> validator, boolean fixedArray) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).defineList(List.of(category, name), defaultValue, fixedArray ? null : () -> "", validator);
    }

    public static ModConfigSpec.ConfigValue<List<? extends Double>> add(String category, String name, List<Double> defaultValue, double minValue, double maxValue, boolean fixedArray) {
        return BUILDER.translation("gui.qmd.config." + category + "." + name).defineList(List.of(category, name), defaultValue, fixedArray ? null : () -> 0.0, e -> range(e, minValue, maxValue));
    }

    private static boolean range(Object element, int min, int max) {
        return element instanceof Integer integer && integer >= min && integer <= max;
    }

    private static boolean range(Object element, double min, double max) {
        return element instanceof Double num && num >= min && num <= max;
    }

    public static int[] readIntegerArray(ModConfigSpec.ConfigValue<List<? extends Integer>> property) {
        int[] currentList = property.get().stream().mapToInt(Integer::intValue).toArray();
        int currentLength = currentList.length;
        int defaultLength = property.getDefault().size();

        if (currentLength == defaultLength) {
            return currentList;
        }
        int[] newList = new int[defaultLength];
        if (currentLength > defaultLength) {
            System.arraycopy(currentList, 0, newList, 0, defaultLength);
        } else {
            System.arraycopy(currentList, 0, newList, 0, currentLength);
            property.set(property.getDefault());
            int[] defaultList = property.get().stream().mapToInt(Integer::intValue).toArray();
            System.arraycopy(defaultList, currentLength, newList, currentLength, defaultLength - currentLength);
        }
        return newList;
    }

    public static boolean[] readBooleanArray(ModConfigSpec.ConfigValue<List<? extends Boolean>> property) {
        boolean[] currentList = Booleans.toArray(property.get().stream().map(Boolean::booleanValue).toList());
        int currentLength = currentList.length;
        int defaultLength = property.getDefault().size();

        if (currentLength == defaultLength) {
            return currentList;
        }
        boolean[] newList = new boolean[defaultLength];
        if (currentLength > defaultLength) {
            System.arraycopy(currentList, 0, newList, 0, defaultLength);
        } else {
            System.arraycopy(currentList, 0, newList, 0, currentLength);
            property.set(property.getDefault());
            boolean[] defaultList = Booleans.toArray(property.get().stream().map(Boolean::booleanValue).toList());
            System.arraycopy(defaultList, currentLength, newList, currentLength, defaultLength - currentLength);
        }
        return newList;
    }

    public static double[] readDoubleArray(ModConfigSpec.ConfigValue<List<? extends Double>> property) {
        double[] currentList = property.get().stream().mapToDouble(Double::doubleValue).toArray();
        int currentLength = currentList.length;
        int defaultLength = property.getDefault().size();

        if (currentLength == defaultLength) {
            return currentList;
        }
        double[] newList = new double[defaultLength];
        if (currentLength > defaultLength) {
            System.arraycopy(currentList, 0, newList, 0, defaultLength);
        } else {
            System.arraycopy(currentList, 0, newList, 0, currentLength);
            property.set(property.getDefault());
            double[] defaultList = property.get().stream().mapToDouble(Double::doubleValue).toArray();
            System.arraycopy(defaultList, currentLength, newList, currentLength, defaultLength - currentLength);
        }
        return newList;
    }

    public static String[] readStringArray(ModConfigSpec.ConfigValue<List<? extends String>> property) {
        String[] currentList = property.get().toArray(String[]::new);
        int currentLength = currentList.length;
        int defaultLength = property.getDefault().size();

        if (currentLength == defaultLength) {
            return currentList;
        }
        String[] newList = new String[defaultLength];
        if (currentLength > defaultLength) {
            System.arraycopy(currentList, 0, newList, 0, defaultLength);
        } else {
            System.arraycopy(currentList, 0, newList, 0, currentLength);
            property.set(property.getDefault());
            String[] defaultList = property.get().toArray(String[]::new);
            System.arraycopy(defaultList, currentLength, newList, currentLength, defaultLength - currentLength);
        }
        return newList;
    }
}