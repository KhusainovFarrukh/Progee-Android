package kh.farrukh.progee.domain.language_details

import kh.farrukh.progee.data.language.LanguageRepository
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 12:26 AM
 *kh.farrukh.progee.domain.language_details
 **/
class GetLanguageByIdUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {

    operator fun invoke(languageId: Long): Flow<Result<Language>> =
        languageRepository.getLanguageById(languageId)
}