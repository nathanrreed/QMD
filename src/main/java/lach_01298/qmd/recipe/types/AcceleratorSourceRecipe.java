package lach_01298.qmd.recipe.types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import com.nred.nuclearcraft.util.StreamCodecsHelper;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.ACCELERATOR_SOURCE_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.ACCELERATOR_SOURCE_RECIPE_TYPE;

public class AcceleratorSourceRecipe extends QMDRecipe {
    public AcceleratorSourceRecipe(List<SizedChanceItemIngredient> itemInputs, List<SizedChanceFluidIngredient> fluidInputs, ParticleStack particleResult) {
        super(itemInputs, fluidInputs, List.of(), List.of(), List.of(), List.of(particleResult));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ACCELERATOR_SOURCE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ACCELERATOR_SOURCE_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<AcceleratorSourceRecipe> {
        private static final MapCodec<AcceleratorSourceRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        SizedChanceItemIngredient.FLAT_CODEC.listOf(0, 1).fieldOf("itemInput").forGetter(AcceleratorSourceRecipe::getItemIngredients),
                        SizedChanceFluidIngredient.FLAT_CODEC.listOf(0, 1).fieldOf("fluidInput").forGetter(AcceleratorSourceRecipe::getFluidIngredients),
                        ParticleStack.CODEC.fieldOf("particleProduct").forGetter(AcceleratorSourceRecipe::getParticleProduct)
                ).apply(inst, AcceleratorSourceRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, AcceleratorSourceRecipe> STREAM_CODEC = StreamCodec.composite(
                StreamCodecsHelper.SIZED_ITEM_INGREDIENT_LIST_STREAM_CODEC, AcceleratorSourceRecipe::getItemIngredients,
                StreamCodecsHelper.SIZED_FLUID_INGREDIENT_LIST_STREAM_CODEC, AcceleratorSourceRecipe::getFluidIngredients,
                ParticleStack.STREAM_CODEC, AcceleratorSourceRecipe::getParticleProduct,
                AcceleratorSourceRecipe::new
        );

        @Override
        public MapCodec<AcceleratorSourceRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AcceleratorSourceRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}