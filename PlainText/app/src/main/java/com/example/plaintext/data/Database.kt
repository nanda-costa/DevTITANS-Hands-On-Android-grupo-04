package com.example.plaintext.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "plaintext.db"
        const val DATABASE_VERSION = 2
        
        const val TABLE_PASSWORDS = "passwords"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NOTES = "notes"

        const val TABLE_PREFERENCES = "preferences"
        const val COLUMN_PREF_LOGIN = "app_login"
        const val COLUMN_PREF_PASSWORD = "app_password"
        const val COLUMN_PREF_AUTOFILL = "app_autofill"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createPasswordsTable = ("CREATE TABLE $TABLE_PASSWORDS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_LOGIN TEXT, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_NOTES TEXT)")
        db?.execSQL(createPasswordsTable)

        val createPrefsTable = ("CREATE TABLE $TABLE_PREFERENCES (" +
                "id INTEGER PRIMARY KEY DEFAULT 0, " +
                "$COLUMN_PREF_LOGIN TEXT DEFAULT '', " +
                "$COLUMN_PREF_PASSWORD TEXT DEFAULT '', " +
                "$COLUMN_PREF_AUTOFILL INTEGER DEFAULT 0)")
        db?.execSQL(createPrefsTable)
        
        // Insert default row
        db?.execSQL("INSERT INTO $TABLE_PREFERENCES (id, $COLUMN_PREF_LOGIN, $COLUMN_PREF_PASSWORD, $COLUMN_PREF_AUTOFILL) VALUES (0, '', '', 0)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PASSWORDS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PREFERENCES")
        onCreate(db)
    }
}
