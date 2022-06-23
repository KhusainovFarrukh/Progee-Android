package kh.farrukh.progee.data.language

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kh.farrukh.progee.api.language.LanguageApi
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.db.CacheDatabase
import kh.farrukh.progee.db.language.models.LanguageRemoteKey
import retrofit2.HttpException
import java.io.IOException

/**
 *Created by farrukh_kh on 6/19/22 8:00 PM
 *kh.farrukh.progee.data.language
 **/
@OptIn(ExperimentalPagingApi::class)
class LanguageRemoteMediator(
    private val languageApi: LanguageApi,
    private val cacheDatabase: CacheDatabase
) : RemoteMediator<Int, Language>() {

    private val languageDao by lazy { cacheDatabase.languageDao() }
    private val remoteKeyDao by lazy { cacheDatabase.languageRemoteKeyDao() }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Language>
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
            val response = languageApi.getLanguages(page = page)

            val endOfPaginationReached = response.page >= response.totalPages

            cacheDatabase.withTransaction {
                // TODO: removed for task: Don't overwrite favState of movies on list/search queries
                //      if uncomment this lines, movieDao.upsertMoviesWithoutFavState() won't work
                if (loadType == LoadType.REFRESH) {
                    languageDao.deleteAll()
                    remoteKeyDao.deleteAll()
                }

                val prevPage = response.prevPage
                val nextPage = response.nextPage
                val keys = response.items.map { language ->
                    LanguageRemoteKey(
                        languageId = language.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                remoteKeyDao.addRemoteKeys(keys)
                languageDao.saveLanguages(response.items.map { it.toLanguage() })
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Language>): LanguageRemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { language ->
                remoteKeyDao.getRemoteKeyByLanguageId(language.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Language>): LanguageRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { language ->
                remoteKeyDao.getRemoteKeyByLanguageId(language.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Language>
    ): LanguageRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { languageId ->
                remoteKeyDao.getRemoteKeyByLanguageId(languageId)
            }
        }
    }
}