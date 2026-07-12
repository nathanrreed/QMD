package lach_01298.qmd.cct;

import com.nred.nuclearcraft.compat.cct.MultiblockPeripheral;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorComputerPort;
import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.core.BlockPos;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class AcceleratorPeripheral extends MultiblockPeripheral<Accelerator> implements IPeripheral {
    public AcceleratorPeripheral(TileAcceleratorComputerPort acceleratorComputerPort) {
        super(acceleratorComputerPort);
    }

    // @Callback(doc = "--function():bool Returns true if structure is complete.")
    @LuaFunction(mainThread = true)
    public boolean isComplete() {
        return test();
    }

    // @Callback(doc = "--function():bool Returns true if accelerator is on.")
    @LuaFunction(mainThread = true)
    public boolean isAcceleratorOn() {
        if (!test()) return false;
        return getMultiblock().isControllorOn;
    }

    // @Callback(doc = "--function():string Returns accelerators logic ID if accelerator is assembled.")
    @LuaFunction(mainThread = true)
    public String getAcceleratorType() {
        if (!test()) return "";
        return getMultiblock().getLogic().getID();
    }

    // @Callback(doc = "--function():int Returns number of RF Cavities.")
    @LuaFunction(mainThread = true)
    public int getNumberOfRfCavity() {
        if (!test()) return 0;
        return getMultiblock().RFCavityNumber;
    }

    // @Callback(doc = "--function():int Returns number of dipole magnets.")
    @LuaFunction(mainThread = true)
    public int getNumberOfDipole() {
        if (!test()) return 0;
        return getMultiblock().dipoleNumber;
    }

    // @Callback(doc = "--function():int Returns number of quadrupole magnets.")
    @LuaFunction(mainThread = true)
    public int getNumberOfQuadrupole() {
        if (!test()) return 0;
        return getMultiblock().quadrupoleNumber;
    }

    // @Callback(doc = "--function():int Returns accelerators current temperature.")
    @LuaFunction(mainThread = true)
    public int getTemperature() {
        if (!test()) return 0;
        return getMultiblock().getTemperature();
    }

    // @Callback(doc = "--function():int Returns accelerators maximum operating temperature.")
    @LuaFunction(mainThread = true)
    public int getMaxTemperature() {
        if (!test()) return 0;
        return getMultiblock().maxOperatingTemp;
    }

    // @Callback(doc = "--function():table Returns infomation about the heat buffer of the accelerator. (stored_heat, heat_capacity)")
    @LuaFunction(mainThread = true)
    public Object[] getHeatBufferInfo() {
        Map<String, Object> statsData = new HashMap<String, Object>();
        statsData.put("heat_stored", test() ? getMultiblock().heatBuffer.getHeatStored() : 0);
        statsData.put("heat_capacity", test() ? getMultiblock().heatBuffer.getHeatCapacity() : 0);

        return new Object[]{statsData};
    }

    // @Callback(doc = "--function():table Returns infomation about the cooling of the accelerator. (cooling_fluid, cooling, max_coolant_in, max_coolant_out)")
    @LuaFunction(mainThread = true)
    public Object[] getCoolingInfo() {
        Map<String, Object> statsData = new HashMap<String, Object>();
        statsData.put("cooling_fluid", test() && getMultiblock().coolingRecipeInfo != null ? getMultiblock().coolingRecipeInfo.recipe.getFluidIngredients().get(0).getStack().getFluidType().getDescription().getString() : "");
        statsData.put("cooling", test() ? getMultiblock().cooling : 0);
        statsData.put("maxCoolantIn", test() ? getMultiblock().maxCoolantIn : 0);
        statsData.put("maxCoolantOut", test() ? getMultiblock().maxCoolantOut : 0);

        return new Object[]{statsData};
    }

    // @Callback(doc = "--function():table Returns infomation about the heating of the accelerator. (internal_heating, external_heating, max_external_heating, ambient_temperature)")
    @LuaFunction(mainThread = true)
    public Object[] getHeatingInfo() {
        Map<String, Object> statsData = new HashMap<>();
        statsData.put("internal_heating", test() ? getMultiblock().rawHeating : 0);
        statsData.put("external_heating", test() ? getMultiblock().getExternalHeating() : 0);
        statsData.put("max_external_heating", test() ? getMultiblock().getMaxExternalHeating() : 0);
        statsData.put("ambient_temperature", test() ? getMultiblock().ambientTemp : 0);
        return new Object[]{statsData};
    }

    // @Callback(doc = "--function():table Returns infomation about the size of the accelerator. (x_length, y_length, z_length, volume, surface_area)")
    @LuaFunction(mainThread = true)
    public Object[] getSizeInfo() {
        Map<String, Object> statsData = new HashMap<String, Object>();
        statsData.put("x_length", test() ? getMultiblock().getExteriorLengthX() : 0);
        statsData.put("y_length", test() ? getMultiblock().getExteriorLengthY() : 0);
        statsData.put("z_length", test() ? getMultiblock().getExteriorLengthZ() : 0);
        statsData.put("volume", test() ? getMultiblock().getExteriorVolume() : 0);
        statsData.put("surface_area", test() ? getMultiblock().getExteriorSurfaceArea() : 0);
        return new Object[]{statsData};
    }

    // @Callback(doc = "--function():table Returns infomation about the beam of the accelerator. (beam_length, beam_radius)")
    @LuaFunction(mainThread = true)
    public Object[] getBeamInfo() {
        Map<String, Object> statsData = new HashMap<String, Object>();
        statsData.put("beam_length", test() ? getMultiblock().getLogic().getBeamLength() : 0);
        statsData.put("beam_radius", test() ? getMultiblock().getLogic().getBeamRadius() : 0);
        return new Object[]{statsData};
    }

    // @Callback(doc = "--function():table Returns infomation about the energy and energy usage of the accelerator. (energy_required, energy_stored, energy_capacity, energy_efficiency)")
    @LuaFunction(mainThread = true)
    public Object[] getEnergyInfo() {
        Map<String, Object> statsData = new HashMap<String, Object>();
        statsData.put("energy_required", test() ? getMultiblock().requiredEnergy : 0);
        statsData.put("energy_stored", test() ? getMultiblock().energyStorage.getEnergyStored() : 0);
        statsData.put("energy_capacity", test() ? getMultiblock().energyStorage.getMaxEnergyStored() : 0);
        statsData.put("energy_efficiency", test() ? getMultiblock().efficiency : 0);
        return new Object[]{statsData};
    }

    // @Callback(doc = "--function():table Returns general accelerator statistics. (accelerating_voltage, dipole_strength, quadrupole_strength, input_particle_min_energy)")
    @LuaFunction(mainThread = true)
    public Object[] getStats() {
        Map<String, Object> statsData = new HashMap<String, Object>();
        statsData.put("accelerating_voltage", test() ? getMultiblock().acceleratingVoltage : 0);
        statsData.put("dipole_strength", test() ? getMultiblock().dipoleStrength : 0);
        statsData.put("quadrupole_strength", test() ? getMultiblock().quadrupoleStrength : 0);
        statsData.put("input_particle_min_energy", test() ? getMultiblock().beams.get(0).getMinEnergy() : 0);
        return new Object[]{statsData};
    }

    // Particle stuff
    // @Callback(doc = "--function():bool Returns if the accelerator has a particle stack")
    @LuaFunction(mainThread = true)
    public Object[] hasParticle() {
        return new Object[]{getMultiblock().beams.get(1).getParticleStack() != null};
    }

    // @Callback(doc = "--function():table Returns input particle stack parameters.(type, amount, energy, focus)")
    @LuaFunction(mainThread = true)
    public Object[] getInputParticleInfo() {
        Map<String, Object> infoData = new HashMap<>();
        if (getMultiblock().beams.get(0).getParticleStack() != null) {
            infoData.put("type", getMultiblock().beams.get(0).getParticleStack().getParticle().getName());
            infoData.put("amount", getMultiblock().beams.get(0).getParticleStack().getAmount());
            infoData.put("energy", getMultiblock().beams.get(0).getParticleStack().getMeanEnergy());
            infoData.put("focus", getMultiblock().beams.get(0).getParticleStack().getFocus());
        }
        return new Object[]{infoData};
    }

    // @Callback(doc = "--function():table Returns output particle stack parameters.(type, amount, energy, focus)")
    @LuaFunction(mainThread = true)
    public Object[] getOutputParticleInfo() {
        Map<String, Object> infoData = new HashMap<String, Object>();
        if (getMultiblock().beams.get(1).getParticleStack() != null) {
            infoData.put("type", getMultiblock().beams.get(1).getParticleStack().getParticle().getName());
            infoData.put("amount", getMultiblock().beams.get(1).getParticleStack().getAmount());
            infoData.put("energy", getMultiblock().beams.get(1).getParticleStack().getMeanEnergy());
            infoData.put("focus", getMultiblock().beams.get(1).getParticleStack().getFocus());
        }
        return new Object[]{infoData};
    }

    // @Callback(doc = "--function():table Returns synchrotron port particle stack parameters.(type, amount, energy, focus)")
    @LuaFunction(mainThread = true)
    public Object[] getSynchrotronParticleInfo() {
        Map<String, Object> infoData = new HashMap<>();
        if (test() && getMultiblock().controller.getLogicID() == "ring_accelerator") {
            if (getMultiblock().beams.get(2).getParticleStack() != null) {
                infoData.put("type", getMultiblock().beams.get(2).getParticleStack().getParticle().getName());
                infoData.put("amount", getMultiblock().beams.get(2).getParticleStack().getAmount());
                infoData.put("energy", getMultiblock().beams.get(2).getParticleStack().getMeanEnergy());
                infoData.put("focus", getMultiblock().beams.get(2).getParticleStack().getFocus());
            }
        }
        return new Object[]{infoData};
    }

    // @Callback(doc = "--function():table Returns infomation about the particle type.(type, mass, energy, charge, spin, interacts_with_em, interacts_with_weak, interacts_with_strong)")
    @LuaFunction(mainThread = true)
    public Object[] getParticleInfo() {
        Map<String, Object> infoData = new HashMap<>();
        if (getMultiblock().beams.get(1).getParticleStack() != null) {
            infoData.put("type", getMultiblock().beams.get(1).getParticleStack().getParticle().getName());
            infoData.put("mass", getMultiblock().beams.get(1).getParticleStack().getParticle().getMass());
            infoData.put("charge", getMultiblock().beams.get(1).getParticleStack().getParticle().getCharge());
            infoData.put("spin", getMultiblock().beams.get(1).getParticleStack().getParticle().getSpin());
            infoData.put("interacts_with_em", getMultiblock().beams.get(1).getParticleStack().getParticle().interactsWithEM());
            infoData.put("interacts_with_weak", getMultiblock().beams.get(1).getParticleStack().getParticle().interactsWithWeak());
            infoData.put("interacts_with_strong", getMultiblock().beams.get(1).getParticleStack().getParticle().interactsWithStrong());
        }
        return new Object[]{infoData};
    }

    // @Callback(doc = "--function():bool Returns if the accelerator has an ion source")
    @LuaFunction(mainThread = true)
    public Object[] hasIonSource() {
        boolean hasSource = false;
        if (test() && getMultiblock().controller.getLogicID() == "linear_accelerator") {
            LinearAcceleratorLogic logic = (LinearAcceleratorLogic) getMultiblock().controller.getLogic();
            if (logic.getSource() != null) {
                hasSource = true;
            }
        }
        return new Object[]{hasSource};
    }

    // @Callback(doc = "--function():table Returns infomation about the ion source. (source_item, source_fluid, particle_type, amount, energy, focus)")
    @LuaFunction(mainThread = true)
    public Object[] getIonSourceInfo() {
        Map<String, Object> infoData = new HashMap<>();
        if (test() && getMultiblock().controller.getLogicID() == "linear_accelerator") {
            LinearAcceleratorLogic logic = (LinearAcceleratorLogic) getMultiblock().controller.getLogic();
            if (logic.getSource() != null) {
                infoData.put("source_item", logic.getSource().getInventoryStacks().get(0).getDisplayName()); // TODO test!
                infoData.put("source_fluid", logic.getSource().getTanks().get(0).getFluidName());
                if (logic.recipeInfo.recipe != null) {
                    ParticleStack particle = logic.recipeInfo.recipe.getParticleProducts().get(0);
                    infoData.put("particle_type", logic.recipeInfo != null ? particle.getParticle().getName() : "");
                    infoData.put("amount", particle.getAmount());
                    infoData.put("energy", particle.getMeanEnergy());
                    infoData.put("focus", particle.getFocus());
                } else {
                    infoData.put("particle_type", "");
                    infoData.put("amount", 0);
                    infoData.put("energy", 0l);
                    infoData.put("focus", 0d);
                }
            }
        }
        return new Object[]{infoData};
    }

    //accelerator control
    // @Callback(doc = "--function(int energy_percentage):int changes output particle energy to this percentage of the max energy (For decelerators it outputs the opposite percentage e.g. 15% -> 85% output energy). Can only be between 5 and 100 inclusive or 0 to turn of accelerator entirely. For beam diverters this only turns it on/off. Returns what it was set to")
    @LuaFunction(mainThread = true)
    public Object[] setEnergyPercentage(int percentage) {
        if (!test()) return new Object[]{0};
        getMultiblock().computerControlled = true;
        if (percentage < 5) {
            percentage = 0;
        }
        if (percentage > 100) {
            percentage = 100;
        }
        getMultiblock().energyPercentage = percentage;
        return new Object[]{percentage};
    }

    // @Callback(doc = "--function(bool computer_controlled):bool turns computer controlled mode on/off. With this on accelerator controller ignores redstone. Returns what it was set to")
    @LuaFunction(mainThread = true)
    public Object[] setComputerControlled(boolean computerControlled) {
        if (!test()) return new Object[]{false};
        getMultiblock().computerControlled = computerControlled;
        return new Object[]{computerControlled};
    }

    // @Callback(doc = "--function():int Returns the energyPercentage set.")
    @LuaFunction(mainThread = true)
    public Object[] getEnergyPercentage() {
        if (!test()) return new Object[]{0};
        return new Object[]{getMultiblock().energyPercentage};
    }

    // @Callback(doc = "--function():bool Returns if accelerator is in computer controlled mode")
    @LuaFunction(mainThread = true)
    public Object[] isComputerControlled() {
        if (!test()) return new Object[]{false};
        return new Object[]{getMultiblock().computerControlled};
    }

    // @Callback(doc = "--function(x,y,z):bool Returns if position (x,y,z) is a beam port")
    @LuaFunction(mainThread = true)
    public Object[] isBeamPort(int x, int y, int z) {
        if (!test()) return new Object[]{false};
        BlockPos pos = new BlockPos(x, y, z);
        for (TileAcceleratorBeamPort port : getMultiblock().getPartMap(TileAcceleratorBeamPort.class).values()) {
            if (port.getTilePos().equals(pos)) {
                return new Object[]{true};
            }
        }
        return new Object[]{false};
    }

    // @Callback(doc = "--function(x,y,z):bool Returns if beam port mode was switched")
    @LuaFunction(mainThread = true)
    public Object[] switchBeamPort(int x, int y, int z) {
        if (!test()) return new Object[]{false};
        BlockPos pos = new BlockPos(x, y, z);
        for (TileAcceleratorBeamPort port : getMultiblock().getPartMap(TileAcceleratorBeamPort.class).values()) {
            if (port.getTilePos().equals(pos)) {
                port.setTrigger();
                port.getMultiblockController().get().getLogic().switchIO();
                return new Object[]{true};
            }
        }
        return new Object[]{false};
    }

    // @Callback(doc = "--function(x,y,z):string Returns beam port's mode. Returns invalid if invalid beam port otherwise either input, output or disabled")
    @LuaFunction(mainThread = true)
    public Object[] getBeamPortMode(int x, int y, int z) {
        if (!test()) return new Object[]{"invalid"};
        BlockPos pos = new BlockPos(x, y, z);
        for (TileAcceleratorBeamPort port : getMultiblock().getPartMap(TileAcceleratorBeamPort.class).values()) {
            if (port.getTilePos().equals(pos)) {
                return new Object[]{port.getIOType().name()};
            }
        }
        return new Object[]{"invalid"};
    }

    // @Callback(doc = "--function(x,y,z):string Returns beam port's switch mode. Returns invalid if invalid beam port otherwise either input or output")
    @LuaFunction(mainThread = true)
    public Object[] getBeamPortSwitchMode(int x, int y, int z) {
        if (!test()) return new Object[]{"invalid"};
        BlockPos pos = new BlockPos(x, y, z);
        for (TileAcceleratorBeamPort port : getMultiblock().getPartMap(TileAcceleratorBeamPort.class).values()) {
            if (port.getTilePos().equals(pos)) {
                return new Object[]{port.getSetting().name()};
            }
        }
        return new Object[]{"invalid"};
    }

    @Override
    public String getType() {
        return "qmd:accelerator";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other instanceof AcceleratorPeripheral && ((AcceleratorPeripheral) other).computerPort.equals(computerPort);
    }
}