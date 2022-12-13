package zeng.qiang.base.net.observer

import androidx.lifecycle.Observer
import zeng.qiang.base.entity.*

abstract class IStateObserver<T> : Observer<BaseResponse<T>> {

    override fun onChanged(response: BaseResponse<T>) {
        when (response) {
            is ApiSuccessResponse -> onSuccess(response.data)
            is ApiEmptyResponse -> onDataEmpty()
            is ApiFailedResponse -> onFailed(response.code, response.msg)
            is ApiErrorResponse -> onError(response.throwable)
        }
        onComplete()
    }

    abstract fun onSuccess(data: T)

    abstract fun onDataEmpty()

    abstract fun onError(e: Throwable)

    abstract fun onComplete()

    abstract fun onFailed(code: String, msg: String)

}