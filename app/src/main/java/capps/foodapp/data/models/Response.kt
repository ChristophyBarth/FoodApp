package capps.foodapp.data.models

data class Response<T>(
    val data: T,
    val message: String,
    val status: String,
)
