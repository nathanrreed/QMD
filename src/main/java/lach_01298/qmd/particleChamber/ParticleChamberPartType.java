package lach_01298.qmd.particleChamber;

import com.nred.nuclearcraft.block.GenericActiveDirectionalTooltipDeviceBlock;
import com.nred.nuclearcraft.block.GenericTooltipDeviceBlock;
import it.zerono.mods.zerocore.base.multiblock.part.GlassBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartTypeProperties;
import lach_01298.qmd.multiblock.block.GenericIOTooltipDeviceBlock;
import lach_01298.qmd.multiblock.block.MultiblockCasing;
import lach_01298.qmd.particleChamber.tile.IParticleChamberPartType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

import static lach_01298.qmd.tile.QMDTiles.*;

public enum ParticleChamberPartType implements IParticleChamberPartType {
    TargetChamberController(() -> TILE_TARGET_CHAMBER_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    DecayChamberController(() -> TILE_DECAY_CHAMBER_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    BeamDumpController(() -> TILE_BEAM_DUMP_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    CollisionChamberController(() -> TILE_COLLISION_CHAMBER_CONTROLLER::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    ParticleChamberBeam(() -> TILE_PARTICLE_CHAMBER_BEAM::get, GenericTooltipDeviceBlock::new),
    ParticleChamberCasing(() -> TILE_PARTICLE_CHAMBER_CASING::get, MultiblockCasing::new),
    ParticleChamberGlass(() -> TILE_PARTICLE_CHAMBER_GLASS::get, GlassBlock::new, GlassBlock::addGlassProperties),
    ParticleChamberEnergyPort(() -> TILE_PARTICLE_CHAMBER_ENERGY_PORT::get, GenericTooltipDeviceBlock::new),
    ParticleChamber(() -> TILE_PARTICLE_CHAMBER::get, GenericTooltipDeviceBlock::new),
    ParticleChamberPort(() -> TILE_PARTICLE_CHAMBER_PORT::get, GenericTooltipDeviceBlock::new),
    ParticleChamberDetector(() -> TILE_PARTICLE_CHAMBER_DETECTOR::get, GenericTooltipDeviceBlock::new),
    ParticleChamberBeamPort(() -> TILE_PARTICLE_CHAMBER_BEAM_PORT::get, GenericIOTooltipDeviceBlock::new),
    ParticleChamberFluidPort(() -> TILE_PARTICLE_CHAMBER_FLUID_PORT::get, GenericActiveDirectionalTooltipDeviceBlock::new),
    ;

    private final MultiblockPartTypeProperties<ParticleChamber, IParticleChamberPartType> _properties;

    ParticleChamberPartType(final Supplier<@NotNull Supplier<@NotNull BlockEntityType<?>>> tileTypeSupplier,
                            final Function<MultiblockPartBlock.@NotNull MultiblockPartProperties<IParticleChamberPartType>, @NotNull MultiblockPartBlock<ParticleChamber, IParticleChamberPartType>> blockFactory,
                            final Function<Block.@NotNull Properties, Block.@NotNull Properties> blockPropertiesFixer) {
        this._properties = new MultiblockPartTypeProperties<>(tileTypeSupplier, blockFactory, "", blockPropertiesFixer, ppf -> ppf);
    }

    ParticleChamberPartType(final Supplier<@NotNull Supplier<@NotNull BlockEntityType<?>>> tileTypeSupplier,
                            final Function<MultiblockPartBlock.@NotNull MultiblockPartProperties<IParticleChamberPartType>, @NotNull MultiblockPartBlock<ParticleChamber, IParticleChamberPartType>> blockFactory) {
        this._properties = new MultiblockPartTypeProperties<>(tileTypeSupplier, blockFactory, "", bp -> bp, ppf -> ppf);
    }

    @Override
    public MultiblockPartTypeProperties<ParticleChamber, IParticleChamberPartType> getPartTypeProperties() {
        return this._properties;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }
}