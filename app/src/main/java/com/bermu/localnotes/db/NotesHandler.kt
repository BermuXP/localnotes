package com.bermu.localnotes.db

import NoteData
import android.content.ContentValues
import android.content.Context

class NotesHandler(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    /**
     * Inserts the data into the database
     * @param title The title of the note
     * @param description The description of the note
     */
    fun insertData(title: String, description: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Note.Table.COLUMN_TITLE, title)
            put(Note.Table.COLUMN_DESCRIPTION, description)
        }
        db.insert(Note.Table.TABLE_NAME, null, values)
        db.close()
    }

    /**
     * Gets all the data from the database
     * @return The list of notes
     */
    fun getAllData(): List<NoteData> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            Note.Table.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val noteList = mutableListOf<NoteData>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(Note.Table.COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(Note.Table.COLUMN_TITLE))
            val description =
                cursor.getString(cursor.getColumnIndexOrThrow(Note.Table.COLUMN_DESCRIPTION))
            noteList.add(NoteData(id, title, description))
        }
        cursor.close()
        db.close()
        return noteList
    }

    /**
     * Gets the specific data from the database
     * @param noteId The id of the note
     * @return The note data
     */
    fun getSpecificData(noteId: Long): NoteData {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            Note.Table.TABLE_NAME,
            null,
            "${Note.Table.COLUMN_ID}=?",
            arrayOf(noteId.toString()),
            null,
            null,
            null
        )
        var note = NoteData(0, "", "")
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(Note.Table.COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(Note.Table.COLUMN_TITLE))
            val description =
                cursor.getString(cursor.getColumnIndexOrThrow(Note.Table.COLUMN_DESCRIPTION))
            note = NoteData(id, title, description)
        }

        cursor.close()
        db.close()
        return note
    }

    /**
     * Updates the data in the database
     * @param id The id of the note
     * @param title The title of the note
     * @param description The description of the note
     */
    fun updateData(id: Long, title: String, description: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Note.Table.COLUMN_TITLE, title)
            put(Note.Table.COLUMN_DESCRIPTION, description)
        }

        db.update(
            Note.Table.TABLE_NAME,
            values,
            "${Note.Table.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
        db.close()
    }

    /**
     * Deletes the data from the database
     * @param id The id of the note
     */
    fun deleteData(id: Long) {
        val db = dbHelper.writableDatabase
        db.delete(
            Note.Table.TABLE_NAME,
            "${Note.Table.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
        db.close()
    }
}
