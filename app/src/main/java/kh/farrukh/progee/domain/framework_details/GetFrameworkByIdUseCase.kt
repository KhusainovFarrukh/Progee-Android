package kh.farrukh.progee.domain.framework_details

import kh.farrukh.progee.data.framework.FrameworkRepository
import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 2:42 AM
 *kh.farrukh.progee.domain.framework_details
 **/
class GetFrameworkByIdUseCase @Inject constructor(
    private val frameworkRepository: FrameworkRepository
) {

    operator fun invoke(languageId: Long, frameworkId: Long): Flow<Result<Framework>> =
        frameworkRepository.getFrameworkById(languageId, frameworkId)
}