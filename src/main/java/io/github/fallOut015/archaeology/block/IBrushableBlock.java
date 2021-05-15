package io.github.fallOut015.archaeology.block;

import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public interface IBrushableBlock {
    IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    BlockState getNormal();
}