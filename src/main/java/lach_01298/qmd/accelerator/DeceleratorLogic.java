package lach_01298.qmd.accelerator;

import com.google.common.collect.Lists;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.DeceleratorUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Equations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeceleratorLogic extends AcceleratorLogic {

    // Multiblock logic

    public DeceleratorLogic(AcceleratorLogic oldLogic) {
        super(oldLogic);
		
		/*
		beam 0 = input particle
		beam 1 = output particle
		tank 0 = input coolant
		tank 1 = output coolant
		*/

    }

    @Override
    public String getID() {
        return "decelerator";
    }

    // Accelerator methods

    @Override
    public int getBeamLength() {
        return 4 * (multiblock.getInteriorLengthX() - 2);
    }

    @Override
    public double getBeamRadius() {
        return (multiblock.getInteriorLengthX() - 2) / 2d;
    }

    public long getAcceleratorMaxEnergy(Particle particle) {
        if (particle != null && multiblock.acceleratingVoltage > 0) {
            return Equations.ringEnergyMaxEnergyFromDipole(multiblock.dipoleStrength, getBeamRadius(), particle.getCharge(), particle.getMass());
        }
        return 0;
    }

    // Multiblock Validation

    @Override
    public boolean isMachineWhole() {
        Accelerator acc = multiblock;

        if (acc.getExteriorLengthY() != getThickness()) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.wrong_height");
            return false;
        }


        if (acc.getExteriorLengthX() != acc.getExteriorLengthZ()) {

            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_square");
            return false;
        }

        if (acc.getExteriorLengthX() < QMDServerConfig.accelerator_ring_min_size) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.to_short");
            return false;
        }

        if (acc.getExteriorLengthX() > QMDServerConfig.accelerator_ring_max_size) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.to_long");
            return false;
        }


        // Beam
        for (BlockPos pos : getInteriorAxisPositions()) {
            if (!(acc.getWorld().getBlockEntity(pos) instanceof TileAcceleratorBeam)) {
                multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_beam");
                return false;
            }
        }


        //Dipoles in conners check

        for (BlockPos pos : getInteriorAxisCorners()) {
            if (!acc.isValidDipole(pos, false)) {
                multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_dipole_in_conner");
                return false;
            }
        }

        //beam ports

        int inputs = 0;
        int outputs = 0;
        for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values()) {
            if (port.getIOType() == IOType.INPUT) {
                inputs++;
            }

            if (port.getIOType() == IOType.OUTPUT) {
                outputs++;
            }

            if (port.getBlockPos().getY() != acc.getMiddleY()) {
                multiblock.setLastError(port.getBlockPos(), QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_inline_with_beam");
                return false;
            }

            port.recalculateOutwardsDirection(acc.getMinimumCoord().get(), acc.getMaximumCoord().get());
            if (port.getOutwardFacing() == null) {

                multiblock.setLastError(port.getBlockPos(), QMD.MOD_ID + ".multiblock_validation.accelerator.ring.something_is_wrong");
                return false;
            }

            if (!(acc.getWorld().getBlockEntity(port.getBlockPos().relative(port.getOutwardFacing().getOpposite())) instanceof TileAcceleratorBeam)) {
                multiblock.setLastError(port.getBlockPos().relative(port.getOutwardFacing().getOpposite()), QMD.MOD_ID + ".multiblock_validation.accelerator.ring.beam_port_must_connect");
                return false;
            }
            if (!acc.isValidDipole(port.getBlockPos().relative(port.getOutwardFacing().getOpposite(), 2), false)) {
                multiblock.setLastError(port.getBlockPos().relative(port.getOutwardFacing().getOpposite(), 2), QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_dipole");
                return false;
            }
        }
        if (inputs != 1 || outputs != 1) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_have_io");
            return false;
        }
        if (containsBlacklistedPart()) {
            return false;
        }

        return super.isMachineWhole();
    }

    public Set<BlockPos> getInteriorAxisPositions() {
        Set<BlockPos> positions = new HashSet<>();
        Accelerator acc = multiblock;

        for (BlockPos pos : BlockPos.betweenClosed(
                acc.getExtremeInteriorCoord(false, false, false).offset(1, acc.getInteriorLengthY() / 2, (getThickness() - 2) / 2),
                acc.getExtremeInteriorCoord(true, false, false).offset(-1, acc.getInteriorLengthY() / 2, (getThickness() - 2) / 2))) {
            positions.add(pos.immutable());
        }
        for (BlockPos pos : BlockPos.betweenClosed(
                acc.getExtremeInteriorCoord(false, false, true).offset(1, acc.getInteriorLengthY() / 2, -(getThickness() - 2) / 2),
                acc.getExtremeInteriorCoord(true, false, true).offset(-1, acc.getInteriorLengthY() / 2, -(getThickness() - 2) / 2))) {
            positions.add(pos.immutable());
        }
        for (BlockPos pos : BlockPos.betweenClosed(
                acc.getExtremeInteriorCoord(false, false, false).offset((getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, 1),
                acc.getExtremeInteriorCoord(false, false, true).offset((getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, -1))) {
            positions.add(pos.immutable());
        }
        for (BlockPos pos : BlockPos.betweenClosed(
                acc.getExtremeInteriorCoord(true, false, false).offset(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, 1),
                acc.getExtremeInteriorCoord(true, false, true).offset(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, -1))) {
            positions.add(pos.immutable());
        }

        return positions;
    }

    public Set<BlockPos> getInteriorAxisCorners() {
        Set<BlockPos> positions = new HashSet<BlockPos>();
        Accelerator acc = multiblock;

        positions.add(acc.getExtremeInteriorCoord(false, false, false).offset(1, acc.getInteriorLengthY() / 2, (getThickness() - 2) / 2));
        positions.add(acc.getExtremeInteriorCoord(false, false, true).offset(1, acc.getInteriorLengthY() / 2, -(getThickness() - 2) / 2));
        positions.add(acc.getExtremeInteriorCoord(true, false, false).offset(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, 1));
        positions.add(acc.getExtremeInteriorCoord(true, false, true).offset(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, -1));

        return positions;
    }

    public static final List<Pair<Class<? extends IMultiblockPart<Accelerator>>, String>> PART_BLACKLIST = Lists.newArrayList(
            Pair.of(TileAcceleratorIonSource.class, QMD.MOD_ID + ".multiblock_validation.accelerator.source"),
            Pair.of(TileAcceleratorSynchrotronPort.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_synch_ports"),
            Pair.of(TileAcceleratorIonCollector.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_ion_collectors"),
            Pair.of(TileAcceleratorPort.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_ion_ports"));

    @Override
    public List<Pair<Class<? extends IMultiblockPart<Accelerator>>, String>> getPartBlacklist() {
        return PART_BLACKLIST;
    }


    // Accelerator formation

    @Override
    public void onAcceleratorFormed() {
        if (!getWorld().isClientSide()) {
            resetBeams();

            setBeamlineFunctional(getInteriorAxisPositions());
            formComponents();

            for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values()) {
                if (port.getIOType() == IOType.INPUT) {
                    port.setIONumber(0);
                }
                if (port.getIOType() == IOType.OUTPUT) {
                    port.setIONumber(1);
                }
            }
        }

        refreshStats();
        super.onAcceleratorFormed();
    }

    // Accelerator Operation

    @Override
    public boolean onUpdateServer() {
        super.onUpdateServer();

        if (multiblock.isControllorOn) {
            produceBeam();
        } else {
            resetOutputBeam();
        }

        push();
        multiblock.sendMultiblockUpdatePacketToListeners();
        return true;
    }

    @Override
    protected void refreshBeams() {
        multiblock.beams.get(0).setParticleStack(null);
        pull();
    }

    @Override
    protected boolean shouldUseEnergy() {
        if (multiblock.beams.get(0).getParticleStack() != null) {
            return true;
        }

        return false;
    }

    // Beam port IO

    @Override
    protected void pull() {
        for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values()) {
            if (port.getIOType() == IOType.INPUT) {
                if (port.getOutwardFacing() != null) {
                    Direction face = port.getOutwardFacing();
                    BlockEntity tile = port.getLevel().getBlockEntity(port.getBlockPos().relative(face));
                    if (tile != null) {
                        IParticleStackHandler otherStorage = getWorld().getCapability(CapabilityParticleStackHandler.BLOCK, tile.getBlockPos(), face.getOpposite());
                        if (otherStorage != null) {
                            ParticleStack stack = otherStorage.extractParticle(face.getOpposite());

                            if (stack != null) {
                                multiblock.beams.get(0).setMaxEnergy(getAcceleratorMaxEnergy(stack.getParticle()));

                                if (!multiblock.beams.get(0).reciveParticle(face, stack)) {
                                    if (stack.getMeanEnergy() > multiblock.beams.get(0).getMaxEnergy()) {
                                        multiblock.errorCode = Accelerator.errorCode_InputParticleEnergyToHigh;
                                    } else if (stack.getMeanEnergy() < multiblock.beams.get(0).getMinEnergy()) {
                                        multiblock.errorCode = Accelerator.errorCode_InputParticleEnergyToLow;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Recipe handling

    private void resetOutputBeam() {
        multiblock.beams.get(1).setParticleStack(null);
    }

    private void produceBeam() {
        if (this.multiblock.beams.get(0).getParticleStack() != null) {
            ParticleStack beam = multiblock.beams.get(0).getParticleStack();
            multiblock.beams.get(1).setParticleStack(beam.copy());
            Particle particle = beam.getParticle();

            ParticleStack beamOut = multiblock.beams.get(1).getParticleStack();

            double fraction = 1;
            if (multiblock.computerControlled) {
                fraction = 1 - (multiblock.energyPercentage / 100d);
            } else {
                fraction = 1 - (getRedstoneLevel() / 15d);
            }

            long energyTarget = (long) (getAcceleratorMaxEnergy(particle) * fraction);

            if (energyTarget > beam.getMeanEnergy()) {
                beamOut.setMeanEnergy(beam.getMeanEnergy());
            } else {
                beamOut.setMeanEnergy(energyTarget);
            }


            beamOut.addFocus(Equations.focusGain(multiblock.quadrupoleStrength, beamOut));

            if (beamOut.getFocus() <= 0) {
                multiblock.errorCode = Accelerator.errorCode_NotEnoughQuadrupoles;
            }

        } else {
            resetOutputBeam();
        }
    }

    // Network
    @Override
    public DeceleratorUpdatePacket getMultiblockUpdatePacket() {
        return new DeceleratorUpdatePacket(multiblock.controller.getTilePos(),
                multiblock.isControllorOn, multiblock.cooling, multiblock.rawHeating, multiblock.currentHeating, multiblock.maxCoolantIn, multiblock.maxCoolantOut, multiblock.maxOperatingTemp,
                multiblock.requiredEnergy, multiblock.efficiency, multiblock.acceleratingVoltage,
                multiblock.RFCavityNumber, multiblock.quadrupoleNumber, multiblock.quadrupoleStrength, multiblock.dipoleNumber, multiblock.dipoleStrength, multiblock.errorCode,
                multiblock.heatBuffer, multiblock.energyStorage, multiblock.tanks, multiblock.beams);
    }

    @Override
    public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message) {
        super.onMultiblockUpdatePacket(message);
        if (message instanceof DeceleratorUpdatePacket) {
            DeceleratorUpdatePacket packet = (DeceleratorUpdatePacket) message;
        }
    }
}