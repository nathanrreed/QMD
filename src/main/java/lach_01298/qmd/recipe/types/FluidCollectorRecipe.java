package lach_01298.qmd.recipe.types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class FluidCollectorRecipe implements Recipe<FluidCollectorInput> {
    private final FluidStack outputFluid;
    private final List<ResourceLocation> blocks;
    private final List<ResourceLocation> dimensions;
    private final List<ResourceLocation> biomes;

    public FluidCollectorRecipe(FluidStack outputFluid, List<ResourceLocation> blocks, List<ResourceLocation> dimensions, List<ResourceLocation> biomes) {
        this.outputFluid = outputFluid;
        this.blocks = blocks;
        this.dimensions = dimensions;
        this.biomes = biomes;
    }

    public FluidStack getOutputFluid() {
        return outputFluid;
    }

    public List<ResourceLocation> getBlocks() {
        return blocks;
    }

    public List<ResourceLocation> getDimensions() {
        return dimensions;
    }

    public List<ResourceLocation> getBiomes() {
        return biomes;
    }

    @Override
    public boolean matches(FluidCollectorInput input, Level level) {
        return (blocks.isEmpty() || blocks.contains(BuiltInRegistries.BLOCK.getKey(input.block()))) && (dimensions.isEmpty() || dimensions.contains(input.dimension())) && (biomes.isEmpty() || biomes.contains(input.biome()));
    }

    @Override
    public ItemStack assemble(FluidCollectorInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        throw new RuntimeException("Raw Recipe Serializer Used!");
    }

    @Override
    public RecipeType<?> getType() {
        throw new RuntimeException("Raw Recipe Type Used!");
    }
}