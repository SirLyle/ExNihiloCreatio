package exnihilofabrico.common.ore

import exnihilofabrico.common.base.BaseItem
import exnihilofabrico.common.base.IHasColor
import exnihilofabrico.util.Color

class OrePieceItem(val properties: OreProperties, settings: Settings): BaseItem(settings), IHasColor {
    override fun getColor(index: Int) = if(index==1) properties.color.toInt() else Color.WHITE.toInt()
}