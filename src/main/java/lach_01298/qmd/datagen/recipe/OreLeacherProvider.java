package lach_01298.qmd.datagen.recipe;

import com.nred.nuclearcraft.recipe.ProcessorRecipeBuilder;
import com.nred.nuclearcraft.util.FluidStackHelper;
import lach_01298.qmd.recipe.types.OreLeacherRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;

import static com.nred.nuclearcraft.datagen.ModFluidTagProvider.SULFURIC_ACID_TAG;
import static com.nred.nuclearcraft.helpers.RecipeHelpers.dustExists;
import static com.nred.nuclearcraft.helpers.RecipeHelpers.tagExists;
import static com.nred.nuclearcraft.registration.ItemRegistration.COPPER_DUST;
import static com.nred.nuclearcraft.registration.ItemRegistration.DUST_MAP;
import static lach_01298.qmd.datagen.QMDFluidTagProvider.HYDROCHLORIC_ACID_TAG;
import static lach_01298.qmd.datagen.QMDFluidTagProvider.NITRIC_ACID_TAG;
import static lach_01298.qmd.enums.MaterialTypes.DustType.*;
import static lach_01298.qmd.item.QMDItems.dusts;

public class OreLeacherProvider {
    public OreLeacherProvider(RecipeOutput recipeOutput) {
        // TODO add iron and gold dust?
        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(Tags.Items.ORES_IRON, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(dustTag("iron"), 3).addItemResult(dusts.get(CHROMIUM), 1, 60).addItemResult(DUST_MAP.get("manganese"), 1, 5)
                .save(dustExists(recipeOutput, "iron"), "iron_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(Tags.Items.ORES_GOLD, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(dustTag("gold"), 3).addItemResult(DUST_MAP.get("silver"), 1, 10)
                .save(dustExists(recipeOutput, "gold"), "gold_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(Tags.Items.ORES_COPPER, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(COPPER_DUST, 3).addItemResult(dusts.get(ZINC), 1, 5).addItemResult(dusts.get(IRIDIUM), 1, 1)
                .save(recipeOutput, "copper_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(oreTag("tin"), 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(DUST_MAP.get("tin"), 3).addItemResult(DUST_MAP.get("zirconium"), 1, 25).addItemResult(dusts.get(TUNGSTEN), 1, 10)
                .save(recipeOutput, "tin_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(oreTag("lead"), 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(DUST_MAP.get("lead"), 3).addItemResult(dusts.get(NICKEL), 1, 10).addItemResult(dusts.get(COBALT), 1, 5)
                .save(recipeOutput, "lead_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(oreTag("uranium"), 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(DUST_MAP.get("uranium"), 3).addItemResult(DUST_MAP.get("thorium"), 1, 10).addItemResult(dusts.get(NIOBIUM), 1, 10)
                .save(recipeOutput, "uranium_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(oreTag("thorium"), 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(DUST_MAP.get("thorium"), 3).addItemResult(dusts.get(TITANIUM), 1, 15).addItemResult(dusts.get(HAFNIUM), 1, 10)
                .save(recipeOutput, "thorium_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(oreTag("lithium"), 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(DUST_MAP.get("lithium"), 3).addItemResult(DUST_MAP.get("aluminum"), 1, 10)
                .save(recipeOutput, "lithium_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(oreTag("magnesium"), 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(DUST_MAP.get("magnesium"), 3).addItemResult(dusts.get(CALCIUM), 1, 10).addItemResult(dusts.get(POTASSIUM), 1, 5)
                .save(recipeOutput, "magnesium_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(oreTag("boron"), 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(DUST_MAP.get("boron"), 3).addItemResult(dustTag("salt"), 2, 50, 1)
                .save(recipeOutput, "boron_leaching");

        // Other mod's ores
        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(OSMIUM_TAG, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(dustTag("osmium"), 3).addItemResult(dustTag("platinum"), 1, 10).addItemResult(dustTag("iridium"), 1, 10)
                .save(tagExists(recipeOutput, OSMIUM_TAG), "osmium_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(IRIDIUM_TAG, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(dustTag("iridium"), 3).addItemResult(dustTag("platinum"), 1, 10).addItemResult(dustTag("osmium"), 1, 10)
                .save(tagExists(recipeOutput, IRIDIUM_TAG), "iridium_leaching");


        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(PLATINUM_TAG, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(dustTag("platinum"), 3).addItemResult(dustTag("iridium"), 1, 10).addItemResult(dustTag("osmium"), 1, 10)
                .save(tagExists(recipeOutput, PLATINUM_TAG), "platinum_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(NICKEL_TAG, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(dusts.get(NICKEL), 3).addItemResult(dustTag("iron"), 1, 25).addItemResult(DUST_MAP.get("aluminum"), 1, 10)
                .save(tagExists(recipeOutput, NICKEL_TAG), "nickel_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(TITANIUM_TAG, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(dusts.get(TITANIUM), 3).addItemResult(dustTag("iron"), 1, 25).addItemResult(DUST_MAP.get("manganese"), 1, 10)
                .save(tagExists(recipeOutput, TITANIUM_TAG), "titanium_leaching");
        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(SILVER_TAG, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(DUST_MAP.get("silver"), 3).addItemResult(DUST_MAP.get("lead"), 1, 25)
                .save(tagExists(recipeOutput, SILVER_TAG), "silver_leaching");

        new ProcessorRecipeBuilder(OreLeacherRecipe.class, 1, 1).addItemInput(ALUMINUM_TAG, 1)
                .addFluidInput(NITRIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(HYDROCHLORIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addFluidInput(SULFURIC_ACID_TAG, FluidStackHelper.NUGGET_VOLUME)
                .addItemResult(DUST_MAP.get("aluminum"), 3).addItemResult(dustTag("iron"), 1, 25)
                .save(tagExists(recipeOutput, ALUMINUM_TAG), "aluminum_leaching");
    }

    public static TagKey<Item> OSMIUM_TAG = oreTag("osmium");
    public static TagKey<Item> IRIDIUM_TAG = oreTag("iridium");
    public static TagKey<Item> PLATINUM_TAG = oreTag("platinum");
    public static TagKey<Item> NICKEL_TAG = oreTag("nickel");
    public static TagKey<Item> TITANIUM_TAG = oreTag("titanium");
    public static TagKey<Item> SILVER_TAG = oreTag("silver");
    public static TagKey<Item> ALUMINUM_TAG = oreTag("aluminum");

    public static TagKey<Item> oreTag(String name) {
        return ItemTags.create(Tags.Items.ORES.location().withSuffix("/" + name));
    }

    private TagKey<Item> dustTag(String name) {
        return ItemTags.create(Tags.Items.DUSTS.location().withSuffix("/" + name));
    }
}
