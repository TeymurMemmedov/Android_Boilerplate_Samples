package com.example.mygenerics

import android.database.sqlite.SQLiteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val message: String?, val exception: Throwable? = null) : ResultWrapper<Nothing>()
}

data class UIState<T> (
    val isLoading:Boolean = false,
    val data:T? = null,
    val error:String? = null

)

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            ResultWrapper.Success(response)
        } catch (e: HttpException) {
            // Retrofit HTTP errors (4xx, 5xx)
            ResultWrapper.Error("HTTP error ${e.code()}: ${e.message()}", e)
        }  catch (e: IOException) {
            // Network issues (no internet, DNS failure, etc.)
            ResultWrapper.Error("Network error. Check your connection.", e)
        } catch (e: JSONException) {
            // Parsing issues
            ResultWrapper.Error("Response parsing error.", e)
        } catch (e: Exception) {
            // Fallback for unexpected errors
            ResultWrapper.Error("Unexpected error occurred: ${e.localizedMessage}", e)
        }
    }
}

suspend fun <T> safeDbRequest(request: suspend () -> T): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            val result = request()
            ResultWrapper.Success(result)
        } catch (e: SQLiteException) {
            ResultWrapper.Error("Database error: ${e.message}", e)
        } catch (e: IllegalStateException) {
            ResultWrapper.Error("Invalid database state: ${e.message}", e)
        } catch (e: Exception) {
            ResultWrapper.Error("Unexpected database error: ${e.localizedMessage}", e)
        }
    }
}