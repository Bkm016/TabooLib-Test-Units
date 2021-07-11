package io.izzel.testunits

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

/**
 * TabooLib_6_0_0_7_Test_Units
 * io.izzel.testunits.TestLifeCycle
 *
 * @author sky
 * @since 2021/6/28 11:00 下午
 */
object TestLifeCycle {

    @Awake(LifeCycle.CONST)
    fun c1() {
        println("Awake CONST")
    }

    @Awake(LifeCycle.INIT)
    fun c2() {
        println("Awake INIT")
    }

    @Awake(LifeCycle.LOAD)
    fun c3() {
        println("Awake LOAD")
    }

    @Awake(LifeCycle.ENABLE)
    fun c4() {
        println("Awake ENABLE")
    }

    @Awake(LifeCycle.ACTIVE)
    fun c5() {
        println("Awake ACTIVE")
    }

    @Awake(LifeCycle.DISABLE)
    fun c6() {
        println("Awake DISABLE")
    }
}