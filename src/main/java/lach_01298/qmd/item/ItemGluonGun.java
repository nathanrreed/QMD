//package lach_01298.qmd.item;
//
//import com.nred.nuclearcraft.capability.radiation.entity.IEntityRads;
//import com.nred.nuclearcraft.radiation.RadiationHelper;
//import com.nred.nuclearcraft.util.InfoHelper;
//import lach_01298.qmd.QMDDamageSources;
//import lach_01298.qmd.config.QMDServerConfig;
//import lach_01298.qmd.entity.EntityGluonBeam;
//import lach_01298.qmd.enums.MaterialTypes.CellType;
//import net.minecraft.ChatFormatting;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.item.UseAnim;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.phys.EntityHitResult;
//import net.minecraft.world.phys.HitResult;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ItemGluonGun extends ItemGun implements IItemMode {
//    private EntityGluonBeam beam;
//
//    public ItemGluonGun() {
//        super();
//    }
//
//    @Override
//    public int getUseDuration(ItemStack stack, LivingEntity entity) {
//        return 72000;
//    }
//
//    @Override
//    public UseAnim getUseAnimation(ItemStack stack) {
//        return UseAnim.NONE;
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack itemstack = player.getItemInHand(hand);
//
//        if (player.isCrouching()) {
//            if (!level.isClientSide()) {
//                if (getMode(itemstack).equals("breaking")) {
//                    setMode(itemstack, "silk_touch");
//                    player.sendSystemMessage(Component.translatable("info.qmd.item.mode.switch", Component.translatable("info.qmd.item.mode.silk_touch").withStyle(ChatFormatting.DARK_GREEN)));
//                } else {
//                    setMode(itemstack, "breaking");
//                    player.sendSystemMessage(Component.translatable("info.qmd.item.mode.switch", Component.translatable("info.qmd.item.mode.breaking").withStyle(ChatFormatting.DARK_GREEN)));
//                }
//            }
//
//            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
//        }
//
//        if (findCell(player) >= 0) {
//            ItemStack cell = player.getInventory().getItem(findCell(player));
//            ItemCell itemCell = (ItemCell) cell.getItem();
//            if (itemCell.getAmountStored(cell) < QMDServerConfig.gluon_particle_usage) {
//                return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
//            }
//
//            player.startUsingItem(hand);
//
//            HitResult lookingAt = rayTrace(player, level, QMDServerConfig.gluon_range, 1.0f, true, true);
//
//            if (!level.isClientSide()) {
//                if (lookingAt != null) {
//                    double length = lookingAt.getLocation().distanceTo(player.position().add(0, player.getEyeHeight(), 0));
//                    if (length > QMDServerConfig.gluon_range) {
//                        length = QMDServerConfig.gluon_range;
//                    }
//                    beam = new EntityGluonBeam(level, player, length, hand);
//                } else {
//                    beam = new EntityGluonBeam(level, player, QMDServerConfig.gluon_range, hand);
//                }
//                level.addFreshEntity(beam);
//            }
//            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
//        }
//
//        return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
//    }
//
//    @Override
//    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
//        if (livingEntity instanceof Player player) {
//            if (findCell(player) >= 0) {
//                ItemStack cell = player.getInventory().getItem(findCell(player));
//                ItemCell itemCell = (ItemCell) cell.getItem();
//                if (itemCell.getAmountStored(cell) < QMDServerConfig.gluon_particle_usage) {
//                    player.stopUsingItem();
//                    return;
//                }
//
//                if (!player.isCreative()) {
//                    player.getInventory().setItem(findCell(player), itemCell.use(cell, QMDServerConfig.gluon_particle_usage));
//                }
//
//                HitResult lookingAt = rayTrace(player, level, QMDServerConfig.gluon_range, 1.0f, true, true);
//
//                if (!level.isClientSide()) {
//                    if (lookingAt != null && lookingAt.getType() == BlockHitResult.Type.BLOCK && lookingAt instanceof BlockHitResult blockHitResult) {
//                        BlockPos pos = blockHitResult.getBlockPos();
//
//                        String mode = getMode(stack);
//                        ItemStack enchantedStack = stack.copy();
//                        if (mode.equals("breaking")) {
////                            Util.mineBlock(level, pos, player, 4, false, true); TODO
//                            enchantedStack.enchant(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 4);
//
//                        } else if (mode.equals("silk_touch")) {
////                            Util.mineBlock(level, pos, player, 0, true, true);
//                            enchantedStack.enchant(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), 1);
//                        }
//
//                        BlockState blockState = level.getBlockState(pos);
//                        Block block = blockState.getBlock();
////                        BlockState removedBlockState = block.playerWillDestroy(level, pos, blockState, player);
////                        FluidState fluidstate = level.getFluidState(pos);
////                        boolean flag = blockState.onDestroyedByPlayer(level, pos, player, false, fluidstate);
////                        if (flag) {
////                            block.destroy(level, pos, removedBlockState);
////                        }
//////                        Block.dropResources(blockState, level, pos, null, player, stack.set(DataComponents.ENCHANTMENTS, new ItemEnchantments.Mutable(new ItemEnchantments())));
////
//                        boolean removed = blockState.onDestroyedByPlayer(level, pos, player, true, level.getFluidState(pos));
//                        if (removed) {
//                            block.destroy(level, pos, blockState);
//                            block.playerDestroy(level, player, pos, blockState, null, enchantedStack);
//                        }
//
//
//                    } else if (lookingAt != null && lookingAt.getType() == BlockHitResult.Type.ENTITY && lookingAt instanceof EntityHitResult entityHitResult) {
//                        Entity entity = entityHitResult.getEntity();
//
//                        if (entity instanceof LivingEntity) {
//                            IEntityRads entityRads = RadiationHelper.getEntityRadiation((LivingEntity) entity);
//                            entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, (LivingEntity) entity, QMDServerConfig.gluon_radiation, false, false, 1));
//                        }
//                        entity.hurt(QMDDamageSources.causeGluonGunDamage(beam, player), (float) QMDServerConfig.gluon_damage);
//                    }
//
//                    if (lookingAt != null) {
//                        beam.setLength(lookingAt.getLocation().distanceTo(player.position().add(0, player.getEyeHeight(), 0)));
//                    }
//                }
//            } else {
//                livingEntity.stopUsingItem();
//            }
//        }
//    }
//
//    private int findCell(Player player) {
//        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
//            ItemStack itemstack = player.getInventory().getItem(i);
//            if (this.isCell(itemstack)) {
//                return i;
//            }
//        }
//
//        return -1;
//    }
//
//    private boolean isCell(ItemStack stack) {
//        if (stack.getItem() instanceof ItemCell cell) {
//            return cell.getCellType() == CellType.GLUEBALLS;
//        }
//        return false;
//    }
//
//    @Override
//    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//        InfoHelper.infoLine(tooltipComponents, ChatFormatting.DARK_GREEN, Component.translatable("info.qmd.item.mode", Component.translatable("info.qmd.item.mode." + getMode(stack))));
//        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//    }
//
//    @Override
//    public String getDefaultMode() {
//        return "breaking";
//    }
//
//    @Override
//    public List<String> getModes() {
//        List<String> modes = new ArrayList<>();
//        modes.add("breaking");
//        modes.add("silk_touch");
//        return modes;
//    }
//}