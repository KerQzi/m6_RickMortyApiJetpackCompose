package kg.geeks.rickmortyapicompose.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharactersDao {

    @Query("SELECT * FROM favorite_characters")
    fun fetchAllFavoriteCharacters(): Flow<List<FavoriteCharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCharacter(character: FavoriteCharacterEntity)

    @Delete
    suspend fun deleteFavoriteCharacter(character: FavoriteCharacterEntity)
}