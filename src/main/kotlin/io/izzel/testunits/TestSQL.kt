package io.izzel.testunits

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.command
import taboolib.common.platform.info
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table
import taboolib.module.database.getHost
import java.lang.Exception

/**
 * TabooLib_6_0_0_7_Test_Units
 * io.izzel.testunits.TestSQL
 *
 * @author sky
 * @since 2021/7/21 2:44 下午
 */
//fixme java.lang.InternalError: Malformed class name
object TestSQL {

    @Awake(LifeCycle.ENABLE)
    fun e() {
        if (TestPlugin.conf.contains("database.host")) {
            val sql = try {
                SQL()
            } catch (ex: Exception) {
                ex.printStackTrace()
                return
            }
            info("Database connected")
            command("sql") {
                literal("set") {
                    dynamic {
                        dynamic {
                            execute { context, argument ->
                                val user = context.argument(-1)
                                sql.setData(user, argument)
                                context.sender.sendMessage("set $user's data to $argument")
                            }
                        }
                    }
                }
                literal("get") {
                    dynamic {
                        execute { context, argument ->
                            context.sender.sendMessage("${argument}'s data is ${sql.getDate(argument)}")
                        }
                    }
                }
            }
        }
    }

    class SQL {

        val host = TestPlugin.conf.getHost("database")

        val table = Table("test", host) {
            add { id() }
            add("user") {
                type(ColumnTypeSQL.VARCHAR, 36) {
                    options(ColumnOptionSQL.UNIQUE_KEY, ColumnOptionSQL.NOTNULL)
                }
            }
            add("data") {
                type(ColumnTypeSQL.VARCHAR, 36) {
                    options(ColumnOptionSQL.NOTNULL)
                }
            }
        }

        val dataSource = host.createDataSource()

        init {
            table.workspace(dataSource) {
                createTable()
            }.run()
        }

        fun hasData(user: String): Boolean {
            return table.workspace(dataSource) {
                select { where { "user" eq user } }
            }.find()
        }

        fun getDate(user: String): String? {
            return table.workspace(dataSource) {
                select { where { "user" eq user } }
            }.firstOrNull {
                getString("data")
            }
        }

        fun setData(user: String, data: String) {
            if (hasData(user)) {
                table.workspace(dataSource) {
                    update {
                        set("data", data)
                        where { "user" eq user }
                    }
                }.run()
            } else {
                table.workspace(dataSource) {
                    insert("user", "data") {
                        value(user, data)
                        finally {
                            generatedKeys.run {
                                next()
                                info("generate id ${getObject(1)}")
                            }
                        }
                    }
                }.run()
            }
        }
    }
}