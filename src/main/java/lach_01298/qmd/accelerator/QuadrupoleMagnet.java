package lach_01298.qmd.accelerator;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.enums.BlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;

public class QuadrupoleMagnet {
    private final Accelerator accelerator;
    private BlockPos pos;
    private Axis axis;

    private final Long2ObjectMap<IAcceleratorComponent> components = new Long2ObjectOpenHashMap<>();

    private BlockTypes.MagnetType type;

    public QuadrupoleMagnet(Accelerator accelerator, BlockPos pos, Axis axis) {
        this.accelerator = accelerator;
        this.pos = pos;
        this.axis = axis;
        setType();
        addComponents();
    }

    private void setType() {
        if (accelerator.getWorld().getBlockEntity(pos.above()) instanceof TileAcceleratorMagnet magnet) {
            type = magnet.magnetType;
        } else {
            QMD.LOGGER.error("could not set quadrupole type because {} at {} is not a instance of TileAcceleratorMagnet", accelerator.getWorld().getBlockEntity(pos.above()), pos.above());
        }
    }

    private void addComponents() {
        addComponent(pos);
        addComponent(pos.above());
        addComponent(pos.below());
        if (axis == Axis.X) {
            addComponent(pos.north());
            addComponent(pos.south());
        }
        if (axis == Axis.Z) {
            addComponent(pos.east());
            addComponent(pos.west());
        }

    }

    private void addComponent(BlockPos position) {
        if (accelerator.getWorld().getBlockEntity(position) instanceof IAcceleratorComponent) {
            components.put(position.asLong(), (IAcceleratorComponent) accelerator.getWorld().getBlockEntity(position));
        } else {
            QMD.LOGGER.error("could not add tile to quadrupole because {} at {} is not a instance of IAcceleratorComponent", accelerator.getWorld().getBlockEntity(position), position);
        }
    }

    public BlockPos getPos() {
        return pos;
    }

    public Axis getAxis() {
        return axis;
    }

    public BlockTypes.MagnetType getType() {
        return type;
    }

    public Long2ObjectMap<IAcceleratorComponent> getComponents() {
        return components;
    }
}