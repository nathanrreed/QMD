package lach_01298.qmd.datagen;

import lach_01298.qmd.QMD;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static lach_01298.qmd.QMDDamageSources.*;

class QMDDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public QMDDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, QMD.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeTags.BYPASSES_ARMOR)
                .addOptional(ANTIMATTER_ANNIHLATION.location())
                .addOptional(SELF_POISONING.location())
                .addOptional(LEPTON_CANNON.location())
                .addOptional(GLUON_GUN.location())
                .addOptional(ANTIMATTER_LAUNCHER.location());

        tag(DamageTypeTags.BYPASSES_EFFECTS)
                .addOptional(ANTIMATTER_ANNIHLATION.location())
                .addOptional(SELF_POISONING.location());

        tag(DamageTypeTags.BYPASSES_INVULNERABILITY)
                .addOptional(ANTIMATTER_ANNIHLATION.location())
                .addOptional(SELF_POISONING.location());
    }
}