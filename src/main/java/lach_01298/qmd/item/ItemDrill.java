//package lach_01298.qmd.item;
//
//import com.nred.nuclearcraft.NuclearcraftNeohaul;
//import com.nred.nuclearcraft.block_entity.internal.energy.EnergyConnection;
//import com.nred.nuclearcraft.item.TooltipItem;
//import com.nred.nuclearcraft.item.energy.IChargeableItem;
//import com.nred.nuclearcraft.util.InfoHelper;
//import com.nred.nuclearcraft.util.NCMath;
//import com.nred.nuclearcraft.util.UnitHelper;
//import lach_01298.qmd.config.QMDServerConfig;
//import lach_01298.qmd.util.Util;
//import net.minecraft.ChatFormatting;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.core.Holder;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.MutableComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.tags.BlockTags;
//import net.minecraft.util.Mth;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.*;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.enchantment.Enchantment;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.ClipContext;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.BlockHitResult;
//import net.neoforged.neoforge.capabilities.Capabilities;
//import net.neoforged.neoforge.common.SimpleTier;
//import net.neoforged.neoforge.energy.IEnergyStorage;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static com.nred.nuclearcraft.registration.DataComponentRegistration.ENERGY_COMPONENT;
//import static lach_01298.qmd.datagen.QMDBlockTagProvider.MINEABLE_WITH_DRILL;
//import static net.minecraft.world.item.Items.STONE_PICKAXE;
//import static net.minecraft.world.item.Items.STONE_SHOVEL;
//
//
//public class ItemDrill extends TooltipItem implements IChargeableItem {
//    private final long capacity;
//    private final int maxTransfer;
//    private final EnergyConnection energyConnection;
//    private int energyUsage;
//    private int radius;
//
//    public ItemDrill(int radius, int capacity, Tier tier, MutableComponent tooltip) {
//        super(new Properties().stacksTo(1).attributes(DiggerItem.createAttributes(tier, 1, -2.8f)).component(DataComponents.TOOL, tier.createToolProperties(MINEABLE_WITH_DRILL)), List.of(tooltip), false, false);
//
//        this.capacity = capacity;
//        this.radius = radius;
//
//        energyUsage = QMDServerConfig.drill_energy_usage;
//        this.maxTransfer = NCMath.toInt(this.capacity);
//        this.energyConnection = EnergyConnection.BOTH;
//    }
//
//    @Override
//    public float getDestroySpeed(ItemStack stack, BlockState state) {
//        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM, null);
//        if (energy.extractEnergy(energyUsage, true) == energyUsage) {
//            super.getDestroySpeed(stack, state); // Will only use speed if block is right type
//        }
//
//        return 1f;
//    }
//
//    @Override
//    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
//        if (!level.isClientSide() && state.getDestroySpeed(level, pos) != 0.0F && stack.get(DataComponents.TOOL).damagePerBlock() > 0) {
//            if (miningEntity instanceof Player player) {
//                if (player.isCreative()) {
//                    return true;
//                }
//
//                IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM, null);
//
//                if (energy.extractEnergy(energyUsage, true) == energyUsage && radius >= 1) {
//                    for (BlockPos blockPos : getDiggedBlocks(pos, player, radius)) {
//                        BlockState blockState = level.getBlockState(blockPos);
//                        if (blockState.getDestroySpeed(level, blockPos) <= stack.get(DataComponents.TOOL).getMiningSpeed(blockState) * 2) {
//                            if (blockState.getDestroySpeed(level, blockPos) != 0 && Util.mineBlock(level, blockPos, blockState, player) && !player.isCreative()) {
//                                blockState.getBlock().playerDestroy(level, player, blockPos, blockState, level.getBlockEntity(blockPos), stack);
//                                energy.extractEnergy(energyUsage, false);
//                            }
//                        }
//                    }
//                }
//
//                energy.extractEnergy(energyUsage, false);
//            }
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
//        return true;
//    }
//
//    @Override
//    public int getEnchantmentValue(ItemStack stack) {
//        return 20;
//    }
//
//    @Override
//    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
//        return enchantment.is(Enchantments.FORTUNE) || enchantment.is(Enchantments.SILK_TOUCH);
//    }
//
//    @Override
//    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
//        if (super.isCorrectToolForDrops(stack, state)) {
//            IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM, null);
//            if (energy.extractEnergy(energyUsage, true) == energyUsage && radius >= 1) {
//                return true;
//            } else if (super.isCorrectToolForDrops(STONE_PICKAXE.getDefaultInstance(), state) || super.isCorrectToolForDrops(STONE_SHOVEL.getDefaultInstance(), state)) {
//                return true; // stone mining level
//            }
//        }
//
//        return false;
//    }
//
//    public int getRadius() {
//        return radius;
//    }
//
//    public Set<BlockPos> getDiggedBlocks(BlockPos pos, Player player, int radius) {
//        Set<BlockPos> postions = new HashSet<>();
//
//        BlockHitResult ray = ItemDrill.getPlayerPOVHitResult(player.level(), player, ClipContext.Fluid.NONE);
//        if (ray != null) {
//            Direction facing = ray.getDirection();
//            switch (facing) {
//                case UP:
//                case DOWN:
//                    for (BlockPos p : BlockPos.betweenClosed(pos.offset(-radius, 0, -radius), pos.offset(radius, 0, radius))) {
//                        postions.add(p);
//                    }
//                    break;
//                case NORTH:
//                case SOUTH:
//                    for (BlockPos p : BlockPos.betweenClosed(pos.offset(-radius, -radius, 0), pos.offset(radius, radius, 0))) {
//                        postions.add(p);
//                    }
//                    break;
//                case EAST:
//                case WEST:
//                    for (BlockPos p : BlockPos.betweenClosed(pos.offset(0, -radius, -radius), pos.offset(0, radius, radius))) {
//                        postions.add(p);
//                    }
//                    break;
//                default:
//                    break;
//            }
//            postions.remove(pos);
//        }
//
//        return postions;
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
//
//    @Override
//    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
//        if (!stack.has(ENERGY_COMPONENT)) {
//            stack.set(ENERGY_COMPONENT, 0);
//        }
//        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//
//        InfoHelper.infoLine(tooltipComponents, ChatFormatting.LIGHT_PURPLE, Component.translatable(NuclearcraftNeohaul.MODID + ".tooltip.energy_stored", UnitHelper.prefix(getEnergyStored(stack), capacity, 5, "RF")));
//    }
//
//    @Override
//    public boolean isBarVisible(ItemStack stack) {
//        return IChargeableItem.getEnergyStored(stack) > 0;
//    }
//
//    @Override
//    public int getBarWidth(ItemStack stack) {
//        return Mth.ceil(Mth.clamp((double) getEnergyStored(stack) / capacity, 0D, 1D) * 13f);
//    }
//
//    public int getEnergyStored(ItemStack stack) {
//        return stack.getOrDefault(ENERGY_COMPONENT, 0);
//    }
//}