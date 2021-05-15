package io.github.fallOut015.archaeology.world.gen.feature;

import io.github.fallOut015.archaeology.MainArchaeology;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeaturesArchaeology {
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MainArchaeology.MODID);



    public static final RegistryObject<Feature<BlockStateFeatureConfig>> PIT = FEATURES.register("pit", () -> new PitFeature(BlockStateFeatureConfig.CODEC));



    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}