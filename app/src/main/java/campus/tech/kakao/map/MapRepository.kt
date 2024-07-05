package campus.tech.kakao.map

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MapRepository(context: Context) {
    private val dbHelper = SQLiteHelper(context)

    suspend fun searchItems(query: String): List<MapItem> {
        return withContext(Dispatchers.IO) {
            val cursor = dbHelper.searchItems(query)
            val items = mutableListOf<MapItem>()
            if (cursor.moveToFirst()) {
                do {
                    val item = MapItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_ADDRESS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.COL_CATEGORY))
                    )
                    items.add(item)
                } while (cursor.moveToNext())
            }
            cursor.close()
            items
        }
    }
}
