package com.example.speerapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.speerapplication.R
import com.example.speerapplication.dataclass.Resource
import com.example.speerapplication.viewmodel.SearchUserViewModel
import com.example.speerapplication.viewmodel.UserSharedViewModel
import com.squareup.picasso.Picasso

/**
 * `ProfileFragment` is a class that extends `Fragment`. It represents a screen in the application where users can view a profile.
 *
 * @property dialog This is a `CustomProgressBar` instance used to show and hide a progress bar dialog during network requests.
 *  
 * Inside this function:
 * - The `UserSharedViewModel` and `SearchUserViewModel` are initialized.
 * - The `CustomProgressBar` is initialized with the activity as its context.
 * - Various views are found by their IDs.
 * - An observer is set on the `userName` of the `UserSharedViewModel`. When the `userName` changes, the `searchUser` function of the `SearchUserViewModel` is called with the new `userName`.
 * - An observer is set on the `userLiveData` of the `SearchUserViewModel`. Depending on the state of the `Resource`:
 *   - If it's `Loading`, the progress bar dialog is shown.
 *   - If it's `Success`, the progress bar dialog is dismissed, the data from the `Resource` is set in the `UserSharedViewModel`.
 *   - If it's `Failure`, the progress bar dialog is dismissed, a toast message is shown to the user indicating that the user was not found.
 * - An observer is set on the `data` of the `UserSharedViewModel`. If the `data` is null, a "not found" message and image are shown. Otherwise, the user's information is displayed.
 * - Click listeners are set on the `followers` and `following` TextViews. When clicked, they navigate to the `FollowDetailsFragment` with a bundle containing information about which button was clicked and the user's name.
 * - A callback is added to the activity's `onBackPressedDispatcher` that navigates to the `SearchFragment` when the back button is pressed.
 */


class ProfileFragment : Fragment() {
    private var dialog: CustomProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val sharedViewModel: UserSharedViewModel by activityViewModels()
        dialog = CustomProgressBar(activity)





        val notFoundText = view.findViewById<TextView>(R.id.notFoundText)
        val notFoundImage = view.findViewById<ImageView>(R.id.notFoundImage)

        val profileImage = view.findViewById<ImageView>(R.id.profile_image)
        val name = view.findViewById<TextView>(R.id.nameTV)
        val userName = view.findViewById<TextView>(R.id.userNameTV)
        val bio = view.findViewById<TextView>(R.id.descriptionTV)
        val followers = view.findViewById<TextView>(R.id.followersTV)
        val following = view.findViewById<TextView>(R.id.followingTV)

        val searchViewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]

        sharedViewModel.userName.observe(viewLifecycleOwner) { userName ->
            searchViewModel.searchUser(userName.toString())
        }

        searchViewModel.userLiveData.observe(viewLifecycleOwner) { result ->
              when(result){
                  is Resource.Loading ->{
                      dialog!!.showDialog()
                  }
                    is Resource.Success ->{
                        sharedViewModel.setData(result.value)
                        dialog!!.dismissDialog()
                    }
                    is Resource.Failure ->{
                        dialog!!.dismissDialog()
                        Toast.makeText(requireContext(),"User Not Found",Toast.LENGTH_SHORT).show()
                    }
              }
        }

        var nameToSent: String? = null




        sharedViewModel.data.observe(viewLifecycleOwner){

            if(it == null){
                notFoundText.visibility = View.VISIBLE
                notFoundImage.visibility = View.VISIBLE
            }
            else{


                nameToSent = it.getUserName()

                userName.text = it.getUserName()
                name.text = it.getName()
                bio.text = it.getBio()
                val followersText = it.getFollowers().toString()+"followers"
                followers.text = followersText
                val followingText = it.getFollowing().toString()+"following"
                following.text = followingText
                Picasso.get().load(it.getUserAvatarUrl()).into(profileImage)

                userName.visibility = View.VISIBLE
                name.visibility = View.VISIBLE
                bio.visibility = View.VISIBLE
                followers.visibility = View.VISIBLE
                following.visibility = View.VISIBLE
                profileImage.visibility = View.VISIBLE
                dialog!!.dismissDialog()

            }

        }




        followers.setOnClickListener {
            val bundle = Bundle().apply {
                putString("button", "followers")
                putString("name", nameToSent)
            }
       findNavController().navigate(R.id.action_profileFragment_to_followDetailsFragment,bundle)
        }

        following.setOnClickListener {
            val bundle = Bundle().apply {
                putString("button", "following")
                putString("name", nameToSent)
            }
         findNavController().navigate(R.id.action_profileFragment_to_followDetailsFragment,bundle)
        }





        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().navigate(R.id.action_profileFragment_to_searchFragment)
        }
        return view
    }


}