package com.leonardoamurca.lmdb.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.leonardoamurca.lmdb.R
import com.leonardoamurca.lmdb.utils.setPresent
import com.leonardoamurca.lmdb.utils.setVisible
import kotlinx.android.synthetic.main.custom_toolbar.view.backButton
import kotlinx.android.synthetic.main.custom_toolbar.view.title
import kotlinx.android.synthetic.main.custom_toolbar.view.nextButton
import kotlinx.android.synthetic.main.custom_toolbar.view.divider

@SuppressLint("Recycle")
class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var showBackButton: Boolean = true
        set(value) {
            backButton.setVisible(value)
            field = value
        }

    var showNextButton: Boolean = true
        set(value) {
            nextButton.setVisible(value)
            field = value
        }

    var showTitle: Boolean = true
        set(value) {
            title.setPresent(value)
            field = value
        }

    var showDivider: Boolean = true
        set(value) {
            divider.setVisible(value)
            field = value
        }

    var onBackClick: OnClickListener? = null
        set(value) {
            backButton.setOnClickListener(value)
            field = value
        }

    private fun setTitleText(newTitle: String) {
        title.text = newTitle
        showTitle = true
    }

    init {
        View.inflate(context, R.layout.custom_toolbar, this)

        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomToolbar, 0, 0
        )

        showBackButton =
            attributes.getBoolean(R.styleable.CustomToolbar_toolbarShowBackButton, true)

        showNextButton =
            attributes.getBoolean(R.styleable.CustomToolbar_toolbarShowNextButton, false)

        showDivider = attributes.getBoolean(R.styleable.CustomToolbar_toolbarShowDivider, true)

        attributes.getString(R.styleable.CustomToolbar_toolbarTitle)?.let {
            setTitleText(it)
        }

    }

    companion object {
        @JvmStatic
        @BindingAdapter("toolbarTitle")
        fun CustomToolbar.setToolbarTitle(title: String) {
            this.title.text = title
            showTitle = true
        }
    }
}