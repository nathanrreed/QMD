package lach_01298.qmd.recipe;

import lach_01298.qmd.QMD;
import lach_01298.qmd.recipe.types.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class RecipeTypeRegistration {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, QMD.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<LiquefierCoolantRecipe>> LIQUEFIER_COOLANT_RECIPE_TYPE = RECIPE_TYPES.register("liquefier_coolant", RecipeType::simple);
    public static final DeferredHolder<RecipeType<?>, RecipeType<IrradiatorRecipe>> IRRADIATOR_RECIPE_TYPE = RECIPE_TYPES.register("irradiator", RecipeType::simple);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MassSpectrometerRecipe>> MASS_SPECTROMETER_RECIPE_TYPE = RECIPE_TYPES.register("mass_spectrometer", RecipeType::simple);
    public static final DeferredHolder<RecipeType<?>, RecipeType<OreLeacherRecipe>> ORE_LEACHER_RECIPE_TYPE = RECIPE_TYPES.register("ore_leacher", RecipeType::simple);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AtmosphereCollectorRecipe>> ATMOSPHERE_COLLECTOR_RECIPE_TYPE = RECIPE_TYPES.register("atmosphere_collector", RecipeType::simple);
    public static final DeferredHolder<RecipeType<?>, RecipeType<LiquidCollectorRecipe>> LIQUID_COLLECTOR_RECIPE_TYPE = RECIPE_TYPES.register("liquid_collector", RecipeType::simple);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AcceleratorCoolingRecipe>> ACCELERATOR_COOLING_RECIPE_TYPE = RECIPE_TYPES.register("accelerator_cooling", RecipeType::simple);
    public static final DeferredHolder<RecipeType<?>, RecipeType<AcceleratorSourceRecipe>> ACCELERATOR_SOURCE_RECIPE_TYPE = RECIPE_TYPES.register("accelerator_source", RecipeType::simple);

    public static void init() {
    }

    public static void register(IEventBus modEventBus) {
        RECIPE_TYPES.register(modEventBus);
    }
}