package lach_01298.qmd.datagen;

import com.nred.nuclearcraft.info.NCFluid;
import com.nred.nuclearcraft.multiblock.turbine.TurbineRotorBladeUtil;
import lach_01298.qmd.QMD;
import lach_01298.qmd.enums.BlockTypes;
import lach_01298.qmd.fluid.QMDFluids;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.joml.Vector2i;

import java.util.Map;
import java.util.function.Function;

import static com.nred.nuclearcraft.helpers.Concat.fluidValues;
import static com.nred.nuclearcraft.helpers.Location.ncLoc;
import static com.nred.nuclearcraft.registration.BlockRegistration.*;
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

        beamline(beamline);

        accelerator();
    }

    private void accelerator() {
        directionalMachine("linear_accelerator_controller", linearAcceleratorController, "accelerator", ACTIVE);
        directionalMachine("ring_accelerator_controller", ringAcceleratorController, "accelerator", ACTIVE);
        directionalMachine("beam_diverter_controller", beamDiverterController, "accelerator", ACTIVE);
        directionalMachine("beam_splitter_controller", beamSplitterController, "accelerator", ACTIVE);
        directionalMachine("decelerator_controller", deceleratorController, "accelerator", ACTIVE);
        directionalMachine("mass_spectrometer_controller", massSpectrometerController, "accelerator", ACTIVE);

        blockWithItem("beam", acceleratorBeam, "accelerator");
        booleanBlock("", "frame", "casing", acceleratorCasing, "accelerator", FRAME, false);
        blockWithItemCutout("glass", acceleratorGlass, "accelerator");

//        booleanBlock("californium", false, "source_back", true, "source", "_on", "_off", FISSION_REACTOR_MAP.get("californium_source"), "fission/source", ACTIVE, Directional);
    }

    private void luminousPaint(String name, DeferredBlock<Block> deferredBlock) {
        Block block = deferredBlock.get();
        ModelFile modelFile = models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(), modLoc("block/block_luminous_paint")).texture("main", modLoc("block/other/" + name));
        directionalBlock(block, modelFile);
    }

    private static final Map<Direction.Axis, Vector2i> axisMap = Map.of(
            Direction.Axis.Y, new Vector2i(90, 0),
            Direction.Axis.Z, new Vector2i(0, 0),
            Direction.Axis.X, new Vector2i(0, 90)
    );

    private void beamline(DeferredBlock<Block> deferredBlock) {
        Block block = deferredBlock.get();
        ModelFile model = models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(), modLoc("block/pipe")).texture("side", BLOCK_FOLDER + "/beamline/side").texture("end", BLOCK_FOLDER + "/beamline/end");

        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction.Axis dir = state.getValue(AXIS_ALL);

                    return switch (dir) {
                        case X -> ConfiguredModel.builder().modelFile(model).rotationY(90).build();
                        case Z -> ConfiguredModel.builder().modelFile(model).build();
                        case Y -> ConfiguredModel.builder().modelFile(model).rotationX(90).build();
                    };
                });
        simpleBlockItem(block, model);
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

    private void directionalMachine(String name, DeferredBlock<Block> deferredBlock, String folder, BooleanProperty property) {
        Block block = deferredBlock.get();
        String base = BLOCK_FOLDER + "/" + folder + "/" + name;
        String casing = BLOCK_FOLDER + "/" + folder + "/casing";
        ModelFile modelOn = models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_on", ncLoc("block/machine")).texture("top", modLoc(casing)).texture("bottom", modLoc(casing)).texture("side", modLoc(casing)).texture("back", modLoc(casing)).texture("front", modLoc(base + "_front_on"));
        ModelFile modelOff = models().withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath() + "_off", ncLoc("block/machine")).texture("top", modLoc(casing)).texture("bottom", modLoc(casing)).texture("side", modLoc(casing)).texture("back", modLoc(casing)).texture("front", modLoc(base + "_front_off"));

        directionalBlock(block, state -> state.getValue(property) ? modelOn : modelOff);
        simpleBlockItem(block, modelOff);
    }

    private void blockWithItemRenderType(String name, DeferredBlock<Block> deferredBlock, String folder, String renderType) {
        Block block = deferredBlock.get();
        String texture = BLOCK_FOLDER + "/" + folder + "/" + name;
        ModelFile model = models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath(), modLoc(texture)).renderType(renderType);
        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }

    private void blockWithItemCutout(String name, DeferredBlock<Block> deferredBlock, String folder) {
        blockWithItemRenderType(name, deferredBlock, folder, "cutout");
    }


    public void directionalBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        directionalBlock(block, modelFunc, 180);
    }

    public void directionalBlock(Block block, Function<BlockState, ModelFile> modelFunc, int angleOffset) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationX(dir == Direction.DOWN ? 90 : dir.getAxis().isHorizontal() ? 0 : 270)
                            .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + angleOffset) % 360)
                            .build();
                });
    }
}