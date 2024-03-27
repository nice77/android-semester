package com.example.task.utils

import java.util.concurrent.CancellationException

public suspend inline fun <T> runSuspendCatching(block : () -> T) : Result<T> {
    return try {
        Result.success(block())
    } catch (e : CancellationException) {
        throw e
    } catch (e : Throwable) {
        Result.failure(e)
    }
}