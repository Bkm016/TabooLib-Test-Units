package io.izzel.testunits.platform

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.*
import taboolib.platform.util.toBukkitLocation
import java.util.*

interface PlatformEntityHandler {

    fun entities(player: ProxyPlayer): List<UUID>

    fun entityNearly(player: ProxyPlayer): UUID?

    fun teleport(player: ProxyPlayer, uuid: UUID)

    @PlatformImplementation(Platform.BUKKIT)
    class BukkitSide : PlatformEntityHandler {

        override fun entities(player: ProxyPlayer): List<UUID> {
            return player.cast<Player>().world.entities.map { it.uniqueId }
        }

        override fun entityNearly(player: ProxyPlayer): UUID? {
            return player.cast<Player>().world.entities.filter { it != player.origin }
                .minByOrNull { it.location.distance(player.location.toBukkitLocation()) }?.uniqueId
        }

        override fun teleport(player: ProxyPlayer, uuid: UUID) {
            player.cast<Player>().teleport(Bukkit.getEntity(uuid) ?: return)
        }
    }
}

fun ProxyPlayer.entities(): List<UUID> {
    return implementations(PlatformEntityHandler::class.java).entities(this)
}

fun ProxyPlayer.entityNearly(): UUID? {
    return implementations(PlatformEntityHandler::class.java).entityNearly(this)
}

fun ProxyPlayer.teleport(uuid: UUID) {
    implementations(PlatformEntityHandler::class.java).teleport(this, uuid)
}