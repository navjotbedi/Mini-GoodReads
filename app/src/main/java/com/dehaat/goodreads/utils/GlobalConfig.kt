package com.dehaat.goodreads.utils

object GlobalConfig {

    object Core {
        const val RELEASE_LOG_TAG = "Mini GoodReads"
    }

    object Settings{
        const val ENABLE_LOGS = true
    }

    object Endpoint {
        const val URL_LOGIN_REQUEST = ""
        const val URL_AUTHOR_REQUEST = ""
    }

    object DB {
        const val DATABASE_NAME = "MiniGoodReadsDB"
        const val DATABASE_VERSION = 1

        object Author {
            const val TABLE_NAME = "author"
        }

        object Book {
            const val TABLE_NAME = "book"
        }
    }
}