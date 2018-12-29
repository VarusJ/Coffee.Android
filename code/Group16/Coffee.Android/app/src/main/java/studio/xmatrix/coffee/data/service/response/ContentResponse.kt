package studio.xmatrix.coffee.data.service.response

import com.google.gson.annotations.SerializedName

data class ContentResponse(
    @SerializedName("State")
    val state: String,
    @SerializedName("Data")
    val data: Content,
    @SerializedName("User")
    val user: UserBaseInfo
) {
    data class Content(
        @SerializedName("ID")
        val id: String,
        @SerializedName("Name")
        val name: String,
        @SerializedName("Detail")
        val detail: String,
        @SerializedName("OwnID")
        val ownId: String,
        @SerializedName("PublishDate")
        val publishDate: Long,
        @SerializedName("EditDate")
        val editDate: Long,
        @SerializedName("LikeNum")
        val likeNum: Long,
        @SerializedName("CommentNum")
        val commentNum: Long,
        @SerializedName("Public")
        val public: Boolean,
        @SerializedName("Native")
        val native: Boolean,
        @SerializedName("Type")
        val type: String,
        @SerializedName("SubType")
        val subType: String,
        @SerializedName("Remarks")
        val remarks: String,
        @SerializedName("Tag")
        val tags: Array<String>,
        @SerializedName("Image")
        val images: Array<Image>,
        @SerializedName("Files")
        val files: Array<File>,
        @SerializedName("Movie")
        val movie: Movie,
        @SerializedName("Album")
        val album: Album,
        @SerializedName("App")
        val app: App
    ) {
        data class Album(
            @SerializedName("Images")
            val images: Image,
            @SerializedName("Title")
            val title: String,
            @SerializedName("Time")
            val time: Long,
            @SerializedName("Location")
            val location: String
        )

        data class App(
            @SerializedName("File")
            val file: File,
            @SerializedName("Web")
            val web: String,
            @SerializedName("URL")
            val url : String,
            @SerializedName("Image")
            val images: Array<Image>,
            val des: String,
            @SerializedName("Version")
            val version: String
        ) {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as App

                if (file != other.file) return false
                if (web != other.web) return false
                if (url != other.url) return false
                if (!images.contentEquals(other.images)) return false
                if (des != other.des) return false
                if (version != other.version) return false

                return true
            }

            override fun hashCode(): Int {
                var result = file.hashCode()
                result = 31 * result + web.hashCode()
                result = 31 * result + url.hashCode()
                result = 31 * result + images.contentHashCode()
                result = 31 * result + des.hashCode()
                result = 31 * result + version.hashCode()
                return result
            }
        }

        data class File(
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

        data class Image(
            @SerializedName("Native")
            val native: Boolean,
            @SerializedName("File")
            val file: File,
            @SerializedName("URL")
            val url: String,
            @SerializedName("Thumb")
            val thumb: String
        )

        data class Movie(
            @SerializedName("File")
            val file: File,
            @SerializedName("URL")
            val url: String,
            @SerializedName("Image")
            val images: Array<Image>,
            @SerializedName("Type")
            val type: String,
            @SerializedName("Detail")
            val detail: String,
            @SerializedName("Watched")
            val watched: Boolean
        ) {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Movie

                if (file != other.file) return false
                if (url != other.url) return false
                if (!images.contentEquals(other.images)) return false
                if (type != other.type) return false
                if (detail != other.detail) return false
                if (watched != other.watched) return false

                return true
            }

            override fun hashCode(): Int {
                var result = file.hashCode()
                result = 31 * result + url.hashCode()
                result = 31 * result + images.contentHashCode()
                result = 31 * result + type.hashCode()
                result = 31 * result + detail.hashCode()
                result = 31 * result + watched.hashCode()
                return result
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Content

            if (id != other.id) return false
            if (name != other.name) return false
            if (detail != other.detail) return false
            if (ownId != other.ownId) return false
            if (publishDate != other.publishDate) return false
            if (editDate != other.editDate) return false
            if (likeNum != other.likeNum) return false
            if (commentNum != other.commentNum) return false
            if (public != other.public) return false
            if (native != other.native) return false
            if (type != other.type) return false
            if (subType != other.subType) return false
            if (remarks != other.remarks) return false
            if (!tags.contentEquals(other.tags)) return false
            if (!images.contentEquals(other.images)) return false
            if (!files.contentEquals(other.files)) return false
            if (movie != other.movie) return false
            if (album != other.album) return false
            if (app != other.app) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + name.hashCode()
            result = 31 * result + detail.hashCode()
            result = 31 * result + ownId.hashCode()
            result = 31 * result + publishDate.hashCode()
            result = 31 * result + editDate.hashCode()
            result = 31 * result + likeNum.hashCode()
            result = 31 * result + commentNum.hashCode()
            result = 31 * result + public.hashCode()
            result = 31 * result + native.hashCode()
            result = 31 * result + type.hashCode()
            result = 31 * result + subType.hashCode()
            result = 31 * result + remarks.hashCode()
            result = 31 * result + tags.contentHashCode()
            result = 31 * result + images.contentHashCode()
            result = 31 * result + files.contentHashCode()
            result = 31 * result + movie.hashCode()
            result = 31 * result + album.hashCode()
            result = 31 * result + app.hashCode()
            return result
        }
    }

    data class UserBaseInfo(
        @SerializedName("Name")
        val name: String,
        @SerializedName("Avatar")
        val avatar: String,
        @SerializedName("Gender")
        val gender: Int
    )
}
