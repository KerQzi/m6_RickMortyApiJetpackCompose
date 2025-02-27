package kg.geeks.rickmortyapicompose.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_characters")
data class FavoriteCharacterEntity(
    @PrimaryKey
    val id: Int? = null,
    val name: String? = null ,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val image: String? = null
)
