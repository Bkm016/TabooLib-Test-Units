package io.izzel.testunits

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.TellrawJson
import taboolib.module.nms.getItemTag
import taboolib.platform.util.buildItem

object TestItemBuilder {

    @Awake(LifeCycle.ENABLE)
    fun init() {
        println(buildItem(XMaterial.STONE) {
            name = "测试"
            lore += "测试123"
            shiny()
        }.getItemTag())
    }
}