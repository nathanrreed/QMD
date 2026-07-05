//package lach_01298.qmd.entity;
//
//import lach_01298.qmd.item.QMDItems;
//import lach_01298.qmd.sound.MovingSoundGluonGun;
//import lach_01298.qmd.sound.MovingSoundGluonGunStart;
//import lach_01298.qmd.sound.QMDSounds;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.sounds.SoundInstance;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//
//import static lach_01298.qmd.entity.QMDEntities.GLUON_BEAM;
//
//public class EntityGluonBeam extends EntityBeamProjectile {
//    @OnlyIn(Dist.CLIENT)
//    private SoundInstance sound;
//
//    public EntityGluonBeam(EntityType<EntityGluonBeam> entityType, Level level) {
//        super(entityType, level);
//    }
//
//    public EntityGluonBeam(Level level, Player player, double length, InteractionHand hand) {
//        super(GLUON_BEAM.get(), level, player, length, hand, 100);
//    }
//
//    @Override
//    public void tick() {
//        if (level().isClientSide()) {
//            if (this.tickCount == 1) {
//                playStartSound();
//            } else {
//                playSound();
//            }
//        } else {
//            if (owner != null) {
//                this.setPos(owner.getX(), owner.getY() + owner.getEyeHeight(), owner.getZ());
//                this.setRot(owner.getYRot(), owner.getXRot());
//
//                if (!owner.isUsingItem() || !owner.getItemInHand(hand).is(QMDItems.gluonGun)) {
//                    this.setRemoved(RemovalReason.DISCARDED);
//                }
//            } else {
//                if (this.livingTime <= 0) {
//                    this.setRemoved(RemovalReason.DISCARDED);
//                } else {
//                    --this.livingTime;
//                }
//            }
//            sendUpdatePacket();
//        }
//    }
//
//    @Override
//    public void remove(RemovalReason reason) {
//        if (level().isClientSide()) {
//            playStopSound();
//        }
//        this.setRemoved(reason);
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    private void playStopSound() {
//        if (sound != null && Minecraft.getInstance().getSoundManager().isActive(sound)) {
//            Minecraft.getInstance().getSoundManager().stop(sound);
//        }
//        level().playSound(null, getX(), getY(), getZ(), QMDSounds.gluon_gun_stop.get(), SoundSource.NEUTRAL, 0.1f, 1.0f);
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    private void playStartSound() {
//        sound = new MovingSoundGluonGunStart(this);
//        Minecraft.getInstance().getSoundManager().play(sound);
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    private void playSound() {
//        if (sound == null || !Minecraft.getInstance().getSoundManager().isActive(sound)) {
//            sound = new MovingSoundGluonGun(this);
//            Minecraft.getInstance().getSoundManager().play(sound);
//        }
//    }
//}