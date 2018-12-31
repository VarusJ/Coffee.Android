package studio.xmatrix.coffee.data.service.response

import com.google.gson.annotations.SerializedName
import studio.xmatrix.coffee.data.model.Content

data class PublishContentResponse(
    @SerializedName("State")
    val state: String,
    @SerializedName("Data")
    val publishData: Array<PublishData>
) {
    data class PublishData(
        @SerializedName("Data")
        val data: ContentResponse.ContentInfo,
        @SerializedName("User")
        val user: ContentResponse.UserBaseInfo
    )

    fun toContents(): List<Content> {
        val arr = ArrayList<Content>()
        publishData.forEach {
            arr.add(
                Content(
                    userName = it.user.name,
                    userAvatar = it.user.avatar,
                    userGender = it.user.gender,
                    id = it.data.id,
                    name = it.data.name,
                    detail = it.data.detail,
                    ownId = it.data.ownId,
                    publishDate = it.data.publishDate,
                    editDate = it.data.editDate,
                    likeNum = it.data.likeNum,
                    commentNum = it.data.commentNum,
                    publicContent = it.data.public,
                    nativeContent = it.data.native,
                    type = it.data.type,
                    subType = it.data.subType,
                    remarks = it.data.remarks,
                    tags = it.data.tags
                ).apply {
                    it.data.images.forEach { images.add(it.toImage()) }
                    it.data.files.forEach { files.add(it) }
                    movie.target = it.data.movie.toMovie()
                    album.target = it.data.album.toAlbum()
                    app.target = it.data.app.toApp()
                }
            )
        }
        return arr
    }
}
