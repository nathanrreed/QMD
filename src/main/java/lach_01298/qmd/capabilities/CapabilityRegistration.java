package lach_01298.qmd.capabilities;

import com.nred.nuclearcraft.block_entity.energy.ITileEnergy;
import com.nred.nuclearcraft.block_entity.fluid.ITileFluid;
import com.nred.nuclearcraft.block_entity.inventory.ITileInventory;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.pipe.TileBeamline;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import java.util.List;

import static lach_01298.qmd.tile.QMDTiles.*;

@EventBusSubscriber(modid = QMD.MOD_ID)
public class CapabilityRegistration {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
//  TODO      event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, side) -> new ComponentEnergyStorage(stack, ENERGY_COMPONENT.get(), (int) ((IChargeableComponentItem) stack.getItem()).getMaxEnergyStored(stack), ((IChargeableComponentItem) stack.getItem()).getMaxTransfer(stack)), basic_drill, advanced_drill);
//        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, side) -> new ComponentEnergyStorage(stack, ENERGY_COMPONENT.get(), (int) ((IChargeableComponentItem) stack.getItem()).getMaxEnergyStored(stack), ((IChargeableComponentItem) stack.getItem()).getMaxTransfer(stack)), helm_hev, chest_hev, legs_hev, boots_hev);

        for (var type : List.of(ORE_LEACHER_ENTITY_TYPE, IRRADIATOR_ENTITY_TYPE)) {
            event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, type.get(), ITileInventory::getItemSideCapability);
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, type.get(), ITileEnergy::getEnergySideCapability);
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, type.get(), ITileFluid::getFluidSideCapability);
        }

        for (var type : List.of(ATMOSPHERE_COLLECTOR_ENTITY_TYPE, LIQUID_COLLECTOR_ENTITY_TYPE)) {
            event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, type.get(), ITileEnergy::getEnergySideCapability);
            event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, type.get(), ITileFluid::getFluidSideCapability);
        }

        event.registerBlockEntity(CapabilityParticleStackHandler.BLOCK, CREATIVE_PARTICLE_SOURCE_ENTITY_TYPE.get(), TileCreativeParticleSource::getCapability);
        event.registerBlockEntity(CapabilityParticleStackHandler.BLOCK, TILE_ACCELERATOR_BEAM_PORT.get(), TileAcceleratorBeamPort::getCapability);
        event.registerBlockEntity(CapabilityParticleStackHandler.BLOCK, TILE_BEAMLINE.get(), TileBeamline::getCapability);

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, TILE_ACCELERATOR_ENERGY_PORT.get(), TileAcceleratorEnergyPort::getEnergySideCapability);

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, TILE_ACCELERATOR_ION_COLLECTOR.get(), TileAcceleratorIonCollector::getFluidSideCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TILE_ACCELERATOR_ION_COLLECTOR.get(), TileAcceleratorIonCollector::getItemSideCapability);

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, TILE_ACCELERATOR_ION_SOURCE_BASIC.get(), TileAcceleratorIonSource.Basic::getFluidSideCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TILE_ACCELERATOR_ION_SOURCE_BASIC.get(), TileAcceleratorIonSource.Basic::getItemSideCapability);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, TILE_ACCELERATOR_ION_SOURCE_LASER.get(), TileAcceleratorIonSource.Laser::getFluidSideCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TILE_ACCELERATOR_ION_SOURCE_LASER.get(), TileAcceleratorIonSource.Laser::getItemSideCapability);

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, TILE_ACCELERATOR_PORT.get(), TileAcceleratorPort::getFluidSideCapability);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TILE_ACCELERATOR_PORT.get(), TileAcceleratorPort::getItemSideCapability);

        event.registerBlockEntity(CapabilityParticleStackHandler.BLOCK, TILE_ACCELERATOR_SYNCHROTRON_PORT.get(), TileAcceleratorSynchrotronPort::getCapability);

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, TILE_ACCELERATOR_VENT.get(), TileAcceleratorVent::getFluidSideCapability);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, TILE_MASS_SPECTROMETER_CONTROLLER.get(), TileMassSpectrometerController::getItemSideCapability);

    }
}