//package lach_01298.qmd.item;
//
//
//import com.nred.nuclearcraft.NuclearcraftNeohaul;
//import com.nred.nuclearcraft.block_entity.internal.energy.EnergyConnection;
//import com.nred.nuclearcraft.item.energy.IChargeableComponentItem;
//import com.nred.nuclearcraft.util.InfoHelper;
//import com.nred.nuclearcraft.util.NCMath;
//import com.nred.nuclearcraft.util.UnitHelper;
//import lach_01298.qmd.config.QMDStartupConfig;
//import net.minecraft.ChatFormatting;
//import net.minecraft.core.Holder;
//import net.minecraft.network.chat.Component;
//import net.minecraft.util.FastColor;
//import net.minecraft.util.Mth;
//import net.minecraft.world.item.ArmorItem;
//import net.minecraft.world.item.ArmorMaterial;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.TooltipFlag;
//
//import javax.annotation.Nonnull;
//import java.util.List;
//
//public class ItemHEVSuit extends ArmorItem implements IChargeableComponentItem {
//    public final double radiationProtection;
//    private final long capacity;
//    private final int maxTransfer;
//    private final EnergyConnection energyConnection;
//
//    public ItemHEVSuit(Holder<ArmorMaterial> materialIn, ArmorItem.Type equipmentSlotIn, double radiationProtection) {
//        super(materialIn, equipmentSlotIn, new Properties());
//        this.radiationProtection = radiationProtection;
//
//        this.capacity = switch (equipmentSlotIn) {
//            case BOOTS -> QMDStartupConfig.hev_energy[0];
//            case LEGGINGS -> QMDStartupConfig.hev_energy[1];
//            case CHESTPLATE -> QMDStartupConfig.hev_energy[2];
//            case HELMET -> QMDStartupConfig.hev_energy[3];
//            default -> QMDStartupConfig.hev_energy[0];
//        };
//
//
//        this.maxTransfer = NCMath.toInt(this.capacity);
//        this.energyConnection = EnergyConnection.BOTH;
//    }
//
//    @Override
//    public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source,
//                                         double damage, int slot) {
//        if (source.damageType.equals("radiation") || source.damageType.equals("sulphuric_acid")
//                || source.damageType.equals("acid_burn") || source.damageType.equals("corium_burn")
//                || source.damageType.equals("hot_coolant_burn")) {
//            return new ArmorProperties(0, radiationProtection, Integer.MAX_VALUE);
//        }
//
//
//        ArmorProperties armourProp = new ArmorProperties(0, 0, Integer.MAX_VALUE);
//
//        if (armor.hasCapability(CapabilityEnergy.ENERGY, null)) {
//            IEnergyStorage energy = armor.getCapability(CapabilityEnergy.ENERGY, null);
//            if (energy.getEnergyStored() > 0) {
//                // adds the additional stats when charged
//                armourProp.Toughness = QMDConfig.hev_toughness[0] - QMDConfig.hev_toughness[1];
//                armourProp.Armor = QMDConfig.hev_armour[slot] - QMDConfig.hev_armour[slot + 4];
//                energy.extractEnergy((int) (QMDConfig.hev_power[0] * damage), false);
//            }
//        }
//
//        return armourProp;
//    }
//
//    @Override
//    public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot) {
//        if (armor.hasCapability(CapabilityEnergy.ENERGY, null)) {
//            IEnergyStorage energy = armor.getCapability(CapabilityEnergy.ENERGY, null);
//            if (energy.getEnergyStored() > 0) {
//                return QMDConfig.hev_armour[slot] - QMDConfig.hev_armour[slot + 4];
//            }
//        }
//        return 0;
//    }
//
//    @Override
//    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {
//        if (ModCheck.ic2Loaded()) {
//            Potion radiation = Potion.getPotionFromResourceLocation("ic2:radiation");
//            if (radiation != null && entity.isPotionActive(radiation)) {
//                entity.removePotionEffect(radiation);
//            }
//        }
//    }
//
//    @Override
//    public boolean handleUnblockableDamage(EntityLivingBase entity, @Nonnull ItemStack armor, DamageSource source,
//                                           double damage, int slot) {
//        return source.damageType.equals("radiation") || source.damageType.equals("sulphuric_acid")
//                || source.damageType.equals("acid_burn") || source.damageType.equals("corium_burn")
//                || source.damageType.equals("hot_coolant_burn");
//    }
//
//    @Override
//    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//        InfoHelper.infoLine(tooltipComponents, ChatFormatting.LIGHT_PURPLE, Component.translatable(NuclearcraftNeohaul.MODID + ".tooltip.energy_stored", UnitHelper.prefix(getEnergyStored(stack), getMaxEnergyStored(stack), 5, "RF")));
//
//        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//    }
//
//    @Override
//    public boolean isBarVisible(ItemStack stack) {
//        return getEnergyStored(stack) > 0;
//    }
//
//    @Override
//    public int getBarWidth(ItemStack stack) {
//        return Mth.ceil(Mth.clamp((double) getEnergyStored(stack) / (float) capacity, 0D, 1D) * 13f);
//    }
//
//    @Override
//    public int getBarColor(ItemStack stack) {
//        return FastColor.ARGB32.lerp((float) getEnergyStored(stack) / (float) capacity, ChatFormatting.RED.getColor(), ChatFormatting.GREEN.getColor());
//    }
//
//    @Override
//    public long getMaxEnergyStored(ItemStack stack) {
//        return capacity;
//    }
//
//    @Override
//    public int getMaxTransfer(ItemStack stack) {
//        return maxTransfer;
//    }
//
//    @Override
//    public boolean canReceive(ItemStack stack) {
//        return energyConnection.canReceive();
//    }
//
//    @Override
//    public boolean canExtract(ItemStack stack) {
//        return energyConnection.canExtract();
//    }
//
//    @Override
//    public EnergyConnection getEnergyConnection(ItemStack stack) {
//        return energyConnection;
//    }
//}
