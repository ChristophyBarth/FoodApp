package capps.foodapp.data.models

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Failure(val exception: Throwable) : ResponseResult<Nothing>()

    fun isSuccess(): Boolean = this is Success
    fun isFailure(): Boolean = this is Failure

    fun getOrNull(): T? = (this as? Success)?.data
    fun exceptionOrNull(): Throwable? = (this as? Failure)?.exception

    inline fun fold(onSuccess: (T) -> Unit, onFailure: (Throwable) -> Unit) {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(exception)
        }
    }

    companion object {
        fun <T> success(data: T): ResponseResult<T> = Success(data)
        fun failure(exception: Throwable): ResponseResult<Nothing> = Failure(exception)
    }
}

