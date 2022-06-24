package kh.farrukh.progee.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kh.farrukh.progee.data.framework.models.Framework
import kh.farrukh.progee.data.image.models.Image
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.data.review.models.Review
import kh.farrukh.progee.data.user.models.User
import kh.farrukh.progee.db.framework.FrameworkDao
import kh.farrukh.progee.db.framework.FrameworkRemoteKeyDao
import kh.farrukh.progee.db.framework.models.FrameworkRemoteKey
import kh.farrukh.progee.db.language.LanguageDao
import kh.farrukh.progee.db.language.LanguageRemoteKeyDao
import kh.farrukh.progee.db.language.models.LanguageRemoteKey
import kh.farrukh.progee.db.review.ReviewDao
import kh.farrukh.progee.db.review.ReviewRemoteKeyDao
import kh.farrukh.progee.db.review.ReviewTypeConverters
import kh.farrukh.progee.db.review.models.ReviewRemoteKey

/**
 *Created by farrukh_kh on 6/19/22 8:00 PM
 *kh.farrukh.progee.db
 **/
@Database(
    entities = [
        Language::class,
        LanguageRemoteKey::class,
        Framework::class,
        FrameworkRemoteKey::class,
        Review::class,
        ReviewRemoteKey::class,
        User::class,
        Image::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ReviewTypeConverters::class)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun languageDao(): LanguageDao
    abstract fun languageRemoteKeyDao(): LanguageRemoteKeyDao
    abstract fun frameworkDao(): FrameworkDao
    abstract fun frameworkRemoteKeyDao(): FrameworkRemoteKeyDao
    abstract fun reviewDao(): ReviewDao
    abstract fun reviewRemoteKeyDao(): ReviewRemoteKeyDao
}