package kh.farrukh.progee.api.global.models

import com.google.gson.annotations.SerializedName

/**
 *Created by farrukh_kh on 6/18/22 2:20 PM
 *kh.farrukh.progee.api.global.models
 **/
data class PagingApiResponse<T>(
    val page: Int,
    val items: List<T>,
    @SerializedName("next_page")
    val nextPage: Int?,
    @SerializedName("prev_page")
    val prevPage: Int?,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_items")
    val totalItems: Int
)