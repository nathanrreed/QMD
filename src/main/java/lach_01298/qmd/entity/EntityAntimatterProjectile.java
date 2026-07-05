//package lach_01298.qmd.entity;
//
//import lach_01298.qmd.config.QMDServerConfig;
//import lach_01298.qmd.network.AntimatterProjectileUpdatePacket;
//import lach_01298.qmd.util.Util;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.projectile.Projectile;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.phys.HitResult;
//import net.minecraft.world.phys.Vec3;
//import net.neoforged.neoforge.network.PacketDistributor;
//
//import java.awt.*;
//
//import static lach_01298.qmd.entity.QMDEntities.ANTIMATER_PROJECTILE;
//
//public class EntityAntimatterProjectile extends Projectile {
//    private Color color;
//    private double damage = 1.0f;
//
//    public EntityAntimatterProjectile(EntityType<EntityAntimatterProjectile> entityType, Level level) {
//        super(entityType, level);
//    }
//
//    public EntityAntimatterProjectile(Level level, double x, double y, double z) {
//        this(ANTIMATER_PROJECTILE.get(), level);
//        this.setPos(x, y, z);
//    }
//
//    public EntityAntimatterProjectile(Level level, LivingEntity shooter, double damage, int color) {
//        this(level, shooter.getX(), shooter.getY() + (double) shooter.getEyeHeight() - 0.10000000149011612D, shooter.getZ());
//        this.damage = damage;
//        this.color = new Color(color);
//    }
//
//    public Color getColor() {
//        return this.color;
//    }
//
//    public void setColor(int color) {
//        this.color = new Color(color);
//    }
//
//    public void explode(Level world, Vec3 pos) {
//        if (!world.isClientSide()) {
//            double size = this.damage;
//            Util.createGammaFlash(world, pos, size, (float) (size * QMDServerConfig.antimatter_launcher_explosion_size), QMDServerConfig.cell_radiation * size);
//        }
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        if (!level().getBlockState(getOnPos()).isAir() || this.isInWater()) {
//            explode(level(), this.position());
//            this.setRemoved(RemovalReason.DISCARDED);
//        }
//    }
//
//    @Override
//    protected void onHit(HitResult result) {
//        super.onHit(result);
//        explode(level(), this.position());
//    }
//
//    protected void sendUpdatePacket() {
//        if (!this.level().isClientSide()) {
//            PacketDistributor.sendToPlayersNear((ServerLevel) level(), null, this.getX(), this.getY(), this.getZ(), 128, new AntimatterProjectileUpdatePacket(this));
//        }
//    }
//
//    @Override
//    protected void defineSynchedData(SynchedEntityData.Builder builder) {
//    }
//}