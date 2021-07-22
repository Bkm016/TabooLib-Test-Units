package io.izzel.testunits

import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardDisplayObjective
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardScore
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardTeam
import org.bukkit.entity.Player
import taboolib.common.reflect.Reflex.Companion.getProperty
import taboolib.module.nms.Packet
import java.util.*

class TestNMSImpl : TestNMS() {
    private val classes = listOf<Class<*>>(
        PacketPlayOutScoreboardObjective::class.java,
        PacketPlayOutScoreboardTeam::class.java,
        PacketPlayOutScoreboardScore::class.java,
        PacketPlayOutScoreboardDisplayObjective::class.java
    )

    override val teamCls = PacketPlayOutScoreboardTeam::class.java

    override fun handlePacket(player: Player, packet: Packet): Boolean {
        val realPacket = packet.source
        if (!classes.contains(realPacket.javaClass)) {
            return true
        }

        repeat(3) {
            player.sendMessage("   ")
        }

        player.sendMessage("§aPacket: §b${realPacket.javaClass.simpleName}")
        realPacket.javaClass.declaredFields.forEach {
            val value = realPacket.getProperty<Any>(it.name) ?: throw IllegalArgumentException("Value null;")

            if (it.type == Optional::class.java) {
                val opt = value as Optional<*>
                val obj = opt.get() ?: return@forEach player.sendMessage("§ak§f: null")
                teamSubCls.fields.forEach { field ->
                    player.sendMessage("§ab.${field.name}§f: §7${obj.getProperty<Any>(field.name)}")
                }
                return@forEach
            }

            player.sendMessage("§a${it.name}§f: §7$value")
        }

        return true
    }
}