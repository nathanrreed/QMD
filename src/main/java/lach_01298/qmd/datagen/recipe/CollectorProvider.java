package lach_01298.qmd.datagen.recipe;

import com.google.common.base.CaseFormat;
import lach_01298.qmd.QMD;
import lach_01298.qmd.fluid.QMDFluids;
import lach_01298.qmd.recipe.types.AtmosphereCollectorRecipe;
import lach_01298.qmd.recipe.types.FluidCollectorRecipe;
import lach_01298.qmd.recipe.types.LiquidCollectorRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.world.level.Level.NETHER;
import static net.minecraft.world.level.Level.OVERWORLD;

public class CollectorProvider {
    public CollectorProvider(RecipeOutput recipeOutput) {
        new CollectorRecipeBuilder<>(new AtmosphereCollectorRecipe(new FluidStack(QMDFluids.QMD_FLUIDS.get("compressed_air").still, 1000), List.of(OVERWORLD.location()), List.of())).save(recipeOutput);

        new CollectorRecipeBuilder<>(new LiquidCollectorRecipe(new FluidStack(Fluids.WATER, 5000), blocks(Blocks.WATER), List.of(OVERWORLD.location()), biomes(Biomes.RIVER, Biomes.FROZEN_RIVER, Biomes.SWAMP, Biomes.MANGROVE_SWAMP))).save(recipeOutput);
        new CollectorRecipeBuilder<>(new LiquidCollectorRecipe(new FluidStack(QMDFluids.QMD_FLUIDS.get("salt_water").still, 1000), blocks(Blocks.WATER), List.of(OVERWORLD.location()), biomes(Biomes.OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_OCEAN, Biomes.FROZEN_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH))).save(recipeOutput);
        new CollectorRecipeBuilder<>(new LiquidCollectorRecipe(new FluidStack(Fluids.LAVA, 100), blocks(Blocks.LAVA), List.of(NETHER.location()), List.of())).save(recipeOutput);
    }

    @SafeVarargs
    public final List<ResourceLocation> biomes(ResourceKey<Biome>... biomes) {
        return Arrays.stream(biomes).map(ResourceKey::location).toList();
    }

    public final List<ResourceLocation> blocks(Block... blocks) {
        return Arrays.stream(blocks).map(BuiltInRegistries.BLOCK::getKey).toList();
    }

    public static class CollectorRecipeBuilder<RECIPE extends FluidCollectorRecipe> implements RecipeBuilder {
        protected final RECIPE recipe;
        protected Map<String, Criterion<?>> criteria = new HashMap<>();

        protected String group = null;

        public CollectorRecipeBuilder(RECIPE recipe) {
            this.recipe = recipe;
        }

        @Override
        public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
            this.criteria.put(name, criterion);
            return this;
        }

        @Override
        public RecipeBuilder group(@Nullable String groupName) {
            this.group = groupName;
            return this;
        }

        @Override
        public Item getResult() {
            return ItemStack.EMPTY.getItem();
        }

        @Override
        public void save(RecipeOutput recipeOutput) {
            save(recipeOutput, BuiltInRegistries.FLUID.getKey(recipe.getOutputFluid().getFluid()));
        }

        @Override
        public void save(RecipeOutput output, ResourceLocation key) {
            key = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, key.withPrefix(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.recipe.getClass().getSimpleName() + "/").replace("_recipe", "")).getPath());
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                    .rewards(AdvancementRewards.Builder.recipe(key))
                    .requirements(AdvancementRequirements.Strategy.OR);

            output.accept(key, this.recipe, advancement.build(key.withPrefix("recipes/")));
        }
    }
}