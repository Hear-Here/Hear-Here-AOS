package com.hearhere.data.data.network

import com.hearhere.data.di.ParserModule
import javax.inject.Inject

class ParsingHelperImpl @Inject constructor(
    @ParserModule.parsing val parser: MusicParsingService,
) : ParsingHelper {
    override suspend fun searchMusicBySong(keyword: String, display: Int?) =
        parser.searchBySong(keyword, display = display)

    override suspend fun searchMusicByArtist(keyword: String, display: Int?) =
        parser.searchByArtistWithXml(keyword,display = display)
}