package com.example.plaintext.data.dao

import android.content.ContentValues
import android.content.Context
import com.example.plaintext.data.Database

class PreferencesDao(context: Context) {
    private val dbHelper = Database(context)

    fun getLogin(): String {
        val db = dbHelper.readableDatabase
        val cursor = db.query(Database.TABLE_PREFERENCES, arrayOf(Database.COLUMN_PREF_LOGIN), "id = 0", null, null, null, null)
        var login = ""
        if (cursor != null && cursor.moveToFirst()) {
            login = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_PREF_LOGIN))
        }
        cursor?.close()
        return login
    }

    fun getPassword(): String {
        val db = dbHelper.readableDatabase
        val cursor = db.query(Database.TABLE_PREFERENCES, arrayOf(Database.COLUMN_PREF_PASSWORD), "id = 0", null, null, null, null)
        var password = ""
        if (cursor != null && cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_PREF_PASSWORD))
        }
        cursor?.close()
        return password
    }

    fun getAutofill(): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.query(Database.TABLE_PREFERENCES, arrayOf(Database.COLUMN_PREF_AUTOFILL), "id = 0", null, null, null, null)
        var autofill = false
        if (cursor != null && cursor.moveToFirst()) {
            autofill = cursor.getInt(cursor.getColumnIndexOrThrow(Database.COLUMN_PREF_AUTOFILL)) == 1
        }
        cursor?.close()
        return autofill
    }

    fun updateLogin(login: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Database.COLUMN_PREF_LOGIN, login)
        }
        val rows = db.update(Database.TABLE_PREFERENCES, values, "id = 0", null)
        if (rows == 0) {
            values.put("id", 0)
            db.insert(Database.TABLE_PREFERENCES, null, values)
        }
    }

    fun updatePassword(password: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Database.COLUMN_PREF_PASSWORD, password)
        }
        val rows = db.update(Database.TABLE_PREFERENCES, values, "id = 0", null)
        if (rows == 0) {
            values.put("id", 0)
            db.insert(Database.TABLE_PREFERENCES, null, values)
        }
    }

    fun updateAutofill(autofill: Boolean) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Database.COLUMN_PREF_AUTOFILL, if (autofill) 1 else 0)
        }
        val rows = db.update(Database.TABLE_PREFERENCES, values, "id = 0", null)
        if (rows == 0) {
            values.put("id", 0)
            db.insert(Database.TABLE_PREFERENCES, null, values)
        }
    }
}
