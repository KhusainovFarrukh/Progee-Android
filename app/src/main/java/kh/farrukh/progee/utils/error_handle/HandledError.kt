package kh.farrukh.movix.utils.error_handle

/**
 *Created by farrukh_kh on 3/25/22 3:58 PM
 *kh.farrukh.movix.utils.error_handle
 **/
sealed class HandledError(val message: String, val throwable: Throwable?) {

    class ConnectionError(
        message: String = "There is no network connection",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)

    class AuthError(
        message: String = "Authentication error",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)

    class PaymentError(
        message: String = "Payment error",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)

    class NotFoundError(
        message: String = "Data not found error",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)

    class PermissionError(
        message: String = "Permission error",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)

    class BadRequestError(
        message: String = "Bad request error",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)

    class ServerError(
        message: String = "Server side error",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)

    class JsonParseError(
        message: String = "Response parsing error",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)

    class NoLocalCacheError(
        message: String = "There is no local cache for this data",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)

    class UnknownError(
        message: String = "Unknown error",
        throwable: Throwable? = null
    ) : HandledError(message, throwable)
}