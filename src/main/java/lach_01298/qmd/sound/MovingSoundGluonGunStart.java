//package lach_01298.qmd.sound;
//
//import lach_01298.qmd.entity.EntityGluonBeam;
//import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
//import net.minecraft.client.resources.sounds.SoundInstance;
//import net.minecraft.sounds.SoundSource;
//
//public class MovingSoundGluonGunStart extends AbstractTickableSoundInstance {
//    private final EntityGluonBeam beam;
//
//    public MovingSoundGluonGunStart(EntityGluonBeam entity) {
//        super(QMDSounds.gluon_gun_start.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
//        this.beam = entity;
//        this.looping = false;
//        this.delay = 0;
//        this.volume = 0.1F;
//        this.x = (float) entity.getX();
//        this.y = (float) entity.getY();
//        this.z = (float) entity.getZ();
//    }
//
//    @Override
//    public void tick() {
//        if (!this.beam.isAlive()) {
//            this.stop();
//        } else {
//            this.x = (float) this.beam.getX();
//            this.y = (float) this.beam.getY();
//            this.z = (float) this.beam.getZ();
//        }
//    }
//}