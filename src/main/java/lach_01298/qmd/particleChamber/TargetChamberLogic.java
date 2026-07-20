package lach_01298.qmd.particleChamber;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import it.zerono.mods.zerocore.lib.data.nbt.ISyncableEntity;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.InventoryHelper;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.TargetChamberUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particleChamber.tile.*;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.recipe.types.TargetChamberRecipe;
import lach_01298.qmd.util.Equations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class TargetChamberLogic extends ParticleChamberLogic {
    public QMDRecipeInfo<TargetChamberRecipe> recipeInfo;

    public QMDRecipeInfo<TargetChamberRecipe> rememberedRecipeInfo;

    protected TileParticleChamber mainChamber;


    public long particleWorkDone = 0;
    public long recipeParticleWork = 100;
    public boolean outputSwitched = false;

    public TargetChamberLogic(ParticleChamberLogic oldLogic) {
        super(oldLogic);
		
		/*
		beam 0 = input particle 1
		beam 1 = output particle 1
		beam 2 = output particle 2
		beam 3 = output particle 3

		*/
    }

    @Override
    public String getID() {
        return "target_chamber";
    }

    @Override
    public boolean isMachineWhole() {

        //sizing
        if (multiblock.getExteriorLengthX() != multiblock.getExteriorLengthZ()) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_square");
            return false;
        }
        if (multiblock.getExteriorLengthX() % 2 != 1) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_be_odd");
            return false;
        }


        BlockPos middle = multiblock.getExtremeCoord(false, false, false).offset(multiblock.getExteriorLengthX() / 2, multiblock.getExteriorLengthY() / 2, multiblock.getExteriorLengthZ() / 2);

        //target
        if (!(multiblock.getWorld().getBlockEntity(middle) instanceof TileParticleChamber)) {
            multiblock.setLastError(middle, QMD.MOD_ID + ".multiblock_validation.chamber.must_have_target");
            return false;
        }

        TileParticleChamber target = (TileParticleChamber) multiblock.getWorld().getBlockEntity(middle);

        // target beams
        int ports = 0;
        for (Direction face : Direction.Plane.HORIZONTAL) {
            if (multiblock.getWorld().getBlockEntity(middle.relative(face, multiblock.getExteriorLengthX() / 2)) instanceof TileParticleChamberBeamPort) {
                ports++;
                for (int i = 1; i <= multiblock.getExteriorLengthX() / 2 - 1; i++) {
                    if (!(multiblock.getWorld().getBlockEntity(middle.relative(face, i)) instanceof TileParticleChamberBeam)) {
                        multiblock.setLastError(middle.relative(face, i), QMD.MOD_ID + ".multiblock_validation.chamber.must_be_beam");
                        return false;
                    }
                }
            }
        }

        if (ports != getPartMap(TileParticleChamberBeamPort.class).size()) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.beam_port_wrong_spot");
            return false;
        }

        // has input
        int inputs = 0;
        for (TileParticleChamberBeamPort tile : getPartMap(TileParticleChamberBeamPort.class).values()) {
            if (tile.getIOType() == IOType.INPUT) {
                inputs++;
            }
        }
        if (inputs != 1) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.chamber.must_have_input_beam");
            return false;
        }


        // Energy Ports
        if (getPartMap(TileParticleChamberEnergyPort.class).isEmpty()) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.need_energy_ports");
            return false;
        }

        if (containsBlacklistedPart()) {
            return false;
        }

        return true;
    }

    @Override
    public int getMinimumInteriorLength() {
        return 1;
    }

    public static final List<Pair<Class<? extends IMultiblockPart<ParticleChamber>>, String>> PART_BLACKLIST = Lists.newArrayList();

    @Override
    public List<Pair<Class<? extends IMultiblockPart<ParticleChamber>>, String>> getPartBlacklist() {
        return PART_BLACKLIST;
    }

    @Override
    public void onChamberFormed() {
        onResetStats();

        multiblock.tanks.get(0).setCapacity(QMDServerConfig.particle_chamber_input_tank_capacity);
        multiblock.tanks.get(1).setCapacity(QMDServerConfig.particle_chamber_output_tank_capacity * getCapacityMultiplier());

        if (!getWorld().isClientSide()) {
            for (TileParticleChamber target : getPartMap(TileParticleChamber.class).values()) {
                this.mainChamber = target;
            }

            for (TileParticleChamberDetector detector : getPartMap(TileParticleChamberDetector.class).values()) {
                multiblock.requiredEnergy += detector.detectorType.getBasePower();
                if (detector.isValidPostion(mainChamber.getBlockPos())) {
                    multiblock.efficiency += detector.detectorType.getEfficiency();
                }
            }

            BlockPos input = null;

            for (TileParticleChamberBeamPort tile : getPartMap(TileParticleChamberBeamPort.class).values()) {
                if (tile.getIOType() == IOType.INPUT) {
                    tile.setIONumber(0);
                    input = tile.getBlockPos();
                }
            }

            int distance = multiblock.getExteriorLengthX() / 2;
            Direction facing = null;
            if (mainChamber.getBlockPos().getX() == input.getX()) {
                if (input.getZ() > mainChamber.getBlockPos().getZ()) {
                    facing = Direction.SOUTH;
                } else {
                    facing = Direction.NORTH;
                }

            } else if (mainChamber.getBlockPos().getZ() == input.getZ()) {
                if (input.getX() > mainChamber.getBlockPos().getX()) {
                    facing = Direction.EAST;
                } else {
                    facing = Direction.WEST;
                }
            }

            if (getWorld().getBlockEntity(mainChamber.getBlockPos().relative(facing.getClockWise(), distance)) instanceof TileParticleChamberBeamPort) {
                TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld().getBlockEntity(mainChamber.getBlockPos().relative(facing.getClockWise(), distance));
                if (outputSwitched) {
                    port.setIONumber(3);
                } else {
                    port.setIONumber(1);
                }

            }
            if (getWorld().getBlockEntity(mainChamber.getBlockPos().relative(facing.getClockWise().getClockWise(), distance)) instanceof TileParticleChamberBeamPort) {
                TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld().getBlockEntity(mainChamber.getBlockPos().relative(facing.getClockWise().getClockWise(), distance));
                port.setIONumber(2);

            }
            if (getWorld().getBlockEntity(mainChamber.getBlockPos().relative(facing.getClockWise().getClockWise().getClockWise(), distance)) instanceof TileParticleChamberBeamPort) {
                TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld().getBlockEntity(mainChamber.getBlockPos().relative(facing.getClockWise().getClockWise().getClockWise(), distance));
                if (outputSwitched) {
                    port.setIONumber(1);
                } else {
                    port.setIONumber(3);
                }
            }
        }

        super.onChamberFormed();
    }

    public void onMachineDisassembled() {
        mainChamber = null;
        for (TileParticleChamberBeamPort tile : getPartMap(TileParticleChamberBeamPort.class).values()) {
            tile.setIONumber(0);
        }
        super.onMachineDisassembled();
    }

    @Override
    public boolean onUpdateServer() {
        multiblock.beams.get(0).setParticleStack(null);
        pull();

        if (isChamberOn()) {
            if (multiblock.energyStorage.extractEnergy(multiblock.requiredEnergy, true) == multiblock.requiredEnergy) {
                refreshRecipe();

                if (recipeInfo != null) {
                    if (rememberedRecipeInfo != null) {
                        if (rememberedRecipeInfo.recipe != recipeInfo.recipe) {
                            particleWorkDone = 0;
                        }
                    }
                    rememberedRecipeInfo = recipeInfo;

                    if (canProduceProduct()) {
                        multiblock.energyStorage.changeEnergyStored(-multiblock.requiredEnergy);
                        particleWorkDone += multiblock.beams.get(0).getParticleStack().getAmount();

                        produceProduct();
                        produceBeams();
                    } else {
                        resetBeams();
                    }
                } else {
                    resetBeams();
                }

            } else {
                resetBeams();
            }
        } else {
            resetBeams();
        }
        push();

        return super.onUpdateServer();
    }


    @Override
    public boolean toggleSetting(BlockPos pos, int ioNumber) {
        if (ioNumber == 0 || ioNumber == 2) {
            return false;
        }

        if (getWorld().getBlockEntity(pos) instanceof TileParticleChamberBeamPort) {
            if (outputSwitched) {
                outputSwitched = false;
            } else {
                outputSwitched = true;
            }

            TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) getWorld().getBlockEntity(pos);
            if (port.getIONumber() == 1) {
                port.setIONumber(3);
            } else if (port.getIONumber() == 3) {
                port.setIONumber(1);
            }

            Direction facing = null;
            if (mainChamber.getBlockPos().getX() == port.getBlockPos().getX()) {
                if (port.getBlockPos().getZ() > mainChamber.getBlockPos().getZ()) {
                    facing = Direction.NORTH;
                } else {
                    facing = Direction.SOUTH;
                }

            } else if (mainChamber.getBlockPos().getZ() == port.getBlockPos().getZ()) {
                if (port.getBlockPos().getX() > mainChamber.getBlockPos().getX()) {
                    facing = Direction.WEST;
                } else {
                    facing = Direction.EAST;
                }
            }


            if (getWorld().getBlockEntity(port.getBlockPos().relative(facing, multiblock.getExteriorLengthX() - 1)) instanceof TileParticleChamberBeamPort) {
                TileParticleChamberBeamPort port2 = (TileParticleChamberBeamPort) getWorld().getBlockEntity(port.getBlockPos().relative(facing, multiblock.getExteriorLengthX() - 1));
                if (port2.getIONumber() == 1) {
                    port2.setIONumber(3);
                } else if (port2.getIONumber() == 3) {
                    port2.setIONumber(1);
                }
            }

            return true;
        }
        return false;
    }

    private boolean canProduceProduct() {
        TileTargetChamberController inv = (TileTargetChamberController) multiblock.controller;
        ItemStack productItem = !recipeInfo.recipe.getItemProducts().isEmpty() ? recipeInfo.recipe.getItemProducts().get(0).getStack() : null;
        FluidStack productFluid = !recipeInfo.recipe.getFluidProducts().isEmpty() ? recipeInfo.recipe.getFluidProducts().get(0).getStack() : null;

        // some strange safety measure
        if (inv.getInventoryStacks().get(1).getCount() <= 0) {
            inv.getInventoryStacks().set(1, ItemStack.EMPTY);
        }

        if (productItem != null) {
            if (!ItemStack.isSameItemSameComponents(inv.getInventoryStacks().get(1), productItem) && inv.getInventoryStacks().get(1) != ItemStack.EMPTY) {
                return false;
            }

            if (inv.getInventoryStacks().get(1).getCount() + productItem.getCount() > productItem.getMaxStackSize()) {
                return false;
            }
        }

        if (productFluid != null) {
            if (multiblock.tanks.get(1).fill(productFluid, FluidAction.SIMULATE) != productFluid.getAmount()) {
                return false;
            }
        }

        return true;
    }

    private void produceProduct() {
        recipeParticleWork = (long) Math.max(recipeInfo.recipe.getCrossSection() * recipeInfo.recipe.getParticleIngredients().get(0).getAmount(), recipeInfo.recipe.getParticleIngredients().get(0).getAmount() / multiblock.efficiency);
        particleWorkDone = Math.min(particleWorkDone, recipeParticleWork * 64);

        while (particleWorkDone >= recipeParticleWork && canProduceProduct()) {
            TileTargetChamberController inv = (TileTargetChamberController) multiblock.controller;
            ItemStack productItem = !recipeInfo.recipe.getItemProducts().isEmpty() ? recipeInfo.recipe.getItemProducts().get(0).getStack() : null;
            if (productItem == null) {
                productItem = ItemStack.EMPTY;
            } else {
                productItem.setCount(recipeInfo.recipe.getItemProducts().get(0).count());
            }

            InventoryHelper.addItem(1, productItem, inv.getInventoryStacks(), inv);
            InventoryHelper.removeItem(0, recipeInfo.recipe.getItemIngredients().get(0).count(), inv.getInventoryStacks(), inv);

            FluidStack productFluid = !recipeInfo.recipe.getFluidProducts().isEmpty() ? recipeInfo.recipe.getFluidProducts().get(0).getStack() : null;
            if (productFluid != null) {
                productFluid.setAmount(recipeInfo.recipe.getFluidProducts().get(0).amount());
                multiblock.tanks.get(1).fill(productFluid, FluidAction.EXECUTE);
            }

            FluidStack ingredientFluid = !recipeInfo.recipe.getFluidIngredients().isEmpty() ? recipeInfo.recipe.getFluidIngredients().get(0).getStack() : null;
            if (ingredientFluid != null) {
                multiblock.tanks.get(0).drain(ingredientFluid, FluidAction.EXECUTE);
            }

            particleWorkDone = Math.max(0, particleWorkDone - recipeParticleWork);
        }
    }

    public void onResetStats() {
        multiblock.efficiency = 1;
        multiblock.requiredEnergy = QMDServerConfig.target_chamber_power;
    }

    private void produceBeams() {
        ParticleStack input = multiblock.beams.get(0).getParticleStack();
        ParticleStack outputPlus = recipeInfo.recipe.getParticleProduct(0);
        ParticleStack outputNeutral = recipeInfo.recipe.getParticleProduct(1);
        ParticleStack outputMinus = recipeInfo.recipe.getParticleProduct(2);

        long energyReleased = recipeInfo.recipe.getEnergyReleased();
        double crossSection = recipeInfo.recipe.getCrossSection();
        double outputFactor = crossSection * multiblock.efficiency;
        if (outputFactor >= 1) {
            outputFactor = 1;
        }

        int particlesOut = 0;
        if (outputPlus != null) {
            particlesOut += outputPlus.getAmount();
        }
        if (outputNeutral != null) {
            particlesOut += outputNeutral.getAmount();
        }
        if (outputMinus != null) {
            particlesOut += outputMinus.getAmount();
        }

        multiblock.beams.get(1).setParticleStack(outputPlus);
        if (outputPlus != null) {
            multiblock.beams.get(1).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
            multiblock.beams.get(1).getParticleStack().setAmount((int) Math.round(outputPlus.getAmount() * outputFactor * input.getAmount()));
            multiblock.beams.get(1).getParticleStack().setFocus(input.getFocus() - Equations.focusLoss(getBeamLength() / 2d, input) - Equations.focusLoss(getBeamLength() / 2d, multiblock.beams.get(1).getParticleStack()));
        }

        multiblock.beams.get(2).setParticleStack(outputNeutral);
        if (outputNeutral != null) {
            multiblock.beams.get(2).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
            multiblock.beams.get(2).getParticleStack().setAmount((int) Math.round(outputNeutral.getAmount() * outputFactor * input.getAmount()));
            multiblock.beams.get(2).getParticleStack().setFocus(input.getFocus() - Equations.focusLoss(getBeamLength() / 2d, input) - Equations.focusLoss(getBeamLength() / 2d, multiblock.beams.get(2).getParticleStack()));
        }

        multiblock.beams.get(3).setParticleStack(outputMinus);
        if (outputMinus != null) {
            multiblock.beams.get(3).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
            multiblock.beams.get(3).getParticleStack().setAmount((int) Math.round(outputMinus.getAmount() * outputFactor * input.getAmount()));
            multiblock.beams.get(3).getParticleStack().setFocus(input.getFocus() - Equations.focusLoss(getBeamLength() / 2d, input) - Equations.focusLoss(getBeamLength() / 2d, multiblock.beams.get(3).getParticleStack()));
        }
    }

    private void resetBeams() {
        multiblock.beams.get(1).setParticleStack(null);
        multiblock.beams.get(2).setParticleStack(null);
        multiblock.beams.get(3).setParticleStack(null);
    }

    protected void refreshRecipe() {
        TileTargetChamberController cont = (TileTargetChamberController) multiblock.controller;
        ArrayList<ItemStack> items = new ArrayList<>();
        ItemStack item = cont.getInventoryStacks().get(0).copy();
        items.add(item);
        ArrayList<Tank> tanks = new ArrayList<>();
        tanks.add(multiblock.tanks.get(0));
        ArrayList<ParticleStack> particles = new ArrayList<>();
        particles.add(multiblock.beams.get(0).getParticleStack());

        recipeInfo = QMDRecipes.target_chamber.getRecipeInfoFromInputs(getWorld(), items, tanks, particles);
    }

    @Override
    public ParticleChamberUpdatePacket getMultiblockUpdatePacket() {
        return new TargetChamberUpdatePacket(multiblock.controller.getTilePos(), multiblock.isChamberOn,
                multiblock.requiredEnergy, multiblock.efficiency, multiblock.energyStorage,
                particleWorkDone, recipeParticleWork, multiblock.tanks, multiblock.beams);
    }

    @Override
    public void onMultiblockUpdatePacket(ParticleChamberUpdatePacket message) {
        super.onMultiblockUpdatePacket(message);
        if (message instanceof TargetChamberUpdatePacket) {
            TargetChamberUpdatePacket packet = (TargetChamberUpdatePacket) message;
            multiblock.beams = packet.beams;
            for (int i = 0; i < multiblock.tanks.size(); i++) multiblock.tanks.get(i).readInfo(message.tanksInfo.get(i));
            this.particleWorkDone = packet.particleCount;
            this.recipeParticleWork = packet.recipeParticleCount;
        }
    }

    @Override
    public void writeToLogicTag(CompoundTag logicTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        super.writeToLogicTag(logicTag, provider, syncReason);

        logicTag.putLong("particleCount", particleWorkDone);
        logicTag.putLong("recipeParticleCount", recipeParticleWork);
        logicTag.putBoolean("outputSwitched", outputSwitched);
    }

    @Override
    public void readFromLogicTag(CompoundTag logicTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        super.readFromLogicTag(logicTag, provider, syncReason);

        particleWorkDone = logicTag.getLong("particleCount");
        recipeParticleWork = logicTag.getLong("recipeParticleCount");
        outputSwitched = logicTag.getBoolean("outputSwitched");
    }
}