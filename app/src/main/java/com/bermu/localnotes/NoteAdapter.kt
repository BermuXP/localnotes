package com.bermu.localnotes

import NoteData
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bermu.localnotes.db.NotesHandler

/**
 * The adapter for the recycler view
 * @param context The context of the application
 * @param items The list of notes
 */
class NoteAdapter(private val context: Context, private val items: List<NoteData>) :
    RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]

        holder.titleTextView.text = currentItem.title
        holder.descriptionTextView.text = currentItem.description

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra("id", currentItem.id)
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            // Create an AlertDialog
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(context.getString(R.string.delete_note))
            alertDialogBuilder.setMessage(context.getString(R.string.delete_note_desc))
            alertDialogBuilder.setPositiveButton(context.getString(R.string.yes)) { dialog, which ->
                val noteHandler = NotesHandler(context)
                noteHandler.deleteData(currentItem.id)
                (context as MainActivity).refreshList()
            }
            alertDialogBuilder.setNegativeButton(context.getString(R.string.no)) { dialog, which ->
                // If the user presses "No", dismiss the dialog
                dialog.dismiss()
            }

            // Show the AlertDialog
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * The view holder for the recycler view
     * @param itemView The view of the item
     */
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textDescription)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }
}
