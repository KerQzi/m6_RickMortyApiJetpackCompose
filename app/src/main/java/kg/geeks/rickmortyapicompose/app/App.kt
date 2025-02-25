package kg.geeks.rickmortyapicompose.app

import android.app.Application
import kg.geeks.rickmortyapicompose.data.serviceLocator.dataModule
import kg.geeks.rickmortyapicompose.ui.serviceLocator.uiModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(dataModule, uiModule))

        }
    }
}