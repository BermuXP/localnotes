package com.bermu.localnotes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "localnotes"
        const val DATABASE_VERSION = 1
    }

    // Create table SQL statement
    private val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ${Note.MyTable.TABLE_NAME} (" +
                "${Note.MyTable.COLUMN_ID} INTEGER PRIMARY KEY," +
                "${Note.MyTable.COLUMN_TITLE} TEXT," +
                "${Note.MyTable.COLUMN_DESCRIPTION} TEXT)"

    // Called when the database is created
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }
}
