package lach_01298.qmd.particleChamber;

import com.google.common.collect.Lists;
import it.zerono.mods.zerocore.lib.data.nbt.ISyncableEntity;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.DecayChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particleChamber.tile.*;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.recipe.types.DecayChamberRecipe;
import lach_01298.qmd.util.Equations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class DecayChamberLogic extends ParticleChamberLogic {
    public QMDRecipeInfo<DecayChamberRecipe> recipeInfo;

    protected TileParticleChamber mainChamber;

    public boolean outputSwitched = false;

    public DecayChamberLogic(ParticleChamberLogic oldLogic) {
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
        return "decay_chamber";
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

    public static final List<Pair<Class<? extends IMultiblockPart<ParticleChamber>>, String>> PART_BLACKLIST = Lists.newArrayList(
            Pair.of(TileParticleChamberFluidPort.class, QMD.MOD_ID + ".multiblock_validation.chamber.no_fluid_ports"),
            Pair.of(TileParticleChamberPort.class, QMD.MOD_ID + ".multiblock_validation.chamber.no_item_ports"));

    @Override
    public List<Pair<Class<? extends IMultiblockPart<ParticleChamber>>, String>> getPartBlacklist() {
        return PART_BLACKLIST;
    }


    @Override
    public void onChamberFormed() {
        onResetStats();
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
                    multiblock.energyStorage.changeEnergyStored(-multiblock.requiredEnergy);
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

        push();
        return super.onUpdateServer();

    }


    public void onResetStats() {
        multiblock.efficiency = 1;
        multiblock.requiredEnergy = QMDServerConfig.decay_chamber_power;
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
            multiblock.beams.get(1).getParticleStack().setAmount((int) (outputPlus.getAmount() * outputFactor * input.getAmount()));
            multiblock.beams.get(1).getParticleStack().setFocus(input.getFocus() - Equations.focusLoss(getBeamLength() / 2d, input) - Equations.focusLoss(getBeamLength() / 2d, multiblock.beams.get(1).getParticleStack()));
        }

        multiblock.beams.get(2).setParticleStack(outputNeutral);
        if (outputNeutral != null) {
            multiblock.beams.get(2).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
            multiblock.beams.get(2).getParticleStack().setAmount((int) (outputNeutral.getAmount() * outputFactor * input.getAmount()));
            multiblock.beams.get(2).getParticleStack().setFocus(input.getFocus() - Equations.focusLoss(getBeamLength() / 2d, input) - Equations.focusLoss(getBeamLength() / 2d, multiblock.beams.get(2).getParticleStack()));
        }

        multiblock.beams.get(3).setParticleStack(outputMinus);
        if (outputMinus != null) {
            multiblock.beams.get(3).getParticleStack().setMeanEnergy(Math.round((input.getMeanEnergy() + energyReleased) / (double) particlesOut));
            multiblock.beams.get(3).getParticleStack().setAmount((int) (outputMinus.getAmount() * outputFactor * input.getAmount()));
            multiblock.beams.get(3).getParticleStack().setFocus(input.getFocus() - Equations.focusLoss(getBeamLength() / 2d, input) - Equations.focusLoss(getBeamLength() / 2d, multiblock.beams.get(3).getParticleStack()));
        }
    }

    private void resetBeams() {
        multiblock.beams.get(1).setParticleStack(null);
        multiblock.beams.get(2).setParticleStack(null);
        multiblock.beams.get(3).setParticleStack(null);
    }

    protected void refreshRecipe() {
        if (multiblock.beams.get(0).getParticleStack() != null) {
            ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
            ParticleStack input = multiblock.beams.get(0).getParticleStack().copy();
            particles.add(input);

            recipeInfo = QMDRecipes.decay_chamber.getRecipeInfoFromInputs(getWorld(), new ArrayList<>(), new ArrayList<>(), particles);
        } else {
            recipeInfo = null;
        }
    }

    @Override
    public ParticleChamberUpdatePacket getMultiblockUpdatePacket() {
        return new DecayChamberUpdatePacket(multiblock.controller.getTilePos(), multiblock.isChamberOn,
                multiblock.requiredEnergy, multiblock.efficiency, multiblock.energyStorage,
                multiblock.tanks, multiblock.beams);
    }

    @Override
    public void onMultiblockUpdatePacket(ParticleChamberUpdatePacket message) {
        super.onMultiblockUpdatePacket(message);
        if (message instanceof DecayChamberUpdatePacket) {
            DecayChamberUpdatePacket packet = (DecayChamberUpdatePacket) message;
            multiblock.beams = packet.beams;

        }
    }

    @Override
    public void writeToLogicTag(CompoundTag logicTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        super.writeToLogicTag(logicTag, provider, syncReason);

        logicTag.putBoolean("outputSwitched", outputSwitched);
    }

    @Override
    public void readFromLogicTag(CompoundTag logicTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        super.readFromLogicTag(logicTag, provider, syncReason);

        outputSwitched = logicTag.getBoolean("outputSwitched");
    }
	
	/*public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		
		return new ContainerDecayChamberController(player, (TileDecayChamberController) multiblock.controller);
	}*/
}