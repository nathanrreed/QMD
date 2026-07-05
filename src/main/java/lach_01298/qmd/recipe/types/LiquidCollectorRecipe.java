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

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.LIQUID_COLLECTOR_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.LIQUID_COLLECTOR_RECIPE_TYPE;

public class LiquidCollectorRecipe extends FluidCollectorRecipe {
    public LiquidCollectorRecipe(FluidStack outputFluid, List<ResourceLocation> blocks, List<ResourceLocation> dimensions, List<ResourceLocation> biomes) {
        super(outputFluid, blocks, dimensions, biomes);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LIQUID_COLLECTOR_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return LIQUID_COLLECTOR_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<LiquidCollectorRecipe> {
        private static final MapCodec<LiquidCollectorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        FluidStack.CODEC.fieldOf("outputFluid").forGetter(LiquidCollectorRecipe::getOutputFluid),
                        ResourceLocation.CODEC.listOf().fieldOf("blocks").forGetter(LiquidCollectorRecipe::getBlocks),
                        ResourceLocation.CODEC.listOf().fieldOf("dimensions").forGetter(LiquidCollectorRecipe::getDimensions),
                        ResourceLocation.CODEC.listOf().fieldOf("biomes").forGetter(LiquidCollectorRecipe::getBiomes)
                ).apply(inst, LiquidCollectorRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, LiquidCollectorRecipe> STREAM_CODEC = StreamCodec.composite(
                FluidStack.STREAM_CODEC, LiquidCollectorRecipe::getOutputFluid,
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), LiquidCollectorRecipe::getBlocks,
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), LiquidCollectorRecipe::getDimensions,
                ResourceLocation.STREAM_CODEC.apply(ByteBufCodecs.list()), LiquidCollectorRecipe::getBiomes,
                LiquidCollectorRecipe::new
        );

        @Override
        public MapCodec<LiquidCollectorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LiquidCollectorRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}