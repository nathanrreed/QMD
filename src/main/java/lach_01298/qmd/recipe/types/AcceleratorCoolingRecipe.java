package lach_01298.qmd.recipe.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import lach_01298.qmd.recipe.QMDRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.ACCELERATOR_COOLING_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.ACCELERATOR_COOLING_RECIPE_TYPE;

public class AcceleratorCoolingRecipe extends QMDRecipe {
    private final int heatRequired;
    private final int coolantTemperature;

    public AcceleratorCoolingRecipe(SizedChanceFluidIngredient fluidInput, SizedChanceFluidIngredient fluidProduct, int heatRequired, int coolantTemperature) {
        super(List.of(), List.of(fluidInput), List.of(), List.of(), List.of(fluidProduct), List.of());
        this.heatRequired = heatRequired;
        this.coolantTemperature = coolantTemperature;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ACCELERATOR_COOLING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ACCELERATOR_COOLING_RECIPE_TYPE.get();
    }

    public int getHeatRequired() {
        return heatRequired;
    }

    public int getCoolantTemperature() {
        return coolantTemperature;
    }

    public static class Serializer implements RecipeSerializer<AcceleratorCoolingRecipe> {
        private static final MapCodec<AcceleratorCoolingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        SizedChanceFluidIngredient.FLAT_CODEC.fieldOf("fluidInput").forGetter(AcceleratorCoolingRecipe::getFluidIngredient),
                        SizedChanceFluidIngredient.FLAT_CODEC.fieldOf("fluidProduct").forGetter(AcceleratorCoolingRecipe::getFluidProduct),
                        Codec.INT.fieldOf("timeMultiplier").forGetter(AcceleratorCoolingRecipe::getHeatRequired),
                        Codec.INT.fieldOf("timeMultiplier").forGetter(AcceleratorCoolingRecipe::getCoolantTemperature)
                ).apply(inst, AcceleratorCoolingRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, AcceleratorCoolingRecipe> STREAM_CODEC = StreamCodec.composite(
                SizedChanceFluidIngredient.STREAM_CODEC, AcceleratorCoolingRecipe::getFluidIngredient,
                SizedChanceFluidIngredient.STREAM_CODEC, AcceleratorCoolingRecipe::getFluidProduct,
                ByteBufCodecs.INT, AcceleratorCoolingRecipe::getHeatRequired,
                ByteBufCodecs.INT, AcceleratorCoolingRecipe::getCoolantTemperature,
                AcceleratorCoolingRecipe::new
        );

        @Override
        public MapCodec<AcceleratorCoolingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AcceleratorCoolingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}