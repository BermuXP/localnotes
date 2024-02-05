package com.bermu.localnotes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bermu.localnotes.db.NotesHandler
import com.onegravity.rteditor.RTEditText
import com.onegravity.rteditor.RTManager
import com.onegravity.rteditor.api.RTApi
import com.onegravity.rteditor.api.RTProxyImpl

class NoteActivity : AppCompatActivity() {

    private lateinit var noteHandler: NotesHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_overview)
        noteHandler = NotesHandler(this)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val titleEditText : EditText= findViewById(R.id.titleEditText)

        val rtApi = RTApi(this, RTProxyImpl(this), null)
        val rtManager = RTManager(rtApi, savedInstanceState)


        val toolbarContainer: ViewGroup = findViewById(R.id.rteContainer)

        val descriptionEditText: RTEditText = findViewById(R.id.descriptionEditText)
        rtManager.registerEditor(descriptionEditText, true)

        val id = intent.getLongExtra("id", 0)
        if (id > 0) {
            val mappedData = noteHandler.getSpecificData(id)
            titleEditText.setText(mappedData.title)
            descriptionEditText.setRichTextEditing(true, mappedData.description)


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
                    noteHandler.updateData(
                        id,
                        newText,
                        descriptionEditText.text.toString()
                    )
                }
            })

            // Set up any additional configurations or listeners as needed
            // For example:
            descriptionEditText.addTextChangedListener(object : TextWatcher {
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
                    noteHandler.updateData(
                        id,
                        titleEditText.text.toString(),
                        newText
                    )
                }
            })
        } else {
            val saveButton = findViewById<Button>(R.id.saveNoteButton)
            saveButton.visibility = Button.VISIBLE
            saveButton.setOnClickListener {
                NotesHandler(this).insertData(
                    titleEditText.text.toString(),
                    descriptionEditText.text.toString()
                )
                finish()
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // todo check if the note has been updated
                // Back is pressed... Finishing the activity
                dialogBack()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the back button click
                dialogBack()
                true
            }
            // Add other menu item handling here if needed
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Shows a dialog when the back button is pressed
     */
    private fun dialogBack() {
        // Create an AlertDialog
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(getString(R.string.you_have_unsaved_changes))
        alertDialogBuilder.setMessage(getString(R.string.return_desc))
        alertDialogBuilder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            finish()
        }
        alertDialogBuilder.setNegativeButton(R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        // Show the AlertDialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}