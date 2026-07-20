package lach_01298.qmd.particleChamber.tile;

import com.google.common.collect.Lists;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.CollisionChamberLogic;
import lach_01298.qmd.tile.ITileIONumber;
import lach_01298.qmd.tile.ITileIOType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static lach_01298.qmd.block.BlockProperties.IO;
import static lach_01298.qmd.tile.QMDTiles.TILE_PARTICLE_CHAMBER_BEAM_PORT;

public class TileParticleChamberBeamPort extends TileParticleChamberPart implements ITileIOType, ITileIONumber, ITileParticleStorage {
    private final @Nonnull List<ParticleStorageAccelerator> backupTanks = Lists.newArrayList(new ParticleStorageAccelerator(), new ParticleStorageAccelerator(), new ParticleStorageAccelerator(), new ParticleStorageAccelerator());
    private IOType mode;
    private int IONumber;

    public TileParticleChamberBeamPort(BlockPos position, BlockState blockState) {
        super(TILE_PARTICLE_CHAMBER_BEAM_PORT.get(), position, blockState);
        this.mode = IOType.INPUT;
        this.IONumber = 0;

    }

    @Override
    public boolean isGoodForPosition(PartPosition position, IMultiblockValidator validatorCallback) {
        return position.isFace();
    }

    @Override
    public IOType getIOType() {
        return mode;
    }

    @Override
    public void setIOType(IOType type) {
        this.mode = type;
    }

    public void cycleMode() {
        setIOType(mode.getNextIO());
        level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(IO, mode));
        markDirtyAndNotify();
        getMultiblockController().get().checkIfMachineIsWhole();
    }

    public boolean toggleSetting() {
        if (isMachineAssembled()) {
            return getMultiblockController().get().toggleSetting(worldPosition, IONumber);
        }

        return false;
    }

    @Override
    public boolean onUseMultitool(net.minecraft.world.item.ItemStack multitool, ServerPlayer player, Level level, Direction facing, BlockPos hitPos) {
        if (player.isCrouching()) {
            if (toggleSetting()) {
                int inputNumberOffset = 0;
                if (getMultiblockController().get().getLogic() instanceof CollisionChamberLogic) {
                    inputNumberOffset = 1;
                }

                player.sendSystemMessage(Component.translatable("qmd.block.particle_chamber_port_setting_toggle", Component.literal("" + (getIONumber() - inputNumberOffset)).withStyle(ChatFormatting.LIGHT_PURPLE)));
            } else {
                return false;
            }
        } else {
            cycleMode();

            ChatFormatting format = switch (getIOType()) {
                case INPUT -> ChatFormatting.DARK_AQUA;
                case OUTPUT -> ChatFormatting.RED;
                default -> ChatFormatting.GRAY;
            };

            player.sendSystemMessage(Component.translatable("qmd.block.port_mode_toggle", Component.translatable("qmd.block.port_mode." + getIOType().name()).withStyle(format), Component.translatable("qmd.block.port.mode").withStyle(ChatFormatting.WHITE)));
        }
        return true;
    }

    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        nbt.putInt("mode", mode.getID());
        nbt.putInt("IONumber", IONumber);
        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        mode = IOType.getTypeFromID(nbt.getInt("mode"));
        IONumber = nbt.getInt("IONumber");
    }

    // Capability
    public ParticleStorage getCapability(@Nullable Direction side) {
        if (!getParticleBeams().isEmpty()) {
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
}