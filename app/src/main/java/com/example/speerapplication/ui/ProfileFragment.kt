package com.example.speerapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.speerapplication.R
import com.example.speerapplication.viewmodel.UserSharedViewModel
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val sharedViewModel: UserSharedViewModel by activityViewModels()


        val notFoundText = view.findViewById<TextView>(R.id.notFoundText)
        val notFoundImage = view.findViewById<ImageView>(R.id.notFoundImage)

        val profileImage = view.findViewById<ImageView>(R.id.profile_image)
        val name = view.findViewById<TextView>(R.id.nameTV)
        val userName = view.findViewById<TextView>(R.id.userNameTV)
        val bio = view.findViewById<TextView>(R.id.descriptionTV)
        val followers = view.findViewById<TextView>(R.id.followersTV)
        val following = view.findViewById<TextView>(R.id.followingTV)



        sharedViewModel.data.observe(viewLifecycleOwner){
            if(it == null){
                notFoundText.visibility = View.VISIBLE
                notFoundImage.visibility = View.VISIBLE
            }
            else{
                userName.visibility = View.VISIBLE
                name.visibility = View.VISIBLE
                bio.visibility = View.VISIBLE
                followers.visibility = View.VISIBLE
                following.visibility = View.VISIBLE
                profileImage.visibility = View.VISIBLE

                sharedViewModel.userName = it.getUserName()

                userName.text = it.getUserName()
                name.text = it.getName()
                bio.text = it.getBio()
                val followersText = it.getFollowers().toString()+"followers"
                followers.text = followersText
                val followingText = it.getFollowing().toString()+"following"
                following.text = followingText
                Picasso.get().load(it.getUserAvatarUrl()).into(profileImage)


            }

        }


        followers.setOnClickListener {
            val bundle = Bundle().apply {
                putString("button", "followers")
            }
       findNavController().navigate(R.id.action_profileFragment_to_followDetailsFragment,bundle)
        }

        following.setOnClickListener {
            val bundle = Bundle().apply {
                putString("button", "following")
            }
         findNavController().navigate(R.id.action_profileFragment_to_followDetailsFragment,bundle)
        }




        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().navigate(R.id.action_profileFragment_to_searchFragment)
        }
        return view
    }


}