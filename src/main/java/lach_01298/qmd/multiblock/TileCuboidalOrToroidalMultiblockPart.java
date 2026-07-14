package lach_01298.qmd.multiblock;

import com.nred.nuclearcraft.block_entity.multiblock.AbstractPartBlockEntity;
import com.nred.nuclearcraft.multiblock.internal.BlockFacing;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileCuboidalOrToroidalMultiblockPart<MULTIBLOCK extends CuboidalOrToroidalMultiblock<MULTIBLOCK>> extends AbstractPartBlockEntity<MULTIBLOCK> {

    /**
     * Minimum bounding box coordinate of the internal. Blocks do not necessarily exist at this coord if your machine
     * is not a cube/rectangular prism.
     */
    private BlockPos miniumInCoord;

    /**
     * Maximum bounding box coordinate of the internal hole. Blocks do not necessarily exist at this coord if your machine
     * is not a cube/rectangular prism.
     */
    private BlockPos maximumInCoord;

    /**
     * The amount of blocks from the outer edge that the inner edge is
     *
     */
    private int toroidThickness;

    private final PartPosition.Type positionType;
    private PartPosition partPosition;
    private BlockFacing outwardFacings;

    public TileCuboidalOrToroidalMultiblockPart(BlockEntityType<?> type, BlockPos position, BlockState blockState, PartPosition.Type positionType, int toroidThickness) {
        super(type, position, blockState);
        this.positionType = positionType;
        partPosition = PartPosition.Unknown;
        outwardFacings = BlockFacing.NONE;
        this.toroidThickness = toroidThickness;
    }

    // Positional Data

    public PartPosition.Type getPartPositionType() {
        return positionType;
    }

    /**
     * Get the external facing of the part in the formed multiblock
     *
     * @return the outward facing of the part. A face is "set" in the BlockFacings
     * object if that face is facing outward
     */
    @Nonnull
    public BlockFacing getOutwardsDir() {

        return outwardFacings;
    }

    /**
     * Get the position of the part in the formed multiblock
     *
     * @return the position of the part
     */
    @Nonnull
    public PartPosition getPartPosition() {

        return partPosition;
    }

    /**
     * Return the single direction this part is facing if the part is in one side of
     * the multiblock
     *
     * @return the direction toward with the part is facing or null if the part is
     * not in one side of the multiblock
     */
    @Nullable
    public Direction getOutwardFacing() {
        Direction facing = null != this.partPosition ? this.partPosition.getDirection().orElse(null) : null;

        if (null == facing) {
            BlockFacing out = this.getOutwardsDir();

            if (!out.none() && 1 == out.countFacesIf(true))
                facing = out.firstIf(true);
        }

        return facing;
    }

    // Handlers from MultiblockTileEntityBase

    @Override
    public void onAttached(MULTIBLOCK newMultiblock) {
        super.onAttached(newMultiblock);
        if (newMultiblock.getPartsCount() > 1) {
            recalculateOutwardsDirection(newMultiblock.getMinimumCoord().get(), newMultiblock.getMaximumCoord().get());
        }
    }

    @Override
    public void onPreMachineAssembled(MULTIBLOCK multiblock) {
        // Discover where I am on the multiblock
        recalculateOutwardsDirection(multiblock.getMinimumCoord().get(), multiblock.getMaximumCoord().get());
    }

    @Override
    public void onPreMachineBroken() {
        partPosition = PartPosition.Unknown;
        outwardFacings = BlockFacing.NONE;
    }

    // Positional helpers

    public void recalculateOutwardsDirection(BlockPos minCoord, BlockPos maxCoord) {
        BlockPos myPosition = this.getWorldPosition();
        BlockPos minInCoord = new BlockPos(minCoord.getX() + toroidThickness - 1, minCoord.getY(), minCoord.getZ() + toroidThickness - 1);
        BlockPos maxInCoord = new BlockPos(maxCoord.getX() - toroidThickness + 1, maxCoord.getY(), maxCoord.getZ() - toroidThickness + 1);
        int myX = myPosition.getX();
        int myY = myPosition.getY();
        int myZ = myPosition.getZ();
        int facesMatching = 0;

        // witch direction are we facing?

        boolean downFacing = myY == minCoord.getY();
        boolean upFacing = myY == maxCoord.getY();
        boolean northFacing = myZ == minCoord.getZ() || (myZ == maxInCoord.getZ() && myX > minInCoord.getX() && myX < maxInCoord.getX());
        boolean southFacing = myZ == maxCoord.getZ() || (myZ == minInCoord.getZ() && myX > minInCoord.getX() && myX < maxInCoord.getX());
        boolean westFacing = myX == minCoord.getX() || (myX == maxInCoord.getX() && myZ > minInCoord.getZ() && myZ < maxInCoord.getZ());
        boolean eastFacing = myX == maxCoord.getX() || (myX == minInCoord.getX() && myZ > minInCoord.getZ() && myZ < maxInCoord.getZ());

        this.outwardFacings = BlockFacing.from(downFacing, upFacing, northFacing, southFacing, westFacing, eastFacing);

        // how many faces are facing outward?

        if (eastFacing || westFacing)
            ++facesMatching;

        if (upFacing || downFacing)
            ++facesMatching;

        if (southFacing || northFacing)
            ++facesMatching;

        // what is our position in the multiblock structure?

        if (facesMatching <= 0) {
            this.partPosition = PartPosition.Interior;
        } else if (facesMatching >= 3) {
            this.partPosition = PartPosition.FrameCorner;
        } else if (facesMatching == 2) {
            if (!eastFacing && !westFacing)
                this.partPosition = PartPosition.FrameEastWest;
            else if (!southFacing && !northFacing)
                this.partPosition = PartPosition.FrameSouthNorth;
            else
                this.partPosition = PartPosition.FrameUpDown;
        } else {
            // only 1 face matches

            if (eastFacing) {
                this.partPosition = PartPosition.EastFace;

            } else if (westFacing) {
                this.partPosition = PartPosition.WestFace;

            } else if (southFacing) {
                this.partPosition = PartPosition.SouthFace;

            } else if (northFacing) {
                this.partPosition = PartPosition.NorthFace;

            } else if (upFacing) {
                this.partPosition = PartPosition.TopFace;

            } else {
                this.partPosition = PartPosition.BottomFace;
            }
        }
    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return positionType.equals(position.getType());
    }

    public boolean isGoodForFrame() {
        return positionType.equals(PartPosition.Type.Frame);
    }

    public boolean isGoodForSides() {
        return positionType.equals(PartPosition.Type.Face);
    }

    public boolean isGoodForTop() {
        return positionType.equals(PartPosition.Type.Face);
    }

    public boolean isGoodForBottom() {
        return positionType.equals(PartPosition.Type.Face);
    }

    public boolean isGoodForInterior() {
        return positionType.equals(PartPosition.Type.Interior);
    }
}