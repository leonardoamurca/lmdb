package com.leonardoamurca.lmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.leonardoamurca.lmdb.databinding.ActivityMainBinding
import com.leonardoamurca.lmdb.navigation.Navigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigator: Navigator by inject()

    private val viewModel: MainViewModel by inject()

    private lateinit var databinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            viewmodel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }
        navigator.activity = this

        viewModel.init()
    }


    override fun onDestroy() {
        super.onDestroy()
        navigator.activity = null // Avoid memory leaks
    }
}