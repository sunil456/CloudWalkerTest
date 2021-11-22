package io.sunil.cloudwalkertest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.sunil.cloudwalkertest.R
import io.sunil.cloudwalkertest.adapters.PhotosAdapter


import io.sunil.cloudwalkertest.utils.Resource
import io.sunil.cloudwalkertest.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_first_teask.*


class FirstTeaskFragment : Fragment(R.layout.fragment_first_teask   ) {

    lateinit var photoViewModel: PhotoViewModel

    lateinit var photosAdapter: PhotosAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoViewModel = (activity as MainActivity).photoViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        photoViewModel.photos.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data.let { photosResponse ->
                        photosAdapter.differ.submitList(photosResponse)

                        Log.d("SUNIL", "onViewCreated: $photosResponse")

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.d("SUNIL", "onViewCreated: $it")
                    }
                    response.statusCode?.let {
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
        photosAdapter = PhotosAdapter()

        rvPhotos.apply {

            adapter = photosAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }


}
