package lach_01298.qmd.proxy;

import com.nred.nuclearcraft.info.NCFluid;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import static com.nred.nuclearcraft.helpers.Concat.fluidValues;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;

@EventBusSubscriber
public class ClientProxy {

//    @Override TODO
//    public void preInit(FMLCommonSetupEvent preEvent) {
//        super.preInit(preEvent);
//        clientPreInit();
//        QMDRenderHandler.init();
//        MinecraftForge.EVENT_BUS.register(DrillBlockRenderHandler.INSTANCE);
//    }
//

    /// /    @SubscribeEvent TODO
    /// /    public void init(RegisterColorHandlersEvent.Item event) {
    /// /        event.register(new IItemColor() {
    /// /            public int colorMultiplier(ItemStack stack, int tintIndex) {
    /// /                return tintIndex > 0 ? -1 : ((ItemArmor) stack.getItem()).getColor(stack);
    /// /            }
    /// /        }, QMDArmour.helm_hev, QMDArmour.chest_hev, QMDArmour.legs_hev, QMDArmour.boots_hev);
    /// /    }
//
//    @Override
//    public void postInit(FMLPostInitializationEvent postEvent) {
//        super.postInit(postEvent);
//        MinecraftForge.EVENT_BUS.register(new QMDTooltipHandler());
//        MinecraftForge.EVENT_BUS.register(new ArmPositionHandler());
//        MinecraftForge.EVENT_BUS.register(new BeamRenderer());
//        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(DrillBlockRenderHandler.INSTANCE);
//    }
//
//
//    @Override
//    public Player getPlayerClient() {
//        return Minecraft.getInstance().player;
//    }
    @SubscribeEvent
    public static void fluidLoad(RegisterClientExtensionsEvent event) {
        for (NCFluid fluid : fluidValues(QMD_FLUIDS)) {
            event.registerFluidType(fluid.client, fluid.type);
        }
    }

    @SubscribeEvent
    public static void fluidColoring(final FMLClientSetupEvent event) {
        for (NCFluid fluid : fluidValues(QMD_FLUIDS)) {
            ItemBlockRenderTypes.setRenderLayer(fluid.still.get(), RenderType.TRANSLUCENT);
            ItemBlockRenderTypes.setRenderLayer(fluid.flowing.get(), RenderType.TRANSLUCENT);
        }
    }

    @SubscribeEvent
    public static void bucketColoring(RegisterColorHandlersEvent.Item event) {
        for (NCFluid fluid : fluidValues(QMD_FLUIDS)) {
            event.register(((stack, tintIndex) -> tintIndex == 0 ? -1 : fluid.client.getTintColor()), fluid.bucket.asItem());
        }
    }
}
