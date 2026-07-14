package lach_01298.qmd.datagen;

import lach_01298.qmd.QMD;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EmiLangProvider extends LanguageProvider {
    public EmiLangProvider(DataGenerator gen, String locale) {
        super(gen.getPackOutput(), "emi", locale);
    }

    @Override
    protected void addTranslations() {
        add("emi.category." + ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "ore_leacher").toLanguageKey(), "Ore Leacher");
        add("emi.category." + ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "irradiator").toLanguageKey(), "Irradiator");
        add("emi.category." + ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "atmosphere_collector").toLanguageKey(), "Atmosphere Collector");
        add("emi.category." + ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "liquid_collector").toLanguageKey(), "Liquid Collector");

        add("emi.category." + ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "accelerator_cooling").toLanguageKey(), "Accelerator Cooling");
        add("emi.category." + ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "accelerator_source").toLanguageKey(), "Ion Source");
        add("emi.category." + ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "mass_spectrometer").toLanguageKey(), "Mass Spectrometer");

        add("emi.category." + ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "particle_info").toLanguageKey(), "Particle Information");
    }
}