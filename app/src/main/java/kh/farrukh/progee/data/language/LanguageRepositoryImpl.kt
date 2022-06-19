package kh.farrukh.progee.data.language

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kh.farrukh.progee.api.language.LanguageApi
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.db.CacheDatabase
import kh.farrukh.progee.utils.NetworkHelper
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
    private val networkHelper: NetworkHelper
) : LanguageRepository {

    override fun getPagedLanguages(): Flow<PagingData<Language>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = LanguageRemoteMediator(languageApi, cacheDatabase)
    ) {
        cacheDatabase.languageDao().languagePagingSource()
    }.flow
}