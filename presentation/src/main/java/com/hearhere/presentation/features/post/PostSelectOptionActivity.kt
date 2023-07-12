package com.hearhere.presentation.features.post

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.hearhere.domain.model.Posting
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
        val uri = Uri.parse(intent.getStringExtra("music_cover") ?: "")
        binding.postMusicCoverIv.setImageCover(uri)

        setViewPager()
    }

    override fun registerViewModels(): List<BaseViewModel> = listOf(viewModel)

    override fun observeViewModel() {
        viewModel.navigationSlide.observe {
            slidePage()
        }

        viewModel.postingState.observe {
            Log.d("state", it.posting.toString())
        }
        viewModel.navigationFinish.observe { if(it!=null) startPostFinish(it) }
    }

    private fun setViewPager() {
        pagerAdapter = PostMusicViewPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isNestedScrollingEnabled = false
    }

    fun startPostFinish(posting: Posting) {
        Intent(this@PostSelectOptionActivity, PostFinishActivity::class.java).also {
            it.putExtra("music", posting)
            startActivity(it)
        }
    }

    fun slidePage() {
        val current = binding.viewPager.currentItem
        binding.viewPager.setCurrentItem(current + 1, false)
    }
}
