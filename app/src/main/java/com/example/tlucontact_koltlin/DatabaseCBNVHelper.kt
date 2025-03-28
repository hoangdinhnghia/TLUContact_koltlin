package com.example.tlucontact_koltlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseCBNVHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CBNV.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "CBNV"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TEN = "ten"
        private const val COLUMN_SDT = "sdt"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_CHUCVU = "chucvu"
        private const val COLUMN_DONVICONGTAC = "donvicongtac"
        private const val COLUMN_ANH = "anh"
        private const val TABLE_USERS = "users"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createUsersTable = """
                    CREATE TABLE  $TABLE_USERS  (
                        $COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT,
                        $COLUMN_EMAIL  TEXT,
                        $COLUMN_PASSWORD TEXT,
                        $COLUMN_USERNAME TEXT
                    )
                """
        db?.execSQL(createUsersTable)

        val insertAdminQuery = """
            INSERT INTO $TABLE_USERS ($COLUMN_EMAIL, $COLUMN_PASSWORD, $COLUMN_USERNAME)
            VALUES ('admin123', 'admin', 'admin')
        """
        db?.execSQL(insertAdminQuery)

        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TEN TEXT,
                $COLUMN_SDT TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_CHUCVU TEXT,
                $COLUMN_DONVICONGTAC TEXT,
                $COLUMN_ANH INTEGER
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addCBNV(cbnv: CBNV): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TEN, cbnv.ten)
            put(COLUMN_SDT, cbnv.sdt)
            put(COLUMN_EMAIL, cbnv.email)
            put(COLUMN_CHUCVU, cbnv.chucVu)
            put(COLUMN_DONVICONGTAC, cbnv.donViCongTac)
            put(COLUMN_ANH, cbnv.anh)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id

    }

    fun getAllCBNV(): List<CBNV> {
        val cbnvList = ArrayList<CBNV>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val cbnv = CBNV(
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_ID)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_TEN)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_SDT)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_CHUCVU)),
                        it.getString(it.getColumnIndexOrThrow(COLUMN_DONVICONGTAC)),
                        it.getInt(it.getColumnIndexOrThrow(COLUMN_ANH))
                    )
                    cbnvList.add(cbnv)
                } while (it.moveToNext())
            }
        }
        db.close()
        return cbnvList
    }

    fun updateCBNV(cbnv: CBNV): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TEN, cbnv.ten)
            put(COLUMN_SDT, cbnv.sdt)
            put(COLUMN_EMAIL, cbnv.email)
            put(COLUMN_CHUCVU, cbnv.chucVu)
            put(COLUMN_DONVICONGTAC, cbnv.donViCongTac)
            put(COLUMN_ANH, cbnv.anh)
        }
        val affectedRows =
            db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(cbnv.id.toString()))
        db.close()
        return affectedRows
    }

    fun deleteCBNV(id: Int): Int {
        val db = writableDatabase
        val affectedRows = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return affectedRows
    }

    fun addUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_EMAIL, username)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_USERNAME, username)
        }
        val result = db.insert(TABLE_USERS, null, contentValues)
        db.close()
        return result != -1L
    }

    fun getUser(email: String, password: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            "users",
            arrayOf("username", "email"),
            "email = ? and password = ?",
            arrayOf(email, password),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            User(username, email)
        } else {
            null
        }
    }
}