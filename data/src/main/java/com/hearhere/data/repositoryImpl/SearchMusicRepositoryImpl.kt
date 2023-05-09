package com.hearhere.data.repositoryImpl

import android.content.Context
import android.util.Log
import com.hearhere.data.data.dto.response.ApiResponse
import com.hearhere.data.data.dto.response.ArtistResult
import com.hearhere.data.data.dto.response.SearchByArtistResponse
import com.hearhere.data.data.dto.response.SearchBySongResponse
import com.hearhere.data.data.network.ParsingHelperImpl
import com.hearhere.data.data.network.SearchArtistParser
import com.hearhere.domain.model.Music
import com.hearhere.domain.repository.SearchMusicRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import javax.inject.Inject

class SearchMusicRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val parsingHelperImpl: ParsingHelperImpl,
) : BaseRepository(), SearchMusicRepository {

    private val parser: SearchArtistParser = SearchArtistParser
    override suspend fun searchMusicBySong(
        keyword: String, option: String?, display: Int?
    ): Result<List<Music>> {
        try {
            val response = safeApiCall<SearchBySongResponse> {
                parsingHelperImpl.searchMusicBySong(
                    keyword = keyword, option = option, display = display
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
        } catch (e: IllegalStateException) {
            return Result.failure<List<Music>>(e)
        }

        return Result.failure<List<Music>>(error("parsing fail"))
    }


    override suspend fun searchMusicByArtist(keyword: String, display: Int?): Result<List<Music>> {
        try {
            val response = safeApiCall<ResponseBody> {
                parsingHelperImpl.searchMusicByArtist(
                    keyword = keyword, display = display
                )
            }
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let {
                        val artist = parser.parse(it)
                        return Result.success(artist.matoToDomain(keyword))
                    }
                }
                else -> {
                    response.throwable?.let { Result.failure<List<Music>>(it) }
                }
            }
        } catch (e: Throwable) {
            return Result.failure<List<Music>>(e)
        } catch (e: IllegalStateException) {
            return Result.failure<List<Music>>(e)
        }

        return Result.failure<List<Music>>(error("parsing fail"))
    }


    fun SearchBySongResponse.mapToDomain(): List<Music> {
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


    fun SearchByArtistResponse.mapToDomain(keyword: String): List<Music> {
        val list = ArrayList<Music>()
        this.channel?.let { channel ->
            channel.itemList?.forEach {
                it.majorsongs?.songs?.forEach { song ->
                    if (song.id !== null && !song.name.isNullOrBlank()) {
                        val music = Music(
                            songId = song.id.toLong(),
                            title = song.name,
                            artist = keyword,
                            cover = it.image,
                            pubYear = ""
                        )
                        list.add(music)
                    }
                }
            }
        }
        return list
    }

    fun List<ArtistResult>.matoToDomain(keyword: String): List<Music> {
        val list = ArrayList<Music>()

        this.forEach { res ->
            val cover = res.image
            val title = res.title
            if (!title.isNullOrEmpty()) {
                res.songList.forEach { song ->
                    if (!song.id.isNullOrBlank() && !song.name.isNullOrBlank()) {
                        val music = Music(
                            songId = song.id!!.toLong(),
                            title = song.name!!,
                            artist = title,
                            cover = cover,
                            pubYear = song.release ?: ""
                        )
                        list.add(music)
                    }
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

