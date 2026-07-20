package lach_01298.qmd.datagen;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.datagen.recipe.*;
import lach_01298.qmd.enums.BlockTypes.*;
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
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

import static com.nred.nuclearcraft.datagen.ModItemTagProvider.isotopeTag;
import static com.nred.nuclearcraft.helpers.RecipeHelpers.tag;
import static com.nred.nuclearcraft.registration.BlockRegistration.PROCESSOR_MAP;
import static com.nred.nuclearcraft.registration.BlockRegistration.RTG_MAP;
import static com.nred.nuclearcraft.registration.ItemRegistration.*;
import static lach_01298.qmd.block.QMDBlocks.*;
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
        new AcceleratorCoolingProvider(recipeOutput);
        new AcceleratorSourceProvider(recipeOutput);

        new LiquefierCoolantProvider(recipeOutput);
        new IrradiatorProvider(recipeOutput);
        new CollectorProvider(recipeOutput);
        new MassSpectrometerProvider(recipeOutput);
        new OreLeacherProvider(recipeOutput);

        new TargetChamberProvider(recipeOutput);
        new CollisionChamberProvider(recipeOutput);
        new DecayChamberProvider(recipeOutput);
        new BeamDumpProvider(recipeOutput);

        NeohaulRecipeProvider.buildRecipes(recipeOutput);

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

        ShapedRecipeBuilder.shaped(MISC, parts.get(PartType.ACCELERATING_BARREL)).pattern("WMW").pattern("BEB").pattern("WMW")
                .define('W', QMDBlocks.RFCavities.get(RFCavityType.BSCCO)).define('M', parts.get(PartType.MAGNET_ND)).define('B', QMDBlocks.beamline).define('E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR))
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.ELITE_PROCESSOR)), has(semiconductors.get(SemiconductorType.ELITE_PROCESSOR))).save(recipeOutput);


        ShapedRecipeBuilder.shaped(MISC, parts.get(PartType.LASER_ASSEMBLY)).pattern("ALA").pattern("SRS").pattern("ALA")
                .define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('L', dischargeLamps.get(LampType.ARGON)).define('S', tag(INGOTS, "silver")).define('R', parts.get(PartType.ROD_ND_YAG))
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)), has(semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDItems.beamMeter).pattern("FFS").pattern("PPC").pattern("FFB")
                .define('F', tag(INGOTS, "ferroboron")).define('S', parts.get(PartType.SCINTILLATOR_PLASTIC)).define('P', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('C', tag(INGOTS, "copper")).define('B', PART_MAP.get("bioplastic"))
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);
// TODO
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.leptonCannon.pattern("EL ").pattern("OBS").pattern("T  ").define('E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('L', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.LASER_ASSEMBLY)), 'O', tag(INGOTS, "osmiridium").define('B', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.ACCELERATING_BARREL)), 'S', "solenoidCopper").define('T', tag(INGOTS, "super_alloy"});
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.gluonGun.pattern("TT ").pattern("OBB").pattern("EL ").define('E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('L', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.LASER_ASSEMBLY)), 'O', tag(INGOTS, "osmiridium").define('B', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.ACCELERATING_BARREL)), 'T', tag(INGOTS, "super_alloy"});
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.antimatterLauncher.pattern("TT ").pattern("LBB").pattern("EO ").define('E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('L', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.LASER_ASSEMBLY)), 'O', tag(INGOTS, "osmiridium").define('B', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.ACCELERATING_BARREL)), 'T', tag(INGOTS, "super_alloy"});
//            ShapedRecipeBuilder.shaped(MISC, QMDArmour.helm_hev.pattern("SBS").pattern("HAH").pattern("SPS").define('P', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('S', tag(INGOTS, "super_alloy").define('H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.helm_boron_nitride, 'B', NCItems.lithium_ion_cell});
//            ShapedRecipeBuilder.shaped(MISC, QMDArmour.chest_hev.pattern("SBS").pattern("HAH").pattern("SPS").define('P', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('S', tag(INGOTS, "super_alloy").define('H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.chest_boron_nitride, 'B', NCItems.lithium_ion_cell});
//            ShapedRecipeBuilder.shaped(MISC, QMDArmour.legs_hev.pattern("SBS").pattern("HAH").pattern("SPS").define('P', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('S', tag(INGOTS, "super_alloy").define('H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.legs_boron_nitride, 'B', NCItems.lithium_ion_cell});
//            ShapedRecipeBuilder.shaped(MISC, QMDArmour.boots_hev.pattern("SBS").pattern("HAH").pattern("SPS").define('P', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('S', tag(INGOTS, "super_alloy").define('H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.boots_boron_nitride, 'B', NCItems.lithium_ion_cell});
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.basic_drill.pattern(" T ").pattern("TMT").pattern("SBS").define('T', tag(INGOTS, "tungstenCarbide").define('M', "motor").define('S', tag(INGOTS, "steel").define('B', NCBlocks.voltaic_pile_advanced});
//            ShapedRecipeBuilder.shaped(MISC, QMDItems.advanced_drill.pattern("NTN").pattern("TMT").pattern("SBS").define('T', tag(INGOTS, "tungstenCarbide").define('M', "motor").define('S', "magnetNeodymium").define('B', NCBlocks.lithium_ion_battery_basic, 'N', tag(Tags.Items.GEMS, "boronNitride"});

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

        // Coolers
        ShapedRecipeBuilder.shaped(MISC, parts.get(PartType.EMPTY_COOLER), 8).pattern("STS").pattern("SHS").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('H', tag(INGOTS, "thermoconducting"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(MISC, acceleratorCoolers.get(CoolerType.WATER)).requires(Tags.Items.BUCKETS_WATER).requires(parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.IRON)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "iron")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.REDSTONE)).pattern("III").pattern("ICI")
                .pattern("III").define('I', tag(Tags.Items.DUSTS, "redstone")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.QUARTZ)).pattern("III").pattern("ICI")
                .pattern("III").define('I', tag(Tags.Items.GEMS, "quartz")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.OBSIDIAN)).pattern("DID").pattern("ICI").pattern("DID")
                .define('I', Items.OBSIDIAN).define('D', tag(Tags.Items.DUSTS, "obsidian")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.NETHER_BRICK)).pattern("DID").pattern("ICI").pattern("DID")
                .define('I', Blocks.NETHER_BRICKS).define('D', Tags.Items.BRICKS_NETHER).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.GLOWSTONE)).pattern("III").pattern("ICI").pattern("III")
                .define('I', tag(Tags.Items.DUSTS, "glowstone")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.LAPIS)).pattern("III").pattern("ICI").pattern("III")
                .define('I', tag(Tags.Items.GEMS, "lapis")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.GOLD)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "gold")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.PRISMARINE)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(Tags.Items.GEMS, "prismarine")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.SLIME)).pattern("III").pattern("ICI").pattern("III")
                .define('I', Tags.Items.SLIME_BALLS).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.END_STONE)).pattern("DID").pattern("ICI").pattern("DID")
                .define('I', Blocks.END_STONE).define('D', tag(Tags.Items.DUSTS, "endstone")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.PURPUR)).pattern("DID").pattern("ICI").pattern("DID")
                .define('I', Blocks.PURPUR_BLOCK).define('D', Items.POPPED_CHORUS_FRUIT).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.DIAMOND)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(Tags.Items.GEMS, "diamond")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.EMERALD)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(Tags.Items.GEMS, "emerald")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.COPPER)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "copper")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.TIN)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "tin")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.LEAD)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "lead")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.BORON)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "boron")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.LITHIUM)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "lithium")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.MAGNESIUM)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "magnesium")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.MANGANESE)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "manganese")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.ALUMINUM)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "aluminum")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.SILVER)).pattern(" I ").pattern("ICI").pattern(" I ")
                .define('I', tag(INGOTS, "silver")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.FLUORITE)).pattern("III").pattern("ICI").pattern("III")
                .define('I', tag(Tags.Items.GEMS, "fluorite")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.VILLIAUMITE)).pattern("III").pattern("ICI").pattern("III")
                .define('I', tag(Tags.Items.GEMS, "villiaumite")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.CAROBBIITE)).pattern("III").pattern("ICI").pattern("III")
                .define('I', tag(Tags.Items.GEMS, "carobbiite")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCoolers.get(CoolerType.ARSENIC)).pattern("III").pattern("ICI").pattern("III")
                .define('I', tag(Tags.Items.DUSTS, "arsenic")).define('C', parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(MISC, acceleratorCoolers.get(CoolerType.LIQUID_NITROGEN)).requires(tag(Tags.Items.BUCKETS, "liquid_nitrogen")).requires(parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(MISC, acceleratorCoolers.get(CoolerType.LIQUID_HELIUM)).requires(tag(Tags.Items.BUCKETS, "liquid_helium")).requires(parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(MISC, acceleratorCoolers.get(CoolerType.CRYOTHEUM)).requires(tag(Tags.Items.BUCKETS, "cryotheum")).requires(parts.get(PartType.EMPTY_COOLER))
                .unlockedBy(getHasName(parts.get(PartType.EMPTY_COOLER)), has(parts.get(PartType.EMPTY_COOLER))).save(recipeOutput);

        // Accelerator Controllers
        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.linearAcceleratorController).pattern("PEP").pattern("BFB").pattern("PEP")
                .define('P', PART_MAP.get("elite_plating")).define('E', tag(INGOTS, "extreme")).define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing)
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.ringAcceleratorController).pattern("PEP").pattern("AFA").pattern("PEP")
                .define('P', PART_MAP.get("elite_plating")).define('E', tag(INGOTS, "extreme")).define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing)
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.beamDiverterController).pattern("PTP").pattern("AFA").pattern("PTP")
                .define('P', PART_MAP.get("advanced_plating")).define('T', tag(INGOTS, "tough")).define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing)
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.deceleratorController).pattern("PEP").pattern("AFA").pattern("PEP")
                .define('P', PART_MAP.get("elite_plating")).define('E', tag(INGOTS, "extreme")).define('A', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing)
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.beamSplitterController).pattern("PTP").pattern("AFA").pattern("PTP")
                .define('P', PART_MAP.get("advanced_plating")).define('T', tag(INGOTS, "super_alloy")).define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing)
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.massSpectrometerController).pattern("PWP").pattern("AFA").pattern("PWP")
                .define('P', PART_MAP.get("elite_plating")).define('W', parts.get(PartType.WIRE_BSCCO)).define('A', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.acceleratorCasing)
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);

        // Accelerator Parts
        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorCasing, 16).pattern("STS").pattern("TFT").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('F', PART_BLOCK_MAP.get("steel_chassis"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorComputerPort, 1).pattern("STS").pattern("TFT").pattern("STS")
                .define('S', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('T', tag(INGOTS, "gold")).define('F', QMDBlocks.acceleratorCasing)
                .unlockedBy(getHasName(QMDBlocks.acceleratorCasing), has(QMDBlocks.acceleratorCasing)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(MISC, QMDBlocks.acceleratorCasing).requires(acceleratorGlass)
                .unlockedBy(getHasName(acceleratorGlass), has(acceleratorGlass)).save(recipeOutput, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "accelerator_casing_from_glass"));

        ShapelessRecipeBuilder.shapeless(MISC, acceleratorGlass).requires(acceleratorCasing).requires(Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(QMDBlocks.acceleratorCasing), has(QMDBlocks.acceleratorCasing)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorBeamPort, 4).pattern("STS").pattern("BFB").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('B', QMDBlocks.beamline).define('F', PART_BLOCK_MAP.get("steel_chassis"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorSynchrotronPort, 4).pattern("STS").pattern("AFA").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('A', tag(INGOTS, "aluminum")).define('F', PART_BLOCK_MAP.get("steel_chassis"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorVent, 4).pattern("SIS").pattern("TFT").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('F', PART_BLOCK_MAP.get("steel_chassis")).define('I', PART_MAP.get("servomechanism"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorEnergyPort, 4).pattern("SIS").pattern("TFT").pattern("SIS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('F', PART_BLOCK_MAP.get("steel_chassis")).define('I', tag(INGOTS, "niobium_tin"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorBeam, 3).pattern("SSS").pattern("BBB").pattern("SSS")
                .define('S', tag(INGOTS, "stainless_steel")).define('B', QMDBlocks.beamline)
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorSource).pattern("AAA").pattern(" CT").pattern("AAA")
                .define('A', PART_MAP.get("advanced_plating")).define('C', QMDBlocks.acceleratorCasing).define('T', QMDItems.sources.get(SourceType.TUNGSTEN_FILAMENT))
                .unlockedBy(getHasName(QMDBlocks.acceleratorCasing), has(QMDBlocks.acceleratorCasing)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorLaserIonSource).pattern("LPL").pattern("EIE").pattern("LPL").define('L', parts.get(PartType.LASER_ASSEMBLY)).define('P', PART_MAP.get("elite_plating")).define('E', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('I', QMDBlocks.acceleratorSource)
                .unlockedBy(getHasName(QMDBlocks.acceleratorSource), has(QMDBlocks.acceleratorSource)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorPort, 4).pattern("SHS").pattern("VFV").pattern("SHS")
                .define('S', tag(INGOTS, "stainless_steel")).define('H', Blocks.HOPPER).define('V', PART_MAP.get("servomechanism")).define('F', PART_BLOCK_MAP.get("steel_chassis"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorRedstonePort, 1).pattern("STS").pattern("TFT").pattern("STS")
                .define('S', tag(Tags.Items.DUSTS, "redstone")).define('T', tag(INGOTS, "steel")).define('F', QMDBlocks.acceleratorCasing)
                .unlockedBy(getHasName(QMDBlocks.acceleratorCasing), has(QMDBlocks.acceleratorCasing)).save(recipeOutput);


        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorIonCollector).pattern("PCP").pattern("CFC").pattern("PCP")
                .define('C', tag(INGOTS, "copper")).define('P', PART_MAP.get("advanced_plating")).define('F', QMDBlocks.acceleratorCasing)
                .unlockedBy(getHasName(QMDBlocks.acceleratorCasing), has(QMDBlocks.acceleratorCasing)).save(recipeOutput);

        // Accelerator magnets
        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorMagnets.get(MagnetType.COPPER), 2).pattern("CCC").pattern("STS").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('C', tag(INGOTS, "copper"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorMagnets.get(MagnetType.Aluminium), 2).pattern("CCC").pattern("STS").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('C', tag(INGOTS, "aluminum"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorMagnets.get(MagnetType.MAGNESIUM_DIBORIDE), 2).pattern("CCC").pattern("STS").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('C', tag(INGOTS, "magnesium_diboride"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorMagnets.get(MagnetType.NIOBIUM_TIN), 2).pattern("CCC").pattern("STS").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('C', tag(INGOTS, "niobium_tin"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorMagnets.get(MagnetType.NIOBIUM_TITANIUM), 2).pattern("CCC").pattern("STS").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('C', tag(INGOTS, "niobium_titanium"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorMagnets.get(MagnetType.BSCCO), 2).pattern("CCC").pattern("STS").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('C', parts.get(PartType.WIRE_BSCCO))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorMagnets.get(MagnetType.SSFAF), 2).pattern("CCC").pattern("STS").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('C', parts.get(PartType.WIRE_SSFAF))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorMagnets.get(MagnetType.YBCO), 2).pattern("CCC").pattern("STS").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tough")).define('C', parts.get(PartType.WIRE_YBCO))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.acceleratorYoke, 4).pattern("IBI").pattern("IBI").pattern("IBI")
                .define('I', tag(INGOTS, "iron")).define('B', PART_MAP.get("bioplastic"))
                .unlockedBy(getHasName(PART_MAP.get("bioplastic")), has(PART_MAP.get("bioplastic"))).save(recipeOutput);

        // Accelerator Cavities
        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.RFCavities.get(RFCavityType.COPPER), 4).pattern("CCC").pattern("S S").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('C', tag(INGOTS, "copper"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.RFCavities.get(RFCavityType.Aluminium), 4).pattern("CCC").pattern("S S").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('C', tag(INGOTS, "aluminum"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.RFCavities.get(RFCavityType.MAGNESIUM_DIBORIDE), 4).pattern("CCC").pattern("S S").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('C', tag(INGOTS, "magnesium_diboride"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.RFCavities.get(RFCavityType.NIOBIUM_TIN), 4).pattern("CCC").pattern("S S").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('C', tag(INGOTS, "niobium_tin"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.RFCavities.get(RFCavityType.NIOBIUM_TITANIUM), 4).pattern("CCC").pattern("S S").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('C', tag(INGOTS, "niobium_titanium"))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.RFCavities.get(RFCavityType.BSCCO), 4).pattern("CCC").pattern("S S").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('C', parts.get(PartType.WIRE_BSCCO))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.RFCavities.get(RFCavityType.SSFAF), 4).pattern("CCC").pattern("S S").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('C', parts.get(PartType.WIRE_SSFAF))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.RFCavities.get(RFCavityType.YBCO), 4).pattern("CCC").pattern("S S").pattern("CCC")
                .define('S', tag(INGOTS, "stainless_steel")).define('C', parts.get(PartType.WIRE_YBCO))
                .unlockedBy(getHasName(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL)), has(ingotAlloys.get(IngotAlloyType.STAINLESS_STEEL))).save(recipeOutput);

        // Particle Chamber Controllers
        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.targetChamberController, 1).pattern("PTP").pattern("BFB").pattern("PTP")
                .define('P', PART_MAP.get("elite_plating")).define('T', tag(INGOTS, "tough")).define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('F', QMDBlocks.particleChamberCasing)
                .unlockedBy(getHasName(QMDBlocks.particleChamberCasing), has(QMDBlocks.particleChamberCasing)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.decayChamberController, 1).pattern("PTP").pattern("BFB").pattern("PTP")
                .define('P', PART_MAP.get("elite_plating")).define('T', tag(INGOTS, "tough")).define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('F', PROCESSOR_MAP.get("decay_hastener"))
                .unlockedBy(getHasName(PROCESSOR_MAP.get("decay_hastener")), has(PROCESSOR_MAP.get("decay_hastener"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.beamDumpController, 1).pattern("PTP").pattern("BFB").pattern("PTP")
                .define('P', PART_MAP.get("elite_plating")).define('T', tag(INGOTS, "tough")).define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('F', Items.COPPER_BLOCK)
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.collisionChamberController, 1).pattern("PTP").pattern("BFB").pattern("PTP")
                .define('P', PART_MAP.get("elite_plating")).define('T', tag(INGOTS, "tough")).define('B', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.particleChamberCasing)
                .unlockedBy(getHasName(QMDBlocks.particleChamberCasing), has(QMDBlocks.particleChamberCasing)).save(recipeOutput);


        // Particle Chamber Parts
        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamberCasing, 16).pattern("STS").pattern("TFT").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tungsten")).define('F', PART_BLOCK_MAP.get("steel_chassis"))
                .unlockedBy(getHasName(PART_BLOCK_MAP.get("steel_chassis")), has(PART_BLOCK_MAP.get("steel_chassis"))).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(MISC, QMDBlocks.particleChamberCasing).requires(QMDBlocks.particleChamberGlass)
                .unlockedBy(getHasName(QMDBlocks.particleChamberGlass), has(QMDBlocks.particleChamberGlass)).save(recipeOutput, "particle_chamber_casing_from_glass");
        ShapelessRecipeBuilder.shapeless(MISC, QMDBlocks.particleChamberGlass).requires(QMDBlocks.particleChamberCasing).requires(Tags.Items.GLASS_BLOCKS)
                .unlockedBy(getHasName(QMDBlocks.particleChamberCasing), has(QMDBlocks.particleChamberCasing)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamberPort, 4).pattern("THT").pattern("VFV").pattern("THT")
                .define('T', tag(INGOTS, "tungsten")).define('H', Blocks.HOPPER).define('V', PART_MAP.get("servomechanism")).define('F', PART_BLOCK_MAP.get("steel_chassis"))
                .unlockedBy(getHasName(PART_BLOCK_MAP.get("steel_chassis")), has(PART_BLOCK_MAP.get("steel_chassis"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamberEnergyPort, 4).pattern("SIS").pattern("TFT").pattern("SIS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tungsten")).define('F', PART_BLOCK_MAP.get("steel_chassis")).define('I', tag(INGOTS, "niobium_tin"))
                .unlockedBy(getHasName(PART_BLOCK_MAP.get("steel_chassis")), has(PART_BLOCK_MAP.get("steel_chassis"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamberBeam, 3).pattern("STS").pattern("BBB").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('B', QMDBlocks.beamline).define('T', tag(INGOTS, "tungsten"))
                .unlockedBy(getHasName(QMDBlocks.beamline), has(QMDBlocks.beamline)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamber, 1).pattern("NSN").pattern("NCN").pattern("NSN")
                .define('S', tag(INGOTS, "stainless_steel")).define('C', PART_BLOCK_MAP.get("machine_chassis")).define('N', tag(INGOTS, "niobium_tin"))
                .unlockedBy(getHasName(PART_BLOCK_MAP.get("machine_chassis")), has(PART_BLOCK_MAP.get("machine_chassis"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamberBeamPort, 4).pattern("STS").pattern("BFB").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tungsten")).define('B', QMDBlocks.beamline).define('F', PART_BLOCK_MAP.get("steel_chassis"))
                .unlockedBy(getHasName(PART_BLOCK_MAP.get("steel_chassis")), has(PART_BLOCK_MAP.get("steel_chassis"))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamberFluidPort, 4).pattern("SIS").pattern("TFT").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tungsten")).define('F', PART_BLOCK_MAP.get("steel_chassis")).define('I', PART_MAP.get("servomechanism"))
                .unlockedBy(getHasName(PART_BLOCK_MAP.get("steel_chassis")), has(PART_BLOCK_MAP.get("steel_chassis"))).save(recipeOutput);

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

        // Detectors
        ShapedRecipeBuilder.shaped(MISC, parts.get(PartType.DETECTOR_CASING), 4).pattern("STS").pattern("SBS").pattern("STS")
                .define('S', tag(INGOTS, "stainless_steel")).define('T', tag(INGOTS, "tungsten")).define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR))
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.BASIC_PROCESSOR)), has(semiconductors.get(SemiconductorType.BASIC_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, parts.get(PartType.WIRE_CHAMBER_CASING), 1).pattern("WWW").pattern("ACA").pattern("WWW")
                .define('W', parts.get(PartType.WIRE_GOLD_TUNGSTEN)).define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('C', parts.get(PartType.DETECTOR_CASING))
                .unlockedBy(getHasName(semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)), has(semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamberDetectors.get(DetectorType.EM_CALORIMETER), 1).pattern("SSS").pattern("SCS").pattern("SSS")
                .define('S', parts.get(PartType.SCINTILLATOR_PWO)).define('C', parts.get(PartType.DETECTOR_CASING))
                .unlockedBy(getHasName(parts.get(PartType.DETECTOR_CASING)), has(parts.get(PartType.DETECTOR_CASING))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamberDetectors.get(DetectorType.HADRON_CALORIMETER), 1).pattern("SSS").pattern("SCS").pattern("SSS")
                .define('S', parts.get(PartType.SCINTILLATOR_PLASTIC)).define('C', parts.get(PartType.DETECTOR_CASING))
                .unlockedBy(getHasName(parts.get(PartType.DETECTOR_CASING)), has(parts.get(PartType.DETECTOR_CASING))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.particleChamberDetectors.get(DetectorType.SILLICON_TRACKER), 1).pattern("BAB").pattern("ACA").pattern("BAB")
                .define('B', semiconductors.get(SemiconductorType.BASIC_PROCESSOR)).define('A', semiconductors.get(SemiconductorType.ADVANCED_PROCESSOR)).define('C', parts.get(PartType.DETECTOR_CASING))
                .unlockedBy(getHasName(parts.get(PartType.DETECTOR_CASING)), has(parts.get(PartType.DETECTOR_CASING))).save(recipeOutput);

//        // Containment Controllers TODO
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.exoticContainmentController.pattern("PEP").pattern("BFB").pattern("PEP").define('P', PART_MAP.get("elite_plating"), 'E', tag(INGOTS, "extreme").define('B', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.vacuumChamberCasing});
//        ShapedRecipeBuilder.shaped(MISC, QMDBlocks.nucleosynthesisChamberController.pattern("PEP").pattern("BFB").pattern("PEP").define('P', PART_MAP.get("elite_plating"), 'E', tag(INGOTS, "super_alloy").define('B', semiconductors.get(SemiconductorType.ELITE_PROCESSOR)).define('F', QMDBlocks.vacuumChamberCasing});
//
//        //Containment Parts
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberCasing, 8).pattern("OTO").pattern("SFS").pattern("OTO").define('S', tag(INGOTS, "stainless_steel").define('T', tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'O', tag(INGOTS, "osmiridium"});
//        addShapelessOreRecipe(QMDBlocks.vacuumChamberCasing.pattern(QMDBlocks.vacuumChamberGlass});
//        addShapelessOreRecipe(QMDBlocks.vacuumChamberGlass.pattern(QMDBlocks.vacuumChamberCasing, "blockGlass"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberBeamPort, 4).pattern("OTO").pattern("BFB").pattern("OTO").define('O', tag(INGOTS, "osmiridium").define('T', tag(INGOTS, "tough").define('B', QMDBlocks.beamline, 'F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberVent, 4).pattern("OIO").pattern("TFT").pattern("OTO").define('O', tag(INGOTS, "osmiridium").define('T', tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "servo"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberEnergyPort, 4).pattern("OIO").pattern("TFT").pattern("OIO").define('O', tag(INGOTS, "osmiridium").define('T', tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "wireBSCCO"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberCoil, 2).pattern("CCC").pattern("OOO").pattern("CCC").define('O', tag(INGOTS, "osmiridium").define('C', "wireBSCCO"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberPort, 4).pattern("OHO").pattern("VFV").pattern("OHO").define('O', tag(INGOTS, "osmiridium").define('H', Blocks.HOPPER, 'V', "servo").define('F', PART_BLOCK_MAP.get("steel_chassis")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberLaser).pattern("OEO").pattern("ELL").pattern("OEO").define('O', tag(INGOTS, "osmiridium").define('L', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.LASER_ASSEMBLY)), 'E', PART_MAP.get("elite_plating")});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeaterVent, 4).pattern("OTO").pattern("TFT").pattern("OIO").define('O', tag(INGOTS, "osmiridium").define('T', tag(INGOTS, "tough").define('F', PART_BLOCK_MAP.get("steel_chassis"), 'I', "servo"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberRedstonePort, 1).pattern("STS").pattern("TFT").pattern("STS").define('S', tag(DUSTS, "redstone").define('T', tag(INGOTS, "steel").define('F', QMDBlocks.vacuumChamberCasing});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberFluidPort, 2).pattern("SIS").pattern("TFT").pattern("STS").define('S', tag(INGOTS, "osmiridium").define('T', tag(INGOTS, "tough").define('F', QMDBlocks.vacuumChamberCasing, 'I', "servo"});
//
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberBeam, 2).pattern("SBS").pattern("B B").pattern("SBS").define('S', tag(INGOTS, "super_alloy").define('B', "wireBSCCO"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberPlasmaGlass, 2).pattern("SBS").pattern("BNB").pattern("SBS").define('S', tag(INGOTS, "super_alloy").define('B', "wireBSCCO").define('N', tag(Tags.Items.GEMS, "boronNitride"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberPlasmaNozzle, 2).pattern("SBS").pattern("BNB").pattern("SBS").define('S', tag(INGOTS, "super_alloy").define('B', "wireBSCCO").define('N', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.ACCELERATING_BARREL))});
//
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.IRON)).pattern("IOI").pattern("OCO").pattern("IOI").define('I', tag(INGOTS, "iron")).define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER)), 'O', tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.REDSTONE)).pattern("IOI").pattern("OCO").pattern("IOI").define('I', tag(DUSTS, "redstone")).define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER)), 'O', tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.QUARTZ)).pattern("IOI").pattern("OCO").pattern("IOI").define('I', tag(Tags.Items.GEMS, "quartz")).define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER)), 'O', tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.OBSIDIAN)).pattern("IOI").pattern("OCO").pattern("IOI").define('I', "obsidian").define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER)), 'O', tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.GLOWSTONE)).pattern("IOI").pattern("OCO").pattern("IOI").define('I', tag(DUSTS, "glowstone")).define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER)), 'O', tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.LAPIS)).pattern("IOI").pattern("OCO").pattern("IOI").define('I', tag(Tags.Items.GEMS, "lapis")).define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER)), 'O', tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.GOLD)).pattern("IOI").pattern("OCO").pattern("IOI").define('I', tag(INGOTS, "gold")).define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER)), 'O', tag(INGOTS, "osmiridium"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.vacuumChamberHeater, 1, BlockTypes.HeaterType.DIAMOND)).pattern("IOI").pattern("OCO").pattern("IOI").define('I', tag(Tags.Items.GEMS, "diamond")).define('C', new ItemStack(QMDItems.part, 1, MaterialTypes.PartType.EMPTY_COOLER)), 'O', tag(INGOTS, "osmiridium"});
//
//        // Liquefier parts
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierController).pattern("ITI").pattern("TCT").pattern("ITI").define('I', tag(INGOTS, "steel")).define('C', new ItemStack(NCBlocks.supercooler), 'T', tag(INGOTS, "thermoconducting"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierNozzle, 4).pattern("I I").pattern("SCS").pattern("I I").define('I', tag(INGOTS, "steel")).define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', tag(INGOTS, "stainless_steel"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierEnergyPort, 4).pattern("ISI").pattern("SCS").pattern("ISI").define('I', tag(INGOTS, "steel")).define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', tag(INGOTS, "copper"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierPort, 4).pattern("ISI").pattern("MCM").pattern("ISI").define('I', tag(INGOTS, "steel")).define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', tag(INGOTS, "stainless_steel").define('M', "servo"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierCompressor, 4, BlockTypes.CompressorType.COPPER)).pattern("ISI").pattern("MCM").pattern("ISI").define('I', tag(INGOTS, "steel")).define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', tag(INGOTS, "copper").define('M', "motor"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierCompressor, 4, BlockTypes.CompressorType.NEODYMIUM)).pattern("ISI").pattern("MCM").pattern("ISI").define('I', tag(INGOTS, "steel")).define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', "magnetNeodymium").define('M', "motor"});
//        ShapedRecipeBuilder.shaped(MISC, new ItemStack(QMDBlocks.liquefierCompressor, 4, BlockTypes.CompressorType.SAMARIUM_COBALT)).pattern("ISI").pattern("MCM").pattern("ISI").define('I', tag(INGOTS, "steel")).define('C', PART_BLOCK_MAP.get("steel_chassis"), 'S', "magnetSamariumCobalt").define('M', "motor"});

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