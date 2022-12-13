package com.example.alfagift.model.util

class AppError(val code: Int, val error: String? = null, val data: String? = null) : Exception(error) {

    companion object {

        const val CONNECTION_UNKNOWN = 1000
        const val CONNECTION_CANCELLED = 1001
        const val CONNECTION_CONNECTION = 1002
        const val CONNECTION_TIMEOUT = 1003

        const val DEVELOPMENT_UNKNOWN = 2000
        const val DEVELOPMENT_NULL_POINTER = 2001
        const val DEVELOPMENT_CLASS_CAST = 2002
        const val DEVELOPMENT_NOT_IMPLEMENTED = 2999

        fun unknown(message: String? = null): AppError {
            return AppError(DEVELOPMENT_UNKNOWN, message ?: "Unknown error")
        }

        fun nullPointer(message: String? = null): AppError {
            return AppError(DEVELOPMENT_NULL_POINTER, message ?: "Empty data error")
        }

        fun classCast(message: String? = null): AppError {
            return AppError(DEVELOPMENT_CLASS_CAST, message ?: "Class cast error")
        }

        fun notImplemented(message: String? = null): AppError {
            return AppError(DEVELOPMENT_NOT_IMPLEMENTED, message ?: "Not implemented error")
        }
    }
}