package com.example.recycler_view_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler_view_example.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    // Instead of findViewById, use new binding method
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewMain.apply{
            //adapter = MainAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }
        fetchJson()
    }
    private fun fetchJson() {
        println("Attempting to Fetch SJON")
        //https://square.github.io/okhttp/recipes/ for some documentation
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
// The JSON at https://api.letsbuildthatapp.com/youtube/home_feed is:
//        {"user":{
//            "id":1,
//            "name":"Brian Voong",
//            "username":"brianvoong"},
//            "videos":[{
//                "id":1,
//                "name":"Instagram Firebase - Learn How to Create Users, Follow, and Send Push Notifications",
//                "link":"https://www.letsbuildthatapp.com/course/instagram-firebase",
//                "imageUrl":"https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/04782e30-d72a-4917-9d7a-c862226e0a93",
//                "channel":{
//                "name":"Lets Build That App",
//                "profileimageUrl":"https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf",
//                "numberOfSubscribers":100000},
//                "numberOfViews":20000},{
//                "id":2,
//                "name":"Intermediate Training Core Data",
//                "link":"https://www.letsbuildthatapp.com/course/intermediate-training-core-data",
//                "imageUrl":"https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/0736fecb-5b88-483b-a83d-ca2a5a6d93f9",
//                "channel":{
//                "name":"Lets Build That App",
//                "profileimageUrl":"https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf",
//                "numberOfSubscribers":100000},
//                "numberOfViews":5000},{
//                "id":3,
//                "name":"Kindle Basic Training",
//                "link":"https://www.letsbuildthatapp.com/basic-training",
//                "imageUrl":"https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/114bec2f-fbfd-4b13-91de-907fe57c6e37",
//                "channel":{
//                "name":"Lets Build That App",
//                "profileimageUrl":"https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf",
//                "numberOfSubscribers":100000},
//                "numberOfViews":7500}]}
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        // As written the client.newCall is non-blocking. As such, the order of the print statements are:
        // DBG: Next statement is client.newCall
        // DBG: Previous statement is client.newCall
        // DBG: Just assigned homeFeed
        Log.d("DBG","Next statement is client.newCall")
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.use{
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val body = it.body?.string()  // can use "it" instead of "response"
                    println(body)
                    val gson = GsonBuilder().create()
                    val homeFeed = gson.fromJson(body, HomeFeed::class.java)
                    Log.d("DBG","Just assigned homeFeed")
                    runOnUiThread {  // changes to views must be made on user thread
                        binding.recyclerViewMain.adapter = MainAdapter(homeFeed)
                    }
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
        Log.d("DBG","Previous statement was client.newCall")
    }
}

