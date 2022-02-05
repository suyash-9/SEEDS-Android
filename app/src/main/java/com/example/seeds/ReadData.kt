package com.example.seeds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import com.riningan.widget.GlowTextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_read_data.*
import java.util.ArrayList



class ReadData : AppCompatActivity() {


    private lateinit var database : DatabaseReference

//    private val courseRVModalArrayList: ArrayList<CourseRVModal>
//        get() {
//            TODO()
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_data)

        database = FirebaseDatabase.getInstance().getReference("Courses")
        var addBtn: Button? = null
        //var showBtn: Button? = null
        addBtn = findViewById(R.id.backbtn)
        //showBtn = findViewById(R.id.showbtn)
         //var regEdt: TextInputEditText? = null
        //regEdt=findViewById(R.id.reg)
        //val registeration = regEdt!!.getText().toString()

        var reg_number:GlowTextView?=null
        var fullname:GlowTextView?=null
        var ageT:GlowTextView?=null
        var bmiT:GlowTextView?=null
        var detailsT:GlowTextView?=null
        var imageView:ImageView?=null

        reg_number=findViewById(R.id.registationTV)
        fullname=findViewById(R.id.username)
        ageT=findViewById(R.id.age)
        bmiT=findViewById(R.id.bmi)
        detailsT=findViewById(R.id.details)
        imageView=findViewById(R.id.idIV)

        val reg_no=intent.getStringExtra("registration_number")
        val full_name=intent.getStringExtra("fullname")
        val age_value=intent.getStringExtra("age")
        val bmi_value=intent.getStringExtra("bmi_value")
        val description=intent.getStringExtra("description")
        val img=intent.getStringExtra("imglink")

        Picasso.get().load(img).into(imageView)

        reg_number.text="RegistrationNumber : "+reg_no.toString()
        fullname.text = "Full Name : "+full_name.toString()
        ageT.text = "Age : "+age_value.toString()
        bmiT.text = "BMI Value : "+bmi_value.toString()
        detailsT.text="Description : \n \n"+description.toString()


        backbtn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

//        showBtn.setOnClickListener{
//
//            if  (registeration.isNotEmpty()){
//
//                database = FirebaseDatabase.getInstance().getReference("Courses")
//                database.child(registeration).get().addOnSuccessListener {
//
//                    if (it.exists()){
//
//                        val name = it.child("coursePrice").value
//                        val bmival = it.child("courseLink").value
//                        val age = it.child("bestSuitedFor").value
//                        val detailsval = it.child("courseDescription").value
//                        Toast.makeText(this,"Successfuly Read",Toast.LENGTH_SHORT).show()
//
//                        fullname.text = name.toString()
//                        ageT.text = bmival.toString()
//                        bmiT.text = age.toString()
//                        detailsT.text=detailsval.toString()
//
//                    }else{
//
//                        Toast.makeText(this,"User Doesn't Exist",Toast.LENGTH_SHORT).show()
//
//
//                    }
//
//                }.addOnFailureListener{
//
//                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
//
//
//                }
//
//            }else{
//
//                Toast.makeText(this,"PLease enter the registration Number",Toast.LENGTH_SHORT).show()
//
//            }
//        }




    }




}