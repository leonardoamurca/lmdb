package com.leonardoamurca.lmdb.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.leonardoamurca.lmdb.R
import com.leonardoamurca.lmdb.databinding.ActivityHomeBinding
import com.leonardoamurca.lmdb.ui.trending.TrendingMoviesActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val viewmodel: HomeViewModel by inject()

    private lateinit var databinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databinding = DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        ).apply {
            viewmodel = this@HomeActivity.viewmodel
            lifecycleOwner = this@HomeActivity
        }

        goToTrendingMoviesButton.setOnClickListener {
            val intent = Intent(this, TrendingMoviesActivity::class.java)
            startActivity(intent)
        }
    }
}