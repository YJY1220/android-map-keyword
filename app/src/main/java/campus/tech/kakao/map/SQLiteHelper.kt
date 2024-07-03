package campus.tech.kakao.map

import android.content.ContentValues
import android.content.Context
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

    // db 처음 생성 시 호출
    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 SQL문
        val createTableStatement = ("CREATE TABLE $TABLE_NAME ("
                + "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COL_NAME TEXT, "
                + "$COL_ADDRESS TEXT, "
                + "$COL_CATEGORY TEXT)")
        db.execSQL(createTableStatement)

        if (isTableEmpty(db)) {
            insertInitialData(db)
        }
    }

    // 테이블이 비어있는지 확인
    private fun isTableEmpty(db: SQLiteDatabase): Boolean {
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_NAME", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count == 0
    }

    // db update 때마다 호출됨
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // 초기 데이터 삽입 메서드
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
}
