package lach_01298.qmd.enums;

import lach_01298.qmd.QMDConstants;
import net.minecraft.util.StringRepresentable;

import static lach_01298.qmd.config.QMDStartupConfig.*;


public class MaterialTypes {
    public enum DustType implements StringRepresentable {
        TUNGSTEN("tungsten"),
        NIOBIUM("niobium"),
        CHROMIUM("chromium"),
        TITANIUM("titanium"),
        COBALT("cobalt"),
        NICKEL("nickel"),
        HAFNIUM("hafnium"),
        ZINC("zinc"),
        OSMIUM("osmium"),
        IRIDIUM("iridium"),
        PLATNIUM("platinum"),
        SODIUM("sodium"),
        POTASSIUM("potassium"),
        CALCIUM("calcium"),
        STRONTIUM("strontium"),
        BARIUM("barium"),
        YTTRIUM("yttrium"),
        NEODYMIUM("neodymium"),
        IODINE("iodine"),
        SAMARIUM("samarium"),
        TERBIUM("terbium"),
        ERBIUM("erbium"),
        YTTERBIUM("ytterbium");


        private final String name;

        DustType(String name) {
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

    public enum IngotType implements StringRepresentable {
        TUNGSTEN("tungsten"),
        NIOBIUM("niobium"),
        CHROMIUM("chromium"),
        TITANIUM("titanium"),
        COBALT("cobalt"),
        NICKEL("nickel"),
        HAFNIUM("hafnium"),
        ZINC("zinc"),
        OSMIUM("osmium"),
        IRIDIUM("iridium"),
        PLATNIUM("platinum"),
        SODIUM("sodium"),
        POTASSIUM("potassium"),
        CALCIUM("calcium"),
        STRONTIUM("strontium"),
        BARIUM("barium"),
        YTTRIUM("yttrium"),
        NEODYMIUM("neodymium"),
        MERCURY("mercury");

        private final String name;

        IngotType(String name) {
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

        public DustType getDust() {
            if (this == MERCURY) return null;
            return DustType.values()[ordinal()];
        }
    }

    public enum IngotAlloyType implements StringRepresentable {
        TUNGSTEN_CARBIDE("tungsten_carbide"),
        NIOBIUM_TIN("niobium_tin"),
        STAINLESS_STEEL("stainless_steel"),
        NIOBIUM_TITANIUM("niobium_titanium"),
        OSMIRIDIUM("osmiridium"),
        NICHROME("nichrome"),
        SUPER_ALLOY("super_alloy");

        private final String name;

        IngotAlloyType(String name) {
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


    public enum IsotopeType implements StringRepresentable {
        SODIUM_22("sodium_22"),
        BERYLLIUM_7("beryllium_7"),
        MAGNESIUM_24("magnesium_24"),
        MAGNESIUM_26("magnesium_26"),
        Uranium_234("uranium_234"),
        PROTACTINIUM_231("protactinium_231"),
        COBALT_60("cobalt_60"),
        IRIDIUM_192("iridium_192"),
        CALCIUM_48("calcium_48");

        private final String name;

        IsotopeType(String name) {
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


    public enum PartType implements StringRepresentable {
        EMPTY_COOLER("empty_cooler"),
        DETECTOR_CASING("detector_casing"),
        SCINTILLATOR_PWO("scintillator_pwo"),
        SCINTILLATOR_PLASTIC("scintillator_plastic"),
        WIRE_BSCCO("wire_bscco"),
        ROD_ND_YAG("rod_nd_yag"),
        WIRE_GOLD_TUNGSTEN("wire_gold_tungsten"),
        WIRE_CHAMBER_CASING("wire_chamber_casing"),
        MAGNET_ND("magnet_nd"),
        ACCELERATING_BARREL("accelerating_barrel"),
        LASER_ASSEMBLY("laser_assembly"),
        WIRE_SSFAF("wire_ssfaf"),
        WIRE_YBCO("wire_ybco"),
        MAGNET_SMC("magnet_smc");

        private final String name;

        PartType(String name) {
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

    public enum SemiconductorType implements StringRepresentable {
        SILICON_P_DOPED("silicon_p_doped"),
        SILICON_N_DOPED("silicon_n_doped"),
        SILICON_BOULE("silicon_boule"),
        SILICON_WAFER("silicon_wafer"),
        BASIC_PROCESSOR("basic_processor"),
        ADVANCED_PROCESSOR("advanced_processor"),
        ELITE_PROCESSOR("elite_processor");

        private final String name;

        SemiconductorType(String name) {
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

    public enum ChemicalDustType implements StringRepresentable {
        TUNGSTEN_OXIDE("tungsten_oxide"),
        BSCCO("bscco"),
        SODIUM_NITRATE("sodium_nitrate"),
        SODIUM_CHLORIDE("sodium_chloride"),
        COPPER_OXIDE("copper_oxide"),
        HAFNIUM_OXIDE("hafnium_oxide"),
        STRONTIUM_CHLORIDE("strontium_chloride"),
        ZINC_SULFIDE("zinc_sulfide"),
        IRON_FLUORIDE("iron_fluoride"),
        SSFAF("ssfaf"),
        YBCO("ybco");

        private final String name;

        ChemicalDustType(String name) {
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

    public enum LuminousPaintType implements StringRepresentable {
        GREEN("green"),
        BLUE("blue"),
        ORANGE("orange");

        private final String name;

        LuminousPaintType(String name) {
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

    public enum FissionWasteType implements StringRepresentable {
        LIGHT("light"),
        HEAVY("heavy");

        private final String name;

        FissionWasteType(String name) {
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

    public enum SpallationWasteType implements StringRepresentable {
        CALIFORNIUM("californium"),
        BERKELIUM("berkelium"),
        CURIUM("curium"),
        AMERICIUM("americium"),
        PLUTONIUM("plutonium"),
        NEPTUNIUM("neptunium"),
        URANIUM("uranium"),
        PROTACTINIUM("protactinium"),
        THORIUM("thorium"),
        RADIUM("radium"),
        POLONIUM("polonium"),
        BISMUTH("bismuth"),
        LEAD("lead"),
        GOLD("gold"),
        PLATINUM("platinum"),
        IRIDIUM("iridium"),
        OSMIUM("osmium"),
        TUNGSTEN("tungsten"),
        HAFNIUM("hafnium"),
        MERCURY("mercury");

        private final String name;

        SpallationWasteType(String name) {
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

    public enum CellType implements IItemCapacity {
        EMPTY("empty", 0, 1),
        ANTIHYDROGEN("antihydrogen", QMDConstants.moleAmount / 10, 1),
        ANTIDEUTERIUM("antideuterium", QMDConstants.moleAmount / 10, 2),
        ANTITRITIUM("antitritium", QMDConstants.moleAmount / 10, 3),
        ANTIHELIUM3("antihelium3", QMDConstants.moleAmount / 10, 3),
        ANTIHELIUM("antihelium", QMDConstants.moleAmount / 10, 4),
        POSITRONIUM("positronium", QMDConstants.moleAmount / 10, 0.00054),
        MUONIUM("muonium", QMDConstants.moleAmount / 10, 0.11),
        TAUONIUM("tauonium", QMDConstants.moleAmount / 10, 1.9),
        GLUEBALLS("glueballs", QMDConstants.moleAmount / 10, 1.8);

        private final String name;
        private final int capacity;
        private final double explosionSize;

        CellType(String name, int capacity, double explosion_size) {
            this.name = name;
            this.capacity = capacity;
            explosionSize = explosion_size;
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
        public int getCapacity() {
            return capacity;
        }

        public double getExplosionSize() {
            return explosionSize;
        }
    }

    public enum SourceType implements IItemCapacity {
        TUNGSTEN_FILAMENT("tungsten_filament", 50 * QMDConstants.moleAmount),
        SODIUM_22("sodium_22", 5 * QMDConstants.moleAmount),
        COBALT_60("cobalt_60", 1 * QMDConstants.moleAmount),
        IRIDIUM_192("iridium_192", 1 * QMDConstants.moleAmount),
        CALCIUM_48("calcium_48", 5 * QMDConstants.moleAmount);

        private final String name;
        private final int capacity;

        SourceType(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
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
        public int getCapacity() {
            return capacity;
        }
    }

    public enum CoperniciumType implements StringRepresentable {
        _291("291"),
        _291_C("291_c"),
        _291_OX("291_ox"),
        _291_NI("291_ni"),
        _291_ZA("291_za");


        private final String name;

        CoperniciumType(String name) {
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

    public enum CoperniciumPelletType implements StringRepresentable {
        MIX_291("mix_291"),
        MIX_291_C("mix_291_c");

        private final String name;

        CoperniciumPelletType(String name) {
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

    public enum CoperniciumFuelType implements StringRepresentable {
        MIX_291_TR("mix_291_tr", 0),
        MIX_291_OX("mix_291_ox", 1),
        MIX_291_NI("mix_291_ni", 2),
        MIX_291_ZA("mix_291_za", 3);

        private final String name;
//        private final int fuelTime, heatGen, criticality;
//        private final double efficiency;
//        private final boolean selfPriming;

        CoperniciumFuelType(String name, int id) {
            this.name = name;
//            fuelTime = copernicium_fuel_time[id + id / 4]; TODO
//            heatGen = copernicium_heat_generation[id + id / 4];
//            efficiency = copernicium_efficiency[id + id / 4];
//            criticality = copernicium_criticality[id + id / 4];
//            selfPriming = copernicium_self_priming[id + id / 4];
        }

        @Override
        public String getSerializedName() {
            return name;
        }

//        @Override
//        public String toString() {
//            return getSerializedName();
//        }
//
//        public int getBaseTime() { // TODO cleanup
//            return fuelTime;
//        }
//
//        public int getBaseHeat() {
//            return heatGen;
//        }
//
//        public double getBaseEfficiency() {
//            return efficiency;
//        }
//
//        public int getCriticality() {
//            return criticality;
//        }
//
//        public boolean getSelfPriming() {
//            return selfPriming;
//        }
    }

    public enum CoperniciumDepletedFuelType implements StringRepresentable {
        MIX_291_TR("mix_291_tr"),
        MIX_291_OX("mix_291_ox"),
        MIX_291_NI("mix_291_ni"),
        MIX_291_ZA("mix_291_za");

        private final String name;

        CoperniciumDepletedFuelType(String name) {
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
}