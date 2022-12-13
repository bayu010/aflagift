package com.example.alfagift.model.util

import androidx.annotation.IntDef

/**
 * Created by Alvin Rusli on 06/07/2017.
 *
 * The resource object for view models.
 */
class Resource<T> private constructor(@State var status: Int, var data: T? = null, var error: AppError? = AppError(0, null), var errorMessage: String? = null) {

    companion object {

        const val LOADING = 0
        const val SUCCESS = 1
        const val ERROR = 2

        @IntDef(LOADING,
            SUCCESS,
            ERROR)
        annotation class State

        /** Creates a new loading resource object  */
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(status = LOADING, data = data)
        }

        /**
         * Creates a new successful resource object.
         * @param data the data to be set
         */
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(status = SUCCESS, data = data)
        }

        /**
         * Creates a new error resource object.
         * @param error the error
         */
        fun <T> error(error: AppError? = null, data: T? = null, errorMessage: String? = null): Resource<T> {
            return Resource(status = ERROR, data = data, error = error, errorMessage = errorMessage)
        }
    }
}
