package lach_01298.qmd.accelerator;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import net.minecraft.core.BlockPos;

public class DipoleMagnet {
    private final Accelerator accelerator;
    private BlockPos pos;

    private final Long2ObjectMap<IAcceleratorComponent> components = new Long2ObjectOpenHashMap<>();
    private MagnetType type;

    public DipoleMagnet(Accelerator accelerator, BlockPos pos) {
        this.accelerator = accelerator;
        this.pos = pos;

        setType();
        addComponents();
    }

    private void setType() {
        if (accelerator.getWorld().getBlockEntity(pos.above()) instanceof TileAcceleratorMagnet magnet) {
            type = magnet.magnetType;
        } else if (accelerator.getWorld().getBlockEntity(pos.north()) instanceof TileAcceleratorMagnet magnet) {
            type = magnet.magnetType;
        } else if (accelerator.getWorld().getBlockEntity(pos.east()) instanceof TileAcceleratorMagnet magnet) {
            type = magnet.magnetType;
        } else {
            QMD.LOGGER.error("could not set dipole type because {} at {} is not a instance of TileAcceleratorMagnet", accelerator.getWorld().getBlockEntity(pos.above()), pos.above());
        }
    }

    private void addComponents() {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    addComponent(pos.offset(x, y, z));
                }
            }
        }
    }

    private void addComponent(BlockPos postion) {
        if (accelerator.getWorld().getBlockEntity(postion) instanceof IAcceleratorComponent) {
            components.put(postion.asLong(), (IAcceleratorComponent) accelerator.getWorld().getBlockEntity(postion));
        } else {
            QMD.LOGGER.error("could not add tile to dipole because {} at {} is not a instance of IAcceleratorComponent", accelerator.getWorld().getBlockEntity(postion), postion);
        }
    }

    public BlockPos getPos() {
        return pos;
    }

    public Long2ObjectMap<IAcceleratorComponent> getComponents() {
        return components;
    }

    public MagnetType getType() {
        return type;
    }
}