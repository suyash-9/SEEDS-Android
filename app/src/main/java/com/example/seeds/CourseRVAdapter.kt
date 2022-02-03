package com.example.seeds

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.ArrayList


class CourseRVAdapter     // creating a constructor.
    (// creating variables for our list, context, interface and position.
    private val courseRVModalArrayList: ArrayList<CourseRVModal?>?,
    private val context: Context,
    private val courseClickInterface: CourseClickInterface
) :
    RecyclerView.Adapter<CourseRVAdapter.ViewHolder>() {
    var lastPos = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file on below line.
        val view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // setting data to our recycler view item on below line.
        val courseRVModal = courseRVModalArrayList?.get(position)
        holder.courseTV.text = courseRVModal?.courseName
        holder.coursePriceTV.text = "" + courseRVModal?.coursePrice
        Picasso.get().load("https://cdn.dribbble.com/users/4051369/screenshots/12908915/media/550f17cad551a1a2d69a84d3117bf9a3.jpg").into(holder.courseIV)
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position)
        holder.courseIV.setOnClickListener { courseClickInterface.onCourseClick(position) }
    }

    private fun setAnimation(itemView: View, position: Int) {
        if (position > lastPos) {
            // on below line we are setting animation.
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            itemView.animation = animation
            lastPos = position
        }
    }

    override fun getItemCount(): Int {
        return courseRVModalArrayList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variable for our image view and text view on below line.
        val courseIV: ImageView
        val courseTV: TextView
        val coursePriceTV: TextView

        init {
            // initializing all our variables on below line.
            courseIV = itemView.findViewById(R.id.idIVCourse)
            courseTV = itemView.findViewById(R.id.idTVCOurseName)
            coursePriceTV = itemView.findViewById(R.id.idTVCousePrice)
        }
    }

    // creating a interface for on click
    interface CourseClickInterface {
        fun onCourseClick(position: Int)
    }
}