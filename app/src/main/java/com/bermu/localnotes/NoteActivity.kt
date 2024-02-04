package com.bermu.localnotes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bermu.localnotes.db.NotesHandler
import com.onegravity.rteditor.RTEditText

class NoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_overview)

        val titleEditText = findViewById<EditText>(R.id.titleEditText)
        val descriptionEditText = findViewById<RTEditText>(R.id.descriptionEditText)

        val id = intent.getLongExtra("id", 0)
        if (id > 0) {
            val mappedData = NotesHandler(this).getSpecificData(id)
            titleEditText.setText(mappedData.title)
            descriptionEditText.setRichTextEditing(true, mappedData.description)
            val saveButton = findViewById<Button>(R.id.saveNoteButton)
            saveButton.visibility = Button.VISIBLE
            saveButton.setOnClickListener {
                NotesHandler(this).updateData(
                    id,
                    titleEditText.text.toString(),
                    descriptionEditText.text.toString()
                )
                finish()
            }
        } else {
            titleEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    val newText = s.toString()
                    // Do something with newText
//                    NotesHandler.updateData(
//                        id,
//                        titleEditText.text.toString(),
//                        descriptionEditText.text.toString()
//                    )
                }
            })
        }

    }

}