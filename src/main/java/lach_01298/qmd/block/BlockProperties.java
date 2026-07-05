package lach_01298.qmd.block;

import lach_01298.qmd.enums.EnumTypes.IOType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class BlockProperties {
    public static final EnumProperty<IOType> IO = EnumProperty.create("io", IOType.class);
    public static final EnumProperty<IOType> IO_SIMPLE = EnumProperty.create("io_simple", IOType.class, IOType.INPUT, IOType.OUTPUT);
    public static final EnumProperty<Direction.Axis> AXIS_HORIZONTAL = EnumProperty.create("axis_horizontal", Direction.Axis.class, Direction.Axis.X, Direction.Axis.Z);
}
