package exnihilofabrico.common.sieves

import exnihilofabrico.ExNihiloConfig
import exnihilofabrico.ExNihiloFabrico
import exnihilofabrico.common.base.BaseItem
import exnihilofabrico.common.base.IHasColor
import exnihilofabrico.util.Color
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText

class MeshItem(val color: Color, val displayName: String, settings: Settings = itemSettings): BaseItem(settings), IHasColor {

    override fun getColor(index: Int) = color.toInt()

    @Environment(EnvType.CLIENT)
    override fun getName(): Text {
        return TranslatableText("item.exnihilofabrico.mesh", TranslatableText(displayName))
    }
    override fun getName(stack: ItemStack): Text {
        return TranslatableText("item.exnihilofabrico.mesh", TranslatableText(displayName))
    }

    companion object {
        val itemSettings = Item.Settings().group(ExNihiloFabrico.ITEM_GROUP).maxCount(ExNihiloConfig.Modules.Sieve.meshStackSize)
    }
}