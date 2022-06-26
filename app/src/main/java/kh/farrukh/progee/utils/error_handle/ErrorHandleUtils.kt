package kh.farrukh.progee.utils.error_handle

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.google.gson.JsonSyntaxException
import kh.farrukh.progee.utils.SingleBlock
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

/**
 *Created by farrukh_kh on 3/25/22 11:12 PM
 *kh.farrukh.movix.utils.error_handle
 **/
fun Throwable.toHandledError(): HandledError {
    printStackTrace()
    return when (this) {
        is IOException -> HandledError.ConnectionError(throwable = this)
        is JsonSyntaxException -> HandledError.JsonParseError(throwable = this)
        is NoLocalCacheFoundException -> HandledError.NoLocalCacheError(message, this)
        is HttpException -> getHttpError()
        else -> HandledError.UnknownError(throwable = this)
    }
}

private fun HttpException.getHttpError() = when {
    code() == HttpURLConnection.HTTP_BAD_REQUEST -> HandledError.BadRequestError(throwable = this)
    code() == HttpURLConnection.HTTP_UNAUTHORIZED -> HandledError.AuthError(throwable = this)
    code() == HttpURLConnection.HTTP_PAYMENT_REQUIRED -> HandledError.PaymentError(throwable = this)
    code() == HttpURLConnection.HTTP_FORBIDDEN -> HandledError.PermissionError(throwable = this)
    code() == HttpURLConnection.HTTP_NOT_FOUND -> HandledError.NotFoundError(throwable = this)
    code() == HttpURLConnection.HTTP_BAD_METHOD -> HandledError.BadRequestError(throwable = this)
    code() == HttpURLConnection.HTTP_INTERNAL_ERROR -> HandledError.ServerError(throwable = this)
    code() == HttpURLConnection.HTTP_BAD_GATEWAY -> HandledError.ServerError(throwable = this)
    else -> HandledError.UnknownError(throwable = this)
}

class NoLocalCacheFoundException(
    override val message: String = "No data found on Local cache"
) : Throwable(message)

fun CombinedLoadStates.onError(action: SingleBlock<HandledError>) {
    when {
        refresh is LoadState.Error -> action((refresh as LoadState.Error).error.toHandledError())
        append is LoadState.Error -> action((append as LoadState.Error).error.toHandledError())
        prepend is LoadState.Error -> action((prepend as LoadState.Error).error.toHandledError())
    }
}