package lach_01298.qmd.recipe;

import com.nred.nuclearcraft.recipe.ProcessorRecipe;
import lach_01298.qmd.QMD;
import lach_01298.qmd.datagen.recipe.TungstenFilementAssemblerRecipe;
import lach_01298.qmd.recipe.types.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class RecipeSerializerRegistration {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, QMD.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, LiquefierCoolantRecipe.Serializer> LIQUEFIER_COOLANT_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("liquefier_coolant_recipe", LiquefierCoolantRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, ProcessorRecipe.Serializer> IRRADIATOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("irradiator_recipe", () -> new ProcessorRecipe.Serializer(IrradiatorRecipe.class));
    public static final DeferredHolder<RecipeSerializer<?>, ProcessorRecipe.Serializer> ORE_LEACHER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("ore_leacher_recipe", () -> new ProcessorRecipe.Serializer(OreLeacherRecipe.class));
    public static final DeferredHolder<RecipeSerializer<?>, MassSpectrometerRecipe.Serializer> MASS_SPECTROMETER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("mass_spectrometer_recipe", MassSpectrometerRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, AcceleratorCoolingRecipe.Serializer> ACCELERATOR_COOLING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("accelerator_cooling_recipe", AcceleratorCoolingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, AcceleratorSourceRecipe.Serializer> ACCELERATOR_SOURCE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("accelerator_source_recipe", AcceleratorSourceRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, AtmosphereCollectorRecipe.Serializer> ATMOSPHERE_COLLECTOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("atmosphere_collector_recipe", AtmosphereCollectorRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, LiquidCollectorRecipe.Serializer> LIQUID_COLLECTOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("liquid_collector_recipe", LiquidCollectorRecipe.Serializer::new);


    public static final DeferredHolder<RecipeSerializer<?>, BeamDumpRecipe.Serializer> BEAM_DUMP_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("beam_dump_recipe", BeamDumpRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, CollisionChamberRecipe.Serializer> COLLISION_CHAMBER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("collision_chamber_recipe", CollisionChamberRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, DecayChamberRecipe.Serializer> DECAY_CHAMBER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("decay_chamber_recipe", DecayChamberRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, TargetChamberRecipe.Serializer> TARGET_CHAMBER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("target_chamber_recipe", TargetChamberRecipe.Serializer::new);

    // Needs to have ItemStack recipe out
    public static final DeferredHolder<RecipeSerializer<?>, TungstenFilementAssemblerRecipe.Serializer> TUNGSTEN_FILAMENT_ASSEMBLER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("tungsten_filement_assembler_recipe", TungstenFilementAssemblerRecipe.Serializer::new);

    public static void init() {
    }

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}