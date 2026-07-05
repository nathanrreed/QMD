package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.datagen.ModDataMapProvider;
import lach_01298.qmd.QMD;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.emptySet;
import static lach_01298.qmd.QMDDamageSources.*;

@EventBusSubscriber(modid = QMD.MOD_ID)
public class QMDDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(QMDBlockLootTableSubProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider)
        );

        event.createDatapackRegistryObjects(new RegistrySetBuilder()
                .add(Registries.DAMAGE_TYPE, bootstrap -> {
                    bootstrap.register(ANTIMATTER_ANNIHLATION, new DamageType("antimatter_annihilation", DamageScaling.NEVER, 0.1f));
                    bootstrap.register(SELF_POISONING, new DamageType("self_poisoning", DamageScaling.NEVER, 0.1f));
                    bootstrap.register(LEPTON_CANNON, new DamageType("lepton_cannon", DamageScaling.NEVER, 0.1f));
                    bootstrap.register(GLUON_GUN, new DamageType("gluon_gun", DamageScaling.NEVER, 0.1f));
                    bootstrap.register(ANTIMATTER_LAUNCHER, new DamageType("antimatter_launcher", DamageScaling.NEVER, 0.1f));
                })
        );

        QMDBlockTagProvider blockTagProvider = new QMDBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagProvider);
        generator.addProvider(event.includeServer(), new QMDFluidTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new QMDItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeClient(), new QMDBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new QMDItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new QMDDamageTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeClient(), new QMDLanguageProvider(generator, "en_us"));
        generator.addProvider(event.includeServer(), new QMDDataMapProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new QMDRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeClient(), new EmiLangProvider(generator, "en_us"));

    }
}