package thecyberseguy.usersapp.network

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import thecyberseguy.usersapp.constants.ErrorCode
import thecyberseguy.usersapp.extensions.getJsonResponse
import thecyberseguy.usersapp.extensions.isHasHtmlTags
import thecyberseguy.usersapp.extensions.toUrlWithQuery
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

@ActivityRetainedScoped
class APIRepository @Inject constructor(private val api: APIInterface, private val network: NetworkChecker) {

    fun get(
        url: String,
        query: List<Pair<String, Any?>>? = null,
        apiCode: String? = null
    ): Flow<APIResult<String>> {
        return safeApiCall({ api.get(url.toUrlWithQuery(query)) }, apiCode)
    }

    private fun safeApiCall(
        apiCall: suspend () -> Response<String>,
        apiCode: String?
    ): Flow<APIResult<String>> {
        return flow {
            if (!network.isConnected) {
                emit(APIResult.Error(apiCode, ErrorCode.NO_INTERNET_CONNECTION))
                return@flow
            }

            try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    emit(APIResult.Success(response.body() ?: ""))
                } else {
                    emit(APIResult.Error(apiCode, getErrorCode(response)))
                }
            } catch (e: IOException) {
                emit(APIResult.Error(apiCode, getErrorCodeByException(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun getErrorCode(response: Response<String>): Int {
        val errorBody = response.errorBody()?.charStream()?.readText() ?: return response.code()

        if (errorBody.isHasHtmlTags()) return response.code()
        return errorBody.getJsonResponse("errorCode")?.toInt() ?: 0
    }

    private fun getErrorCodeByException(e: Exception): Int {
        e.printStackTrace()

        return when (e) {
            is SocketTimeoutException -> ErrorCode.NO_INTERNET_CONNECTION
            else -> ErrorCode.INTERNAL_SERVER_ERROR
        }
    }

}