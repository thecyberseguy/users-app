package thecyberseguy.usersapp.network

sealed class APIResult<T>(
    val data: T? = null,
    val apiCode: String? = null,
    val errorCode: Int? = null
) {
    class Loading<T> : APIResult<T>()
    class Success<T>(data: T?) : APIResult<T>(data)
    class Error<T>(apiCode: String?, errorCode: Int?) : APIResult<T>(null, apiCode, errorCode)
}