package com.example.speerapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.speerapplication.R
import com.example.speerapplication.adapter.RecAdapter
import com.example.speerapplication.dataclass.Resource
import com.example.speerapplication.dataclass.UserProfile
import com.example.speerapplication.viewmodel.FollowDetailsViewModel
import com.example.speerapplication.viewmodel.UserSharedViewModel


class FollowDetailsFragment : Fragment() {

    lateinit var recyclerAdapter : RecAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_follow_details, container, false)

        val button = arguments?.getString("button")

        val sharedViewModel: UserSharedViewModel by activityViewModels()
        val userName = sharedViewModel.userName

        val viewModel = ViewModelProvider(this)[FollowDetailsViewModel::class.java]

        viewModel.getFollowersOrFollowing(userName!!,button!!)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerAdapter= RecAdapter(requireContext())
        recyclerView.adapter = recyclerAdapter



        viewModel.followersLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is Resource.Loading ->{
                    //show progress bar
                }
                is Resource.Success ->{
                    Toast.makeText(requireContext(),"Users Found",Toast.LENGTH_SHORT).show()
                    recyclerAdapter.setList(result.value as MutableList<UserProfile>)
                    recyclerAdapter.notifyDataSetChanged()

                }
                is Resource.Failure ->{
                    Toast.makeText(requireContext(),"Users Not Found",Toast.LENGTH_SHORT).show()
                }
            }
        }


        return view
    }


}