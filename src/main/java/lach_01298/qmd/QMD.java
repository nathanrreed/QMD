package lach_01298.qmd;

import com.mojang.logging.LogUtils;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.proxy.CommonProxy;
import lach_01298.qmd.recipe.QMDRecipes;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.slf4j.Logger;


@Mod(QMD.MOD_ID)
public class QMD {
    public static final String MOD_ID = "qmd";

    public static final Logger LOGGER = LogUtils.getLogger();

    public QMD(IEventBus modEventBus, ModContainer modContainer) {
        QMDRecipes.registerRecipes();

        modContainer.registerConfig(ModConfig.Type.SERVER, QMDServerConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.STARTUP, QMDStartupConfig.SPEC);
        QMDStartupConfig.loadConfig();


        LOGGER.info("PreInitialization");
        CommonProxy.preInit(modEventBus);

        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::addReloadListeners);
        modEventBus.addListener(this::commonSetup);
    }

    private ReloadListener recipeCacheManager;

    public void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(getRecipeCacheManager());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        setRecipeCacheManager(new ReloadListener());
    }

    public static class ReloadListener implements ResourceManagerReloadListener {
        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) {
//            QMDRecipes.getHandlers().forEach(e -> e.postReload(NCUtil.getRecipeManager(null))); TODO add
        }
    }

    public ReloadListener getRecipeCacheManager() {
        return recipeCacheManager;
    }

    private void setRecipeCacheManager(ReloadListener manager) {
        if (recipeCacheManager == null) {
            recipeCacheManager = manager;
        } else {
            LOGGER.warn("Recipe cache manager has already been set.");
        }
    }
}
