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

/**
 * `RecAdapter` is a class that extends `RecyclerView.Adapter`. It is used to populate a RecyclerView with data.
 *
 * @property context This is the context in which the RecAdapter is being used.
 * @property itemClickListener This is an instance of `OnItemClickListener` which is used to handle click events on items in the RecyclerView.
 * @property followList This is a mutable list of `UserProfile` objects that represents the data to be displayed in the RecyclerView.
 *
 * @function setList This function is used to set the data for the RecyclerView.
 * @param list The list of `UserProfile` objects to be displayed.
 *
 * @function getList This function is used to get the current data of the RecyclerView.
 * @return The current list of `UserProfile` objects being displayed.
 *
 * @function getItemCount This function returns the total number of items in the data set held by the adapter.
 * @return The total number of items in this adapter.
 *
 * @function onBindViewHolder This function is called by the RecyclerView to display the data at the specified position.
 * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
 * @param position The position of the item within the adapter's data set.
 */

class RecAdapter(private val context: Context, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RecAdapter.ViewHolder>() {

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


        holder.name.setOnClickListener {
            itemClickListener.onItemClicked(user!!)
        }
        holder.profileImage.setOnClickListener {
            itemClickListener.onItemClicked(user!!)
        }
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

         val name: TextView = view.findViewById(R.id.userNameItem)
        val profileImage: ImageView = view.findViewById(R.id.profileImageItem)


        fun bind(user: UserProfile?) {

                val text = "@"+user?.getUserName()
                name.text = text
                Picasso.get().load(user?.getUserAvatarUrl()).into(profileImage)

        }

    }
    interface OnItemClickListener {
        fun onItemClicked(user: UserProfile)
    }

}