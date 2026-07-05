package lach_01298.qmd.item;

import com.nred.nuclearcraft.capability.radiation.entity.IEntityRads;
import com.nred.nuclearcraft.item.NCItem;
import com.nred.nuclearcraft.radiation.RadiationHelper;
import com.nred.nuclearcraft.util.InfoHelper;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.util.Units;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nred.nuclearcraft.registration.DamageTypeRegistration.FATAL_RADS;

public class ItemCustomParticleSource extends NCItem implements IItemParticleAmount {
    private final int capacity;
    private ItemStack emptyItem;
    private boolean explode;
    private double size;
    private double radiation;

    public ItemCustomParticleSource(int capacity, int stackSize) {
        super(new Properties().stacksTo(stackSize));
        this.capacity = capacity;
        emptyItem = ItemStack.EMPTY;
        explode = false;
        size = 0;
        radiation = 0;
    }

    public ItemCustomParticleSource(int capacity, int stackSize, double size, double radiation) {
        super(new Properties().stacksTo(stackSize));

        this.capacity = capacity;
        emptyItem = ItemStack.EMPTY;
        explode = true;
        this.size = size;
        this.radiation = radiation;
    }

    @Override
    public int getItemCapacity(ItemStack stack) {
        return capacity;
    }

    @Override
    public ItemStack getEmptyItem() {
        return new ItemStack(emptyItem.getItem(), 1);
    }

    public void setEmptyItem(ItemStack stack) {
        emptyItem = stack;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Mth.ceil(Mth.clamp((double) getAmountStored(stack) / (float) this.getItemCapacity(stack), 0D, 1D) * 13f);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getAmountStored(stack) > 0;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return FastColor.ARGB32.lerp((float) getItemCapacity(stack) / (float) getAmountStored(stack), ChatFormatting.RED.getColor(), ChatFormatting.GREEN.getColor());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        InfoHelper.infoLine(tooltipComponents, ChatFormatting.DARK_GREEN, Component.translatable("info.qmd.item.amount", Units.getSIFormat(getAmountStored(stack), "pu"), Units.getSIFormat(getItemCapacity(stack), "pu")));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entityItem) {
        if (explode) {
            if (entityItem.tickCount > QMDServerConfig.cell_lifetime || entityItem.isInLava() || entityItem.isOnFire() || !entityItem.isAlive()) {
                explode(entityItem.level(), entityItem.getOnPos(), stack);
                stack.shrink(stack.getCount());

            }
        }
        return false;
    }

    public void explode(Level level, BlockPos pos, ItemStack stack) {
        if (!level.isClientSide()) {
            double s = size;
            double r = radiation;

            if (IItemParticleAmount.getCapacity(stack) > 0) {
                s *= stack.getCount() * getAmountStored(stack) / (double) IItemParticleAmount.getCapacity(stack);
                r *= stack.getCount() * getAmountStored(stack) / (double) IItemParticleAmount.getCapacity(stack);
            }


            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) s, Level.ExplosionInteraction.BLOCK);
//            level.addFreshEntity(new EntityGammaFlash(level, pos.getX(), pos.getY(), pos.getZ(), s)); TODO

            double radius = 128 * Math.sqrt(s);

            Set<LivingEntity> entitylist = new HashSet<>(level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
                            pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius)));

            for (LivingEntity entity : entitylist) {
                IEntityRads entityRads = RadiationHelper.getEntityRadiation(entity);
                if (entityRads != null) {
                    double rads = Math.min(r, r / pos.distToLowCornerSqr(entity.getX(), entity.getY(), entity.getZ()));
                    entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, entity, rads, false, false, 1));

                    if (rads >= entityRads.getMaxRads()) {
                        entity.hurt(level.damageSources().source(FATAL_RADS), Float.MAX_VALUE);
                    }
                }
            }
        }
    }


}
