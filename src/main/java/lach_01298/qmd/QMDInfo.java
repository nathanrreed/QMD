package lach_01298.qmd;

import com.nred.nuclearcraft.util.UnitHelper;
import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.enums.BlockTypes.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class QMDInfo { // TODO should QMDServerConfig be used here instead?
    // RF Cavity info
    public static Component[] RFCavityFixedInfo(RFCavityType type) {
        return new Component[]{
                Component.translatable("info." + QMD.MOD_ID + ".rf_cavity.voltage", type.getVoltage()),
                Component.translatable("info." + QMD.MOD_ID + ".item.efficiency", Math.round(100D * type.getEfficiency()) + "%"),
                Component.translatable("info." + QMD.MOD_ID + ".item.heat", type.getHeatGenerated()),
                Component.translatable("info." + QMD.MOD_ID + ".item.power", type.getBasePower()),
                Component.translatable("info." + QMD.MOD_ID + ".item.max_temp", type.getMaxOperatingTemp())
        };
    }

    public static Component[] RFCavityInfo() {
        RFCavityType[] values = RFCavityType.values();
        Component[] info = new Component[values.length];
        for (int i = 0; i < values.length; i++) {
            info[i] = Component.translatable("tile." + QMD.MOD_ID + ".rf_cavity.desc");
        }
        return info;
    }

    // Magnet info
    public static Component[] magnetFixedInfo(MagnetType type) {
        return new Component[]{
                Component.translatable("info." + QMD.MOD_ID + ".accelerator_magnet.strength", type.getStrength()),
                Component.translatable("info." + QMD.MOD_ID + ".item.efficiency", Math.round(100D * type.getEfficiency()) + "%"),
                Component.translatable("info." + QMD.MOD_ID + ".item.heat", type.getHeatGenerated()),
                Component.translatable("info." + QMD.MOD_ID + ".item.power", type.getBasePower()),
                Component.translatable("info." + QMD.MOD_ID + ".item.max_temp", type.getMaxOperatingTemp())
        };
    }

    public static Component magnetInfo(MagnetType type) {
        return Component.translatable("tile." + QMD.MOD_ID + ".accelerator_magnet.desc");
    }

    // Cooler info
    public static Component[] coolerFixedInfo(CoolerType type) {
        return new Component[]{coolerCoolingRateString(type)};
    }

    private static Component coolerCoolingRateString(CoolerType type) {
        return Component.translatable("tile." + QMD.MOD_ID + ".accelerator.cooler.cooling_rate", UnitHelper.prefix(type.getHeatRemoved(), 3, "H/t"));
    }

    public static Component[] coolerInfo() {
        CoolerType[] values = CoolerType.values();
        Component[] info = new Component[values.length];
        for (int i = 0; i < values.length; i++) {
            info[i] = coolerInfoString(values[i]);
        }
        return info;
    }

    private static Component coolerInfoString(CoolerType type) {
        return Component.translatable("tile." + QMD.MOD_ID + ".accelerator.cooler." + type.getSerializedName() + ".desc");
    }

    // Detector info
    public static Component[] detectorFixedInfo(DetectorType type) {
        return new Component[]{
                Component.translatable("info." + QMD.MOD_ID + ".particle_chamber.detector.efficiency", Math.round(1000D * type.getEfficiency()) / 10d + "%"),
                Component.translatable("info." + QMD.MOD_ID + ".particle_chamber.detector.power", type.getBasePower())
        };
    }

    public static Component detectorInfo(DetectorType type) {
        return detectorInfoString(type);
    }

    public static Component[] ionSourceFixedInfo(int id) {
        return new Component[]{
                Component.translatable("info." + QMD.MOD_ID + ".item.power", QMDStartupConfig.ion_source_power[id]),
                Component.translatable("info." + QMD.MOD_ID + ".ion_source.output_multiplier", QMDStartupConfig.ion_source_output_multiplier[id]),
                Component.translatable("info." + QMD.MOD_ID + ".ion_source.focus", QMDStartupConfig.ion_source_focus[id])
        };
    }

    public static Component ionSourceInfo() {
        return Component.translatable("tile." + QMD.MOD_ID + ".ion_source.desc");
    }

    private static Component detectorInfoString(DetectorType type) {
        return Component.translatable("tile." + QMD.MOD_ID + ".particle_chamber.detector." + type.getSerializedName() + ".desc");
    }

    public static Component beamlineInfo() {
        return Component.translatable("tile." + QMD.MOD_ID + ".beamline.desc");
    }

    public static Component beamlineFixedlineInfo() {
        return Component.translatable("info." + QMD.MOD_ID + ".beamline.attenuation", QMDStartupConfig.beamAttenuationRate);
    }

    public static MutableComponent drillInfo(int id) {
        int size = 2 * QMDStartupConfig.drill_radius[id] + 1;
        return Component.translatable("info." + QMD.MOD_ID + ".item.drill.desc", size, size);
    }

    // Compressor info
    public static Component[] compressorFixedInfo(CompressorType type) {
        return new Component[]{
                Component.translatable("info." + QMD.MOD_ID + ".liquefier.compressor.energy_efficiency", Math.round(1000D * type.getEnergyEfficiency()) / 10d + "%"),
                Component.translatable("info." + QMD.MOD_ID + ".liquefier.compressor.heat_efficiency", Math.round(1000D * type.getHeatEfficiency()) / 10d + "%")
        };
    }

    public static Component compressorInfo(CompressorType type) {
        return Component.translatable("tile." + QMD.MOD_ID + ".liquefier_compressor.desc");
    }

    public static Component liquefierNozzleFixedInfo() {
        return Component.translatable("info." + QMD.MOD_ID + ".liquefier_nozzle.speed", QMDStartupConfig.liquefier_nozzle_speed);
    }
}