package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.helpers.Location;
import com.nred.nuclearcraft.info.NCFluid;
import lach_01298.qmd.QMD;
import lach_01298.qmd.enums.MaterialTypes.LuminousPaintType;
import lach_01298.qmd.fluid.QMDFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Map;
import java.util.stream.Stream;

import static com.nred.nuclearcraft.helpers.Concat.fluidEntries;
import static lach_01298.qmd.enums.MaterialTypes.PartType.*;
import static lach_01298.qmd.item.QMDItems.*;

public class QMDItemModelProvider extends ItemModelProvider {
    public QMDItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, QMD.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Map.Entry<? extends StringRepresentable, DeferredItem<Item>> entry : Stream.of(dusts, ingots, ingotAlloys, sources, isotopes, semiconductors, chemicalDusts, fissionWastes, spallationWastes, cells).flatMap(e -> e.entrySet().stream()).toList()) {
            simpleItem(entry.getValue(), entry.getKey().getSerializedName(), entry.getValue().getId().getPath().replaceFirst("^" + entry.getKey().getSerializedName() + "_", ""));
        }

        withExistingParent(parts.get(EMPTY_COOLER).getId().toString(), mcLoc(BLOCK_FOLDER + "/cube_all")).texture("all", "item/part/empty_cooler");
        withExistingParent(parts.get(DETECTOR_CASING).getId().toString(), mcLoc(BLOCK_FOLDER + "/cube_all")).texture("all", "item/part/detector_casing");
        withExistingParent(parts.get(WIRE_CHAMBER_CASING).getId().toString(), mcLoc(BLOCK_FOLDER + "/cube_all")).texture("all", "item/part/wire_chamber_casing");

        simpleItem(parts.get(SCINTILLATOR_PWO), "scintillator_pwo", "part");
        simpleItem(parts.get(SCINTILLATOR_PLASTIC), "scintillator_plastic", "part");
        simpleItem(parts.get(WIRE_BSCCO), "wire_bscco", "part");
        simpleItem(parts.get(ROD_ND_YAG), "rod_nd_yag", "part");
        simpleItem(parts.get(WIRE_GOLD_TUNGSTEN), "wire_gold_tungsten", "part");
        simpleItem(parts.get(MAGNET_ND), "magnet_nd", "part");
        simpleItem(parts.get(ACCELERATING_BARREL), "accelerating_barrel", "part");
        simpleItem(parts.get(LASER_ASSEMBLY), "laser_assembly", "part");
        simpleItem(parts.get(WIRE_SSFAF), "wire_ssfaf", "part");
        simpleItem(parts.get(WIRE_YBCO), "wire_ybco", "part");
        simpleItem(parts.get(MAGNET_SMC), "magnet_smc", "part");

        simpleItem(sword_tungsten_carbide, "sword_tungsten_carbide", "tools");
        simpleItem(pickaxe_tungsten_carbide, "pickaxe_tungsten_carbide", "tools");
        simpleItem(shovel_tungsten_carbide, "shovel_tungsten_carbide", "tools");
        simpleItem(axe_tungsten_carbide, "axe_tungsten_carbide", "tools");
        simpleItem(hoe_tungsten_carbide, "hoe_tungsten_carbide", "tools");
        simpleItem(beamMeter, "beam_meter", "tools");

        simpleItem(flesh, "flesh", "misc");
        simpleItem(potassiumIodineTablet, "potassium_iodine_tablet", "misc");
        simpleItem(luminousPaints.get(LuminousPaintType.GREEN), "green_luminous_paint", "misc");
        simpleItem(luminousPaints.get(LuminousPaintType.BLUE), "blue_luminous_paint", "misc");
        simpleItem(luminousPaints.get(LuminousPaintType.ORANGE), "orange_luminous_paint", "misc");

        for (Map.Entry<? extends StringRepresentable, DeferredItem<Item>> entry : Stream.of(copernicium).flatMap(e -> e.entrySet().stream()).toList()) {
            simpleItem(entry.getValue(), entry.getKey().getSerializedName(), "copernicium");
        }
        for (Map.Entry<? extends StringRepresentable, DeferredItem<Item>> entry : Stream.of(pellet_copernicium, fuel_copernicium).flatMap(e -> e.entrySet().stream()).toList()) {
            simpleItem(entry.getValue(), entry.getKey().getSerializedName(), "copernicium/fuel");
        }
        for (Map.Entry<? extends StringRepresentable, DeferredItem<Item>> entry : Stream.of(depleted_fuel_copernicium).flatMap(e -> e.entrySet().stream()).toList()) {
            simpleItem(entry.getValue(), entry.getKey().getSerializedName(), "copernicium/depleted_fuel");
        }

        for (Map.Entry<String, NCFluid> entry : fluidEntries(QMDFluids.QMD_FLUIDS)) {
            NCFluid fluid = entry.getValue();
            ((DynamicFluidContainerModelBuilder<?>) this.withExistingParent(entry.getKey() + "_bucket", fluid.gaseous ? Location.neoLoc("item/bucket") : Location.neoLoc("item/bucket_drip")).customLoader(DynamicFluidContainerModelBuilder::begin)).fluid(fluid.still.get()).flipGas(fluid.gaseous).applyTint(true);
        }
    }

    private void simpleItem(DeferredItem<Item> item, String name, String folder) {
        withExistingParent(item.getId().toString(), mcLoc("item/generated")).texture("layer0", "item/" + folder + "/" + name);
    }
}