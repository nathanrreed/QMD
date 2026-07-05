package lach_01298.qmd.capabilities;

import lach_01298.qmd.QMD;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.IParticleStorage;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorage;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public class CapabilityParticleStackHandler {
    public static final BlockCapability<IParticleStackHandler, @Nullable Direction> BLOCK = BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "particle"), IParticleStackHandler.class);

    // TODO why isn't this used
    private static class DefaultParticleHandlerStorage<T extends IParticleStackHandler> implements INBTSerializable<Tag> {
        @Override
        public Tag serializeNBT(HolderLookup.Provider provider) {
            if (!(provider instanceof IParticleStorage tank))
                throw new RuntimeException("IParticleStackHandler instance does not implement IParticleStorage");
            CompoundTag nbt = new CompoundTag();
            ParticleStack particles = tank.getParticleStack();
            if (particles != null) {
                particles.writeToNBT(nbt);
            } else {
                nbt.putString("Empty", "");
            }
            nbt.putLong("MaxEnergy", tank.getMaxEnergy());
            nbt.putInt("Capacity", tank.getCapacity());
            nbt.putLong("MinEnergy", tank.getMinEnergy());
            return nbt;
        }

        @Override
        public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
            if (!(provider instanceof ParticleStorage tank))
                throw new RuntimeException("IParticleStackHandler instance is not instance of ParticleStorage");
            CompoundTag tags = (CompoundTag) nbt;
            tank.setMaxEnergy(tags.getLong("MaxEnergy"));
            tank.setCapacity(tags.getInt("Capacity"));
            tank.setMinEnergy(tags.getLong("MinEnergy"));
            tank.readFromNBT(tags);
        }
    }
}
