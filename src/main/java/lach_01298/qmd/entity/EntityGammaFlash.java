//package lach_01298.qmd.entity;
//
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.level.Level;
//
//import static lach_01298.qmd.entity.QMDEntities.GAMMA_FLASH;
//
//public class EntityGammaFlash extends Entity {
//    private int livingTime;
//    private double size;
//
//    public EntityGammaFlash(EntityType<EntityGammaFlash> entityType, Level level) {
//        super(entityType, level);
//        this.livingTime = 50;
//        this.size = 1;
//    }
//
//    public EntityGammaFlash(Level level, double x, double y, double z, double size) {
//        super(GAMMA_FLASH.get(), level);
//        this.setPos(x, y, z);
//        this.setRot(0.0F, 0.0F);
//        this.livingTime = 500;
//        this.size = size;
//    }
//
//    public double getSize() {
//        return size;
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//
//        if (this.livingTime == 0) {
//            this.setRemoved(RemovalReason.DISCARDED);
//        } else {
//            --this.livingTime;
//        }
//    }
//
//    @Override
//    protected void defineSynchedData(SynchedEntityData.Builder builder) {
//    }
//
//    @Override
//    protected void readAdditionalSaveData(CompoundTag compound) {
//    }
//
//    @Override
//    protected void addAdditionalSaveData(CompoundTag compound) {
//    }
//}