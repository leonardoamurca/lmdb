package com.leonardoamurca.lmdb.ui.titlebar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.leonardoamurca.lmdb.R
import com.leonardoamurca.lmdb.databinding.FragmentTitleBarBinding
import com.leonardoamurca.lmdb.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_title_bar.*
import org.koin.android.ext.android.inject

class TitleBarFragment : Fragment() {

    private val viewModel: TitleBarViewModel by inject()

    private lateinit var databinding: FragmentTitleBarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate<FragmentTitleBarBinding>(
            inflater,
            R.layout.fragment_title_bar,
            container,
            false
        ).apply {
            viewmodel = this@TitleBarFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(TITLE)
        backButton.setOnClickListener {
            val intent = Intent(this.context, HomeActivity::class.java)
            startActivity(intent)
        }

        viewModel.init(title)
    }

    companion object {
        private const val TITLE = "TITLE"

        fun newInstance(title: String) = TitleBarFragment().apply {
            arguments = bundleOf(
                TITLE to title
            )
        }
    }
}