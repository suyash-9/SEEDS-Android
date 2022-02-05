package com.example.seeds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import java.util.HashMap


class EditCourseActivity : AppCompatActivity() {
    // creating variables for our edit text, firebase database,
    // database reference, course rv modal,progress bar.
    private var courseNameEdt: TextInputEditText? = null
    private var courseDescEdt: TextInputEditText? = null
    private var coursePriceEdt: TextInputEditText? = null
    private var bestSuitedEdt: TextInputEditText? = null
    private var courseImgEdt: TextInputEditText? = null
    private var courseLinkEdt: TextInputEditText? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    var courseRVModal: CourseRVModal? = null
    private var loadingPB: ProgressBar? = null

    // creating a string for our course id.
    private var courseID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_course)
        // initializing all our variables on below line.
        val addCourseBtn = findViewById<Button>(R.id.idBtnAddCourse)
        courseNameEdt = findViewById(R.id.idEdtCourseName)
        courseDescEdt = findViewById(R.id.idEdtCourseDescription)
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice)
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor)
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink)
        courseLinkEdt = findViewById(R.id.idEdtCourseLink)
        loadingPB = findViewById(R.id.idPBLoading)
        firebaseDatabase = FirebaseDatabase.getInstance()
        // on below line we are getting our modal class on which we have passed.
        courseRVModal = intent.getParcelableExtra("course")
        val deleteCourseBtn = findViewById<Button>(R.id.idBtnDeleteCourse)
        if (courseRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            courseNameEdt?.setText(courseRVModal!!.courseName)
            coursePriceEdt?.setText(courseRVModal!!.coursePrice)
            bestSuitedEdt?.setText(courseRVModal!!.bestSuitedFor)
            courseImgEdt?.setText(courseRVModal!!.courseImg)
            courseLinkEdt?.setText(courseRVModal!!.courseLink)
            courseDescEdt?.setText(courseRVModal!!.courseDescription)
            courseID = courseRVModal!!.courseId
        }

        // on below line we are initialing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase!!.getReference("Courses").child(courseID!!)
        // on below line we are adding click listener for our add course button.
        addCourseBtn.setOnClickListener {
            // on below line we are making our progress bar as visible.
            loadingPB?.setVisibility(View.VISIBLE)
            // on below line we are getting data from our edit text.
            val courseName = courseNameEdt!!.getText().toString()
            val courseDesc = courseDescEdt!!.getText().toString()
            val coursePrice = coursePriceEdt!!.getText().toString()
            val bestSuited = bestSuitedEdt!!.getText().toString()
            val courseImg = courseImgEdt!!.getText().toString()
            val courseLink = courseLinkEdt!!.getText().toString()
            // on below line we are creating a map for
            // passing a data using key and value pair.
            val map: MutableMap<String, Any?> =
                HashMap()
            map["courseName"] = courseName
            map["courseDescription"] = courseDesc
            map["coursePrice"] = coursePrice
            map["bestSuitedFor"] = bestSuited
            map["courseImg"] = courseImg
            map["courseLink"] = courseLink
            map["courseId"] = courseID

            // on below line we are calling a database reference on
            // add value event listener and on data change method
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // making progress bar visibility as gone.
                    loadingPB?.setVisibility(View.GONE)
                    // adding a map to our database.
                    databaseReference!!.updateChildren(map)
                    // on below line we are displaying a toast message.
                    Toast.makeText(this@EditCourseActivity, "Credentials Updated..", Toast.LENGTH_SHORT)
                        .show()
                    // opening a new activity after updating our coarse.
                    startActivity(Intent(this@EditCourseActivity, MainActivity::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    // displaying a failure message on toast.
                    Toast.makeText(
                        this@EditCourseActivity,
                        "Fail to update credentials..",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        // adding a click listener for our delete course button.
        deleteCourseBtn.setOnClickListener { // calling a method to delete a course.
            deleteCourse()
        }
    }

    private fun deleteCourse() {
        // on below line calling a method to delete the course.
        databaseReference!!.removeValue()
        // displaying a toast message on below line.
        Toast.makeText(this, "Credentials Deleted..", Toast.LENGTH_SHORT).show()
        // opening a main activity on below line.
        startActivity(Intent(this@EditCourseActivity, MainActivity::class.java))
    }
}