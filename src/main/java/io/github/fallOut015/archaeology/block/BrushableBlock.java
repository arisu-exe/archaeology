package io.github.fallOut015.archaeology.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BrushableBlock extends Block implements IBrushableBlock {
    final BlockState normal;

    public BrushableBlock(final BlockState normal, Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(15)));
        this.normal = normal;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return Block.box(0, 0, 0, 16, state.getValue(LEVEL).intValue() + 1, 16);
    }
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }
    @Override
    public BlockState getNormal() {
        return this.normal;
    }
}