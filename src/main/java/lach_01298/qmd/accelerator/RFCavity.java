package lach_01298.qmd.accelerator;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;

public class RFCavity {
    private final Accelerator accelerator;
    private BlockPos pos;
    private Axis axis;
    private RFCavityType type;

    private final Long2ObjectMap<IAcceleratorComponent> components = new Long2ObjectOpenHashMap<>();

    public RFCavity(Accelerator accelerator, BlockPos pos, Axis axis) {
        this.accelerator = accelerator;
        this.pos = pos;
        this.axis = axis;
        setType();
        addComponents();
    }

    private void setType() {
        if (accelerator.getWorld().getBlockEntity(pos.above()) instanceof TileAcceleratorRFCavity cavity) {
            type = cavity.rfCavityType;
        } else {
            QMD.LOGGER.error("could not set RF Cavity type because {} at {} is not a instance of TileAcceleratorRFCavity", accelerator.getWorld().getBlockEntity(pos.above()), pos.above());
        }
    }

    private void addComponents() {
        addComponent(pos);
        addComponent(pos.above());
        addComponent(pos.below());
        if (axis == Axis.X) {
            addComponent(pos.north());
            addComponent(pos.south());
            addComponent(pos.north().above());
            addComponent(pos.north().below());
            addComponent(pos.south().above());
            addComponent(pos.south().below());
        }
        if (axis == Axis.Z) {
            addComponent(pos.east());
            addComponent(pos.west());
            addComponent(pos.east().above());
            addComponent(pos.east().below());
            addComponent(pos.west().above());
            addComponent(pos.west().below());
        }
    }

    private void addComponent(BlockPos position) {
        if (accelerator.getWorld().getBlockEntity(position) instanceof IAcceleratorComponent) {
            components.put(position.asLong(), (IAcceleratorComponent) accelerator.getWorld().getBlockEntity(position));
        } else {
            QMD.LOGGER.error("could not add tile to RF Cavity becuase{} at {} is not a instance of IAcceleratorComponent", accelerator.getWorld().getBlockEntity(position), position);
        }
    }

    public BlockPos getPos() {
        return pos;
    }

    public Axis getAxis() {
        return axis;
    }

    public RFCavityType getType() {
        return type;
    }

    public Long2ObjectMap<IAcceleratorComponent> getComponents() {
        return components;
    }
}