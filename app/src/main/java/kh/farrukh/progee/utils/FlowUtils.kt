package kh.farrukh.progee.utils

import kh.farrukh.progee.utils.error_handle.HandledError
import kh.farrukh.progee.utils.error_handle.NoLocalCacheFoundException
import kh.farrukh.progee.utils.error_handle.toHandledError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

/**
 *Created by farrukh_kh on 3/24/22 6:08 PM
 *kh.farrukh.movix.utils
 **/
//TODO flow from db sometimes working sometimes not (is seen when local data changed via DatabaseInspector)

inline fun <T, R> requestWithLocalCache(
    crossinline remoteFetch: suspend () -> R,
    crossinline localQuery: () -> Flow<T?>,
    crossinline saveFetchResult: suspend (T) -> Unit,
    crossinline remoteResponseMapper: (R) -> T,
    crossinline shouldFetch: () -> Boolean = { true },
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<Result<T>> = flow {

    val cachedData = localQuery().first()

    val flow = if (shouldFetch()) {
        emit(Result.Loading(data = cachedData))

        try {
            saveFetchResult(remoteResponseMapper(remoteFetch()))
            localQuery().toResultFlow(cachedData)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            localQuery().map { lastCachedData ->
                Result.Error(error = throwable.toHandledError(), data = lastCachedData)
            }
        }
    } else {
        localQuery().toResultFlow(cachedData)
    }

    emitAll(flow)
}.flowOn(coroutineContext)

inline fun <T, R> requestRemoteOnly(
    crossinline remoteFetch: suspend () -> R,
    crossinline saveFetchResult: suspend ((T) -> Unit),
    crossinline remoteResponseMapper: (R) -> T,
    crossinline shouldFetch: () -> Boolean = { true },
    coroutineContext: CoroutineContext = Dispatchers.IO
): Flow<Result<T>> = flow {

    if (shouldFetch()) {
        emit(Result.Loading())

        try {
            val response = remoteResponseMapper(remoteFetch())
            saveFetchResult(response)
            emit(Result.Success(response))
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            emit(Result.Error(error = throwable.toHandledError()))
        }
    } else {
        emit(Result.Error(HandledError.ConnectionError()))
    }
}.flowOn(coroutineContext)

fun <T> Flow<T?>.toResultFlow(
    cachedData: T? = null,
    errorMessage: String = "Data not found on local cache"
): Flow<Result<T>> = transform { value ->
    return@transform if (value == null) {
        emit(
            Result.Error(
                error = NoLocalCacheFoundException(errorMessage).toHandledError(),
                data = cachedData
            )
        )
    } else {
        emit(Result.Success(value))
    }
}