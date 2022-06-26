package kh.farrukh.progee.utils

import kh.farrukh.progee.utils.error_handle.HandledError

/**
 *Created by farrukh_kh on 3/24/22 10:27 PM
 *kh.farrukh.movix.utils
 **/
sealed class Result<T>(
    open val data: T? = null,
    open val error: HandledError? = null
) {
    class Success<T>(override val data: T) : Result<T>(data = data)
    class Loading<T>(data: T? = null) : Result<T>(data = data)
    class Error<T>(override val error: HandledError, data: T? = null) : Result<T>(error = error, data = data)
}