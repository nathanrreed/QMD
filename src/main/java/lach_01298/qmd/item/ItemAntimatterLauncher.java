//package lach_01298.qmd.item;
// TODO
//import lach_01298.qmd.enums.MaterialTypes.CellType;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionResultHolder;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//
//public class ItemAntimatterLauncher extends ItemGun {
//    public ItemAntimatterLauncher(String... tooltip) {
//        super(tooltip);
//    }
//
//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack itemstack = player.getItemInHand(hand);
//
//        if (findCell(player) >= 0) {
//            ItemStack cell = player.getInventory().getItem(findCell(player));
//            ItemCell itemCell = (ItemCell) cell.getItem();
//            CellType type = itemCell.getCellType();
//            float damage = 0;
//            int color = 0;
//            switch (type) {
//                case ANTIHYDROGEN -> {
//                    damage = 1.0f;
//                    color = 0xB37AC4;
//                }
//                case ANTIDEUTERIUM -> {
//                    damage = 2.0f;
//                    color = 0x9E6FEF;
//                }
//                case ANTITRITIUM -> {
//                    damage = 3.0f;
//                    color = 0x5DBBD6;
//                }
//                case ANTIHELIUM3 -> {
//                    damage = 3.0f;
//                    color = 0xCBBB67;
//                }
//                case ANTIHELIUM -> {
//                    damage = 4.0f;
//                    color = 0xC57B81;
//                }
//            }
//
//            if (itemCell.getAmountStored(cell) < QMDConfig.antimatter_launcher_particle_usage) {
//                return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
//            }
//
//            if (!player.isCreative()) {
//                player.getInventory().setItem(findCell(player), itemCell.use(cell, QMDConfig.antimatter_launcher_particle_usage));
//            }
//
//            player.getCooldowns().addCooldown(this, QMDConfig.antimatter_launcher_cool_down);
//
//            if (!level.isClientSide()) {
//                EntityAntimatterProjectile projectile = new EntityAntimatterProjectile(world, player, damage, color);
//                float velocity = 3.0F;
//                projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, 1.0F);
//                world.spawnEntity(projectile);
//            }
//
//            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
//        }
//
//        return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
//    }
//
//
//    private int findCell(Player player) {
//        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
//            ItemStack itemstack = player.getInventory().getItem(i);
//
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
//            return cell.getCellType() == CellType.ANTIHYDROGEN || cell.getCellType() == CellType.ANTIDEUTERIUM || cell.getCellType() == CellType.ANTITRITIUM || cell.getCellType() == CellType.ANTIHELIUM3 || cell.getCellType() == CellType.ANTIHELIUM;
//        }
//        return false;
//    }
//}