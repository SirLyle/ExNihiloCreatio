package exnihilofabrico.modules

import exnihilofabrico.ExNihiloFabrico
import exnihilofabrico.api.registry.ExNihiloRegistries
import exnihilofabrico.id
import exnihilofabrico.modules.base.BaseItem
import exnihilofabrico.modules.farming.PlantableItem
import exnihilofabrico.modules.farming.TallPlantableItem
import exnihilofabrico.modules.farming.TransformingItem
import exnihilofabrico.modules.infested.SilkWormItem
import net.minecraft.block.Blocks
import net.minecraft.block.KelpBlock
import net.minecraft.block.TallPlantBlock
import net.minecraft.fluid.Fluids
import net.minecraft.item.FoodComponents
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object ModItems {
    val seed_settings: Item.Settings = Item.Settings().group(ExNihiloFabrico.ITEM_GROUP).maxCount(64)

    val TREE_SEEDS = mutableMapOf<Identifier, Item>()

    val CROP_SEEDS = mutableMapOf<Identifier, Item>()

    val FLOWER_SEEDS = mutableMapOf<Identifier, Item>(
        id("seed_sunflower") to TallPlantableItem(Blocks.SUNFLOWER as TallPlantBlock, seed_settings),
        id("seed_lilac") to TallPlantableItem(Blocks.LILAC as TallPlantBlock, seed_settings),
        id("seed_rose_bush") to TallPlantableItem(Blocks.ROSE_BUSH as TallPlantBlock, seed_settings),
        id("seed_peony") to TallPlantableItem(Blocks.PEONY as TallPlantBlock, seed_settings)
    )

    val OTHER_SEEDS = mutableMapOf<Identifier, Item>()

    val RESOURCES = mutableMapOf<Identifier, Item>(
        id("pebble_andesite") to BaseItem(Item.Settings().maxCount(64)),
        id("pebble_diorite") to BaseItem(Item.Settings().maxCount(64)),
        id("pebble_granite") to BaseItem(Item.Settings().maxCount(64)),
        id("pebble_stone") to BaseItem(Item.Settings().maxCount(64)),
        id("porcelain") to BaseItem(Item.Settings().maxCount(64)),
        id("unfired_crucible") to BaseItem(Item.Settings().maxCount(64)),
        id("salt_bottle") to BaseItem(Item.Settings().maxCount(64))
    )

    val DOLLS = listOf<Identifier>(
        id("doll"),
        id("doll_blaze"),
        id("doll_enderman"),
        id("doll_guardian"),
        id("doll_shulker")
    )

    fun registerItems(registry: Registry<Item>) {
        // Setup Conditional Items.
        setup()
        // Register Seeds
        TREE_SEEDS.forEach { (k, v) -> Registry.register(registry, k, v) }
        CROP_SEEDS.forEach { (k, v) -> Registry.register(registry, k, v) }
        OTHER_SEEDS.forEach { (k, v) -> Registry.register(registry, k, v) }
        FLOWER_SEEDS.forEach { (k, v) -> Registry.register(registry, k, v) }

        // Register Meshes
        ExNihiloRegistries.MESH.registerItems(registry)

        // Register Others
        RESOURCES.forEach { (k, v) -> Registry.register(registry, k, v) }
        DOLLS.forEach { Registry.register(registry, it, BaseItem(Item.Settings().maxCount(64))) }

        // Register Ores
        ExNihiloRegistries.ORES.registerPieceItems(registry)
        ExNihiloRegistries.ORES.registerChunkItems(registry)

        ModFluids.registerBuckets(registry)
    }

    fun setup() {

        if(ExNihiloFabrico.config.modules.silkworms.enabled) {
            RESOURCES[id("silkworm_raw")] = SilkWormItem(Item.Settings().maxCount(64).food(FoodComponents.COD))
            RESOURCES[id("silkworm_cooked")] = BaseItem(Item.Settings().maxCount(64).food(FoodComponents.COOKED_COD))
        }
        if(ExNihiloFabrico.config.modules.seeds.enabled) {
            if(ExNihiloFabrico.config.modules.seeds.carrot)
                CROP_SEEDS[id("seed_carrot")] = PlantableItem(Blocks.CARROTS, seed_settings)
            if(ExNihiloFabrico.config.modules.seeds.chorus)
                CROP_SEEDS[id("seed_chorus")] = PlantableItem(Blocks.CHORUS_FLOWER, seed_settings)
            if(ExNihiloFabrico.config.modules.seeds.grass)
                OTHER_SEEDS[id("seed_grass")] = TransformingItem(Blocks.DIRT, Blocks.GRASS_BLOCK, seed_settings)
            if(ExNihiloFabrico.config.modules.seeds.kelp)
                CROP_SEEDS[id("seed_kelp")] = object: PlantableItem((0..25).map { Blocks.KELP.defaultState.with(KelpBlock.AGE, it) }, seed_settings) {
                    override fun placementCheck(context: ItemUsageContext): Boolean {
                        return context.world.getFluidState(context.blockPos.offset(context.side)).fluid == Fluids.WATER
                    }
                }
            if(ExNihiloFabrico.config.modules.seeds.mycelium)
                OTHER_SEEDS[id("seed_mycelium")] = TransformingItem(Blocks.DIRT, Blocks.MYCELIUM, seed_settings)
            if(ExNihiloFabrico.config.modules.seeds.potato)
                CROP_SEEDS[id("seed_potato")] = PlantableItem(Blocks.POTATOES, seed_settings)
            if(ExNihiloFabrico.config.modules.seeds.seaPickle)
                OTHER_SEEDS[id("seed_sea_pickle")] = PlantableItem(Blocks.SEA_PICKLE, seed_settings)
            if(ExNihiloFabrico.config.modules.seeds.sugarCane)
                CROP_SEEDS[id("seed_sugarcane")] = PlantableItem(Blocks.SUGAR_CANE, seed_settings)
            if(ExNihiloFabrico.config.modules.seeds.cactus)
                CROP_SEEDS[id("seed_cactus")] = PlantableItem(Blocks.CACTUS, seed_settings)
            if(ExNihiloFabrico.config.modules.seeds.treeSeeds) {
                TREE_SEEDS[id("seed_oak")] = PlantableItem(Blocks.OAK_SAPLING, seed_settings)
                TREE_SEEDS[id("seed_birch")] = PlantableItem(Blocks.BIRCH_SAPLING, seed_settings)
                TREE_SEEDS[id("seed_spruce")] = PlantableItem(Blocks.SPRUCE_SAPLING, seed_settings)
                TREE_SEEDS[id("seed_jungle")] = PlantableItem(Blocks.JUNGLE_SAPLING, seed_settings)
                TREE_SEEDS[id("seed_acacia")] = PlantableItem(Blocks.ACACIA_SAPLING, seed_settings)
                TREE_SEEDS[id("seed_dark_oak")] = PlantableItem(Blocks.DARK_OAK_SAPLING, seed_settings)
                ExNihiloFabrico.config.modules.seeds.rubberSeed
                    .map { Registry.BLOCK[Identifier(it)] }
                    .map { it.defaultState }
                    .filter { !it.isAir } // Remove unrecognized ones
                    .let { rubberSaplings ->
                        if(!rubberSaplings.isEmpty())
                            TREE_SEEDS[id("seed_rubber")] = PlantableItem(rubberSaplings, seed_settings)
                    }
            }
        }
    }
}