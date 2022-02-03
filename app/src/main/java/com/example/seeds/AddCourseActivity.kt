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


class AddCourseActivity : AppCompatActivity() {
    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private var addCourseBtn: Button? = null
    private var courseNameEdt: TextInputEditText? = null
    private var courseDescEdt: TextInputEditText? = null
    private var coursePriceEdt: TextInputEditText? = null
    private var bestSuitedEdt: TextInputEditText? = null
    private var courseImgEdt: TextInputEditText? = null
    private var courseLinkEdt: TextInputEditText? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    private var loadingPB: ProgressBar? = null
    private var courseID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        // initializing all our variables.
        addCourseBtn = findViewById(R.id.idBtnAddCourse)
        courseNameEdt = findViewById(R.id.idEdtCourseName)
        courseDescEdt = findViewById(R.id.idEdtCourseDescription)
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice)
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor)
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink)
        courseLinkEdt = findViewById(R.id.idEdtCourseLink)
        loadingPB = findViewById(R.id.idPBLoading)
        firebaseDatabase = FirebaseDatabase.getInstance()
        // on below line creating our database reference.
        databaseReference = firebaseDatabase!!.getReference("Courses")
        // adding click listener for our add course button.
        addCourseBtn?.setOnClickListener(View.OnClickListener {
            loadingPB?.setVisibility(View.VISIBLE)
            // getting data from our edit text.
            val courseName = courseNameEdt!!.getText().toString()
            val courseDesc = courseDescEdt!!.getText().toString()
            val coursePrice = coursePriceEdt!!.getText().toString()
            val bestSuited = bestSuitedEdt!!.getText().toString()
            val courseImg = courseImgEdt!!.getText().toString()
            val courseLink = courseLinkEdt!!.getText().toString()
            courseID = courseName
            // on below line we are passing all data to our modal class.
            val courseRVModal = CourseRVModal(
                courseID,
                courseName,
                courseDesc,
                coursePrice,
                bestSuited,
                courseImg,
                courseLink
            )
            // on below line we are calling a add value event
            // to pass data to firebase database.
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // on below line we are setting data in our firebase database.
                    databaseReference!!.child(courseID!!).setValue(courseRVModal)
                    // displaying a toast message.
                    Toast.makeText(this@AddCourseActivity, "Course Added..", Toast.LENGTH_SHORT)
                        .show()
                    // starting a main activity.
                    startActivity(Intent(this@AddCourseActivity, MainActivity::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    // displaying a failure message on below line.
                    Toast.makeText(
                        this@AddCourseActivity,
                        "Fail to add Course..",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        })
    }
}