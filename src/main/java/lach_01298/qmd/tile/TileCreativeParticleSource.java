package lach_01298.qmd.tile;

import com.google.common.collect.Lists;
import com.nred.nuclearcraft.block_entity.ITickable;
import com.nred.nuclearcraft.block_entity.ITileGui;
import com.nred.nuclearcraft.block_entity.NCTile;
import com.nred.nuclearcraft.handler.BlockEntityInfoHandler;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lach_01298.qmd.QMD;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.machine.container.MachineMenuImpl.CreativeParticleSourceMenu;
import lach_01298.qmd.machine.network.CreativeParticleSourceUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static lach_01298.qmd.tile.QMDTiles.CREATIVE_PARTICLE_SOURCE_ENTITY_TYPE;

public class TileCreativeParticleSource extends NCTile implements ITileParticleStorage, ITickable, ITileGui<TileCreativeParticleSource, CreativeParticleSourceUpdatePacket, BlockEntityMenuInfo<TileCreativeParticleSource>> {
    protected final BlockEntityMenuInfo<TileCreativeParticleSource> info = BlockEntityInfoHandler.getTileContainerInfo("creative_particle_source");
    private final @Nonnull List<ParticleStorageSource> particleBeams;

    protected Set<Player> playersToUpdate;

    public TileCreativeParticleSource(BlockPos pos, BlockState blockState) {
        super(CREATIVE_PARTICLE_SOURCE_ENTITY_TYPE.get(), pos, blockState);

        playersToUpdate = new ObjectOpenHashSet<>();
        particleBeams = Lists.newArrayList(new ParticleStorageSource());
    }

    @Override
    public CompoundTag writeAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.writeAll(nbt, registries);
        writeBeams(particleBeams, nbt, registries);

        return nbt;
    }

    @Override
    public void readAll(CompoundTag nbt, HolderLookup.Provider registries) {
        super.readAll(nbt, registries);
        readBeams(particleBeams, nbt, registries);
    }

    // Capability

    public IParticleStackHandler getCapability(@Nullable Direction side) {
        if (!getParticleBeams().isEmpty()) {
            return getParticleBeams().get(0);
        }
        return null;
    }

    @Override
    public void update() {
        if (!level.isClientSide()) {
            if (!getIsRedstonePowered()) {
                for (Direction face : Direction.values()) {
                    BlockEntity tile = level.getBlockEntity(worldPosition.relative(face));
                    if (tile != null) {
                        IParticleStackHandler otherStorage = level.getCapability(CapabilityParticleStackHandler.BLOCK, tile.getBlockPos(), face.getOpposite());
                        if (otherStorage != null) {
                            otherStorage.reciveParticle(face.getOpposite(), this.particleBeams.get(0).getParticleStack());
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<? extends ParticleStorage> getParticleBeams() {
        return particleBeams;
    }

    public String getParticleName() {
        if (particleBeams.get(0).getParticleStack() != null) {
            if (particleBeams.get(0).getParticleStack().getParticle() != null) {
                return particleBeams.get(0).getParticleStack().getParticle().getName();
            }
        }

        return "";
    }

    public int getParticleAmount() {
        if (particleBeams.get(0).getParticleStack() != null) {
            return particleBeams.get(0).getParticleStack().getAmount();
        }

        return 0;
    }

    public long getParticleEnergy() {
        if (particleBeams.get(0).getParticleStack() != null) {
            return particleBeams.get(0).getParticleStack().getMeanEnergy();
        }

        return 0;
    }

    public double getParticleFocus() {
        if (particleBeams.get(0).getParticleStack() != null) {
            return particleBeams.get(0).getParticleStack().getFocus();
        }

        return 0;
    }

    @Override
    public BlockEntityMenuInfo<TileCreativeParticleSource> getContainerInfo() {
        return info;
    }

    @Override
    public Set<Player> getTileUpdatePacketListeners() {
        return playersToUpdate;
    }

    @Override
    public CreativeParticleSourceUpdatePacket getTileUpdatePacket() {
        return new CreativeParticleSourceUpdatePacket(worldPosition, particleBeams);
    }

    @Override
    public void onTileUpdatePacket(CreativeParticleSourceUpdatePacket message) {
        for (int i = 0; i < message.beams.size(); i++) {
            particleBeams.set(i, message.beams.get(i));
        }
    }

    public CompoundTag writeBeams(List<ParticleStorageSource> beams, CompoundTag data, HolderLookup.Provider registries) {
        for (int i = 0; i < beams.size(); i++) {
            beams.get(i).writeToNBT(data, i);
        }

        return data;
    }

    public void readBeams(List<ParticleStorageSource> beams, CompoundTag data, HolderLookup.Provider registries) {
        for (int i = 0; i < beams.size(); i++) {
            beams.get(i).readFromNBT(data, i);
        }
    }

    public void setParticleBeams(List<ParticleStorageSource> beams) {
        for (int i = 0; i < beams.size(); i++) {
            particleBeams.set(i, beams.get(i));
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(QMD.MOD_ID + ".menu.title.creative_particle_source");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new CreativeParticleSourceMenu(containerId, playerInventory, this);
    }
}