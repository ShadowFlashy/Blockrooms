package com.shadowflashy.blockrooms.world;

import com.shadowflashy.blockrooms.BlockroomsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public final class BlockroomsDimensions {
    public static final ResourceKey<Level> LEVEL_0 = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(BlockroomsMod.MOD_ID, "level_0"));
    public static final ResourceKey<DimensionType> LEVEL_0_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(BlockroomsMod.MOD_ID, "level_0_type"));

    private BlockroomsDimensions() {
    }
}
