//package lach_01298.qmd.entity;
//
//import lach_01298.qmd.network.BeamProjectileUpdatePacket;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.neoforged.neoforge.network.PacketDistributor;
//
//public abstract class EntityBeamProjectile extends Entity {
//    protected double length = 1;
//    protected int livingTime;
//    protected Player owner;
//    protected InteractionHand hand;
//
//    public EntityBeamProjectile(EntityType<? extends Entity> entityType, Level level) {
//        super(entityType, level);
//        this.livingTime = 100;
//    }
//
//    public EntityBeamProjectile(EntityType<? extends Entity> entityType, Level level, Player player, double length, InteractionHand hand, int lifetime) {
//        super(entityType, level);
//        this.owner = player;
//        this.hand = hand;
//        this.length = length;
//        this.livingTime = lifetime;
//
//        this.setPos(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
//        this.setRot(player.getYRot(), player.getXRot());
//    }
//
//    @Override
//    public boolean shouldRenderAtSqrDistance(double distance) {
//        return super.shouldRenderAtSqrDistance(distance + this.length);
//    }
//
//    public void setOwner(Player owner) {
//        this.owner = owner;
//    }
//
//    public void setLength(double length) {
//        this.length = length;
//    }
//
//    public void setHand(InteractionHand hand) {
//        this.hand = hand;
//    }
//
//    public Player getOwner() {
//        return owner;
//    }
//
//    public double getLength() {
//        return length;
//    }
//
//    public InteractionHand getHand() {
//        return hand;
//    }
//
//    @Override
//    protected void defineSynchedData(SynchedEntityData.Builder builder) {
//    }
//
//    @Override
//    protected void readAdditionalSaveData(CompoundTag compound) {
//        livingTime = compound.getInt("livingTime");
//    }
//
//    @Override
//    protected void addAdditionalSaveData(CompoundTag compound) {
//        compound.putInt("livingTime", livingTime);
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//
//        if (this.livingTime <= 0) {
//            this.setRemoved(RemovalReason.DISCARDED);
//        } else {
//            --this.livingTime;
//        }
//        sendUpdatePacket();
//    }
//
//    protected void sendUpdatePacket() {
//        if (!this.level().isClientSide()) {
//            if (this.getOwner() != null) {
//                PacketDistributor.sendToPlayersNear((ServerLevel) level(), null, this.getX(), this.getY(), this.getZ(), 128, new BeamProjectileUpdatePacket(this));
//            }
//        }
//    }
//}