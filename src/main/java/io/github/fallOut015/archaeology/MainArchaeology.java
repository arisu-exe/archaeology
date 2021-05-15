package io.github.fallOut015.archaeology;

import io.github.fallOut015.archaeology.block.BlocksArchaeology;
import io.github.fallOut015.archaeology.item.ItemsArchaeology;
import io.github.fallOut015.archaeology.world.gen.feature.FeaturesArchaeology;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod(MainArchaeology.MODID)
public class MainArchaeology {
    public static final String MODID = "archaeology";

    public MainArchaeology() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        BlocksArchaeology.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemsArchaeology.register(FMLJavaModLoadingContext.get().getModEventBus());
        FeaturesArchaeology.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
    }
    private void enqueueIMC(final InterModEnqueueEvent event) {
    }
    private void processIMC(final InterModProcessEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber
    public static class Events {
        @SubscribeEvent
        public static void onBiomeLoad(final BiomeLoadingEvent biomeLoadingEvent) {
            final BiomeGenerationSettingsBuilder generation = biomeLoadingEvent.getGeneration();
            final List<Supplier<ConfiguredFeature<?, ?>>> undergroundOres = generation.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
            final List<Supplier<ConfiguredFeature<?, ?>>> topLayerModification = generation.getFeatures(GenerationStage.Decoration.TOP_LAYER_MODIFICATION);
            final Biome.Category category = biomeLoadingEvent.getCategory();

            final RuleTest NATURAL_DIRT = new TagMatchRuleTest(Tags.Blocks.DIRT);
            final ConfiguredFeature<?, ?> ORE_LOOSE_DIRT = Feature.ORE.configured(new OreFeatureConfig(NATURAL_DIRT, BlocksArchaeology.LOOSE_DIRT.get().defaultBlockState(), 2)).range(64).squared().count(2);
            undergroundOres.add(() -> ORE_LOOSE_DIRT);

            final RuleTest NATURAL_GRAVEL = new TagMatchRuleTest(Tags.Blocks.GRAVEL);
            final ConfiguredFeature<?, ?> ORE_LOOSE_GRAVEL = Feature.ORE.configured(new OreFeatureConfig(NATURAL_GRAVEL, BlocksArchaeology.LOOSE_GRAVEL.get().defaultBlockState(), 2)).range(64).squared().count(2);
            undergroundOres.add(() -> ORE_LOOSE_GRAVEL);

            final RuleTest NATURAL_SAND = new TagMatchRuleTest(Tags.Blocks.SAND);
            final ConfiguredFeature<?, ?> ORE_LOOSE_SAND = Feature.ORE.configured(new OreFeatureConfig(NATURAL_SAND, BlocksArchaeology.LOOSE_SAND.get().defaultBlockState(), 2)).range(64).squared().count(2);
            undergroundOres.add(() -> ORE_LOOSE_SAND);

            switch(category) {
                case DESERT:
                case BEACH:
                    final ConfiguredFeature<?, ?> PIT_LOOSE_SAND = FeaturesArchaeology.PIT.get().configured(new BlockStateFeatureConfig(BlocksArchaeology.LOOSE_SAND.get().defaultBlockState())).decorated(Placement.HEIGHTMAP_WORLD_SURFACE.configured(NoPlacementConfig.NONE).chance(16));
                    topLayerModification.add(() -> PIT_LOOSE_SAND);

                    break;

                default:
                    final ConfiguredFeature<?, ?> PIT_LOOSE_DIRT = FeaturesArchaeology.PIT.get().configured(new BlockStateFeatureConfig(BlocksArchaeology.LOOSE_DIRT.get().defaultBlockState())).decorated(Placement.HEIGHTMAP_WORLD_SURFACE.configured(NoPlacementConfig.NONE).chance(16));
                    topLayerModification.add(() -> PIT_LOOSE_DIRT);

                    final ConfiguredFeature<?, ?> PIT_LOOSE_GRAVEL = FeaturesArchaeology.PIT.get().configured(new BlockStateFeatureConfig(BlocksArchaeology.LOOSE_GRAVEL.get().defaultBlockState())).decorated(Placement.HEIGHTMAP_WORLD_SURFACE.configured(NoPlacementConfig.NONE).chance(16));
                    topLayerModification.add(() -> PIT_LOOSE_GRAVEL);
            }
        }
    }
}