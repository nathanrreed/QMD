//package lach_01298.qmd.render;
//
//import lach_01298.qmd.QMD;
//import lach_01298.qmd.render.entity.RenderGluonBeam;
//import net.minecraft.client.renderer.entity.EntityRenderers;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
//
//import static lach_01298.qmd.entity.QMDEntities.GLUON_BEAM;
//
//@EventBusSubscriber(modid = QMD.MOD_ID)
//public class QMDRenderHandler {
//    @SubscribeEvent
//    public static void entityRenderer(final FMLClientSetupEvent event) {
/// /        ClientRegistry.bindTileEntitySpecialRenderer(TileExoticContainmentController.class, new RenderContainmentMaterial());
/// /        ClientRegistry.bindTileEntitySpecialRenderer(TileLiquefierController.class, new RenderLiquefier());
/// /
/// /        EntityRenderers.register(GAMMA_FLASH.get(), RenderGammaFlash::new); TODO
/// /        EntityRenderers.register(LEPTON_BEAM.get(), RenderLeptonBeam::new);
//        EntityRenderers.register(GLUON_BEAM.get(), RenderGluonBeam::new);
////        EntityRenderers.register(ANTIMATER_PROJECTILE.get(), RenderAntimatterProjectile::new);
//    }
//}