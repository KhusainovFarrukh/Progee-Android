package kh.farrukh.progee.domain.framework_list

import androidx.paging.PagingData
import kh.farrukh.progee.data.framework.FrameworkRepository
import kh.farrukh.progee.data.framework.models.Framework
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 2:41 AM
 *kh.farrukh.progee.domain.framework_list
 **/
class GetFrameworkListUseCase @Inject constructor(
    private val frameworkRepository: FrameworkRepository
) {

    operator fun invoke(): Flow<PagingData<Framework>> =
        frameworkRepository.getPagedFrameworks()
}