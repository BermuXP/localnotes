package com.bermu.localnotes.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class NotesHandler(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun insertData(title: String, description: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Note.MyTable.COLUMN_TITLE, title)
            put(Note.MyTable.COLUMN_DESCRIPTION, description)
        }
        db.insert(Note.MyTable.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllData(): Cursor {
        val db = dbHelper.readableDatabase
        return db.query(
            Note.MyTable.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }

    fun updateData(id: Long, name: String, age: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Note.MyTable.COLUMN_TITLE, name)
            put(Note.MyTable.COLUMN_DESCRIPTION, age)
        }
        db.update(
            Note.MyTable.TABLE_NAME,
            values,
            "${Note.MyTable.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
        db.close()
    }

    fun deleteData(id: Long) {
        val db = dbHelper.writableDatabase
        db.delete(
            Note.MyTable.TABLE_NAME,
            "${Note.MyTable.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
        db.close()
    }
}
