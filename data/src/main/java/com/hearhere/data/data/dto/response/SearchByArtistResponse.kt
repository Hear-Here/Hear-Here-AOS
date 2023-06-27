package com.hearhere.data.data.dto.response

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class SearchByArtistResponse(
    @field:Element(name = "channel")
    var channel: ArtistChannel? = null
)

@Root(name = "channel", strict = false)
data class ArtistChannel @JvmOverloads constructor(
    @field:ElementList(inline = true, required = false)
    var itemList: List<ArtistItem>? = null
)

@Root(name = "item", strict = false)
data class ArtistItem @JvmOverloads constructor(
    @field:Attribute(required = false)
    var id: String = "",

    @field:Element(name = "title", required = false)
    var title: String = "",

    @field:Element(name = "link", required = false)
    var link: String = "",

    @field:Element(name = "image", required = false)
    var image: String = "",

    @field:Element(name = "maniadb:majorsongs", required = false)
    var majorsongs: MajorSongs? = null,

    @field:Element(name = "maniadb:majormembers ", required = false)
    var majors: String? = null,
)
@Root(name = "maniadb:majorsongs", strict = false)
data class MajorSongs @JvmOverloads constructor(
    @field:ElementList(name = "song", inline = true)
    var songs: List<Song> = listOf()
)

@Root(name = "song", strict = false)
data class Song@JvmOverloads constructor(
    @field:Element(name = "id", required = false)
    var id: String = "",

    @field:Element(name = "name", required = false)
    var name: String = "",
)

data class ArtistResult(
    var title: String? = null,
    var release: String? = null,
    var link: String? = null,
    var image: String? = null,
    var songList: MutableList<SongResult> = mutableListOf()
)

data class SongResult(
    var id: String? = null,
    var name: String? = null,
    var release: String? = null,
    var cover: String? = null
)
