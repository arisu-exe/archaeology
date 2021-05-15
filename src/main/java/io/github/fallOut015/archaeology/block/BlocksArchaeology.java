package io.github.fallOut015.archaeology.block;

import io.github.fallOut015.archaeology.MainArchaeology;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlocksArchaeology {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MainArchaeology.MODID);



    public static final RegistryObject<Block> LOOSE_DIRT = BLOCKS.register("loose_dirt", () -> new BrushableBlock(Blocks.DIRT.defaultBlockState(), AbstractBlock.Properties.copy(Blocks.DIRT).noOcclusion()));
    public static final RegistryObject<Block> LOOSE_GRAVEL = BLOCKS.register("loose_gravel", () -> new FallingBrushableBlock(Blocks.GRAVEL.defaultBlockState(), AbstractBlock.Properties.copy(Blocks.GRAVEL).noOcclusion()));
    public static final RegistryObject<Block> LOOSE_SAND = BLOCKS.register("loose_sand", () -> new FallingBrushableBlock(Blocks.SAND.defaultBlockState(), AbstractBlock.Properties.copy(Blocks.SAND).noOcclusion()));



    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}