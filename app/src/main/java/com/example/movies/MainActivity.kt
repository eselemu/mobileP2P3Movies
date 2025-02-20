package com.example.movies

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    lateinit var moviesList : ArrayList<Movie>

    val database = Firebase.database
    val myRef = database.getReference("movies")

    lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbar = findViewById<Toolbar>(R.id.toolbar_Main)
        setSupportActionBar(toolbar)


        auth = FirebaseAuth.getInstance()

        readDB()
    }

    private fun fillMoviesList(){
        val list = findViewById<ListView>(R.id.listView_MovieCatalog_Main)
        list.adapter = MovieAdapter(this, moviesList)
    }

    private fun readDB(){
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                moviesList = ArrayList<Movie>()
                snapshot.children.forEach{
                    child ->
                    var movie = Movie(child.child("name").value.toString() ?: "",
                        child.child("year").value.toString() ?: "",
                        child.child("genre").value.toString() ?: "",
                        child.child("img").value.toString() ?: "",
                        child.key.toString() ?: "")
                    moviesList.add(movie)
                }
                fillMoviesList()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_nav)
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