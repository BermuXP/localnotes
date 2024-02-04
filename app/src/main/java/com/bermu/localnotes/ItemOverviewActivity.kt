package com.bermu.localnotes

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ItemOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_overview)

        val titleEditText: EditText = findViewById(R.id.titleEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")

        titleEditText.setText(title)
        descriptionEditText.setText(description)
    }
}