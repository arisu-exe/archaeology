package io.github.fallOut015.archaeology.item;

import io.github.fallOut015.archaeology.MainArchaeology;
import io.github.fallOut015.archaeology.block.BlocksArchaeology;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsArchaeology {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainArchaeology.MODID);



    public static final RegistryObject<Item> LOOSE_DIRT = ITEMS.register("loose_dirt", () -> new BlockItem(BlocksArchaeology.LOOSE_DIRT.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
    public static final RegistryObject<Item> LOOSE_GRAVEL = ITEMS.register("loose_gravel", () -> new BlockItem(BlocksArchaeology.LOOSE_GRAVEL.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
    public static final RegistryObject<Item> LOOSE_SAND = ITEMS.register("loose_sand", () -> new BlockItem(BlocksArchaeology.LOOSE_SAND.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));

    public static final RegistryObject<Item> WOODEN_BRUSH = ITEMS.register("wooden_brush", () -> new BrushItem(new Item.Properties().tab(ItemGroup.TAB_TOOLS).defaultDurability(256)));
    public static final RegistryObject<Item> STONE_BRUSH = ITEMS.register("stone_brush", () -> new BrushItem(new Item.Properties().tab(ItemGroup.TAB_TOOLS).defaultDurability(512)));



    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}