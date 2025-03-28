import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tlucontact.Donvi
import com.example.tlucontact_koltlin.DatabaseCBNVHelper
import com.example.tlucontact_koltlin.DatabaseCBNVHelper.Companion


class DatabaseDonviHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Donvi.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Donvi"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TEN = "ten"
        private const val COLUMN_SDT = "sdt"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_DIACHI = "diachi"
        private const val COLUMN_ANH = "anh"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TEN TEXT,
                $COLUMN_SDT TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_DIACHI TEXT,
                $COLUMN_ANH INTEGER
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addDonvi(donvi: Donvi): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TEN, donvi.ten)
            put(COLUMN_SDT, donvi.sdt)
            put(COLUMN_EMAIL, donvi.email)
            put(COLUMN_DIACHI, donvi.diachi)
            put(COLUMN_ANH, donvi.anh)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllDonvi(): List<Donvi> {
        val donviList = ArrayList<Donvi>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val donvi = Donvi(
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_ID)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_TEN)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_SDT)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_DIACHI)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_ANH))
                    )
                    donviList.add(donvi)
                } while (it.moveToNext())
            }
        }
        db.close()
        return donviList
    }

    fun updateDonvi(donvi: Donvi): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TEN, donvi.ten)
            put(COLUMN_SDT, donvi.sdt)
            put(COLUMN_EMAIL, donvi.email)
            put(COLUMN_DIACHI, donvi.diachi)
        }
        val affectedRows = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(donvi.id.toString()))
        db.close()
        return affectedRows
    }

    fun deleteDonvi(id: Int): Int {
        val db = writableDatabase
        val affectedRows = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return affectedRows
    }
}