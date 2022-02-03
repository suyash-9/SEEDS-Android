package com.example.seeds

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seeds.CourseRVAdapter.CourseClickInterface
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.util.ArrayList


class MainActivity : AppCompatActivity(), CourseClickInterface {
    // creating variables for fab, firebase database,
    // progress bar, list, adapter,firebase auth,
    // recycler view and relative layout.
    private var addCourseFAB: FloatingActionButton? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    private var courseRV: RecyclerView? = null
    private var mAuth: FirebaseAuth? = null
    private var loadingPB: ProgressBar? = null
    private var courseRVModalArrayList: ArrayList<CourseRVModal?>? = null
    private var courseRVAdapter: CourseRVAdapter? = null
    private var homeRL: RelativeLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initializing all our variables.
        courseRV = findViewById(R.id.idRVCourses)
        homeRL = findViewById(R.id.idRLBSheet)
        loadingPB = findViewById(R.id.idPBLoading)
        addCourseFAB = findViewById(R.id.idFABAddCourse)
        firebaseDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
        courseRVModalArrayList = ArrayList()
        // on below line we are getting database reference.
        databaseReference = firebaseDatabase!!.getReference("Courses")
        // on below line adding a click listener for our floating action button.
        addCourseFAB?.setOnClickListener(View.OnClickListener { // opening a new activity for adding a course.
            val i = Intent(this@MainActivity, AddCourseActivity::class.java)
            startActivity(i)
        })
        // on below line initializing our adapter class.
        courseRVAdapter = CourseRVAdapter(
            courseRVModalArrayList,
            this,this
        )
        //{ position: Int -> onCourseClick(position) }
        // setting layout malinger to recycler view on below line.
        courseRV?.setLayoutManager(LinearLayoutManager(this))
        // setting adapter to recycler view on below line.
        courseRV?.setAdapter(courseRVAdapter)
        // on below line calling a method to fetch courses from database.
        courses
    }// notifying our adapter when child is moved.// notifying our adapter when child is removed.// this method is called when new child is added

    // we are notifying our adapter and making progress bar
    // visibility as gone.
// on below line we are hiding our progress bar.
    // adding snapshot to our array list on below line.
    // notifying our adapter that data has changed.
    // on below line clearing our list.
    // on below line we are calling add child event listener method to read the data.
    private val courses: Unit
        private get() {
            // on below line clearing our list.
            courseRVModalArrayList!!.clear()
            // on below line we are calling add child event listener method to read the data.
            databaseReference!!.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    // on below line we are hiding our progress bar.
                    loadingPB!!.visibility = View.GONE
                    // adding snapshot to our array list on below line.
                    courseRVModalArrayList!!.add(snapshot.getValue(CourseRVModal::class.java))
                    // notifying our adapter that data has changed.
                    courseRVAdapter?.notifyDataSetChanged()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    // this method is called when new child is added
                    // we are notifying our adapter and making progress bar
                    // visibility as gone.
                    loadingPB!!.visibility = View.GONE
                    courseRVAdapter?.notifyDataSetChanged()
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    // notifying our adapter when child is removed.
                    courseRVAdapter?.notifyDataSetChanged()
                    loadingPB!!.visibility = View.GONE
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    // notifying our adapter when child is moved.
                    courseRVAdapter?.notifyDataSetChanged()
                    loadingPB!!.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

    override fun onCourseClick(position: Int) {
        // calling a method to display a bottom sheet on below line.
        displayBottomSheet(courseRVModalArrayList!![position])
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // adding a click listener for option selected on below line.
        val id = item.itemId
        return when (id) {
            R.id.idLogOut -> {
                // displaying a toast message on user logged out inside on click.
                Toast.makeText(applicationContext, "User Logged Out", Toast.LENGTH_LONG).show()
                // on below line we are signing out our user.
                mAuth!!.signOut()
                // on below line we are opening our login activity.
                val i = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // on below line we are inflating our menu
        // file for displaying our menu options.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun displayBottomSheet(modal: CourseRVModal?) {
        // on below line we are creating our bottom sheet dialog.
        val bottomSheetTeachersDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        // on below line we are inflating our layout file for our bottom sheet.
        val layout: View = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL)
        // setting content view for bottom sheet on below line.
        bottomSheetTeachersDialog.setContentView(layout)
        // on below line we are setting a cancelable
        bottomSheetTeachersDialog.setCancelable(false)
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true)
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show()
        // on below line we are creating variables for
        // our text view and image view inside bottom sheet
        // and initialing them with their ids.
        val courseNameTV = layout.findViewById<TextView>(R.id.idTVCourseName)
        val courseDescTV = layout.findViewById<TextView>(R.id.idTVCourseDesc)
        val suitedForTV = layout.findViewById<TextView>(R.id.idTVSuitedFor)
        val priceTV = layout.findViewById<TextView>(R.id.idTVCoursePrice)
        val courseIV = layout.findViewById<ImageView>(R.id.idIVCourse)
        // on below line we are setting data to different views on below line.
        courseNameTV.text = modal!!.courseName
        courseDescTV.text = modal.courseDescription
        suitedForTV.text = "Suited for " + modal.bestSuitedFor
        priceTV.text = "Rs." + modal.coursePrice
        Picasso.get().load("https://cdn.dribbble.com/users/4051369/screenshots/12908915/media/550f17cad551a1a2d69a84d3117bf9a3.jpg").into(courseIV)
        val viewBtn = layout.findViewById<Button>(R.id.idBtnVIewDetails)
        val editBtn = layout.findViewById<Button>(R.id.idBtnEditCourse)

        // adding on click listener for our edit button.
        editBtn.setOnClickListener { // on below line we are opening our EditCourseActivity on below line.
            val i = Intent(this@MainActivity, EditCourseActivity::class.java)
            // on below line we are passing our course modal
            i.putExtra("course", modal)
            startActivity(i)
        }
        // adding click listener for our view button on below line.
        viewBtn.setOnClickListener { // on below line we are navigating to browser
            // for displaying course details from its url
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(modal.courseLink)
            startActivity(i)
        }
    }
}