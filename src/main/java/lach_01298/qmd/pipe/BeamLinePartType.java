package lach_01298.qmd.pipe;

import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartTypeProperties;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

import static lach_01298.qmd.tile.QMDTiles.TILE_BEAMLINE;

public enum BeamLinePartType implements IPipePartType {
    BeamLine(() -> TILE_BEAMLINE::get, BlockBeamline::new, "");

    private final MultiblockPartTypeProperties<Pipe, IPipePartType> _properties;

    BeamLinePartType(final Supplier<@NotNull Supplier<@NotNull BlockEntityType<?>>> tileTypeSupplier,
                     final Function<MultiblockPartBlock.@NotNull MultiblockPartProperties<IPipePartType>, @NotNull MultiblockPartBlock<Pipe, IPipePartType>> blockFactory,
                     final String translationKey) {
        this._properties = new MultiblockPartTypeProperties<>(tileTypeSupplier, blockFactory, translationKey, bp -> bp, ppf -> ppf);
    }

    @Override
    public MultiblockPartTypeProperties<Pipe, IPipePartType> getPartTypeProperties() {
        return this._properties;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }
}
