package kg.geeks.rickmortyapicompose.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: Int? = null,
    val name: String? = null,
    val image: String? = null,
    val status: Status,
    val species: String? = null,
    val gender: String? = null,
    val location: String? = null,
)

enum class Status {
    Alive, Dead, unknown
}


