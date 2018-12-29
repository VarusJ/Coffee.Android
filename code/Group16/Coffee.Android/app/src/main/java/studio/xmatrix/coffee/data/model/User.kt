package studio.xmatrix.coffee.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.annotation.Unique

@Entity
data class User(
    @Id
    var _id: Long = 0,

    @Transient
    val state: String,

    @Unique
    val id: String?,
    val email: String?,
    val userClass: Int?,

    // UserInfo
    var name: String?,
    val avatar: String?,
    val bio: String?,
    val gender: Int?,

    val maxSize: Long?,
    val usedSize: Long?,
    val singleSize: Long?
) {
    companion object {
        // User Class
        const val ClassBlackUser = 0
        const val ClassLimitUser = 1
        const val ClassNormalUser = 2
        const val ClassVerifyUser = 3
        const val ClassVIPUser = 4
        const val ClassSVIPUser = 5
        const val ClassAdmin = 6
        const val ClassSAdmin = 7

        // Gender
        const val GenderMan = 0
        const val GenderWoman = 1
        const val GenderUnknown = 2
    }
}
