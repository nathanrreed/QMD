package lach_01298.qmd.fluid;

import com.nred.nuclearcraft.block.fluid.LiquidFluidBlock;
import com.nred.nuclearcraft.info.NCFluid;
import com.nred.nuclearcraft.info.NCFluidMaker;
import lach_01298.qmd.QMD;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;

import static lach_01298.qmd.block.QMDBlocks.BLOCKS;
import static lach_01298.qmd.item.QMDItems.ITEMS;

public class QMDFluids {
    public static final HashMap<String, NCFluid> QMD_FLUIDS = new HashMap<>();

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, QMD.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, QMD.MOD_ID);

    private static final NCFluidMaker MAKER = new NCFluidMaker(FLUID_TYPES, FLUIDS, BLOCKS, ITEMS);

    public static final NCFluid.TypeInfo EXOTIC_TYPE = new NCFluid.TypeInfo(8, false, "liquid_still", "liquid_flow", FluidType.Properties.create(), BlockFluidExotic::new);

    public static void init() {
        // acids
        addFluidPair(NCFluid.ACID_TYPE, "hydrochloric_acid", 0x99ffee);
        addFluidPair(NCFluid.ACID_TYPE, "nitric_acid", 0x4f9eff);

        // solutions
        addFluidPair(NCFluid.SALT_SOLUTION_TYPE, "sodium_chloride_solution", waterBlend(0x0057fa));
        addFluidPair(NCFluid.SALT_SOLUTION_TYPE, "sodium_nitrate_solution", waterBlend(0xffffff));
        addFluidPair(NCFluid.SALT_SOLUTION_TYPE, "lead_nitrate_solution", waterBlend(0xd5d5d5));
        addFluidPair(NCFluid.SALT_SOLUTION_TYPE, "sodium_tungstate_solution", waterBlend(0xfffea3));
        addFluidPair(NCFluid.SALT_SOLUTION_TYPE, "lead_tungstate_solution", waterBlend(0xd58715));
        addFluidPair(NCFluid.SALT_SOLUTION_TYPE, "salt_water", waterBlend(0x2974ff));

        //cryo liquids
        QMD_FLUIDS.put("liquid_hydrogen", MAKER.registerFluid("liquid_hydrogen", "liquid", false, false, 0XFFB37AC4, 71, 20, 170, 0, LiquidFluidBlock::new));
        QMD_FLUIDS.put("liquid_argon", MAKER.registerFluid("liquid_argon", "liquid", false, false, 0XFFFF75DD, 1395, 87, 170, 0, LiquidFluidBlock::new));
        QMD_FLUIDS.put("liquid_neon", MAKER.registerFluid("liquid_neon", "liquid", false, false, 0XFFFF9F7A, 1207, 27, 170, 0, LiquidFluidBlock::new));
        QMD_FLUIDS.put("liquid_oxygen", MAKER.registerFluid("liquid_oxygen", "liquid", false, false, 0XFF7E8CC8, 1141, 90, 170, 0, LiquidFluidBlock::new));
        QMD_FLUIDS.put("liquid_air", MAKER.registerFluid("liquid_air", "liquid", false, false, 0XFF6CDEFF, 870, 79, 170, 0, LiquidFluidBlock::new));

        //liquids
        QMD_FLUIDS.put("mercury", MAKER.registerFluid("mercury", "liquid_opaque", false, false, 0XFFC6C6C6, 13540, 300, 1000, 0, LiquidFluidBlock::new));
        QMD_FLUIDS.put("hot_mercury", MAKER.registerFluid("hot_mercury", "liquid_opaque", false, false, 0XFFD0D0D0, 13540, 630, 1000, 0, LiquidFluidBlock::new));
        QMD_FLUIDS.put("iodine", MAKER.registerFluid("iodine", "liquid", false, false, 0XFF7400B3, 3960, 387, 1000, 0, LiquidFluidBlock::new));

        //gases
        addFluidPair(NCFluid.GAS_TYPE, "argon", 0xff75dd);
        addFluidPair(NCFluid.GAS_TYPE, "neon", 0xff9f7a);
        addFluidPair(NCFluid.GAS_TYPE, "chlorine", 0xffff8f);
        addFluidPair(NCFluid.GAS_TYPE, "nitric_oxide", 0xc9eeff);
        addFluidPair(NCFluid.GAS_TYPE, "nitrogen_dioxide", 0x782a10);
        addFluidPair(NCFluid.GAS_TYPE, "compressed_air", 0xBDF0FF);

        //molten
        addFluidPair(NCFluid.MOLTEN_TYPE, "silicon", 0x676767);
        addFluidPair(NCFluid.MOLTEN_TYPE, "yag", 0xfffddb);
        addFluidPair(NCFluid.MOLTEN_TYPE, "nd_yag", 0xe4bcf5);
        addFluidPair(NCFluid.MOLTEN_TYPE, "tungsten", 0x4E564F);
        addFluidPair(NCFluid.MOLTEN_TYPE, "niobium", 0xCCCCC0);
        addFluidPair(NCFluid.MOLTEN_TYPE, "chromium", 0xC9C9C9);
        addFluidPair(NCFluid.MOLTEN_TYPE, "titanium", 0x8E7E8D);
        addFluidPair(NCFluid.MOLTEN_TYPE, "cobalt", 0x364F70);
        addFluidPair(NCFluid.MOLTEN_TYPE, "nickel", 0xA3A998);
        addFluidPair(NCFluid.MOLTEN_TYPE, "hafnium", 0x948484);
        addFluidPair(NCFluid.MOLTEN_TYPE, "zinc", 0xE0E0E0);
        addFluidPair(NCFluid.MOLTEN_TYPE, "osmium", 0x6F89A9);
        addFluidPair(NCFluid.MOLTEN_TYPE, "iridium", 0xDDD5DD);
        addFluidPair(NCFluid.MOLTEN_TYPE, "platinum", 0x71A4A9);
        addFluidPair(NCFluid.MOLTEN_TYPE, "calcium", 0xF4F3EA);
        addFluidPair(NCFluid.MOLTEN_TYPE, "strontium", 0xC4CB97);
        addFluidPair(NCFluid.MOLTEN_TYPE, "yttrium", 0xD1C375);
        addFluidPair(NCFluid.MOLTEN_TYPE, "neodymium", 0x818A97);

        addFluidPair(NCFluid.MOLTEN_TYPE, "samarium", 0xa4d95f);
        addFluidPair(NCFluid.MOLTEN_TYPE, "terbium", 0x5ba694);
        addFluidPair(NCFluid.MOLTEN_TYPE, "erbium", 0x5a7a45);
        addFluidPair(NCFluid.MOLTEN_TYPE, "ytterbium", 0x7a4552);
        addFluidPair(NCFluid.MOLTEN_TYPE, "bismuth", 0x827e73);
        addFluidPair(NCFluid.MOLTEN_TYPE, "polonium", 0x5c7c78);
        addFluidPair(NCFluid.MOLTEN_TYPE, "radium", 0x6e607b);

        addFluidPair(NCFluid.MOLTEN_TYPE, "sodium_chloride", 0xd4cccc);

        //hot gases
        addFluidPair(NCFluid.HOT_GAS_TYPE, "carbon", 0x343434);
        addFluidPair(NCFluid.HOT_GAS_TYPE, "high_pressure_mercury", 0xAAAAAA);
        addFluidPair(NCFluid.HOT_GAS_TYPE, "exhaust_mercury", 0x888888);

        //exotic matter
        addFluidPair(EXOTIC_TYPE, "antihydrogen", 0xB37AC4);
        addFluidPair(EXOTIC_TYPE, "antideuterium", 0x9E6FEF);
        addFluidPair(EXOTIC_TYPE, "antitritium", 0x5DBBD6);
        addFluidPair(EXOTIC_TYPE, "antihelium3", 0xCBBB67);
        addFluidPair(EXOTIC_TYPE, "antihelium", 0xC57B81);
        addFluidPair(EXOTIC_TYPE, "positronium", 0xc9c9c9);
        addFluidPair(EXOTIC_TYPE, "muonium", 0x9b93ff);
        addFluidPair(EXOTIC_TYPE, "tauonium", 0xc86300);
        addFluidPair(EXOTIC_TYPE, "glueballs", 0xa6f1f2);
    }

    public static void register(IEventBus modEventBus) {
        FLUID_TYPES.register(modEventBus);
        FLUIDS.register(modEventBus);
    }

    private static void addFluidPair(NCFluid.TypeInfo type, String name, int colour) {
        QMD_FLUIDS.put(name, MAKER.registerFluid(name, 0xFF000000 | colour, type));
    }

    private static int waterBlend(int soluteColor, float blendRatio) {
        return FastColor.ARGB32.lerp(blendRatio, 0x2F43F4, soluteColor);
    }

    private static int waterBlend(int soluteColor) {
        return waterBlend(soluteColor, 0.5F);
    }
}
