package lach_01298.qmd.multiblock.block;

import com.nred.nuclearcraft.block.GenericTooltipDeviceBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.IMultiblockPartType;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import lach_01298.qmd.enums.EnumTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import static lach_01298.qmd.block.BlockProperties.IO;

public class GenericIOTooltipDeviceBlock<Controller extends IMultiblockController<Controller>, PartType extends IMultiblockPartType> extends GenericTooltipDeviceBlock<Controller, PartType> {
    public GenericIOTooltipDeviceBlock(MultiblockPartProperties<PartType> properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState().setValue(IO, EnumTypes.IOType.INPUT));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IO);
    }
}