package lach_01298.qmd.recipe.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.DECAY_CHAMBER_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.DECAY_CHAMBER_RECIPE_TYPE;

public class DecayChamberRecipe extends QMDParticleRecipe {
    public DecayChamberRecipe(ParticleStack particleIngredient, List<ParticleStack> particleProductsList, long maxEnergy, double crossSection, long energyReleased) {
        super(List.of(), List.of(), List.of(particleIngredient), List.of(), List.of(), particleProductsList, maxEnergy, crossSection, energyReleased);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DECAY_CHAMBER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return DECAY_CHAMBER_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<DecayChamberRecipe> {
        private static final MapCodec<DecayChamberRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        ParticleStack.CODEC.fieldOf("particleInput").forGetter(DecayChamberRecipe::getParticleIngredient),
                        ParticleStack.CODEC.listOf(1, 3).fieldOf("particleProducts").forGetter(DecayChamberRecipe::getParticleProducts),
                        Codec.LONG.fieldOf("maxEnergy").forGetter(DecayChamberRecipe::getMaxEnergy),
                        Codec.DOUBLE.fieldOf("crossSection").forGetter(DecayChamberRecipe::getCrossSection),
                        Codec.LONG.fieldOf("energyReleased").forGetter(DecayChamberRecipe::getEnergyReleased)
                ).apply(inst, DecayChamberRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, DecayChamberRecipe> STREAM_CODEC = StreamCodec.composite(
                ParticleStack.STREAM_CODEC, DecayChamberRecipe::getParticleIngredient,
                ParticleStack.PARTICLE_STACK_LIST_STREAM_CODEC, DecayChamberRecipe::getParticleProducts,
                ByteBufCodecs.VAR_LONG, DecayChamberRecipe::getMaxEnergy,
                ByteBufCodecs.DOUBLE, DecayChamberRecipe::getCrossSection,
                ByteBufCodecs.VAR_LONG, DecayChamberRecipe::getEnergyReleased,
                DecayChamberRecipe::new
        );

        @Override
        public MapCodec<DecayChamberRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DecayChamberRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}