package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.info.NCFluid;
import com.nred.nuclearcraft.multiblock.turbine.TurbineRotorBladeUtil;
import lach_01298.qmd.QMD;
import lach_01298.qmd.enums.BlockTypes;
import lach_01298.qmd.fluid.QMDFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;
import java.util.function.Function;

import static com.nred.nuclearcraft.helpers.Concat.fluidValues;
import static com.nred.nuclearcraft.helpers.Location.ncLoc;
import static com.nred.nuclearcraft.registration.BlockRegistration.ACTIVE;
import static lach_01298.qmd.block.QMDBlocks.*;
import static net.neoforged.neoforge.client.model.generators.ModelProvider.BLOCK_FOLDER;

public class QMDBlockStateProvider extends BlockStateProvider {

    public QMDBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, QMD.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        luminousPaint("green_luminous_paint", greenLuminousPaint);
        luminousPaint("blue_luminous_paint", blueLuminousPaint);
        luminousPaint("orange_luminous_paint", orangeLuminousPaint);

        for (Map.Entry<BlockTypes.LampType, DeferredBlock<Block>> entry : dischargeLamps.entrySet()) {
            booleanBlock(entry.getKey().getSerializedName().toLowerCase(), "", "_off", entry.getValue(), "lamp", ACTIVE, true);
        }

        blockSidesAndTop(rtgStrontium, "machine", "rtg_strontium_top", "rtg_strontium_side");
        blockWithItem("strontium_90", strontium90, "other");
        blockWithItem("creative_particle_source", creativeParticleSource, "other");

        turbineBladeWithItem("turbine_blade_super_alloy", turbineBladeSuperAlloy, "other");
        cutoutBooleanBlock("shield_hafnium", "on", "off", fissionShield, "fission", ACTIVE);
        blockWithItem("reflector_tungsten_carbide", fissionReflector, "fission");

        processorModel("irradiator", irradiator, "machine");
        processorModel("ore_leacher", oreLeacher, "machine");

        blockWithItem("atmosphere_collector", atmosphereCollector, "machine");
        blockWithItem("liquid_collector", liquidCollector, "machine");

        for (NCFluid fluid : fluidValues(QMDFluids.QMD_FLUIDS)) {
            simpleBlock(fluid.block.get(), models().cubeAll(fluid.block.get().getName().getString(), fluid.client.getStillTexture()));
        }
    }

    private void luminousPaint(String name, DeferredBlock<Block> deferredBlock) {
        Block block = deferredBlock.get();
        ModelFile modelFile = models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(), modLoc("block/block_luminous_paint")).texture("main", modLoc("block/other/" + name));
        directionalBlock(block, modelFile);
    }

    private void turbineBladeWithItem(String name, DeferredBlock<Block> deferredBlock, String folder) {
        Block block = deferredBlock.get();
        String texture = BLOCK_FOLDER + "/" + folder + "/" + name;

        ModelFile model = models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(), ncLoc(name.contains("stator") ? "block/turbine_rotor_stator" : "block/turbine_rotor_blade")).texture("texture", texture);
        rotorPartModel(block, $ -> model);
        simpleBlockItem(block, model);
    }

    private void rotorPartModel(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    TurbineRotorBladeUtil.TurbinePartDir dir = state.getValue(TurbineRotorBladeUtil.DIR);

                    return switch (dir) {
                        case INVISIBLE -> ConfiguredModel.builder().modelFile(models().getExistingFile(ncLoc("block_invisible"))).build();
                        case X -> ConfiguredModel.builder().modelFile(modelFunc.apply(state)).rotationX(90).rotationY(90).build();
                        case Y -> ConfiguredModel.builder().modelFile(modelFunc.apply(state)).build();
                        case Z -> ConfiguredModel.builder().modelFile(modelFunc.apply(state)).rotationX(90).build();
                    };
                });
    }

    private void blockWithItem(String name, DeferredBlock<Block> deferredBlock, String folder) {
        Block block = deferredBlock.get();
        String texture = BLOCK_FOLDER + "/" + folder + "/" + name;
        ModelFile model = models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath(), modLoc(texture));
        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }

    private void booleanBlock(String name, String nameTrue, String nameFalse, DeferredBlock<Block> deferredBlock, String folder, BooleanProperty property, boolean default_state) {
        Block block = deferredBlock.get();
        String texture = BLOCK_FOLDER + "/" + folder + "/" + name;
        ModelFile modelTrue = models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_" + nameTrue, modLoc(texture + nameTrue));
        ModelFile modelFalse = models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_" + nameFalse, modLoc(texture + nameFalse));

        propertyBlock(block, state -> state.getValue(property) ? modelTrue : modelFalse);
        simpleBlockItem(block, default_state ? modelTrue : modelFalse);
    }

    private void blockSidesAndTop(DeferredBlock<Block> deferredBlock, String folder, String top, String side) {
        Block block = deferredBlock.get();
        String base = BLOCK_FOLDER + "/" + folder + "/";
        ModelFile model = models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(), ncLoc("block/top_sides")).texture("top", modLoc(base + top)).texture("sides", modLoc(base + side));

        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }

    private void cutoutBooleanBlock(String name, String nameTrue, String nameFalse, DeferredBlock<Block> deferredBlock, String folder, BooleanProperty property) {
        Block block = deferredBlock.get();
        String texture = BLOCK_FOLDER + "/" + folder + "/" + name + "_";
        ModelFile modelTrue = models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_" + nameTrue, modLoc(texture + nameTrue));
        ModelFile modelFalse = models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_" + nameFalse, modLoc(texture + nameFalse)).renderType("cutout");

        propertyBlock(block, state -> state.getValue(property) ? modelTrue : modelFalse);
        simpleBlockItem(block, modelFalse);
    }

    public void propertyBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(modelFunc.apply(state))
                        .build());
    }

    private void processorModel(String name, DeferredBlock<Block> deferredBlock, String folder) {
        Block block = deferredBlock.get();
        ModelFile modelOn = models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_on", ncLoc("block/processor")).texture("front", modLoc("block/" + folder + "/" + name + "_front_on"));
        ModelFile modelOff = models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_off", ncLoc("block/processor")).texture("front", modLoc("block/" + folder + "/" + name + "_front_off"));
        horizontalBlock(block, state -> state.getValue(ACTIVE) ? modelOn : modelOff);

        simpleBlockItem(block, modelOff);
    }
}