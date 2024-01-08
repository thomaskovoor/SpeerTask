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
import com.facebook.shimmer.ShimmerFrameLayout


class FollowDetailsFragment : Fragment(), RecAdapter.OnItemClickListener {

    lateinit var recyclerAdapter : RecAdapter
    private val sharedViewModel: UserSharedViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_follow_details, container, false)

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
                    //show progress bar
                }
                is Resource.Success ->{
                    Toast.makeText(requireContext(),"Users Found",Toast.LENGTH_SHORT).show()
                    val currentList = recyclerAdapter.getList()
                    currentList.addAll(result.value)
                    recyclerAdapter.setList(currentList)
                    recyclerAdapter.notifyDataSetChanged()

                }
                is Resource.Failure ->{
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