package kh.farrukh.progee.ui.language_details

import kh.farrukh.movix.utils.error_handle.HandledError
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.utils.Result

/**
 *Created by farrukh_kh on 6/24/22 12:35 AM
 *kh.farrukh.progee.ui.language_details
 **/
data class LanguageDetailsViewState(
    val isLoading: Boolean = true,
    val language: Language? = null,
    val error: HandledError? = null,
)

val LanguageDetailsViewState.isFirstLoading get() = isLoading && language == null && error == null
val LanguageDetailsViewState.isEmpty get() = !isLoading && language == null

fun Result<Language>.toLanguageDetailsViewState(oldState: LanguageDetailsViewState): LanguageDetailsViewState =
    when (this) {
        is Result.Error -> oldState.copy(isLoading = false, error = error, language = data)
        is Result.Loading -> oldState.copy(isLoading = true, language = data)
        is Result.Success -> oldState.copy(isLoading = false, language = data)
    }