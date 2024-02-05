package com.bermu.localnotes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * The database helper class
 * @param context The context of the application
 */
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "localnotes"

        // Update whenever there is a database update.
        const val DATABASE_VERSION = 1
    }

    // Create table SQL statement
    private val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ${Note.Table.TABLE_NAME} (" +
            "${Note.Table.COLUMN_ID} INTEGER PRIMARY KEY," +
            "${Note.Table.COLUMN_TITLE} TEXT," +
            "${Note.Table.COLUMN_DESCRIPTION} TEXT)"


    // Create the table
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }
}
