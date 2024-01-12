package com.example.speerapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.speerapplication.R
import com.example.speerapplication.dataclass.Resource
import com.example.speerapplication.viewmodel.SearchUserViewModel
import com.example.speerapplication.viewmodel.UserSharedViewModel


class SearchFragment : Fragment() {
    private var dialog: CustomProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

       val viewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]

        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        dialog = CustomProgressBar(activity)






        val sharedViewModel: UserSharedViewModel by activityViewModels()

        viewModel.userLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is Resource.Loading ->{
                    dialog!!.showDialog()
                }
                is Resource.Success ->{
                    dialog!!.dismissDialog()
                    sharedViewModel.setData(result.value)
                    findNavController().navigate(R.id.action_searchFragment_to_profileFragment)
                }
                 is Resource.Failure ->{
                     dialog!!.dismissDialog()
                     Toast.makeText(activity,"User not found",Toast.LENGTH_SHORT).show()
                     sharedViewModel.setData(null)
                     findNavController().navigate(R.id.action_searchFragment_to_profileFragment)
                }

            }


        }



            return view
    }
}