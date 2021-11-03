package rocks.ivski.bbc.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.Response
import retrofit2.Retrofit
import rocks.ivski.bbc.data.api.Api
import rocks.ivski.bbc.data.dto.Character
import rocks.ivski.bbc.utils.ApiResult
import java.lang.Exception

class Repository(retrofit: Retrofit) {

    private val api = retrofit.create(Api::class.java)

    suspend fun getCharacters(): ApiResult<List<Character>> {
        return makeApiCall { api.getCharacters() }
    }

    private suspend fun <T> makeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    ApiResult.Success(response.body()!!)
                } else {
                    ApiResult.GenericError(response.code(), response.errorBody().toString())
                }
            } catch (exception: IOException) {
                ApiResult.NetworkError
            } catch (exception: Exception) {
                ApiResult.GenericError(error = exception.localizedMessage)
            }
        }
    }
}