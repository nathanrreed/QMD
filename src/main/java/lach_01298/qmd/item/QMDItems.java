package lach_01298.qmd.item;

import com.nred.nuclearcraft.item.NCFoodItem;
import com.nred.nuclearcraft.item.NCItem;
import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.enums.MaterialTypes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

import static lach_01298.qmd.block.QMDBlocks.*;

public class QMDItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(QMD.MOD_ID);

    public static final Map<DustType, DeferredItem<Item>> dusts = new HashMap<>();
    public static final Map<IngotType, DeferredItem<Item>> ingots = new HashMap<>();
    public static final Map<IngotAlloyType, DeferredItem<Item>> ingotAlloys = new HashMap<>();
    public static final Map<IsotopeType, DeferredItem<Item>> isotopes = new HashMap<>();
    public static final Map<PartType, DeferredItem<Item>> parts = new HashMap<>();
    public static final Map<SemiconductorType, DeferredItem<Item>> semiconductors = new HashMap<>();
    public static final Map<ChemicalDustType, DeferredItem<Item>> chemicalDusts = new HashMap<>();
    public static final Map<FissionWasteType, DeferredItem<Item>> fissionWastes = new HashMap<>();
    public static final Map<SpallationWasteType, DeferredItem<Item>> spallationWastes = new HashMap<>();

    public static Map<SourceType, DeferredItem<Item>> sources = new HashMap<>();

    public static Tier TUNGSTEN_CARBIDE;

    public static DeferredItem<Item> sword_tungsten_carbide;
    public static DeferredItem<Item> pickaxe_tungsten_carbide;
    public static DeferredItem<Item> shovel_tungsten_carbide;
    public static DeferredItem<Item> axe_tungsten_carbide;
    public static DeferredItem<Item> hoe_tungsten_carbide;

    public static Map<CellType, DeferredItem<Item>> cells = new HashMap<>();

    public static DeferredItem<Item> flesh;
    public static DeferredItem<Item> potassiumIodineTablet;
    public static Map<LuminousPaintType, DeferredItem<Item>> luminousPaints = new HashMap<>();

//    public static DeferredItem<Item> leptonCannon;
//    public static DeferredItem<Item> gluonGun;
//    public static DeferredItem<Item> antimatterLauncher;

    public static DeferredItem<Item> beamMeter;
//    public static DeferredItem<Item> basic_drill;
//    public static DeferredItem<Item> advanced_drill;

    public static Map<CoperniciumType, DeferredItem<Item>> copernicium = new HashMap<>();
    public static Map<CoperniciumPelletType, DeferredItem<Item>> pellet_copernicium = new HashMap<>();
    public static Map<CoperniciumFuelType, DeferredItem<Item>> fuel_copernicium = new HashMap<>();
    public static Map<CoperniciumDepletedFuelType, DeferredItem<Item>> depleted_fuel_copernicium = new HashMap<>();

    public static void init() {
        for (DustType type : DustType.values()) {
            dusts.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_dust", () -> new NCItem(new Properties())));
        }
        for (IngotType type : IngotType.values()) {
            ingots.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_ingot", () -> new NCItem(new Properties())));
        }
        for (IngotAlloyType type : IngotAlloyType.values()) {
            ingotAlloys.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_alloy", () -> new NCItem(new Properties())));
        }
        for (SourceType type : SourceType.values()) {
            sources.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_source", () -> new ItemSource(type)));
        }
        for (IsotopeType type : IsotopeType.values()) {
            isotopes.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_isotope", () -> new NCItem(new Properties())));
        }
        for (PartType type : PartType.values()) {
            parts.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_part", () -> new NCItem(new Properties())));
        }
        for (SemiconductorType type : SemiconductorType.values()) {
            semiconductors.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_semiconductor", () -> new NCItem(new Properties())));
        }
        for (ChemicalDustType type : ChemicalDustType.values()) {
            chemicalDusts.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_chemical_dust", () -> new NCItem(new Properties())));
        }
        for (FissionWasteType type : FissionWasteType.values()) {
            fissionWastes.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_waste_fission", () -> new NCItem(new Properties())));
        }
        for (SpallationWasteType type : SpallationWasteType.values()) {
            spallationWastes.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_waste_spallation", () -> new NCItem(new Properties())));
        }

        TUNGSTEN_CARBIDE = toolMaterial(0, ingotAlloys.get(IngotAlloyType.TUNGSTEN_CARBIDE));

        sword_tungsten_carbide = ITEMS.register("sword_tungsten_carbide", () -> new SwordItem(TUNGSTEN_CARBIDE, new Properties().attributes(SwordItem.createAttributes(TUNGSTEN_CARBIDE, 3, -2.4F))));
        pickaxe_tungsten_carbide = ITEMS.register("pickaxe_tungsten_carbide", () -> new PickaxeItem(TUNGSTEN_CARBIDE, new Properties().attributes(PickaxeItem.createAttributes(TUNGSTEN_CARBIDE, 1.0F, -2.8F))));
        shovel_tungsten_carbide = ITEMS.register("shovel_tungsten_carbide", () -> new ShovelItem(TUNGSTEN_CARBIDE, new Properties().attributes(AxeItem.createAttributes(TUNGSTEN_CARBIDE, 1.5F, -3.0F))));
        axe_tungsten_carbide = ITEMS.register("axe_tungsten_carbide", () -> new AxeItem(TUNGSTEN_CARBIDE, new Properties().attributes(AxeItem.createAttributes(TUNGSTEN_CARBIDE, 7.0F, -3.2F))));
        hoe_tungsten_carbide = ITEMS.register("hoe_tungsten_carbide", () -> new AxeItem(TUNGSTEN_CARBIDE, new Properties().attributes(HoeItem.createAttributes(TUNGSTEN_CARBIDE, -1.0F, -2.0F))));

        flesh = ITEMS.register("flesh", () -> new NCFoodItem(4, 0.1F));

        potassiumIodineTablet = ITEMS.register("potassium_iodine_tablet", ItemTablet::new);

        luminousPaints.put(LuminousPaintType.GREEN, ITEMS.register("green_luminous_paint", () -> new BlockItem(greenLuminousPaint.get(), new Properties())));
        luminousPaints.put(LuminousPaintType.BLUE, ITEMS.register("blue_luminous_paint", () -> new BlockItem(blueLuminousPaint.get(), new Properties())));
        luminousPaints.put(LuminousPaintType.ORANGE, ITEMS.register("orange_luminous_paint", () -> new BlockItem(orangeLuminousPaint.get(), new Properties())));

//        leptonCannon = ITEMS.register("lepton_cannon", ItemLeptonCannon::new); TODO
//        gluonGun = ITEMS.register("gluon_gun", ItemGluonGun::new);
//        antimatterLauncher = ITEMS.register(new ItemAntimatterLauncher(), "antimatter_launcher");

        beamMeter = ITEMS.register("beam_meter", ItemBeamMeter::new);

//        basic_drill = ITEMS.register("drill_basic", () -> new ItemDrill(QMDStartupConfig.drill_radius[0], QMDStartupConfig.drill_energy_capacity[0], new SimpleTier(BlockTags.create(ResourceLocation.parse(QMDStartupConfig.tool_mining_level[1])), -1, (float) QMDStartupConfig.tool_speed[1], 0, 20, () -> Ingredient.EMPTY), QMDInfo.drillInfo(0)));
//        advanced_drill = ITEMS.register("drill_advanced", () -> new ItemDrill(QMDStartupConfig.drill_radius[1], QMDStartupConfig.drill_energy_capacity[1], new SimpleTier(BlockTags.create(ResourceLocation.parse(QMDStartupConfig.tool_mining_level[2])), -1, (float) QMDStartupConfig.tool_speed[2], 0, 20, () -> Ingredient.EMPTY), QMDInfo.drillInfo(1)));
//
        for (CellType type : CellType.values()) {
            cells.put(type, ITEMS.register(type.getSerializedName().toLowerCase() + "_cell", () -> new ItemCell(type)));
        }
        for (CoperniciumType type : CoperniciumType.values()) {
            copernicium.put(type, ITEMS.register("copernicium_" + type.getSerializedName().toLowerCase(), () -> new NCItem(new Properties())));
        }
        for (CoperniciumPelletType type : CoperniciumPelletType.values()) {
            pellet_copernicium.put(type, ITEMS.register("copernicium_" + type.getSerializedName().toLowerCase(), () -> new NCItem(new Properties())));
        }
        for (CoperniciumFuelType type : CoperniciumFuelType.values()) {
            fuel_copernicium.put(type, ITEMS.register("copernicium_" + type.getSerializedName().toLowerCase(), () -> new NCItem(new Properties())));
        }
        for (CoperniciumDepletedFuelType type : CoperniciumDepletedFuelType.values()) {
            depleted_fuel_copernicium.put(type, ITEMS.register("depleted_" + type.getSerializedName().toLowerCase(), () -> new NCItem(new Properties())));
        }
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    public static Tier toolMaterial(int id, ItemLike repairStack) {
        return new SimpleTier(
                BlockTags.create(ResourceLocation.parse(QMDStartupConfig.tool_mining_level[id])),
                QMDStartupConfig.tool_durability[id], (float) QMDStartupConfig.tool_speed[id], (float) QMDStartupConfig.tool_attack_damage[id], QMDStartupConfig.tool_enchantability[id],
                () -> Ingredient.of(repairStack)
        );
    }
}