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
import lach_01298.qmd.config.QMDStartupConfig;
import lach_01298.qmd.machine.container.MachineMenuImpl;
import lach_01298.qmd.machine.container.MachineMenuImpl.CreativeParticleSourceMenu;
import lach_01298.qmd.machine.tile.TileQMDProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.BiFunction;

import static com.nred.nuclearcraft.handler.BlockEntityInfoHandler.*;
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

        // TODO
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "linear_accelerator_controller", TileLinearAcceleratorController.class, ContainerLinearAcceleratorController::new, clientGetGuiInfoTileFunction(() -> GuiLinearAcceleratorController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "ring_accelerator_controller", TileRingAcceleratorController.class, ContainerRingAcceleratorController::new, clientGetGuiInfoTileFunction(() -> GuiRingAcceleratorController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "beam_diverter_controller", TileBeamDiverterController.class, ContainerBeamDiverterController::new, clientGetGuiInfoTileFunction(() -> GuiBeamDiverterController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "decelerator_controller", TileDeceleratorController.class, ContainerDeceleratorController::new, clientGetGuiInfoTileFunction(() -> GuiDeceleratorController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "beam_splitter_controller", TileBeamSplitterController.class, ContainerBeamSplitterController::new, clientGetGuiInfoTileFunction(() -> GuiBeamSplitterController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "mass_spectrometer_controller", TileMassSpectrometerController.class, ContainerMassSpectrometerController::new, clientGetGuiInfoTileFunction(() -> GuiMassSpectrometerController::new)));
//
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "target_chamber_controller", TileTargetChamberController.class, ContainerTargetChamberController::new, clientGetGuiInfoTileFunction(() -> GuiTargetChamberController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "decay_chamber_controller", TileDecayChamberController.class, ContainerDecayChamberController::new, clientGetGuiInfoTileFunction(() -> GuiDecayChamberController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "beam_dump_controller", TileBeamDumpController.class, ContainerBeamDumpController::new, clientGetGuiInfoTileFunction(() -> GuiBeamDumpController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "collision_chamber_controller", TileCollisionChamberController.class, ContainerCollisionChamberController::new, clientGetGuiInfoTileFunction(() -> GuiCollisionChamberController::new)));
//
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "neutral_containment_controller", TileExoticContainmentController.class, ContainerExoticContainmentController::new, clientGetGuiInfoTileFunction(() -> GuiNeutralContainmentController::new)));
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "nucleosynthesis_chamber_controller", TileNucleosynthesisChamberController.class, ContainerNucleosynthesisChamberController::new, clientGetGuiInfoTileFunction(() -> GuiNucleosynthesisChamberController::new)));
//
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "beamline", TileBeamline.class, null, (GuiFunction<TileBeamline>) null));
//
//		registerContainerInfo(new BlockEntityMenuInfo<>(QMD.MOD_ID, "liquefier_controller", TileLiquefierController.class, ContainerLiquefierController::new, clientGetGuiInfoTileFunction(() -> GUILiquefierController::new)));
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
//            registerRecipeViewerCategoryInfo(new RecipeViewerSimpleCategoryInfoBuilder<>("atmosphere_collector", List.of(atmosphereCollector)).setFluidInputSlots(standardSlot(56, 35)).setFluidOutputSlots(bigSlot(112, 31)));
//            registerRecipeViewerCategoryInfo(new RecipeViewerSimpleCategoryInfoBuilder<>("liquid_collector", List.of(liquidCollector)).setFluidInputSlots(standardSlot(56, 35)).setFluidOutputSlots(bigSlot(112, 31)));
        }
    }
}
