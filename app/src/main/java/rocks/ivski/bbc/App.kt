package rocks.ivski.bbc

import android.app.Application
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import rocks.ivski.bbc.di.appModule
import rocks.ivski.bbc.di.networkModule
import rocks.ivski.bbc.di.vmModule

class App: Application() {

    @ExperimentalSerializationApi
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(networkModule(BuildConfig.BASE_URL), appModule, vmModule)
        }
    }
}