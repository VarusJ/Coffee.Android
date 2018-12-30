package studio.xmatrix.coffee.data.model

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.*
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import studio.xmatrix.coffee.data.common.converter.StringsConverter

@Entity
data class Content(
    @Id
    var _id: Long = 0,

    val userName: String,
    val userAvatar: String,
    val userGender: Int,

    @Unique
    val id: String,
    val name: String,
    val detail: String,
    val ownId: String,
    val publishDate: Long,
    val editDate: Long,
    val likeNum: Long,
    val commentNum: Long,
    val publicContent: Boolean,
    val nativeContent: Boolean,
    val type: String,
    val subType: String,
    val remarks: String,
    @Convert(converter = StringsConverter::class, dbType = String::class)
    var tags: List<String>
) {
    lateinit var images: ToMany<Image>
    lateinit var files: ToMany<File>
    lateinit var movie: ToOne<Movie>
    lateinit var album: ToOne<Album>
    lateinit var app: ToOne<App>
}

@Entity
data class Album(
    @Id
    var _id: Long = 0,

    val title: String,
    val time: Long,
    val location: String
) {
    lateinit var images: ToMany<Image>
}

@Entity
data class App(
    @Id
    var _id: Long = 0,

    val web: String,
    val url: String,
    val des: String,
    val version: String
) {
    lateinit var file: ToOne<File>
    lateinit var images: ToMany<Image>
}

@Entity
data class File(
    @Id
    var _id: Long = 0,

    @SerializedName("File")
    val file: String,
    @SerializedName("Size")
    val size: Long,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Time")
    val time: Long,
    @SerializedName("Count")
    val count: Long,
    @SerializedName("Type")
    val type: String
)

@Entity
data class Image(
    @Id
    var _id: Long = 0,

    val native: Boolean,
    val url: String,
    val thumb: String
) {
    lateinit var file: ToOne<File>
}

@Entity
data class Movie(
    @Id
    var _id: Long = 0,

    val url: String,
    val type: String,
    val detail: String,
    val watched: Boolean
) {
    lateinit var file: ToOne<File>
    lateinit var images: ToMany<Image>
}
