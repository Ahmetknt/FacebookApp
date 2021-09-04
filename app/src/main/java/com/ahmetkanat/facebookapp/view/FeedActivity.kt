package com.ahmetkanat.facebookapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmetkanat.facebookapp.R
import com.ahmetkanat.facebookapp.adapter.FeedAdapter
import com.ahmetkanat.facebookapp.databinding.ActivityFeedBinding
import com.ahmetkanat.facebookapp.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FeedActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFeedBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postList : ArrayList<Post>
    private lateinit var feedAdapter : FeedAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore
        postList = ArrayList<Post>()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedAdapter(postList)
        binding.recyclerView.adapter = feedAdapter

        getData()


    }

    fun getData(){

        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if(value != null){
                    if(!value.isEmpty){
                        val documents = value.documents

                        postList.clear()

                        for(document in documents){
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            val post = Post(userEmail,downloadUrl,comment)
                            postList.add(post)
                        }

                        feedAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.feed_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.add_post){

            val intent = Intent(this,UploadActivity::class.java)
            startActivity(intent)

        }else if(item.itemId == R.id.logout){

            auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}