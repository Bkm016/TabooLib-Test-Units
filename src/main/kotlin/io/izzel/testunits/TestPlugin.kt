package io.izzel.testunits

import taboolib.common.LifeCycle
import taboolib.common.TabooLibCommon
import taboolib.common.platform.*
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.SecuredFile
import java.io.File
import java.nio.charset.StandardCharsets

object TestPlugin : Plugin() {

    @Config
    lateinit var conf: SecuredFile
        private set

    @ConfigNode
    var demo1 = 0
        private set

    @ConfigNode
    var demo2 = 0
        private set

    @ConfigNode
    var demo3 = 0
        private set

    override fun onLoad() {
        info("onLoad()")
        submit {
            info("submit info from onLoad()")
        }
    }

    override fun onEnable() {
        info("inEnable()")
        submit {
            warning("submit warning from onEnable()")
        }
        if (TabooLibCommon.isKotlinEnvironment()) {
            info("isKotlinEnvironment")
        } else {
            info("isNotKotlinEnvironment")
        }
        // platform IO 测试
        info(releaseResourceFile("resources/test").readText(StandardCharsets.UTF_8))
        info(getJarFile())
        info(getDataFolder())
        info(server())
        info(console())
    }

    override fun onActive() {
        info("onActive()")
        submit {
            severe("submit severe from onActive()")
        }
    }

    override fun onDisable() {
        info("onDisable()")
    }

    @Awake(LifeCycle.ENABLE)
    fun c() {
        command("config") {
            literal("test") {
                execute {
                    info("config test $demo1, $demo2, $demo3")
                }
            }
            literal("reload") {
                execute {
                    conf.load(File(getDataFolder(), "config.yml"))
                    sender.sendMessage("reload ok")
                }
            }
            optional {
                sender.sendMessage("argument $argument")
            }
        }
    }
}