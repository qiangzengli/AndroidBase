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

data class ApiSuccessResponse<T>(override val data: T) : BaseResponse<T>()

class ApiEmptyResponse<T> : BaseResponse<T>()

data class ApiFailedResponse<T>(override val code: String, override val msg: String) :
    BaseResponse<T>()

data class ApiErrorResponse<T>(override val throwable: Throwable) :
    BaseResponse<T>()