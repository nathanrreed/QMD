//package lach_01298.qmd.item;
//
//import com.nred.nuclearcraft.item.TooltipItem;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.util.Mth;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntitySelector;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.ClipContext;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.phys.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//public abstract class ItemGun extends TooltipItem {
//    public ItemGun(String... tooltip) {
//        super(new Properties().stacksTo(1), Arrays.stream(tooltip).toList());
//    }
//
//    public HitResult rayTrace(Player player, Level level, double reachDistance, float partialTicks, boolean hitBlocks, boolean hitEntities) {
//        Vec3 eyesPos = player.getEyePosition(partialTicks);
//        Vec3 lookVec = player.getViewVector(1.0F);
//        Vec3 rayVec = eyesPos.add(lookVec.scale(reachDistance));
//        Entity pointedEntity = null;
//        Vec3 hitVec = null;
//        EntityHitResult entityHit = null;
//        BlockHitResult blockHit = null;
//        HitResult missHit = BlockHitResult.miss(eyesPos.subtract(rayVec), Direction.UP, new BlockPos(Mth.floor(eyesPos.subtract(rayVec).x), Mth.floor(eyesPos.subtract(rayVec).y), Mth.floor(eyesPos.subtract(rayVec).z)));
//
//        if (hitEntities) {
//            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().deflate(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance).inflate(1.0), EntitySelector.NO_SPECTATORS.and(Objects::nonNull).and(entity -> !entity.equals(player)));
//
//            double d2 = reachDistance;
//            for (LivingEntity entity1 : list) {
//                AABB axisalignedbb = entity1.getBoundingBox().inflate(entity1.getPickRadius());
//                Optional<Vec3> raytraceresult = axisalignedbb.clip(eyesPos, rayVec);
//
//                if (axisalignedbb.contains(eyesPos)) {
//                    pointedEntity = entity1;
//                    hitVec = raytraceresult.orElse(eyesPos);
//                } else if (raytraceresult.isPresent()) {
//                    double d3 = eyesPos.distanceTo(raytraceresult.get());
//
//                    if (d3 < d2 || d2 == 0.0D) {
//                        if (entity1.getRootVehicle() == player.getRootVehicle() && !entity1.canRiderInteract()) {
//                            if (d2 == 0.0D) {
//                                pointedEntity = entity1;
//                                hitVec = raytraceresult.get();
//                            }
//                        } else {
//                            pointedEntity = entity1;
//                            hitVec = raytraceresult.get();
//                            d2 = d3;
//                        }
//                    }
//                }
//            }
//            if (pointedEntity != null) {
//                entityHit = new EntityHitResult(pointedEntity, hitVec);
//            }
//        }
//
//        if (hitBlocks) {
//            blockHit = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
//        }
//
//        double blockDistance = 0;
//        double entityDistance = 0;
//
//        if (entityHit != null) {
//            entityDistance = entityHit.distanceTo(player);
//        }
//
//        if (blockHit != null) {
//            blockDistance = blockHit.distanceTo(player);
//        }
//
//        if (hitEntities && hitBlocks) {
//            if (entityDistance < blockDistance && entityDistance > 0) {
//                return entityHit;
//            } else {
//                return blockHit;
//            }
//        } else if (hitEntities) {
//            if (entityHit == null) {
//                return missHit;
//            }
//            return entityHit;
//        } else {
//            if (blockHit == null) {
//                return missHit;
//            }
//            return blockHit;
//        }
//    }
//}