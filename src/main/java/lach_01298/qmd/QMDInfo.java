package lach_01298.qmd;

import com.nred.nuclearcraft.NuclearcraftNeohaul;
import com.nred.nuclearcraft.util.UnitHelper;
import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.enums.ICoolerEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringRepresentable;

public class QMDInfo {
//    // RF Cavity info TODO
//    public static Component[][] RFCavityFixedInfo() {
//        RFCavityType[] values = RFCavityType.values();
//        Component[][] info = new Component[values.length][];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = new Component[]{
//                    Component.translatable("info." + QMD.MOD_ID + ".rf_cavity.voltage", values[i].getVoltage()),
//                    Component.translatable("info." + QMD.MOD_ID + ".item.efficiency", Math.round(100D * values[i].getEfficiency()) + "%"),
//                    Component.translatable("info." + QMD.MOD_ID + ".item.heat", values[i].getHeatGenerated()),
//                    Component.translatable("info." + QMD.MOD_ID + ".item.power", values[i].getBasePower()),
//                    Component.translatable("info." + QMD.MOD_ID + ".item.max_temp", values[i].getMaxOperatingTemp())
//            };
//        }
//        return info;
//    }
//
//    public static Component[] RFCavityInfo() {
//        RFCavityType[] values = RFCavityType.values();
//        Component[] info = new Component[values.length];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = Component.translatable("tile." + QMD.MOD_ID + ".rf_cavity.desc");
//        }
//        return info;
//    }
//
//    // Magnet info
//    public static Component[][] magnetFixedInfo() {
//        MagnetType[] values = MagnetType.values();
//        Component[][] info = new Component[values.length][];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = new Component[]{
//                    Component.translatable("info." + QMD.MOD_ID + ".accelerator_magnet.strength", values[i].getStrength()),
//                    Component.translatable("info." + QMD.MOD_ID + ".item.efficiency", Math.round(100D * values[i].getEfficiency()) + "%"),
//                    Component.translatable("info." + QMD.MOD_ID + ".item.heat", values[i].getHeatGenerated()),
//                    Component.translatable("info." + QMD.MOD_ID + ".item.power", values[i].getBasePower()),
//                    Component.translatable("info." + QMD.MOD_ID + ".item.max_temp", values[i].getMaxOperatingTemp())
//            };
//        }
//        return info;
//    }
//
//    public static Component[] magnetInfo() {
//        MagnetType[] values = MagnetType.values();
//        Component[] info = new Component[values.length];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = Component.translatable("tile." + QMD.MOD_ID + ".accelerator_magnet.desc");
//        }
//        return info;
//    }
//
//    // Cooler info
//    public static Component[][] coolerFixedInfo() {
//        return coolerFixedInfo(CoolerType.values());
//    }
//
//    private static <T extends Enum<T> & StringRepresentable & ICoolerEnum> Component[][] coolerFixedInfo(T[] values) {
//        Component[][] info = new Component[values.length][];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = new Component[]{coolerCoolingRateString(values[i])};
//        }
//        return info;
//    }
//
//    private static <T extends Enum<T> & ICoolerEnum> Component coolerCoolingRateString(T type) {
//        return Component.translatable("tile." + QMD.MOD_ID + ".accelerator.cooler.cooling_rate", UnitHelper.prefix(type.getHeatRemoved(), 3, "H/t"));
//    }
//
//    public static Component[] coolerInfo() {
//        CoolerType[] values = CoolerType.values();
//        Component[] info = new Component[values.length];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = coolerInfoString(values[i]);
//        }
//        return info;
//    }
//
//    private static Component coolerInfoString(CoolerType type) {
//        return Component.translatable("tile." + QMD.MOD_ID + ".accelerator.cooler." + type.getSerializedName() + ".desc");
//    }
//
//    // Detector info
//    public static Component[][] detectorFixedInfo() {
//        DetectorType[] values = DetectorType.values();
//        Component[][] info = new Component[values.length][];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = new Component[]{
//                    Component.translatable("info." + QMD.MOD_ID + ".particle_chamber.detector.efficiency", Math.round(1000D * values[i].getEfficiency()) / 10d + "%"),
//                    Component.translatable("info." + QMD.MOD_ID + ".particle_chamber.detector.power", values[i].getBasePower())
//            };
//        }
//        return info;
//    }
//
//    public static Component[] detectorInfo() {
//        DetectorType[] values = DetectorType.values();
//        Component[] info = new Component[values.length];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = detectorInfoString(values[i]);
//        }
//        return info;
//    }
//
//    public static Component[] ionSourceFixedInfo(int id) {
//        return new Component[]{
//                Component.translatable("info." + QMD.MOD_ID + ".item.power", QMDConfig.ion_source_power[id]),
//                Component.translatable("info." + QMD.MOD_ID + ".ion_source.output_multiplier", QMDConfig.ion_source_output_multiplier[id]),
//                Component.translatable("info." + QMD.MOD_ID + ".ion_source.focus", QMDConfig.ion_source_focus[id])
//        };
//    }
//
//    public static Component ionSourceInfo() {
//        return Component.translatable("tile." + QMD.MOD_ID + ".ion_source.desc");
//    }
//
//    private static Component detectorInfoString(DetectorType type) {
//        return Component.translatable("tile." + QMD.MOD_ID + ".particle_chamber.detector." + type.getSerializedName() + ".desc");
//    }
//
//    public static Component beamlineInfo() {
//        return Component.translatable("tile." + QMD.MOD_ID + ".beamline.desc");
//    }
//
//    public static Component beamlineFixedlineInfo() {
//        return Component.translatable("info." + QMD.MOD_ID + ".beamline.attenuation", QMDConfig.beamAttenuationRate);
//    }
//
//    // Fission Neutron Shields
//
//    public static Component[][] neutronShieldFixedInfo() {
//        NeutronShieldType[] values = NeutronShieldType.values();
//        Component[][] info = new Component[values.length][];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = new Component[]{
//                    Component.translatable("info." + NuclearcraftNeohaul.MODID + ".tooltip.fission_shield.heat_per_flux", UnitHelper.prefix(values[i].getHeatPerFlux(), 5, "H/t/N")),
//                    Component.translatable("info." + NuclearcraftNeohaul.MODID + ".tooltip.fission_shield.efficiency", Math.round(100D * values[i].getEfficiency()) + "%"),};
//        }
//        return info;
//    }
//
//    public static Component[] neutronShieldInfo() {
//        NeutronShieldType[] values = NeutronShieldType.values();
//        Component[] info = new Component[values.length];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = Component.translatable("info." + NuclearcraftNeohaul.MODID + ".tooltip.fission_shield");
//        }
//        return info;
//    }
//
//    // Heater info
//    public static Component[][] heaterFixedInfo() {
//        return heaterFixedInfo(HeaterType.values());
//    }
//
//    private static <T extends Enum<T> & StringRepresentable & ICoolerEnum> Component[][] heaterFixedInfo(T[] values) {
//        Component[][] info = new Component[values.length][];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = new Component[]{coolerCoolingRateString(values[i])};
//        }
//        return info;
//    }

    public static MutableComponent drillInfo(int id) {
        int size = 2 * QMDStartupConfig.drill_radius[id] + 1;
        return Component.translatable("info." + QMD.MOD_ID + ".item.drill.desc", size, size);
    }

//    // Compressor info TODO
//    public static Component[][] compressorFixedInfo() {
//        CompressorType[] values = CompressorType.values();
//        Component[][] info = new Component[values.length][];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = new Component[]{
//                    Component.translatable("info." + QMD.MOD_ID + ".liquefier.compressor.energy_efficiency", Math.round(1000D * values[i].getEnergyEfficiency()) / 10d + "%"),
//                    Component.translatable("info." + QMD.MOD_ID + ".liquefier.compressor.heat_efficiency", Math.round(1000D * values[i].getHeatEfficiency()) / 10d + "%")
//            };
//        }
//        return info;
//    }
//
//    public static Component[] compressorInfo() {
//        CompressorType[] values = CompressorType.values();
//        Component[] info = new Component[values.length];
//        for (int i = 0; i < values.length; i++) {
//            info[i] = Component.translatable("tile." + QMD.MOD_ID + ".liquefier_compressor.desc");
//        }
//        return info;
//    }
//
//    public static Component liquefierNozzleFixedInfo() {
//        return Component.translatable("info." + QMD.MOD_ID + ".liquefier_nozzle.speed", QMDConfig.liquefier_nozzle_speed);
//    }
}