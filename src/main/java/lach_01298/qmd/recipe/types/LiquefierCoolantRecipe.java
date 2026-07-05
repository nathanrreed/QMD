package lach_01298.qmd.recipe.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nred.nuclearcraft.recipe.BasicRecipe;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.LIQUEFIER_COOLANT_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.LIQUEFIER_COOLANT_RECIPE_TYPE;

public class LiquefierCoolantRecipe extends BasicRecipe {
    private final int heatRequired;
    private final int inputTemp;
    private final int outputTemp;

    public LiquefierCoolantRecipe(SizedChanceFluidIngredient fluidIngredient, SizedChanceFluidIngredient fluidProduct, int heatRequired, int inputTemp, int outputTemp) {
        super(List.of(), List.of(fluidIngredient), List.of(), List.of(fluidProduct));
        this.heatRequired = heatRequired;
        this.inputTemp = inputTemp;
        this.outputTemp = outputTemp;
    }

    public int getHeatRequired() {
        return heatRequired;
    }

    public int getInputTemperature() {
        return inputTemp;
    }

    public int getOutputTemperature() {
        return outputTemp;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LIQUEFIER_COOLANT_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return LIQUEFIER_COOLANT_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<LiquefierCoolantRecipe> {
        private static final MapCodec<LiquefierCoolantRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        SizedChanceFluidIngredient.FLAT_CODEC.fieldOf("fluidInput").forGetter(LiquefierCoolantRecipe::getFluidIngredient),
                        SizedChanceFluidIngredient.FLAT_CODEC.fieldOf("fluidProduct").forGetter(LiquefierCoolantRecipe::getFluidProduct),
                        Codec.INT.fieldOf("heatRequired").forGetter(LiquefierCoolantRecipe::getHeatRequired),
                        Codec.INT.fieldOf("inputTemp").forGetter(LiquefierCoolantRecipe::getInputTemperature),
                        Codec.INT.fieldOf("outputTemp").forGetter(LiquefierCoolantRecipe::getOutputTemperature)
                ).apply(inst, LiquefierCoolantRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, LiquefierCoolantRecipe> STREAM_CODEC = StreamCodec.composite(
                SizedChanceFluidIngredient.STREAM_CODEC, LiquefierCoolantRecipe::getFluidIngredient,
                SizedChanceFluidIngredient.STREAM_CODEC, LiquefierCoolantRecipe::getFluidProduct,
                ByteBufCodecs.INT, LiquefierCoolantRecipe::getHeatRequired,
                ByteBufCodecs.INT, LiquefierCoolantRecipe::getInputTemperature,
                ByteBufCodecs.INT, LiquefierCoolantRecipe::getOutputTemperature,
                LiquefierCoolantRecipe::new
        );

        @Override
        public MapCodec<LiquefierCoolantRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LiquefierCoolantRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}