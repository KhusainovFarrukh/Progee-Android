package kh.farrukh.progee.data.framework

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kh.farrukh.progee.api.framework.FrameworkApi
import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.db.CacheDatabase
import kh.farrukh.progee.di.modules.IoDispatcher
import kh.farrukh.progee.utils.NetworkHelper
import kh.farrukh.progee.utils.Result
import kh.farrukh.progee.utils.requestWithLocalCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/24/22 2:29 AM
 *kh.farrukh.progee.data.framework
 **/
@OptIn(ExperimentalPagingApi::class)
class FrameworkRepositoryImpl @Inject constructor(
    private val remoteDataSource: FrameworkRemoteDataSource,
    private val localDataSource: FrameworkLocalDataSource,
    private val frameworkApi: FrameworkApi,
    private val cacheDatabase: CacheDatabase,
    private val networkHelper: NetworkHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FrameworkRepository {

    override fun getPagedFrameworks(): Flow<PagingData<Framework>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = FrameworkRemoteMediator(frameworkApi, cacheDatabase)
    ) {
        cacheDatabase.frameworkDao().frameworkPagingSource()
    }.flow

    override fun getFrameworkById(frameworkId: Long): Flow<Result<Framework>> =
        requestWithLocalCache(
            remoteFetch = { remoteDataSource.getFrameworkById(frameworkId) },
            localQuery = { localDataSource.getFrameworkById(frameworkId) },
            saveFetchResult = { language -> localDataSource.saveFramework(language) },
            remoteResponseMapper = { apiModel -> apiModel.toFramework() },
            shouldFetch = { networkHelper.hasConnection },
            coroutineContext = ioDispatcher
        )
}