package lach_01298.qmd.tile;

import lach_01298.qmd.enums.EnumTypes;

public interface ITileIOType {
    EnumTypes.IOType getIOType();

    void setIOType(EnumTypes.IOType type);
}