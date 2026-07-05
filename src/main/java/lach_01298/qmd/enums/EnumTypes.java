package lach_01298.qmd.enums;

import net.minecraft.util.StringRepresentable;

public class EnumTypes {
    public enum IOType implements StringRepresentable {
        INPUT("input", 0),
        OUTPUT("output", 1),
        DISABLED("disabled", 2);


        private String name;
        private int id;


        private IOType(String name, int id) {
            this.name = name;
            this.id = id;

        }

        public IOType getNextIO() {
            if (this.getID() >= IOType.values().length - 1) {
                return getTypeFromID(0);

            }
            return getTypeFromID(this.getID() + 1);
        }

        public static IOType getTypeFromID(int id) {
            return IOType.values()[id];
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

        public int getID() {
            return id;
        }
    }
}