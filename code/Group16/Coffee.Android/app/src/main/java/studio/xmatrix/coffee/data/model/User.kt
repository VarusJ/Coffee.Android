package studio.xmatrix.coffee.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class User(
    @Id
    var _id: Long = 0,
    val name: String
)
