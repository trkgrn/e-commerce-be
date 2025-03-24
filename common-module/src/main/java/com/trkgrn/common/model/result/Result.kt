package com.trkgrn.common.model.result

sealed class Result @JvmOverloads constructor(
    var success: Boolean,
    var message: String? = null
)

class ErrorResult @JvmOverloads constructor(
    var errorDetail: Long? = null,
    message: String? = null
) : Result(success = false, message)

class SuccessResult @JvmOverloads constructor(
    message: String? = null
) : Result(success = true, message)

open class DataResult<T> @JvmOverloads constructor(
    var data: T,
    success: Boolean,
    message: String? = null
) : Result(success, message)

class ErrorDataResult<T> @JvmOverloads constructor(
    data: T,
    message: String? = null
) : DataResult<T>(data, success = false, message)

class SuccessDataResult<T> @JvmOverloads constructor(
    data: T,
    message: String? = null
) : DataResult<T>(data, success = true, message)
