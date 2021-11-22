package io.sunil.cloudwalkertest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import io.sunil.cloudwalkertest.R
import io.sunil.cloudwalkertest.repository.PhotoRepository
import io.sunil.cloudwalkertest.viewmodel.PhotoViewModel
import io.sunil.cloudwalkertest.viewmodel.PhotoViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private lateinit var mainActivity: ActivityMainBinding

    lateinit var photoViewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mainActivity = ActivityMainBinding.inflate(layoutInflater)

        val photoRepository = PhotoRepository()

        val viewModelProviderFactory = PhotoViewModelProviderFactory(photoRepository)

        photoViewModel = ViewModelProvider(this, viewModelProviderFactory)[PhotoViewModel::class.java]

        setContentView(R.layout.activity_main)



        bottomNavigationView.setupWithNavController(testNavHostFragment.findNavController())
    }
}