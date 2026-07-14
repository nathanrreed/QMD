package lach_01298.qmd.enums;

import com.nred.nuclearcraft.multiblock.PlacementRule;
import com.nred.nuclearcraft.multiblock.fisson.FissionNeutronShieldType;
import com.nred.nuclearcraft.multiblock.rtg.RTGType;
import com.nred.nuclearcraft.multiblock.turbine.TurbineRotorBladeType;
import com.nred.nuclearcraft.radiation.RadSources;
import it.zerono.mods.zerocore.lib.multiblock.variant.IMultiblockVariant;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.CoolerPlacement;
import lach_01298.qmd.accelerator.tile.TileAcceleratorPart;
import lach_01298.qmd.config.QMDStartupConfig;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockTypes {
    public static final RTGType STRONTIUM_RTG = new RTGType("qmd:strontium", () -> QMDStartupConfig.rtg_power[0], RadSources.STRONTIUM_90 / 8D);
    public static final TurbineRotorBladeType SUPER_ALLOY = new TurbineRotorBladeType("qmd:super_alloy", () -> QMDStartupConfig.turbine_blade_efficiency[0], () -> QMDStartupConfig.turbine_blade_expansion[0]);
    public static final FissionNeutronShieldType HAFNIUM = new FissionNeutronShieldType("qmd:hafnium", () -> QMDStartupConfig.fission_shield_heat_per_flux[0], () -> QMDStartupConfig.fission_shield_efficiency[0]);

    public enum CoolerType implements StringRepresentable, IMultiblockVariant, ICoolerEnum {
        WATER("water", QMDStartupConfig.cooler_heat_removed[0]),
        IRON("iron", QMDStartupConfig.cooler_heat_removed[1]),
        REDSTONE("redstone", QMDStartupConfig.cooler_heat_removed[2]),
        QUARTZ("quartz", QMDStartupConfig.cooler_heat_removed[3]),
        OBSIDIAN("obsidian", QMDStartupConfig.cooler_heat_removed[4]),
        NETHER_BRICK("nether_brick", QMDStartupConfig.cooler_heat_removed[5]),
        GLOWSTONE("glowstone", QMDStartupConfig.cooler_heat_removed[6]),
        LAPIS("lapis", QMDStartupConfig.cooler_heat_removed[7]),
        GOLD("gold", QMDStartupConfig.cooler_heat_removed[8]),
        PRISMARINE("prismarine", QMDStartupConfig.cooler_heat_removed[9]),
        SLIME("slime", QMDStartupConfig.cooler_heat_removed[10]),
        END_STONE("end_stone", QMDStartupConfig.cooler_heat_removed[11]),
        PURPUR("purpur", QMDStartupConfig.cooler_heat_removed[12]),
        DIAMOND("diamond", QMDStartupConfig.cooler_heat_removed[13]),
        EMERALD("emerald", QMDStartupConfig.cooler_heat_removed[14]),
        COPPER("copper", QMDStartupConfig.cooler_heat_removed[15]),
        TIN("tin", QMDStartupConfig.cooler_heat_removed[16]),
        LEAD("lead", QMDStartupConfig.cooler_heat_removed[17]),
        BORON("boron", QMDStartupConfig.cooler_heat_removed[18]),
        LITHIUM("lithium", QMDStartupConfig.cooler_heat_removed[19]),
        MAGNESIUM("magnesium", QMDStartupConfig.cooler_heat_removed[20]),
        MANGANESE("manganese", QMDStartupConfig.cooler_heat_removed[21]),
        ALUMINUM("aluminum", QMDStartupConfig.cooler_heat_removed[22]),
        SILVER("silver", QMDStartupConfig.cooler_heat_removed[23]),
        FLUORITE("fluorite", QMDStartupConfig.cooler_heat_removed[24]),
        VILLIAUMITE("villiaumite", QMDStartupConfig.cooler_heat_removed[25]),
        CAROBBIITE("carobbiite", QMDStartupConfig.cooler_heat_removed[26]),
        ARSENIC("arsenic", QMDStartupConfig.cooler_heat_removed[27]),
        LIQUID_NITROGEN("liquid_nitrogen", QMDStartupConfig.cooler_heat_removed[28]),
        LIQUID_HELIUM("liquid_helium", QMDStartupConfig.cooler_heat_removed[29]),
        ENDERIUM("enderium", QMDStartupConfig.cooler_heat_removed[30]),
        CRYOTHEUM("cryotheum", QMDStartupConfig.cooler_heat_removed[31]);

        private String name;
        private int heat;

        CoolerType(String name, int heat) {
            this.name = name;
            this.heat = heat;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return getSerializedName();
        }

        @Override
        public int getHeatRemoved() {
            return heat;
        }

        @Override
        public int getId() {
            return ordinal();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getTranslationKey() {
            return "";
        }

        public PlacementRule<Accelerator, TileAcceleratorPart> getRule() {
            return CoolerPlacement.RULE_MAP.get(this.name + "_cooler");
        }

        public String getTooltipRule() {
            return CoolerPlacement.TOOLTIP_MAP.get(this.name + "_cooler");
        }

        @Override
        public BlockBehaviour.Properties getBlockProperties() {
            if (this == GLOWSTONE) {
                return this.getDefaultBlockProperties().lightLevel(e -> 15);
            }
            return this.getDefaultBlockProperties();
        }
    }

    public enum RFCavityType implements StringRepresentable, IMultiblockVariant {
        COPPER("copper", QMDStartupConfig.RF_cavity_voltage[0], QMDStartupConfig.RF_cavity_efficiency[0], QMDStartupConfig.RF_cavity_heat_generated[0], QMDStartupConfig.RF_cavity_base_power[0], QMDStartupConfig.RF_cavity_max_temp[0]),
        MAGNESIUM_DIBORIDE("magnesium_diboride", QMDStartupConfig.RF_cavity_voltage[1], QMDStartupConfig.RF_cavity_efficiency[1], QMDStartupConfig.RF_cavity_heat_generated[1], QMDStartupConfig.RF_cavity_base_power[1], QMDStartupConfig.RF_cavity_max_temp[1]),
        NIOBIUM_TIN("niobium_tin", QMDStartupConfig.RF_cavity_voltage[2], QMDStartupConfig.RF_cavity_efficiency[2], QMDStartupConfig.RF_cavity_heat_generated[2], QMDStartupConfig.RF_cavity_base_power[2], QMDStartupConfig.RF_cavity_max_temp[2]),
        NIOBIUM_TITANIUM("niobium_titanium", QMDStartupConfig.RF_cavity_voltage[3], QMDStartupConfig.RF_cavity_efficiency[3], QMDStartupConfig.RF_cavity_heat_generated[3], QMDStartupConfig.RF_cavity_base_power[3], QMDStartupConfig.RF_cavity_max_temp[3]),
        BSCCO("bscco", QMDStartupConfig.RF_cavity_voltage[4], QMDStartupConfig.RF_cavity_efficiency[4], QMDStartupConfig.RF_cavity_heat_generated[4], QMDStartupConfig.RF_cavity_base_power[4], QMDStartupConfig.RF_cavity_max_temp[4]),
        Aluminium("aluminium", QMDStartupConfig.RF_cavity_voltage[5], QMDStartupConfig.RF_cavity_efficiency[5], QMDStartupConfig.RF_cavity_heat_generated[5], QMDStartupConfig.RF_cavity_base_power[5], QMDStartupConfig.RF_cavity_max_temp[5]),
        SSFAF("ssfaf", QMDStartupConfig.RF_cavity_voltage[6], QMDStartupConfig.RF_cavity_efficiency[6], QMDStartupConfig.RF_cavity_heat_generated[6], QMDStartupConfig.RF_cavity_base_power[6], QMDStartupConfig.RF_cavity_max_temp[6]),
        YBCO("ybco", QMDStartupConfig.RF_cavity_voltage[7], QMDStartupConfig.RF_cavity_efficiency[7], QMDStartupConfig.RF_cavity_heat_generated[7], QMDStartupConfig.RF_cavity_base_power[7], QMDStartupConfig.RF_cavity_max_temp[7]);

        private String name;
        private int voltage;
        private double efficiency;
        private int heat;
        private int basePower;
        private int maxTemp;

        RFCavityType(String name, int voltage, double efficiency, int heat, int basePower, int maxTemp) {
            this.name = name;
            this.voltage = voltage;
            this.efficiency = efficiency;
            this.heat = heat;
            this.basePower = basePower;
            this.maxTemp = maxTemp;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return getSerializedName();
        }

        public int getVoltage() {
            return voltage;
        }

        public double getEfficiency() {
            return efficiency;
        }

        public int getHeatGenerated() {
            return heat;
        }

        public int getBasePower() {
            return basePower;
        }

        public int getMaxOperatingTemp() {
            return maxTemp;
        }

        @Override
        public int getId() {
            return ordinal();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getTranslationKey() {
            return "";
        }

        @Override
        public BlockBehaviour.Properties getBlockProperties() {
            return getDefaultBlockProperties();
        }
    }

    public enum MagnetType implements StringRepresentable, IMultiblockVariant {
        COPPER("copper", QMDStartupConfig.magnet_strength[0], QMDStartupConfig.magnet_efficiency[0], QMDStartupConfig.magnet_heat_generated[0], QMDStartupConfig.magnet_base_power[0], QMDStartupConfig.magnet_max_temp[0]),
        MAGNESIUM_DIBORIDE("magnesium_diboride", QMDStartupConfig.magnet_strength[1], QMDStartupConfig.magnet_efficiency[1], QMDStartupConfig.magnet_heat_generated[1], QMDStartupConfig.magnet_base_power[1], QMDStartupConfig.magnet_max_temp[1]),
        NIOBIUM_TIN("niobium_tin", QMDStartupConfig.magnet_strength[2], QMDStartupConfig.magnet_efficiency[2], QMDStartupConfig.magnet_heat_generated[2], QMDStartupConfig.magnet_base_power[2], QMDStartupConfig.magnet_max_temp[2]),
        NIOBIUM_TITANIUM("niobium_titanium", QMDStartupConfig.magnet_strength[3], QMDStartupConfig.magnet_efficiency[3], QMDStartupConfig.magnet_heat_generated[3], QMDStartupConfig.magnet_base_power[3], QMDStartupConfig.magnet_max_temp[3]),
        BSCCO("bscco", QMDStartupConfig.magnet_strength[4], QMDStartupConfig.magnet_efficiency[4], QMDStartupConfig.magnet_heat_generated[4], QMDStartupConfig.magnet_base_power[4], QMDStartupConfig.magnet_max_temp[4]),
        Aluminium("aluminium", QMDStartupConfig.magnet_strength[5], QMDStartupConfig.magnet_efficiency[5], QMDStartupConfig.magnet_heat_generated[5], QMDStartupConfig.magnet_base_power[5], QMDStartupConfig.magnet_max_temp[5]),
        SSFAF("ssfaf", QMDStartupConfig.magnet_strength[6], QMDStartupConfig.magnet_efficiency[6], QMDStartupConfig.magnet_heat_generated[6], QMDStartupConfig.magnet_base_power[6], QMDStartupConfig.magnet_max_temp[6]),
        YBCO("ybco", QMDStartupConfig.magnet_strength[7], QMDStartupConfig.magnet_efficiency[7], QMDStartupConfig.magnet_heat_generated[7], QMDStartupConfig.magnet_base_power[7], QMDStartupConfig.magnet_max_temp[7]);

        private String name;
        private double strength;
        private double efficiency;
        private int heat;
        private int basePower;
        private int maxTemp;

        MagnetType(String name, double strength, double efficiency, int heat, int basePower, int maxTemp) {
            this.name = name;
            this.strength = strength;
            this.efficiency = efficiency;
            this.heat = heat;
            this.basePower = basePower;
            this.maxTemp = maxTemp;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return getSerializedName();
        }

        public double getStrength() {
            return strength;
        }

        public double getEfficiency() {
            return efficiency;
        }

        public int getHeatGenerated() {
            return heat;
        }

        public int getBasePower() {
            return basePower;
        }

        public int getMaxOperatingTemp() {
            return maxTemp;
        }

        @Override
        public int getId() {
            return ordinal();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getTranslationKey() {
            return "";
        }

        @Override
        public BlockBehaviour.Properties getBlockProperties() {
            return getDefaultBlockProperties();
        }
    }

    public enum DetectorType implements StringRepresentable {
        BUBBLE_CHAMBER("bubble_chamber", QMDStartupConfig.detector_efficiency[0], QMDStartupConfig.detector_base_power[0]),
        SILLICON_TRACKER("silicon_tracker", QMDStartupConfig.detector_efficiency[1], QMDStartupConfig.detector_base_power[1]),
        WIRE_CHAMBER("wire_chamber", QMDStartupConfig.detector_efficiency[2], QMDStartupConfig.detector_base_power[2]),
        EM_CALORIMETER("em_calorimeter", QMDStartupConfig.detector_efficiency[3], QMDStartupConfig.detector_base_power[3]),
        HADRON_CALORIMETER("hadron_calorimeter", QMDStartupConfig.detector_efficiency[4], QMDStartupConfig.detector_base_power[4]);

        private String name;
        private double efficiency;
        private int basePower;

        DetectorType(String name, double efficiency, int basePower) {
            this.name = name;
            this.efficiency = efficiency;
            this.basePower = basePower;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return getSerializedName();
        }

        public double getEfficiency() {
            return efficiency;
        }

        public int getBasePower() {
            return basePower;
        }

//        @Override TODO
//        public int getHarvestLevel() {
//            return 0;
//        }
//
//        @Override
//        public String getHarvestTool() {
//            return "pickaxe";
//        }
//
//        @Override
//        public float getHardness() {
//            return 2;
//        }
//
//        @Override
//        public float getResistance() {
//            return 10;
//        }
//
//        @Override
//        public int getLightValue() {
//            return 0;
//        }
    }

    public enum LampType implements StringRepresentable {
        EMPTY("empty"),
        HYDROGEN("hydrogen"),
        HELIUM("helium"),
        NITROGEN("nitrogen"),
        OXYGEN("oxygen"),
        NEON("neon"),
        ARGON("argon"),
        SODIUM("sodium"),
        MERCURY("mercury");

        private final String name;

        LampType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return getSerializedName();
        }
    }

    public enum HeaterType implements StringRepresentable, ICoolerEnum {
        IRON("iron", QMDStartupConfig.heater_heat_removed[0]),
        REDSTONE("redstone", QMDStartupConfig.heater_heat_removed[1]),
        QUARTZ("quartz", QMDStartupConfig.heater_heat_removed[2]),
        OBSIDIAN("obsidian", QMDStartupConfig.heater_heat_removed[3]),
        GLOWSTONE("glowstone", QMDStartupConfig.heater_heat_removed[4]),
        LAPIS("lapis", QMDStartupConfig.heater_heat_removed[5]),
        GOLD("gold", QMDStartupConfig.heater_heat_removed[6]),
        DIAMOND("diamond", QMDStartupConfig.heater_heat_removed[7]);

        private String name;
        private int heat;

        HeaterType(String name, int heat) {
            this.name = name;
            this.heat = heat;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return getSerializedName();
        }

        @Override
        public int getHeatRemoved() {
            return heat;
        }

//        @Override TODO
//        public int getHarvestLevel() {
//            return 0;
//        }
//
//        @Override
//        public String getHarvestTool() {
//            return "pickaxe";
//        }
//
//        @Override
//        public float getHardness() {
//            return 2;
//        }
//
//        @Override
//        public float getResistance() {
//            return 10;
//        }
//
//        @Override
//        public int getLightValue() {
//            if (this == GLOWSTONE) {
//                return 15;
//            }
//            return 0;
//        }
    }

    public enum CompressorType implements StringRepresentable {
        COPPER("copper", QMDStartupConfig.liquefier_compressor_energy_efficiency[0], QMDStartupConfig.liquefier_compressor_heat_efficiency[0]),
        NEODYMIUM("neodymium", QMDStartupConfig.liquefier_compressor_energy_efficiency[1], QMDStartupConfig.liquefier_compressor_heat_efficiency[1]),
        SAMARIUM_COBALT("samarium_cobalt", QMDStartupConfig.liquefier_compressor_energy_efficiency[2], QMDStartupConfig.liquefier_compressor_heat_efficiency[2]);

        private String name;
        private double energyEfficiency;
        private double heatEfficiency;

        CompressorType(String name, double energyEfficiency, double heatEfficiency) {
            this.name = name;
            this.energyEfficiency = energyEfficiency;
            this.heatEfficiency = heatEfficiency;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return getSerializedName();
        }

        public double getEnergyEfficiency() {
            return energyEfficiency;
        }

        public double getHeatEfficiency() {
            return heatEfficiency;
        }

//        @Override TODO
//        public int getHarvestLevel() {
//            return 0;
//        }
//
//        @Override
//        public String getHarvestTool() {
//            return "pickaxe";
//        }
//
//        @Override
//        public float getHardness() {
//            return 2;
//        }
//
//        @Override
//        public float getResistance() {
//            return 10;
//        }
//
//        @Override
//        public int getLightValue() {
//            return 0;
//        }
    }
}