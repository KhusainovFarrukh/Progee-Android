package kh.farrukh.progee.data.framework

import androidx.paging.PagingData
import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.utils.Result
import kotlinx.coroutines.flow.Flow

/**
 *Created by farrukh_kh on 6/24/22 2:29 AM
 *kh.farrukh.progee.data.framework
 **/
interface FrameworkRepository {

    fun getPagedFrameworks(): Flow<PagingData<Framework>>

    fun getFrameworkById(frameworkId: Long): Flow<Result<Framework>>
}