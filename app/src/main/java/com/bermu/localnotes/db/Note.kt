package com.bermu.localnotes.db

class Note {
    // Define table and column names
    object Table {
        const val TABLE_NAME = "notes"

        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
    }
}