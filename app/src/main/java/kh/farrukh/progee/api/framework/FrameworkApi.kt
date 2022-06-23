package kh.farrukh.progee.api.framework

import kh.farrukh.progee.api.framework.models.FrameworkApiModel
import kh.farrukh.progee.api.global.models.PagingApiResponse
import kh.farrukh.progee.api.global.models.PublishState
import kh.farrukh.progee.api.language.models.LanguageApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Created by farrukh_kh on 6/24/22 2:17 AM
 *kh.farrukh.progee.api.framework
 **/
interface FrameworkApi {

    @GET("frameworks")
    suspend fun getFrameworks(
        @Query("state") state: PublishState? = null,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("sort_by") sortBy: String? = null,
        @Query("order_by") orderBy: String? = null
    ): PagingApiResponse<FrameworkApiModel>

    @GET("frameworks/{frameworkId}")
    suspend fun getFrameworkById(
        @Path("frameworkId") frameworkId: Long
    ): FrameworkApiModel
}