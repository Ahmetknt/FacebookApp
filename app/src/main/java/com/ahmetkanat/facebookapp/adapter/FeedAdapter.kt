package com.ahmetkanat.facebookapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetkanat.facebookapp.databinding.RecyclerRowBinding
import com.ahmetkanat.facebookapp.model.Post
import com.squareup.picasso.Picasso

class FeedAdapter(val postList : ArrayList<Post>) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {


    class FeedHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FeedHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        holder.binding.recyclerViewComment.text = postList.get(position).comment
        holder.binding.recyclerViewUserMail.text = postList.get(position).userEmail
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.recyclerViewImageView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}