package kh.farrukh.progee.data.framework

import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.db.CacheDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 2:29 AM
 *kh.farrukh.progee.data.framework
 **/
class FrameworkLocalDataSource @Inject constructor(
    private val cacheDatabase: CacheDatabase
) {

    private val frameworkDao by lazy { cacheDatabase.frameworkDao() }

    fun getFrameworkById(frameworkId: Long): Flow<Framework> =
        frameworkDao.getFrameworkById(frameworkId)

    suspend fun saveFramework(framework: Framework) =
        frameworkDao.saveFramework(framework)
}