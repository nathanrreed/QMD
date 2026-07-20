package lach_01298.qmd.proxy;

import com.nred.nuclearcraft.info.NCFluid;
import lach_01298.qmd.QMDDamageSources;
import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.accelerator.CoolerPlacement;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.fluid.QMDFluids;
import lach_01298.qmd.init.QMDCreativeTabs;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.menu.QMDMenus;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.recipe.RecipeSerializerRegistration;
import lach_01298.qmd.recipe.RecipeTypeRegistration;
import lach_01298.qmd.sound.QMDSounds;
import lach_01298.qmd.tile.QMDTileInfoHandler;
import lach_01298.qmd.tile.QMDTiles;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import static com.nred.nuclearcraft.helpers.Concat.fluidValues;
import static com.nred.nuclearcraft.registration.CommonSetup.addFluidsMixing;

@EventBusSubscriber
public class CommonProxy {
    public static void preInit(IEventBus modEventBus) {
        QMDTileInfoHandler.preInit();

        QMDSounds.init();
        QMDBlocks.init();
        QMDItems.init();
        QMDMenus.init();
        QMDTiles.init();

        QMDDamageSources.init();
        QMDFluids.init();
        Particles.init();
//        QMDArmour.init();

        Particles.register();

        RecipeSerializerRegistration.init();
        RecipeTypeRegistration.init();

        CoolerPlacement.preInit();
//        HeaterPlacement.preInit(); TODO

        QMDSounds.register(modEventBus);
        QMDBlocks.register(modEventBus);
        QMDItems.register(modEventBus);
        QMDFluids.register(modEventBus);
        QMDMenus.register(modEventBus);
        QMDCreativeTabs.register(modEventBus);
        RecipeSerializerRegistration.register(modEventBus);
        RecipeTypeRegistration.register(modEventBus);
        QMDTiles.register(modEventBus);
        // QMDEntities.register(modEventBus); TODO
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        QMDRadSources.init();
//        QMDEntities.register(); TODO
        CoolerPlacement.init();
//        HeaterPlacement.init();
//        QMDArmour.blacklistShielding();
//        MinecraftForge.EVENT_BUS.register(new ArmourBonusHandler());

        for (NCFluid fluid : fluidValues(QMDFluids.QMD_FLUIDS)) {
            addFluidsMixing(fluid);
        }
    }

    @SubscribeEvent
    public static void postInitServer(ServerAboutToStartEvent event) { // TODO is this the right event type
        postInit(event.getServer().getRecipeManager());
    }

    private static boolean init = false;

    @SubscribeEvent
    public static void postInitClient(RecipesUpdatedEvent event) { // TODO is this the right event type
        if (init) {
            return;
        }
        init = true;
        postInit(event.getRecipeManager());
    }

    public static void postInit(RecipeManager manager) {
        QMDRecipes.init(manager);
//        CapabilityParticleStackHandler.register(); TODO
//
//        QMDArmour.addRadResistance();
        QMDRecipes.postInit(manager);

        CoolerPlacement.postInit();
//        HeaterPlacement.postInit();
    }

//    publicstatic void onIdMapping(FMLModIdMappingEvent idMappingEvent) { TODO
//        QMDRecipes.refreshRecipeCaches();
//        QMDRadSources.init();
//        QMDArmour.addRadResistance();
//        CoolerPlacement.recipe_handler.refreshCache();
//        HeaterPlacement.recipe_handler.refreshCache();
//    }
}