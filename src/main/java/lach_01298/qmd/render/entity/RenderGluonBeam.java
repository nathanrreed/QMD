//package lach_01298.qmd.render.entity;
//
//import com.mojang.blaze3d.vertex.*;
//import lach_01298.qmd.QMD;
//import lach_01298.qmd.entity.EntityGluonBeam;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.HumanoidArm;
//import net.minecraft.world.entity.player.Player;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//import org.joml.Quaternionf;
//
//@OnlyIn(Dist.CLIENT)
//public class RenderGluonBeam extends EntityRenderer<EntityGluonBeam> {
//    private static final ResourceLocation Texture = ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "textures/entities/gluon_beam.png");
//    private float previouspartialTick = -1;
//
//    public RenderGluonBeam(EntityRendererProvider.Context context) {
//        super(context);
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(EntityGluonBeam entity) {
//        return Texture;
//    }
//
//    @Override
//    public void render(EntityGluonBeam entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        if (previouspartialTick == partialTick) {
//            return;
//        } else {
//            previouspartialTick = partialTick;
//        }
//
//        if (entity.getOwner() == null) {
//            return;
//        }
//
//        float width = 0.15F;
//        float brightness = 1.0F;
//
//        float timeFactor = ((float) entity.tickCount + partialTick) / 0.05F;
//        Player player = Minecraft.getInstance().player;
//        poseStack.pushPose();
//        poseStack.translate(entity.getOwner().getX() - player.getX(), entity.getOwner().getY() + entity.getOwner().getEyeHeight() - player.getY(), entity.getOwner().getZ() - player.getZ());
//
//        poseStack.mulPose(new Quaternionf().setAngleAxis(Math.toRadians(-90 - entity.getOwner().getYRot()), 0, 1, 0));
//        poseStack.mulPose(new Quaternionf().setAngleAxis(Math.toRadians(-entity.getOwner().getXRot()), 0, 0, 1));
//
//        poseStack.pushPose();
//
//        if (entity.getHand() == InteractionHand.MAIN_HAND && entity.getOwner().getMainArm() == HumanoidArm.RIGHT || entity.getHand() == InteractionHand.OFF_HAND && entity.getOwner().getMainArm() == HumanoidArm.LEFT) {
//            poseStack.translate(0.45, -0.24, 0.3);
//        } else {
//            poseStack.translate(0.5, -0.24, -0.3);
//        }
//
//        poseStack.pushPose();
//        poseStack.mulPose(new Quaternionf().setAngleAxis(Math.toRadians(timeFactor), 1, 0, 0));
//
//        Tesselator tesselator = Tesselator.getInstance();
//        BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
////
////        float lastBrightnessX = OpenGlHelper.lastBrightnessX;
////        float lastBrightnessY = OpenGlHelper.lastBrightnessY;
////        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
////        poseStack.disableLighting();
////
////        int lastBlendFuncSrc = poseStack.glGetInteger(GL11.GL_BLEND_SRC);
////        int lastBlendFuncDest = poseStack.glGetInteger(GL11.GL_BLEND_DST);
////        poseStack.enableBlend();
////        poseStack.blendFunc(poseStack.SourceFactor.SRC_ALPHA, poseStack.DestFactor.ONE);
////        poseStack.disableCull();
////        poseStack.enableRescaleNormal();
//
//        for (int i = 0; i < 2; ++i) {
//            poseStack.mulPose(new Quaternionf().setAngleAxis(Math.toRadians(90), 1, 0, 0));
////            poseStack.glNormal3f(0.0F, 0.0F, 0.0125F);
////
////            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
//            bufferbuilder.addVertex((float) entity.getLength(), -width, 0.0f).setUv(0, (float) (entity.getLength() - timeFactor / 200f)).setColor(1.0f, 1.0f, 1.0f, brightness);
//            bufferbuilder.addVertex(0f, -width, 0.0f).setUv(0, (float) (0 - timeFactor / 200d)).setColor(1.0f, 1.0f, 1.0f, brightness);
//            bufferbuilder.addVertex(0f, width, 0.0f).setUv(1, (float) (0 - timeFactor / 200d)).setColor(1.0f, 1.0f, 1.0f, brightness);
//            bufferbuilder.addVertex((float) entity.getLength(), width, 0.0f).setUv(1, (float) (entity.getLength() - timeFactor / 200d)).setColor(1.0f, 1.0f, 1.0f, brightness);
//        }
//
////        poseStack.disableRescaleNormal();
////        poseStack.enableCull();
////
////        poseStack.disableBlend();
////
////        poseStack.enableLighting();
////        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
//
//        poseStack.popPose();
//        poseStack.popPose();
//        poseStack.popPose();
//    }
//}