package lach_01298.qmd.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.math.BigDecimal;
import java.math.MathContext;

public class Util {
    public static ResourceLocation appendPath(ResourceLocation location, String stringToAppend) {
        return location.withSuffix(stringToAppend);
    }

    public static Direction getAxisFacing(Direction.Axis axis, boolean positive) {
        if (axis == Direction.Axis.X) {
            if (positive) {
                return Direction.EAST;
            }
            return Direction.WEST;
        }
        if (axis == Direction.Axis.Y) {
            if (positive) {
                return Direction.UP;
            }
            return Direction.DOWN;
        }
        if (axis == Direction.Axis.Z) {
            if (positive) {
                return Direction.SOUTH;
            }
            return Direction.NORTH;
        }

        return null;
    }

    public static int getTaxiDistance(BlockPos a, BlockPos b) {
        int x = Math.abs(a.getX() - b.getX());
        int y = Math.abs(a.getY() - b.getY());
        int z = Math.abs(a.getZ() - b.getZ());
        return x + y + z;
    }

    public static boolean mineBlock(Level level, BlockPos pos, BlockState state, Player player) {
        boolean removed = state.onDestroyedByPlayer(level, pos, player, state.canHarvestBlock(level, pos, player), level.getFluidState(pos));
        if (removed)
            state.getBlock().destroy(level, pos, state);
        return removed;
    }

    //    public static boolean mineBlock(Level level, BlockPos pos, Player player, int fortune, boolean silkTouch, boolean ignoreHarvestLevels) {
//        BlockState state = level.getBlockState(pos);
//        Block block = state.getBlock();
//
//        if (level.getBlockState(pos).isAir()) {
//            return false;
//        }
//
//        if (!ForgeHooks.canHarvestBlock(block, player, level, pos) && !ignoreHarvestLevels) {
//            return false;
//        }
//
//        if (state.getBlockHardness(level, pos) < 0) // Don't break unbreakable blocks
//        {
//            return false;
//        }
//
//        ServerPlayer playerMP = null;
//        if (player instanceof ServerPlayer) {
//            playerMP = (ServerPlayer) player;
//        }
//
//        if (playerMP != null) {
//            if (ForgeHooks.onBlockBreakEvent(level, playerMP.interactionManager.getGameType(), playerMP, pos) == -1) { // Should make the breaking cancelable
//                return false;
//            }
//        }
//
//        if (!level.isClientSide()) {
//            if (block.removedByPlayer(state, level, pos, player, !player.capabilities.isCreativeMode)) {
//                block.onPlayerDestroy(level, pos, state);
//
//                player.addStat(StatList.getBlockStats(block));
//
//                if (silkTouch && block.canSilkHarvest(level, pos, state, player) && !player.isCreative()) {
//                    List<ItemStack> items = new ArrayList<ItemStack>();
//                    ItemStack itemstack = getSilkTouchDrop(state);
//
//                    if (!itemstack.isEmpty()) {
//                        items.add(itemstack);
//                    }
//
//                    net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, level, pos, state, 0, 1.0f, true,
//                            player);
//                    for (ItemStack item : items) {
//                        block.spawnAsEntity(level, pos, item);
//                    }
//                } else if (!player.isCreative()) {
//
//                    int xp = state.getBlock().getExpDrop(state, level, pos, fortune);
//                    block.dropXpOnBlockBreak(level, pos, xp);
//                    block.dropBlockAsItem(level, pos, state, fortune);
//                }
//
//
//            }
//            playerMP.connection.sendPacket(new SPacketBlockChange(level, pos));
//        } else {
//            if (block.removedByPlayer(state, level, pos, player, !player.capabilities.isCreativeMode)) {
//                block.onPlayerDestroy(level, pos, state);
//            }
//            Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
//        }
//
//        return true;
//    }
//
//
//    public static ItemStack getSilkTouchDrop(BlockState state) {
//        Item item = Item.getItemFromBlock(state.getBlock());
//        int i = 0;
//
//        if (item.getHasSubtypes()) {
//            i = state.getBlock().getMetaFromState(state);
//        }
//
//        return new ItemStack(item, 1, i);
//    }
//
//
    public static void createGammaFlash(Level world, Vec3 pos, double size, float explosionSize, double radiation) {
//        if (explosionSize > 0f) { TODO
//            world.createExplosion(null, pos.x, pos.y, pos.z, explosionSize, true);
//        }
//
//        world.spawnEntity(new EntityGammaFlash(world, pos.x, pos.y, pos.z, size));
//
//        Set<EntityLivingBase> entitylist = new HashSet();
//        double radius = 128 * Math.sqrt(size);
//
//        entitylist.addAll(world.getEntitiesWithinAABB(EntityLivingBase.class,
//                new AxisAlignedBB(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius)));
//
//        for (EntityLivingBase entity : entitylist) {
//            IEntityRads entityRads = RadiationHelper.getEntityRadiation(entity);
//            if (entityRads != null) {
//                double rads = Math.min(radiation, (radiation) / pos.squareDistanceTo(entity.posX, entity.posY, entity.posZ));
//
//                entityRads.setRadiationLevel(RadiationHelper.addRadsToEntity(entityRads, entity, rads, false, false, 1));
//                if (entityRads.isFatal() && !entityRads.isImmune()) {
//                    entity.attackEntityFrom(DamageSources.FATAL_RADS, Float.MAX_VALUE);
//                }
//            }
//        }
    }

    public static double roundToSigFigs(double number, int sigFigs) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.round(new MathContext(sigFigs));
        return bd.doubleValue();
    }
}