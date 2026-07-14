package lach_01298.qmd.datagen.recipe;

import com.nred.nuclearcraft.info.NCFluid;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import lach_01298.qmd.QMD;
import lach_01298.qmd.enums.MaterialTypes.DustType;
import lach_01298.qmd.enums.MaterialTypes.IsotopeType;
import lach_01298.qmd.recipe.QMDRecipeBuilder;
import lach_01298.qmd.recipe.types.MassSpectrometerRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

import static com.nred.nuclearcraft.helpers.RecipeHelpers.*;
import static com.nred.nuclearcraft.registration.FluidRegistration.GAS_MAP;
import static com.nred.nuclearcraft.registration.FluidRegistration.MOLTEN_MAP;
import static com.nred.nuclearcraft.registration.ItemRegistration.*;
import static com.nred.nuclearcraft.util.FluidStackHelper.COAL_DUST_VOLUME;
import static com.nred.nuclearcraft.util.FluidStackHelper.GEM_VOLUME;
import static lach_01298.qmd.datagen.recipe.OreLeacherProvider.*;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;
import static lach_01298.qmd.item.QMDItems.dusts;
import static lach_01298.qmd.item.QMDItems.isotopes;

public class MassSpectrometerProvider {
    public MassSpectrometerProvider(RecipeOutput recipeOutput) {
        // Ores

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("iron"), 1)), List.of(),
                List.of(dustChance("chromium", 3, 40, 2), dustChance("manganese", 1, 20), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "iron"), 4)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "iron_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("gold"), 1)), List.of(),
                List.of(dustChance("silver", 1, 40), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "gold"), 4)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "gold_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("copper"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "copper"), 4), dustChance("zinc", 1, 20), dustChance("iridium", 1, 4)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "copper_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("tin"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "zirconium"), 1), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "tin"), 4), dustChance("tungsten", 1, 40)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "tin_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("lead"), 1)), List.of(),
                List.of(dustChance("nickel", 1, 40), dustChance("cobalt", 1, 20), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "lead"), 4)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "lead_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("uranium"), 1)), List.of(),
                List.of(dustChance("niobium", 1, 40), dustChance("radium", 1, 5), dustChance("thorium", 1, 40), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "uranium"), 4)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "uranium_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("thorium"), 1)), List.of(),
                List.of(dustChance("titanium", 1, 60), dustChance("hafnium", 1, 40), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "thorium"), 4)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "thorium_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("lithium"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "lithium"), 4), dustChance("sodium", 1, 20), dustChance("aluminum", 1, 40)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "lithium_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("magnesium"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "magnesium"), 4), dustChance("potassium", 1, 20), dustChance("calcium", 1, 40)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "magnesium_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("boron"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "boron"), 4), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "sodium"), 3)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "boron_ore"));

        // Other mod's ores

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("osmium"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "osmium"), 4), dustChance("iridium", 1, 40), dustChance("platinum", 1, 40)), List.of(),
                1)).save(tagExists(recipeOutput, OSMIUM_TAG), ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "osmium_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("iridium"), 1)), List.of(),
                List.of(dustChance("osmium", 1, 40), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "iridium"), 4), dustChance("platinum", 1, 40)), List.of(),
                1)).save(tagExists(recipeOutput, IRIDIUM_TAG), ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "iridium_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("platinum"), 1)), List.of(),
                List.of(dustChance("osmium", 1, 40), dustChance("iridium", 1, 40), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "platinum"), 4)), List.of(),
                1)).save(tagExists(recipeOutput, PLATINUM_TAG), ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "platinum_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("nickel"), 1)), List.of(),
                List.of(dustChance("aluminum", 1, 40), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "iron"), 1), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "nickel"), 4)), List.of(),
                1)).save(tagExists(recipeOutput, NICKEL_TAG), ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "nickel_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("titanium"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "titanium"), 4), dustChance("manganese", 1, 40), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "iron"), 1)), List.of(),
                1)).save(tagExists(recipeOutput, TITANIUM_TAG), ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "titanium_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("silver"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "silver"), 4), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "lead"), 1)), List.of(),
                1)).save(tagExists(recipeOutput, SILVER_TAG), ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "silver_ore"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(oreTag("aluminum"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "aluminum"), 4), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "iron"), 1)), List.of(),
                1)).save(tagExists(recipeOutput, ALUMINUM_TAG), ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "aluminum_ore"));

        // Isotope Separation
        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "uranium"), 10)), List.of(),
                List.of(SizedChanceItemIngredient.of(URANIUM_MAP.get("235"), 1), SizedChanceItemIngredient.of(URANIUM_MAP.get("238"), 9)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "uranium_separation"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "boron"), 12)), List.of(),
                List.of(SizedChanceItemIngredient.of(BORON_MAP.get("10"), 3), SizedChanceItemIngredient.of(BORON_MAP.get("11"), 9)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "boron_separation"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "lithium"), 10)), List.of(),
                List.of(SizedChanceItemIngredient.of(LITHIUM_MAP.get("6"), 1), SizedChanceItemIngredient.of(LITHIUM_MAP.get("7"), 9)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "lithium_separation"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(tags(List.of(tag(Tags.Items.INGOTS, "magnesium"), tag(Tags.Items.DUSTS, "magnesium")), 9)), List.of(),
                List.of(SizedChanceItemIngredient.of(isotopes.get(IsotopeType.MAGNESIUM_24), 8), SizedChanceItemIngredient.of(isotopes.get(IsotopeType.MAGNESIUM_26), 1)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "magnesium_separation"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(tags(List.of(tag(Tags.Items.INGOTS, "calcium"), tag(Tags.Items.DUSTS, "calcium")), 8)), List.of(),
                List.of(SizedChanceItemIngredient.of(isotopes.get(IsotopeType.CALCIUM_48), 1)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "calcium_separation"));

        // Common materials

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(tags(List.of(Tags.Items.GLASS_BLOCKS, Tags.Items.SANDS), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(GEM_MAP.get("silicon"), 1)), List.of(NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), 1000)),
                1)).save(recipeOutput);

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "redstone"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(GEM_DUST_MAP.get("sulfur"), 1)), List.of(NCFluid.sizedIngredient(QMD_FLUIDS.get("mercury"), 144)),
                1)).save(recipeOutput);

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(Items.COAL, 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1), SizedChanceItemIngredient.of(GEM_DUST_MAP.get("sulfur"), 1, 15)), List.of(),
                1)).save(recipeOutput);

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(), List.of(NCFluid.sizedIngredient(MOLTEN_MAP.get("coal"), COAL_DUST_VOLUME)),
                List.of(), List.of(NCFluid.sizedIngredient(QMD_FLUIDS.get("carbon"), COAL_DUST_VOLUME), NCFluid.sizedIngredient(MOLTEN_MAP.get("sulfur"), GEM_VOLUME / 6)),
                1)).save(recipeOutput);

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(ItemTags.LOGS, 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1)),
                List.of(NCFluid.sizedIngredient(GAS_MAP.get("hydrogen"), 1000, 12, 0, 10), NCFluid.sizedIngredient(GAS_MAP.get("nitrogen"), 1000, 2, 0, 10), NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), 1000, 42, 0, 10)),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "graphite_from_log"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(Items.SUGAR, 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1, 26)),
                List.of(NCFluid.sizedIngredient(GAS_MAP.get("hydrogen"), 1000, 48, 0, 10), NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), 1000, 24, 0, 10)),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "graphite_from_sugar"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(Items.BONE, 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(dusts.get(DustType.CALCIUM), 1, 23)),
                List.of(NCFluid.sizedIngredient(GAS_MAP.get("hydrogen"), 1000, 4, 0, 10), NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), 1000, 59, 0, 10)),
                1)).save(recipeOutput);

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(Items.ROTTEN_FLESH, 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(DUST_MAP.get("graphite"), 1, 19)),
                List.of(NCFluid.sizedIngredient(GAS_MAP.get("hydrogen"), 1000, 10, 0, 10), NCFluid.sizedIngredient(GAS_MAP.get("nitrogen"), 1000, 3, 0, 10), NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), 1000, 65, 0, 10)),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "graphite_from_rotten_flesh"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(tags(List.of(tag(Tags.Items.GEMS, "quartz"), tag(Tags.Items.DUSTS, "quartz")), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(GEM_MAP.get("silicon"), 1)), List.of(NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), 1000)),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "silicon_from_quartz"));

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "emerald"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(DUST_MAP.get("beryllium"), 3), SizedChanceItemIngredient.of(GEM_MAP.get("silicon"), 6), SizedChanceItemIngredient.of(DUST_MAP.get("aluminum"), 2)), List.of(NCFluid.sizedIngredient(GAS_MAP.get("oxygen"), 9000)),
                1)).save(recipeOutput);

        new QMDRecipeBuilder<>(new MassSpectrometerRecipe(List.of(SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS, "lapis"), 1)), List.of(),
                List.of(SizedChanceItemIngredient.of(dusts.get(DustType.CALCIUM), 4), SizedChanceItemIngredient.of(DUST_MAP.get("aluminum"), 3), SizedChanceItemIngredient.of(GEM_MAP.get("silicon"), 3), SizedChanceItemIngredient.of(GEM_DUST_MAP.get("sulfur"), 1)), List.of(),
                1)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "calcium_from_lapis"));

        // Chemicals TODO
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("dustManganeseOxide", "ingotManganeseOxide"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustManganese",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 500),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("dustManganeseDioxide", "ingotManganeseDioxide"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustManganese",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("gemRhodochrosite", "dustRhodochrosite"),1),new EmptyFluidIngredient(),
//                "dustGraphite", new EmptyItemIngredient(),"dustMaganese", new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),fluidStack("oxygen", 1500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("gemBoronNitride", "dustBoronNitride"),1),new EmptyFluidIngredient(),
//                "dustBoron", new EmptyItemIngredient(),new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),fluidStack("nitrogen", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("gemFluorite", "dustFluorite"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustCalcium",new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("fluorine", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("gemVilliaumite", "dustVilliaumite","dustSodiumFluoride"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustSodium",new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("fluorine", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("gemCarobbiite", "dustCarobbiite","dustPotassiumFluoride"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustPotassium",new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("fluorine", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("gemBoronArsenide",new EmptyFluidIngredient(),
//                "dustBoron", "dustArsenic",new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustCalciumSulfate",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustSulfur","dustCalcium", new EmptyItemIngredient(),
//                fluidStack("oxygen", 2000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustSodiumHydroxide",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), new EmptyItemIngredient(),"dustSodium", new EmptyItemIngredient(),
//                fluidStack("hydrogen", 500), fluidStack("oxygen", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustPotassiumHydroxide",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), new EmptyItemIngredient(),"dustPotassium", new EmptyItemIngredient(),
//                fluidStack("hydrogen", 500), fluidStack("oxygen", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustBorax",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"boron"),2), new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sodium"),2),
//                fluidStack("hydrogen", 10000), new EmptyFluidIngredient(), fluidStack("oxygen", 8500), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustAmmoniumSulfate",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sulfur"),1),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*4), fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustAmmoniumBisulfate",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sulfur"),1),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*5/2), fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustAmmoniumPersulfate",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sulfur"),2),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*4), fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*4), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustHydroquinone",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),6), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3), new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustSodiumHydroquinone",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),6), new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sodium"),1),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3), new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustPotassiumHydroquinone",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),6), new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"potassium"),1),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3), new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("dustZirconia", "ingotZirconia"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustZirconium",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("dustTinOxide", "ingotTinOxide"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustTin",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("dustNickelOxide", "ingotNickelOxide"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustNickel",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("dustCobaltOxide", "ingotCobaltOxide"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustCobalt",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("dustRutheniumOxide", "ingotRutheniumOxide"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustRuthenium",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("dustIridiumOxide", "ingotIridiumOxide"),1),new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustIridium",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustTungstenOxide", new EmptyFluidIngredient(),
//                new EmptyItemIngredient(),"dustTungsten", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000),new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustSodiumNitrate", new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), new EmptyItemIngredient(),"ingotSodium", new EmptyItemIngredient(),
//                fluidStack("nitrogen", 500),fluidStack("oxygen", 1500),new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustSalt", new EmptyFluidIngredient(),
//                "dustSodium", new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),fluidStack("chlorine", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustCopperOxide",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustCopper",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustHafniumOxide",new EmptyFluidIngredient(),
//                new EmptyItemIngredient(), "dustHafnium",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe("dustZincSulfide",new EmptyFluidIngredient(),
//                "dustSulfur", "dustZinc",new EmptyItemIngredient(),new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//        //alloys
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(oreStackList(Lists.newArrayList("ingotBronze","dustBronze"),4),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"copper"),3), "dustTin", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "tough"),4),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"lithium"),2), "dustBoron", "dustIron", new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "hard_carbon"),2),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),3), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "magnesium_diboride"),3),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"boron"),2), "dustMagnesium", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "lithium_manganese_dioxide"),2),new EmptyFluidIngredient(),
//                "dustLithium", new EmptyItemIngredient(), "dustManganese", new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "ferroBoron"),2),new EmptyFluidIngredient(),
//                "dustBoron", "dustIron", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "shibuichi"),4),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"copper"),3), "dustSilver", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "tin_silver"),4),new EmptyFluidIngredient(),
//                "dustSilver", SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"tin"),3), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "lead_platinum"),4),new EmptyFluidIngredient(),
//                "dustPlatinum", SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"lead"),3), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "extreme"),8),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"lithium"),4), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"boron"),2), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),3), oreStackList(Lists.newArrayList("dustIron","ingotIron"),2),
//                new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "zircaloy"),8),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"zirconium"),7), "dustTin", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "silicon_carbide"),2),new EmptyFluidIngredient(),
//                "dustGraphite", "itemSilicon", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "hsla_steel"),32),new EmptyFluidIngredient(),
//                "dustGraphite", "dustManganese", SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"iron"),30), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "zirconium_molybdenum"),16),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"zirconium"),1), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"molybdenum"),15), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "hastelloy"),4),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"chromium"),3), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"nickel"),3), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"molybdenum"),1), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "niobium_tin"),4),new EmptyFluidIngredient(),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"niobium"),3), "dustTin", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "stainless_steel"),6),new EmptyFluidIngredient(),
//                "dustChromium", SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"iron"),5), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "niobium_titanium"),2),new EmptyFluidIngredient(),
//                "dustTitanium", "dustNiobium", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "osmiridium"),2),new EmptyFluidIngredient(),
//                "dustOsmium", "dustIridium", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "nichrome"),2),new EmptyFluidIngredient(),
//                "dustChromium", "dustNickel", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "super_alloy"),6),new EmptyFluidIngredient(),
//                "dustTitanium", SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"chromium"),2), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"nickel"),2), "niobium",
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "electrum"),2),new EmptyFluidIngredient(),
//                "dustSilver", "dustGold", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "invar"),3),new EmptyFluidIngredient(),
//                "dustNickel", "dustIron", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(SizedChanceItemIngredient.of(tag(Tags.Items.INGOTS, "constantan"),2),new EmptyFluidIngredient(),
//                "dustNickel", "dustCopper", new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//        // chemical fluids
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("water", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", 990),fluidStack("deuterium", 10), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("heavy_water", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("deuterium", FluidStackHelper.BUCKET_VOLUME),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("hydrogen_peroxide", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("ethanol", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),2), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3),new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("methanol", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),1), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*2),new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("benzene", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),6), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("phenol", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),6), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3),new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("fluorobenzene", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),6), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*5/2),new EmptyFluidIngredient(), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("difluorobenzene", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),6), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*2),new EmptyFluidIngredient(), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("dimethyldifluorosilane", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),2), new EmptyItemIngredient(), oreStack("itemSilicon",1),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3),new EmptyFluidIngredient(), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("carbon_dioxide", FluidStackHelper.BUCKET_VOLUME),
//                "dustGraphite", new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("carbon_monoxide", FluidStackHelper.BUCKET_VOLUME),
//                "dustGraphite", new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("ethene", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),2), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*2),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("ethyne", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),2), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*1),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("fluoromethane", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),1), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3/2),new EmptyFluidIngredient(), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("ammonia", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3/2), fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("oxygen_difluoride", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("diborane", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"boron"),2), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("sulfur_dioxide", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sulfur"),1), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("sulfur_trioxide", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sulfur"),1), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*3/2), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("tetrafluoroethene", FluidStackHelper.BUCKET_VOLUME),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"graphite"),2), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(),fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME*2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("hydrogen_sulfide", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sulfur"),1), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("hydrofluoric_acid", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("boric_acid", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"boron"),1), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3/2), new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*3/2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("sulfuric_acid", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sulfur"),1), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*2), new EmptyFluidIngredient(),  new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("hydrochloric_acid", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("chlorine", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("nitric_acid", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*3/2), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("nitrogen_dioxide", FluidStackHelper.BUCKET_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//
//        //molten chemicals
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("lif", FluidStackHelper.INGOT_VOLUME),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"lithium"),1), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(),fluidStack("bef2", FluidStackHelper.INGOT_VOLUME),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"beryllium"),1), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(), fluidStack("naoh", FluidStackHelper.GEM_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(),"dustSodium", new EmptyItemIngredient(),
//                fluidStack("hydrogen", 500), fluidStack("oxygen", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(), fluidStack("koh", FluidStackHelper.GEM_VOLUME),
//                new EmptyItemIngredient(), new EmptyItemIngredient(),"dustPotassium", new EmptyItemIngredient(),
//                fluidStack("hydrogen", 500), fluidStack("oxygen", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(), fluidStack("sodium_sulfide", FluidStackHelper.INGOT_VOLUME),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sodium"),2), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sulfur"),1),new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(), fluidStack("potassium_sulfide", FluidStackHelper.INGOT_VOLUME),
//                SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"sulfur"),1), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"potassium"),2),new EmptyItemIngredient(), new EmptyItemIngredient(),
//                new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
//
//         new QMDRecipeBuilder<>(new MassSpectrometerRecipe(new EmptyItemIngredient(), fluidStack("alumina", FluidStackHelper.INGOT_VOLUME),
//                new EmptyItemIngredient(), SizedChanceItemIngredient.of(tag(Tags.Items.DUSTS,"aluminum"),2),new EmptyItemIngredient(), new EmptyItemIngredient(),
//                fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*3/2), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
    }

    private SizedChanceItemIngredient dustChance(String name, int count, int chancePercent) {
        return new SizedChanceItemIngredient(Ingredient.of(tag(Tags.Items.DUSTS, name)), count, chancePercent, 0);
    }

    private SizedChanceItemIngredient dustChance(String name, int count, int chancePercent, int minStackSize) {
        return new SizedChanceItemIngredient(Ingredient.of(tag(Tags.Items.DUSTS, name)), count, chancePercent, minStackSize);
    }
}