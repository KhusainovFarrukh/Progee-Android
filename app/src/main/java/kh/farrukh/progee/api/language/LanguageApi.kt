package kh.farrukh.progee.api.language

import kh.farrukh.progee.api.global.models.PagingApiResponse
import kh.farrukh.progee.api.global.models.PublishState
import kh.farrukh.progee.api.language.models.LanguageApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Created by farrukh_kh on 6/18/22 2:06 PM
 *kh.farrukh.progee.api.language
 **/
interface LanguageApi {

    @GET("languages")
    suspend fun getLanguages(
        @Query("state") state: PublishState? = null,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("sort_by") sortBy: String? = null,
        @Query("order_by") orderBy: String? = null
    ): PagingApiResponse<LanguageApiModel>

    @GET("languages/{languageId}")
    suspend fun getLanguageById(
        @Path("languageId") languageId: Long
    ): LanguageApiModel
}