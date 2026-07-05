package lach_01298.qmd;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class QMDDamageSources {
    public static final ResourceKey<DamageType> ANTIMATTER_ANNIHLATION = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "antimatter_annihilation"));
    public static final ResourceKey<DamageType> SELF_POISONING = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "self_poisoning"));

    public static final ResourceKey<DamageType> LEPTON_CANNON = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "lepton_cannon"));
    public static final ResourceKey<DamageType> GLUON_GUN = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "gluon_gun"));
    public static final ResourceKey<DamageType> ANTIMATTER_LAUNCHER = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "antimatter_launcher"));

//    public static final DamageSource causeLeptonCannonDamage(EntityLeptonBeam beam, @Nullable Entity indirectEntityIn) {
//        return beam.level().damageSources().source(LEPTON_CANNON, beam, indirectEntityIn);
//    }
//
//    public static DamageSource causeGluonGunDamage(EntityGluonBeam beam, @Nullable Entity indirectEntityIn) {
//        return beam.level().damageSources().source(GLUON_GUN, beam, indirectEntityIn);
//    }
//
//    public static DamageSource causeAntimatterLauncherDamage(EntityAntimatterProjectile projectile, @Nullable Entity indirectEntityIn) {
//        return projectile.level().damageSources().source(ANTIMATTER_LAUNCHER, projectile, indirectEntityIn);
//    }

    public static void init() {
    }
}