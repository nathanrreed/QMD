package lach_01298.qmd.accelerator.tile;

import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.block.BlockAcceleratorRedstonePort;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.nred.nuclearcraft.registration.BlockRegistration.ACTIVE;
import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_REDSTONE_PORT;

public class TileAcceleratorRedstonePort extends TileAcceleratorPart {
    private int redstoneLevel = 0;

    public TileAcceleratorRedstonePort(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_REDSTONE_PORT.get(), position, blockState, PartPosition.Type.Face);
    }

    public void updateBlockState(boolean isActive) {
        if (getBlockType() instanceof BlockAcceleratorRedstonePort) {
            setActivity(isActive);
        }
    }

    // IMultitoolLogic

    @Override
    public boolean onUseMultitool(ItemStack multitool, ServerPlayer player, Level level, Direction facing, BlockPos hitPos) {
        if (!player.isCrouching()) {
            Accelerator multiblock = getMultiblockController().orElse(null);
            if (level.getBlockState(worldPosition).getValue(ACTIVE)) {
                setRedstoneLevel(0);
                updateBlockState(false);
                multiblock.checkIfMachineIsWhole();
                player.sendSystemMessage(Component.translatable("qmd.block.redstone_port_toggle",
                        Component.translatable("qmd.block.redstone_port_toggle.input").withStyle(ChatFormatting.DARK_AQUA),
                        Component.translatable("qmd.block.redstone_port_toggle.mode").withStyle(ChatFormatting.WHITE)));
            } else {
                setRedstoneLevel(0);
                updateBlockState(true);
                multiblock.checkIfMachineIsWhole();
                player.sendSystemMessage(Component.translatable("qmd.block.redstone_port_toggle",
                        Component.translatable("qmd.block.redstone_port_toggle.output").withStyle(ChatFormatting.RED),
                        Component.translatable("qmd.block.redstone_port_toggle.mode").withStyle(ChatFormatting.WHITE)));
            }
            markDirtyAndNotify();
            return true;
        }
        return super.onUseMultitool(multitool, player, level, facing, hitPos);
    }

    // NBT

    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        nbt.putInt("redstoneLevel", redstoneLevel);
        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        redstoneLevel = nbt.getInt("redstoneLevel");
    }

    public int getRedstoneLevel() {
        return redstoneLevel;
    }

    public void setRedstoneLevel(int redstoneLevel) {
        if (this.redstoneLevel != redstoneLevel) {
            this.redstoneLevel = Mth.clamp(redstoneLevel, 0, 15);
            this.level.updateNeighbourForOutputSignal(worldPosition, getBlockType());
        }
    }
}