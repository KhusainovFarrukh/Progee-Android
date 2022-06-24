package kh.farrukh.progee.data.review

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kh.farrukh.progee.api.review.ReviewApi
import kh.farrukh.progee.data.review.models.Review
import kh.farrukh.progee.db.CacheDatabase
import kh.farrukh.progee.di.modules.IoDispatcher
import kh.farrukh.progee.utils.NetworkHelper
import kh.farrukh.progee.utils.Result
import kh.farrukh.progee.utils.requestWithLocalCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by farrukh_kh on 6/25/22 12:17 AM
 *kh.farrukh.progee.data.review
 **/
@OptIn(ExperimentalPagingApi::class)
class ReviewRepositoryImpl @Inject constructor(
    private val remoteDataSource: ReviewRemoteDataSource,
    private val localDataSource: ReviewLocalDataSource,
    private val reviewApi: ReviewApi,
    private val cacheDatabase: CacheDatabase,
    private val networkHelper: NetworkHelper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ReviewRepository {

    override fun getPagedReviews(languageId: Long): Flow<PagingData<Review>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = ReviewRemoteMediator(languageId, reviewApi, cacheDatabase)
    ) {
        cacheDatabase.reviewDao().reviewPagingSourceByLanguageId(languageId)
    }.flow

    override fun getReviewById(languageId: Long, reviewId: Long): Flow<Result<Review>> =
        requestWithLocalCache(
            remoteFetch = { remoteDataSource.getReviewById(languageId, reviewId) },
            localQuery = { localDataSource.getReviewById(reviewId) },
            saveFetchResult = { language -> localDataSource.saveReview(language) },
            remoteResponseMapper = { apiModel -> apiModel.toReview() },
            shouldFetch = { networkHelper.hasConnection },
            coroutineContext = ioDispatcher
        )
}