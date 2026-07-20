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

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.COLLISION_CHAMBER_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.COLLISION_CHAMBER_RECIPE_TYPE;

public class CollisionChamberRecipe extends QMDParticleRecipe {
    public CollisionChamberRecipe(List<ParticleStack> particleIngredients, List<ParticleStack> particleProductsList, long maxEnergy, double crossSection, long energyReleased) {
        super(List.of(), List.of(), particleIngredients, List.of(), List.of(), particleProductsList, maxEnergy, crossSection, energyReleased);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return COLLISION_CHAMBER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return COLLISION_CHAMBER_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CollisionChamberRecipe> {
        private static final MapCodec<CollisionChamberRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        ParticleStack.CODEC.listOf(2, 2).fieldOf("particleInputs").forGetter(CollisionChamberRecipe::getParticleIngredients),
                        ParticleStack.CODEC.listOf(1, 4).fieldOf("particleProducts").forGetter(CollisionChamberRecipe::getParticleProducts),
                        Codec.LONG.fieldOf("maxEnergy").forGetter(CollisionChamberRecipe::getMaxEnergy),
                        Codec.DOUBLE.fieldOf("crossSection").forGetter(CollisionChamberRecipe::getCrossSection),
                        Codec.LONG.fieldOf("energyReleased").forGetter(CollisionChamberRecipe::getEnergyReleased)
                ).apply(inst, CollisionChamberRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, CollisionChamberRecipe> STREAM_CODEC = StreamCodec.composite(
                ParticleStack.PARTICLE_STACK_LIST_STREAM_CODEC, CollisionChamberRecipe::getParticleIngredients,
                ParticleStack.PARTICLE_STACK_LIST_STREAM_CODEC, CollisionChamberRecipe::getParticleProducts,
                ByteBufCodecs.VAR_LONG, CollisionChamberRecipe::getMaxEnergy,
                ByteBufCodecs.DOUBLE, CollisionChamberRecipe::getCrossSection,
                ByteBufCodecs.VAR_LONG, CollisionChamberRecipe::getEnergyReleased,
                CollisionChamberRecipe::new
        );

        @Override
        public MapCodec<CollisionChamberRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CollisionChamberRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}