package kh.farrukh.progee.data.framework

import kh.farrukh.progee.api.framework.FrameworkApi
import kh.farrukh.progee.api.framework.models.FrameworkApiModel
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 2:29 AM
 *kh.farrukh.progee.data.framework
 **/
class FrameworkRemoteDataSource @Inject constructor(
    private val frameworkApi: FrameworkApi
) {

    suspend fun getFrameworkById(languageId: Long, frameworkId: Long): FrameworkApiModel =
        frameworkApi.getFrameworkById(languageId, frameworkId)
}