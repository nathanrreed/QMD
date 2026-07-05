package lach_01298.qmd.datagen;

import lach_01298.qmd.QMD;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static lach_01298.qmd.block.QMDBlocks.*;

public class QMDBlockTagProvider extends BlockTagsProvider {
    public QMDBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, QMD.MOD_ID, existingFileHelper);
    }

    public static final TagKey<Block> MINEABLE_WITH_DRILL = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "mineable/drill"));

    @Override
    public void addTags(HolderLookup.Provider provider) {
        tag(MINEABLE_WITH_DRILL).addTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.MINEABLE_WITH_SHOVEL);

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(rtgStrontium.get(), strontium90.get(), fissionReflector.get(), fissionShield.get(), turbineBladeSuperAlloy.get(), irradiator.get(), oreLeacher.get(), atmosphereCollector.get(), liquidCollector.get());
    }
}