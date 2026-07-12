package lach_01298.qmd.recipe.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import com.nred.nuclearcraft.util.StreamCodecsHelper;
import lach_01298.qmd.recipe.QMDRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.MASS_SPECTROMETER_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.MASS_SPECTROMETER_RECIPE_TYPE;

public class MassSpectrometerRecipe extends QMDRecipe {
    private final double timeMultiplier;

    public MassSpectrometerRecipe(List<SizedChanceItemIngredient> itemInputs, List<SizedChanceFluidIngredient> fluidInputs, List<SizedChanceItemIngredient> itemResults, double timeMultiplier) {
        super(itemInputs, fluidInputs, List.of(), itemResults, List.of(), List.of());
        this.timeMultiplier = timeMultiplier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MASS_SPECTROMETER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return MASS_SPECTROMETER_RECIPE_TYPE.get();
    }

    public double getTimeMultiplier() {
        return timeMultiplier;
    }

    public double getBaseProcessTime(double defaultProcessTime) {
        return getTimeMultiplier() * defaultProcessTime;
    }

    public static class Serializer implements RecipeSerializer<MassSpectrometerRecipe> {
        private static final MapCodec<MassSpectrometerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        SizedChanceItemIngredient.FLAT_CODEC.listOf(0, 1).fieldOf("itemInput").forGetter(MassSpectrometerRecipe::getItemIngredients),
                        SizedChanceFluidIngredient.FLAT_CODEC.listOf(0, 1).fieldOf("fluidInput").forGetter(MassSpectrometerRecipe::getFluidIngredients),
                        SizedChanceItemIngredient.FLAT_CODEC.listOf(1, 4).fieldOf("itemProducts").forGetter(MassSpectrometerRecipe::getItemProducts),
                        Codec.DOUBLE.fieldOf("timeMultiplier").forGetter(MassSpectrometerRecipe::getTimeMultiplier)
                ).apply(inst, MassSpectrometerRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, MassSpectrometerRecipe> STREAM_CODEC = StreamCodec.composite(
                StreamCodecsHelper.SIZED_ITEM_INGREDIENT_LIST_STREAM_CODEC, MassSpectrometerRecipe::getItemIngredients,
                StreamCodecsHelper.SIZED_FLUID_INGREDIENT_LIST_STREAM_CODEC, MassSpectrometerRecipe::getFluidIngredients,
                StreamCodecsHelper.SIZED_ITEM_INGREDIENT_LIST_STREAM_CODEC, MassSpectrometerRecipe::getItemProducts,
                ByteBufCodecs.DOUBLE, MassSpectrometerRecipe::getTimeMultiplier,
                MassSpectrometerRecipe::new
        );

        @Override
        public MapCodec<MassSpectrometerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MassSpectrometerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}