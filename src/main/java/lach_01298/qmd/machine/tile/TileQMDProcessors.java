package lach_01298.qmd.machine.tile;

import com.nred.nuclearcraft.block_entity.processor.ProcessorEntityImpl.BasicEnergyProcessorEntity;
import com.nred.nuclearcraft.block_entity.processor.ProcessorEntityImpl.BasicUpgradableEnergyProcessorEntity;
import com.nred.nuclearcraft.payload.processor.EnergyProcessorUpdatePacket;
import com.nred.nuclearcraft.recipe.ProcessorRecipe;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.datamap.IrradiatorFuel;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.machine.network.IrradiatorUpdatePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static lach_01298.qmd.datamap.QMDDatamaps.IRRADIATOR_FUELS;
import static lach_01298.qmd.tile.QMDTiles.IRRADIATOR_ENTITY_TYPE;
import static lach_01298.qmd.tile.QMDTiles.ORE_LEACHER_ENTITY_TYPE;

public class TileQMDProcessors {
    public static class OreLeacherEntity extends BasicUpgradableEnergyProcessorEntity<ProcessorRecipe, OreLeacherEntity> {
        public OreLeacherEntity(BlockPos pos, BlockState blockState) {
            super(ORE_LEACHER_ENTITY_TYPE.get(), pos, blockState, "ore_leacher");
        }
    }

    // TODO check this
    public static class IrradiatorEntity extends BasicEnergyProcessorEntity<ProcessorRecipe, IrradiatorEntity> {
        public IrradiatorEntity(BlockPos pos, BlockState blockState) {
            super(IRRADIATOR_ENTITY_TYPE.get(), pos, blockState, "irradiator");
        }

        int FUEL_SLOT = 1;

        @Override
        public boolean hasSufficientEnergy() {
            if (getItem(FUEL_SLOT).getItem() instanceof IItemParticleAmount itemParticle) {
                int amount = itemParticle.getAmountStored(getItem(FUEL_SLOT));
                double amountNeeded = Math.max(1D, Math.ceil(getBaseProcessTime() / getSpeedMultiplier())) * QMDServerConfig.irradiator_fuel_usage;

                return amount >= amountNeeded;
            }
            return false;
        }

        @Override
        public List<ItemStack> getItemInputs(boolean consumed) {
            return consumed ? getConsumedStacks() : getInventoryStacks().subList(0, FUEL_SLOT);
        }

        @Override
        public void process() {
            super.process();
            if (getItem(FUEL_SLOT).getItem() instanceof IItemParticleAmount itemParticle) {
                setItem(FUEL_SLOT, itemParticle.use(getItem(FUEL_SLOT), Mth.ceil(BuiltInRegistries.ITEM.wrapAsHolder(getItem(FUEL_SLOT).getItem()).getData(IRRADIATOR_FUELS).speedMultiplier() * QMDServerConfig.irradiator_fuel_usage)));
            }
        }

        @Override
        public EnergyProcessorUpdatePacket getTileUpdatePacket() {
            if (getItem(FUEL_SLOT).getItem() instanceof IItemParticleAmount itemParticle) {
                return new IrradiatorUpdatePacket(worldPosition, isProcessing, time, baseProcessTime, getTanks(), itemParticle.getAmountStored(getItem(FUEL_SLOT)));
            }
            return super.getTileUpdatePacket();
        }

        @Override
        public void onTileUpdatePacket(EnergyProcessorUpdatePacket message) {
            if (message instanceof IrradiatorUpdatePacket msg && level.isClientSide()) {
                if (getItem(FUEL_SLOT).getItem() instanceof IItemParticleAmount itemParticle) {
                    itemParticle.setAmountStored(getItem(FUEL_SLOT), msg.fuelAmount);
                }
            }
            super.onTileUpdatePacket(message);
        }

        @Override
        public double getSpeedMultiplier() {
            IrradiatorFuel irradiatorFuel = BuiltInRegistries.ITEM.wrapAsHolder(getItem(FUEL_SLOT).getItem()).getData(IRRADIATOR_FUELS);
            return irradiatorFuel == null ? 1 : irradiatorFuel.speedMultiplier();
        }

        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            if (slot == FUEL_SLOT && stack.getCount() == 1 && stack.getItem() instanceof IItemParticleAmount && BuiltInRegistries.ITEM.wrapAsHolder(stack.getItem()).getData(IRRADIATOR_FUELS) != null) {
                return true;
            }
            return super.canPlaceItem(slot, stack);
        }
    }
}