package kh.farrukh.progee.ui.framework_details

import kh.farrukh.progee.utils.error_handle.HandledError
import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.utils.Result

/**
 *Created by farrukh_kh on 6/24/22 12:19 PM
 *kh.farrukh.progee.ui.framework_details
 **/
data class FrameworkDetailsViewState(
    val isLoading: Boolean = true,
    val framework: Framework? = null,
    val error: HandledError? = null,
)

val FrameworkDetailsViewState.isFirstLoading get() = isLoading && framework == null && error == null
val FrameworkDetailsViewState.isEmpty get() = !isLoading && framework == null

fun Result<Framework>.toFrameworkDetailsViewState(oldState: FrameworkDetailsViewState): FrameworkDetailsViewState =
    when (this) {
        is Result.Error -> oldState.copy(isLoading = false, error = error, framework = data)
        is Result.Loading -> oldState.copy(isLoading = true, framework = data)
        is Result.Success -> oldState.copy(isLoading = false, framework = data)
    }