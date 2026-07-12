package lach_01298.qmd.multiblock;

import com.nred.nuclearcraft.multiblock.Multiblock;
import com.nred.nuclearcraft.multiblock.internal.MultiblockValidationError;
import com.nred.nuclearcraft.util.NCMath;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Collections;

public abstract class CuboidalOrToroidalMultiblock<MULTIBLOCK extends CuboidalOrToroidalMultiblock<MULTIBLOCK>> extends Multiblock<MULTIBLOCK> {

    private int thickness;

    protected CuboidalOrToroidalMultiblock(Level level, int thickness) {
        super(level);
        this.thickness = thickness;
    }


    /**
     * @return True if the machine is "whole" and should be assembled. False
     * otherwise.
     */
    @Override
    protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
        if (getPartsCount() < getMinimumNumberOfPartsForAssembledMachine()) {
            validatorCallback.setLastError(MultiblockValidationError.VALIDATION_ERROR_TOO_FEW_PARTS);
            return false;
        }

        BlockPos maximumCoord = getMaximumCoord().get();
        BlockPos minimumCoord = getMinimumCoord().get();
        BlockPos maxInCoord = getMaxInCoord();
        BlockPos minInCoord = getMinInCoord();

        int minX = minimumCoord.getX();
        int minY = minimumCoord.getY();
        int minZ = minimumCoord.getZ();
        int maxX = maximumCoord.getX();
        int maxY = maximumCoord.getY();
        int maxZ = maximumCoord.getZ();

        // Quickly check for exceeded dimensions
        int deltaX = maxX - minX + 1;
        int deltaY = maxY - minY + 1;
        int deltaZ = maxZ - minZ + 1;

        int maxXSize = getMaximumXSize();
        int maxYSize = getMaximumYSize();
        int maxZSize = getMaximumZSize();
        int minXSize = getMinimumXSize();
        int minYSize = getMinimumYSize();
        int minZSize = getMinimumZSize();

        if (maxXSize > 0 && deltaX > maxXSize) {
            setLastError("zerocore:api.multiblock.validation.machine_too_large", Collections.emptyList(), maxXSize, "X");
            return false;
        }
        if (maxYSize > 0 && deltaY > maxYSize) {
            setLastError("zerocore:api.multiblock.validation.machine_too_large", Collections.emptyList(), maxYSize, "Y");
            return false;
        }
        if (maxZSize > 0 && deltaZ > maxZSize) {
            setLastError("zerocore:api.multiblock.validation.machine_too_large", Collections.emptyList(), maxZSize, "Z");
            return false;
        }
        if (deltaX < minXSize) {
            setLastError("zerocore:api.multiblock.validation.machine_too_small", Collections.emptyList(), minXSize, "X");
            return false;
        }
        if (deltaY < minYSize) {
            setLastError("zerocore:api.multiblock.validation.machine_too_small", Collections.emptyList(), minYSize, "Y");
            return false;
        }
        if (deltaZ < minZSize) {
            setLastError("zerocore:api.multiblock.validation.machine_too_small", Collections.emptyList(), minZSize, "Z");
            return false;
        }

        // Now we run a simple check on each block within that volume.
        // Any block deviating = NO DEAL SIR
        BlockEntity blockEntity;
        int extremes;
        boolean isPartValid;
        TileCuboidalOrToroidalMultiblockPart<?> part;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    // Okay, figure out what sort of block this should be.
                    BlockPos pos = new BlockPos(x, y, z);
                    blockEntity = getWorld().getBlockEntity(pos);
                    if (blockEntity instanceof TileCuboidalOrToroidalMultiblockPart<?> multiblockPart) {
                        part = multiblockPart;
                        // Ensure this part should actually be allowed within a cube of this
                        // multiblock's type
                        if (!part.testOnController(controller -> this.getClass().equals(controller.getClass()))) {
                            validatorCallback.setLastError(pos, "zerocore:api.multiblock.validation.invalid_part");
                            return false;
                        }
                    } else {
                        // This is permitted so that we can incorporate certain non-multiblock parts
                        // inside interiors
                        part = null;
                    }

                    // don't check the hole
                    if (isToroidal()) {
                        if (z > minInCoord.getZ() && z < maxInCoord.getZ() && x > minInCoord.getX()
                                && x < maxInCoord.getX()) {
                            if (z > minInCoord.getZ() + 1 && z < maxInCoord.getZ() - 1 && x > minInCoord.getX() + 1
                                    && x < maxInCoord.getX() - 1) {
                                continue;
                            }

                            if (part != null) { // check inner edge isn't a multiblock part
                                setLastError(new BlockPos(x, y, z), "zerocore.api.multiblock.validation.cannot_be_multiblock_part");
                                return false;
                            } else {
                                continue;
                            }
                        }
                    }

                    // Validate block type against both part-level and material-level validators.
                    extremes = 0;

                    if (x == minX || (x == maxInCoord.getX() && z > minInCoord.getZ() && z < maxInCoord.getZ()))
                        extremes++;
                    if (y == minY)
                        extremes++;
                    if (z == minZ || (z == maxInCoord.getZ() && x > minInCoord.getX() && x < maxInCoord.getX()))
                        extremes++;
                    if (x == maxX || (x == minInCoord.getX() && z > minInCoord.getZ() && z < maxInCoord.getZ()))
                        extremes++;
                    if (y == maxY)
                        extremes++;
                    if (z == maxZ || (z == minInCoord.getZ() && x > minInCoord.getX() && x < maxInCoord.getX()))
                        extremes++;

                    if (extremes >= 2) {
                        isPartValid = part != null ? part.isGoodForFrame() : isBlockGoodForFrame(getWorld(), x, y, z, validatorCallback);

                        if (!isPartValid) {
                            if (validatorCallback.isLastErrorEmpty()) {
                                setLastError(pos, "zerocore:api.multiblock.validation.invalid_part_for_frame");
                            }
                            return false;
                        }
                    } else if (extremes == 1) {
                        if (y == maxY) {
                            isPartValid = part != null ? part.isGoodForTop() : isBlockGoodForTop(getWorld(), x, y, z, validatorCallback);

                            if (!isPartValid) {
                                if (validatorCallback.isLastErrorEmpty()) {
                                    setLastError(pos, "zerocore:api.multiblock.validation.invalid_part_for_top");
                                }
                                return false;
                            }
                        } else if (y == minY) {
                            isPartValid = part != null ? part.isGoodForBottom() : isBlockGoodForBottom(getWorld(), x, y, z, validatorCallback);

                            if (!isPartValid) {
                                if (validatorCallback.isLastErrorEmpty()) {
                                    setLastError(pos, "zerocore:api.multiblock.validation.invalid_part_for_bottom");
                                }
                                return false;
                            }
                        } else {
                            // Side
                            isPartValid = part != null ? part.isGoodForSides() : isBlockGoodForSides(getWorld(), x, y, z, validatorCallback);

                            if (!isPartValid) {
                                if (validatorCallback.isLastErrorEmpty()) {
                                    setLastError(pos, "zerocore:api.multiblock.validation.invalid_part_for_sides");
                                }
                                return false;
                            }
                        }
                    } else {
                        isPartValid = part != null ? part.isGoodForInterior() : isBlockGoodForInterior(getWorld(), x, y, z, validatorCallback);

                        if (!isPartValid) {
                            if (validatorCallback.isLastErrorEmpty()) {
                                setLastError(pos, "zerocore:api.multiblock.validation.invalid_part_for_interior");
                            }
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private BlockPos getMinInCoord() {
        return new BlockPos(getMinimumCoord().get().getX() + thickness - 1, getMinimumCoord().get().getY(), getMinimumCoord().get().getZ() + thickness - 1);
    }

    private BlockPos getMaxInCoord() {
        return new BlockPos(getMaximumCoord().get().getX() - thickness + 1, getMaximumCoord().get().getY(), getMaximumCoord().get().getZ() - thickness + 1);
    }

    protected BlockPos getMinimumInteriorCoord() {
        return new BlockPos(getMinInteriorX(), getMinInteriorY(), getMinInteriorZ());
    }

    protected BlockPos getMaximumInteriorCoord() {
        return new BlockPos(getMaxInteriorX(), getMaxInteriorY(), getMaxInteriorZ());
    }

    public int getMinInteriorX() {
        return getMinimumCoord().get().getX() + 1;
    }

    public int getMinInteriorY() {
        return getMinimumCoord().get().getY() + 1;
    }

    public int getMinInteriorZ() {
        return getMinimumCoord().get().getZ() + 1;
    }

    public int getMaxInteriorX() {
        return getMaximumCoord().get().getX() - 1;
    }

    public int getMaxInteriorY() {
        return getMaximumCoord().get().getY() - 1;
    }

    public int getMaxInteriorZ() {
        return getMaximumCoord().get().getZ() - 1;
    }

    public BlockPos getExtremeInteriorCoord(boolean maxX, boolean maxY, boolean maxZ) {
        return new BlockPos(maxX ? getMaxInteriorX() : getMinInteriorX(), maxY ? getMaxInteriorY() : getMinInteriorY(),
                maxZ ? getMaxInteriorZ() : getMinInteriorZ());
    }

    public int getExteriorLengthX() {
        return Math.abs(getMaximumCoord().get().getX() - getMinimumCoord().get().getX()) + 1;
    }

    public int getExteriorLengthY() {
        return Math.abs(getMaximumCoord().get().getY() - getMinimumCoord().get().getY()) + 1;
    }

    public int getExteriorLengthZ() {
        return Math.abs(getMaximumCoord().get().getZ() - getMinimumCoord().get().getZ()) + 1;
    }

    public int getInteriorLengthX() {
        return getExteriorLengthX() - 2;
    }

    public int getInteriorLengthY() {
        return getExteriorLengthY() - 2;
    }

    public int getInteriorLengthZ() {
        return getExteriorLengthZ() - 2;
    }

    public int getExteriorVolume() {
        if (isToroidal()) {
            return (getExteriorLengthX() * getExteriorLengthZ()
                    - (getExteriorLengthX() - 2 * thickness) * (getExteriorLengthZ() - 2 * thickness))
                    * getExteriorLengthY();
        }
        return getExteriorLengthX() * getExteriorLengthY() * getExteriorLengthZ();
    }

    private boolean isToroidal() {
        if (thickness < 1) {
            return false;
        }
        return getMaxInCoord().getX() > getMinInCoord().getX() && getMaxInCoord().getZ() > getMinInCoord().getZ();
    }

    public int getInteriorVolume() {
        if (isToroidal()) {
            return ((getExteriorLengthX() - 1) * (getExteriorLengthZ() - 1)
                    - (getExteriorLengthX() - 2 * (thickness + 1)) * (getExteriorLengthZ() - 2 * (thickness + 1)))
                    * getInteriorLengthY();
        }

        return getInteriorLengthX() * getInteriorLengthY() * getInteriorLengthZ();
    }

    public int getExteriorSurfaceArea() {
        if (isToroidal()) {
            return 4 * (thickness + getExteriorLengthY())
                    * (getExteriorLengthX() + getExteriorLengthZ() - 2 * thickness);
        }
        return 2 * (getExteriorLengthX() * getExteriorLengthY() + getExteriorLengthY() * getExteriorLengthZ()
                + getExteriorLengthZ() * getExteriorLengthX());
    }

    public int getInteriorSurfaceArea() {
        if (isToroidal()) {
            return 4 * (thickness - 2 + getInteriorLengthY())
                    * (getInteriorLengthX() + getInteriorLengthZ() - 2 * (thickness - 2));
        }
        return 2 * (getInteriorLengthX() * getInteriorLengthY() + getInteriorLengthY() * getInteriorLengthZ()
                + getInteriorLengthZ() * getInteriorLengthX());
    }

    public int getInteriorLength(Direction dir) {
        return switch (dir) {
            case DOWN, UP -> getInteriorLengthY();
            case NORTH, SOUTH -> getInteriorLengthZ();
            case WEST, EAST -> getInteriorLengthX();
        };
    }

    public abstract int getMinimumInteriorLength();

    public abstract int getMaximumInteriorLength();

    @Override
    protected int getMinimumNumberOfPartsForAssembledMachine() {
        return NCMath.hollowCube(getMinimumInteriorLength() + 2);
    }

    @Override
    protected int getMinimumXSize() {
        return getMinimumInteriorLength() + 2;
    }

    @Override
    protected int getMinimumYSize() {
        return getMinimumInteriorLength() + 2;
    }

    @Override
    protected int getMinimumZSize() {
        return getMinimumInteriorLength() + 2;
    }

    @Override
    protected int getMaximumXSize() {
        return getMaximumInteriorLength() + 2;
    }

    @Override
    protected int getMaximumYSize() {
        return getMaximumInteriorLength() + 2;
    }

    @Override
    protected int getMaximumZSize() {
        return getMaximumInteriorLength() + 2;
    }

    public Iterable<BlockPos> getWallMinX() {
        return BlockPos.betweenClosed(getExtremeCoord(false, false, false), getExtremeCoord(false, true, true));
    }

    public Iterable<BlockPos> getWallMaxX() {
        return BlockPos.betweenClosed(getExtremeCoord(true, false, false), getExtremeCoord(true, true, true));
    }

    public Iterable<BlockPos> getWallMinY() {
        return BlockPos.betweenClosed(getExtremeCoord(false, false, false), getExtremeCoord(true, false, true));
    }

    public Iterable<BlockPos> getWallMaxY() {
        return BlockPos.betweenClosed(getExtremeCoord(false, true, false), getExtremeCoord(true, true, true));
    }

    public Iterable<BlockPos> getWallMinZ() {
        return BlockPos.betweenClosed(getExtremeCoord(false, false, false), getExtremeCoord(true, true, false));
    }

    public Iterable<BlockPos> getWallMaxZ() {
        return BlockPos.betweenClosed(getExtremeCoord(false, false, true), getExtremeCoord(true, true, true));
    }

    public Iterable<BlockPos> getWallMin(Direction.Axis axis) {
        return switch (axis) {
            case X -> getWallMinX();
            case Y -> getWallMinY();
            case Z -> getWallMinZ();
        };
    }

    public Iterable<BlockPos> getWallMax(Direction.Axis axis) {
        return switch (axis) {
            case X -> getWallMaxX();
            case Y -> getWallMaxY();
            case Z -> getWallMaxZ();
        };
    }

    public boolean isInMinWall(Direction.Axis axis, BlockPos pos) {
        return switch (axis) {
            case X -> pos.getX() == getMinX() || (pos.getX() == getMaxInCoord().getX()
                    && pos.getZ() > getMinInCoord().getZ() && pos.getZ() < getMaxInCoord().getZ());
            case Y -> pos.getY() == getMinY();
            case Z -> pos.getZ() == getMinZ() || (pos.getZ() == getMaxInCoord().getZ()
                    && pos.getX() > getMinInCoord().getX() && pos.getX() < getMaxInCoord().getX());
        };
    }

    public boolean isInMaxWall(Direction.Axis axis, BlockPos pos) {
        return switch (axis) {
            case X -> pos.getX() == getMaxX() || (pos.getX() == getMinInCoord().getX()
                    && pos.getZ() > getMinInCoord().getZ() && pos.getZ() < getMaxInCoord().getZ());
            case Y -> pos.getY() == getMaxY();
            case Z -> pos.getZ() == getMaxZ() || (pos.getZ() == getMinInCoord().getZ()
                    && pos.getX() > getMinInCoord().getX() && pos.getX() < getMaxInCoord().getX());
        };
    }

    public boolean isInWall(Direction side, BlockPos pos) {
        return switch (side) {
            case DOWN -> pos.getY() == getMinY();
            case UP -> pos.getY() == getMaxY();
            case NORTH -> pos.getZ() == getMinZ() || (pos.getZ() == getMaxInCoord().getZ()
                    && pos.getX() > getMinInCoord().getX() && pos.getX() < getMaxInCoord().getX());
            case SOUTH -> pos.getZ() == getMaxZ() || (pos.getZ() == getMinInCoord().getZ()
                    && pos.getX() > getMinInCoord().getX() && pos.getX() < getMaxInCoord().getX());
            case WEST -> pos.getX() == getMinX() || (pos.getX() == getMaxInCoord().getX()
                    && pos.getZ() > getMinInCoord().getZ() && pos.getZ() < getMaxInCoord().getZ());
            case EAST -> pos.getX() == getMaxX() || (pos.getX() == getMinInCoord().getX()
                    && pos.getZ() > getMinInCoord().getZ() && pos.getZ() < getMaxInCoord().getZ());
        };
    }

    public BlockPos getMinimumInteriorPlaneCoord(Direction normal, int depth, int uCushion, int vCushion) {
        return switch (normal) {
            case DOWN -> getExtremeInteriorCoord(false, false, false).relative(Direction.UP, depth)
                    .relative(Direction.SOUTH, uCushion).relative(Direction.EAST, vCushion);
            case UP -> getExtremeInteriorCoord(false, true, false).relative(Direction.DOWN, depth)
                    .relative(Direction.SOUTH, uCushion).relative(Direction.EAST, vCushion);
            case NORTH -> getExtremeInteriorCoord(false, false, false).relative(Direction.SOUTH, depth)
                    .relative(Direction.EAST, uCushion).relative(Direction.UP, vCushion);
            case SOUTH -> getExtremeInteriorCoord(false, false, true).relative(Direction.NORTH, depth)
                    .relative(Direction.EAST, uCushion).relative(Direction.UP, vCushion);
            case WEST -> getExtremeInteriorCoord(false, false, false).relative(Direction.EAST, depth)
                    .relative(Direction.UP, uCushion).relative(Direction.SOUTH, vCushion);
            case EAST -> getExtremeInteriorCoord(true, false, false).relative(Direction.WEST, depth)
                    .relative(Direction.UP, uCushion).relative(Direction.SOUTH, vCushion);
        };
    }

    public BlockPos getMaximumInteriorPlaneCoord(Direction normal, int depth, int uCushion, int vCushion) {
        return switch (normal) {
            case DOWN -> getExtremeInteriorCoord(true, false, true).relative(Direction.UP, depth)
                    .relative(Direction.NORTH, uCushion).relative(Direction.WEST, vCushion);
            case UP -> getExtremeInteriorCoord(true, true, true).relative(Direction.DOWN, depth)
                    .relative(Direction.NORTH, uCushion).relative(Direction.WEST, vCushion);
            case NORTH -> getExtremeInteriorCoord(true, true, false).relative(Direction.SOUTH, depth)
                    .relative(Direction.WEST, uCushion).relative(Direction.DOWN, vCushion);
            case SOUTH -> getExtremeInteriorCoord(true, true, true).relative(Direction.NORTH, depth)
                    .relative(Direction.WEST, uCushion).relative(Direction.DOWN, vCushion);
            case WEST -> getExtremeInteriorCoord(false, true, true).relative(Direction.EAST, depth)
                    .relative(Direction.DOWN, uCushion).relative(Direction.NORTH, vCushion);
            case EAST -> getExtremeInteriorCoord(true, true, true).relative(Direction.WEST, depth)
                    .relative(Direction.DOWN, uCushion).relative(Direction.NORTH, vCushion);
        };
    }

    public Iterable<BlockPos> getInteriorPlaneMinX(int depth, int minUCushion, int minVCushion, int maxUCushion, int maxVCushion) {
        return BlockPos.betweenClosed(
                getMinimumInteriorPlaneCoord(Direction.WEST, depth, minUCushion, minVCushion),
                getMaximumInteriorPlaneCoord(Direction.WEST, depth, maxUCushion, maxVCushion));
    }

    public Iterable<BlockPos> getInteriorPlaneMaxX(int depth, int minUCushion, int minVCushion, int maxUCushion, int maxVCushion) {
        return BlockPos.betweenClosed(
                getMinimumInteriorPlaneCoord(Direction.EAST, depth, minUCushion, minVCushion),
                getMaximumInteriorPlaneCoord(Direction.EAST, depth, maxUCushion, maxVCushion));
    }

    public Iterable<BlockPos> getInteriorPlaneMinY(int depth, int minUCushion, int minVCushion, int maxUCushion, int maxVCushion) {
        return BlockPos.betweenClosed(
                getMinimumInteriorPlaneCoord(Direction.DOWN, depth, minUCushion, minVCushion),
                getMaximumInteriorPlaneCoord(Direction.DOWN, depth, maxUCushion, maxVCushion));
    }

    public Iterable<BlockPos> getInteriorPlaneMaxY(int depth, int minUCushion, int minVCushion, int maxUCushion, int maxVCushion) {
        return BlockPos.betweenClosed(getMinimumInteriorPlaneCoord(Direction.UP, depth, minUCushion, minVCushion),
                getMaximumInteriorPlaneCoord(Direction.UP, depth, maxUCushion, maxVCushion));
    }

    public Iterable<BlockPos> getInteriorPlaneMinZ(int depth, int minUCushion, int minVCushion, int maxUCushion, int maxVCushion) {
        return BlockPos.betweenClosed(
                getMinimumInteriorPlaneCoord(Direction.NORTH, depth, minUCushion, minVCushion),
                getMaximumInteriorPlaneCoord(Direction.NORTH, depth, maxUCushion, maxVCushion));
    }

    public Iterable<BlockPos> getInteriorPlaneMaxZ(int depth, int minUCushion, int minVCushion, int maxUCushion, int maxVCushion) {
        return BlockPos.betweenClosed(
                getMinimumInteriorPlaneCoord(Direction.SOUTH, depth, minUCushion, minVCushion),
                getMaximumInteriorPlaneCoord(Direction.SOUTH, depth, maxUCushion, maxVCushion));
    }

    public Iterable<BlockPos> getInteriorPlane(Direction normal, int depth, int minUCushion, int minVCushion, int maxUCushion, int maxVCushion) {
        return switch (normal) {
            case DOWN -> getInteriorPlaneMinY(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
            case UP -> getInteriorPlaneMaxY(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
            case NORTH -> getInteriorPlaneMinZ(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
            case SOUTH -> getInteriorPlaneMaxZ(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
            case WEST -> getInteriorPlaneMinX(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
            case EAST -> getInteriorPlaneMaxX(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
        };
    }

    public int getToriodThickness() {
        return thickness;
    }

    public void SetToriodThickness(int thickness) {
        this.thickness = thickness;
    }
}