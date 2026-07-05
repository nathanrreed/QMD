package lach_01298.qmd.datagen;

import lach_01298.qmd.QMD;
import lach_01298.qmd.fluid.QMDFluids;
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
import static com.nred.nuclearcraft.registration.FluidRegistration.MOLTEN_MAP;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;

public class QMDFluidTagProvider extends FluidTagsProvider {
    public QMDFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, QMD.MOD_ID, existingFileHelper);
    }

    public static final TagKey<Fluid> LIQUID_HELIUM_TAG = FluidTags.create(ResourceLocation.parse("c:liquid_helium"));
    public static final TagKey<Fluid> LIQUID_NITROGEN_TAG = FluidTags.create(ResourceLocation.parse("c:liquid_nitrogen"));
    public static final TagKey<Fluid> LIQUID_NEON_TAG = FluidTags.create(ResourceLocation.parse("c:liquid_neon"));
    public static final TagKey<Fluid> LIQUID_ARGON_TAG = FluidTags.create(ResourceLocation.parse("c:liquid_argon"));

    public static final TagKey<Fluid> NITRIC_ACID_TAG = FluidTags.create(ResourceLocation.parse("c:nitric_acid"));
    public static final TagKey<Fluid> HYDROCHLORIC_ACID_TAG = FluidTags.create(ResourceLocation.parse("c:hydrochloric_acid"));

    public static final TagKey<Fluid> MERCURY_TAG = FluidTags.create(ResourceLocation.parse("c:mercury"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(NITRIC_ACID_TAG).add(QMD_FLUIDS.get("nitric_acid").still.get());
        tag(HYDROCHLORIC_ACID_TAG).add(QMD_FLUIDS.get("hydrochloric_acid").still.get());

        tag(MERCURY_TAG).add(QMD_FLUIDS.get("mercury").still.get());

        tag(ARGON_TAG).add(QMD_FLUIDS.get("argon").still.get());
        tag(NEON_TAG).add(QMD_FLUIDS.get("neon").still.get());
    }
}
