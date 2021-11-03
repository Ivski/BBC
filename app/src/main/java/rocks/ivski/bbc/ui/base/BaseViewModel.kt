package rocks.ivski.bbc.ui.base

import androidx.lifecycle.ViewModel
import rocks.ivski.bbc.utils.ApiResult

abstract class BaseViewModel : ViewModel() {

    fun <T> handleApiResponse(response: ApiResult<T>, onSuccess: ((result: T) -> Unit)? = null) {
        when (response) {
            is ApiResult.GenericError -> TODO()
            is ApiResult.NetworkError -> TODO()
            is ApiResult.Success -> onSuccess?.invoke(response.value)
        }
    }
}