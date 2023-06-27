package com.hearhere.presentation.features.post

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlin.collections.ArrayList

class SearchMusicViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    var list: ArrayList<Fragment> = arrayListOf(SearchTitleFragment(), SearchSingerFragment())

    override fun getItemCount(): Int = 2

    fun setList(fragments: Array<Fragment>) {
        list.clear()
        list.addAll(fragments)
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchTitleFragment()
            else -> SearchSingerFragment()
        }
    }
}
