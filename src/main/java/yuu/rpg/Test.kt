package yuu.rpg

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.IOException
import java.io.FileNotFoundException
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.FileReader
import java.io.BufferedReader
import java.io.File
import java.sql.*


class Test internal constructor(private val plugin: RPG) : Listener {

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerJoin(e: PlayerJoinEvent) {

        val p = e.player
        val uuid = p.uniqueId

        val dbname = "RPG.db" // 利用するデータベースファイル
        var conn: Connection? = null
        var stmt: Statement? = null
        var ps: PreparedStatement? = null

        val sql = "INSERT INTO User_Data values(?)"


        try {
            Class.forName("org.sqlite.JDBC")
            conn = DriverManager.getConnection("jdbc:sqlite:$dbname")
            //   conn.setAutoCommit(false)  //オートコミットはオフ

            stmt = conn!!.createStatement()
            stmt!!.executeUpdate("CREATE TABLE User_Data (uuid VARCHAR(40))")

            ps = conn.prepareStatement(sql)

            ps.setString(1,uuid.toString())

            //INSERT文を実行する
            val i = ps.executeUpdate()

            //処理件数を表示する
            println("結果：$i")

            //コミット
            //conn.commit()



        } catch (e: Exception) {
            //例外発生時の処理
            conn!!.rollback()      //ロールバックする
            e.printStackTrace()  //エラー内容をコンソールに出力する
        } finally {
                if (ps != null) {
                    ps.close()
                }
                if (conn != null) {
                    conn.close()
                }
        }
    }
}
