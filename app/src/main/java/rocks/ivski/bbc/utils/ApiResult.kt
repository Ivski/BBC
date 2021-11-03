package rocks.ivski.bbc.utils

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T): ApiResult<T>()
    data class GenericError(val code: Int? = null, val error: String? = null): ApiResult<Nothing>()
    object NetworkError: ApiResult<Nothing>()
}