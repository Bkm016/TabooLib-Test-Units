package io.izzel.testunits

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import taboolib.module.ui.ClickEvent
import taboolib.module.ui.linked.MenuLinked
import taboolib.module.ui.stored.MenuStored

/**
 * TabooLib_6_0_0_7_Test_Units
 * io.izzel.testunits.TestMenu
 *
 * @author sky
 * @since 2021/7/18 12:26 下午
 */
class TestMenu : MenuStored() {
}

class TestMenu2(val player: Player) : MenuLinked<String>(player) {

    override fun getElements(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun getSlots(): MutableList<Int> {
        TODO("Not yet implemented")
    }

    override fun onBuild(p0: Inventory) {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: ClickEvent, p1: String) {
        TODO("Not yet implemented")
    }

    override fun generateItem(p0: Player, p1: String, p2: Int, p3: Int): ItemStack? {
        TODO("Not yet implemented")
    }

}