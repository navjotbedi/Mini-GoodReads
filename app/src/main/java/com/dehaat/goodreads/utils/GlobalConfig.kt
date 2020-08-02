package com.dehaat.goodreads.utils

object GlobalConfig {

    object Core {
        const val RELEASE_LOG_TAG = "Mini GoodReads"
        const val CACHE_NAME = RELEASE_LOG_TAG + "_cache"
    }

    object Settings {
        const val ENABLE_LOGS = true
        const val CACHE_SIZE: Long = 60 * 1024 * 1024
    }

    object Endpoint {
        const val URL_BASE = ""

        const val URL_LOGIN_REQUEST = ""
        const val URL_AUTHOR_REQUEST = ""
    }

    object DB {
        const val DATABASE_NAME = "MiniGoodReadsDB"
        const val DATABASE_VERSION = 1
        const val COLUMN_ID = "id"

        object Author {
            const val TABLE_NAME = "author"

            const val COLUMN_NAME = "name"
            const val COLUMN_BIO = "bio"
        }

        object Book {
            const val TABLE_NAME = "book"

            const val COLUMN_AUTHOR_ID = "author_id"
            const val COLUMN_TITLE = "title"
            const val COLUMN_PUBLISHER = "publisher"
            const val COLUMN_PUBLISHED_DATE = "published_date"
            const val COLUMN_DESCRIPTION = "description"
            const val COLUMN_PRICE = "price"
        }
    }
}