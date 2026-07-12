package lach_01298.qmd.accelerator.tile;

import com.nred.nuclearcraft.block_entity.energy.ITileEnergy;
import com.nred.nuclearcraft.block_entity.internal.energy.EnergyConnection;
import com.nred.nuclearcraft.block_entity.internal.energy.EnergyStorage;
import com.nred.nuclearcraft.block_entity.internal.energy.EnergyTileWrapper;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_ENERGY_PORT;

public class TileAcceleratorEnergyPort extends TileAcceleratorPart implements ITileEnergy {
    protected final EnergyStorage backupStorage = new EnergyStorage(1);

    protected final EnergyConnection[] energyConnections = ITileEnergy.energyConnectionAll(EnergyConnection.IN);
    protected final EnergyTileWrapper[] energySides = ITileEnergy.getDefaultEnergySides(this);

    public TileAcceleratorEnergyPort(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_ENERGY_PORT.get(), position, blockState, PartPosition.Type.Face);
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        if (!isMachineAssembled()) {
            return backupStorage;
        }
        return getMultiblockController().get().energyStorage;
    }

    @Override
    public EnergyConnection[] getEnergyConnections() {
        return energyConnections;
    }

    @Override
    public EnergyTileWrapper[] getEnergySides() {
        return energySides;
    }

    // NBT

    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        writeEnergy(nbt, registries);
        writeEnergyConnections(nbt, registries);
        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        readEnergy(nbt, registries);
        readEnergyConnections(nbt, registries);
    }
}