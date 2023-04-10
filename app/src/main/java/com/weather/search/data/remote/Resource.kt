package com.weather.search.data.remote

class Resource<T> constructor(
    statusVale: Status,
    dataValue: T?,
    messageValue: String?
) {
    val status = statusVale
    val data = dataValue
    val message = messageValue

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                statusVale = Status.SUCCESS,
                dataValue = data,
                messageValue = null
            )
        }

        fun <T> error(errorMessage: String? = ""): Resource<T> {
            return Resource(
                statusVale = Status.ERROR,
                dataValue = null,
                messageValue = errorMessage
            )
        }

        fun <T> loading(): Resource<T> {
            return Resource(
                statusVale = Status.LOADING,
                dataValue = null,
                messageValue = null
            )
        }
    }
}