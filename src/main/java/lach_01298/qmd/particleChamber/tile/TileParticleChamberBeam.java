package lach_01298.qmd.particleChamber.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import static lach_01298.qmd.tile.QMDTiles.TILE_PARTICLE_CHAMBER_BEAM;

public class TileParticleChamberBeam extends TileParticleChamberPart implements IParticleChamberComponent {
    private boolean isFunctional;

    public TileParticleChamberBeam(BlockPos pos, BlockState state) {
        super(TILE_PARTICLE_CHAMBER_BEAM.get(), pos, state);
        isFunctional = false;
    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return position == PartPosition.Interior;
    }

    @Override
    public boolean isFunctional() {
        return isFunctional && this.isMachineAssembled();
    }

    @Override
    public void setFunctional(boolean func) {
        isFunctional = func;
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

    @Override
    public void resetStats() {
        isFunctional = false;
    }
}