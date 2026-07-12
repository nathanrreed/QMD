package lach_01298.qmd.datagen;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.datagen.recipe.*;
import lach_01298.qmd.enums.BlockTypes.LampType;
import lach_01298.qmd.enums.MaterialTypes.*;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.item.QMDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

import static com.nred.nuclearcraft.datagen.ModItemTagProvider.isotopeTag;
import static com.nred.nuclearcraft.helpers.RecipeHelpers.tag;
import static com.nred.nuclearcraft.registration.BlockRegistration.PROCESSOR_MAP;
import static com.nred.nuclearcraft.registration.BlockRegistration.RTG_MAP;
import static com.nred.nuclearcraft.registration.ItemRegistration.*;
import static lach_01298.qmd.block.QMDBlocks.dischargeLamps;
import static lach_01298.qmd.block.QMDBlocks.strontium90;
import static lach_01298.qmd.item.QMDItems.*;
import static net.minecraft.data.recipes.RecipeCategory.MISC;
import static net.neoforged.neoforge.common.Tags.Items.DUSTS;
import static net.neoforged.neoforge.common.Tags.Items.INGOTS;

public class QMDRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public QMDRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        new LiquefierCoolantProvider(recipeOutput);
        new IrradiatorProvider(recipeOutput);
        new CollectorProvider(recipeOutput);
        new MassSpectrometerProvider(recipeOutput);
        new OreLeacherProvider(recipeOutput);

        NCRecipeProvider.buildRecipes(recipeOutput);

        // Crafting
        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.beamline, 6).pattern("SSS").pattern("   ").pattern("SSS").define('S', tag(INGOTS, "stainless_steel"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        toolSet(recipeOutput, ingotAlloys.get(IngotAlloyType.TUNGSTEN_CARBIDE), QMDItems.sword_tungsten_carbide, QMDItems.pickaxe_tungsten_carbide, QMDItems.shovel_tungsten_carbide, QMDItems.axe_tungsten_carbide, QMDItems.hoe_tungsten_carbide);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.fissionReflector, 2).pattern("TTT").pattern("TST").pattern("TTT")
                .define('T', tag(INGOTS, "tungsten_carbide")).define('S', PART_BLOCK_MAP.get("steel_chassis"))
                .unlockedBy(getHasName(PART_BLOCK_MAP.get("steel_chassis")), has(PART_BLOCK_MAP.get("steel_chassis"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.oreLeacher, 1).pattern("PRP").pattern("CFC").pattern("PHP")
                .define('P', PART_MAP.get("elite_plating")).define('F', PART_BLOCK_MAP.get("machine_chassis")).define('C', PROCESSOR_MAP.get("chemical_reactor")).define('R', PROCESSOR_MAP.get("rock_crusher")).define('H', tag(INGOTS, "hard_carbon"))
                .unlockedBy(getHasName(PROCESSOR_MAP.get("chemical_reactor")), has(PROCESSOR_MAP.get("chemical_reactor"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.irradiator, 1).pattern("TPT").pattern("PFP").pattern("TPT")
                .define('P', PART_MAP.get("du_plating")).define('F', PART_BLOCK_MAP.get("machine_chassis")).define('T', tag(INGOTS, "tungsten"))
                .unlockedBy(getHasName(PART_BLOCK_MAP.get("machine_chassis")), has(PART_BLOCK_MAP.get("machine_chassis"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.rtgStrontium, 1).pattern("AGA").pattern("GSG").pattern("AGA")
                .define('S', tag(Tags.Items.STORAGE_BLOCKS, "strontium_90")).define('A', PART_MAP.get("advanced_plating")).define('G', tag(INGOTS, "graphite"))
                .unlockedBy(getHasName(strontium90), has(strontium90)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, dischargeLamps.get(LampType.EMPTY), 4).pattern("GGG").pattern("GFG").pattern("GRG")
                .define('F', QMDItems.sources.get(SourceType.TUNGSTEN_FILAMENT).get()).define('R', tag(DUSTS, "redstone")).define('G', Tags.Items.GLASS_PANES)
                .unlockedBy(getHasName(QMDItems.sources.get(SourceType.TUNGSTEN_FILAMENT)), has(QMDItems.sources.get(SourceType.TUNGSTEN_FILAMENT))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.atmosphereCollector, 1).pattern("ASA").pattern("BMB").pattern("ASA")
                .define('A', PART_MAP.get("advanced_plating")).define('S', tag(INGOTS, "steel")).define('B', Items.BUCKET).define('M', PART_MAP.get("electric_motor"))
                .unlockedBy(getHasName(PART_MAP.get("advanced_plating")), has(PART_MAP.get("advanced_plating"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.liquidCollector, 1).pattern("ASA").pattern("BMB").pattern("ASA")
                .define('A', PART_MAP.get("advanced_plating")).define('S', tag(INGOTS, "bronze")).define('B', Items.BUCKET).define('M', PART_MAP.get("electric_motor"))
                .unlockedBy(getHasName(PART_MAP.get("advanced_plating")), has(PART_MAP.get("advanced_plating"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.fissionShield, 4).pattern("BHB").pattern("HFH").pattern("BHB")
                .define('B', PART_MAP.get("basic_plating")).define('F', PART_BLOCK_MAP.get("steel_chassis")).define('H', tag(INGOTS, "hafnium"))
                .unlockedBy(getHasName(PART_BLOCK_MAP.get("steel_chassis")), has(PART_BLOCK_MAP.get("steel_chassis"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDItems.cells.get(CellType.EMPTY)).pattern("WEW").pattern("O O").pattern("WRW")
                .define('W', parts.get(PartType.WIRE_BSCCO)).define('O', tag(INGOTS, "osmiridium")).define('R', RTG_MAP.get("rtg_californium")).define('E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR))
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.ELITE_PROCESSOR)), has(semiconductors.get(SemiconductorType.ELITE_PROCESSOR))).save(recipeOutput);

//      TODO  ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.ACCELERATING_BARREL.getID()).pattern("WMW").pattern("BEB").pattern("WMW").define('W', new ItemStack(QMDBlocks.RFCavity, 1, BlockTypes.RFCavityType.BSCCO.getID()), 'M', "magnetNeodymium").define('B', QMDBlocks.beamline, 'E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)});

        ShapedRecipeBuilder.shaped(MISC, parts.get(PartType.LASER_ASSEMBLY)).pattern("ALA").pattern("SRS").pattern("ALA")
                .define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('L', dischargeLamps.get(LampType.ARGON)).define('S', tag(INGOTS, "silver")).define('R', parts.get(PartType.ROD_ND_YAG))
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)), has(semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDItems.beamMeter).pattern("FFS").pattern("PPC").pattern("FFB")
                .define('F', tag(INGOTS, "ferroboron")).define('S', parts.get(PartType.SCINTILLATOR_PLASTIC)).define('P', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('C', tag(INGOTS, "copper")).define('B', PART_MAP.get("bioplastic"))
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);
// TODO
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.leptonCannon.pattern("EL ").pattern("OBS").pattern("T  ").define('E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('L', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.LASER_ASSEMBLY.getID()), 'O', "tag(INGOTS, "osmiridium").define('B', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.ACCELERATING_BARREL.getID()), 'S', "solenoidCopper").define('T', "tag(INGOTS, "super_alloy"});
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.gluonGun.pattern("TT ").pattern("OBB").pattern("EL ").define('E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('L', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.LASER_ASSEMBLY.getID()), 'O', "tag(INGOTS, "osmiridium").define('B', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.ACCELERATING_BARREL.getID()), 'T', "tag(INGOTS, "super_alloy"});
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.antimatterLauncher.pattern("TT ").pattern("LBB").pattern("EO ").define('E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('L', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.LASER_ASSEMBLY.getID()), 'O', "tag(INGOTS, "osmiridium").define('B', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.ACCELERATING_BARREL.getID()), 'T', "tag(INGOTS, "super_alloy"});
//            ShapedRecipeBuilder.shaped(MISC, QMDArmour.helm_hev.pattern("SBS").pattern("HAH").pattern("SPS").define('P', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('S', "tag(INGOTS, "super_alloy").define('H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.helm_boron_nitride, 'B', NCItems.lithium_ion_cell});
//            ShapedRecipeBuilder.shaped(MISC, QMDArmour.chest_hev.pattern("SBS").pattern("HAH").pattern("SPS").define('P', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('S', "tag(INGOTS, "super_alloy").define('H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.chest_boron_nitride, 'B', NCItems.lithium_ion_cell});
//            ShapedRecipeBuilder.shaped(MISC, QMDArmour.legs_hev.pattern("SBS").pattern("HAH").pattern("SPS").define('P', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('S', "tag(INGOTS, "super_alloy").define('H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.legs_boron_nitride, 'B', NCItems.lithium_ion_cell});
//            ShapedRecipeBuilder.shaped(MISC, QMDArmour.boots_hev.pattern("SBS").pattern("HAH").pattern("SPS").define('P', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('S', "tag(INGOTS, "super_alloy").define('H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.boots_boron_nitride, 'B', NCItems.lithium_ion_cell});
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.basic_drill.pattern(" T ").pattern("TMT").pattern("SBS").define('T', tag(INGOTS, "tungstenCarbide").define('M', "motor").define('S', "tag(INGOTS, "steel").define('B', NCBlocks.voltaic_pile_advanced});
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.advanced_drill.pattern("NTN").pattern("TMT").pattern("SBS").define('T', tag(INGOTS, "tungstenCarbide").define('M', "motor").define('S', "magnetNeodymium").define('B', NCBlocks.lithium_ion_battery_basic, 'N', "gemBoronNitride"});

        ShapelessRecipeBuilder.shapeless(MISC, Items.GUNPOWDER, 8).requires(tag(DUSTS, "coal")).requires(tag(DUSTS, "coal")).requires(tag(DUSTS, "sulfur")).requires(tag(DUSTS, "sulfur")).requires(tag(DUSTS, "sodium_nitrate")).requires(tag(DUSTS, "sodium_nitrate")).requires(tag(DUSTS, "sodium_nitrate")).requires(tag(DUSTS, "sodium_nitrate"))
                .unlockedBy(getHasName(GEM_DUST_MAP.get("sulfur")), has(GEM_DUST_MAP.get("sulfur"))).save(recipeOutput, "gunpowder_from_sulfur");
        ShapelessRecipeBuilder.shapeless(MISC, Items.GUNPOWDER, 8).requires(tag(DUSTS, "charcoal")).requires(tag(DUSTS, "charcoal")).requires(tag(DUSTS, "sulfur")).requires(tag(DUSTS, "sulfur")).requires(tag(DUSTS, "sodium_nitrate")).requires(tag(DUSTS, "sodium_nitrate")).requires(tag(DUSTS, "sodium_nitrate")).requires(tag(DUSTS, "sodium_nitrate"))
                .unlockedBy(getHasName(GEM_DUST_MAP.get("sulfur")), has(GEM_DUST_MAP.get("sulfur"))).save(recipeOutput, "gunpowder_from_sulfur_2");

        ShapelessRecipeBuilder.shapeless(MISC, luminousPaints.get(LuminousPaintType.GREEN), 16).requires(tag(DUSTS, "zinc_sulfide")).requires(tag(DUSTS, "promethium_147")).requires(tag(DUSTS, "copper"))
                .unlockedBy(getHasName(COPPER_DUST), has(COPPER_DUST)).save(recipeOutput, "green_luminous_paint_promethium_147");
        ShapelessRecipeBuilder.shapeless(MISC, luminousPaints.get(LuminousPaintType.GREEN), 16).requires(tag(DUSTS, "zinc_sulfide")).requires(tag(DUSTS, "radium")).requires(tag(DUSTS, "copper"))
                .unlockedBy(getHasName(COPPER_DUST), has(COPPER_DUST)).save(recipeOutput, "green_luminous_paint_radium");

        ShapelessRecipeBuilder.shapeless(MISC, luminousPaints.get(LuminousPaintType.BLUE), 16).requires(tag(DUSTS, "zinc_sulfide")).requires(tag(DUSTS, "promethium_147")).requires(tag(DUSTS, "silver"))
                .unlockedBy(getHasName(COPPER_DUST), has(COPPER_DUST)).save(recipeOutput, "blue_luminous_paint_promethium_147");
        ShapelessRecipeBuilder.shapeless(MISC, luminousPaints.get(LuminousPaintType.BLUE), 16).requires(tag(DUSTS, "zinc_sulfide")).requires(tag(DUSTS, "radium")).requires(tag(DUSTS, "silver"))
                .unlockedBy(getHasName(COPPER_DUST), has(COPPER_DUST)).save(recipeOutput, "blue_luminous_paint_radium");

        ShapelessRecipeBuilder.shapeless(MISC, luminousPaints.get(LuminousPaintType.ORANGE), 16).requires(tag(DUSTS, "zinc_sulfide")).requires(tag(DUSTS, "promethium_147")).requires(tag(DUSTS, "magnesium"))
                .unlockedBy(getHasName(COPPER_DUST), has(COPPER_DUST)).save(recipeOutput, "orange_luminous_paint_promethium_147");
        ShapelessRecipeBuilder.shapeless(MISC, luminousPaints.get(LuminousPaintType.ORANGE), 16).requires(tag(DUSTS, "zinc_sulfide")).requires(tag(DUSTS, "radium")).requires(tag(DUSTS, "magnesium"))
                .unlockedBy(getHasName(COPPER_DUST), has(COPPER_DUST)).save(recipeOutput, "orange_luminous_paint_radium");

        ShapelessRecipeBuilder.shapeless(MISC, vazkii.patchouli.api.PatchouliAPI.get().getBookStack(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "guide"))).requires(Items.BOOK).requires(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK)).save(recipeOutput.withConditions(new ModLoadedCondition("patchouli")));

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.turbineBladeSuperAlloy, 4).pattern("SHS").pattern("SHS").pattern("SHS")
                .define('S', tag(INGOTS, "super_alloy")).define('H', tag(INGOTS, "hsla_steel"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.SUPER_ALLOY)), has(ingotAlloys.get(IngotAlloyType.SUPER_ALLOY))).save(recipeOutput);

        // Fuels
        ShapedRecipeBuilder.shaped(MISC, pellet_copernicium.get(CoperniciumPelletType.MIX_291), 9).pattern("CUU").pattern("UUU").pattern("UUU")
                .define('C', copernicium.get(CoperniciumType._291)).define('U', URANIUM_MAP.get("238"))
                .unlockedBy(getHasName(copernicium.get(CoperniciumType._291)), has(copernicium.get(CoperniciumType._291))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, pellet_copernicium.get(CoperniciumPelletType.MIX_291_C), 9).pattern("CUU").pattern("UUU").pattern("UUU")
                .define('C', copernicium.get(CoperniciumType._291_C)).define('U', URANIUM_MAP.get("238_c"))
                .unlockedBy(getHasName(copernicium.get(CoperniciumType._291_C)), has(copernicium.get(CoperniciumType._291_C))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, fuel_copernicium.get(CoperniciumFuelType.MIX_291_OX), 9).pattern("CUU").pattern("UUU").pattern("UUU")
                .define('C', copernicium.get(CoperniciumType._291_OX)).define('U', URANIUM_MAP.get("238_ox"))
                .unlockedBy(getHasName(copernicium.get(CoperniciumType._291_OX)), has(copernicium.get(CoperniciumType._291_OX))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, fuel_copernicium.get(CoperniciumFuelType.MIX_291_NI), 9).pattern("CUU").pattern("UUU").pattern("UUU")
                .define('C', copernicium.get(CoperniciumType._291_NI)).define('U', URANIUM_MAP.get("238_ni"))
                .unlockedBy(getHasName(copernicium.get(CoperniciumType._291_NI)), has(copernicium.get(CoperniciumType._291_NI))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, fuel_copernicium.get(CoperniciumFuelType.MIX_291_ZA), 9).pattern("CUU").pattern("UUU").pattern("UUU")
                .define('C', copernicium.get(CoperniciumType._291_ZA)).define('U', URANIUM_MAP.get("238_za"))
                .unlockedBy(getHasName(copernicium.get(CoperniciumType._291_ZA)), has(copernicium.get(CoperniciumType._291_ZA))).save(recipeOutput);

        // Coolers TODO
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDItems.part, 8, MaterialTypes.PartType.EMPTY_COOLER.getID()).pattern("STS").pattern("SHS").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('H', "tag(INGOTS, "thermoconducting"});
//        addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.WATER.getID()).pattern(new BucketIngredient("water"), new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.IRON.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "iron").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.REDSTONE.getID()).pattern("III").pattern("ICI").pattern("III").define('I', "dustRedstone").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.QUARTZ.getID()).pattern("III").pattern("ICI").pattern("III").define('I', "gemQuartz").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.OBSIDIAN.getID()).pattern("DID").pattern("ICI").pattern("DID").define('I', "obsidian").define('D', "dustObsidian").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.NETHER_BRICK.getID()).pattern("DID").pattern("ICI").pattern("DID").define('I', Blocks.NETHER_BRICK, 'D', "ingotBrickNether").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.GLOWSTONE.getID()).pattern("III").pattern("ICI").pattern("III").define('I', "dustGlowstone").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.LAPIS.getID()).pattern("III").pattern("ICI").pattern("III").define('I', "gemLapis").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.GOLD.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "gold").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.PRISMARINE.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "gemPrismarine").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.SLIME.getID()).pattern("III").pattern("ICI").pattern("III").define('I', "slimeball").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.END_STONE.getID()).pattern("DID").pattern("ICI").pattern("DID").define('I', "endstone").define('D', "dustEndstone").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.PURPUR.getID()).pattern("DID").pattern("ICI").pattern("DID").define('I', Blocks.PURPUR_BLOCK, 'D', Items.CHORUS_FRUIT_POPPED, 'C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.DIAMOND.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "gemDiamond").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.EMERALD.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "gemEmerald").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler1, 1, CoolerType1.COPPER.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "copper").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.TIN.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "tin").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LEAD.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "lead").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.BORON.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "boron").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LITHIUM.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "lithium").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.MAGNESIUM.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "magnesium").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.MANGANESE.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "manganese").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.ALUMINUM.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "aluminum").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.SILVER.getID()).pattern(" I ").pattern("ICI").pattern(" I ").define('I', "tag(INGOTS, "silver").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.FLUORITE.getID()).pattern("III").pattern("ICI").pattern("III").define('I', "gemFluorite").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.VILLIAUMITE.getID()).pattern("III").pattern("ICI").pattern("III").define('I', "gemVilliaumite").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.CAROBBIITE.getID()).pattern("III").pattern("ICI").pattern("III").define('I', "gemCarobbiite").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.ARSENIC.getID()).pattern("III").pattern("ICI").pattern("III").define('I', "dustArsenic").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LIQUID_NITROGEN.getID()).pattern(new BucketIngredient("liquid_nitrogen"), new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.LIQUID_HELIUM.getID()).pattern(new BucketIngredient("liquid_helium"), new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//        addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2, 1, CoolerType2.CRYOTHEUM.getID()).pattern(new BucketIngredient("cryotheum"), new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID())});
//
//        // Accelerator Controllers
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.linearAcceleratorController.pattern("PEP").pattern("BFB").pattern("PEP").define('P', PART_MAP.get("elite_plating"), 'E', "tag(INGOTS, "extreme").define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.ringAcceleratorController.pattern("PEP").pattern("AFA").pattern("PEP").define('P', PART_MAP.get("elite_plating"), 'E', "tag(INGOTS, "extreme").define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.beamDiverterController.pattern("PTP").pattern("AFA").pattern("PTP").define('P', "PART_MAP.get("advanced_plating")").define('T', "tag(INGOTS, "tough").define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.deceleratorController.pattern("PEP").pattern("AFA").pattern("PEP").define('P', PART_MAP.get("elite_plating"), 'E', "tag(INGOTS, "extreme").define('A', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.beamSplitterController.pattern("PTP").pattern("AFA").pattern("PTP").define('P', "PART_MAP.get("advanced_plating")").define('T', "tag(INGOTS, "super_alloy").define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.massSpectrometerController.pattern("PWP").pattern("AFA").pattern("PWP").define('P', PART_MAP.get("elite_plating"), 'W', "wireBSCCO").define('A', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing});
//
//        //Accelerator Parts
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorCasing, 16).pattern("STS").pattern("TFT").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorComputerPort, 1).pattern("STS").pattern("TFT").pattern("STS").define('S', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('T', "tag(INGOTS, "gold").define('F', QMDBlocks.acceleratorCasing});
//        addShapelessOreRecipe(QMDBlocks.acceleratorCasing.pattern(QMDBlocks.acceleratorGlass});
//        addShapelessOreRecipe(QMDBlocks.acceleratorGlass.pattern(QMDBlocks.acceleratorCasing, "blockGlass"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorBeamPort, 4).pattern("STS").pattern("BFB").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('B', QMDBlocks.beamline, 'F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorSynchrotronPort, 4).pattern("STS").pattern("AFA").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('A', "tag(INGOTS, "aluminum").define('F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorVent, 4).pattern("SIS").pattern("TFT").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "servo"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorEnergyPort, 4).pattern("SIS").pattern("TFT").pattern("SIS").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "tag(INGOTS, "niobium_tin"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorBeam, 3).pattern("SSS").pattern("BBB").pattern("SSS").define('S', tag(INGOTS, "stainless_steel").define('B', QMDBlocks.beamline});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorSource.pattern("AAA").pattern(" CT").pattern("AAA").define('A', "PART_MAP.get("advanced_plating")").define('C', QMDBlocks.acceleratorCasing, 'T', new ItemStack(QMDItems.source, 1, MaterialTypes.SourceType.TUNGSTEN_FILAMENT.getID())});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorLaserIonSource.pattern("LPL").pattern("EIE").pattern("LPL").define('L', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.LASER_ASSEMBLY.getID()), 'P', PART_MAP.get("elite_plating"), 'E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('I', QMDBlocks.acceleratorSource});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorPort, 4).pattern("SHS").pattern("VFV").pattern("SHS").define('S', tag(INGOTS, "stainless_steel").define('H', Blocks.HOPPER, 'V', "servo").define('F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorRedstonePort, 1).pattern("STS").pattern("TFT").pattern("STS").define('S', "dustRedstone").define('T', "tag(INGOTS, "steel").define('F', QMDBlocks.acceleratorCasing});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorIonCollector.pattern("PCP").pattern("CFC").pattern("PCP").define('C', "tag(INGOTS, "copper").define('P', "PART_MAP.get("advanced_plating")").define('F', QMDBlocks.acceleratorCasing});
//
//        //Accelerator magnets
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorMagnet, 2, BlockTypes.MagnetType.COPPER.getID()).pattern("CCC").pattern("STS").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('C', "tag(INGOTS, "copper"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorMagnet, 2, BlockTypes.MagnetType.Aluminium.getID()).pattern("CCC").pattern("STS").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('C', "tag(INGOTS, "aluminum"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorMagnet, 2, BlockTypes.MagnetType.MAGNESIUM_DIBORIDE.getID()).pattern("CCC").pattern("STS").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('C', "tag(INGOTS, "magnesiumDiboride"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorMagnet, 2, BlockTypes.MagnetType.NIOBIUM_TIN.getID()).pattern("CCC").pattern("STS").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('C', "tag(INGOTS, "niobium_tin"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorMagnet, 2, BlockTypes.MagnetType.NIOBIUM_TITANIUM.getID()).pattern("CCC").pattern("STS").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('C', "tag(INGOTS, "niobium_titanium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorMagnet, 2, BlockTypes.MagnetType.BSCCO.getID()).pattern("CCC").pattern("STS").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('C', "wireBSCCO"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorMagnet, 2, BlockTypes.MagnetType.SSFAF.getID()).pattern("CCC").pattern("STS").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('C', "wireSSFAF"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorMagnet, 2, BlockTypes.MagnetType.YBCO.getID()).pattern("CCC").pattern("STS").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('C', "wireYBCO"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorYoke, 4).pattern("IBI").pattern("IBI").pattern("IBI").define('I', "tag(INGOTS, "iron").define('B', "bioplastic"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.acceleratorYoke, 4).pattern("IBI").pattern("IBI").pattern("IBI").define('I', "tag(INGOTS, "iron").define('B', "sheetPlastic"});
//
//        //Accelerator Cavities
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.RFCavity, 4, BlockTypes.RFCavityType.COPPER.getID()).pattern("CCC").pattern("S S").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('C', "tag(INGOTS, "copper"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.RFCavity, 4, BlockTypes.RFCavityType.Aluminium.getID()).pattern("CCC").pattern("S S").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('C', "tag(INGOTS, "aluminum"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.RFCavity, 4, BlockTypes.RFCavityType.MAGNESIUM_DIBORIDE.getID()).pattern("CCC").pattern("S S").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('C', "tag(INGOTS, "magnesiumDiboride"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.RFCavity, 4, BlockTypes.RFCavityType.NIOBIUM_TIN.getID()).pattern("CCC").pattern("S S").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('C', "tag(INGOTS, "niobium_tin"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.RFCavity, 4, BlockTypes.RFCavityType.NIOBIUM_TITANIUM.getID()).pattern("CCC").pattern("S S").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('C', "tag(INGOTS, "niobium_titanium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.RFCavity, 4, BlockTypes.RFCavityType.BSCCO.getID()).pattern("CCC").pattern("S S").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('C', "wireBSCCO"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.RFCavity, 4, BlockTypes.RFCavityType.SSFAF.getID()).pattern("CCC").pattern("S S").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('C', "wireSSFAF"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.RFCavity, 4, BlockTypes.RFCavityType.YBCO.getID()).pattern("CCC").pattern("S S").pattern("CCC").define('S', tag(INGOTS, "stainless_steel").define('C', "wireYBCO"});
//
//        //particle chamber controllers
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.targetChamberController.pattern("PTP").pattern("BFB").pattern("PTP").define('P', PART_MAP.get("elite_plating"), 'T', "tag(INGOTS, "tough").define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('F', QMDBlocks.particleChamberCasing});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.decayChamberController.pattern("PTP").pattern("BFB").pattern("PTP").define('P', PART_MAP.get("elite_plating"), 'T', "tag(INGOTS, "tough").define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('F', NCBlocks.decay_hastener});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.beamDumpController.pattern("PTP").pattern("BFB").pattern("PTP").define('P', PART_MAP.get("elite_plating"), 'T', "tag(INGOTS, "tough").define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('F', "blockCopper"});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.collisionChamberController.pattern("PTP").pattern("BFB").pattern("PTP").define('P', PART_MAP.get("elite_plating"), 'T', "tag(INGOTS, "tough").define('B', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.particleChamberCasing});
//
//        //particle chamber parts
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.particleChamberCasing, 16).pattern("STS").pattern("TFT").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('T', tag(INGOTS, "tungsten").define('F', PART_BLOCK_MAP.get("steel_chassis")});
//        addShapelessOreRecipe(QMDBlocks.particleChamberCasing.pattern(QMDBlocks.particleChamberGlass});
//        addShapelessOreRecipe(QMDBlocks.particleChamberGlass.pattern(QMDBlocks.particleChamberCasing, "blockGlass"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.particleChamberPort, 4).pattern("THT").pattern("VFV").pattern("THT").define('T', tag(INGOTS, "tungsten").define('H', Blocks.HOPPER, 'V', "servo").define('F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.particleChamberEnergyPort, 4).pattern("SIS").pattern("TFT").pattern("SIS").define('S', tag(INGOTS, "stainless_steel").define('T', tag(INGOTS, "tungsten").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "tag(INGOTS, "niobium_tin"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.particleChamberBeam, 3).pattern("STS").pattern("BBB").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('B', QMDBlocks.beamline, 'T', tag(INGOTS, "tungsten"});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamber.pattern("NSN").pattern("NCN").pattern("NSN").define('S', tag(INGOTS, "stainless_steel").define('C', PART_BLOCK_MAP.get("machine_chassis"), 'N', "tag(INGOTS, "niobium_tin"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.particleChamberBeamPort, 4).pattern("STS").pattern("BFB").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('T', tag(INGOTS, "tungsten").define('B', QMDBlocks.beamline, 'F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.particleChamberFluidPort, 4).pattern("SIS").pattern("TFT").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('T', tag(INGOTS, "tungsten").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "servo"});

        // Sources
        ShapedRecipeBuilder.shaped(MISC, IItemParticleAmount.fullItem(new ItemStack(QMDItems.sources.get(SourceType.SODIUM_22).get()))).pattern("BSB").pattern("SSS").pattern("BSB")
                .define('S', isotopeTag("sodium/22")).define('B', PART_MAP.get("bioplastic"))
                .unlockedBy(getHasName(isotopes.get(IsotopeType.SODIUM_22)), has(isotopes.get(IsotopeType.SODIUM_22))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, IItemParticleAmount.fullItem(new ItemStack(QMDItems.sources.get(SourceType.COBALT_60).get()))).pattern("BBB").pattern("BSB").pattern("BBB")
                .define('S', isotopeTag("cobalt/60")).define('B', PART_MAP.get("bioplastic"))
                .unlockedBy(getHasName(isotopes.get(IsotopeType.COBALT_60)), has(isotopes.get(IsotopeType.COBALT_60))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, IItemParticleAmount.fullItem(new ItemStack(QMDItems.sources.get(SourceType.IRIDIUM_192).get()))).pattern("BBB").pattern("BSB").pattern("BBB")
                .define('S', isotopeTag("iridium/192")).define('B', PART_MAP.get("bioplastic"))
                .unlockedBy(getHasName(isotopes.get(IsotopeType.IRIDIUM_192)), has(isotopes.get(IsotopeType.IRIDIUM_192))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, IItemParticleAmount.fullItem(new ItemStack(QMDItems.sources.get(SourceType.CALCIUM_48).get()))).pattern("BSB").pattern("SSS").pattern("BSB")
                .define('S', isotopeTag("calcium/48")).define('B', PART_MAP.get("bioplastic"))
                .unlockedBy(getHasName(isotopes.get(IsotopeType.CALCIUM_48)), has(isotopes.get(IsotopeType.CALCIUM_48))).save(recipeOutput);

// TODO
//        ShapedRecipeBuilder.shaped(MISC, IItemParticleAmount.fullItem(new ItemStack(QMDItems.source, 1, MaterialTypes.SourceType.SODIUM_22.getID())).pattern("BSB").pattern("SSS").pattern("BSB").define('S', isotopeTag("sodium/22").define('B', "sheetPlastic"});
//        ShapedRecipeBuilder.shaped(MISC, IItemParticleAmount.fullItem(new ItemStack(QMDItems.source, 1, MaterialTypes.SourceType.COBALT_60.getID())).pattern("BBB").pattern("BSB").pattern("BBB").define('S', isotopeTag("cobalt/60").define('B', "sheetPlastic"});
//        ShapedRecipeBuilder.shaped(MISC, IItemParticleAmount.fullItem(new ItemStack(QMDItems.source, 1, MaterialTypes.SourceType.IRIDIUM_192.getID())).pattern("BBB").pattern("BSB").pattern("BBB").define('S', isotopeTag("iridium/192").define('B', "sheetPlastic"});
//        ShapedRecipeBuilder.shaped(MISC, IItemParticleAmount.fullItem(new ItemStack(QMDItems.source, 1, MaterialTypes.SourceType.CALCIUM_48.getID())).pattern("BSB").pattern("SSS").pattern("BSB").define('S', isotopeTag("calcium/48").define('B', "sheetPlastic"});
//
//        //detectors
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDItems.part, 4, MaterialTypes.PartType.DETECTOR_CASING.getID()).pattern("STS").pattern("SBS").pattern("STS").define('S', tag(INGOTS, "stainless_steel").define('T', tag(INGOTS, "tungsten").define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.WIRE_CHAMBER_CASING.getID()).pattern("WWW").pattern("ACA").pattern("WWW").define('W', "wireGoldTungsten").define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.DETECTOR_CASING.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.particleChamberDetector, 1, BlockTypes.DetectorType.EM_CALORIMETER.getID()).pattern("SSS").pattern("SCS").pattern("SSS").define('S', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.SCINTILLATOR_PWO.getID()), 'C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.DETECTOR_CASING.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.particleChamberDetector, 1, BlockTypes.DetectorType.HADRON_CALORIMETER.getID()).pattern("SSS").pattern("SCS").pattern("SSS").define('S', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.SCINTILLATOR_PLASTIC.getID()), 'C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.DETECTOR_CASING.getID())});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.particleChamberDetector, 1, BlockTypes.DetectorType.SILLICON_TRACKER.getID()).pattern("BAB").pattern("ACA").pattern("BAB").define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.DETECTOR_CASING.getID())});
//
//        // Containment Controllers
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.exoticContainmentController.pattern("PEP").pattern("BFB").pattern("PEP").define('P', PART_MAP.get("elite_plating"), 'E', "tag(INGOTS, "extreme").define('B', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.vacuumChamberCasing});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.nucleosynthesisChamberController.pattern("PEP").pattern("BFB").pattern("PEP").define('P', PART_MAP.get("elite_plating"), 'E', "tag(INGOTS, "super_alloy").define('B', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.vacuumChamberCasing});
//
//        //Containment Parts
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberCasing, 8).pattern("OTO").pattern("SFS").pattern("OTO").define('S', tag(INGOTS, "stainless_steel").define('T', "tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'O', "tag(INGOTS, "osmiridium"});
//        addShapelessOreRecipe(QMDBlocks.vacuumChamberCasing.pattern(QMDBlocks.vacuumChamberGlass});
//        addShapelessOreRecipe(QMDBlocks.vacuumChamberGlass.pattern(QMDBlocks.vacuumChamberCasing, "blockGlass"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberBeamPort, 4).pattern("OTO").pattern("BFB").pattern("OTO").define('O', "tag(INGOTS, "osmiridium").define('T', "tag(INGOTS, "tough").define('B', QMDBlocks.beamline, 'F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberVent, 4).pattern("OIO").pattern("TFT").pattern("OTO").define('O', "tag(INGOTS, "osmiridium").define('T', "tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "servo"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberEnergyPort, 4).pattern("OIO").pattern("TFT").pattern("OIO").define('O', "tag(INGOTS, "osmiridium").define('T', "tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "wireBSCCO"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberCoil, 2).pattern("CCC").pattern("OOO").pattern("CCC").define('O', "tag(INGOTS, "osmiridium").define('C', "wireBSCCO"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberPort, 4).pattern("OHO").pattern("VFV").pattern("OHO").define('O', "tag(INGOTS, "osmiridium").define('H', Blocks.HOPPER, 'V', "servo").define('F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberLaser).pattern("OEO").pattern("ELL").pattern("OEO").define('O', "tag(INGOTS, "osmiridium").define('L', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.LASER_ASSEMBLY.getID()), 'E', PART_MAP.get("elite_plating")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeaterVent, 4).pattern("OTO").pattern("TFT").pattern("OIO").define('O', "tag(INGOTS, "osmiridium").define('T', "tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "servo"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberRedstonePort, 1).pattern("STS").pattern("TFT").pattern("STS").define('S', tag(DUSTS, "redstone").define('T', "tag(INGOTS, "steel").define('F', QMDBlocks.vacuumChamberCasing});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberFluidPort, 2).pattern("SIS").pattern("TFT").pattern("STS").define('S', "tag(INGOTS, "osmiridium").define('T', "tag(INGOTS, "tough").define('F', QMDBlocks.vacuumChamberCasing, 'I', "servo"});
//
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberBeam, 2).pattern("SBS").pattern("B B").pattern("SBS").define('S', "tag(INGOTS, "super_alloy").define('B', "wireBSCCO"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberPlasmaGlass, 2).pattern("SBS").pattern("BNB").pattern("SBS").define('S', "tag(INGOTS, "super_alloy").define('B', "wireBSCCO").define('N', "gemBoronNitride"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberPlasmaNozzle, 2).pattern("SBS").pattern("BNB").pattern("SBS").define('S', "tag(INGOTS, "super_alloy").define('B', "wireBSCCO").define('N', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.ACCELERATING_BARREL.getID())});
//
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.IRON.getID()).pattern("IOI").pattern("OCO").pattern("IOI").define('I', "tag(INGOTS, "iron").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID()), 'O', "tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.REDSTONE.getID()).pattern("IOI").pattern("OCO").pattern("IOI").define('I', tag(DUSTS, "redstone").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID()), 'O', "tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.QUARTZ.getID()).pattern("IOI").pattern("OCO").pattern("IOI").define('I', "gemQuartz").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID()), 'O', "tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.OBSIDIAN.getID()).pattern("IOI").pattern("OCO").pattern("IOI").define('I', "obsidian").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID()), 'O', "tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.GLOWSTONE.getID()).pattern("IOI").pattern("OCO").pattern("IOI").define('I', tag(DUSTS, "glowstone").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID()), 'O', "tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.LAPIS.getID()).pattern("IOI").pattern("OCO").pattern("IOI").define('I', "gemLapis").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID()), 'O', "tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.GOLD.getID()).pattern("IOI").pattern("OCO").pattern("IOI").define('I', "tag(INGOTS, "gold").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID()), 'O', "tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.DIAMOND.getID()).pattern("IOI").pattern("OCO").pattern("IOI").define('I', "gemDiamond").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER.getID()), 'O', "tag(INGOTS, "osmiridium"});
//
//        // Liquefier parts
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierController).pattern("ITI").pattern("TCT").pattern("ITI").define('I', "tag(INGOTS, "steel").define('C', new ItemStack(NCBlocks.supercooler), 'T', "tag(INGOTS, "thermoconducting"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierNozzle, 4).pattern("I I").pattern("SCS").pattern("I I").define('I', "tag(INGOTS, "steel").define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', tag(INGOTS, "stainless_steel"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierEnergyPort, 4).pattern("ISI").pattern("SCS").pattern("ISI").define('I', "tag(INGOTS, "steel").define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', "tag(INGOTS, "copper"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierPort, 4).pattern("ISI").pattern("MCM").pattern("ISI").define('I', "tag(INGOTS, "steel").define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', tag(INGOTS, "stainless_steel").define('M', "servo"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierCompressor, 4, BlockTypes.CompressorType.COPPER.getID()).pattern("ISI").pattern("MCM").pattern("ISI").define('I', "tag(INGOTS, "steel").define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', "tag(INGOTS, "copper").define('M', "motor"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierCompressor, 4, BlockTypes.CompressorType.NEODYMIUM.getID()).pattern("ISI").pattern("MCM").pattern("ISI").define('I', "tag(INGOTS, "steel").define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', "magnetNeodymium").define('M', "motor"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierCompressor, 4, BlockTypes.CompressorType.SAMARIUM_COBALT.getID()).pattern("ISI").pattern("MCM").pattern("ISI").define('I', "tag(INGOTS, "steel").define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', "magnetSamariumCobalt").define('M', "motor"});

        // Furnace
        for (
                var entry : QMDItems.ingots.entrySet()) {
            if (entry.getKey().getDust() == null) {
                continue;
            }

            smelting(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, Ingredient.of(dusts.get(entry.getKey().getDust())), entry.getValue(), 0, 200, entry.getKey().getSerializedName(), "from_smelting");
            smelting(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, Ingredient.of(dusts.get(entry.getKey().getDust())), entry.getValue(), 0, 200 / 2, entry.getKey().getSerializedName(), "from_blasting");
        }
    }

    public static <T extends AbstractCookingRecipe> void smelting(RecipeOutput recipeOutput, RecipeSerializer<T> recipeSerializer, AbstractCookingRecipe.Factory<T> factory, Ingredient ingredient, ItemLike pResult, float pExp, int pSmeltTime, String pGroup, String pRecipeName) {
        SimpleCookingRecipeBuilder.generic(ingredient, RecipeCategory.MISC, pResult, pExp, pSmeltTime, recipeSerializer, factory).group(pGroup)
                .unlockedBy(getHasName(ingredient.getItems()[0].getItem()), has(ingredient.getItems()[0].getItem()))
                .save(recipeOutput, QMD.MOD_ID + ":" + getItemName(pResult) + "_" + pRecipeName + "_" + getItemName(ingredient.getItems()[0].getItem()));
    }

    private void toolSet(RecipeOutput recipeOutput, ItemLike item, ItemLike sword, ItemLike pickaxe, ItemLike shovel, ItemLike axe, ItemLike hoe) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword)
                .define('#', Items.STICK)
                .define('X', item)
                .pattern("X")
                .pattern("X")
                .pattern("#")
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe)
                .define('#', Items.STICK)
                .define('X', item)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .define('#', Items.STICK)
                .define('X', item)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .define('#', Items.STICK)
                .define('X', item)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe)
                .define('#', Items.STICK)
                .define('X', item)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .unlockedBy(getHasName(item), has(item))
                .save(recipeOutput);
    }
}