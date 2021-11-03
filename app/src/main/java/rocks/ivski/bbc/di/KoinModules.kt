package rocks.ivski.bbc.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import rocks.ivski.bbc.BuildConfig
import rocks.ivski.bbc.data.repo.Repository
import rocks.ivski.bbc.navigation.Navigator
import rocks.ivski.bbc.ui.list.ListViewModel

@ExperimentalSerializationApi
fun networkModule(baseUrl: String) = module {
    single { getOkHttpClient() }
    single { getRetrofit(baseUrl, get()) }
}

val appModule = module {
    single { Repository(get()) }
    single { Navigator() }
}
val vmModule = module {
    single { ListViewModel(get(), get()) }
}

@ExperimentalSerializationApi
private fun getRetrofit(url: String, client: OkHttpClient): Retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .client(client)
    .baseUrl(url)
    .build()

private fun getOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    return OkHttpClient.Builder().addInterceptor(interceptor).build()
}