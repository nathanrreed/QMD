package lach_01298.qmd.recipe.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import com.nred.nuclearcraft.util.StreamCodecsHelper;
import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

import static lach_01298.qmd.recipe.RecipeSerializerRegistration.TARGET_CHAMBER_RECIPE_SERIALIZER;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.TARGET_CHAMBER_RECIPE_TYPE;

public class TargetChamberRecipe extends QMDParticleRecipe {
    public TargetChamberRecipe(List<SizedChanceItemIngredient> itemIngredientsList, List<SizedChanceFluidIngredient> fluidIngredientsList, List<ParticleStack> particleIngredientsList, List<SizedChanceItemIngredient> itemProductsList, List<SizedChanceFluidIngredient> fluidProductsList, List<ParticleStack> particleProducts, long maxEnergy, double crossSection, long energyReleased) {
        super(itemIngredientsList, fluidIngredientsList, particleIngredientsList, itemProductsList, fluidProductsList, particleProducts, maxEnergy, crossSection, energyReleased);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TARGET_CHAMBER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TARGET_CHAMBER_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<TargetChamberRecipe> {
        private static final MapCodec<TargetChamberRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->
                inst.group(
                        SizedChanceItemIngredient.FLAT_CODEC.listOf(0, 1).fieldOf("itemInput").forGetter(TargetChamberRecipe::getItemIngredients),
                        SizedChanceFluidIngredient.FLAT_CODEC.listOf(0, 1).fieldOf("fluidInput").forGetter(TargetChamberRecipe::getFluidIngredients),
                        ParticleStack.CODEC.listOf(0, 1).fieldOf("particleInput").forGetter(TargetChamberRecipe::getParticleIngredients),
                        SizedChanceItemIngredient.FLAT_CODEC.listOf(0, 1).fieldOf("itemProduct").forGetter(TargetChamberRecipe::getItemProducts),
                        SizedChanceFluidIngredient.FLAT_CODEC.listOf(0, 1).fieldOf("fluidProduct").forGetter(TargetChamberRecipe::getFluidProducts),
                        ParticleStack.CODEC.listOf(0, 3).fieldOf("particleProducts").forGetter(TargetChamberRecipe::getParticleProducts),
                        Codec.LONG.fieldOf("maxEnergy").forGetter(TargetChamberRecipe::getMaxEnergy),
                        Codec.DOUBLE.fieldOf("crossSection").forGetter(TargetChamberRecipe::getCrossSection),
                        Codec.LONG.fieldOf("energyReleased").forGetter(TargetChamberRecipe::getEnergyReleased)
                ).apply(inst, TargetChamberRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, TargetChamberRecipe> STREAM_CODEC = StreamCodecsHelper.composite(
                StreamCodecsHelper.SIZED_ITEM_INGREDIENT_LIST_STREAM_CODEC, TargetChamberRecipe::getItemIngredients,
                StreamCodecsHelper.SIZED_FLUID_INGREDIENT_LIST_STREAM_CODEC, TargetChamberRecipe::getFluidIngredients,
                ParticleStack.PARTICLE_STACK_LIST_STREAM_CODEC, TargetChamberRecipe::getParticleIngredients,
                StreamCodecsHelper.SIZED_ITEM_INGREDIENT_LIST_STREAM_CODEC, TargetChamberRecipe::getItemProducts,
                StreamCodecsHelper.SIZED_FLUID_INGREDIENT_LIST_STREAM_CODEC, TargetChamberRecipe::getFluidProducts,
                ParticleStack.PARTICLE_STACK_LIST_STREAM_CODEC, TargetChamberRecipe::getParticleProducts,
                ByteBufCodecs.VAR_LONG, TargetChamberRecipe::getMaxEnergy,
                ByteBufCodecs.DOUBLE, TargetChamberRecipe::getCrossSection,
                ByteBufCodecs.VAR_LONG, TargetChamberRecipe::getEnergyReleased,
                TargetChamberRecipe::new
        );

        @Override
        public MapCodec<TargetChamberRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TargetChamberRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}