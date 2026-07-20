package lach_01298.qmd.datagen;

import lach_01298.qmd.QMD;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.nred.nuclearcraft.datagen.ModFluidTagProvider.ARGON_TAG;
import static com.nred.nuclearcraft.datagen.ModFluidTagProvider.NEON_TAG;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;

public class QMDFluidTagProvider extends FluidTagsProvider {
    public QMDFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, QMD.MOD_ID, existingFileHelper);
    }

    public static final TagKey<Fluid> LIQUID_HYDROGEN_TAG = fluidTag("liquid_hydrogen");
    public static final TagKey<Fluid> LIQUID_ARGON_TAG = fluidTag("liquid_argon");
    public static final TagKey<Fluid> LIQUID_NEON_TAG = fluidTag("liquid_neon");
    public static final TagKey<Fluid> LIQUID_OXYGEN_TAG = fluidTag("liquid_oxygen");

    public static final TagKey<Fluid> NITRIC_ACID_TAG = fluidTag("nitric_acid");
    public static final TagKey<Fluid> HYDROCHLORIC_ACID_TAG = fluidTag("hydrochloric_acid");

    public static final TagKey<Fluid> MERCURY_TAG = fluidTag("mercury");
    public static final TagKey<Fluid> CHLORINE_TAG = fluidTag("chlorine");
    public static final TagKey<Fluid> DIBORANE_TAG = fluidTag("diborane");

    public static final TagKey<Fluid> ENDERIUM_TAG = fluidTag("enderium");

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(NITRIC_ACID_TAG).add(QMD_FLUIDS.get("nitric_acid").still.get());
        tag(HYDROCHLORIC_ACID_TAG).add(QMD_FLUIDS.get("hydrochloric_acid").still.get());

        tag(MERCURY_TAG).add(QMD_FLUIDS.get("mercury").still.get());

        tag(ARGON_TAG).add(QMD_FLUIDS.get("argon").still.get());
        tag(NEON_TAG).add(QMD_FLUIDS.get("neon").still.get());
        tag(CHLORINE_TAG).add(QMD_FLUIDS.get("chlorine").still.get());

        tag(LIQUID_HYDROGEN_TAG).add(QMD_FLUIDS.get("liquid_hydrogen").still.get());
        tag(LIQUID_ARGON_TAG).add(QMD_FLUIDS.get("liquid_argon").still.get());
        tag(LIQUID_NEON_TAG).add(QMD_FLUIDS.get("liquid_neon").still.get());
        tag(LIQUID_OXYGEN_TAG).add(QMD_FLUIDS.get("liquid_oxygen").still.get());
    }

    public static TagKey<Fluid> fluidTag(String name) {
        return FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }
}
