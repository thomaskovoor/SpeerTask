package com.example.speerapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.speerapplication.R
import com.example.speerapplication.dataclass.Resource
import com.example.speerapplication.viewmodel.SearchUserViewModel
import com.example.speerapplication.viewmodel.UserSharedViewModel

/**
 * `SearchFragment` is a class that extends `Fragment`. It represents a screen in the application where users can search for other users.
 *
 * @property dialog This is a `CustomProgressBar` instance used to show and hide a progress bar dialog during network requests.
 *
 * Inside this function:
 * - The `SearchUserViewModel` is initialized.
 * - The `SearchView` is found by its ID and its focus is cleared.
 * - An `OnQueryTextListener` is set on the `SearchView` to listen for when the user submits a query. When a query is submitted, the `searchUser` function of the `SearchUserViewModel` is called with the query.
 * - The `CustomProgressBar` is initialized with the activity as its context.
 * - The `UserSharedViewModel` is initialized.
 * - An observer is set on the `userLiveData` of the `SearchUserViewModel`. Depending on the state of the `Resource`:
 *   - If it's `Loading`, the progress bar dialog is shown.
 *   - If it's `Success`, the progress bar dialog is dismissed, the data from the `Resource` is set in the `UserSharedViewModel`, and the app navigates to the `ProfileFragment`.
 *   - If it's `Failure`, the progress bar dialog is dismissed, a toast message is shown to the user indicating that the user was not found, the data in the `UserSharedViewModel` is set to null, and the app navigates to the `ProfileFragment`.
 */



class SearchFragment : Fragment() {

    private var dialog: CustomProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        dialog = CustomProgressBar(activity)

        val viewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]
        val sharedViewModel: UserSharedViewModel by activityViewModels()


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
                     sharedViewModel.setData(null)
                     findNavController().navigate(R.id.action_searchFragment_to_profileFragment)
                }

            }


        }



            return view
    }
}