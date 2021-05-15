package io.github.fallOut015.archaeology.world.gen.feature;

import com.mojang.serialization.Codec;
import io.github.fallOut015.archaeology.block.IBrushableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class PitFeature extends Feature<BlockStateFeatureConfig> {
    public PitFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader level, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
        final int r = rand.nextInt(9) + 8;
        for(int x = -r; x < r; ++ x) {
            for(int z = -r; z < r; ++ z) {
                for(int y = 0; y > -r; -- y) {
                    final BlockPos replacePos = pos.east(x).north(z).above(y);
                    if(y >= quadratic(z, r) && y >= quadratic(x, r) && !level.isEmptyBlock(replacePos)) {
                        level.setBlock(replacePos, Blocks.AIR.defaultBlockState(), 2);
                        if(y == Math.ceil(quadratic(z, r)) || y == Math.ceil(quadratic(x, r))) {
                            for(int i = 0; i < rand.nextInt(3); ++ i) {
                                final BlockState state = config.state;
                                level.setBlock(replacePos.below(i), i == 0 ? state.setValue(IBrushableBlock.LEVEL, rand.nextInt(9) + 7) : ((IBrushableBlock) state.getBlock()).getNormal(), 3);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private static double quadratic(double x, double r) {
        return MathHelper.clamp(((x + r) * (x - r)) / r, -r, r);
    }
}