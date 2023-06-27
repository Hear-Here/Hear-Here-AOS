package com.hearhere.presentation.post

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : BaseActivity<ActivityPostBinding>(R.layout.activity_post) {

    private val viewModel: PostViewModel by viewModels()
    private lateinit var adapter: BaseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        setViewPager()

        adapter = BaseAdapter.build()

        binding.searchBtn.setOnClickListener {
            viewModel.searchMusic(binding.searchEt.text.toString())
        }
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.selectedMusic.observe {
            if (it != null) {
                val music = it
                // 이 값을 받아서 start activity
                val intent = Intent(this@PostActivity, PostSelectOptionActivity::class.java)
                intent.putExtra("music_cover", music.cover)
                intent.putExtra("music_artist", music.artist)
                intent.putExtra("music_title", music.title)
                intent.putExtra("music_songId", music.songId)
                startActivity(intent)
            }
        }
    }

    private fun setViewPager() {
        val pagerAdapter = SearchMusicViewPagerAdapter(this)
        val tabName = listOf("Title", "Artist")
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = (tabName[position])
        }.attach()
    }
}
