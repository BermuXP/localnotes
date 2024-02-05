package com.bermu.localnotes

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bermu.localnotes.db.NotesHandler

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.addNoteButton).setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }
        refreshList()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // Create your custom animation.
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 200L

                // Call SplashScreenView.remove at the end of your custom animation.
                slideUp.doOnEnd { splashScreenView.remove() }

                // Run your animation.
                slideUp.start()
            }
        }
    }

    /**
     * Refreshes the list of notes
     */
    fun refreshList() {
        val noteList = NotesHandler(this).getAllData()
        recyclerView.adapter = NoteAdapter(this, noteList)
    }

    override fun onRestart() {
        super.onRestart()
        refreshList()
    }
}