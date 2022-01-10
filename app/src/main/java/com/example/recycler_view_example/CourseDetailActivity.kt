package com.example.recycler_view_example

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_view_example.databinding.ActivityCourseDetailBinding
import com.example.recycler_view_example.databinding.CourseLessonRowBinding
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException

class CourseDetailActivity : AppCompatActivity() {
    // Instead of findViewById, use new binding method
    private lateinit var binding: ActivityCourseDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.recyclerViewCourseDetail.setBackgroundColor(Color.RED)
        binding.recyclerViewCourseDetail.layoutManager = LinearLayoutManager(
            this@CourseDetailActivity,
            RecyclerView.VERTICAL,
            false
        )
        // We'll change the nav bar title.
        val navBarTitle = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle
        fetchJSON()
    }

    private fun fetchJSON() {
        val videoId = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY, -1)
        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=$videoId"
        Log.d("DBG","$courseDetailUrl")

        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                Log.d("DBG","onResponse for courseDetailUrl request")
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val courseLessons = gson.fromJson(body,Array<CourseLesson>::class.java)
                runOnUiThread{
                    binding.recyclerViewCourseDetail.adapter = CourseDetailAdapter(courseLessons)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.d("DBG","onFailure for courseDetailUrl request $e")
            }
        })
    }

    private class CourseDetailAdapter(val courseLessons: Array<CourseLesson>): RecyclerView.Adapter<CourseLessonViewHolder>() {
        override fun getItemCount(): Int {
            return courseLessons.count()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseLessonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellForRow = CourseLessonRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CourseLessonViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: CourseLessonViewHolder, position: Int) {
            val courseLesson = courseLessons.get(position)
            holder.courseLessonTitle.text = courseLesson.name
            holder.courseLessonDuration.text = courseLesson.duration
            val imageViewThumbNail = holder.courseLessonImageViewThumbnail
            Picasso.get().load(courseLesson.imageUrl).into(imageViewThumbNail)
            holder.courseLesson = courseLesson
        }
    }
    // Had to remove "private" designation from class CourseLessonViewHolder so that CourseLessionAcitivity.kt could
    // access the COURSE_LESSON_LINK_KEY from the intent data.
    class CourseLessonViewHolder(val binding: CourseLessonRowBinding, var courseLesson: CourseLesson? = null): RecyclerView.ViewHolder(binding.root) {
        companion object {
            val COURSE_LESSON_LINK_KEY = "COURSE_LESSON_LINK"
        }

        val courseLessonTitle: TextView = binding.textViewCourseLessonTitle
        val courseLessonDuration: TextView = binding.textViewDuration
        val courseLessonImageViewThumbnail: ImageView = binding.imageViewCourseLessonThumbnail

        init {
            binding.root.setOnClickListener(){
                val intent = Intent(binding.root.context, CourseLessonActivity::class.java)
                intent.putExtra(COURSE_LESSON_LINK_KEY,courseLesson?.link)
                binding.root.context.startActivity(intent)
            }
        }
    }
}