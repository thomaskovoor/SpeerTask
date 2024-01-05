package com.example.speerapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.speerapplication.R
import com.example.speerapplication.dataclass.UserProfile
import com.squareup.picasso.Picasso

class RecAdapter( private val context: Context) : RecyclerView.Adapter<RecAdapter.ViewHolder>() {

    private var followList:MutableList<UserProfile>?=null

    fun setList(list:MutableList<UserProfile>){
        this.followList = list

    }
    fun getList(): MutableList<UserProfile> {
        return this.followList ?: mutableListOf()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return followList?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = followList?.get(position)
        holder.bind(user)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val name: TextView = view.findViewById(R.id.userNameItem)
        private val profileImage: ImageView = view.findViewById(R.id.profileImageItem)

        fun bind(user: UserProfile?) {

            val text = "@"+user?.getUserName()
            name.text = text
            Picasso.get().load(user?.getUserAvatarUrl()).into(profileImage)

        }

    }

}