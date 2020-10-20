package com.sunasterisk.appchat.utils

import java.lang.Exception
import com.sunasterisk.appchat.db.Result

suspend fun <T : Any> getOneShotResult(function: suspend () -> T): Result<T> = try {
    Result.success(function())
} catch (e: Exception) {
    Result.failed(e)
}
