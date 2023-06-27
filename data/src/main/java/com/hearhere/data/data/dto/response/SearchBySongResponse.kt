package com.hearhere.data.data.dto.response

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class SearchBySongResponse(
    @field:Element(name = "channel")
    var channel: SongChannel? = null
)

@Root(name = "channel", strict = false)
data class SongChannel @JvmOverloads constructor(
    @field:ElementList(inline = true, required = false)
    var itemList: List<SongItem>? = null
)

@Root(name = "item", strict = false)
data class SongItem @JvmOverloads constructor(
    @field:Attribute(required = false)
    var id: String = "",

    @field:Element(name = "title", required = false)
    var title: String = "",

    @field:Element(name = "link", required = false)
    var link: String = "",

    @field:Element(name = "pubDate", required = false)
    var pubDate: String = "",

    @field:Element(name = "album", required = false)
    var album: Album? = null,

    @field:Element(name = "artist", required = false)
    var artist: Artist? = null
)

@Root(name = "album", strict = false)
data class Album @JvmOverloads constructor(
    @field:Attribute(required = false)
    var id: String = "",

    @field:Element(name = "title")
    var title: String = "",

    @field:Element(name = "image")
    var image: String = ""
)

@Root(name = "artist", strict = false)
data class Artist @JvmOverloads constructor(
    @field:Element(name = "name")
    var name: String = ""
)
