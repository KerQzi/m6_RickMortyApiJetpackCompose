package kg.geeks.rickmortyapicompose.data.serviceLocator

import android.content.Context
import androidx.room.Room
import kg.geeks.rickmortyapicompose.BuildConfig
import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.db.AppDatabase
import kg.geeks.rickmortyapicompose.data.repository.CharacterRepository
import kg.geeks.rickmortyapicompose.data.repository.EpisodeRepository
import kg.geeks.rickmortyapicompose.data.repository.FavoritesRepository
import kg.geeks.rickmortyapicompose.data.repository.LocationRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule: Module = module {
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { get<Retrofit>().create(ApiService::class.java) }

    single { provideDatabase(androidContext()) }
    single { get<AppDatabase>().favoriteCharactersDao() }

    single { CharacterRepository(get()) }
    single { EpisodeRepository(get()) }
    single { LocationRepository(get()) }
    single { FavoritesRepository(get()) }
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "rickmorty_database")
        .fallbackToDestructiveMigration()
        .build()
}

