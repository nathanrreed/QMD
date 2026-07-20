package lach_01298.qmd.recipe.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.BEAM_DUMP_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.BEAM_DUMP_RECIPE_TYPE;

public class BeamDumpRecipe extends QMDParticleRecipe {
    public BeamDumpRecipe(ParticleStack particleIngredient, SizedChanceFluidIngredient fluidProduct, long maxEnergy) {
        super(List.of(), List.of(), List.of(particleIngredient), List.of(), List.of(fluidProduct), List.of(), maxEnergy, 0, 0);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BEAM_DUMP_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return BEAM_DUMP_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<BeamDumpRecipe> {
        private static final MapCodec<BeamDumpRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        ParticleStack.CODEC.fieldOf("particleInput").forGetter(BeamDumpRecipe::getParticleIngredient),
                        SizedChanceFluidIngredient.FLAT_CODEC.fieldOf("fluidProduct").forGetter(BeamDumpRecipe::getFluidProduct),
                        Codec.LONG.fieldOf("maxEnergy").forGetter(BeamDumpRecipe::getMaxEnergy)
                ).apply(inst, BeamDumpRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, BeamDumpRecipe> STREAM_CODEC = StreamCodec.composite(
                ParticleStack.STREAM_CODEC, BeamDumpRecipe::getParticleIngredient,
                SizedChanceFluidIngredient.STREAM_CODEC, BeamDumpRecipe::getFluidProduct,
                ByteBufCodecs.VAR_LONG, BeamDumpRecipe::getMaxEnergy,
                BeamDumpRecipe::new
        );

        @Override
        public MapCodec<BeamDumpRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BeamDumpRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}