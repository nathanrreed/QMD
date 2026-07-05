package lach_01298.qmd.recipe.types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.ATMOSPHERE_COLLECTOR_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.ATMOSPHERE_COLLECTOR_RECIPE_TYPE;

public class AtmosphereCollectorRecipe extends FluidCollectorRecipe {
    public AtmosphereCollectorRecipe(FluidStack outputFluid, List<ResourceLocation> dimensions, List<ResourceLocation> biomes) {
        super(outputFluid, List.of(), dimensions, biomes);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ATMOSPHERE_COLLECTOR_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ATMOSPHERE_COLLECTOR_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<AtmosphereCollectorRecipe> {
        private static final MapCodec<AtmosphereCollectorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        FluidStack.CODEC.fieldOf("outputFluid").forGetter(AtmosphereCollectorRecipe::getOutputFluid),
                        ResourceLocation.CODEC.listOf().fieldOf("dimensions").forGetter(AtmosphereCollectorRecipe::getDimensions),
                        ResourceLocation.CODEC.listOf().fieldOf("biomes").forGetter(AtmosphereCollectorRecipe::getBiomes)
                ).apply(inst, AtmosphereCollectorRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, AtmosphereCollectorRecipe> STREAM_CODEC = StreamCodec.composite(
                FluidStack.STREAM_CODEC, AtmosphereCollectorRecipe::getOutputFluid,
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), AtmosphereCollectorRecipe::getDimensions,
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), AtmosphereCollectorRecipe::getBiomes,
                AtmosphereCollectorRecipe::new
        );

        @Override
        public MapCodec<AtmosphereCollectorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AtmosphereCollectorRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}