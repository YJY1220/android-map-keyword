package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "map.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "map_table"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_ADDRESS = "address"
        const val COL_CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableStatement = ("CREATE TABLE $TABLE_NAME ("
                + "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COL_NAME TEXT, "
                + "$COL_ADDRESS TEXT, "
                + "$COL_CATEGORY TEXT)")
        db.execSQL(createTableStatement)

        // 초기 데이터 삽입
        if (isTableEmpty(db)) {
            insertInitialData(db)
        }
    }

    private fun isTableEmpty(db: SQLiteDatabase): Boolean {
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        cursor.moveToFirst()
        val cnt = cursor.getInt(0)
        cursor.close()
        return cnt == 0
    }
    // 데이터베이스 업그레이드 시 기존 데이터 유지
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        for (i in 1..50) {
            val values = ContentValues().apply {
                put(COL_NAME, "")
                put(COL_ADDRESS, "")
                put(COL_CATEGORY, "")
            }
            db.insert(TABLE_NAME, null, values)
        }
    }

    //항목 검색
    fun searchItems(query: String): Cursor {
        val db = this.readableDatabase
        return db.rawQuery( //cursor로 반환
            "SELECT * FROM $TABLE_NAME WHERE $COL_NAME LIKE ?",
            arrayOf("%$query%")
        )
    }
}
