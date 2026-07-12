package lach_01298.qmd.accelerator;

import com.nred.nuclearcraft.block.GenericActiveDirectionalTooltipDeviceBlock;
import com.nred.nuclearcraft.block.GenericDirectionalTooltipDeviceBlock;
import com.nred.nuclearcraft.block.GenericTooltipDeviceBlock;
import it.zerono.mods.zerocore.base.multiblock.part.GlassBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartTypeProperties;
import lach_01298.qmd.accelerator.block.*;
import lach_01298.qmd.accelerator.tile.IAcceleratorPartType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

import static lach_01298.qmd.tile.QMDTiles.*;

public enum AcceleratorPartType implements IAcceleratorPartType {
    LinearAcceleratorController(() -> TILE_LINEAR_ACCELERATOR_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    RingAcceleratorController(() -> TILE_RING_ACCELERATOR_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    BeamDiverterController(() -> TILE_BEAM_DIVERTER_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    BeamSplitterController(() -> TILE_BEAM_SPLITTER_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    DeceleratorController(() -> TILE_DECELERATOR_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    MassSpectrometerController(() -> TILE_MASS_SPECTROMETER_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    AcceleratorBeam(() -> TILE_ACCELERATOR_BEAM::get, GenericTooltipDeviceBlock::new),
    AcceleratorCasing(() -> TILE_ACCELERATOR_CASING::get, BlockAcceleratorCasing::new),
    AcceleratorGlass(() -> TILE_ACCELERATOR_GLASS::get, GlassBlock::new),
    AcceleratorVent(() -> TILE_ACCELERATOR_VENT::get, BlockAcceleratorVent::new),
    AcceleratorBeamPort(() -> TILE_ACCELERATOR_BEAM_PORT::get, BlockAcceleratorBeamPort::new),
    AcceleratorSynchrotronPort(() -> TILE_ACCELERATOR_SYNCHROTRON_PORT::get, GenericTooltipDeviceBlock::new),
    AcceleratorSource(() -> TILE_ACCELERATOR_ION_SOURCE_BASIC::get, BlockAcceleratorSource::new),
    AcceleratorLaserIonSource(() -> TILE_ACCELERATOR_ION_SOURCE_LASER::get, BlockAcceleratorSource::new),
    AcceleratorIonCollector(() -> TILE_ACCELERATOR_ION_COLLECTOR::get, GenericDirectionalTooltipDeviceBlock::new),
    AcceleratorYoke(() -> TILE_ACCELERATOR_YOKE::get, GenericTooltipDeviceBlock::new),
    AcceleratorCooler(() -> TILE_ACCELERATOR_COOLER::get, GenericTooltipDeviceBlock::new),
    AcceleratorMagnet(() -> TILE_ACCELERATOR_MAGNET::get, GenericTooltipDeviceBlock::new),
    RFCavity(() -> TILE_ACCELERATOR_RF_CAVITY::get, GenericTooltipDeviceBlock::new),

    AcceleratorEnergyPort(() -> TILE_ACCELERATOR_ENERGY_PORT::get, GenericTooltipDeviceBlock::new),
    AcceleratorComputerPort(() -> TILE_ACCELERATOR_COMPUTER_PORT::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    AcceleratorRedstonePort(() -> TILE_ACCELERATOR_REDSTONE_PORT::get, BlockAcceleratorRedstonePort::new),
    AcceleratorPort(() -> TILE_ACCELERATOR_PORT::get, GenericTooltipDeviceBlock::new),
    ;

    private final MultiblockPartTypeProperties<Accelerator, IAcceleratorPartType> _properties;

    AcceleratorPartType(final Supplier<@NotNull Supplier<@NotNull BlockEntityType<?>>> tileTypeSupplier,
                        final Function<MultiblockPartBlock.@NotNull MultiblockPartProperties<IAcceleratorPartType>, @NotNull MultiblockPartBlock<Accelerator, IAcceleratorPartType>> blockFactory,
                        final Function<Block.@NotNull Properties, Block.@NotNull Properties> blockPropertiesFixer) {
        this._properties = new MultiblockPartTypeProperties<>(tileTypeSupplier, blockFactory, "", blockPropertiesFixer, ppf -> ppf);
    }

    AcceleratorPartType(final Supplier<@NotNull Supplier<@NotNull BlockEntityType<?>>> tileTypeSupplier,
                        final Function<MultiblockPartBlock.@NotNull MultiblockPartProperties<IAcceleratorPartType>, @NotNull MultiblockPartBlock<Accelerator, IAcceleratorPartType>> blockFactory) {
        this._properties = new MultiblockPartTypeProperties<>(tileTypeSupplier, blockFactory, "", bp -> bp, ppf -> ppf);
    }

    @Override
    public MultiblockPartTypeProperties<Accelerator, IAcceleratorPartType> getPartTypeProperties() {
        return this._properties;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }
}