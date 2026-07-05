//package lach_01298.qmd.entity;
//
//import lach_01298.qmd.QMD;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.MobCategory;
//import net.neoforged.bus.api.IEventBus;
//import net.neoforged.neoforge.registries.DeferredRegister;
//
//import java.util.function.Supplier;
//
//public class QMDEntities {
//    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, QMD.MOD_ID);
//
//    public static Supplier<EntityType<EntityGammaFlash>> GAMMA_FLASH;
//    public static Supplier<EntityType<EntityLeptonBeam>> LEPTON_BEAM;
//    public static Supplier<EntityType<EntityGluonBeam>> GLUON_BEAM;
//    public static Supplier<EntityType<EntityAntimatterProjectile>> ANTIMATER_PROJECTILE;
//
//    public static void init(IEventBus modEventBus) {
//        GAMMA_FLASH = ENTITY_TYPES.register("gamma_flash", () -> GammaFlash().noSummon().setTrackingRange(128).updateInterval(1).build("gamma_flash"));
//        LEPTON_BEAM = ENTITY_TYPES.register("lepton_beam", () -> LeptonBeam().sized(0.25f, 0.25f).noSummon().setTrackingRange(128).updateInterval(1).build("lepton_beam"));
//        GLUON_BEAM = ENTITY_TYPES.register("gluon_beam", () -> GluonBeam().sized(0.25f, 0.25f).noSummon().setTrackingRange(128).updateInterval(1).build("gluon_beam"));
//        ANTIMATER_PROJECTILE = ENTITY_TYPES.register("antimater_projectile", () -> AntimatterProjectile().sized(0.5F, 0.5F).noSummon().setTrackingRange(128).updateInterval(1).build("antimater_projectile"));
//
//        ENTITY_TYPES.register(modEventBus);
//    }
//
//    private static EntityType.Builder<EntityGammaFlash> GammaFlash() {
//        EntityType.Builder<EntityGammaFlash> builder = EntityType.Builder.of(EntityGammaFlash::new, MobCategory.MISC);
//        return builder;
//    }
//
//    private static EntityType.Builder<EntityLeptonBeam> LeptonBeam() {
//        EntityType.Builder<EntityLeptonBeam> builder = EntityType.Builder.of(EntityLeptonBeam::new, MobCategory.MISC);
//        return builder;
//    }
//
//    private static EntityType.Builder<EntityGluonBeam> GluonBeam() {
//        EntityType.Builder<EntityGluonBeam> builder = EntityType.Builder.of(EntityGluonBeam::new, MobCategory.MISC);
//        return builder;
//    }
//
//    private static EntityType.Builder<EntityAntimatterProjectile> AntimatterProjectile() {
//        EntityType.Builder<EntityAntimatterProjectile> builder = EntityType.Builder.of(EntityAntimatterProjectile::new, MobCategory.MISC);
//        return builder;
//    }
//}