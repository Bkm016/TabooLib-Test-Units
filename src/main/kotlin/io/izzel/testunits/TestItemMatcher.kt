package io.izzel.testunits

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.chat.TellrawJson
import taboolib.module.nms.getItemTag
import taboolib.platform.util.buildBook
import taboolib.platform.util.hasItem

object TestItemMatcher {

    @Awake(LifeCycle.ENABLE)
    fun init() {
        val inv = Bukkit.createInventory(null, 54)
        inv.addItem(ItemStack(Material.DIAMOND, 16))
        println("match item x1 ${inv.hasItem { it.type == Material.DIAMOND }}")
        println("match item x32 ${inv.hasItem(32) { it.type == Material.DIAMOND }}")
    }
}