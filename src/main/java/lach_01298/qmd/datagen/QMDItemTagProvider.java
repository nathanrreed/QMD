package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.info.NCFluid;
import lach_01298.qmd.QMD;
import lach_01298.qmd.enums.MaterialTypes.IsotopeType;
import lach_01298.qmd.fluid.QMDFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static com.nred.nuclearcraft.datagen.ModItemTagProvider.isotopeTag;
import static com.nred.nuclearcraft.helpers.Concat.fluidValues;
import static lach_01298.qmd.item.QMDItems.*;

class QMDItemTagProvider extends ItemTagsProvider {
    public QMDItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, QMD.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.MINING_TOOL_TOOLS).add(pickaxe_tungsten_carbide.asItem());
        tag(Tags.Items.MELEE_WEAPON_TOOLS).add(sword_tungsten_carbide.asItem());
        tag(ItemTags.PICKAXES).add(pickaxe_tungsten_carbide.asItem());
        tag(ItemTags.SWORDS).add(sword_tungsten_carbide.asItem());
        tag(ItemTags.AXES).add(axe_tungsten_carbide.asItem());
        tag(ItemTags.SHOVELS).add(shovel_tungsten_carbide.asItem());
        tag(ItemTags.HOES).add(hoe_tungsten_carbide.asItem());

        for (var entry : Stream.of(ingots, ingotAlloys).flatMap(e -> e.entrySet().stream()).sorted((a, b) -> a.getKey().getSerializedName().compareTo(b.getKey().getSerializedName())).toList()) {
            simpleTag(entry.getKey().getSerializedName(), entry.getValue().get(), Tags.Items.INGOTS);
        }
        for (var entry : Stream.of(dusts, chemicalDusts).flatMap(e -> e.entrySet().stream()).sorted((a, b) -> a.getKey().getSerializedName().compareTo(b.getKey().getSerializedName())).toList()) {
            simpleTag(entry.getKey().getSerializedName(), entry.getValue().get(), Tags.Items.DUSTS);
        }

        for (NCFluid fluid : fluidValues(QMDFluids.QMD_FLUIDS)) {
            tag(Tags.Items.BUCKETS).add(fluid.bucket.get());
        }

        for (IsotopeType key : isotopes.keySet()) {
            String[] parts = key.getSerializedName().split("_");
            tag(isotopeTag(parts[0] + "/" + parts[1])).add(isotopes.get(key).asItem());
        }
    }

    private void simpleTag(String name, Item item, TagKey<Item> tag) {
        this.tag(tag).add(item);
        this.tag(ItemTags.create(tag.location().withSuffix("/" + name))).add(item);
    }
}