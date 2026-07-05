package lach_01298.qmd.datamap;

import lach_01298.qmd.QMD;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber
public class QMDDatamaps {
    public static final DataMapType<Item, IrradiatorFuel> IRRADIATOR_FUELS = DataMapType.builder(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "irradiator_fuels"), Registries.ITEM, IrradiatorFuel.CODEC).synced(IrradiatorFuel.CODEC, true).build();

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(IRRADIATOR_FUELS);
    }
}
