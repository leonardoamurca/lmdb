package com.leonardoamurca.lmdb.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.leonardoamurca.lmdb.R
import kotlinx.android.synthetic.main.view_tab_layout.view.*

class HomeTabBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var selectedItem: Items = Items.HOME
        set(value) {
            field = value
            setupSelectedView(value)
        }

    var onTabSelect: OnTabSelectListener? = null

    init {
        View.inflate(context, R.layout.view_tab_layout, this)

        setupSelectedView(selectedItem)

        firstItem.setOnClickListener { onTabSelect?.onSelect(Items.HOME) }
        secondItem.setOnClickListener { onTabSelect?.onSelect(Items.FAVORITES) }
    }

    private fun setupSelectedView(item: Items) {
        firstItem.setImageResource(R.drawable.ic_home_off)
        secondItem.setImageResource(R.drawable.ic_favorite_off)

        when (item) {
            Items.HOME -> firstItem.setImageResource(R.drawable.ic_home_on)
            Items.FAVORITES -> secondItem.setImageResource(R.drawable.ic_favorite_on)
        }
    }

    enum class Items(val position: Int) {
        HOME(ITEM_0),
        FAVORITES(ITEM_1)
    }

    companion object {
        private const val ITEM_0 = 0
        private const val ITEM_1 = 1
    }
}