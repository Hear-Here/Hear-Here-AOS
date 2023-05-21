package com.hearhere.presentation.post

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlin.collections.ArrayList

class ViewPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    var list: ArrayList<Fragment> = arrayListOf(SearchTitleFragment(), SearchSingerFragment())

    override fun getItemCount(): Int = 2

//    override fun createFragment(position: Int): Fragment {
//        TODO("Not yet implemented")
//    }

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