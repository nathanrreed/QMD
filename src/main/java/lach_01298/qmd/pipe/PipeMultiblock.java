package lach_01298.qmd.pipe;

import com.nred.nuclearcraft.multiblock.Multiblock;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.world.level.Level;

public abstract class PipeMultiblock<MULTIBLOCK extends Multiblock<MULTIBLOCK>> extends Multiblock<MULTIBLOCK> {
    protected PipeMultiblock(Level level) {
        super(level);
    }

    @Override
    protected int getMinimumNumberOfPartsForAssembledMachine() {
        return 1;
    }

    @Override
    protected int getMaximumXSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected int getMaximumZSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected int getMaximumYSize() {
        return Integer.MAX_VALUE;
    }

    public int getLengthX() {
        return Math.abs(getMaximumCoord().get().getX() - getMinimumCoord().get().getX()) + 1;
    }

    public int getLengthY() {
        return Math.abs(getMaximumCoord().get().getY() - getMinimumCoord().get().getY()) + 1;
    }

    public int getLengthZ() {
        return Math.abs(getMaximumCoord().get().getZ() - getMinimumCoord().get().getZ()) + 1;
    }

    public int length() {

        if (this.getLengthX() > this.getLengthZ() && this.getLengthX() > this.getLengthY()) {
            return this.getLengthX();
        } else if (this.getLengthZ() > this.getLengthY()) {
            return this.getLengthZ();
        } else {
            return this.getLengthY();
        }
    }

    @Override
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        return true;
    }
}