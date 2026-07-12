package lach_01298.qmd.accelerator;

import com.nred.nuclearcraft.multiblock.PlacementRule;
import com.nred.nuclearcraft.multiblock.PlacementRule.AdjacencyType;
import com.nred.nuclearcraft.multiblock.PlacementRule.CountType;
import com.nred.nuclearcraft.multiblock.PlacementRule.PlacementMap;
import com.nred.nuclearcraft.util.I18nHelper;
import com.nred.nuclearcraft.util.Lang;
import com.nred.nuclearcraft.util.StringHelper;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.enums.BlockTypes.CoolerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;

import static lach_01298.qmd.config.QMDStartupConfig.cooler_rule;

public class CoolerPlacement {

    /**
     * List of all defined rule parsers. Earlier entries are prioritised!
     */
    public static final List<PlacementRule.RuleParser<Accelerator, TileAcceleratorPart>> RULE_PARSER_LIST = new LinkedList<>();

    /**
     * Map of all placement rule IDs to unparsed rule strings, used for ordered iterations.
     */
    public static final Object2ObjectMap<String, String> RULE_MAP_RAW = new Object2ObjectArrayMap<>();

    /**
     * Map of all defined placement rules.
     */
    public static final Object2ObjectMap<String, PlacementRule<Accelerator, TileAcceleratorPart>> RULE_MAP = new PlacementMap<>();

    /**
     * List of all defined tooltip builders. Earlier entries are prioritised!
     */
    public static final List<PlacementRule.TooltipBuilder<Accelerator, TileAcceleratorPart>> TOOLTIP_BUILDER_LIST = new LinkedList<>();

    /**
     * Map of all localised tooltips.
     */
    public static final Object2ObjectMap<String, String> TOOLTIP_MAP = new Object2ObjectOpenHashMap<>();

    public static void preInit() {
        RULE_PARSER_LIST.add(new DefaultRuleParser());

        TOOLTIP_BUILDER_LIST.add(new DefaultTooltipBuilder());
    }

    public static void init() {
        RULE_MAP.put("", new PlacementRule.Or<>(new ArrayList<>()));

        addRule("water_cooler", cooler_rule[CoolerType.WATER.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.WATER), 1));
        addRule("iron_cooler", cooler_rule[CoolerType.IRON.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.IRON), 1));
        addRule("redstone_cooler", cooler_rule[CoolerType.REDSTONE.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.REDSTONE), 1));
        addRule("quartz_cooler", cooler_rule[CoolerType.QUARTZ.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.QUARTZ), 1));
        addRule("obsidian_cooler", cooler_rule[CoolerType.OBSIDIAN.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.OBSIDIAN), 1));
        addRule("nether_brick_cooler", cooler_rule[CoolerType.NETHER_BRICK.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.NETHER_BRICK), 1));
        addRule("glowstone_cooler", cooler_rule[CoolerType.GLOWSTONE.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.GLOWSTONE), 1));
        addRule("lapis_cooler", cooler_rule[CoolerType.LAPIS.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.LAPIS), 1));
        addRule("gold_cooler", cooler_rule[CoolerType.GOLD.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.GOLD), 1));
        addRule("prismarine_cooler", cooler_rule[CoolerType.PRISMARINE.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.PRISMARINE), 1));
        addRule("slime_cooler", cooler_rule[CoolerType.SLIME.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.SLIME), 1));
        addRule("end_stone_cooler", cooler_rule[CoolerType.END_STONE.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.END_STONE), 1));
        addRule("purpur_cooler", cooler_rule[CoolerType.PURPUR.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.PURPUR), 1));
        addRule("diamond_cooler", cooler_rule[CoolerType.DIAMOND.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.DIAMOND), 1));
        addRule("emerald_cooler", cooler_rule[CoolerType.EMERALD.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.EMERALD), 1));
        addRule("copper_cooler", cooler_rule[CoolerType.COPPER.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.COPPER), 1));
        addRule("tin_cooler", cooler_rule[CoolerType.TIN.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.TIN), 1));
        addRule("lead_cooler", cooler_rule[CoolerType.LEAD.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.LEAD), 1));
        addRule("boron_cooler", cooler_rule[CoolerType.BORON.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.BORON), 1));
        addRule("lithium_cooler", cooler_rule[CoolerType.LITHIUM.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.LITHIUM), 1));
        addRule("magnesium_cooler", cooler_rule[CoolerType.MAGNESIUM.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.MAGNESIUM), 1));
        addRule("manganese_cooler", cooler_rule[CoolerType.MANGANESE.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.MANGANESE), 1));
        addRule("aluminum_cooler", cooler_rule[CoolerType.ALUMINUM.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.ALUMINUM), 1));
        addRule("silver_cooler", cooler_rule[CoolerType.SILVER.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.SILVER), 1));
        addRule("fluorite_cooler", cooler_rule[CoolerType.FLUORITE.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.FLUORITE), 1));
        addRule("villiaumite_cooler", cooler_rule[CoolerType.VILLIAUMITE.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.VILLIAUMITE), 1));
        addRule("carobbiite_cooler", cooler_rule[CoolerType.CAROBBIITE.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.CAROBBIITE), 1));
        addRule("arsenic_cooler", cooler_rule[CoolerType.ARSENIC.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.ARSENIC), 1));
        addRule("liquid_nitrogen_cooler", cooler_rule[CoolerType.LIQUID_NITROGEN.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.LIQUID_NITROGEN), 1));
        addRule("liquid_helium_cooler", cooler_rule[CoolerType.LIQUID_HELIUM.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.LIQUID_HELIUM), 1));
        addRule("enderium_cooler", cooler_rule[CoolerType.ENDERIUM.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.ENDERIUM), 1));
        addRule("cryotheum_cooler", cooler_rule[CoolerType.CRYOTHEUM.ordinal()], new ItemStack(QMDBlocks.acceleratorCoolers.get(CoolerType.CRYOTHEUM), 1));


    }

    public static void addRule(String id, String rule, Object... blocks) {
        RULE_MAP_RAW.put(id, rule);
        RULE_MAP.put(id, parse(rule));
    }

    public static void postInit() {
        for (Object2ObjectMap.Entry<String, PlacementRule<Accelerator, TileAcceleratorPart>> entry : RULE_MAP.object2ObjectEntrySet()) {
            for (PlacementRule.TooltipBuilder<Accelerator, TileAcceleratorPart> builder : TOOLTIP_BUILDER_LIST) {
                String tooltip = builder.buildTooltip(entry.getValue());
                if (tooltip != null)
                    TOOLTIP_MAP.put(entry.getKey(), tooltip);
            }
        }
    }

    // Default Rule Parser

    public static PlacementRule<Accelerator, TileAcceleratorPart> parse(String string) {
        return PlacementRule.parse(string, RULE_PARSER_LIST);
    }

    /**
     * Rule parser for all rule types available in base NC.
     */
    public static class DefaultRuleParser extends PlacementRule.DefaultRuleParser<Accelerator, TileAcceleratorPart> {

        @Override
        protected @Nullable PlacementRule<Accelerator, TileAcceleratorPart> partialParse(String s) {
            s = s.toLowerCase(Locale.ROOT);

            s = s.replaceAll("at exactly one vertex", "vertex");

            boolean exact = s.contains("exact"), atMost = s.contains("at most");
            boolean axial = s.contains("axial"), vertex = s.contains("vertex");
            boolean different = s.contains("different");

            if ((exact && atMost) || (axial && vertex))
                return null;

            s = s.replaceAll("at least", "");
            s = s.replaceAll("exactly", "");
            s = s.replaceAll("exact", "");
            s = s.replaceAll("at most", "");
            s = s.replaceAll("axially", "");
            s = s.replaceAll("axial", "");
            s = s.replaceAll("at one vertex", "");
            s = s.replaceAll("at a vertex", "");
            s = s.replaceAll("at vertex", "");
            s = s.replaceAll("vertex", "");

            int amount = -1;
            String rule = null, type = null;

            String[] split = s.split(Pattern.quote(" "));
            for (int i = 0; i < split.length; i++) {
                if (StringHelper.NUMBER_S2I_MAP.containsKey(split[i])) {
                    amount = StringHelper.NUMBER_S2I_MAP.getInt(split[i]);
                } else if (rule == null) {
                    if (split[i].contains("beam")) {
                        rule = "beam";
                    } else if (split[i].contains("magnet")) {
                        rule = "magnet";

                    } else if (split[i].contains("yoke")) {
                        rule = "yoke";
                    } else if (split[i].contains("cavity")) {
                        rule = "cavity";

                    } else if (split[i].contains("cooler")) {
                        rule = "cooler";
                        if (i > 0)
                            type = split[i - 1];
                        else
                            return null;
                    }
                }
            }

            if (amount < 0 || rule == null)
                return null;

            CountType countType = exact ? CountType.EXACTLY : (atMost ? CountType.AT_MOST : CountType.AT_LEAST);
            AdjacencyType adjType = axial ? AdjacencyType.AXIAL : (vertex ? AdjacencyType.VERTEX : AdjacencyType.STANDARD);

            if (rule.equals("beam")) {
                return new AdjacentBeam(amount, countType, adjType);
            } else if (rule.equals("magnet")) {
                if (different) {
                    return new AdjacentDifferentMagnet(amount, countType, adjType);
                }
                return new AdjacentMagnet(amount, countType, adjType, type);
            } else if (rule.equals("yoke")) {
                return new AdjacentYoke(amount, countType, adjType);
            } else if (rule.equals("cavity")) {
                if (different) {
                    return new AdjacentDifferentCavity(amount, countType, adjType);
                }
                return new AdjacentRFCavity(amount, countType, adjType, type);
            } else if (rule.equals("cooler")) {
                return new AdjacentCooler(amount, countType, adjType, type);
            }

            return null;
        }
    }


    // Adjacent

    public static abstract class Adjacent extends PlacementRule.Adjacent<Accelerator, TileAcceleratorPart> {

        public Adjacent(String dependency, int amount, CountType countType, AdjacencyType adjType) {
            super(dependency, amount, countType, adjType);
        }
    }

    public static class AdjacentBeam extends Adjacent {

        public AdjacentBeam(int amount, CountType countType, AdjacencyType adjType) {
            super("beam", amount, countType, adjType);
        }

        @Override
        public boolean satisfied(TileAcceleratorPart part, Direction dir, boolean simulate) {
            return isBeam(part.getMultiblockController(), part.getTilePos().relative(dir));
        }
    }

    public static class AdjacentMagnet extends Adjacent {

        protected final String magnetType;

        public AdjacentMagnet(int amount, CountType countType, AdjacencyType adjType, String magnetType) {
            super("magnet", amount, countType, adjType);
            this.magnetType = magnetType;
        }

        @Override
        public boolean satisfied(TileAcceleratorPart part, Direction dir, boolean simulate) {
            return isActiveMagnet(part.getMultiblockController(), part.getTilePos().relative(dir), magnetType);
        }
    }

    public static class AdjacentYoke extends Adjacent {

        public AdjacentYoke(int amount, CountType countType, AdjacencyType adjType) {
            super("yoke", amount, countType, adjType);
        }

        @Override
        public boolean satisfied(TileAcceleratorPart part, Direction dir, boolean simulate) {
            return isActiveYoke(part.getMultiblockController(), part.getTilePos().relative(dir));
        }
    }

    public static class AdjacentRFCavity extends Adjacent {

        protected final String cavityType;

        public AdjacentRFCavity(int amount, CountType countType, AdjacencyType adjType, String cavityType) {
            super("cavity", amount, countType, adjType);
            this.cavityType = cavityType;
        }

        @Override
        public boolean satisfied(TileAcceleratorPart part, Direction dir, boolean simulate) {
            return isActiveRFCavity(part.getMultiblockController(), part.getTilePos().relative(dir), cavityType);
        }
    }

    public static class AdjacentCooler extends Adjacent {

        protected final String coolerType;

        public AdjacentCooler(int amount, CountType countType, AdjacencyType adjType, String coolerType) {
            super(coolerType + "_cooler", amount, countType, adjType);
            this.coolerType = coolerType;
        }

        @Override
        public void checkIsRuleAllowed(String ruleID) {
            super.checkIsRuleAllowed(ruleID);
            if (countType != CountType.AT_LEAST && coolerType.equals("any")) {

                throw new IllegalArgumentException((countType == CountType.EXACTLY ? "Exact 'any cooler'" : "'At most n of any cooler'") + " placement rule with ID \"" + ruleID + "\" is disallowed due to potential ambiguity during rule checks!");
            }
        }

        @Override
        public boolean satisfied(TileAcceleratorPart part, Direction dir, boolean simulate) {
            return isActiveCooler(part.getMultiblockController(), part.getTilePos().relative(dir), coolerType);
        }
    }


    public static class AdjacentDifferentMagnet extends Adjacent {


        public AdjacentDifferentMagnet(int amount, CountType countType, AdjacencyType adjType) {
            super("magnet", amount, countType, adjType);
        }

        @Override
        public void checkIsRuleAllowed(String ruleID) {
            if (adjType != AdjacencyType.STANDARD) {
                throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can only be AdjacencyType standard  ");
            }
            if (amount > 6) {
                throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can not require more than six adjacencies!");
            }
            if (amount < 1) {
                throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can only require more than one difrent adjacencies");
            }
        }


        @Override
        public boolean satisfied(TileAcceleratorPart tile, boolean simulate) {
            List<String> storedTypes = new ArrayList<String>();

            for (Direction dir : Direction.values()) {
                if (isActiveMagnet(tile.getMultiblockController(), tile.getTilePos().relative(dir), null)) {
                    String type = tile.getMultiblockController().get().getPartMap(TileAcceleratorMagnet.class)
                            .get(tile.getTilePos().relative(dir).asLong()).magnetType.getName();

                    if (!storedTypes.contains(type)) {
                        storedTypes.add(type);
                    }
                }

                if (countType == CountType.AT_LEAST) {
                    if (storedTypes.size() >= amount)
                        return true;
                } else if (storedTypes.size() > amount)
                    return false;
            }
            return countType == CountType.AT_MOST || (countType == CountType.EXACTLY && storedTypes.size() == amount);
        }


        @Override
        public String buildSubTooltip() {
            return Lang.localize("nc.sf.placement_rule.adjacent." + countType.tooltipSubstring(amount) + adjType.tooltipSubstring(amount), I18nHelper.getPluralForm("nc.sf." + dependencies.get(0), amount, Lang.localize("nc.sf.different." + StringHelper.NUMBER_I2S_MAP.get(amount))));
        }

        @Override
        public boolean satisfied(TileAcceleratorPart tile, Direction dir, boolean simulate) {
            return false;
        }
    }

    public static class AdjacentDifferentCavity extends Adjacent {
        List<String> storedTypes;

        public AdjacentDifferentCavity(int amount, CountType countType, AdjacencyType adjType) {
            super("cavity", amount, countType, adjType);
            storedTypes = new ArrayList<String>();
        }

        @Override
        public void checkIsRuleAllowed(String ruleID) {
            if (adjType != AdjacencyType.STANDARD) {
                throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can only be AdjacencyType standard  ");
            }
            if (amount > 6) {
                throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can not require more than six adjacencies!");
            }
            if (amount < 1) {
                throw new IllegalArgumentException("Diffrent Adjacency placement rule with ID \"" + ruleID + "\" can only require more than one difrent adjacencies");
            }
        }


        @Override
        public boolean satisfied(TileAcceleratorPart tile, boolean simulate) {
            List<String> storedTypes = new ArrayList<String>();

            for (Direction dir : Direction.values()) {
                if (isActiveRFCavity(tile.getMultiblockController(), tile.getTilePos().relative(dir), null)) {
                    String type = tile.getMultiblockController().get().getPartMap(TileAcceleratorRFCavity.class)
                            .get(tile.getTilePos().relative(dir).asLong()).rfCavityType.getName();

                    if (!storedTypes.contains(type)) {
                        storedTypes.add(type);
                    }
                }

                if (countType == CountType.AT_LEAST) {
                    if (storedTypes.size() >= amount)
                        return true;
                } else if (storedTypes.size() > amount)
                    return false;
            }
            return countType == CountType.AT_MOST || (countType == CountType.EXACTLY && storedTypes.size() == amount);
        }

        @Override
        public boolean satisfied(TileAcceleratorPart part, Direction dir, boolean simulate) {
            return false;
        }

        @Override
        public String buildSubTooltip() {
            return Lang.localize("nc.sf.placement_rule.adjacent." + countType.tooltipSubstring(amount) + adjType.tooltipSubstring(amount), I18nHelper.getPluralForm("nc.sf." + dependencies.get(0), amount, Lang.localize("nc.sf.different." + StringHelper.NUMBER_I2S_MAP.get(amount))));
        }
    }


    // Helper Methods

    public static boolean isBeam(Optional<Accelerator> accelerator, BlockPos pos) {
        if (accelerator.isEmpty()) return false;
        TileAcceleratorBeam beam = accelerator.get().getPartMap(TileAcceleratorBeam.class).get(pos.asLong());
        return beam != null && beam.isFunctional();
    }

    public static boolean isActiveMagnet(Optional<Accelerator> accelerator, BlockPos pos, String magnetName) {
        if (accelerator.isEmpty()) return false;
        TileAcceleratorMagnet magnet = accelerator.get().getPartMap(TileAcceleratorMagnet.class).get(pos.asLong());
        return magnet != null && ((magnet.isFunctional() && magnet.magnetType.getName().equals(magnetName)) || (magnet.isFunctional() && magnetName == null));
    }

    public static boolean isActiveYoke(Optional<Accelerator> accelerator, BlockPos pos) {
        if (accelerator.isEmpty()) return false;
        TileAcceleratorYoke yoke = accelerator.get().getPartMap(TileAcceleratorYoke.class).get(pos.asLong());
        return yoke != null && yoke.isFunctional();
    }

    public static boolean isActiveRFCavity(Optional<Accelerator> accelerator, BlockPos pos, String cavityName) {
        if (accelerator.isEmpty()) return false;
        TileAcceleratorRFCavity cavity = accelerator.get().getPartMap(TileAcceleratorRFCavity.class).get(pos.asLong());
        return cavity != null && (cavity.isFunctional() && (cavity.rfCavityType.getName().equals(cavityName)) || (cavity.isFunctional() && cavityName == null));
    }

    public static boolean isActiveCooler(Optional<Accelerator> accelerator, BlockPos pos, String coolerName) {
        if (accelerator.isEmpty()) return false;
        TileAcceleratorCooler cooler = accelerator.get().getPartMap(TileAcceleratorCooler.class).get(pos.asLong());
        return cooler != null && cooler.isFunctional() && (coolerName.equals("any") || cooler.coolerType.getName().equals(coolerName));
    }

    // Default Tooltip Builder

    public static class DefaultTooltipBuilder extends PlacementRule.DefaultTooltipBuilder<Accelerator, TileAcceleratorPart> {
    }
}