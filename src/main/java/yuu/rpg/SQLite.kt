package yuu.rpg

import java.sql.*
import java.util.ArrayList





object SQLite {

    @Throws(SQLException::class)
    @JvmStatic
    //fun main(args: Array<String>) {
    fun main() {
        val dbname = "RPG.db" // 利用するデータベースファイル
        var conn: Connection? = null
        var stmt: Statement? = null
        try {
            Class.forName("org.sqlite.JDBC")
            conn = DriverManager.getConnection("jdbc:sqlite:$dbname")
            println("接続成功")

            stmt = conn!!.createStatement()
      //      stmt!!.executeUpdate("CREATE TABLE products (pid INTEGER, name VARCHAR(20), price INTEGER, PRIMARY KEY (pid))")
            stmt!!.executeUpdate("CREATE TABLE products (uuid VARCHAR(40))")
            println("テーブル作成")

            stmt!!.executeUpdate("INSERT INTO products VALUES(1, 'AAA', 100)")
            stmt!!.executeUpdate("INSERT INTO products VALUES(2, 'BBB', 80)")
            stmt!!.executeUpdate("INSERT INTO products VALUES(3, 'CCC', 220)")
            println("データ挿入")

            val rs = stmt!!.executeQuery("SELECT * FROM products WHERE price >= 100")
            println("選択")
            while (rs.next()) {
                val pid = rs.getInt("pid")
                val name = rs.getString("name")
                val price = rs.getInt("price")
                println(pid.toString() + "\t" + name + "\t" + price)
                //println(pid + "\t" + name + "\t" + price)
        }
            rs.close()

            stmt!!.executeUpdate("DROP TABLE products")
            println("テーブル削除")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (stmt != null) {
                    stmt!!.close()
                }
                if (conn != null) {
                    conn!!.close()
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * コネクションの取得
     *
     * @throws Exception 実行時例外
     */
    @Throws(Exception::class)
    fun SQL_Connection() {
        var conn: Connection? = null

        try {

            // クラスのロード
            Class.forName("org.sqlite.JDBC")

            // コネクションの取得
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:RPG.db")

            // SELECTのテスト
            SQL_Select(conn)

            println("connectionTest_OK")
        } catch (e: Exception) {
            println("connectionTest_NG")
            throw e
        } finally {
            conn?.close()
        }
    }

    /**
     * データ取得
     *
     * @param conn コネクション
     * @throws SQLException SQL例外
     */
    @Throws(SQLException::class)
    fun SQL_Select(conn: Connection) {
        var stmt: Statement? = null
        var rs: ResultSet? = null

        // 項目名を格納する配列
        var clmnAry: ArrayList<String>? = null
        try {

            // ステートメントの作成
            stmt = conn.createStatement()

            // SQLの実行
            rs = stmt!!.executeQuery("SELECT * FROM TBL")

            // 結果の表示
            while (rs!!.next()) {

                // 初回のみ項目名を取得
                if (clmnAry == null) {
                    clmnAry = ArrayList()
                    val metaData = rs.metaData
                    val columnCount = metaData.columnCount
                    for (i in 0 until columnCount) {
                        System.out.println(metaData.getColumnName(i + 1))
                        clmnAry.add(metaData.getColumnName(i + 1))
                    }
                }

                // データ取得(全項目)
                for (clmn in clmnAry) {
                    println(rs.getObject(clmn))
                }

                // データ取得(項目指定)
                println(rs.getString("IDX"))

                // データ取得(位置指定:1～)
                println(rs.getString(1))
            }

            println("selectTest_OK")
        } catch (e: Exception) {
            println("selectTest_NG")
            throw e
        } finally {
            if (rs != null) {
                rs.close()
                rs = null
            }
            if (stmt != null) {
                stmt.close()
                stmt = null
            }
        }
    }


}