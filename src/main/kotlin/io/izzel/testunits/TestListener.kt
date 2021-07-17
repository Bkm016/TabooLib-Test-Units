package io.izzel.testunits

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.*
import taboolib.common.reflect.Reflex.Companion.reflex
import taboolib.common5.util.compileJS
import taboolib.module.chat.TellrawJson
import taboolib.module.chat.colored
import taboolib.module.nms.PacketSendEvent
import taboolib.module.nms.sendScoreboard
import taboolib.module.nms.sendToast

@PlatformSide([Platform.BUKKIT])
object TestListener {

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        e.player.sendMessage("Player Join 1")
        adaptPlayer(e.player).sendMessage("Player Join 2")
        submit(delay = 20) {
            TestEvent(e.player).call()
        }
    }

    @SubscribeEvent
    fun e(e: TestEvent) {
        e.player.sendMessage("Player Test")
        try {
            e.player.sendMessage("Console: ${Bukkit.getServer().reflex<Any>("console")}")
            e.player.sendMessage("Recent TPS: ${Bukkit.getServer().reflex<Any>("console")!!.reflex<DoubleArray>("recentTps")!![0]}")
            e.player.sendMessage("Player Ping: ${e.player.ping}")
            e.player.sendMessage("Player Connection: ${e.player.reflex<Any>("entity/connection")}")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        try {
            e.player.sendMessage("Script Eval ${"1 + 1".compileJS()!!.eval()}")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        try {
            e.player.sendMessage("Color Test: ${"&cN&aM&dS&aL &{255-100-100}NMSL &{#FFFFFF}NMSL &{RED}NMSL".colored()}")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        try {
            TellrawJson().append("Json Test: N").append("M").hoverText("M").append("S").hoverText("S").append("L").hoverText("L").sendTo(adaptPlayer(e.player))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        e.player.sendScoreboard("123", "456")
        submit(delay = 20) {
            e.player.sendScoreboard("456", "123")
            e.player.sendToast(Material.STONE, "测试")
        }
    }

    @SubscribeEvent
    fun e(e: PacketSendEvent) {
        if (e.packet.name == "PacketPlayOutChat") {
//            info(e.packet.read("a"))
//            info(e.packet.read("components"))
//            info(e.packet.read("b"))
        }
    }
}