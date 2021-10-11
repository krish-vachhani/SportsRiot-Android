package com.example.sportsriot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sportsriot.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class PostAdapter(options: FirestoreRecyclerOptions<Post>, private val listener: IPostAdapter) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(
    options
) {
    class PostViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val chatButton: ImageView = itemView.findViewById(R.id.chatButton)
        val sportsName:TextView = itemView.findViewById(R.id.sportsName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder= PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false))
        viewHolder.chatButton.setOnClickListener {
            listener.onChatClicked(snapshots.getSnapshot(viewHolder.bindingAdapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.postText.text = model.text
        holder.userText.text = model.createdBy.displayName
        holder.sportsName.text = model.sportsName
        Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).centerCrop().into(holder.userImage)
        holder.createdAt.text = Utils.getTimeAgo(model.createdAt)



    }

}
interface IPostAdapter{
    fun onChatClicked(postId:String)
}