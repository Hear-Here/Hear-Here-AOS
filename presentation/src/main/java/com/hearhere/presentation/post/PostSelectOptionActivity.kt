package com.hearhere.presentation.post

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.hearhere.presentation.R
import com.hearhere.presentation.base.BaseActivity
import com.hearhere.presentation.base.BaseAdapter
import com.hearhere.presentation.base.BaseViewModel
import com.hearhere.presentation.databinding.ActivityPostSelectOptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostSelectOptionActivity : BaseActivity<ActivityPostSelectOptionBinding>(R.layout.activity_post_select_option) {

    private val viewModel: PostSelectOptionViewModel by viewModels()
    private lateinit var adapter: BaseAdapter
    private lateinit var pagerAdapter: PostMusicViewPagerAdapter

    override fun onCreateView(savedInstanceState: Bundle?) {

        val coverUrl = intent.getStringExtra("music_cover")
        val artist = intent.getStringExtra("music_artist")
        val title = intent.getStringExtra("music_title")
        val songId = intent.getLongExtra("music_songId", 0)

        viewModel.cover = coverUrl
        viewModel.artist = artist
        viewModel.title = title
        viewModel.songId = songId
        // intent.getStringExtra("전달했던 데이터의 이름표")

//        binding.textViewTv.text = message
//        binding.postMusicCoverIv.setImageURI(cover_url?.toUri())
        val uri = Uri.parse(coverUrl)
        binding.postMusicCoverIv.setImageCover(uri)
        binding.postTitleTv.text = title
        binding.postArtistTv.text = artist

        setViewPager()
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
    }

    private fun setViewPager() {
        pagerAdapter = PostMusicViewPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
    }

    fun startPostFinish() {
        Intent(this@PostSelectOptionActivity, PostFinishActivity::class.java).also {

            Log.d("옥채연/startPostFinish/message", viewModel.posting!!.content.toString())
            Log.d("옥채연/startPostFinish", viewModel.posting.toString())
            it.putExtra("music", viewModel.posting)
            startActivity(it)
        }
    }

    fun slidePage() {

        val current = binding.viewPager.currentItem

        binding.viewPager.setCurrentItem(current + 1, false)

        Log.d("옥채연", viewModel.genre.toString())
        Log.d("옥채연", viewModel.with.toString())
        Log.d("옥채연", viewModel.weather.toString())
        Log.d("옥채연", viewModel.emotion.toString())
    }
}
