package lach_01298.qmd.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static lach_01298.qmd.block.QMDBlocks.*;

public class QMDBlockLootTableSubProvider extends BlockLootSubProvider {
    public QMDBlockLootTableSubProvider(HolderLookup.Provider registries) {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        dropSelf(greenLuminousPaint.get()); // TODO Add to?
        dropSelf(blueLuminousPaint.get());
        dropSelf(orangeLuminousPaint.get());

        dropSelf(fissionReflector.get());
        dropSelf(fissionShield.get());
        dropSelf(turbineBladeSuperAlloy.get());
        dropSelf(rtgStrontium.get());
        dropSelf(strontium90.get());

        for (DeferredBlock<Block> block : dischargeLamps.values()) {
            dropSelf(block.get());
        }

        add(irradiator.get(), createMachineDrop(irradiator.get()));
        add(oreLeacher.get(), createMachineDrop(oreLeacher.get()));

        dropSelf(atmosphereCollector.get());
        dropSelf(liquidCollector.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> all = new ArrayList<>();
        all.addAll(Stream.of(greenLuminousPaint, blueLuminousPaint, orangeLuminousPaint, rtgStrontium, strontium90, fissionReflector, fissionShield, turbineBladeSuperAlloy, irradiator, oreLeacher, atmosphereCollector, liquidCollector, creativeParticleSource).map(DeferredHolder::get).toList());
        all.addAll(Stream.of(dischargeLamps).flatMap(e -> e.values().parallelStream()).map(DeferredHolder::get).toList());
        return all;
    }

    private LootTable.Builder createMachineDrop(Block block) {
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                                .add(LootItem.lootTableItem(block).apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(DataComponents.CUSTOM_DATA).include(DataComponents.CUSTOM_NAME)))
                        )
                );
    }
}