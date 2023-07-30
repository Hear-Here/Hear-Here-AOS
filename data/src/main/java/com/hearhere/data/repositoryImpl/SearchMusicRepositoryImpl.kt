package com.hearhere.data.repositoryImpl

import android.content.Context
import com.hearhere.data.data.dto.response.ArtistResult
import com.hearhere.data.data.dto.response.SearchByArtistResponse
import com.hearhere.data.data.dto.response.SearchBySongResponse
import com.hearhere.data.data.network.ParsingHelperImpl
import com.hearhere.data.data.network.SearchArtistParser
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.SearchedMusic
import com.hearhere.domain.repository.SearchMusicRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import okhttp3.ResponseBody
import javax.inject.Inject

class SearchMusicRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val parsingHelperImpl: ParsingHelperImpl
) : BaseRepository(), SearchMusicRepository {

    private val parser: SearchArtistParser = SearchArtistParser
    override suspend fun searchMusicBySong(
        keyword: String,
        display: Int?
    ): Result<List<SearchedMusic>> {
        safeApiCall<SearchBySongResponse> {
            parsingHelperImpl.searchMusicBySong(
                keyword = keyword,
                display = display
            )
        }.first().let { it ->
            when (it) {
                is ApiResponse.Success -> {
                    return(Result.success(it.data?.mapToDomain() ?: emptyList()))
                }

                else -> {
                    it.throwable?.let { return Result.failure<List<SearchedMusic>>(it) }
                }
            }
        }

        return Result.failure<List<SearchedMusic>>(error("parsing fail"))
    }

    override suspend fun searchMusicByArtist(keyword: String, display: Int?): Result<List<SearchedMusic>> {
        safeApiCall<ResponseBody> {
            parsingHelperImpl.searchMusicByArtist(
                keyword = keyword,
                display = display
            )
        }.first().let { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let {
                        val artist = parser.parse(it)
                        return Result.success(artist.matoToDomain(keyword))
                    }
                }
                else -> {
                    response.throwable?.let { return Result.failure<List<SearchedMusic>>(it) }
                }
            }
        }
        return Result.failure<List<SearchedMusic>>(error("parsing fail"))
    }

    fun SearchBySongResponse.mapToDomain(): List<SearchedMusic> {
        val list = ArrayList<SearchedMusic>()
        this.channel?.let { channel ->
            channel.itemList?.forEach {
                if (it.id !== null && !it.title.isNullOrBlank() && it.artist !== null && it.album != null) {
                    val searchedMusic = SearchedMusic(
                        songId = it.id.toLong(),
                        title = it.title,
                        artist = it.artist!!.name,
                        cover = it.album?.image ?: null,
                        pubYear = it.album?.title?.getPubYear()
                    )
                    list.add(searchedMusic)
                }
            }
        }
        list.sortByDescending { it.pubYear }
        return list
    }

    fun SearchByArtistResponse.mapToDomain(keyword: String): List<SearchedMusic> {
        val list = ArrayList<SearchedMusic>()
        this.channel?.let { channel ->
            channel.itemList?.forEach {
                it.majorsongs?.songs?.forEach { song ->
                    if (song.id !== null && !song.name.isNullOrBlank()) {
                        val searchedMusic = SearchedMusic(
                            songId = song.id.toLong(),
                            title = song.name,
                            artist = keyword,
                            cover = it.image,
                            pubYear = ""
                        )
                        list.add(searchedMusic)
                    }
                }
            }
        }
        return list
    }

    fun List<ArtistResult>.matoToDomain(keyword: String): List<SearchedMusic> {
        val list = ArrayList<SearchedMusic>()

        this.forEach { res ->
            val cover = res.image
            val title = res.title
            if (!title.isNullOrEmpty()) {
                res.songList.forEach { song ->
                    if (!song.id.isNullOrBlank() && !song.name.isNullOrBlank()) {
                        val searchedMusic = SearchedMusic(
                            songId = song.id!!.toLong(),
                            title = song.name!!,
                            artist = title,
                            cover = song.cover,
                            pubYear = song.release ?: ""
                        )
                        list.add(searchedMusic)
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
