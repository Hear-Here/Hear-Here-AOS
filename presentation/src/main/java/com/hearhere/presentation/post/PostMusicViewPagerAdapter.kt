package com.hearhere.presentation.post

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlin.collections.ArrayList

class PostMusicViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    var list: ArrayList<Fragment> = arrayListOf(PostSelectGenreFragment(), PostSelectWithFragment(),
        PostSelectWeatherFragment(), PostSelectEmotionFragment(), PostSelectMessageFragment())


    override fun getItemCount(): Int = 5

    fun setList(fragments: Array<Fragment>) {
        list.clear()
        list.addAll(fragments)
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PostSelectGenreFragment()
            1 -> PostSelectWithFragment()
            2 -> PostSelectWeatherFragment()
            3 -> PostSelectEmotionFragment()
            else -> PostSelectMessageFragment()
        }
    }



}