package zeng.qiang.base.entity

/**
 * 响应体基类
 */
open class BaseResponse<T>(
    open val msg: String? = null,
    open val code: String? = null,
    open val status: Int? = null,
    open val data: T? = null,
    open val throwable: Throwable? = null,
) {
    /**
     * 是否请求成功
     */
    val isSucceed get() = code == "0"
}

data class SuccessResponse<T>(override val data: T) : BaseResponse<T>()

class EmptyResponse<T> : BaseResponse<T>()

data class FailedResponse<T>(override val code: String, override val msg: String) :
    BaseResponse<T>()

data class ErrorResponse<T>(override val throwable: Throwable) :
    BaseResponse<T>()