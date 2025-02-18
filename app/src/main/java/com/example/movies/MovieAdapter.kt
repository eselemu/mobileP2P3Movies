package com.example.movies

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MovieAdapter (private val context: Activity, private val moviesList
: ArrayList<Movie>):ArrayAdapter<Movie>(context, R.layout.item, moviesList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater:LayoutInflater = LayoutInflater.from(context)
        val view:View = inflater.inflate(R.layout.item, null)
        view.findViewById<TextView>(R.id.txtView_Name).text = moviesList[position].name.toString()
        view.findViewById<TextView>(R.id.txtView_Year).text = moviesList[position].year.toString()
        view.findViewById<TextView>(R.id.txtView_Genre).text = moviesList[position].genre.toString()

        return view
        //return super.getView(position, convertView, parent)
    }
}