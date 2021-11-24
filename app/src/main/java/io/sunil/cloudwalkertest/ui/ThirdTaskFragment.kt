package io.sunil.cloudwalkertest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import io.sunil.cloudwalkertest.R
import io.sunil.cloudwalkertest.adapters.PhotosAdapter
import io.sunil.cloudwalkertest.utils.Resource
import io.sunil.cloudwalkertest.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_first_teask.progressBar
import kotlinx.android.synthetic.main.fragment_third_task.*


class ThirdTaskFragment : Fragment(R.layout.fragment_third_task) {

    lateinit var photoViewModel: PhotoViewModel

    lateinit var photosAdapter: PhotosAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photoViewModel = (activity as MainActivity).photoViewModel
//        photoViewModel.fetchUsersPhotos()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        photoViewModel.fetchUsersPhotos()



        photoViewModel.userPhotos.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data.let { photosResponse ->
                        photosAdapter.differ.submitList(photosResponse)

                        Log.d("SUNIL", "onViewCreated Third Task: $photosResponse")

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.d("SUNIL", "onViewCreated: $it")
                    }
                    response.statusCode?.let {
                        if(it == 404)
                            Log.d("SUNIL", "onViewCreated: $it")

                        if (it == 403)
                            Log.d("SUNIL", "onViewCreated: $it")

                        if (it == 502)
                            Log.d("SUNIL", "onViewCreated: $it")


                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun setUpRecyclerView(){
        photosAdapter = PhotosAdapter(1)

        rvOnlyPhotos.apply {
            adapter = photosAdapter
            layoutManager = GridLayoutManager(activity, 5)

        }

    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }


}