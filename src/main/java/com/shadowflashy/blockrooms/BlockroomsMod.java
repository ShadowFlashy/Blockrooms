package com.shadowflashy.blockrooms;

import com.mojang.logging.LogUtils;
import com.shadowflashy.blockrooms.entity.FacelingEntity;
import com.shadowflashy.blockrooms.world.BlockroomsDimensions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(BlockroomsMod.MOD_ID)
public final class BlockroomsMod {
    public static final String MOD_ID = "blockrooms";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredBlock<Block> LIMINAL_TILES = BLOCKS.registerSimpleBlock(
            "liminal_tiles",
            BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(2.0F, 6.0F).requiresCorrectToolForDrops()
    );
    public static final DeferredItem<BlockItem> LIMINAL_TILES_ITEM = ITEMS.registerSimpleBlockItem("liminal_tiles", LIMINAL_TILES);

    public static final DeferredHolder<EntityType<?>, EntityType<FacelingEntity>> FACELING = ENTITY_TYPES.registerEntityType(
            "faceling",
            FacelingEntity::new,
            MobCategory.MONSTER,
            builder -> builder.sized(0.6F, 1.95F)
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKROOMS_TAB = CREATIVE_MODE_TABS.register(
            "blockrooms",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.blockrooms"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> LIMINAL_TILES_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> output.accept(LIMINAL_TILES_ITEM.get()))
                    .build()
    );

    public BlockroomsMod(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onBuildCreativeTabs);
        modEventBus.addListener(this::onEntityAttributes);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Blockrooms loaded. Registered dimension key: {}", BlockroomsDimensions.LEVEL_0.location());
    }

    private void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(LIMINAL_TILES_ITEM);
        }
    }

    private void onEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(FACELING.get(), FacelingEntity.createAttributes().build());
    }
}
