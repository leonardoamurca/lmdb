package com.leonardoamurca.lmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.isOnBackstack
import com.leonardoamurca.lmdb.databinding.ActivityMainBinding
import com.leonardoamurca.lmdb.navigation.Navigator
import com.leonardoamurca.lmdb.ui.HomeTabBarView
import com.leonardoamurca.lmdb.ui.home.HomeFragment
import com.leonardoamurca.lmdb.ui.trending.TrendingMoviesFragment
import com.leonardoamurca.lmdb.utils.subscribe
import kotlinx.android.synthetic.main.activity_main.*
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

        val openedTab = getHomeTabItem()
        viewModel.init(openedTab)
        homeTabBar.selectedItem = openedTab

        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.selectedPosition.subscribe(this) {
            databinding.homeTabBar.selectedItem = this
        }
    }

    private fun getHomeTabItem(): HomeTabBarView.Items {
        val fragment = supportFragmentManager.primaryNavigationFragment

        return getTabFrom(fragment)
    }

    private fun getTabFrom(fragment: Fragment?): HomeTabBarView.Items {
        if (fragment == null) {
            return HomeTabBarView.Items.HOME
        }

        return when (fragment) {
            is HomeFragment -> HomeTabBarView.Items.HOME
            is TrendingMoviesFragment -> HomeTabBarView.Items.FAVORITES
            else -> HomeTabBarView.Items.HOME
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        navigator.activity = null // Avoid memory leaks
    }

    override fun onBackPressed() {

        val handled = recursivelyDispatchOnBackPressed(supportFragmentManager)
        if (!handled) {
            super.onBackPressed()
        }
    }

    private fun recursivelyDispatchOnBackPressed(fm: FragmentManager): Boolean {
        if (fm.backStackEntryCount == 0)
            return false

        val reverseOrder =
            fm.fragments.filter { it is OnBackPressed && it.isOnBackstack() }.reversed()
        for (f in reverseOrder) {
            val handledByChildFragments = recursivelyDispatchOnBackPressed(f.childFragmentManager)
            if (handledByChildFragments) {
                return true
            }

            val backpressable = f as OnBackPressed
            if (backpressable.onBackPressed()) {
                return true
            }
        }
        return false
    }
}