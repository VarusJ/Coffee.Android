package studio.xmatrix.coffee.data.model

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import studio.xmatrix.coffee.data.common.converter.StringsConverter

@Entity
class Content constructor() {
    @Id
    var _id: Long = 0

    lateinit var userName: String
    lateinit var userAvatar: String
    var userGender: Int = User.GenderUnknown

    @Unique
    lateinit var id: String
    lateinit var name: String
    lateinit var detail: String
    lateinit var ownId: String
    var publishDate: Long = 0
    var editDate: Long = 0
    var likeNum: Long = 0
    var commentNum: Long = 0
    var publicContent: Boolean = false
    var nativeContent: Boolean = false
    lateinit var type: String
    lateinit var subType: String
    lateinit var remarks: String
    @Convert(converter = StringsConverter::class, dbType = String::class)
    lateinit var tags: List<String>

    lateinit var images: ToMany<Image>
    lateinit var files: ToMany<File>
    lateinit var movie: ToOne<Movie>
    lateinit var album: ToOne<Album>
    lateinit var app: ToOne<App>

    constructor(
        userName: String,
        userAvatar: String,
        userGender: Int,
        id: String,
        name: String,
        detail: String,
        ownId: String,
        publishDate: Long,
        editDate: Long,
        likeNum: Long,
        commentNum: Long,
        publicContent: Boolean,
        nativeContent: Boolean,
        type: String,
        subType: String,
        remarks: String,
        tags: List<String>
    ): this() {
        this.userName = userName
        this.userAvatar = userAvatar
        this.userGender = userGender
        this.id = id
        this.name = name
        this.detail = detail
        this.ownId = ownId
        this.publishDate = publishDate
        this.editDate = editDate
        this.likeNum = likeNum
        this.commentNum = commentNum
        this.publicContent = publicContent
        this.nativeContent = nativeContent
        this.type = type
        this.subType = subType
        this.remarks = remarks
        this.tags = tags
    }
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
