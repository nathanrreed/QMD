//package lach_01298.qmd.render.entity;
//
//import lach_01298.qmd.QMD;
//import lach_01298.qmd.entity.EntityBeamProjectile;
//import net.minecraft.client.Minecraft;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.phys.AABB;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//import net.neoforged.bus.api.EventPriority;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
//
//import java.util.List;
//
//@EventBusSubscriber(modid = QMD.MOD_ID)
//public class BeamRenderer {
//
//    //this is to make the EntityBeamProjectile to render even in unrendered chunks
//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public static void renderBeamEffects(RenderLevelStageEvent event) {
//        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;
//
//        List<EntityBeamProjectile> list = Minecraft.getInstance().level.getEntitiesOfClass(EntityBeamProjectile.class, AABB.INFINITE);
//
//        for (Entity entity : list) {
//            float partialTicks = event.getPartialTick().getGameTimeDeltaTicks();
//            double d0 = entity.xo + (entity.getX() - entity.xOld) * (double) partialTicks;
//            double d1 = entity.zo + (entity.getY() - entity.yo) * (double) partialTicks;
//            double d2 = entity.xo + (entity.getZ() - entity.zo) * (double) partialTicks;
//            float f = entity.yRotO + (entity.getYRot() - entity.yRotO) * partialTicks;
//            Entity entity2 = Minecraft.getInstance().cameraEntity;
//            double d3 = entity2.xo + (entity2.getX() - entity2.xo) * (double) partialTicks;
//            double d4 = entity2.yo + (entity2.getY() - entity2.yo) * (double) partialTicks;
//            double d5 = entity2.zo + (entity2.getZ() - entity2.zo) * (double) partialTicks;
//
//            Minecraft.getInstance().getEntityRenderDispatcher().render(entity, d0 - d3, d1 - d4, d2 - d5, f, partialTicks, event.getPoseStack(), Minecraft.getInstance().renderBuffers().bufferSource(), -1);
//        }
//    }
//}