package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.datagen.recipes.processor.FuelReprocessorProvider;
import com.nred.nuclearcraft.info.NCFluid;
import com.nred.nuclearcraft.recipe.BasicRecipeBuilder;
import com.nred.nuclearcraft.recipe.ProcessorRecipeBuilder;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import com.nred.nuclearcraft.recipe.exchanger.CondenserRecipe;
import com.nred.nuclearcraft.recipe.exchanger.HeatExchangerRecipe;
import com.nred.nuclearcraft.recipe.fission.FissionHeatingRecipe;
import com.nred.nuclearcraft.recipe.fission.PebbleFissionRecipe;
import com.nred.nuclearcraft.recipe.fission.SolidFissionRecipe;
import com.nred.nuclearcraft.recipe.machine.MultiblockDistillerRecipe;
import com.nred.nuclearcraft.recipe.processor.*;
import com.nred.nuclearcraft.recipe.turbine.TurbineRecipe;
import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.enums.BlockTypes.LampType;
import lach_01298.qmd.enums.MaterialTypes.*;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nred.nuclearcraft.datagen.ModFluidTagProvider.*;
import static com.nred.nuclearcraft.datagen.ModItemTagProvider.isotopeTag;
import static com.nred.nuclearcraft.helpers.RecipeHelpers.*;
import static com.nred.nuclearcraft.info.NCFluid.sizedIngredient;
import static com.nred.nuclearcraft.registration.FluidRegistration.*;
import static com.nred.nuclearcraft.registration.ItemRegistration.*;
import static com.nred.nuclearcraft.util.FluidStackHelper.*;
import static lach_01298.qmd.block.QMDBlocks.dischargeLamps;
import static lach_01298.qmd.datagen.QMDFluidTagProvider.*;
import static lach_01298.qmd.datagen.QMDRecipeProvider.smelting;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;
import static lach_01298.qmd.item.QMDItems.*;

public class NCRecipeProvider {
    public static void buildRecipes(RecipeOutput recipeOutput) {
        // Alloy furnace
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInputs(ingotDusts("steel", 5, "chromium", 1)).addItemResult(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL), 6).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInputs(ingotDusts("niobium", 3, "tin", 1)).addItemResult(ingotAlloys.get(IngotAlloyType.NIOBIUM_TIN), 4).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInputs(ingotDusts("niobium", 1, "titanium", 1)).addItemResult(ingotAlloys.get(IngotAlloyType.NIOBIUM_TITANIUM), 2).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 2, 2).addItemInputs(ingotDusts("tungsten", 1, "graphite", 1)).addItemResult(ingotAlloys.get(IngotAlloyType.TUNGSTEN_CARBIDE), 2).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInputs(ingotDusts("osmium", 1, "iridium", 1)).addItemResult(ingotAlloys.get(IngotAlloyType.OSMIRIDIUM), 2).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInputs(ingotDusts("nickel", 1, "chromium", 1)).addItemResult(ingotAlloys.get(IngotAlloyType.NICHROME), 2).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInputs(ingotDusts("nichrome", 2, "niobium_titanium", 1)).addItemResult(ingotAlloys.get(IngotAlloyType.SUPER_ALLOY), 3).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInput(dust("zinc", 1)).addItemInput(dust("sulfur", 1)).addItemResult(chemicalDusts.get(ChemicalDustType.ZINC_SULFIDE), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInput(SizedChanceItemIngredient.of(isotopeTag("uranium/238"), 9)).addItemInput(SizedChanceItemIngredient.of(isotopeTag("uranium/235"), 1)).addItemResult(INGOT_MAP.get("uranium"), 10).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInput(SizedChanceItemIngredient.of(isotopeTag("boron/11"), 9)).addItemInput(SizedChanceItemIngredient.of(isotopeTag("boron/10"), 3)).addItemResult(INGOT_MAP.get("boron"), 12).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInput(SizedChanceItemIngredient.of(isotopeTag("lithium/7"), 9)).addItemInput(SizedChanceItemIngredient.of(isotopeTag("lithium/6"), 1)).addItemResult(INGOT_MAP.get("lithium"), 10).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInput(SizedChanceItemIngredient.of(isotopeTag("magnesium/24"), 8)).addItemInput(SizedChanceItemIngredient.of(isotopeTag("magnesium/26"), 1)).addItemResult(INGOT_MAP.get("magnesium"), 9).save(recipeOutput);

//        // Fluid Infuser TODO
//        NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.parts.get(MaterialTypes.PartType.DETECTOR_CASING)), fluidStack("liquid_hydrogen", BUCKET_VOLUME), new ItemStack(QMDBlocks.particleChamberDetector, 1, BlockTypes.DetectorType.BUBBLE_CHAMBER.getID()), 1D, 1D);
//        NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.parts.get(MaterialTypes.PartType.WIRE_CHAMBER_CASING)), fluidStack("argon", BUCKET_VOLUME), new ItemStack(QMDBlocks.particleChamberDetector, 1, BlockTypes.DetectorType.WIRE_CHAMBER.getID()), 1D, 1D);
//
//        NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.parts.get(PartType.EMPTY_COOLER)), fluidStack("water", BUCKET_VOLUME), new ItemStack(QMDBlocks.acceleratorCoolers, 1, CoolerType.WATER.getID()), 1D, 1D);
//        NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.parts.get(PartType.EMPTY_COOLER)), fluidStack("liquid_helium", BUCKET_VOLUME), new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LIQUID_HELIUM.getID()), 1D, 1D);
//        NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.parts.get(PartType.EMPTY_COOLER)), fluidStack("liquid_nitrogen", BUCKET_VOLUME), new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LIQUID_NITROGEN.getID()), 1D, 1D);
//        NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.parts.get(PartType.EMPTY_COOLER)), fluidStack("cryotheum", BUCKET_VOLUME), new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.CRYOTHEUM.getID()), 1D, 1D);
//        NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.parts.get(PartType.EMPTY_COOLER)), fluidStack("enderium", INGOT_VOLUME * 4), new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.ENDERIUM.getID()), 1D, 1D);

        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dischargeLamps.get(LampType.EMPTY), 1).addFluidInput(HYDROGEN_TAG, BUCKET_VOLUME / 4).addItemResult(dischargeLamps.get(LampType.HYDROGEN), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dischargeLamps.get(LampType.EMPTY), 1).addFluidInput(HELIUM_TAG, BUCKET_VOLUME / 4).addItemResult(dischargeLamps.get(LampType.HELIUM), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dischargeLamps.get(LampType.EMPTY), 1).addFluidInput(NITROGEN_TAG, BUCKET_VOLUME / 4).addItemResult(dischargeLamps.get(LampType.NITROGEN), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dischargeLamps.get(LampType.EMPTY), 1).addFluidInput(OXYGEN_TAG, BUCKET_VOLUME / 4).addItemResult(dischargeLamps.get(LampType.OXYGEN), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dischargeLamps.get(LampType.EMPTY), 1).addFluidInput(NEON_TAG, BUCKET_VOLUME / 4).addItemResult(dischargeLamps.get(LampType.NEON), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dischargeLamps.get(LampType.EMPTY), 1).addFluidInput(ARGON_TAG, BUCKET_VOLUME / 4).addItemResult(dischargeLamps.get(LampType.ARGON), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dischargeLamps.get(LampType.ARGON), 1).addFluidInput(SODIUM_TAG, INGOT_VOLUME).addItemResult(dischargeLamps.get(LampType.SODIUM), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dischargeLamps.get(LampType.ARGON), 1).addFluidInput(MERCURY_TAG, INGOT_VOLUME).addItemResult(dischargeLamps.get(LampType.MERCURY), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dust("strontium", 1)).addFluidInput(HYDROCHLORIC_ACID_TAG, BUCKET_VOLUME * 2).addItemResult(chemicalDusts.get(ChemicalDustType.STRONTIUM_CHLORIDE), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(ingotDust("iron", 1)).addFluidInput(HYDROFLUORIC_ACID_TAG, (int) (BUCKET_VOLUME * 1.5)).addItemResult(chemicalDusts.get(ChemicalDustType.IRON_FLUORIDE), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dust("copper", 1)).addFluidInput(OXYGEN_TAG, BUCKET_VOLUME).addItemResult(chemicalDusts.get(ChemicalDustType.COPPER_OXIDE), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dust("tungsten", 1)).addFluidInput(OXYGEN_TAG, BUCKET_VOLUME).addItemResult(chemicalDusts.get(ChemicalDustType.TUNGSTEN_OXIDE), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(dust("hafnium", 1)).addFluidInput(OXYGEN_TAG, BUCKET_VOLUME).addItemResult(chemicalDusts.get(ChemicalDustType.HAFNIUM_OXIDE), 1).save(recipeOutput);

        // Fluid Enricher
        new ProcessorRecipeBuilder(FluidEnricherRecipe.class, 1, 1).addItemInput(dust("tungsten_oxide", 1)).addFluidInput(SALT_SOLUTION_MAP.get("sodium_hydroxide_solution"), GEM_VOLUME * 2).addFluidResult(QMD_FLUIDS.get("sodium_tungstate_solution"), GEM_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidEnricherRecipe.class, 1, 1).addItemInput(dust("lead", 1)).addFluidInput(NITRIC_ACID_TAG, GEM_VOLUME * 2).addFluidResult(QMD_FLUIDS.get("lead_nitrate_solution"), GEM_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidEnricherRecipe.class, 2, 2).addItemInput(ingot("yttrium", 1)).addFluidInput(MOLTEN_MAP.get("alumina"), 120).addFluidResult(QMD_FLUIDS.get("yag"), 48).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidEnricherRecipe.class, 2, 2).addItemInput(ingot("neodymium", 1)).addFluidInput(QMD_FLUIDS.get("yag"), INGOT_BLOCK_VOLUME).addFluidResult(QMD_FLUIDS.get("nd_yag"), INGOT_BLOCK_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidEnricherRecipe.class, 1, 1).addItemInput(dust("salt", 1)).addFluidInput(Fluids.WATER, BUCKET_VOLUME).addFluidResult(QMD_FLUIDS.get("sodium_chloride_solution"), GEM_VOLUME).save(recipeOutput);

        // Chemical Reactor
        new ProcessorRecipeBuilder(ChemicalReactorRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("sodium_tungstate_solution"), GEM_VOLUME).addFluidInput(QMD_FLUIDS.get("lead_nitrate_solution"), GEM_VOLUME).addFluidResult(QMD_FLUIDS.get("lead_tungstate_solution"), GEM_VOLUME).addFluidResult(QMD_FLUIDS.get("sodium_nitrate_solution"), GEM_VOLUME).save(recipeOutput);

        new ProcessorRecipeBuilder(ChemicalReactorRecipe.class, 1, 1).addFluidInput(HYDROGEN_TAG, BUCKET_VOLUME).addFluidInput(CHLORINE_TAG, BUCKET_VOLUME).addFluidResult(HYDROCHLORIC_ACID_TAG, 2 * BUCKET_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(ChemicalReactorRecipe.class, 1, 1).addFluidInput(FluidTags.create(ResourceLocation.parse("mekanism:hydrogen_chloride")), BUCKET_VOLUME).addFluidInput(Fluids.WATER, BUCKET_VOLUME).addFluidResult(HYDROCHLORIC_ACID_TAG, BUCKET_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(ChemicalReactorRecipe.class, 1, 1).addFluidInput(NITROGEN_TAG, BUCKET_VOLUME).addFluidInput(OXYGEN_TAG, BUCKET_VOLUME).addFluidResult(NITRIC_ACID_TAG, 2 * BUCKET_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(ChemicalReactorRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("nitric_oxide"), 2 * BUCKET_VOLUME).addFluidInput(OXYGEN_TAG, BUCKET_VOLUME).addFluidResult(QMD_FLUIDS.get("nitrogen_dioxide"), 2 * BUCKET_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(ChemicalReactorRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("nitrogen_dioxide"), 3 * BUCKET_VOLUME).addFluidInput(Fluids.WATER, BUCKET_VOLUME).addFluidResult(QMD_FLUIDS.get("nitric_acid"), 2 * BUCKET_VOLUME).addFluidResult(QMD_FLUIDS.get("nitric_oxide"), BUCKET_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(ChemicalReactorRecipe.class, 1, 1).addFluidInput(SALT_SOLUTION_MAP.get("sodium_hydroxide_solution"), GEM_VOLUME).addFluidInput(NITRIC_ACID_TAG, BUCKET_VOLUME).addFluidResult(QMD_FLUIDS.get("sodium_nitrate_solution"), GEM_VOLUME).save(recipeOutput);

        // Separator
        new ProcessorRecipeBuilder(SeparatorRecipe.class, 6, 1).addItemInput(ingotDust("magnesium", 9)).addItemResult(isotopes.get(IsotopeType.MAGNESIUM_24), 8).addItemResult(isotopes.get(IsotopeType.MAGNESIUM_26), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(SeparatorRecipe.class, 6, 1).addItemInput(ingotDust("calcium", 8)).addItemResult(isotopes.get(IsotopeType.CALCIUM_48), 1).save(recipeOutput);

        // Centrifuge
        new ProcessorRecipeBuilder(CentrifugeRecipe.class, 1, 1).addFluidInput(MOLTEN_MAP.get("redstone"), REDSTONE_DUST_VOLUME).addFluidResult(QMD_FLUIDS.get("mercury"), INGOT_VOLUME).addFluidResult(MOLTEN_MAP.get("sulfur"), GEM_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(CentrifugeRecipe.class, 1, 1).addFluidInput(MOLTEN_MAP.get("coal"), COAL_DUST_VOLUME).addFluidResult(QMD_FLUIDS.get("carbon"), COAL_DUST_VOLUME).addFluidResult(MOLTEN_MAP.get("sulfur"), GEM_VOLUME / 6).save(recipeOutput);

        // Fluid Mixer
        new ProcessorRecipeBuilder(FluidMixerRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("mercury"), INGOT_VOLUME).addFluidInput(MOLTEN_MAP.get("sulfur"), GEM_VOLUME).addFluidResult(MOLTEN_MAP.get("redstone"), REDSTONE_DUST_VOLUME).save(recipeOutput);

        //Electrolyzer TODO
//        NCRecipes.electrolyzer.addRecipe(fluidStack("sodium_chloride", GEM_VOLUME), fluidStack("sodium", INGOT_VOLUME), fluidStack("chlorine", BUCKET_VOLUME / 2), new EmptyFluidIngredient(), new EmptyFluidIngredient(), 1D, 1D);
//        NCRecipes.electrolyzer.addRecipe(fluidStack("sodium_chloride_solution", 2 * GEM_VOLUME), fluidStack("hydrogen", BUCKET_VOLUME), fluidStack("chlorine", BUCKET_VOLUME), fluidStack("sodium_hydroxide_solution", GEM_VOLUME * 2), new EmptyFluidIngredient(), 0.5D, 1D);
//
//        NCRecipes.multiblock_electrolyzer.addElectrolyte("chloride_solution", fluidStack("sodium_chloride_solution", 1), 1D);
//        NCRecipes.multiblock_electrolyzer.addRecipe(emptyItemStack(), emptyItemStack(), fluidStack("sodium_chloride_solution", 2 * GEM_VOLUME), emptyFluidStack(), emptyItemStack(), emptyItemStack(), emptyItemStack(), emptyItemStack(), fluidStack("hydrogen", BUCKET_VOLUME), fluidStack("chlorine", BUCKET_VOLUME), fluidStack("sodium_hydroxide_solution", GEM_VOLUME * 2), new EmptyFluidIngredient(), 0.5D, 1D, 0D, "chloride_solution");
//
//        // Manufactory
//        NCRecipes.manufactory.addRecipe("bouleSilicon", oreStack("waferSilicon", 4), 1D, 1D);
//
//        // Melter
//        NCRecipes.melter.addRecipe("ingotMercury", fluidStack("mercury", INGOT_VOLUME), 0.5D, 0D);
//        NCRecipes.melter.addRecipe("dustIodine", fluidStack("iodine", INGOT_VOLUME));
//        NCRecipes.melter.addRecipe("dustSamarium", fluidStack("samarium", INGOT_VOLUME));
//        NCRecipes.melter.addRecipe("dustTerbium", fluidStack("terbium", INGOT_VOLUME));
//        NCRecipes.melter.addRecipe("dustErbium", fluidStack("erbium", INGOT_VOLUME));
//        NCRecipes.melter.addRecipe("dustYtterbium", fluidStack("ytterbium", INGOT_VOLUME));
//        NCRecipes.melter.addRecipe("dustMolybdenum", fluidStack("molybdenum", INGOT_VOLUME));
//        NCRecipes.melter.addRecipe("dustBismuth", fluidStack("bismuth", INGOT_VOLUME));
//        NCRecipes.melter.addRecipe("dustPolonium", fluidStack("polonium", INGOT_VOLUME));
//        NCRecipes.melter.addRecipe("dustRadium", fluidStack("radium", INGOT_VOLUME));
//
//        if (FluidRegHelper.fluidExists("brine") && QMDConfig.override_nc_recipes) {
//            List<IFluidIngredient> fluidIngredients = new ArrayList<IFluidIngredient>();
//            List<IItemIngredient> itemIngredients = new ArrayList<IItemIngredient>();
//            itemIngredients.add(tag(DUSTS, "salt", 1));
//            NCRecipes.melter.removeRecipe(NCRecipes.melter.getRecipeFromIngredients(itemIngredients, fluidIngredients));
//            itemIngredients.add(oreStack("itemSalt", 1));
//            NCRecipes.melter.removeRecipe(NCRecipes.melter.getRecipeFromIngredients(itemIngredients, fluidIngredients));
//
//            NCRecipes.melter.addRecipe("dustSalt", fluidStack("sodium_chloride", GEM_VOLUME));
//        } else if (!FluidRegHelper.fluidExists("brine")) {
//            NCRecipes.melter.addRecipe("dustSalt", fluidStack("sodium_chloride", GEM_VOLUME));
//        }
//
//        if (QMDConfig.override_nc_recipes) {
//            List<IFluidIngredient> fluidIngredients = new ArrayList<IFluidIngredient>();
//            List<IItemIngredient> itemIngredients = new ArrayList<IItemIngredient>();
//            itemIngredients.add(tag(DUSTS, "graphite", 1));
//
//            NCRecipes.melter.removeRecipe(NCRecipes.melter.getRecipeFromIngredients(itemIngredients, fluidIngredients));
//
//            itemIngredients = new ArrayList<IItemIngredient>();
//            itemIngredients.add(tag(DUSTS, "graphite", 1));
//
//            NCRecipes.melter.removeRecipe(NCRecipes.melter.getRecipeFromIngredients(itemIngredients, fluidIngredients));
//            NCRecipes.melter.addRecipe(oreStackList(Lists.newArrayList("dustGraphite", "ingotGraphite"), 1), fluidStack("carbon", COAL_DUST_VOLUME));
//
//            itemIngredients = new ArrayList<IItemIngredient>();
//            itemIngredients.add(oreStack("blockGraphite", 1));
//
//            NCRecipes.melter.removeRecipe(NCRecipes.melter.getRecipeFromIngredients(itemIngredients, fluidIngredients));
//            NCRecipes.melter.addRecipe("blockGraphite", fluidStack("carbon", COAL_BLOCK_VOLUME));
//
//        }
//        NCRecipes.melter.addRecipe(oreStackList(Lists.newArrayList("dustCharcoal", "charcoal"), 1), fluidStack("carbon", COAL_DUST_VOLUME));
//
//
//        // Ingot Former
//        NCRecipes.ingot_former.addRecipe(fluidStack("carbon", COAL_DUST_VOLUME), "ingotGraphite");
//
//        if (QMDConfig.override_nc_recipes) {
//            List<IFluidIngredient> fluidIngredients = new ArrayList<IFluidIngredient>();
//            List<IItemIngredient> itemIngredients = new ArrayList<IItemIngredient>();
//
//            fluidIngredients.add(fluidStack("coal", COAL_DUST_VOLUME));
//
//            NCRecipes.ingot_former.removeRecipe(NCRecipes.ingot_former.getRecipeFromIngredients(itemIngredients, fluidIngredients));
//            NCRecipes.ingot_former.addRecipe(fluidStack("coal", COAL_DUST_VOLUME), "coal", 0.5D, 1D);
//        }
//
//        // Crystallizer
//        NCRecipes.crystallizer.addRecipe(fluidStack("silicon", INGOT_BLOCK_VOLUME), "bouleSilicon", 2D, 2D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("lead_tungstate_solution", GEM_VOLUME), new ItemStack(QMDItems.parts.get(MaterialTypes.PartType.SCINTILLATOR_PWO)), 1D, 1D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("sodium_nitrate_solution", GEM_VOLUME), "dustSodiumNitrate", 1D, 1D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("sodium_chloride_solution", GEM_VOLUME), "dustSalt", 1D, 1D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("nd_yag", INGOT_VOLUME * 3), "rodNdYAG", 2D, 2D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("salt_water", BUCKET_VOLUME * 10), "dustSalt", 2D, 4D);
//
//        NCRecipes.crystallizer.addRecipe(fluidStack("iodine", INGOT_VOLUME), "dustIodine", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("samarium", INGOT_VOLUME), "dustSamarium", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("terbium", INGOT_VOLUME), "dustTerbium", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("erbium", INGOT_VOLUME), "dustErbium", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("ytterbium", INGOT_VOLUME), "dustYtterbium", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("molybdenum", INGOT_VOLUME), "dustMolybdenum", 0.25D, 0D);
//
//        NCRecipes.crystallizer.addRecipe(fluidStack("bismuth", INGOT_VOLUME), "dustbismuth", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("radium", INGOT_VOLUME), "dustRadium", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("polonium", INGOT_VOLUME), "dustPolonium", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("europium_155", INGOT_VOLUME), "dustEuropium155", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("ruthenium_106", INGOT_VOLUME), "dustRuthenium106", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("strontium_90", INGOT_VOLUME), "dustStrontium90", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("promethium_147", INGOT_VOLUME), "dustPromethium147", 0.25D, 0D);
//        NCRecipes.crystallizer.addRecipe(fluidStack("caesium_137", INGOT_VOLUME), "dustCaesium137", 0.25D, 0D);
//
//        // Pressurizer
//        NCRecipes.pressurizer.addRecipe((tag(DUSTS, "strontium_90", 9)), "blockStrontium90", 1D, 2D);
//
//        // SuperCooler
//        if (QMDConfig.override_nc_recipes) {
//            List<IItemIngredient> emptyitems = new ArrayList<IItemIngredient>();
//            List<IFluidIngredient> helium = new ArrayList<IFluidIngredient>();
//            helium.add(fluidStack("helium", BUCKET_VOLUME * 8));
//            List<IFluidIngredient> nitrogen = new ArrayList<IFluidIngredient>();
//            nitrogen.add(fluidStack("nitrogen", BUCKET_VOLUME * 8));
//
//            NCRecipes.supercooler.removeRecipe(NCRecipes.supercooler.getRecipeFromIngredients(emptyitems, helium));
//            NCRecipes.supercooler.removeRecipe(NCRecipes.supercooler.getRecipeFromIngredients(emptyitems, nitrogen));
//
//            NCRecipes.supercooler.addRecipe(fluidStack("helium", 64), fluidStack("liquid_helium", 1), 2D / 150D, 5D);
//            NCRecipes.supercooler.addRecipe(fluidStack("nitrogen", 64), fluidStack("liquid_nitrogen", 1), 2D / 150D, 2.5D);
//
//            NCRecipes.supercooler.addRecipe(fluidStack("hydrogen", 64), fluidStack("liquid_hydrogen", 1), 2D / 150D, 3.75D);
//            NCRecipes.supercooler.addRecipe(fluidStack("neon", 64), fluidStack("liquid_neon", 1), 2D / 150D, 3.75D);
//            NCRecipes.supercooler.addRecipe(fluidStack("argon", 64), fluidStack("liquid_argon", 1), 2D / 150D, 2.5D);
//            NCRecipes.supercooler.addRecipe(fluidStack("oxygen", 64), fluidStack("liquid_oxygen", 1), 2D / 150D, 2.5D);
//            NCRecipes.supercooler.addRecipe(fluidStack("compressed_air", 64), fluidStack("liquid_air", 1), 2D / 150D, 1D);
//        } else {
//            NCRecipes.supercooler.addRecipe(fluidStack("hydrogen", BUCKET_VOLUME * 8), fluidStack("liquid_hydrogen", 25), 1D, 1D);
//            NCRecipes.supercooler.addRecipe(fluidStack("neon", BUCKET_VOLUME * 8), fluidStack("liquid_neon", 25), 1D, 1D);
//            NCRecipes.supercooler.addRecipe(fluidStack("argon", BUCKET_VOLUME * 8), fluidStack("liquid_argon", 25), 0.5D, 0.5D);
//            NCRecipes.supercooler.addRecipe(fluidStack("oxygen", BUCKET_VOLUME * 8), fluidStack("liquid_oxygen", 25), 0.5D, 0.5D);
//            NCRecipes.supercooler.addRecipe(fluidStack("compressed_air", BUCKET_VOLUME * 8), fluidStack("liquid_air", 25), 0.5D, 0.5D);
//        }
//
//        // Decay Hastener
//        if (QMDConfig.override_nc_recipes) {
//            List<IItemIngredient> itemIngredients = new ArrayList<IItemIngredient>();
//            itemIngredients.add(new ItemIngredient(new ItemStack(NCItems.plutonium, 1, MetaEnums.PlutoniumType._238.getID())));
//            List<IFluidIngredient> fluidIngredients = new ArrayList<IFluidIngredient>();
//            NCRecipes.decay_hastener.removeRecipe(NCRecipes.decay_hastener.getRecipeFromIngredients(itemIngredients, fluidIngredients));
//
//            NCRecipes.decay_hastener.addDecayRecipes("Plutonium238", "Uranium234", RadSources.PLUTONIUM_238);
//        }
//
//        NCRecipes.decay_hastener.addDecayRecipes("Beryllium7", "Lithium7", QMDRadSources.BERYLLIUM_7);
//        NCRecipes.decay_hastener.addDecayRecipes("Protactinium231", "Lead", QMDRadSources.PROTACTINIUM_231);
//
//        NCRecipes.decay_hastener.addDecayRecipes("Uranium234", "Radium", QMDRadSources.URANIUM_234);
//        NCRecipes.decay_hastener.addRecipe(isotopeTag("cobalt/60", "dustNickel", getDecayHastenerTimeMultipler(QMDRadSources.COBALT_60), 1d, QMDRadSources.COBALT_60);
//        NCRecipes.decay_hastener.addRecipe(isotopeTag("iridium/192", "dustPlatinum", getDecayHastenerTimeMultipler(QMDRadSources.IRIDIUM_192), 1d, QMDRadSources.IRIDIUM_192);
//
//        // Assembler
//        NCRecipes.assembler.addRecipe(tag(DUSTS, "bscco", 3), tag(INGOTS, "silver", 6), new EmptyItemIngredient(), new EmptyItemIngredient(), oreStack("wireBSCCO", 6), 1D, 1D);
//        NCRecipes.assembler.addRecipe(tag(DUSTS, "ssfaf", 3), tag(INGOTS, "silver", 6), new EmptyItemIngredient(), new EmptyItemIngredient(), oreStack("wireSSFAF", 6), 1D, 1D);
//        NCRecipes.assembler.addRecipe(tag(DUSTS, "ybco", 3), tag(INGOTS, "silver", 6), new EmptyItemIngredient(), new EmptyItemIngredient(), oreStack("wireYBCO", 6), 1D, 1D);
//        NCRecipes.assembler.addRecipe(tag(INGOTS, "tungsten", 4), tag(INGOTS, "gold", 2), new EmptyItemIngredient(), new EmptyItemIngredient(), oreStack("wireGoldTungsten", 6), 1D, 1D);
//        NCRecipes.assembler.addRecipe(tag(DUSTS, "bismuth", 2), tag(DUSTS, "strontium", 2), tag(DUSTS, "calcium", 2), tag(DUSTS, "copper_oxide", 3), tag(DUSTS, "bscco", 3), 1D, 1D);
//        NCRecipes.assembler.addRecipe(tag(DUSTS, "samarium", 1), tag(DUSTS, "strontium", 1), tag(DUSTS, "iron_fluoride", 2), tag(DUSTS, "arsenic", 2), tag(DUSTS, "ssfaf", 6), 1D, 1D);
//        NCRecipes.assembler.addRecipe(tag(DUSTS, "yttrium", 1), tag(DUSTS, "barium", 2), tag(DUSTS, "copper_oxide", 3), new EmptyItemIngredient(), tag(DUSTS, "ybco", 3), 1D, 1D);
//
//        NCRecipes.assembler.addRecipe(oreStackList(PLASTIC_TYPES, 2), oreStack("dyeBlue", 1), new EmptyItemIngredient(), new EmptyItemIngredient(), new ItemStack(QMDItems.parts.get(MaterialTypes.PartType.SCINTILLATOR_PLASTIC)), 1D, 1D);
//        NCRecipes.assembler.addRecipe("siliconNDoped", tag(DUSTS, "redstone", 4), "ingotGold", "ingotSilver", "processorBasic", 1D, 1D);
//        NCRecipes.assembler.addRecipe("processorBasic", tag(DUSTS, "redstone", 4), "dustHafniumOxide", "siliconPDoped", "processorAdvanced", 1D, 1D);
//        NCRecipes.assembler.addRecipe("processorAdvanced", oreStack("wireBSCCO", 4), "dustHafniumOxide", "ingotPlatinum", "processorElite", 1D, 1D);
//        NCRecipes.assembler.addRecipe(tag(INGOTS, "tungsten", 2), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), IItemParticleAmount.fullItem(new ItemStack(QMDItems.sources.get(MaterialTypes.SourceType.TUNGSTEN_FILAMENT))), 1D, 1D);
//        NCRecipes.assembler.addRecipe(tag(INGOTS, "ferroboron", 2), "ingotNeodymium", new EmptyItemIngredient(), new EmptyItemIngredient(), "magnetNeodymium", 1D, 1D);
//        NCRecipes.assembler.addRecipe("dustPotassium", "dustIodine", new ItemStack(Items.SUGAR, 4), "bioplastic", new ItemStack(QMDItems.potassiumIodineTablet, 4), 1D, 1D);
//        NCRecipes.assembler.addRecipe(tag(INGOTS, "cobalt", 2), "dustSamarium", new EmptyItemIngredient(), new EmptyItemIngredient(), "magnetSamariumCobalt", 1D, 1D);
//
//        //Fission Irradiator
//        NCRecipes.fission_irradiator.addRecipe("waferSilicon", "siliconNDoped", 120000, 0d, 0);
//        NCRecipes.fission_irradiator.addRecipe("ingotUranium234", "ingotUranium235", 1920000, 0d, QMDRadSources.URANIUM_234);
//        NCRecipes.fission_irradiator.addRecipe("dustProtactinium231", "dustProtactinium233", 3840000, 0d, QMDRadSources.PROTACTINIUM_231);
//        NCRecipes.fission_irradiator.addRecipe("ingotCobalt", isotopeTag("cobalt/60", 1920000, 0d, 0);
//        //NCRecipes.fission_irradiator.addRecipe(FluidUtil.getFilledBucket(fluidStack("deuterium", 1000).getStack()), FluidUtil.getFilledBucket(fluidStack("tritium", 1000).getStack()),60000,0d,0); //1920000
//
//        //fuel reprocessor
//        NCRecipes.fuel_reprocessor.addRecipe("wasteFissionLight", chanceOreStack("dustStrontium", 1, 20), chanceOreStack("dustStrontium90", 1, 5), chanceOreStack("dustYttrium", 1, 5), chanceOreStack("dustZirconium", 1, 20), chanceOreStack("dustNiobium", 1, 5), chanceOreStack("dustMolybdenum", 1, 30), chanceOreStack("dustRuthenium106", 1, 5), chanceOreStack("dustSilver", 1, 10));
//        NCRecipes.fuel_reprocessor.addRecipe("wasteFissionHeavy", chanceOreStack("dustNiobium", 1, 4), chanceOreStack("dustMolybdenum", 1, 21), chanceOreStack("dustRuthenium106", 1, 4), chanceOreStack("dustSilver", 1, 7), chanceOreStack("dustTin", 1, 35), chanceOreStack("dustIodine", 1, 7), chanceOreStack("dustCaesium137", 1, 4), chanceOreStack("dustNeodymium", 1, 18));
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationCalifornium", chanceOreStack("dustThorium", 1, 26), chanceOreStack("dustProtactinium231", 1, 13), chanceOreStack("dustRadium", 1, 12), chanceOreStack("dustPolonium", 1, 9), chanceOreStack("dustBismuth", 1, 20), chanceOreStack("dustLead", 1, 20), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationBerkelium", chanceOreStack("dustRadium", 1, 9), chanceOreStack("dustPolonium", 1, 15), chanceOreStack("dustBismuth", 1, 40), chanceOreStack("dustLead", 1, 35), chanceOreStack("ingotMercury", 1, 1), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationCurium", chanceOreStack("dustRadium", 1, 13), chanceOreStack("dustPolonium", 1, 17), chanceOreStack("dustBismuth", 1, 16), chanceOreStack("dustLead", 1, 50), chanceOreStack("ingotMercury", 1, 4), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationAmericium", chanceOreStack("dustPolonium", 1, 22), chanceOreStack("dustBismuth", 1, 15), chanceOreStack("dustLead", 1, 55), chanceOreStack("ingotMercury", 1, 5), chanceOreStack("dustGold", 1, 1), chanceOreStack("dustPlatinum", 1, 2), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationPlutonium", chanceOreStack("dustPolonium", 1, 22), chanceOreStack("dustBismuth", 1, 14), chanceOreStack("dustLead", 1, 55), chanceOreStack("ingotMercury", 1, 5), chanceOreStack("dustGold", 1, 1), chanceOreStack("dustPlatinum", 1, 3), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationNeptunium", chanceOreStack("dustPolonium", 1, 36), chanceOreStack("dustBismuth", 1, 17), chanceOreStack("dustLead", 1, 34), chanceOreStack("ingotMercury", 1, 7), chanceOreStack("dustGold", 1, 2), chanceOreStack("dustPlatinum", 1, 4), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationUranium", chanceOreStack("dustPolonium", 1, 21), chanceOreStack("dustBismuth", 1, 12), chanceOreStack("dustLead", 1, 55), chanceOreStack("ingotMercury", 1, 7), chanceOreStack("dustGold", 1, 1), chanceOreStack("dustPlatinum", 1, 4), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationThorium", chanceOreStack("dustPolonium", 1, 10), chanceOreStack("dustBismuth", 1, 7), chanceOreStack("dustLead", 1, 62), chanceOreStack("ingotMercury", 1, 11), chanceOreStack("dustGold", 1, 2), chanceOreStack("dustPlatinum", 1, 8), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationProtactinium", chanceOreStack("dustPolonium", 1, 36), chanceOreStack("dustBismuth", 1, 6), chanceOreStack("dustLead", 1, 39), chanceOreStack("ingotMercury", 1, 10), chanceOreStack("dustGold", 1, 2), chanceOreStack("dustPlatinum", 1, 7), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationRadium", chanceOreStack("dustLead", 1, 58), chanceOreStack("ingotMercury", 1, 18), chanceOreStack("dustGold", 1, 3), chanceOreStack("dustPlatinum", 1, 10), chanceOreStack("dustIridium", 1, 6), chanceOreStack("dustOsmium", 1, 5), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationPolonium", chanceOreStack("dustIridium", 1, 52), chanceOreStack("dustOsmium", 1, 21), chanceOreStack("dustTungsten", 1, 12), chanceOreStack("dustHafnium", 1, 10), chanceOreStack("dustYtterbium", 1, 5), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationBismuth", chanceOreStack("dustIridium", 1, 42), chanceOreStack("dustOsmium", 1, 27), chanceOreStack("dustTungsten", 1, 14), chanceOreStack("dustHafnium", 1, 11), chanceOreStack("dustYtterbium", 1, 6), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationLead", chanceOreStack("dustIridium", 1, 27), chanceOreStack("dustOsmium", 1, 35), chanceOreStack("dustTungsten", 1, 15), chanceOreStack("dustHafnium", 1, 12), chanceOreStack("dustYtterbium", 1, 8), chanceOreStack("dustErbium", 1, 3), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationMercury", chanceOreStack("dustOsmium", 1, 42), chanceOreStack("dustTungsten", 1, 27), chanceOreStack("dustHafnium", 1, 16), chanceOreStack("dustYtterbium", 1, 10), chanceOreStack("dustErbium", 1, 3), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationGold", chanceOreStack("dustHafnium", 1, 72), chanceOreStack("dustYtterbium", 1, 20), chanceOreStack("dustErbium", 1, 8), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationPlatinum", chanceOreStack("dustTungsten", 1, 30), chanceOreStack("dustHafnium", 1, 44), chanceOreStack("dustYtterbium", 1, 17), chanceOreStack("dustErbium", 1, 7), chanceOreStack("dustTerbium", 1, 2), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationIridium", chanceOreStack("dustHafnium", 1, 59), chanceOreStack("dustYtterbium", 1, 29), chanceOreStack("dustErbium", 1, 10), chanceOreStack("dustTerbium", 1, 2), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationOsmium", chanceOreStack("dustHafnium", 1, 48), chanceOreStack("dustYtterbium", 1, 35), chanceOreStack("dustErbium", 1, 11), chanceOreStack("dustTerbium", 1, 3), chanceOreStack("dustEuropium155", 1, 3), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationTungsten", chanceOreStack("dustYtterbium", 1, 50), chanceOreStack("dustErbium", 1, 26), chanceOreStack("dustTerbium", 1, 6), chanceOreStack("dustEuropium155", 1, 5), chanceOreStack("dustSamarium", 1, 7), chanceOreStack("dustNeodymium", 1, 6), new EmptyItemIngredient(), new EmptyItemIngredient());
//        NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationHafnium", chanceOreStack("dustErbium", 1, 32), chanceOreStack("dustTerbium", 1, 16), chanceOreStack("dustEuropium155", 1, 11), chanceOreStack("dustSamarium", 1, 13), chanceOreStack("dustNeodymium", 1, 23), chanceOreStack("dustPromethium147", 1, 5), new EmptyItemIngredient(), new EmptyItemIngredient());
//
        // Fission fuel recipes
        addFissionFuelRecipes(recipeOutput);

        // Fission Heating
        new BasicRecipeBuilder<>(new FissionHeatingRecipe(sizedIngredient(QMD_FLUIDS.get("mercury"), 1), sizedIngredient(QMD_FLUIDS.get("high_pressure_mercury"), 2), FissionHeatingRecipe.RecipeHeatingType.OTHER, 512)).save(recipeOutput);
        new BasicRecipeBuilder<>(new FissionHeatingRecipe(sizedIngredient(QMD_FLUIDS.get("hot_mercury"), 1), sizedIngredient(QMD_FLUIDS.get("high_pressure_mercury"), 2), FissionHeatingRecipe.RecipeHeatingType.OTHER, 256)).save(recipeOutput);

        // Turbine
        new BasicRecipeBuilder<>(new TurbineRecipe(NCFluid.sizedIngredient(QMD_FLUIDS.get("high_pressure_mercury"), 1), NCFluid.sizedIngredient(QMD_FLUIDS.get("exhaust_mercury"), 3), 256.0, 3.0, 1.0, "cloud", 5D / 116D)).save(recipeOutput);

        //Heat Exchanger
        new BasicRecipeBuilder<>(new HeatExchangerRecipe(NCFluid.sizedIngredient(QMD_FLUIDS.get("exhaust_mercury"), 6), NCFluid.sizedIngredient(QMD_FLUIDS.get("hot_mercury"), 1), 128D, HeatExchangerRecipe.RecipeHeatingType.OTHER, 700, 700, false, 0, 0.0)).save(recipeOutput);

        // Condenser
        new BasicRecipeBuilder<>(new CondenserRecipe(NCFluid.sizedIngredient(QMD_FLUIDS.get("exhaust_mercury"), 6), NCFluid.sizedIngredient(QMD_FLUIDS.get("hot_mercury"), 1), 128D, 700, 700, 0, 0.0D)).save(recipeOutput);
        new BasicRecipeBuilder<>(new CondenserRecipe(NCFluid.sizedIngredient(QMD_FLUIDS.get("hot_mercury"), 1), NCFluid.sizedIngredient(QMD_FLUIDS.get("mercury"), 1), 256D, 700, 300, 0, 0.0D)).save(recipeOutput);
        new BasicRecipeBuilder<>(new CondenserRecipe(NCFluid.sizedIngredient(CUSTOM_FLUID_MAP.get("condensate_water"), 1), SizedChanceFluidIngredient.of(Fluids.WATER, 1), 32D, 350, 300, 0, 0.0D)).save(recipeOutput);

        // Distiller
        new BasicRecipeBuilder<>(new MultiblockDistillerRecipe(List.of(NCFluid.sizedIngredient(QMD_FLUIDS.get("salt_water"), 10 * BUCKET_VOLUME)), List.of(NCFluid.sizedIngredient(QMD_FLUIDS.get("sodium_chloride_solution"), GEM_VOLUME), SizedChanceFluidIngredient.of(Fluids.WATER, BUCKET_VOLUME * 9)), 1.0, 1.0)).save(recipeOutput);
        new BasicRecipeBuilder<>(new MultiblockDistillerRecipe(List.of(NCFluid.sizedIngredient(QMD_FLUIDS.get("liquid_air"), BUCKET_VOLUME)), List.of(
                NCFluid.sizedIngredient(GAS_MAP.get("nitrogen"), 38400),
                NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), 12800),
                NCFluid.sizedIngredient(QMD_FLUIDS.get("argon"), 6400),
                NCFluid.sizedIngredient(QMD_FLUIDS.get("neon"), 5120),
                NCFluid.sizedIngredient(GAS_MAP.get("helium"), 1280)
        ), 1.0, 1.0)).save(recipeOutput);
    }

    private static void addFissionFuelRecipes(RecipeOutput recipeOutput) {
        new BasicRecipeBuilder<>(new PebbleFissionRecipe(fuel_copernicium.get(CoperniciumFuelType.MIX_291_TR), depleted_fuel_copernicium.get(CoperniciumDepletedFuelType.MIX_291_TR), 10000, 2000, 5.0D, 20, 10, 0.04, true, QMDRadSources.MIX_291)).save(recipeOutput);
        new BasicRecipeBuilder<>(new SolidFissionRecipe(fuel_copernicium.get(CoperniciumFuelType.MIX_291_OX), depleted_fuel_copernicium.get(CoperniciumDepletedFuelType.MIX_291_OX), 10000, 2000, 5.0D, 25, 0.11D, true, QMDRadSources.MIX_291)).save(recipeOutput);
        new BasicRecipeBuilder<>(new SolidFissionRecipe(fuel_copernicium.get(CoperniciumFuelType.MIX_291_NI), depleted_fuel_copernicium.get(CoperniciumDepletedFuelType.MIX_291_NI), 12004, 1666, 5.0D, 25, 0.11D, true, QMDRadSources.MIX_291)).save(recipeOutput);
        new BasicRecipeBuilder<>(new SolidFissionRecipe(fuel_copernicium.get(CoperniciumFuelType.MIX_291_ZA), depleted_fuel_copernicium.get(CoperniciumDepletedFuelType.MIX_291_ZA), 9001, 2222, 5.0D, 20, 0.11D, true, QMDRadSources.MIX_291)).save(recipeOutput);

        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInput(copernicium.get(CoperniciumType._291), 1).addItemInput(ingotDust("zirconium", 1)).addItemResult(copernicium.get(CoperniciumType._291_ZA), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInput(copernicium.get(CoperniciumType._291), 1).addItemInput(ingotDust("graphite", 1)).addItemResult(copernicium.get(CoperniciumType._291_C), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInput(pellet_copernicium.get(CoperniciumPelletType.MIX_291), 1).addItemInput(ingotDust("zirconium", 1)).addItemResult(fuel_copernicium.get(CoperniciumFuelType.MIX_291_ZA), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(AlloyFurnaceRecipe.class, 1, 1).addItemInput(pellet_copernicium.get(CoperniciumPelletType.MIX_291), 1).addItemInput(ingotDust("graphite", 1)).addItemResult(pellet_copernicium.get(CoperniciumPelletType.MIX_291_C), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(copernicium.get(CoperniciumType._291), 1).addFluidInput(OXYGEN_TAG, BUCKET_VOLUME).addItemResult(copernicium.get(CoperniciumType._291_OX), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(copernicium.get(CoperniciumType._291), 1).addFluidInput(NITROGEN_TAG, BUCKET_VOLUME).addItemResult(copernicium.get(CoperniciumType._291_NI), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(pellet_copernicium.get(CoperniciumPelletType.MIX_291), 1).addFluidInput(OXYGEN_TAG, BUCKET_VOLUME).addItemResult(fuel_copernicium.get(CoperniciumFuelType.MIX_291_OX), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(pellet_copernicium.get(CoperniciumPelletType.MIX_291), 1).addFluidInput(NITROGEN_TAG, BUCKET_VOLUME).addItemResult(fuel_copernicium.get(CoperniciumFuelType.MIX_291_NI), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(pellet_copernicium.get(CoperniciumPelletType.MIX_291_C), 9).addItemInput(tag(Tags.Items.DUSTS, "graphite"), 1).addItemInput(PART_MAP.get("pyrolytic_carbon"), 1).addItemInput(ALLOY_MAP.get("silicon_carbide"), 1).addItemResult(fuel_copernicium.get(CoperniciumFuelType.MIX_291_TR), 9).save(recipeOutput);

        FuelReprocessorProvider.addReprocessingRecipes(recipeOutput, "mix_291", depleted_fuel_copernicium.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().getSerializedName(), Map.Entry::getValue)), "Americium243", 4, "Curium243", 2, "Curium245", 1, "Berkelium247", 1, "ruthenium_106", "europium_155", 0.5D, 60);

        new ProcessorRecipeBuilder(SeparatorRecipe.class, 1, 1).addItemInput(copernicium.get(CoperniciumType._291_C), 1).addItemResult(copernicium.get(CoperniciumType._291), 1).addItemResult(DUST_MAP.get("graphite"), 1).save(recipeOutput, "copernicium_from_c");
        new ProcessorRecipeBuilder(SeparatorRecipe.class, 1, 1).addItemInput(copernicium.get(CoperniciumType._291_ZA), 1).addItemResult(copernicium.get(CoperniciumType._291), 1).addItemResult(DUST_MAP.get("zirconium"), 1).save(recipeOutput, "copernicium_from_za");

        new ProcessorRecipeBuilder(SeparatorRecipe.class, 1, 1).addItemInput(pellet_copernicium.get(CoperniciumPelletType.MIX_291_C), 1).addItemResult(pellet_copernicium.get(CoperniciumPelletType.MIX_291), 1).addItemResult(DUST_MAP.get("graphite"), 1).save(recipeOutput, "mix_291_from_c");
        new ProcessorRecipeBuilder(SeparatorRecipe.class, 1, 1).addItemInput(fuel_copernicium.get(CoperniciumFuelType.MIX_291_ZA), 1).addItemResult(pellet_copernicium.get(CoperniciumPelletType.MIX_291), 1).addItemResult(DUST_MAP.get("zirconium"), 1).save(recipeOutput, "mix_291_from_za");

        new ProcessorRecipeBuilder(SeparatorRecipe.class, 1, 1).addItemInput(pellet_copernicium.get(CoperniciumPelletType.MIX_291), 1).addItemResult(copernicium.get(CoperniciumType._291), 1).addItemResult(URANIUM_MAP.get("238"), 8).save(recipeOutput);

        reductionRecipes(recipeOutput, copernicium.get(CoperniciumType._291_OX), copernicium.get(CoperniciumType._291), "copernicium");
        reductionRecipes(recipeOutput, copernicium.get(CoperniciumType._291_NI), copernicium.get(CoperniciumType._291), "copernicium");
        reductionRecipes(recipeOutput, fuel_copernicium.get(CoperniciumFuelType.MIX_291_OX), pellet_copernicium.get(CoperniciumPelletType.MIX_291), "copernicium");
        reductionRecipes(recipeOutput, fuel_copernicium.get(CoperniciumFuelType.MIX_291_NI), pellet_copernicium.get(CoperniciumPelletType.MIX_291), "copernicium");
    }

    public static void reductionRecipes(RecipeOutput recipeOutput, ItemLike isotope, ItemLike isotope_result, String group) {
        smelting(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, Ingredient.of(isotope), isotope_result, 0, 200, group, "from_smelting");
        smelting(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, Ingredient.of(isotope), isotope_result, 0, 200 / 2, group, "from_blasting");
    }
}