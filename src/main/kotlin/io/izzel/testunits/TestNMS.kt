package io.izzel.testunits

import org.bukkit.entity.Player
import taboolib.module.nms.Packet

abstract class TestNMS {

    abstract val teamCls: Class<*>

    val teamSubCls by lazy { teamCls.declaredClasses.find { cls -> cls.simpleName == "b" }.also(::println) ?: throw IllegalArgumentException("CNM") }

    abstract fun handlePacket(player: Player, packet: Packet): Boolean
    
}