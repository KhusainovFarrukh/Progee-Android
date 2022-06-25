package kh.farrukh.progee.data.framework

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kh.farrukh.progee.api.framework.FrameworkApi
import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.db.CacheDatabase
import kh.farrukh.progee.db.framework.models.FrameworkRemoteKey
import retrofit2.HttpException
import java.io.IOException

/**
 *Created by farrukh_kh on 6/24/22 2:29 AM
 *kh.farrukh.progee.data.framework
 **/
@OptIn(ExperimentalPagingApi::class)
class FrameworkRemoteMediator(
    private val languageId: Long,
    private val frameworkApi: FrameworkApi,
    private val cacheDatabase: CacheDatabase
) : RemoteMediator<Int, Framework>() {

    private val frameworkDao by lazy { cacheDatabase.frameworkDao() }
    private val remoteKeyDao by lazy { cacheDatabase.frameworkRemoteKeyDao() }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Framework>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                // TODO: add maxSize to PagingConfig and test LoadType.PREPEND
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            // TODO: handle nullability (if Response wrapper is used)
            val response = frameworkApi.getFrameworks(languageId = languageId, page = page)

            val endOfPaginationReached = response.page >= response.totalPages

            cacheDatabase.withTransaction {
                // TODO: needs logic change
                if (loadType == LoadType.REFRESH) {
                    frameworkDao.deleteAll()
                    remoteKeyDao.deleteAll()
                }

                val keys = response.items.map { framework ->
                    FrameworkRemoteKey(
                        frameworkId = framework.id,
                        prevPage = response.prevPage,
                        nextPage = response.nextPage
                    )
                }

                remoteKeyDao.saveRemoteKeys(keys)
                frameworkDao.saveFrameworks(response.items.map { it.toFramework() })
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Framework>): FrameworkRemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { framework -> remoteKeyDao.getRemoteKeyByFrameworkId(framework.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Framework>): FrameworkRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { framework -> remoteKeyDao.getRemoteKeyByFrameworkId(framework.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Framework>
    ): FrameworkRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { frameworkId ->
                remoteKeyDao.getRemoteKeyByFrameworkId(frameworkId)
            }
        }
    }
}