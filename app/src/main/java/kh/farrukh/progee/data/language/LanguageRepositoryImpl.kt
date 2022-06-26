package kh.farrukh.progee.data.language

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.sqlite.db.SimpleSQLiteQuery
import kh.farrukh.progee.api.language.LanguageApi
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.data.language.models.SortType
import kh.farrukh.progee.db.CacheDatabase
import kh.farrukh.progee.di.modules.IoDispatcher
import kh.farrukh.progee.utils.NetworkHelper
import kh.farrukh.progee.utils.Result
import kh.farrukh.progee.utils.requestWithLocalCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/19/22 8:19 PM
 *kh.farrukh.progee.data.language
 **/
@OptIn(ExperimentalPagingApi::class)
class LanguageRepositoryImpl @Inject constructor(
    private val remoteDataSource: LanguageRemoteDataSource,
    private val localDataSource: LanguageLocalDataSource,
    private val languageApi: LanguageApi,
    private val cacheDatabase: CacheDatabase,
    private val networkHelper: NetworkHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LanguageRepository {

    override fun getPagedLanguages(sortType: SortType): Flow<PagingData<Language>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = LanguageRemoteMediator(sortType, languageApi, cacheDatabase)
    ) {
        cacheDatabase.languageDao().languagePagingSource(
            SimpleSQLiteQuery(
                "SELECT * FROM languages ORDER BY ${sortType.sortBy} ${sortType.orderBy.uppercase()}"
            )
        )
    }.flow

    override fun getLanguageById(languageId: Long): Flow<Result<Language>> = requestWithLocalCache(
        remoteFetch = { remoteDataSource.getLanguageById(languageId) },
        localQuery = { localDataSource.getLanguageById(languageId) },
        saveFetchResult = { language -> localDataSource.saveLanguage(language) },
        remoteResponseMapper = { apiModel -> apiModel.toLanguage() },
        shouldFetch = { networkHelper.hasConnection },
        coroutineContext = ioDispatcher
    )
}