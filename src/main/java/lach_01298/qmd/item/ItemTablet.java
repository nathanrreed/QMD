package lach_01298.qmd.item;

import com.nred.nuclearcraft.item.NCFoodItem;
import lach_01298.qmd.QMDDamageSources;
import lach_01298.qmd.config.QMDServerConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.nred.nuclearcraft.registration.CapabilityRegistration.CAPABILITY_ENTITY_RADS;

public class ItemTablet extends NCFoodItem {
    public ItemTablet() {
        super(0, 0f, List.of(), "item.qmd.potassium_iodine_tablet.desc", true);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity player) {
        super.finishUsingItem(stack, level, player);

        double lifetime = QMDServerConfig.ki_time;
        double currentTime = player.getCapability(CAPABILITY_ENTITY_RADS, null).getRadiationImmunityTime();

        if (currentTime > lifetime) {
            double newTime = currentTime + lifetime * lifetime / currentTime;
            player.getCapability(CAPABILITY_ENTITY_RADS, null).setRadiationImmunityTime(newTime);

            int newIntTime = (int) newTime;

            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, newIntTime));

            if (currentTime > lifetime * 1.5) { // Maybe you should not take that many
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, newIntTime));
            }
            if (currentTime > lifetime * 2) { // You should not take this many
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, newIntTime * 2));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, newIntTime * 2));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, newIntTime * 2));
            }
            if (currentTime > lifetime * 3) { // You really should not take this many
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, newIntTime * 2, 1));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, newIntTime * 2, 1));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, newIntTime * 2, 1));
                player.addEffect(new MobEffectInstance(MobEffects.POISON, newIntTime / 2));
            }
            if (currentTime > lifetime * 4) { // Are you trying to kill your self?
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, newIntTime));
                player.hurt(level.damageSources().source(QMDDamageSources.SELF_POISONING), 10f);
            }
            if (currentTime > lifetime * 4.5) { // Apparently so
                player.hurt(level.damageSources().source(QMDDamageSources.SELF_POISONING), player.getHealth());
            }
        } else {
            player.getCapability(CAPABILITY_ENTITY_RADS, null).setRadiationImmunityTime(currentTime + lifetime);
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (int) (currentTime + lifetime)));
        }

        return player.eat(level, stack, stack.getFoodProperties(player));
    }
}