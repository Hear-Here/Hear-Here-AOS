package com.hearhere.data.data.network

import com.hearhere.data.di.ParserModule
import javax.inject.Inject

class ParsingHelperImpl @Inject constructor(
    @ParserModule.parsing private val parser: MusicParsingService,
) : ParsingHelper {
    override suspend fun getMusicInfo(keyword: String, option: String?, display: Int?) =
        parser.getMusicInfo(keyword, option = option, display = display)
}