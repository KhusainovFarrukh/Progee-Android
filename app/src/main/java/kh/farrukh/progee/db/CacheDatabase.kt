package kh.farrukh.progee.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kh.farrukh.progee.data.language.models.Language
import kh.farrukh.progee.db.language.LanguageDao
import kh.farrukh.progee.db.language.LanguageRemoteKeyDao

/**
 *Created by farrukh_kh on 6/19/22 8:00 PM
 *kh.farrukh.progee.db
 **/
@Database(
    entities = [
        Language::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun languageDao(): LanguageDao
    abstract fun languageRemoteKeyDao(): LanguageRemoteKeyDao
}