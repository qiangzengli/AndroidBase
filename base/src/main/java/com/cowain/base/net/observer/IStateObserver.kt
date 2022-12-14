package com.cowain.base.net.observer

import androidx.lifecycle.Observer
import com.cowain.base.response.*

abstract class IStateObserver<T> : Observer<BaseResponse<T>> {

    override fun onChanged(response: BaseResponse<T>) {
        when (response) {
            is SuccessResponse -> onSuccess(response.data)
            is EmptyResponse -> onDataEmpty()
            is FailedResponse -> onFailed(response.code, response.msg)
            is ErrorResponse -> onError(response.throwable)
        }
        onComplete()
    }

    abstract fun onSuccess(data: T)

    abstract fun onDataEmpty()

    abstract fun onError(e: Throwable)

    abstract fun onComplete()

    abstract fun onFailed(code: String, msg: String)

}