package lach_01298.qmd.tile;

import com.nred.nuclearcraft.block.tile.info.SimpleTileInfoBlock;
import com.nred.nuclearcraft.block_entity.processor.IBasicUpgradableProcessor;
import com.nred.nuclearcraft.block_entity.processor.info.builder.ProcessorMenuInfoBuilderImpl.BasicProcessorMenuInfoBuilder;
import com.nred.nuclearcraft.block_entity.processor.info.builder.ProcessorMenuInfoBuilderImpl.BasicUpgradableProcessorMenuInfoBuilder;
import com.nred.nuclearcraft.compat.recipe_viewer.info.RecipeViewerProcessorCategoryInfo;
import com.nred.nuclearcraft.handler.BlockEntityMenuInfo;
import com.nred.nuclearcraft.menu.MenuFunction;
import com.nred.nuclearcraft.payload.processor.ProcessorUpdatePacket;
import com.nred.nuclearcraft.recipe.BasicRecipe;
import com.nred.nuclearcraft.util.ModCheck;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.machine.container.MachineMenuImpl;
import lach_01298.qmd.machine.container.MachineMenuImpl.CreativeParticleSourceMenu;
import lach_01298.qmd.machine.tile.TileQMDProcessors;
import lach_01298.qmd.multiblock.container.*;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import lach_01298.qmd.particleChamber.tile.TileCollisionChamberController;
import lach_01298.qmd.particleChamber.tile.TileDecayChamberController;
import lach_01298.qmd.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.pipe.TileBeamline;
import lach_01298.qmd.recipe_viewer.RecipeViewerQMDCategoryInfoBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.BiFunction;

import static com.nred.nuclearcraft.handler.BlockEntityInfoHandler.*;
import static com.nred.nuclearcraft.util.ContainerInfoHelper.bigSlot;
import static com.nred.nuclearcraft.util.ContainerInfoHelper.standardSlot;
import static lach_01298.qmd.block.QMDBlocks.irradiator;
import static lach_01298.qmd.block.QMDBlocks.oreLeacher;

public class QMDTileInfoHandler {

    public static void preInit() {
        registerProcessorInfo(new QMDBasicUpgradableProcessorMenuInfoBuilder<>("ore_leacher", TileQMDProcessors.OreLeacherEntity.class, TileQMDProcessors.OreLeacherEntity::new, MachineMenuImpl.OreLeacherMenu::new).setParticles("splash", "red_dust").setDefaultProcessTime(() -> QMDStartupConfig.processor_time[0]).setDefaultProcessPower(() -> QMDStartupConfig.processor_power[0]).setLosesProgress(true).setItemInputSlots(standardSlot(36, 11)).setFluidInputSlots(standardSlot(36, 42), standardSlot(56, 42), standardSlot(76, 42)).setItemOutputSlots(standardSlot(112, 42), standardSlot(132, 42), standardSlot(152, 42)).setScreenTexture(QMD.MOD_ID + ":screen/ore_leacher").setRecipeViewerTexture(QMD.MOD_ID + ":textures/gui/sprites/screen/ore_leacher.png").setFrontTexture(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "block/machine/ore_leacher_front")).setProgressBarGuiXYWHUV(40, 29, 70, 25, 176, 0));
        registerProcessorInfo(new BasicProcessorMenuInfoBuilder<>("irradiator", TileQMDProcessors.IrradiatorEntity.class, TileQMDProcessors.IrradiatorEntity::new, MachineMenuImpl.IrradiatorMenu::new).setParticles("end_rod", "red_dust").setDefaultProcessTime(() -> QMDStartupConfig.processor_time[1]).setDefaultProcessPower(() -> QMDStartupConfig.processor_power[1]).setLosesProgress(true).setItemInputSlots(standardSlot(44, 54), standardSlot(80, 21)).setItemOutputSlots(standardSlot(116, 54)).setScreenTexture(QMD.MOD_ID + ":screen/irradiator").setRecipeViewerTexture(QMD.MOD_ID + ":textures/gui/sprites/screen/irradiator.png").setFrontTexture(ResourceLocation.fromNamespaceAndPath(QMD.MOD_ID, "block/machine/irradiator_front")).setRedstoneControlGuiXY(7, 63).setMachineConfigGuiXY(7, 43).setProgressBarGuiXYWHUV(62, 57, 52, 9, 176, 0));

        registerContainerInfo(new BlockEntityMenuInfo<>("creative_particle_source", TileCreativeParticleSource.class, CreativeParticleSourceMenu::new));
        registerBlockTileInfo(new SimpleTileInfoBlock<>("atmosphere_collector", TileAtmosphereCollector.class, TileAtmosphereCollector::new));
        registerBlockTileInfo(new SimpleTileInfoBlock<>("liquid_collector", TileLiquidCollector.class, TileLiquidCollector::new));

        registerContainerInfo(new BlockEntityMenuInfo<>("ion_source", TileAcceleratorIonSource.class, ContainerAcceleratorIonSource::new));

        registerContainerInfo(new BlockEntityMenuInfo<>("linear_accelerator_controller", TileLinearAcceleratorController.class, ContainerLinearAcceleratorController::new));
        registerContainerInfo(new BlockEntityMenuInfo<>("ring_accelerator_controller", TileRingAcceleratorController.class, ContainerRingAcceleratorController::new));
        registerContainerInfo(new BlockEntityMenuInfo<>("beam_diverter_controller", TileBeamDiverterController.class, ContainerBeamDiverterController::new));
        registerContainerInfo(new BlockEntityMenuInfo<>("decelerator_controller", TileDeceleratorController.class, ContainerDeceleratorController::new));
        registerContainerInfo(new BlockEntityMenuInfo<>("beam_splitter_controller", TileBeamSplitterController.class, ContainerBeamSplitterController::new));
        registerContainerInfo(new BlockEntityMenuInfo<>("mass_spectrometer_controller", TileMassSpectrometerController.class, ContainerMassSpectrometerController::new));

        registerContainerInfo(new BlockEntityMenuInfo<>("target_chamber_controller", TileTargetChamberController.class, ContainerTargetChamberController::new));
        registerContainerInfo(new BlockEntityMenuInfo<>("decay_chamber_controller", TileDecayChamberController.class, ContainerDecayChamberController::new));
        registerContainerInfo(new BlockEntityMenuInfo<>("beam_dump_controller", TileBeamDumpController.class, ContainerBeamDumpController::new));
        registerContainerInfo(new BlockEntityMenuInfo<>("collision_chamber_controller", TileCollisionChamberController.class, ContainerCollisionChamberController::new));

        // TODO
//		registerContainerInfo(new BlockEntityMenuInfo<>( "neutral_containment_controller", TileExoticContainmentController.class, ContainerExoticContainmentController::new, clientGetGuiInfoTileFunction(() -> GuiNeutralContainmentController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>( "nucleosynthesis_chamber_controller", TileNucleosynthesisChamberController.class, ContainerNucleosynthesisChamberController::new, clientGetGuiInfoTileFunction(() -> GuiNucleosynthesisChamberController::new)));

        registerContainerInfo(new BlockEntityMenuInfo<>("beamline", TileBeamline.class, null));
    }

    public static class QMDBasicUpgradableProcessorMenuInfoBuilder<TILE extends BlockEntity & IBasicUpgradableProcessor<TILE, PACKET, RECIPE>, PACKET extends ProcessorUpdatePacket, RECIPE extends BasicRecipe> extends BasicUpgradableProcessorMenuInfoBuilder<TILE, PACKET, RECIPE> {
        public QMDBasicUpgradableProcessorMenuInfoBuilder(String name, Class<TILE> tileClass, BiFunction<BlockPos, BlockState, TILE> tileSupplier, MenuFunction<TILE> menuFunction) {
            super(name, tileClass, tileSupplier, menuFunction);
        }
    }

    public static void init() {
        if (ModCheck.jeiLoaded() || ModCheck.emiLoaded()) {
            registerRecipeViewerCategoryInfo(new RecipeViewerProcessorCategoryInfo<>("irradiator", List.of(irradiator)));
            registerRecipeViewerCategoryInfo(new RecipeViewerProcessorCategoryInfo<>("ore_leacher", List.of(oreLeacher)));

            registerRecipeViewerCategoryInfo(new RecipeViewerQMDCategoryInfoBuilder<>("accelerator_cooling").setFluidInputSlots(standardSlot(9, 5)).setFluidOutputSlots(bigSlot(65, 1)).setProgressBarGuiXYWHUV(27, 6, 37, 16, 93, 0).buildCategoryInfo());
            registerRecipeViewerCategoryInfo(new RecipeViewerQMDCategoryInfoBuilder<>("accelerator_source").setItemInputSlots(standardSlot(9, 5)).setFluidInputSlots(standardSlot(9, 22)).setParticleOutputSlots(standardSlot(61, 14)).setRecipeViewerTexture("qmd:textures/gui/sprites/recipe_viewer/ion_source.png").disableProgressBar().buildCategoryInfo());
            registerRecipeViewerCategoryInfo(new RecipeViewerQMDCategoryInfoBuilder<>("mass_spectrometer").setRecipeViewerTexture("qmd:textures/gui/sprites/screen/mass_spectrometer_controller.png").setItemInputSlots(standardSlot(46, 14)).setFluidInputSlots(standardSlot(46, 33)).setItemOutputSlots(standardSlot(82, 14), standardSlot(101, 14), standardSlot(120, 14), standardSlot(139, 14)).setFluidOutputSlots(standardSlot(82, 33), standardSlot(101, 33), standardSlot(120, 33), standardSlot(139, 33)).setRecipeViewerBackgroundXYWH(45, 13, 111, 93).setProgressBarGuiXYWHUV(52, 51, 101, 55, 0, 201).buildCategoryInfo());

            registerRecipeViewerCategoryInfo(new RecipeViewerQMDCategoryInfoBuilder<>("target_chamber").setParticleInputSlots(standardSlot(8, 34)).setItemInputSlots(standardSlot(43, 26)).setFluidInputSlots(standardSlot(43, 43)).setItemOutputSlots(standardSlot(84, 26)).setFluidOutputSlots(standardSlot(84, 43)).setParticleOutputSlots(standardSlot(76, 3), standardSlot(119, 34), standardSlot(76, 66)).setRecipeViewerBackgroundXYWH(0, 0, 150, 115).setProgressBarGuiXYWHUV(61, 36, 21, 12, 182, 0).buildCategoryInfo());
            registerRecipeViewerCategoryInfo(new RecipeViewerQMDCategoryInfoBuilder<>("decay_chamber").setParticleInputSlots(standardSlot(49, 31)).setParticleOutputSlots(standardSlot(82, 8), standardSlot(82, 31), standardSlot(82, 54)).disableProgressBar().setRecipeViewerBackgroundXYWH(0, 0, 150, 100).buildCategoryInfo());
            registerRecipeViewerCategoryInfo(new RecipeViewerQMDCategoryInfoBuilder<>("beam_dump").setRecipeViewerTexture("qmd:textures/gui/sprites/screen/beam_dump_controller.png").setParticleInputSlots(standardSlot(37, 37)).setFluidOutputSlots(standardSlot(81, 37)).setProgressBarGuiXYWHUV(54, 38, 26, 14, 143, 6).buildCategoryInfo());
            registerRecipeViewerCategoryInfo(new RecipeViewerQMDCategoryInfoBuilder<>("collision_chamber").setParticleInputSlots(standardSlot(43, 33), standardSlot(111, 33)).setParticleOutputSlots(standardSlot(46, 2), standardSlot(46, 64), standardSlot(108, 2), standardSlot(108, 64)).disableProgressBar().setRecipeViewerBackgroundXYWH(0, 0, 160, 112).buildCategoryInfo());
        }
    }
}
