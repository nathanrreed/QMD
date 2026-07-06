package lach_01298.qmd.recipe_viewer.emi;

import com.nred.nuclearcraft.NuclearcraftNeohaul;
import com.nred.nuclearcraft.info.NCFluid;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import lach_01298.qmd.QMD;
import lach_01298.qmd.fluid.QMDFluids;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe_viewer.emi.EmiRecipeViewerImpl.EmiIrradiatorRecipe;
import lach_01298.qmd.recipe_viewer.emi.EmiRecipeViewerImpl.EmiOreLeacherRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.nred.nuclearcraft.compat.emi.ModEmiPlugin.createDataMapCategory;
import static lach_01298.qmd.block.QMDBlocks.*;
import static lach_01298.qmd.datamap.QMDDatamaps.IRRADIATOR_FUELS;
import static lach_01298.qmd.recipe.RecipeTypeRegistration.*;

@EmiEntrypoint
public class QMDEmiPlugin implements EmiPlugin {

    private static final EmiStack IRRADIATOR_WORKSTATION = EmiStack.of(irradiator);
    public static final EmiRecipeCategory EMI_IRRADIATOR_CATEGORY = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "irradiator"), IRRADIATOR_WORKSTATION);

    private static final EmiStack ORE_LEACHER_WORKSTATION = EmiStack.of(oreLeacher);
    public static final EmiRecipeCategory EMI_ORE_LEACHER_CATEGORY = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "ore_leacher"), ORE_LEACHER_WORKSTATION);

    private static final EmiStack ATMOSPHERE_COLLECTOR_WORKSTATION = EmiStack.of(atmosphereCollector);
    public static final EmiRecipeCategory EMI_ATMOSPHERE_COLLECTOR_CATEGORY = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "atmosphere_collector"), ATMOSPHERE_COLLECTOR_WORKSTATION);

    private static final EmiStack LIQUID_COLLECTOR_WORKSTATION = EmiStack.of(liquidCollector);
    public static final EmiRecipeCategory EMI_LIQUID_COLLECTOR_CATEGORY = new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "liquid_collector"), LIQUID_COLLECTOR_WORKSTATION);

    @Override
    public void register(EmiRegistry registry) {
        RecipeManager manager = registry.getRecipeManager();

        Particles.getRegisteredParticles().forEach(particle -> {
            registry.addEmiStack(new ParticleEmiStack(particle, 1));
        });

        for (NCFluid entry : QMDFluids.QMD_FLUIDS.values()) {
            registry.addEmiStack(EmiStack.of(entry.bucket));
        }

        addCategory(registry, manager, EMI_IRRADIATOR_CATEGORY, IRRADIATOR_WORKSTATION, IRRADIATOR_RECIPE_TYPE.get(), EmiIrradiatorRecipe::new);
        addCategory(registry, manager, EMI_ORE_LEACHER_CATEGORY, ORE_LEACHER_WORKSTATION, ORE_LEACHER_RECIPE_TYPE.get(), EmiOreLeacherRecipe::new);

        addCategory(registry, manager, EMI_ATMOSPHERE_COLLECTOR_CATEGORY, ATMOSPHERE_COLLECTOR_WORKSTATION, ATMOSPHERE_COLLECTOR_RECIPE_TYPE.get(), EmiAtmosphereCollectorRecipe::new);
        addCategory(registry, manager, EMI_LIQUID_COLLECTOR_CATEGORY, LIQUID_COLLECTOR_WORKSTATION, LIQUID_COLLECTOR_RECIPE_TYPE.get(), EmiLiquidCollectorRecipe::new);
    }

    private <R> void createItemDataMapCategory(EmiRegistry registry, DataMapType<Item, R> dataMapType, ResourceLocation name, ItemLike defaultIcon, List<EmiStack> workStations, Function<Item, Component> tooltip) {
        createDataMapCategory(registry, BuiltInRegistries.ITEM.getDataMap(dataMapType).entrySet(), name, EmiStack.of(defaultIcon), workStations, (e) -> EmiStack.of(Objects.requireNonNull(BuiltInRegistries.ITEM.get(e.getKey()))), tooltip);
    }

    @Override
    public void initialize(EmiInitRegistry registry) {
    }

    private static <T extends Recipe<I>, I extends RecipeInput> void addCategory(EmiRegistry registry, RecipeManager manager, EmiRecipeCategory category, EmiStack workstation, RecipeType<T> recipeType, BiFunction<ResourceLocation, T, EmiRecipe> emiRecipeFunc) {
        addCategory(registry, manager, category, List.of(workstation), recipeType, emiRecipeFunc);
    }

    private static <T extends Recipe<I>, I extends RecipeInput> void addCategory(EmiRegistry registry, RecipeManager manager, EmiRecipeCategory category, Collection<EmiStack> workstations, RecipeType<T> recipeType, BiFunction<ResourceLocation, T, EmiRecipe> emiRecipeFunc) {
        registry.addCategory(category);
        for (EmiStack workstation : workstations) {
            registry.addWorkstation(category, workstation);
        }
        for (RecipeHolder<T> recipe : manager.getAllRecipesFor(recipeType)) {
            registry.addRecipe(emiRecipeFunc.apply(recipe.id(), recipe.value()));
        }
    }
}