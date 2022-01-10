package com.example.recycler_view_example

//    "videos":
//    [{"id":1,
//        "name":"Instagram Firebase - Learn How to Create Users, Follow, and Send Push Notifications",
//        "link":"https://www.letsbuildthatapp.com/course/instagram-firebase",
//        "imageUrl":"https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/04782e30-d72a-4917-9d7a-c862226e0a93",
//        "channel":{
//            "name":"Lets Build That App",
//            "profileimageUrl":"https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf",
//            "numberOfSubscribers":100000
//        }
//        "numberOfViews":20000}}
// The vals of the classes below need to case-wise match the JSON fields exactly.
class HomeFeed(val videos: List<Video>)

class Video(val id: Int, val name: String, val link: String, val imageUrl: String, val channel: Channel, val numberOfViews: Int)

class Channel(val name: String, val profileimageUrl: String)

//{
//    name: "Creating a Registration Screen",
//    duration: "21:50",
//    number: 1,
//    imageUrl: "https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/d6938a65-b273-421b-976a-40cf923a17ba_thumbnail",
//    link: "https://www.letsbuildthatapp.com/course_video?id=1022"
//},
// The vals of the class below need to case-wise match the JSON fields exactly.
class CourseLesson(val name: String, val duration: String, val number: Int, val imageUrl: String, val link: String)
