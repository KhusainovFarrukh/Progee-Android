package kh.farrukh.progee.data.language.models

/**
 *Created by farrukh_kh on 6/26/22 1:28 PM
 *kh.farrukh.progee.data.language.models
 **/
sealed class SortType(val sortBy: String, val orderBy: String, val label: String = "$sortBy ($orderBy)") {
    object Default : SortType("id", "ASC", "Default")
    object NewestFirst : SortType("createdAt", "ASC", "Newest first")
    object OldestFirst : SortType("createdAt", "DESC", "Oldest first")
    object AtoZName : SortType("name", "ASC", "A to Z (Name)")
    object ZtoAName : SortType("name", "DESC", "Z to A (Name)")
    object AtoZDescription : SortType("description", "ASC", "A to Z (Description)")
    object ZtoADescription : SortType("description", "DESC", "Z to A (Description)")
    class Custom(sortBy: String, orderBy: String, label: String) :
        SortType(sortBy, orderBy, label)
}

fun languageSortTypes() = listOf(
    SortType.Default,
    SortType.NewestFirst,
    SortType.OldestFirst,
    SortType.AtoZName,
    SortType.ZtoAName,
    SortType.AtoZDescription,
    SortType.ZtoADescription
)