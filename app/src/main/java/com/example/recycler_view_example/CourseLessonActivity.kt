package com.example.recycler_view_example

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recycler_view_example.databinding.ActivityCourseLessonBinding

class CourseLessonActivity: AppCompatActivity() {
    // Instead of findViewById, use new binding method
    private lateinit var binding: ActivityCourseLessonBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCourseLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.webviewCourseLesson.setBackgroundColor(Color.YELLOW)
        val courseLink = intent.getStringExtra(CourseDetailActivity.CourseLessonViewHolder.COURSE_LESSON_LINK_KEY)
        binding.webviewCourseLesson.settings.javaScriptEnabled = true
        binding.webviewCourseLesson.settings.loadWithOverviewMode = true
        binding.webviewCourseLesson.settings.useWideViewPort = true

        if (courseLink != null) {
            binding.webviewCourseLesson.loadUrl(courseLink)
        } else {
            Log.d("DBG","courseLink is null")
        }
//        binding.webviewCourseLesson.loadUrl("https://www.google.com")
    }
}