package io.izzel.testunits

import io.izzel.testunits.platform.entities
import io.izzel.testunits.platform.entityNearly
import io.izzel.testunits.platform.teleport
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.*
import taboolib.module.nms.*
import taboolib.module.nms.type.LightType
import java.util.*

object TestCommand {

    @Awake(LifeCycle.ENABLE)
    fun c() {
        command("nms") {
            literal("nbt") {
                execute { context, _ ->
                    context.sender.sendMessage("your item nbt is ${context.sender.cast<Player>().itemInHand.getItemTag()}")
                }
            }
            literal("light") {
                literal("create") {
                    execute { context, _ ->
                        context.sender.cast<Player>().location.block.createLight(15, LightType.BLOCK)
                    }
                }
                literal("delete") {
                    execute { context, _ ->
                        context.sender.cast<Player>().location.block.deleteLight(LightType.BLOCK)
                    }
                }
            }
            literal("scoreboard") {
                dynamic(optional = true) {
                    execute { context, argument ->
                        if (context.sender is ProxyPlayer) {
                            context.sender.sendMessage("scoreboard set")
                            context.sender.cast<Player>().sendScoreboard(*argument.split(' ').toTypedArray())
                        }
                    }
                }
                execute { context, _ ->
                    if (context.sender is ProxyPlayer) {
                        context.sender.sendMessage("scoreboard unset")
                        context.sender.cast<Player>().sendScoreboard()
                    }
                }
            }
        }
        // entityTo <uuid>
        command("entityTo") {
            literal("random") {
                execute { context, _ ->
                    if (context.sender is ProxyPlayer) {
                        val entityRandom = (context.sender as ProxyPlayer).entities().randomOrNull()
                        if (entityRandom == null) {
                            context.sender.sendMessage("No Entity")
                        } else {
                            (context.sender as ProxyPlayer).teleport(entityRandom)
                        }
                    } else {
                        context.sender.sendMessage("Not Player")
                    }
                }
            }
            dynamic(optional = true) {
                suggestion {
                    (it.sender as? ProxyPlayer)?.entities()?.map { it.toString() }
                }
                execute { context, argument ->
                    if (context.sender is ProxyPlayer) {
                        try {
                            (context.sender as ProxyPlayer).teleport(UUID.fromString(argument))
                        } catch (ex: IllegalArgumentException) {
                            context.sender.sendMessage("Invalid UUID: [${argument}]")
                        }
                    } else {
                        context.sender.sendMessage("Not Player")
                    }
                }
            }
            execute { context, _ ->
                if (context.sender is ProxyPlayer) {
                    val entityNearly = (context.sender as ProxyPlayer).entityNearly()
                    if (entityNearly == null) {
                        context.sender.sendMessage("No Entity")
                    } else {
                        (context.sender as ProxyPlayer).teleport(entityNearly)
                    }
                } else {
                    context.sender.sendMessage("Not Player")
                }
            }
        }
        command("entityTo2") {
            literal("do") {
                literal("random") {
                    execute { context, _ ->
                        if (context.sender is ProxyPlayer) {
                            val entityRandom = (context.sender as ProxyPlayer).entities().randomOrNull()
                            if (entityRandom == null) {
                                context.sender.sendMessage("No Entity")
                            } else {
                                (context.sender as ProxyPlayer).teleport(entityRandom)
                            }
                        } else {
                            context.sender.sendMessage("Not Player")
                        }
                    }
                }
                dynamic(optional = true) {
                    suggestion {
                        (it.sender as? ProxyPlayer)?.entities()?.map { it.toString() }
                    }
                    restrict { context, argument ->
                        try {
                            UUID.fromString(argument)
                            true
                        } catch (ex: Exception) {
                            false
                        }
                    }
                    execute { context, argument ->
                        if (context.sender is ProxyPlayer) {
                            try {
                                (context.sender as ProxyPlayer).teleport(UUID.fromString(argument))
                            } catch (ex: IllegalArgumentException) {
                                context.sender.sendMessage("Invalid UUID")
                            }
                        } else {
                            context.sender.sendMessage("Not Player")
                        }
                    }
                }
                execute { context, _ ->
                    if (context.sender is ProxyPlayer) {
                        val entityNearly = (context.sender as ProxyPlayer).entityNearly()
                        if (entityNearly == null) {
                            context.sender.sendMessage("No Entity")
                        } else {
                            (context.sender as ProxyPlayer).teleport(entityNearly)
                        }
                    } else {
                        context.sender.sendMessage("Not Player")
                    }
                }
            }
        }
    }
}