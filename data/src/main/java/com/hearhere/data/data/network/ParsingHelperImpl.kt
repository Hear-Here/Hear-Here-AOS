package com.hearhere.data.data.network

import android.content.Context
import com.hearhere.data.data.dto.response.MusicResponse
import com.hearhere.data.di.ParserModule
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class ParsingHelperImpl @Inject constructor(
    @ParserModule.parsing private val parser: MusicParsingService,
) : ParsingHelper {
    override suspend fun getMusicInfo(keyword: String, option: String?, display: Int?) =
        parser.getMusicInfo(keyword, option = option, display = display)
}