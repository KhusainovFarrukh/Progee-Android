package kh.farrukh.progee.data.review

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kh.farrukh.progee.api.review.ReviewApi
import kh.farrukh.progee.api.review.models.ReviewValue
import kh.farrukh.progee.data.review.models.Review
import kh.farrukh.progee.db.CacheDatabase
import kh.farrukh.progee.db.review.models.ReviewRemoteKey
import retrofit2.HttpException
import java.io.IOException

/**
 *Created by farrukh_kh on 6/25/22 12:14 AM
 *kh.farrukh.progee.data.review
 **/
@OptIn(ExperimentalPagingApi::class)
class ReviewRemoteMediator(
    private val languageId: Long,
    private val value: ReviewValue?,
    private val reviewApi: ReviewApi,
    private val cacheDatabase: CacheDatabase
) : RemoteMediator<Int, Review>() {

    private val reviewDao by lazy { cacheDatabase.reviewDao() }
    private val remoteKeyDao by lazy { cacheDatabase.reviewRemoteKeyDao() }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Review>
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
            val response = reviewApi.getReviews(languageId = languageId, page = page, value = value)

            val endOfPaginationReached = response.page >= response.totalPages

            cacheDatabase.withTransaction {
                // TODO: needs logic change
                if (loadType == LoadType.REFRESH) {
                    reviewDao.deleteAll()
                    remoteKeyDao.deleteAll()
                }

                val keys = response.items.map { review ->
                    ReviewRemoteKey(
                        reviewId = review.id,
                        prevPage = response.prevPage,
                        nextPage = response.nextPage
                    )
                }

                remoteKeyDao.saveRemoteKeys(keys)
                reviewDao.saveReviews(response.items.map { it.toReview() })
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Review>): ReviewRemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { review -> remoteKeyDao.getRemoteKeyByReviewId(review.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Review>): ReviewRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { review -> remoteKeyDao.getRemoteKeyByReviewId(review.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Review>
    ): ReviewRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { reviewId ->
                remoteKeyDao.getRemoteKeyByReviewId(reviewId)
            }
        }
    }
}