package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.ITickable;
import com.nred.nuclearcraft.block_entity.fluid.ITileFluid;
import com.nred.nuclearcraft.block_entity.internal.fluid.*;
import com.nred.nuclearcraft.block_entity.passive.ITilePassive;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.block.BlockAcceleratorVent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

import static com.nred.nuclearcraft.NuclearcraftNeohaul.MODID;
import static com.nred.nuclearcraft.registration.BlockRegistration.FACING_ALL;
import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_VENT;
import static net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;
import static net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction.SIMULATE;

public class TileAcceleratorVent extends TileAcceleratorPart implements ITileFluid, ITickable {
    private final @Nonnull List<Tank> backupTanks = Lists.newArrayList(new Tank(1, new HashSet<>()),
            new Tank(1, new HashSet<>()));

    private @Nonnull FluidConnection[] fluidConnections = ITileFluid.fluidConnectionAll(Lists.newArrayList(TankSorption.IN, TankSorption.NON));

    private @Nonnull FluidTileWrapper[] fluidSides;

    public TileAcceleratorVent(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_VENT.get(), position, blockState, PartPosition.Type.Face);
        fluidSides = ITileFluid.getDefaultFluidSides(this);
    }

    @Override
    public void onPreMachineAssembled(Accelerator multiblock) {
        super.onPreMachineAssembled(multiblock);
        if (!level.isClientSide() && getPartPosition().getDirection().isPresent()) {
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(FACING_ALL, getPartPosition().getDirection().get()), 2);
        }
    }

    public void updateBlockState(boolean isActive) {
        if (getBlockType() instanceof BlockAcceleratorVent) {
            ((BlockAcceleratorVent) getBlockType()).setActivity(isActive, this);
        }
    }

    @Override
    public void update() {
        Direction facing = getPartPosition().getDirection().orElse(null);
        if (!level.isClientSide() && !getTanks().get(1).isEmpty() && facing != null && getTankSorption(facing, 1).canDrain()) {
            pushFluidToSide(facing);
        }
    }

    // Fluids

    @Override
    public @Nonnull List<Tank> getTanks() {
        Accelerator multiblock = getMultiblockController().orElse(null);
        return multiblock != null ? multiblock.tanks.subList(0, 2) : backupTanks;
    }

    @Override
    @Nonnull
    public FluidConnection[] getFluidConnections() {
        return fluidConnections;
    }

    @Override
    public void setFluidConnections(@Nonnull FluidConnection[] connections) {
        fluidConnections = connections;
    }

    @Override
    @Nonnull
    public FluidTileWrapper[] getFluidSides() {
        return fluidSides;
    }

    @Override
    public ChemicalTileWrapper[] getChemicalSides() {
        return null;
    }

    @Override
    public void pushFluidToSide(@Nonnull Direction side) {
        BlockEntity tile = getTileWorld().getBlockEntity(getTilePos().relative(side));
        if (tile == null || tile instanceof TileAcceleratorVent)
            return;

        if (tile instanceof ITilePassive)
            if (!((ITilePassive) tile).canPushFluidsTo())
                return;

        IFluidHandler adjStorage = level.getCapability(Capabilities.FluidHandler.BLOCK, tile.getBlockPos(), side.getOpposite());
        if (adjStorage == null)
            return;

        for (int i = 0; i < getTanks().size(); i++) {
            if (getTanks().get(i).getFluid().isEmpty() || !getTankSorption(side, i).canDrain())
                continue;

            getTanks().get(i).drain(adjStorage.fill(getTanks().get(i).drain(getTanks().get(i).getCapacity(), SIMULATE), EXECUTE), EXECUTE);
        }
    }

    @Override
    public boolean getInputTanksSeparated() {
        return false;
    }

    @Override
    public void setInputTanksSeparated(boolean separated) {
    }

    @Override
    public boolean getVoidUnusableFluidInput(int tankNumber) {
        return false;
    }

    @Override
    public void setVoidUnusableFluidInput(int tankNumber, boolean voidUnusableFluidInput) {
    }

    @Override
    public TankOutputSetting getTankOutputSetting(int tankNumber) {
        return TankOutputSetting.DEFAULT;
    }

    @Override
    public void setTankOutputSetting(int tankNumber, TankOutputSetting setting) {
    }

    @Override
    public boolean hasConfigurableFluidConnections() {
        return true;
    }

    // IMultitoolLogic


    @Override
    public boolean onUseMultitool(ItemStack multitool, ServerPlayer player, Level level, Direction facing, BlockPos hitPos) {
        if (!player.isCrouching()) {
            Accelerator multiblock = getMultiblockController().orElse(null);
            if (multiblock != null) {
                if (getTankSorption(facing, 0) != TankSorption.IN) {
                    for (Direction side : Direction.values()) {
                        setTankSorption(side, 0, TankSorption.IN);
                        setTankSorption(side, 1, TankSorption.NON);
                    }
                    updateBlockState(false);
                    multiblock.checkIfMachineIsWhole();
                    player.sendSystemMessage(Component.translatable(MODID + ".tooltip.vent_toggle", Component.translatable(MODID + ".tooltip.in_config").withStyle(ChatFormatting.DARK_AQUA)));
                } else {
                    for (Direction side : Direction.values()) {
                        setTankSorption(side, 0, TankSorption.NON);
                        setTankSorption(side, 1, TankSorption.OUT);
                    }
                    updateBlockState(true);
                    multiblock.checkIfMachineIsWhole();
                    player.sendSystemMessage(Component.translatable(MODID + ".tooltip.vent_toggle", Component.translatable(MODID + ".tooltip.out_config").withStyle(ChatFormatting.RED)));
                }
                markDirtyAndNotify();
                return true;
            }
        }
        return super.onUseMultitool(multitool, player, level, facing, hitPos);
    }

    // NBT

    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        writeFluidConnections(nbt, registries);
        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        readFluidConnections(nbt, registries);
    }
}