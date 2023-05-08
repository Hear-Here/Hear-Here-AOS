package com.hearhere.data.repositoryImpl

import android.content.Context
import com.hearhere.data.data.dto.response.ApiResponse
import com.hearhere.data.data.dto.response.MusicResponse
import com.hearhere.data.data.network.ParsingHelperImpl
import com.hearhere.domain.model.Music
import com.hearhere.domain.repository.SearchMusicRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchMusicRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val parsingHelperImpl: ParsingHelperImpl
) : BaseRepository(), SearchMusicRepository {

    override suspend fun searchMusicBySong(
        keyword: String,
        option: String?,
        display: Int?
    ): Result<List<Music>> {
        val list = ArrayList<Music>()
        try {
            val response = safeApiCall {
                parsingHelperImpl.getMusicInfo(
                    keyword = keyword,
                    option = option,
                    display = display
                )
            }
            when (response) {
                is ApiResponse.Success -> {
                    return Result.success(response.data?.mapToDomain() ?: emptyList())
                }
                else -> {
                    response.throwable?.let { Result.failure<List<Music>>(it) }
                }
            }
        } catch (e: Throwable) {
            return Result.failure<List<Music>>(e)
        }catch (e : IllegalStateException){
            return Result.failure<List<Music>>(e)
        }

        return Result.failure<List<Music>>(error("parsing fail"))
    }

    fun MusicResponse.mapToDomain(): List<Music> {
        val list = ArrayList<Music>()
        this.channel?.let { channel ->
            channel.itemList?.forEach {
                if (it.id !== null && !it.title.isNullOrBlank() && it.artist !== null && it.album != null) {
                    val music = Music(
                        songId = it.id.toLong(),
                        title = it.title,
                        artist = it.artist!!.name,
                        cover = it.album?.image ?: null,
                        pubYear = it.album?.title?.getPubYear()
                    )
                    list.add(music)
                }
            }
        }
        list.sortByDescending { it.pubYear }
        return list
    }

    fun String.getPubYear(): String {
        val end = this.lastIndexOf(')', this.length - 1)
        if (end == -1) return ""

        val start = this.lastIndexOf('(', end)
        if (start == -1) return ""

        val year = this.subSequence(start + 1, end)
        return year.toString()
    }
}

