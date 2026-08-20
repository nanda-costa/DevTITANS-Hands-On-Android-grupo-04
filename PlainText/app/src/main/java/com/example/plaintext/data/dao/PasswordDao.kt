package com.example.plaintext.data.dao

import android.content.ContentValues
import android.content.Context
import com.example.plaintext.data.Database
import com.example.plaintext.data.model.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class PasswordDao(context: Context) {
    private val dbHelper = Database(context)
    private val _passwordList = MutableStateFlow<List<Password>>(emptyList())
    val passwordList = _passwordList.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        val db = dbHelper.readableDatabase
        val cursor = db.query(Database.TABLE_PASSWORDS, null, null, null, null, null, null)
        val list = mutableListOf<Password>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Database.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_NAME))
                val login = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_LOGIN))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_PASSWORD))
                val notes = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_NOTES))
                list.add(Password(id, name, login, password, notes))
            } while (cursor.moveToNext())
        }
        cursor?.close()
        _passwordList.value = list
    }

    fun getAll(): Flow<List<Password>> {
        return passwordList
    }

    fun getById(id: Int): Password? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            Database.TABLE_PASSWORDS,
            null,
            "${Database.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        var result: Password? = null
        if (cursor != null && cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_NAME))
            val login = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_LOGIN))
            val passwordText = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_PASSWORD))
            val notes = cursor.getString(cursor.getColumnIndexOrThrow(Database.COLUMN_NOTES))
            result = Password(id, name, login, passwordText, notes)
        }
        cursor?.close()
        return result
    }

    fun insert(password: Password): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Database.COLUMN_NAME, password.name)
            put(Database.COLUMN_LOGIN, password.login)
            put(Database.COLUMN_PASSWORD, password.password)
            put(Database.COLUMN_NOTES, password.notes)
        }
        val id = db.insert(Database.TABLE_PASSWORDS, null, values)
        refresh()
        return id
    }

    fun update(password: Password) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Database.COLUMN_NAME, password.name)
            put(Database.COLUMN_LOGIN, password.login)
            put(Database.COLUMN_PASSWORD, password.password)
            put(Database.COLUMN_NOTES, password.notes)
        }
        db.update(
            Database.TABLE_PASSWORDS,
            values,
            "${Database.COLUMN_ID} = ?",
            arrayOf(password.id.toString())
        )
        refresh()
    }

    fun delete(password: Password): Int {
        val db = dbHelper.writableDatabase
        val count = db.delete(
            Database.TABLE_PASSWORDS,
            "${Database.COLUMN_ID} = ?",
            arrayOf(password.id.toString())
        )
        refresh()
        return count
    }

    fun isEmpty(): Flow<Boolean> {
        return passwordList.map { it.isEmpty() }
    }
}
