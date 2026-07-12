package lach_01298.qmd.accelerator.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_RF_CAVITY;

public class TileAcceleratorRFCavity extends TileAcceleratorPart implements IAcceleratorComponent {
    public final RFCavityType rfCavityType;

    public boolean isFunctional = false;

    public TileAcceleratorRFCavity(final BlockPos position, final BlockState blockState, RFCavityType rfCavityType) {
        super(TILE_ACCELERATOR_RF_CAVITY.get(), position, blockState, PartPosition.Type.Interior);
        this.rfCavityType = rfCavityType;
    }

    @Override
    public boolean isFunctional() {
        return isFunctional;
    }

    @Override
    public void setFunctional(boolean func) {
        isFunctional = func;
    }


    @Override
    public int getMaxOperatingTemp() {
        return rfCavityType.getMaxOperatingTemp();
    }

    // NBT
    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        nbt.putBoolean("isFunctional", isFunctional);
        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        isFunctional = nbt.getBoolean("isFunctional");
    }
}