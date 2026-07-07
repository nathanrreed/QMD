package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.datamap.ElectrolyzerElectrolyteData;
import com.nred.nuclearcraft.datamap.FissionReflectorData;
import lach_01298.qmd.datamap.IrradiatorFuel;
import lach_01298.qmd.enums.MaterialTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

import static com.nred.nuclearcraft.registration.DataMapTypeRegistration.ELECTROLYZER_ELECTROLYTE_DATA;
import static com.nred.nuclearcraft.registration.DataMapTypeRegistration.FISSION_REFLECTOR_DATA;
import static lach_01298.qmd.block.QMDBlocks.fissionReflector;
import static lach_01298.qmd.datamap.QMDDatamaps.IRRADIATOR_FUELS;
import static lach_01298.qmd.fluid.QMDFluids.QMD_FLUIDS;
import static lach_01298.qmd.item.QMDItems.sources;

public class QMDDataMapProvider extends DataMapProvider {
    protected QMDDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(FISSION_REFLECTOR_DATA)
                .add(fissionReflector.getId(), new FissionReflectorData(0.75D, 0.75D), false);

        builder(IRRADIATOR_FUELS)
                .add(sources.get(MaterialTypes.SourceType.COBALT_60), new IrradiatorFuel(1.0), false)
                .add(sources.get(MaterialTypes.SourceType.IRIDIUM_192), new IrradiatorFuel(10.0), false);

        builder(ELECTROLYZER_ELECTROLYTE_DATA)
                .add(QMD_FLUIDS.get("sodium_chloride_solution").still.getId(), new ElectrolyzerElectrolyteData(1.0, "chloride_solution"), false);
    }
}