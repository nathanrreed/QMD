package lach_01298.qmd.accelerator;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.internal.fluid.Tank;
import com.nred.nuclearcraft.recipe.SizedChanceFluidIngredient;
import com.nred.nuclearcraft.recipe.SizedChanceItemIngredient;
import it.zerono.mods.zerocore.lib.data.nbt.ISyncableEntity;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockPart;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.config.QMDServerConfig;
import lach_01298.qmd.multiblock.InventoryHelper;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.MassSpectrometerUpdatePacket;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.recipe.types.MassSpectrometerRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;
import static lach_01298.qmd.recipe.QMDRecipes.mass_spectrometer;
import static net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;
import static net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.SIMULATE;

public class MassSpectrometerLogic extends AcceleratorLogic {
    public QMDRecipeInfo<MassSpectrometerRecipe> recipeInfo;

    public static final int diameter = 7;

    public double workDone = 0;
    public double recipeWork = 100;
    public double speed = 1;

    // Multiblock logic

    public MassSpectrometerLogic(AcceleratorLogic oldLogic) {
        super(oldLogic);
		
		/*
		tank 0 = input coolant
		tank 1 = output coolant
		tank 2 = input fluid
		tank 3 = output fluid 1
		tank 4 = output fluid 2
		tank 5 = output fluid 3
		tank 6 = output fluid 4
		*
		*/

        //on the rare occasion of changing the multiblock to a different type with the tank full
        if (!(oldLogic instanceof MassSpectrometerLogic || oldLogic.getID().equals(""))) {
            multiblock.tanks.get(2).setFluidStored(null);
            multiblock.tanks.get(3).setFluidStored(null);
            multiblock.tanks.get(4).setFluidStored(null);
            multiblock.tanks.get(5).setFluidStored(null);
            multiblock.tanks.get(6).setFluidStored(null);
        }
    }

    @Override
    public String getID() {
        return "mass_spectrometer";
    }

    // Accelerator methods

    @Override
    public int getBeamLength() {
        return 13;
    }

    @Override
    public double getBeamRadius() {
        return 2.5;
    }

    // Multiblock validation

    @Override
    public boolean isMachineWhole() {
        Axis axis;
        Accelerator acc = multiblock;

        // check size
        if (acc.getExteriorLengthY() != diameter) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.wrong_height");
            return false;
        }

        if (acc.getExteriorLengthX() != diameter && acc.getExteriorLengthZ() != diameter) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.wrong_width");
            return false;
        }

        int length = diameter - 2;
        if (acc.getExteriorLengthX() == acc.getExteriorLengthZ()) {
            axis = null;
        } else if (acc.getExteriorLengthX() == diameter) {
            axis = Axis.Z;
            length = acc.getInteriorLengthZ();
        } else {
            axis = Axis.X;
            length = acc.getInteriorLengthX();
        }

        if (length % 2 == 0) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_odd_length");
            return false;
        }

        //check ion sources and collectors


        int sourceAmount = length / 2;

        if (getPartMap(TileAcceleratorIonSource.class).size() != sourceAmount) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.need_ion_source_amount", sourceAmount);
            return false;
        }


        // figure out axis if the multiblock is a cube
        if (axis == null) {
            TileAcceleratorIonSource source = getPartMap(TileAcceleratorIonSource.class).values().iterator().next();
            Direction normal = getWallNormal(source.getBlockPos());
            if (normal == null) {
                multiblock.setLastError(source.getBlockPos(), QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_pos");
                return false;
            }
            axis = normal.getAxis() == Axis.X ? Axis.Z : Axis.X;
        }

        boolean[] layerHasSource = new boolean[sourceAmount];
        Arrays.fill(layerHasSource, false);


        for (TileAcceleratorIonSource source : getPartMap(TileAcceleratorIonSource.class).values()) {
            BlockPos sourcePos = source.getBlockPos();
            boolean validSource = false;
            for (String validSourceName : QMDServerConfig.mass_spectrometer_valid_sources) {
                if (validSourceName.equals(source.name)) {
                    validSource = true;
                }
            }

            if (!validSource) {
                multiblock.setLastError(sourcePos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_type");
                return false;
            }


            if (sourcePos.getY() != acc.getMinInteriorY()) {
                multiblock.setLastError(sourcePos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_pos");
                return false;
            }

            if (getWallNormal(source.getBlockPos()) == null) {
                multiblock.setLastError(source.getBlockPos(), QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_pos");
                return false;
            }

            if (acc.getWorld().getBlockState(sourcePos).getValue(FACING_ALL) != getWallNormal(sourcePos).getOpposite()) {
                multiblock.setLastError(sourcePos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_facing");
                return false;
            }
            int offset = axis == Axis.X ? sourcePos.getX() - acc.getMinInteriorX() : sourcePos.getZ() - acc.getMinInteriorZ();

            if (offset % 2 != 1 || offset / 2 >= layerHasSource.length || offset < 0) {
                multiblock.setLastError(sourcePos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_pos");
                return false;
            }

            if (layerHasSource[offset / 2]) {
                multiblock.setLastError(sourcePos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_in_layer_already");
                return false;
            } else {
                layerHasSource[offset / 2] = true;
            }

            for (int i = 1; i <= 4; i++) {
                if (!(acc.getWorld().getBlockEntity(sourcePos.above(i)) instanceof TileAcceleratorIonCollector)) {
                    multiblock.setLastError(sourcePos.above(i), QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_ion_collector");
                    return false;
                }
                if (acc.getWorld().getBlockState(sourcePos.above(i)).getValue(FACING_ALL) != getWallNormal(sourcePos).getOpposite()) {
                    multiblock.setLastError(sourcePos.above(i), QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_collector_wrong_facing");
                    return false;
                }
            }
        }

        if (getPartMap(TileAcceleratorIonCollector.class).size() != sourceAmount * 4) {
            multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.need_ion_collector_amount", Collections.emptyList());
            return false;
        }


        // check interior
        Class magnetType = null;

        for (int layerNumber = 0; layerNumber < length; layerNumber++) {
            Iterable<BlockPos> layer = acc.getInteriorPlane(Direction.fromAxisAndDirection(axis, AxisDirection.NEGATIVE), layerNumber, 0, 0, 0, 0);

            if (layerNumber % 2 == 0) {
                for (BlockPos pos : layer) {
                    if (axis == Axis.X) {
                        if (pos.getY() > acc.getMinInteriorY() && pos.getY() < acc.getMaxInteriorY() && pos.getZ() > acc.getMinInteriorZ() && pos.getZ() < acc.getMaxInteriorZ()) {
                            if (!(acc.getWorld().getBlockEntity(pos) instanceof TileAcceleratorYoke)) {
                                multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_yoke");
                                return false;
                            }
                        } else {
                            if (!(acc.getWorld().getBlockEntity(pos) instanceof TileAcceleratorMagnet)) {
                                multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_magnet");
                                return false;
                            } else {
                                if (magnetType == null) {
                                    TileAcceleratorMagnet magnet = (TileAcceleratorMagnet) acc.getWorld().getBlockEntity(pos);
                                    boolean validMagnet = false;
                                    for (String validSourceName : QMDServerConfig.mass_spectrometer_valid_magnets) {
                                        if (validSourceName.equals(magnet.magnetType.getName())) {
                                            validMagnet = true;
                                            break;
                                        }
                                    }
                                    if (!validMagnet) {
                                        multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.magnet_wrong_type");
                                        return false;
                                    }
                                    magnetType = acc.getWorld().getBlockEntity(pos).getClass();
                                } else if (!magnetType.isInstance(acc.getWorld().getBlockEntity(pos))) {
                                    multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_one_magnet_type");
                                    return false;
                                }
                            }
                        }

                    } else {
                        if (pos.getY() > acc.getMinInteriorY() && pos.getY() < acc.getMaxInteriorY() && pos.getX() > acc.getMinInteriorX() && pos.getX() < acc.getMaxInteriorX()) {
                            if (!(acc.getWorld().getBlockEntity(pos) instanceof TileAcceleratorYoke)) {
                                multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_yoke");
                                return false;
                            }
                        } else {
                            if (!(acc.getWorld().getBlockEntity(pos) instanceof TileAcceleratorMagnet)) {
                                multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_magnet");
                                return false;
                            } else {
                                if (magnetType == null) {
                                    TileAcceleratorMagnet magnet = (TileAcceleratorMagnet) acc.getWorld().getBlockEntity(pos);
                                    boolean validMagnet = false;
                                    for (String validSourceName : QMDServerConfig.mass_spectrometer_valid_magnets) {
                                        if (validSourceName.equals(magnet.magnetType.getName())) {
                                            validMagnet = true;
                                            break;
                                        }
                                    }
                                    if (!validMagnet) {
                                        multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.magnet_wrong_type");
                                        return false;
                                    }
                                    magnetType = acc.getWorld().getBlockEntity(pos).getClass();
                                } else if (!magnetType.isInstance(acc.getWorld().getBlockEntity(pos))) {
                                    multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_one_magnet_type");
                                    return false;
                                }
                            }
                        }
                    }
                }
            } else {
                for (BlockPos pos : layer) {
                    if (!(acc.getWorld().getBlockEntity(pos) instanceof TileAcceleratorBeam)) {
                        multiblock.setLastError(pos, QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_beam");
                        return false;
                    }
                }
            }
        }

        return super.isMachineWhole();
    }

    public Direction getWallNormal(BlockPos pos) {
        Accelerator acc = multiblock;
        if (pos.getY() == acc.getMaxY()) {
            return Direction.UP;
        } else if (pos.getY() == acc.getMinY()) {
            return Direction.DOWN;
        } else if (pos.getX() == acc.getMinX()) {
            return Direction.WEST;
        } else if (pos.getX() == acc.getMaxX()) {
            return Direction.EAST;
        } else if (pos.getZ() == acc.getMaxZ()) {
            return Direction.SOUTH;
        } else if (pos.getZ() == acc.getMinZ()) {
            return Direction.NORTH;
        }
        return null;
    }

    @Override
    public int getThickness() {
        return -1;
    }

    public static final List<Pair<Class<? extends IMultiblockPart<Accelerator>>, String>> PART_BLACKLIST = Lists.newArrayList(
            Pair.of(TileAcceleratorBeamPort.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_beam_ports"),
            Pair.of(TileAcceleratorSynchrotronPort.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_synch_ports"),
            Pair.of(TileAcceleratorRFCavity.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_rf_cavity"));

    @Override
    public List<Pair<Class<? extends IMultiblockPart<Accelerator>>, String>> getPartBlacklist() {
        return PART_BLACKLIST;
    }

    // Accelerator formation
    @Override
    public void onAcceleratorFormed() {
        Accelerator acc = multiblock;

        acc.tanks.get(2).setCapacity(QMDServerConfig.accelerator_base_input_tank_capacity * 1000);
        acc.tanks.get(2).setAllowedFluids(mass_spectrometer.getValidFluids(getWorld(), 0));
        acc.tanks.get(3).setCapacity(QMDServerConfig.accelerator_base_input_tank_capacity * 1000);
        acc.tanks.get(4).setCapacity(QMDServerConfig.accelerator_base_input_tank_capacity * 1000);
        acc.tanks.get(5).setCapacity(QMDServerConfig.accelerator_base_input_tank_capacity * 1000);
        acc.tanks.get(6).setCapacity(QMDServerConfig.accelerator_base_input_tank_capacity * 1000);


        if (!getWorld().isClientSide()) {
            resetBeams();
            speed = 0;
            for (TileAcceleratorIonSource source : getPartMap(TileAcceleratorIonSource.class).values()) {
                BlockPos sourcePos = source.getBlockPos();
                source.setIONumber(2);
                speed += source.outputParticleMultiplier;

                for (int i = 1; i <= 4; i++) {
                    if (acc.getWorld().getBlockEntity(sourcePos.above(i)) instanceof TileAcceleratorIonCollector) {
                        TileAcceleratorIonCollector collector = (TileAcceleratorIonCollector) multiblock.getWorld().getBlockEntity(sourcePos.above(i));
                        collector.setIONumber(i + 2);
                    }
                }
            }
        }

        refreshStats();
        super.onAcceleratorFormed();

        acc.cooling = (long) (2 * (acc.rawHeating + acc.getMaxExternalHeating()));
    }

    public void refreshStats() {
        Accelerator acc = multiblock;

        int energy = 0;
        long heat = 0;
        for (TileAcceleratorMagnet magnet : multiblock.getPartMap(TileAcceleratorMagnet.class).values()) {
            energy += magnet.magnetType.getBasePower() / 16;
            heat += magnet.magnetType.getHeatGenerated() / 16;
        }
        for (TileAcceleratorIonSource source : multiblock.getPartMap(TileAcceleratorIonSource.class).values()) {
            energy += source.basePower;
        }

        acc.requiredEnergy = energy;
        acc.rawHeating = heat;
        acc.dipoleStrength = 0;
        acc.quadrupoleStrength = 0;
        acc.efficiency = 1;
        acc.acceleratingVoltage = 0;
    }

    // Accelerator disassembly

    public void onAcceleratorBroken() {
        for (TileAcceleratorIonSource source : getPartMap(TileAcceleratorIonSource.class).values()) {
            source.setIONumber(0);
        }

        for (TileAcceleratorIonCollector collector : getPartMap(TileAcceleratorIonCollector.class).values()) {
            collector.setIONumber(0);
        }


        super.onAcceleratorBroken();
    }

    // Accelerator Operation

    @Override
    public boolean onUpdateServer() {
        super.onUpdateServer();
        if (multiblock.isControllorOn) {
            refreshRecipe();

            if (recipeInfo != null) {

                if (canProduceProduct()) {

                    if (multiblock.energyStorage.extractEnergy(multiblock.requiredEnergy,
                            true) == multiblock.requiredEnergy) {
                        internalHeating();

                        multiblock.energyStorage.changeEnergyStored(-multiblock.requiredEnergy);
                        workDone += speed;
                        produceProduct();
                    }
                }

            } else {
                workDone = 0;
            }
        } else {
            workDone = 0;
        }
        multiblock.sendMultiblockUpdatePacketToListeners();
        return true;
    }

    protected void operate() {
        if ((isRedstonePowered() && !multiblock.computerControlled) || (multiblock.computerControlled && multiblock.energyPercentage > 0)) {
            if (multiblock.getTemperature() <= multiblock.maxOperatingTemp) {
                operational = true;
                return;
            } else {
                if (operational) {
                    quenchMagnets();
                }
                operational = false;
                multiblock.errorCode = Accelerator.errorCode_ToHot;
                return;
            }
        } else {
            operational = false;
            return;
        }

    }

    // Recipe handling

    private boolean canProduceProduct() {
        TileMassSpectrometerController inv = (TileMassSpectrometerController) multiblock.controller;
        List<SizedChanceItemIngredient> productItems = recipeInfo.recipe.getItemProducts();
        List<SizedChanceFluidIngredient> productFluids = recipeInfo.recipe.getFluidProducts();

        for (int i = 0; i < productItems.size(); i++) {

            ItemStack stack = productItems.get(i).getStack();
            if (stack != null) {
                // some strange safety measure
                if (inv.getInventoryStacks().get(i + 2).getCount() <= 0) {
                    inv.getInventoryStacks().set(i + 2, ItemStack.EMPTY);
                }


                if (!ItemStack.isSameItemSameComponents(inv.getInventoryStacks().get(i + 2), stack) && inv.getInventoryStacks().get(i + 2) != ItemStack.EMPTY) {
                    return false;
                }

                if (inv.getInventoryStacks().get(i + 2).getCount() + stack.getCount() > stack.getMaxStackSize()) {
                    return false;
                }
            }
        }

        for (int i = 0; i < productFluids.size(); i++) {
            FluidStack stack = productFluids.get(i).getStack();
            if (stack != null) {
                if (multiblock.tanks.get(i + 3).fill(stack, SIMULATE) != stack.getAmount()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void produceProduct() {
        recipeWork = recipeInfo.recipe.getBaseProcessTime(QMDServerConfig.processor_time[2]);

        while (workDone >= recipeWork && canProduceProduct()) {

            TileMassSpectrometerController inv = (TileMassSpectrometerController) multiblock.controller;

            List<SizedChanceItemIngredient> productItems = recipeInfo.recipe.getItemProducts();
            for (int i = 0; i < productItems.size(); i++) {
                ItemStack productItem = productItems.get(i).getStack();

                if (productItem == null) {
                    productItem = ItemStack.EMPTY;
                } else {
                    productItem.setCount(productItems.get(i).count());
                }

                InventoryHelper.addItem(i + 2, productItem, inv.getInventoryStacks(), inv);

            }

            if (!recipeInfo.recipe.getItemIngredients().isEmpty()) {
                InventoryHelper.removeItem(0, recipeInfo.recipe.getItemIngredients().get(0).count(), inv.getInventoryStacks(), inv);
            }

            List<SizedChanceFluidIngredient> productFluids = recipeInfo.recipe.getFluidProducts();
            for (int i = 0; i < productFluids.size(); i++) {

                FluidStack productFluid = productFluids.get(i).getStack();
                if (productFluid != null) {
                    productFluid.setAmount(productFluids.get(i).amount());
                    multiblock.tanks.get(i + 3).fill(productFluid, EXECUTE);
                }

            }

            FluidStack ingredientFluid = !recipeInfo.recipe.getFluidIngredients().isEmpty() ? recipeInfo.recipe.getFluidIngredients().get(0).getStack() : null;
            if (ingredientFluid != null) {
                multiblock.tanks.get(2).drain(ingredientFluid, EXECUTE);
            }

            workDone = Math.max(0, workDone - recipeWork);
        }
    }

    protected void refreshRecipe() {
        TileMassSpectrometerController cont = (TileMassSpectrometerController) multiblock.controller;
        ArrayList<ItemStack> items = new ArrayList<>();
        ItemStack item = cont.getInventoryStacks().get(0).copy();
        items.add(item);
        ArrayList<Tank> tanks = new ArrayList<>();
        tanks.add(multiblock.tanks.get(2));

        recipeInfo = mass_spectrometer.getRecipeInfoFromInputs(getWorld(), items, tanks, new ArrayList<>());
    }

    // NBT

    @Override
    public void writeToLogicTag(CompoundTag logicTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        super.writeToLogicTag(logicTag, provider, syncReason);
        logicTag.putDouble("workDone", workDone);
        logicTag.putDouble("recipeWork", recipeWork);
        logicTag.putDouble("speed", speed);

    }

    @Override
    public void readFromLogicTag(CompoundTag logicTag, HolderLookup.Provider provider, ISyncableEntity.SyncReason syncReason) {
        super.readFromLogicTag(logicTag, provider, syncReason);
        workDone = logicTag.getDouble("workDone");
        recipeWork = logicTag.getDouble("recipeWork");
        speed = logicTag.getDouble("speed");
    }

    // Packets

    @Override
    public AcceleratorUpdatePacket getMultiblockUpdatePacket() {
        return new MassSpectrometerUpdatePacket(multiblock.controller.getTilePos(),
                multiblock.isControllorOn, multiblock.cooling, multiblock.rawHeating, multiblock.currentHeating, multiblock.maxCoolantIn, multiblock.maxCoolantOut, multiblock.maxOperatingTemp,
                multiblock.requiredEnergy, multiblock.efficiency, multiblock.acceleratingVoltage,
                multiblock.RFCavityNumber, multiblock.quadrupoleNumber, multiblock.quadrupoleStrength, multiblock.dipoleNumber, multiblock.dipoleStrength, multiblock.errorCode,
                multiblock.heatBuffer, multiblock.energyStorage, multiblock.tanks, multiblock.beams, workDone, recipeWork, speed);
    }

    @Override
    public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message) {
        super.onMultiblockUpdatePacket(message);
        if (message instanceof MassSpectrometerUpdatePacket) {
            MassSpectrometerUpdatePacket packet = (MassSpectrometerUpdatePacket) message;
            this.workDone = packet.workDone;
            this.recipeWork = packet.recipeWork;
            this.speed = packet.speed;
        }
    }
}