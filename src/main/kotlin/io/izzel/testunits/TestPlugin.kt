package io.izzel.testunits

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffectType
import taboolib.common.TabooLibCommon
import taboolib.common.platform.*
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.SecuredFile
import taboolib.module.nms.getI18nName
import taboolib.module.nms.getInternalKey
import taboolib.module.nms.getInternalName
import taboolib.module.nms.getItemTag
import taboolib.platform.util.modifyMeta
import java.nio.charset.StandardCharsets

object TestPlugin : Plugin() {

    @Config(migrate = true)
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
        info("Config $demo1, $demo2, $demo3")
        try {
            println(ItemStack(Material.STONE).modifyMeta {
                lore = listOf("123")
            }.getItemTag())
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
        info(ItemStack(Material.STONE).getI18nName())
        info(ItemStack(Material.STONE).getInternalKey())
        info(ItemStack(Material.STONE).getInternalName())
        info(Enchantment.ARROW_DAMAGE.getI18nName())
        info(Enchantment.ARROW_DAMAGE.getInternalName())
        info(PotionEffectType.ABSORPTION.getI18nName())
        info(PotionEffectType.ABSORPTION.getInternalName())
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
}