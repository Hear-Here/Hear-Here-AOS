package com.hearhere.data.data.network

import android.util.Log
import com.hearhere.data.data.dto.response.ArtistResult
import com.hearhere.data.data.dto.response.SongResult
import okhttp3.ResponseBody
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

object SearchArtistParser  {
    fun parse(responseBody: ResponseBody): List<ArtistResult> {
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(responseBody.byteStream(), "UTF-8")
        var eventType = parser.eventType
        var artistList = mutableListOf<ArtistResult>()
        var artist: ArtistResult? = null

        var songId = ""
        var songName = ""

        var nowTag : String? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "item" -> artist = ArtistResult()
                        "title" -> {
                           nowTag ="title"
                        }
                        "release"->{
                            nowTag="release"
                        }
                        "link" -> {
                            val link = parser.text
                            if (artist != null) {
                                artist.link = link
                            }
                        }
                        "image" -> {
                            nowTag = "image"
                        }
                        "id"->{
                            nowTag = "id"
                        }
                        "name"->{
                            if(nowTag==null || nowTag!=="artist") nowTag ="name"
                            if(nowTag == "artist") nowTag="artist_name"
                        }
                        "maniadb:artist"->{
                            nowTag ="artist"
                        }

                    }
                }
                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        "item" ->   artistList.add(artist!!)
                        "song"->{
                            if (artist != null) {
                                artist.songList.add(SongResult(songId,songName,artist.release))
                            }
                        }
                    }
                }
                XmlPullParser.TEXT ->{
                    when(nowTag){
                        "id"->{
                            songId = parser.text.trim()
                            nowTag = null
                        }
                        "name"->{
                            val filter = parser.text.toString().indexOf("&nbsp")
                            songName = if(filter != -1) parser.text.trim().substring(0,filter)
                            else parser.text.trim()
                            nowTag = null
                        }
                        "image"->{
                            if (artist != null) {
                                artist.image = parser.text.trim()
                            }
                            nowTag = null
                        }
                        "release"->{
                            if (artist != null) {
                                artist.release = parser.text.trim()
                            }
                            nowTag = null
                        }
                        "artist_name"->{
                            if (artist != null) {
                                artist.title= parser.text.trim()
                            }
                            nowTag = null
                        }
                    }
                }

            }
            eventType = parser.next()
        }
        return artistList
    }
}