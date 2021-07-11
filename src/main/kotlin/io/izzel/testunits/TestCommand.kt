package io.izzel.testunits

import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PermissionDefault
import taboolib.common.platform.command
import taboolib.common.platform.onlinePlayers
import taboolib.module.kether.*
import taboolib.module.lang.sendLang
import taboolib.module.nms.createLight
import taboolib.module.nms.deleteLight
import taboolib.module.nms.getItemTag
import java.lang.Exception

object TestCommand {

    @Awake(LifeCycle.ENABLE)
    fun c() {
        command("test") {
            execute {
                sender.sendMessage("test")
            }
        }
        command("test1") {
            literal("demo1") {
                execute {
                    sender.sendMessage("online players: ${onlinePlayers().map { it.name }}, your name ${sender.name}")
                }
            }
            required {
                complete {
                    (1..10).map { it.toString() }
                }
                restrict {
                    isInt()
                }
                execute {
                    sender.sendMessage("your num is $argument")
                }
            }
        }
        command("test2", aliases = emptyList(), permissionDefault = PermissionDefault.TRUE) {
            literal("demo1") {
                literal("demo2") {
                    literal("demo3") {
                        execute {
                            sender.sendMessage("demo3")
                        }
                    }
                    optional {
                        complete {
                            (1..10).map { it.toString() }
                        }
                        restrict {
                            isInt()
                        }
                        execute {
                            sender.sendMessage("your num is $argument")
                        }
                    }
                }
                optional {
                    execute {
                        sender.sendMessage(argument)
                    }
                }
            }
        }
        command("test3") {
            literal("kether") {
                execute {
                    try {
                        sender.sendMessage(KetherFunction.parse("your name is {{ sender }}", sender = sender))
                        KetherShell.eval("tell *HelloWorld!", sender = sender)
                    } catch (ex: Exception) {
                        ex.printKetherErrorMessage()
                    }
                }
            }
            literal("lang") {
                execute {
                    sender.sendLang("test1")
                    sender.sendLang("test2", sender.name)
                }
            }
            literal("nbt") {
                execute {
                    sender.sendMessage("your item nbt is ${(sender.origin as Player).itemInHand.getItemTag()}")
                }
            }
            literal("light") {
                literal("create") {
                    execute {
                        (sender.origin as Player).location.block.createLight(15)
                    }
                }
                literal("delete") {
                    execute {
                        (sender.origin as Player).location.block.deleteLight()
                    }
                }
            }
        }
    }
}