package studio.xmatrix.coffee.data.service.response

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("State")
    val state: String,
    @SerializedName("Data")
    val data: String
) {
    companion object {
        const val StatusSuccess = "success"
        const val StatusBadReq = "bad_req"
        const val StatusNotLogin = "not_login"
        const val StatusNotAllow = "not_allow"
        const val StatusExist = "had_exist"
        const val StatusError = "error"
        const val StatusNotValid = "not_invalid"
    }
}
