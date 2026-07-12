package lach_01298.qmd.recipe;

import com.nred.nuclearcraft.handler.BasicRecipeHandler;
import com.nred.nuclearcraft.recipe.NCRecipes;
import com.nred.nuclearcraft.recipe.ProcessorRecipe;
import com.nred.nuclearcraft.util.NCUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lach_01298.qmd.QMD;
import lach_01298.qmd.recipe.types.AcceleratorCoolingRecipe;
import lach_01298.qmd.recipe.types.AcceleratorSourceRecipe;
import lach_01298.qmd.recipe.types.LiquefierCoolantRecipe;
import lach_01298.qmd.recipe.types.MassSpectrometerRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class QMDRecipes {
    private static boolean initialized = false;
    private static final Object2ObjectMap<String, QMDRecipeHandler<?>> RECIPE_HANDLER_MAP = new Object2ObjectOpenHashMap<>();

    public static AcceleratorSourceRecipes accelerator_source;
    public static AcceleratorCoolingRecipes accelerator_cooling;
    public static MassSpectrometerRecipes mass_spectrometer;

    //    public static LiquefierRecipes liquefier; TODO
    public static LiquefierCoolantRecipes liquefier_coolant;

    //    public static TargetChamberRecipes target_chamber;
//    public static DecayChamberRecipes decay_chamber;
//    public static BeamDumpRecipes beam_dump;
//    public static CollisionChamberRecipes collision_chamber;
//
    public static OreLeacherRecipes ore_leacher;
    public static IrradiatorRecipes irradiator;
//    public static IrradiatorFuel irradiator_fuel;
//
//    public static NeutralContainmentRecipes neutral_containment;
//    public static CellFillingRecipes cell_filling;
//    public static NucleosynthesisChamberRecipes nucleosynthesis_chamber;
//    public static VacuumChamberHeaterRecipes vacuum_chamber_heating;

    public static void putHandler(QMDRecipeHandler<?> handler) {
        RECIPE_HANDLER_MAP.put(handler.getName(), handler);
    }

    public static <T extends QMDRecipeHandler<?>> T getHandler(String name) {
        return (T) RECIPE_HANDLER_MAP.get(name);
    }

    public static Collection<QMDRecipeHandler<?>> getHandlers() {
        return RECIPE_HANDLER_MAP.values();
    }

    public static List<? extends QMDRecipe> getRecipeList(String name, Level level) {
        return getHandler(name).getRecipeList(NCUtil.getRecipeManager(level));
    }

    public static List<Set<ResourceLocation>> getValidFluids(String name, Level level) {
        return getHandler(name).getValidFluids(NCUtil.getRecipeManager(level));
    }

    public static void registerRecipes() {
        if (initialized)
            return;

        putHandler(new AcceleratorSourceRecipes());
        putHandler(new AcceleratorCoolingRecipes());
        putHandler(new MassSpectrometerRecipes());

//        putHandler(new LiquefierRecipes());
        NCRecipes.putHandler(new LiquefierCoolantRecipes());

//        putHandler(new TargetChamberRecipes());
//        putHandler(new DecayChamberRecipes());
//        putHandler(new BeamDumpRecipes());
//        putHandler(new CollisionChamberRecipes());
//
        NCRecipes.putHandler(new OreLeacherRecipes());
        NCRecipes.putHandler(new IrradiatorRecipes());
//        putHandler(new IrradiatorFuel());
//
//        putHandler(new NeutralContainmentRecipes());
//        putHandler(new CellFillingRecipes());
//        putHandler(new NucleosynthesisChamberRecipes());
//        putHandler(new VacuumChamberHeaterRecipes());

        registerShortcuts();
//        addRecipes();

        initialized = true;
    }

    public static void registerShortcuts() {
        accelerator_source = getHandler("accelerator_source");
        accelerator_cooling = getHandler("accelerator_cooling");
        mass_spectrometer = getHandler("mass_spectrometer");

//        liquefier = (LiquefierRecipes) getHandler("liquefier");
        liquefier_coolant = NCRecipes.getHandler("liquefier_coolant");

//        target_chamber = (TargetChamberRecipes) getHandler("target_chamber");
//        decay_chamber = (DecayChamberRecipes) getHandler("decay_chamber");
//        beam_dump = (BeamDumpRecipes) getHandler("beam_dump");
//        collision_chamber = (CollisionChamberRecipes) getHandler("collision_chamber");
//
        ore_leacher = NCRecipes.getHandler("ore_leacher");
        irradiator = NCRecipes.getHandler("irradiator");
//        irradiator_fuel = (IrradiatorFuel) getHandler("irradiator_fuel");
//
//        neutral_containment = (NeutralContainmentRecipes) getHandler("neutral_containment");
//        cell_filling = (CellFillingRecipes) getHandler("cell_filling");
//        nucleosynthesis_chamber = (NucleosynthesisChamberRecipes) getHandler("nucleosynthesis_chamber");
//        vacuum_chamber_heating = (VacuumChamberHeaterRecipes) getHandler("vacuum_chamber_heating");
    }


    public static void init(RecipeManager recipeManager) {
        for (QMDRecipeHandler<?> handler : getHandlers()) {
            handler.init(recipeManager);
        }
    }

    public static void postInit(RecipeManager recipeManager) {
        for (QMDRecipeHandler<?> handler : getHandlers()) {
            handler.postInit(recipeManager);
        }
    }

    //    public static void refreshRecipeCaches() { TODO
//        for (QMDRecipeHandler handler : getHandlers()) {
//            handler.refreshCache();
//        }
//        for (BasicRecipeHandler handler : getBasicHandlers()) {
//            handler.refreshCache();
//        }
//    }

    public static class QMDProcessorRecipeHandler extends BasicRecipeHandler<ProcessorRecipe> {
        public QMDProcessorRecipeHandler(@NotNull String name, int itemInputSize, int fluidInputSize, int itemOutputSize, int fluidOutputSize) {
            super(name, itemInputSize, fluidInputSize, itemOutputSize, fluidOutputSize);
        }


        @Override
        public RecipeType<ProcessorRecipe> getRecipeType() {
            return (RecipeType<ProcessorRecipe>) BuiltInRegistries.RECIPE_TYPE.get(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, getName()));
        }
    }

    public static class LiquefierCoolantRecipes extends BasicRecipeHandler<LiquefierCoolantRecipe> {
        LiquefierCoolantRecipes() {
            super("liquefier_coolant", 0, 1, 0, 1);
        }
    }

    public static class IrradiatorRecipes extends QMDProcessorRecipeHandler {
        public IrradiatorRecipes() {
            super("irradiator", 1, 0, 1, 0);
        }
    }

    public static class OreLeacherRecipes extends QMDProcessorRecipeHandler {
        public OreLeacherRecipes() {
            super("ore_leacher", 1, 3, 3, 0);
        }
    }

    public static class MassSpectrometerRecipes extends QMDRecipeHandler<MassSpectrometerRecipe> {
        public MassSpectrometerRecipes() {
            super("mass_spectrometer", 1, 1, 0, 4, 4, 0);
        }
    }

    public static class AcceleratorCoolingRecipes extends QMDRecipeHandler<AcceleratorCoolingRecipe> {
        public AcceleratorCoolingRecipes() {
            super("accelerator_cooling", 0, 1, 0, 0, 1, 0);
        }
    }

    public static class AcceleratorSourceRecipes extends QMDRecipeHandler<AcceleratorSourceRecipe> {
        public AcceleratorSourceRecipes() {
            super("accelerator_source", 1, 1, 0, 0, 0, 1);
        }
    }
}