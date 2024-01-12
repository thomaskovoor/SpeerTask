package com.example.speerapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.speerapplication.R
import com.example.speerapplication.adapter.RecAdapter
import com.example.speerapplication.dataclass.Resource
import com.example.speerapplication.dataclass.UserProfile
import com.example.speerapplication.viewmodel.FollowDetailsViewModel
import com.example.speerapplication.viewmodel.UserSharedViewModel

/**
 * `FollowDetailsFragment` is a class that extends `Fragment` and implements `RecAdapter.OnItemClickListener`. It represents a screen in the application where users can view a list of followers or following users.
 *
 * @property recyclerAdapter This is an instance of `RecAdapter` used to populate the RecyclerView with data.
 * @property sharedViewModel This is an instance of `UserSharedViewModel` shared between fragments.
 * @property dialog This is a `CustomProgressBar` instance used to show and hide a progress bar dialog during network requests.
 *  
 * Inside this function:
 * - The `FollowDetailsViewModel` is initialized.
 * - The `CustomProgressBar` is initialized with the activity as its context.
 * - The `button` and `userName` are retrieved from the fragment's arguments.
 * - The `getFollowersOrFollowing` function of the `FollowDetailsViewModel` is called with the `userName` and `button`.
 * - The RecyclerView is initialized with a `LinearLayoutManager` and the `RecAdapter`.
 * - An `OnScrollListener` is added to the RecyclerView. When the user scrolls to the end of the list, the `loadNextPage` function of the `FollowDetailsViewModel` is called.
 * - An observer is set on the `followersLiveData` of the `FollowDetailsViewModel`. Depending on the state of the `Resource`:
 *   - If it's `Loading`, the progress bar dialog is shown.
 *   - If it's `Success`, the progress bar dialog is dismissed, the data from the `Resource` is added to the current list in the `RecAdapter`, and the `RecAdapter` is notified of the data change.
 *   - If it's `Failure`, the progress bar dialog is dismissed, a toast message is shown to the user indicating that the users were not found.
 *
 * @function onItemClicked This function is called when an item in the RecyclerView is clicked. It sets the `userName` in the `UserSharedViewModel` and navigates to the `ProfileFragment`.
 *
 * @param user The `UserProfile` of the clicked item.
 */


class FollowDetailsFragment : Fragment(), RecAdapter.OnItemClickListener {

    lateinit var recyclerAdapter : RecAdapter
    private val sharedViewModel: UserSharedViewModel by activityViewModels()
    private var dialog: CustomProgressBar? = null





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_follow_details, container, false)
        dialog = CustomProgressBar(activity)


        val button = arguments?.getString("button")


        val userName = arguments?.getString("name")

        val viewModel = ViewModelProvider(this)[FollowDetailsViewModel::class.java]

        viewModel.getFollowersOrFollowing(userName!!,button!!)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerAdapter= RecAdapter(requireContext(),this)
        recyclerView.adapter = recyclerAdapter


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (totalItemCount <= lastVisibleItem + 2) {
                    viewModel.loadNextPage(userName, button)
                }
            }
        })



        viewModel.followersLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is Resource.Loading ->{
                    dialog!!.showDialog()

                }
                is Resource.Success ->{
                    val currentList = recyclerAdapter.getList()
                    currentList.addAll(result.value)
                    recyclerAdapter.setList(currentList)
                    dialog!!.dismissDialog()
                    recyclerAdapter.notifyDataSetChanged()



                }
                is Resource.Failure ->{
                    dialog!!.dismissDialog()
                    Toast.makeText(requireContext(),"Users Not Found",Toast.LENGTH_SHORT).show()
                }
            }
        }


        return view
    }

    override fun onItemClicked(user: UserProfile) {
        sharedViewModel.setUserName(user.getUserName())
        findNavController().navigate(R.id.action_followDetailsFragment_to_profileFragment)
    }


}