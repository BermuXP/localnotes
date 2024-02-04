package com.bermu.localnotes.db

import NoteData
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

    fun getAllData(): List<NoteData> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            Note.MyTable.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val noteList = mutableListOf<NoteData>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(Note.MyTable.COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(Note.MyTable.COLUMN_TITLE))
            val description =
                cursor.getString(cursor.getColumnIndexOrThrow(Note.MyTable.COLUMN_DESCRIPTION))
            noteList.add(NoteData(id, title, description))
        }
        cursor.close()
        db.close()
        return noteList
    }

    fun getSpecificData(noteId: Long): NoteData {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            Note.MyTable.TABLE_NAME,
            null,
            "${Note.MyTable.COLUMN_ID}=?",
            arrayOf(noteId.toString()),
            null,
            null,
            null
        )
        var note = NoteData(0, "", "")
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(Note.MyTable.COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(Note.MyTable.COLUMN_TITLE))
            val description =
                cursor.getString(cursor.getColumnIndexOrThrow(Note.MyTable.COLUMN_DESCRIPTION))
            note = NoteData(id, title, description)
        }

        cursor.close()
        db.close()
        return note
    }

    fun updateData(id: Long, title: String, description: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Note.MyTable.COLUMN_TITLE, title)
            put(Note.MyTable.COLUMN_DESCRIPTION, description)
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
