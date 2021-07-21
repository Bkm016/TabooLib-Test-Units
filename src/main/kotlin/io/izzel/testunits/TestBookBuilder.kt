package io.izzel.testunits

import org.bukkit.Material
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.SubscribeEvent
import taboolib.common.platform.submit
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.TellrawJson
import taboolib.module.nms.getInternalKey
import taboolib.module.nms.getItemTag
import taboolib.platform.util.buildBook
import taboolib.platform.util.buildItem
import taboolib.platform.util.sendBook

object TestBookBuilder {

    val book by lazy {
        buildBook {
            write("测试1")
            writeRaw(TellrawJson().append("测试2").hoverText("123").toRawMessage())
            writeRaw(TellrawJson().append("测试3").hoverItem(ItemStack(Material.DIAMOND).getInternalKey()).toRawMessage())
            val diamond = buildItem(XMaterial.DIAMOND) {
                name = "super diamond"
            }
            writeRaw(TellrawJson().append("测试4").hoverItem(diamond.getInternalKey(), diamond.getItemTag().toString()).toRawMessage())
        }
    }

    @Awake(LifeCycle.ENABLE)
    fun init() {
        val stone = buildItem(XMaterial.STONE) {
            name = "123"
        }
        println(book.getItemTag())
        println(TellrawJson().append("123").hoverItem(stone.getInternalKey(), stone.getItemTag().toString()).toRawMessage())
    }

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        submit(delay = 20) {
            e.player.sendBook {
                writeRaw(TellrawJson().append("测试2").hoverText("123").toRawMessage())
            }
            e.player.sendBook(book)
        }
    }
}