package io.github.fallOut015.archaeology.item;

import io.github.fallOut015.archaeology.MainArchaeology;
import io.github.fallOut015.archaeology.block.IBrushableBlock;
import io.github.fallOut015.archaeology.loot.LootParameterSetsArchaeology;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class BrushItem extends Item {
    // fortune
    // luck

    public BrushItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        final World level = context.getLevel();
        final BlockPos clickedPos = context.getClickedPos();
        final BlockState state = level.getBlockState(clickedPos);
        final Block block = state.getBlock();
        final PlayerEntity player = context.getPlayer();

        if(block instanceof IBrushableBlock) {
            final Integer heightLevel = state.getValue(IBrushableBlock.LEVEL);
            if(level.isClientSide()) {
                for(int i = 0; i < 8; ++ i) {
                    final Random random = level.getRandom();
                    double x = clickedPos.getX() + random.nextDouble();
                    double y = clickedPos.getY() + (heightLevel.doubleValue() + 1d) / 16d;
                    double z = clickedPos.getZ() + random.nextDouble();
                    level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state), x, y, z, 0, 0, 0);
                }
                if(heightLevel.intValue() == 0) {
                    level.playSound(player, clickedPos, block.getSoundType(state, level, clickedPos, player).getBreakSound(), SoundCategory.BLOCKS, 1.0f, 1.0f);
                } else {
                    level.playSound(player, clickedPos, block.getSoundType(state, level, clickedPos, player).getStepSound(), SoundCategory.BLOCKS, 1.0f, 1.0f);
                }
            }
            if(heightLevel.intValue() == 0) {
                if(!level.isClientSide()) {
                    final Random random = level.getRandom();
                    final ItemStack stack = context.getItemInHand();

                    level.setBlockAndUpdate(clickedPos, Blocks.AIR.defaultBlockState());

                    LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld) level)).withRandom(level.getRandom()).withLuck(1).withParameter(LootParameters.TOOL, stack).withParameter(LootParameters.BLOCK_STATE, state);
                    LootTable loottable = level.getServer().getLootTables().get(new ResourceLocation(MainArchaeology.MODID, "brushed/" + block.getRegistryName().getPath()));
                    List<ItemStack> list = loottable.getRandomItems(lootcontext$builder.create(LootParameterSetsArchaeology.BRUSHED));
                    level.addFreshEntity(new ItemEntity(level, clickedPos.getX(), clickedPos.getY() + 0.5, clickedPos.getZ(), list.get(random.nextInt(list.size()))));

                    stack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(context.getHand()));
                }

                return ActionResultType.CONSUME;
            } else {
                level.setBlockAndUpdate(clickedPos, state.setValue(IBrushableBlock.LEVEL, Integer.valueOf(heightLevel.intValue() - 1)));
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}