package lach_01298.qmd.multiblock.block;

import com.nred.nuclearcraft.block.GenericTooltipDeviceBlock;
import com.nred.nuclearcraft.registration.BlockRegistration;
import it.zerono.mods.zerocore.lib.block.multiblock.IMultiblockPartType;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;

public class MultiblockCasing<Controller extends IMultiblockController<Controller>, PartType extends IMultiblockPartType> extends GenericTooltipDeviceBlock<Controller, PartType> {
    public MultiblockCasing(@NotNull MultiblockPartProperties<PartType> properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState().setValue(BlockRegistration.FRAME, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockRegistration.FRAME);
    }
}