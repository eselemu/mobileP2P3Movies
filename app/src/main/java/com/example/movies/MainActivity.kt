package com.example.movies

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    lateinit var moviesList : ArrayList<Movie>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        auth = FirebaseAuth.getInstance()

        /*val btnGoEdit = findViewById<Button>(R.id.butGoEdit_Main)
        btnGoEdit.setOnClickListener {
            startActivity(Intent(this, EditActivity:: class.java))
            finish()
        }

        val btnExit = findViewById<Button>(R.id.butExit_Main)
        btnExit.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity:: class.java))
            finish()
        }*/

        setMovies()
    }

    private fun setMovies() {
        moviesList = ArrayList<Movie>()
        var movie = Movie("UwU", "Desde el dia 0", "Romance", "0")
        moviesList.add(movie)

        val list = findViewById<ListView>(R.id.listView_MovieCatalog_Main)
        list.adapter = MovieAdapter(this, moviesList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuItem_Exit){
            auth.signOut()
            startActivity(Intent(this, LoginActivity:: class.java))
            finish()
        }
        else if (item.itemId == R.id.menuItem_Profile){}
        return super.onOptionsItemSelected(item)
    }
}