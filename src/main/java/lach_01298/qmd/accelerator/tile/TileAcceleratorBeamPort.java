package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.ITickable;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.tile.ITileIONumber;
import lach_01298.qmd.tile.ITileIOType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static lach_01298.qmd.block.BlockProperties.IO;
import static lach_01298.qmd.tile.QMDTiles.TILE_ACCELERATOR_BEAM_PORT;

public class TileAcceleratorBeamPort extends TileAcceleratorPart implements ITileIOType, ITileIONumber, ITileParticleStorage, ITickable {
    private final @Nonnull List<ParticleStorageAccelerator> backupTanks = Lists.newArrayList(new ParticleStorageAccelerator());
    private IOType mode;
    private IOType setting;
    private boolean triggered = false;
    private boolean powered = false;
    private int IONumber;

    public TileAcceleratorBeamPort(final BlockPos position, final BlockState blockState) {
        super(TILE_ACCELERATOR_BEAM_PORT.get(), position, blockState, PartPosition.Type.Face);
        this.mode = IOType.DISABLED;
        this.setting = IOType.INPUT;
        this.IONumber = 0;
    }

    @Override
    public IOType getIOType() {

        return mode;
    }

    @Override
    public void setIOType(IOType type) {
        this.mode = type;
        level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(IO, type));
        markDirtyAndNotify(true);
    }

    /**
     * used for right clicking port
     */
    public void cycleMode() {
        setIOType(mode.getNextIO());
        getMultiblockController().get().checkIfMachineIsWhole();
    }

    public IOType getSetting() {
        return setting;
    }

    public void toggleSetting() {
        if (setting == IOType.INPUT) {
            setSettingType(IOType.OUTPUT);
        } else {
            setSettingType(IOType.INPUT);
        }
    }

    public void switchMode() {
        if (mode == setting) {
            setIOType(IOType.DISABLED);
        } else {
            setIOType(setting);
        }

        level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(IO, mode));
        markDirtyAndNotify(true);
    }

    public void setSettingType(IOType setting) {
        this.setting = setting;

        level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(IO, mode));
        markDirtyAndNotify(true);
    }

    @Override
    public boolean onUseMultitool(ItemStack multitool, ServerPlayer player, Level level, Direction facing, BlockPos hitPos) {
        if (player.isCrouching()) {
            toggleSetting();

            ChatFormatting format = switch (getSetting()) {
                case INPUT -> ChatFormatting.DARK_AQUA;
                case OUTPUT -> ChatFormatting.RED;
                default -> ChatFormatting.GRAY;
            };

            player.sendSystemMessage(Component.translatable("qmd.block.accelerator_port_setting_toggle",
                    Component.translatable("qmd.block.port_mode." + getSetting().name()).withStyle(format),
                    Component.translatable("qmd.block.port.mode").withStyle(ChatFormatting.WHITE)));
        } else {
            cycleMode();

            ChatFormatting format = switch (getIOType()) {
                case INPUT -> ChatFormatting.DARK_AQUA;
                case OUTPUT -> ChatFormatting.RED;
                default -> ChatFormatting.GRAY;
            };

            player.sendSystemMessage(Component.translatable("qmd.block.port_mode_toggle",
                    Component.translatable("qmd.block.port_mode." + getIOType().name()).withStyle(format),
                    Component.translatable("qmd.block.port.mode").withStyle(ChatFormatting.WHITE)));
        }

        return true;
    }

    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        nbt.putInt("mode", mode.getID());
        nbt.putInt("setting", setting.getID());
        nbt.putBoolean("triggered", triggered);
        nbt.putInt("IONumber", IONumber);
        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        mode = IOType.getTypeFromID(nbt.getInt("mode"));
        setting = IOType.getTypeFromID(nbt.getInt("setting"));
        triggered = nbt.getBoolean("triggered");
        IONumber = nbt.getInt("IONumber");
    }

    // Capability

    public ParticleStorage getCapability(@Nullable Direction side) {
        if (!getParticleBeams().isEmpty() && mode != IOType.DISABLED) {
            if (getParticleBeams().size() > IONumber) {
                return getParticleBeams().get(IONumber);
            }
        }
        return null;
    }

    @Override
    public List<? extends ParticleStorage> getParticleBeams() {
        if (!isMachineAssembled())
            return backupTanks;
        return getMultiblockController().get().beams;
    }

    public void setIONumber(int number) {
        if (number >= 0) {
            IONumber = number;
        }
    }

    public int getIONumber() {
        return IONumber;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void resetTrigger() {
        triggered = false;
    }

    public void setTrigger() {
        triggered = true;
    }

    @Override
    public void update() {
        if (!level.isClientSide()) { // TODO check this is called
            Accelerator multiblock = getMultiblockController().orElse(null);
            if (multiblock != null && multiblock.getLogic() != null) {
                if (getIsRedstonePowered() && !powered) {
                    setTrigger();
                    multiblock.getLogic().switchIO();
                }
            }
            powered = getIsRedstonePowered();
        }
    }
}
