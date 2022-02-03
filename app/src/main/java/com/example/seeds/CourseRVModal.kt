package com.example.seeds

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator


class CourseRVModal : Parcelable {
    // creating getter and setter methods.
    // creating variables for our different fields.
    var courseName: String? = null
    var courseDescription: String? = null
    var coursePrice: String? = null
    var bestSuitedFor: String? = null
    var courseImg: String? = null
    var courseLink: String? = null
    var courseId: String? = null

    // creating an empty constructor.
    constructor() {}
    protected constructor(`in`: Parcel) {
        courseName = `in`.readString()
        courseId = `in`.readString()
        courseDescription = `in`.readString()
        coursePrice = `in`.readString()
        bestSuitedFor = `in`.readString()
        courseImg = `in`.readString()
        courseLink = `in`.readString()
    }

    constructor(
        courseId: String?,
        courseName: String?,
        courseDescription: String?,
        coursePrice: String?,
        bestSuitedFor: String?,
        courseImg: String?,
        courseLink: String?
    ) {
        this.courseName = courseName
        this.courseId = courseId
        this.courseDescription = courseDescription
        this.coursePrice = coursePrice
        this.bestSuitedFor = bestSuitedFor
        this.courseImg = courseImg
        this.courseLink = courseLink
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(courseName)
        dest.writeString(courseId)
        dest.writeString(courseDescription)
        dest.writeString(coursePrice)
        dest.writeString(bestSuitedFor)
        dest.writeString(courseImg)
        dest.writeString(courseLink)
    }

    companion object {
        @SuppressLint("ParcelCreator")
        @JvmField
        val CREATOR: Creator<CourseRVModal?> = object : Creator<CourseRVModal?> {
            override fun createFromParcel(`in`: Parcel): CourseRVModal? {
                return CourseRVModal(`in`)
            }

            override fun newArray(size: Int): Array<CourseRVModal?> {
                return arrayOfNulls(size)
            }
        }
    }


}