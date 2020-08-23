package com.leonardoamurca.lmdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonardoamurca.lmdb.R
import com.leonardoamurca.lmdb.databinding.FragmentHomeBinding
import com.leonardoamurca.lmdb.ui.adapter.CollectionAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private lateinit var databinding: FragmentHomeBinding

    private val viewModel: HomeViewModel by inject()

    private lateinit var collectionAdapter: CollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        ).apply {
            viewmodel = this@HomeFragment.viewModel
            lifecycleOwner = this@HomeFragment
        }

        viewModel.viewModelScope.launch {
            viewModel.fetchCollections()
        }

        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeList()
        setupObservers()
    }

    private fun initializeList() {
        collectionAdapter = CollectionAdapter(viewModel::showSelectedMovie, viewModel::navigate)
        databinding.adapter = collectionAdapter
        databinding.rvParent.layoutManager =
            LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
            )
    }

    private fun setupObservers() {
        viewModel.collections.observe(viewLifecycleOwner, Observer {
            it.let(collectionAdapter::submitList)
        })
    }
}

