package com.dehaat.goodreads.utils

object GlobalConfig {

    object Core {
        const val RELEASE_LOG_TAG = "Mini GoodReads"
        const val CACHE_NAME = RELEASE_LOG_TAG + "_cache"
    }

    object Settings {
        const val VALID_PASSWORD = "aaa"
        const val ENABLE_LOGS = true
        const val CACHE_SIZE: Long = 60 * 1024 * 1024
    }

    object Network {
        const val HEADER_AUTH_TOKEN = "HEADER_AUTH_TOKEN"
    }

    object Endpoint {
        const val URL_BASE = "https://485b074b-80e0-442c-88fc-db2bc45708c5.mock.pstmn.io"

        const val URL_LOGIN_REQUEST = "/login"
        const val URL_AUTHOR_REQUEST = "/books"
    }

    object Extras {
        const val URL_DEVELOPER = "https://www.linkedin.com/in/navjotsinghbedi/"
        const val URL_CODE = "https://github.com/navjotbedi/Mini-GoodReads"
    }

    object Preference {
        const val KEY_AUTH_TOKEN = "KEY_AUTH_TOKEN"
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