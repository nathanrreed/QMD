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
    }
}