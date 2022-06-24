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

    @GET("languages/{languageId}/frameworks")
    suspend fun getFrameworks(
        @Path("languageId") languageId: Long,
        @Query("state") state: PublishState? = null,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("sort_by") sortBy: String? = null,
        @Query("order_by") orderBy: String? = null
    ): PagingApiResponse<FrameworkApiModel>

    @GET("languages/{languageId}/frameworks/{frameworkId}")
    suspend fun getFrameworkById(
        @Path("languageId") languageId: Long,
        @Path("frameworkId") frameworkId: Long
    ): FrameworkApiModel
}