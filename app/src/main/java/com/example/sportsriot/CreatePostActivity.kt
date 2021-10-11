package com.example.sportsriot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.sportsriot.daos.PostDao
import com.example.sportsriot.daos.UserDao
import com.example.sportsriot.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CreatePostActivity : AppCompatActivity() {
    private lateinit var postButton: Button
    private lateinit var postInput:EditText
    private lateinit var sportsNameInput:EditText
    private lateinit var phoneNumberInput:EditText
    private lateinit var postDao: PostDao
    private lateinit var userDao: UserDao
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        auth = Firebase.auth
        postButton = findViewById(R.id.postButton)
        postInput = findViewById(R.id.postInput)
        sportsNameInput = findViewById(R.id.sportsNameInput)
        phoneNumberInput = findViewById(R.id.phoneNumberInput)
        postDao = PostDao()
        userDao = UserDao()

        postButton.setOnClickListener {

            GlobalScope.launch{
                val currentUserDetails = userDao.getUserById(auth.currentUser!!.uid).await().toObject(
                    User::class.java)
                val uid: String = currentUserDetails!!.uid
                val displayName: String = currentUserDetails.displayName.toString()
                val imageUrl: String = currentUserDetails.imageUrl
                val phoneNumber: String = phoneNumberInput.text.toString().trim()
                userDao.addUser(User(uid,displayName,imageUrl,phoneNumber))
                val input = postInput.text.toString().trim()
                val sportsInput = sportsNameInput.text.toString().trim()
                if(input.isNotEmpty()&&sportsInput.isNotEmpty()){
                    postDao.addPost(input,sportsInput)
                    finish()
                }

            }

        }
    }
    override fun onBackPressed() {

        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}