package io.github.fallOut015.archaeology.loot;

import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class LootParameterSetsArchaeology {
    public static final LootParameterSet BRUSHED = register("archaeology:brushed", builder ->
        builder.optional(LootParameters.TOOL).optional(LootParameters.BLOCK_STATE)
    );

    private static LootParameterSet register(String name, Consumer<LootParameterSet.Builder> builder) {
        try {
            Method lootParameterSets$register = LootParameterSets.class.getDeclaredMethod("register", String.class, Consumer.class);
            lootParameterSets$register.setAccessible(true);
            return (LootParameterSet) lootParameterSets$register.invoke(null, name, builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}