package lach_01298.qmd.datagen.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nred.nuclearcraft.recipe.BasicRecipeInput;
import com.nred.nuclearcraft.recipe.ProcessorRecipe;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import com.nred.nuclearcraft.recipe.processor.AssemblerRecipe;
import com.nred.nuclearcraft.util.StreamCodecsHelper;
import lach_01298.qmd.enums.MaterialTypes;
import lach_01298.qmd.item.IItemParticleAmount;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

import static lach_01298.qmd.item.QMDItems.sources;
import static lach_01298.qmd.recipe.RecipeSerializerRegistration.TUNGSTEN_FILAMENT_ASSEMBLER_RECIPE_SERIALIZER;

public class TungstenFilementAssemblerRecipe extends AssemblerRecipe {
    public TungstenFilementAssemblerRecipe(List<SizedChanceItemIngredient> itemInputs, double timeModifier, double powerModifier, double radiation) {
        super(itemInputs, List.of(SizedChanceItemIngredient.of(sources.get(MaterialTypes.SourceType.TUNGSTEN_FILAMENT), 1)), List.of(), List.of(), timeModifier, powerModifier, radiation);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return IItemParticleAmount.fullItem(new ItemStack(sources.get(MaterialTypes.SourceType.TUNGSTEN_FILAMENT).get()));
    }

    @Override
    public ItemStack assemble(BasicRecipeInput input, HolderLookup.Provider registries) {
        return getResultItem(registries);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public List<SizedChanceItemIngredient> getItemProducts() {
        return super.getItemProducts();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TUNGSTEN_FILAMENT_ASSEMBLER_RECIPE_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<TungstenFilementAssemblerRecipe> {
        private static final MapCodec<TungstenFilementAssemblerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        SizedChanceItemIngredient.FLAT_CODEC.listOf(0, 4).fieldOf("itemIngredients").forGetter(ProcessorRecipe::getItemIngredients),
                        Codec.DOUBLE.fieldOf("timeModifier").forGetter(ProcessorRecipe::getProcessTimeMultiplier),
                        Codec.DOUBLE.fieldOf("powerModifier").forGetter(ProcessorRecipe::getProcessPowerMultiplier),
                        Codec.DOUBLE.fieldOf("radiation").forGetter(ProcessorRecipe::getBaseProcessRadiation)
                ).apply(inst, TungstenFilementAssemblerRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, TungstenFilementAssemblerRecipe> STREAM_CODEC = StreamCodec.composite(
                StreamCodecsHelper.SIZED_ITEM_INGREDIENT_LIST_STREAM_CODEC, ProcessorRecipe::getItemIngredients,
                ByteBufCodecs.DOUBLE, ProcessorRecipe::getProcessTimeMultiplier,
                ByteBufCodecs.DOUBLE, ProcessorRecipe::getProcessPowerMultiplier,
                ByteBufCodecs.DOUBLE, ProcessorRecipe::getBaseProcessRadiation,
                TungstenFilementAssemblerRecipe::new
        );

        @Override
        public MapCodec<TungstenFilementAssemblerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TungstenFilementAssemblerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}