package lach_01298.qmd.accelerator.tile;

public interface IAcceleratorComponent extends IAcceleratorPart {
    int getMaxOperatingTemp();

    boolean isFunctional();

    void setFunctional(boolean func);

    default boolean isToHot() {
        if (getMultiblockController().get().getTemperature() > this.getMaxOperatingTemp()) {
            return true;
        }
        return false;
    }
}