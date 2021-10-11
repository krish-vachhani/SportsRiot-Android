package com.example.sportsriot.models

data class Post(val text:String = "",
                val createdBy: User = User(),
                val createdAt:Long = 0L,
                val sportsName:String = "",
                val contacts:ArrayList<String> = ArrayList())