package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.datagen.recipes.processor.FuelReprocessorProvider;
import com.nred.nuclearcraft.info.NCFluid;
import com.nred.nuclearcraft.radiation.RadSources;
import com.nred.nuclearcraft.recipe.BasicRecipeBuilder;
import com.nred.nuclearcraft.recipe.ProcessorRecipeBuilder;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import com.nred.nuclearcraft.recipe.exchanger.CondenserRecipe;
import com.nred.nuclearcraft.recipe.exchanger.HeatExchangerRecipe;
import com.nred.nuclearcraft.recipe.fission.FissionHeatingRecipe;
import com.nred.nuclearcraft.recipe.fission.FissionIrradiatorRecipe;
import com.nred.nuclearcraft.recipe.fission.PebbleFissionRecipe;
import com.nred.nuclearcraft.recipe.fission.SolidFissionRecipe;
import com.nred.nuclearcraft.recipe.machine.MultiblockDistillerRecipe;
import com.nred.nuclearcraft.recipe.machine.MultiblockElectrolyzerRecipe;
import com.nred.nuclearcraft.recipe.processor.*;
import com.nred.nuclearcraft.recipe.turbine.TurbineRecipe;
import com.nred.nuclearcraft.util.NCMath;
import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.datagen.recipe.TungstenFilementAssemblerRecipe;
import lach_01298.qmd.enums.BlockTypes.CoolerType;
import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.enums.BlockTypes.LampType;
import lach_01298.qmd.enums.MaterialTypes.*;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Items;
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
import static lach_01298.qmd.block.QMDBlocks.*;
import static lach_01298.qmd.datagen.QMDFluidTagProvider.*;
import static lach_01298.qmd.datagen.QMDFluidTagProvider.ENDERIUM_TAG;
import static lach_01298.qmd.datagen.QMDFluidTagProvider.fluidTag;
import static lach_01298.qmd.datagen.QMDRecipeProvider.smelting;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;
import static lach_01298.qmd.item.QMDItems.*;
import static net.neoforged.neoforge.common.Tags.Items.*;

public class NeohaulRecipeProvider {
    public static void buildRecipes(RecipeOutput recipeOutput) { // TODO add mod loaded condition));

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

        // Fluid Infuser
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(parts.get(PartType.DETECTOR_CASING), 1).addFluidInput(LIQUID_HYDROGEN_TAG, BUCKET_VOLUME).addItemResult(particleChamberDetectors.get(DetectorType.BUBBLE_CHAMBER), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(parts.get(PartType.WIRE_CHAMBER_CASING), 1).addFluidInput(ARGON_TAG, BUCKET_VOLUME).addItemResult(particleChamberDetectors.get(DetectorType.WIRE_CHAMBER), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(parts.get(PartType.EMPTY_COOLER), 1).addFluidInput(Fluids.WATER, BUCKET_VOLUME).addItemResult(acceleratorCoolers.get(CoolerType.WATER), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(parts.get(PartType.EMPTY_COOLER), 1).addFluidInput(LIQUID_HELIUM_TAG, BUCKET_VOLUME).addItemResult(acceleratorCoolers.get(CoolerType.LIQUID_HELIUM), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(parts.get(PartType.EMPTY_COOLER), 1).addFluidInput(LIQUID_NITROGEN_TAG, BUCKET_VOLUME).addItemResult(acceleratorCoolers.get(CoolerType.LIQUID_NITROGEN), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(parts.get(PartType.EMPTY_COOLER), 1).addFluidInput(CRYOTHEUM_TAG, BUCKET_VOLUME).addItemResult(acceleratorCoolers.get(CoolerType.CRYOTHEUM), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(FluidInfuserRecipe.class, 1, 1).addItemInput(parts.get(PartType.EMPTY_COOLER), 1).addFluidInput(ENDERIUM_TAG, BUCKET_VOLUME * 4).addItemResult(acceleratorCoolers.get(CoolerType.ENDERIUM), 1).save(recipeOutput);

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
        new ProcessorRecipeBuilder(FluidEnricherRecipe.class, 2, 2).addItemInput(ingot("yttrium", 1)).addFluidInput(fluidTag("alumina"), 120).addFluidResult(QMD_FLUIDS.get("yag"), 48).save(recipeOutput);
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
        new ProcessorRecipeBuilder(CentrifugeRecipe.class, 1, 1).addFluidInput(fluidTag("redstone"), REDSTONE_DUST_VOLUME).addFluidResult(QMD_FLUIDS.get("mercury"), INGOT_VOLUME).addFluidResult(MOLTEN_MAP.get("sulfur"), GEM_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(CentrifugeRecipe.class, 1, 1).addFluidInput(fluidTag("coal"), COAL_DUST_VOLUME).addFluidResult(QMD_FLUIDS.get("carbon"), COAL_DUST_VOLUME).addFluidResult(MOLTEN_MAP.get("sulfur"), GEM_VOLUME / 6).save(recipeOutput);

        // Fluid Mixer
        new ProcessorRecipeBuilder(FluidMixerRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("mercury"), INGOT_VOLUME).addFluidInput(fluidTag("sulfur"), GEM_VOLUME).addFluidResult(MOLTEN_MAP.get("redstone"), REDSTONE_DUST_VOLUME).save(recipeOutput);

        // Electrolyzer
        new ProcessorRecipeBuilder(ElectrolyzerRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("sodium_chloride"), GEM_VOLUME).addFluidResult(MOLTEN_MAP.get("sodium"), INGOT_VOLUME / 2).addFluidResult(QMD_FLUIDS.get("chlorine"), BUCKET_VOLUME / 2).save(recipeOutput);
        new ProcessorRecipeBuilder(ElectrolyzerRecipe.class, 0.5, 1).addFluidInput(QMD_FLUIDS.get("sodium_chloride_solution"), 2 * GEM_VOLUME).addFluidResult(GAS_MAP.get("hydrogen"), BUCKET_VOLUME).addFluidResult(QMD_FLUIDS.get("chlorine"), BUCKET_VOLUME).addFluidResult(SALT_SOLUTION_MAP.get("sodium_hydroxide_solution"), GEM_VOLUME * 2).save(recipeOutput);

        new BasicRecipeBuilder<>(new MultiblockElectrolyzerRecipe(List.of(), List.of(sizedIngredient(QMD_FLUIDS.get("sodium_chloride_solution"), 2 * GEM_VOLUME)), List.of(), List.of(sizedIngredient(GAS_MAP.get("hydrogen"), BUCKET_VOLUME), sizedIngredient(QMD_FLUIDS.get("chlorine"), BUCKET_VOLUME), sizedIngredient(SALT_SOLUTION_MAP.get("sodium_hydroxide_solution"), GEM_VOLUME * 2)), 0.5D, 1D, "chloride_solution")).save(recipeOutput);

        // Manufactory
        new ProcessorRecipeBuilder(ManufactoryRecipe.class, 1, 1).addItemInput(semiconductors.get(SemiconductorType.SILICON_BOULE), 1).addItemResult(semiconductors.get(SemiconductorType.SILICON_WAFER), 4).save(recipeOutput);

        // Melter
        new ProcessorRecipeBuilder(MelterRecipe.class, 0.5, 1).addItemInput(tag(Tags.Items.INGOTS, "mercury"), 1).addFluidResult(QMD_FLUIDS.get("mercury"), INGOT_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "iodine"), 1).addFluidResult(QMD_FLUIDS.get("iodine"), INGOT_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "samarium"), 1).addFluidResult(QMD_FLUIDS.get("samarium"), INGOT_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "terbium"), 1).addFluidResult(QMD_FLUIDS.get("terbium"), INGOT_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "erbium"), 1).addFluidResult(QMD_FLUIDS.get("erbium"), INGOT_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "ytterbium"), 1).addFluidResult(QMD_FLUIDS.get("ytterbium"), INGOT_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "molybdenum"), 1).addFluidResult(FISSION_FLUID_MAP.get("molybdenum"), INGOT_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "bismuth"), 1).addFluidResult(QMD_FLUIDS.get("bismuth"), INGOT_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "polonium"), 1).addFluidResult(QMD_FLUIDS.get("polonium"), INGOT_VOLUME).save(recipeOutput);
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "radium"), 1).addFluidResult(QMD_FLUIDS.get("radium"), INGOT_VOLUME).save(recipeOutput);

        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "salt"), 1).addFluidResult(QMD_FLUIDS.get("sodium_chloride"), GEM_VOLUME).save(recipeOutput);

        new ProcessorRecipeBuilder(MelterRecipe.class, 0.5, 1).addItemInput(tags(List.of(tag(INGOTS, "graphite"), tag(DUSTS, "graphite")), 1)).addFluidResult(QMD_FLUIDS.get("carbon"), COAL_DUST_VOLUME).save(recipeOutput, "carbon_from_graphite");
        new ProcessorRecipeBuilder(MelterRecipe.class, 4.5, 1).addItemInput(tag(STORAGE_BLOCKS, "graphite"), 1).addFluidResult(QMD_FLUIDS.get("carbon"), COAL_BLOCK_VOLUME).save(recipeOutput, "carbon_from_graphite_block");

        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(tag(DUSTS, "charcoal"), 1).addFluidResult(QMD_FLUIDS.get("carbon"), COAL_DUST_VOLUME).save(recipeOutput, "carbon_from_charcoal_dust");
        new ProcessorRecipeBuilder(MelterRecipe.class, 1, 1).addItemInput(Items.CHARCOAL, 1).addFluidResult(QMD_FLUIDS.get("carbon"), COAL_DUST_VOLUME).save(recipeOutput, "carbon_from_charcoal");


        // Ingot Former
        new ProcessorRecipeBuilder(IngotFormerRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("carbon"), INGOT_VOLUME).addItemResult(INGOT_MAP.get("graphite"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(IngotFormerRecipe.class, 0.5, 1).addFluidInput(fluidTag("coal"), COAL_DUST_VOLUME).addItemResult(Items.COAL, 1).save(recipeOutput);

        // Crystallizer
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 2, 2).addFluidInput(QMD_FLUIDS.get("silicon"), INGOT_BLOCK_VOLUME).addItemResult(semiconductors.get(SemiconductorType.SILICON_BOULE), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("lead_tungstate_solution"), GEM_VOLUME).addItemResult(parts.get(PartType.SCINTILLATOR_PWO), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("sodium_nitrate_solution"), GEM_VOLUME).addItemResult(chemicalDusts.get(ChemicalDustType.SODIUM_NITRATE), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 1, 1).addFluidInput(QMD_FLUIDS.get("sodium_chloride_solution"), GEM_VOLUME).addItemResult(tag(DUSTS, "salt"), 1).save(tagExists(recipeOutput, tag(DUSTS, "salt")), "salt_from_sodium_chloride_solution");
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 2, 2).addFluidInput(QMD_FLUIDS.get("nd_yag"), INGOT_VOLUME * 3).addItemResult(parts.get(PartType.ROD_ND_YAG), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 2, 4).addFluidInput(QMD_FLUIDS.get("salt_water"), BUCKET_VOLUME * 10).addItemResult(tag(DUSTS, "salt"), 1).save(tagExists(recipeOutput, tag(DUSTS, "salt")), "salt_from_salt_water");

        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(QMD_FLUIDS.get("iodine"), INGOT_VOLUME).addItemResult(dusts.get(DustType.IODINE), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(QMD_FLUIDS.get("samarium"), INGOT_VOLUME).addItemResult(dusts.get(DustType.SAMARIUM), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(QMD_FLUIDS.get("terbium"), INGOT_VOLUME).addItemResult(dusts.get(DustType.TERBIUM), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(QMD_FLUIDS.get("erbium"), INGOT_VOLUME).addItemResult(dusts.get(DustType.ERBIUM), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(QMD_FLUIDS.get("ytterbium"), INGOT_VOLUME).addItemResult(dusts.get(DustType.YTTERBIUM), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(FISSION_FLUID_MAP.get("molybdenum"), INGOT_VOLUME).addItemResult(FISSION_DUST_MAP.get("molybdenum"), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(QMD_FLUIDS.get("bismuth"), INGOT_VOLUME).addItemResult(FISSION_DUST_MAP.get("bismuth"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(QMD_FLUIDS.get("radium"), INGOT_VOLUME).addItemResult(FISSION_DUST_MAP.get("radium"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(QMD_FLUIDS.get("polonium"), INGOT_VOLUME).addItemResult(FISSION_DUST_MAP.get("polonium"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(FISSION_FLUID_MAP.get("europium_155"), INGOT_VOLUME).addItemResult(FISSION_DUST_MAP.get("europium_155"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(FISSION_FLUID_MAP.get("ruthenium_106"), INGOT_VOLUME).addItemResult(FISSION_DUST_MAP.get("ruthenium_106"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(FISSION_FLUID_MAP.get("strontium_90"), INGOT_VOLUME).addItemResult(FISSION_DUST_MAP.get("strontium_90"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(FISSION_FLUID_MAP.get("promethium_147"), INGOT_VOLUME).addItemResult(FISSION_DUST_MAP.get("promethium_147"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(CrystallizerRecipe.class, 0.25D, 0).addFluidInput(FISSION_FLUID_MAP.get("caesium_137"), INGOT_VOLUME).addItemResult(FISSION_DUST_MAP.get("caesium_137"), 1).save(recipeOutput);

        // Pressurizer
        new ProcessorRecipeBuilder(PressurizerRecipe.class, 1, 1).addItemInput(tag(DUSTS, "strontium_90"), 1).addItemResult(strontium90, 1).save(recipeOutput);

        // SuperCooler
        new ProcessorRecipeBuilder(SupercoolerRecipe.class, 2D / 150D, 5D).addFluidInput(HELIUM_TAG, 64).addFluidResult(CUSTOM_FLUID_MAP.get("liquid_helium"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(SupercoolerRecipe.class, 2D / 150D, 2.5D).addFluidInput(NITROGEN_TAG, 64).addFluidResult(CUSTOM_FLUID_MAP.get("liquid_nitrogen"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(SupercoolerRecipe.class, 2D / 150D, 3.75D).addFluidInput(HYDROGEN_TAG, 64).addFluidResult(QMD_FLUIDS.get("liquid_hydrogen"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(SupercoolerRecipe.class, 2D / 150D, 3.75D).addFluidInput(NEON_TAG, 64).addFluidResult(QMD_FLUIDS.get("liquid_neon"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(SupercoolerRecipe.class, 2D / 150D, 2.5D).addFluidInput(ARGON_TAG, 64).addFluidResult(QMD_FLUIDS.get("liquid_argon"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(SupercoolerRecipe.class, 2D / 150D, 2.5D).addFluidInput(OXYGEN_TAG, 64).addFluidResult(QMD_FLUIDS.get("liquid_oxygen"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(SupercoolerRecipe.class, 2D / 150D, 1D).addFluidInput(QMD_FLUIDS.get("compressed_air"), 64).addFluidResult(QMD_FLUIDS.get("liquid_air"), 1).save(recipeOutput);

        // Decay Hastener
        new ProcessorRecipeBuilder(DecayHastenerRecipe.class, getDecayHastenerTimeMultipler(RadSources.PLUTONIUM_238), 1.0, RadSources.PLUTONIUM_238).addItemInput(PLUTONIUM_MAP.get("238"), 1).addItemResult(isotopes.get(IsotopeType.Uranium_234), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(DecayHastenerRecipe.class, 1.0, 1.0, QMDRadSources.BERYLLIUM_7).addItemInput(isotopeTag("beryllium/7"), 1).addItemResult(LITHIUM_MAP.get("7"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(DecayHastenerRecipe.class, 1.0, 1.0, QMDRadSources.PROTACTINIUM_231).addItemInput(isotopeTag("protactinium/231"), 1).addItemResult(DUST_MAP.get("lead"), 1).save(recipeOutput);

        new ProcessorRecipeBuilder(DecayHastenerRecipe.class, 1.0, 1.0, QMDRadSources.URANIUM_234).addItemInput(isotopeTag("uranium/234"), 1).addItemResult(FISSION_DUST_MAP.get("radium"), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(DecayHastenerRecipe.class, getDecayHastenerTimeMultipler(QMDRadSources.COBALT_60), 1.0, QMDRadSources.COBALT_60).addItemInput(isotopeTag("cobalt/60"), 1).addItemResult(dusts.get(DustType.NICKEL), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(DecayHastenerRecipe.class, getDecayHastenerTimeMultipler(QMDRadSources.IRIDIUM_192), 1.0, QMDRadSources.IRIDIUM_192).addItemInput(isotopeTag("iridium/192"), 1).addItemResult(dusts.get(DustType.PLATINUM), 1).save(recipeOutput);

        // Assembler
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(DUSTS, "bscco"), 3).addItemInput(tag(INGOTS, "silver"), 6).addItemResult(parts.get(PartType.WIRE_BSCCO), 6).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(DUSTS, "ssfaf"), 3).addItemInput(tag(INGOTS, "silver"), 6).addItemResult(parts.get(PartType.WIRE_SSFAF), 6).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(DUSTS, "ybco"), 3).addItemInput(tag(INGOTS, "silver"), 6).addItemResult(parts.get(PartType.WIRE_YBCO), 6).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(INGOTS, "tungsten"), 4).addItemInput(tag(INGOTS, "gold"), 2).addItemResult(parts.get(PartType.WIRE_GOLD_TUNGSTEN), 6).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(DUSTS, "bismuth"), 2).addItemInput(tag(DUSTS, "strontium"), 2).addItemInput(tag(DUSTS, "calcium"), 2).addItemInput(tag(DUSTS, "copper_oxide"), 3).addItemResult(chemicalDusts.get(ChemicalDustType.BSCCO), 3).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(DUSTS, "samarium"), 1).addItemInput(tag(DUSTS, "strontium"), 1).addItemInput(tag(DUSTS, "iron_fluoride"), 2).addItemInput(tag(DUSTS, "arsenic"), 2).addItemResult(chemicalDusts.get(ChemicalDustType.SSFAF), 6).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(DUSTS, "yttrium"), 1).addItemInput(tag(DUSTS, "barium"), 2).addItemInput(tag(DUSTS, "copper_oxide"), 3).addItemResult(chemicalDusts.get(ChemicalDustType.YBCO), 3).save(recipeOutput);

        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(PART_MAP.get("bioplastic"), 2).addItemInput(Tags.Items.DYES_BLUE, 1).addItemResult(parts.get(PartType.SCINTILLATOR_PLASTIC), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(semiconductors.get(SemiconductorType.SILICON_WAFER), 1).addItemInput(tag(DUSTS, "redstone"), 4).addItemInput(tag(INGOTS, "gold"), 1).addItemInput(tag(INGOTS, "silver"), 1).addItemResult(semiconductors.get(SemiconductorType.BASIC_PROCESSOR), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(semiconductors.get(SemiconductorType.BASIC_PROCESSOR), 1).addItemInput(tag(DUSTS, "redstone"), 4).addItemInput(tag(DUSTS, "hafnium_oxide"), 1).addItemInput(semiconductors.get(SemiconductorType.SILICON_P_DOPED), 1).addItemResult(semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR), 1).addItemInput(parts.get(PartType.WIRE_BSCCO), 4).addItemInput(tag(DUSTS, "hafnium_oxide"), 1).addItemInput(tag(INGOTS, "platinum"), 1).addItemResult(semiconductors.get(SemiconductorType.ELITE_PROCESSOR), 1).save(recipeOutput);

        new BasicRecipeBuilder<>(new TungstenFilementAssemblerRecipe(List.of(SizedChanceItemIngredient.of(tag(INGOTS, "tungsten"), 2)), 1.0, 1.0, 1.0)).save(recipeOutput);

        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(INGOTS, "ferroboron"), 2).addItemInput(tag(INGOTS, "neodymium"), 1).addItemResult(parts.get(PartType.MAGNET_ND), 1).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(DUSTS, "potassium"), 1).addItemInput(tag(DUSTS, "iodine"), 1).addItemInput(Items.SUGAR, 4).addItemInput(PART_MAP.get("bioplastic"), 1).addItemResult(potassiumIodineTablet, 4).save(recipeOutput);
        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(tag(INGOTS, "cobalt"), 2).addItemInput(dusts.get(DustType.SAMARIUM), 1).addItemResult(parts.get(PartType.MAGNET_SMC), 1).save(recipeOutput);

        // Fission Irradiator
        new BasicRecipeBuilder<>(new FissionIrradiatorRecipe(SizedChanceItemIngredient.of(semiconductors.get(SemiconductorType.SILICON_WAFER), 1), SizedChanceItemIngredient.of(semiconductors.get(SemiconductorType.SILICON_N_DOPED), 1), 120000, 0, 0, 0L, -1L, 0)).save(recipeOutput);
        new BasicRecipeBuilder<>(new FissionIrradiatorRecipe(SizedChanceItemIngredient.of(isotopeTag("uranium/234"), 1), SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1), 1920000, 0, 0, 0L, -1L, QMDRadSources.URANIUM_234)).save(recipeOutput);
        new BasicRecipeBuilder<>(new FissionIrradiatorRecipe(SizedChanceItemIngredient.of(tag(DUSTS, "protactinium_231"), 1), SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("protactinium_233"), 1), 3840000, 0, 0, 0L, -1L, QMDRadSources.PROTACTINIUM_231)).save(recipeOutput);
        new BasicRecipeBuilder<>(new FissionIrradiatorRecipe(SizedChanceItemIngredient.of(tag(INGOTS, "cobalt"), 1), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.COBALT_60), 1), 1920000, 0, 0, 0L, -1L, 0)).save(recipeOutput);
        // NCRecipes.fission_irradiator.addRecipe(FluidUtil.getFilledBucket(fluidStack("deuterium", 1000).getStack()), FluidUtil.getFilledBucket(fluidStack("tritium", 1000).getStack()),60000,0d,0); //1920000

        // Fuel Reprocessor
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(fissionWastes.get(FissionWasteType.LIGHT), 1).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.STRONTIUM), 1, 20)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("strontium_90"), 1, 5)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTRIUM), 1, 5)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("zirconium"), 1, 20)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.NIOBIUM), 1, 5)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("molybdenum"), 1, 30)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("ruthenium_106"), 1, 5)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("silver"), 1, 10)).save(recipeOutput, "light_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(fissionWastes.get(FissionWasteType.HEAVY), 1).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.NIOBIUM), 1, 4)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("molybdenum"), 1, 21)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("ruthenium_106"), 1, 4)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("silver"), 1, 7)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("tin"), 1, 35)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.IODINE), 1, 7)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("caesium_137"), 1, 4)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.NEODYMIUM), 1, 18)).save(recipeOutput, "heavy_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.CALIFORNIUM), 1).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("thorium"), 1, 26)).addItemResult(SizedChanceItemIngredient.of(isotopes.get(IsotopeType.PROTACTINIUM_231), 1, 13)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("radium"), 1, 12)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1, 9)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("bismuth"), 1, 20)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 20)).save(recipeOutput, "californium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.BERKELIUM), 1).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("radium"), 1, 9)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1, 15)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("bismuth"), 1, 40)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 35)).addItemResult(SizedChanceItemIngredient.of(ingots.get(IngotType.MERCURY), 1, 1)).save(recipeOutput, "berkelium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.CURIUM), 1).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("radium"), 1, 13)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1, 17)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("bismuth"), 1, 16)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 50)).addItemResult(SizedChanceItemIngredient.of(ingots.get(IngotType.MERCURY), 1, 4)).save(recipeOutput, "curium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.AMERICIUM), 1).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1, 22)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("bismuth"), 1, 15)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 55)).addItemResult(SizedChanceItemIngredient.of(ingots.get(IngotType.MERCURY), 1, 5)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "gold"), 1, 1)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.PLATINUM), 1, 2)).save(recipeOutput, "americium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.PLUTONIUM), 1).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1, 22)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("bismuth"), 1, 14)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 55)).addItemResult(SizedChanceItemIngredient.of(ingots.get(IngotType.MERCURY), 1, 5)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "gold"), 1, 1)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.PLATINUM), 1, 3)).save(recipeOutput, "plutonium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.NEPTUNIUM), 1).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1, 36)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("bismuth"), 1, 17)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 34)).addItemResult(SizedChanceItemIngredient.of(ingots.get(IngotType.MERCURY), 1, 7)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "gold"), 1, 2)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.PLATINUM), 1, 4)).save(recipeOutput, "neptunium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.URANIUM), 1).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1, 21)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("bismuth"), 1, 12)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 55)).addItemResult(SizedChanceItemIngredient.of(ingots.get(IngotType.MERCURY), 1, 7)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "gold"), 1, 1)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.PLATINUM), 1, 4)).save(recipeOutput, "uranium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.THORIUM), 1).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1, 10)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("bismuth"), 1, 7)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 62)).addItemResult(SizedChanceItemIngredient.of(ingots.get(IngotType.MERCURY), 1, 11)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "gold"), 1, 2)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.PLATINUM), 1, 8)).save(recipeOutput, "thorium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.PROTACTINIUM), 1).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("polonium"), 1, 36)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("bismuth"), 1, 6)).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 39)).addItemResult(SizedChanceItemIngredient.of(ingots.get(IngotType.MERCURY), 1, 10)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "gold"), 1, 2)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.PLATINUM), 1, 7)).save(recipeOutput, "protactinium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.RADIUM), 1).addItemResult(SizedChanceItemIngredient.of(DUST_MAP.get("lead"), 1, 58)).addItemResult(SizedChanceItemIngredient.of(ingots.get(IngotType.MERCURY), 1, 18)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "gold"), 1, 3)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.PLATINUM), 1, 10)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "iridium"), 1, 6)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "osmium"), 1, 5)).save(recipeOutput, "radium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.POLONIUM), 1).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "iridium"), 1, 52)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "osmium"), 1, 21)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TUNGSTEN), 1, 12)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.HAFNIUM), 1, 10)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTERBIUM), 1, 5)).save(recipeOutput, "polonium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.BISMUTH), 1).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "iridium"), 1, 42)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "osmium"), 1, 27)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TUNGSTEN), 1, 14)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.HAFNIUM), 1, 11)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTERBIUM), 1, 6)).save(recipeOutput, "bismuth_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.LEAD), 1).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "iridium"), 1, 27)).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "osmium"), 1, 35)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TUNGSTEN), 1, 15)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.HAFNIUM), 1, 12)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTERBIUM), 1, 8)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.ERBIUM), 1, 3)).save(recipeOutput, "lead_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.MERCURY), 1).addItemResult(SizedChanceItemIngredient.of(tag(DUSTS, "osmium"), 1, 42)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TUNGSTEN), 1, 27)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.HAFNIUM), 1, 16)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTERBIUM), 1, 10)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.ERBIUM), 1, 3)).save(recipeOutput, "mercury_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.GOLD), 1).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.HAFNIUM), 1, 72)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTERBIUM), 1, 20)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.ERBIUM), 1, 8)).save(recipeOutput, "gold_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.PLATINUM), 1).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TUNGSTEN), 1, 30)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.HAFNIUM), 1, 44)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTERBIUM), 1, 17)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.ERBIUM), 1, 7)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TERBIUM), 1, 2)).save(recipeOutput, "platinum_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.IRIDIUM), 1).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.HAFNIUM), 1, 59)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTERBIUM), 1, 29)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.ERBIUM), 1, 10)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TERBIUM), 1, 2)).save(recipeOutput, "iridium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.OSMIUM), 1).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.HAFNIUM), 1, 48)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTERBIUM), 1, 35)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.ERBIUM), 1, 11)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TERBIUM), 1, 3)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("europium_155"), 1, 3)).save(recipeOutput, "osmium_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.TUNGSTEN), 1).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.YTTERBIUM), 1, 50)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.ERBIUM), 1, 26)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TERBIUM), 1, 6)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("europium_155"), 1, 5)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.SAMARIUM), 1, 7)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.NEODYMIUM), 1, 6)).save(recipeOutput, "tungsten_waste");
        new ProcessorRecipeBuilder(FuelReprocessorRecipe.class, 1, 1).addItemInput(spallationWastes.get(SpallationWasteType.HAFNIUM), 1).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.ERBIUM), 1, 32)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.TERBIUM), 1, 16)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("europium_155"), 1, 11)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.SAMARIUM), 1, 13)).addItemResult(SizedChanceItemIngredient.of(dusts.get(DustType.NEODYMIUM), 1, 23)).addItemResult(SizedChanceItemIngredient.of(FISSION_DUST_MAP.get("promethium_147"), 1, 5)).save(recipeOutput, "hafnium_waste");

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

        new ProcessorRecipeBuilder(AssemblerRecipe.class, 1, 1).addItemInput(pellet_copernicium.get(CoperniciumPelletType.MIX_291_C), 9).addItemInput(tag(DUSTS, "graphite"), 1).addItemInput(PART_MAP.get("pyrolytic_carbon"), 1).addItemInput(ALLOY_MAP.get("silicon_carbide"), 1).addItemResult(fuel_copernicium.get(CoperniciumFuelType.MIX_291_TR), 9).save(recipeOutput);

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

    public static double getDecayHastenerTimeMultipler(double radiation) {
        double F = Math.log1p(Math.log(2D)), Z = 0.1674477985420331D;
        return NCMath.roundTo(Z * (radiation >= 1D ? F / Math.log1p(Math.log1p(radiation)) : Math.log1p(Math.log1p(1D / radiation)) / F), 5D / 800);
    }
}