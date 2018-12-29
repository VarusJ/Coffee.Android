package studio.xmatrix.coffee.data.service.response

import com.google.gson.annotations.SerializedName

data class PublishContentResponse(
    @SerializedName("State")
    val state: String,
    @SerializedName("Data")
    val publishData: Array<PublishData>
) {
    data class PublishData(
        @SerializedName("Data")
        val data: ContentResponse.Content,
        @SerializedName("User")
        val user: ContentResponse.UserBaseInfo
    )
}
