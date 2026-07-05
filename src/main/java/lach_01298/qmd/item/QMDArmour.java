//package lach_01298.qmd.item;
//
//import com.nred.nuclearcraft.radiation.RadArmor;
//import com.nred.nuclearcraft.recipe.RecipeHelper;
//import lach_01298.qmd.QMD;
//import lach_01298.qmd.config.QMDStartupConfig;
//import net.minecraft.Util;
//import net.minecraft.core.Holder;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.world.item.ArmorItem;
//import net.minecraft.world.item.ArmorMaterial;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.neoforged.neoforge.registries.DeferredItem;
//
//import java.util.*;
//import java.util.Map.Entry;
//
//import static com.nred.nuclearcraft.registration.Registers.ARMOR_MATERIALS;
//import static lach_01298.qmd.config.QMDStartupConfig.hev_armour;
//import static lach_01298.qmd.config.QMDStartupConfig.hev_toughness;
//import static lach_01298.qmd.item.QMDItems.ITEMS;
//import static net.minecraft.world.item.ArmorItem.Type.*;
//
//
//public class QMDArmour {
//    public static final Holder<ArmorMaterial> UN_POWERED_HEV = ARMOR_MATERIALS.register("hev", () -> new ArmorMaterial(
//            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
//                map.put(ArmorItem.Type.BOOTS, hev_armour[5]);
//                map.put(ArmorItem.Type.LEGGINGS, hev_armour[6]);
//                map.put(ArmorItem.Type.CHESTPLATE, hev_armour[7]);
//                map.put(ArmorItem.Type.HELMET, hev_armour[8]);
//                map.put(ArmorItem.Type.BODY, hev_armour[9]);
//            }),
//            0,
//            SoundEvents.ARMOR_EQUIP_GENERIC,
//            () -> Ingredient.EMPTY,
//            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "hev"), "", true)),
//            (float) hev_toughness[1],
//            0
//    ));
//    public static final Holder<ArmorMaterial> POWERED_HEV = ARMOR_MATERIALS.register("hev_powered", () -> new ArmorMaterial( // TODO??
//            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
//                map.put(ArmorItem.Type.BOOTS, hev_armour[0]);
//                map.put(ArmorItem.Type.LEGGINGS, hev_armour[1]);
//                map.put(ArmorItem.Type.CHESTPLATE, hev_armour[2]);
//                map.put(ArmorItem.Type.HELMET, hev_armour[3]);
//                map.put(ArmorItem.Type.BODY, hev_armour[4]);
//            }),
//            0,
//            SoundEvents.ARMOR_EQUIP_GENERIC,
//            () -> Ingredient.EMPTY,
//            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "hev"), "", true)),
//            (float) hev_toughness[0],
//            0
//    ));
//
//    public static DeferredItem<Item> helm_hev;
//    public static DeferredItem<Item> chest_hev;
//    public static DeferredItem<Item> legs_hev;
//    public static DeferredItem<Item> boots_hev;
//
//
//    public static void init() {
//        helm_hev = ITEMS.register("helm_hev", () -> new ItemHEVSuit(UN_POWERED_HEV, HELMET, 0.2D));
//        chest_hev = ITEMS.register("chest_hev", () -> new ItemHEVSuit(UN_POWERED_HEV, CHESTPLATE, 0.4D));
//        legs_hev = ITEMS.register("legs_hev", () -> new ItemHEVSuit(UN_POWERED_HEV, LEGGINGS, 0.2D));
//        boots_hev = ITEMS.register("boots_hev", () -> new ItemHEVSuit(UN_POWERED_HEV, BOOTS, 0.2D));
//    }
//
//    public static void addRadResistance() {
//        Map<ItemStack, Double> radAmours = new HashMap<>(); // TODO
//        radAmours.put(new ItemStack(helm_hev.get()), QMDStartupConfig.hev_rad_res[0]);
//        radAmours.put(new ItemStack(chest_hev.get()), QMDStartupConfig.hev_rad_res[1]);
//        radAmours.put(new ItemStack(legs_hev.get()), QMDStartupConfig.hev_rad_res[2]);
//        radAmours.put(new ItemStack(boots_hev.get()), QMDStartupConfig.hev_rad_res[3]);
//
//
//        for (Entry<ItemStack, Double> entry : radAmours.entrySet()) {
//            int packed = RecipeHelper.pack(entry.getKey());
//            double resistance = entry.getValue();
//
//            RadArmor.ARMOR_RAD_RESISTANCE_MAP.put(packed, resistance);
//        }
//    }
//
//    public static void blacklistShielding() {
//        List<ItemStack> radAmours = new ArrayList<>(); // TODO
//        radAmours.add(new ItemStack(helm_hev.get()));
//        radAmours.add(new ItemStack(chest_hev.get()));
//        radAmours.add(new ItemStack(legs_hev.get()));
//        radAmours.add(new ItemStack(boots_hev.get()));
//
//        for (ItemStack stack : radAmours) {
//            int packed = RecipeHelper.pack(stack);
//            RadArmor.ARMOR_STACK_SHIELDING_BLACKLIST.add(packed);
//        }
//    }
//}