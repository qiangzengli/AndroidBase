package com.cowain.base.viewmodel

import androidx.lifecycle.ViewModel
import com.google.gson.JsonSyntaxException
import com.kunminx.architecture.domain.message.MutableResult
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import com.cowain.base.entity.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * ViewModel 基类
 */
open class BaseViewModel : ViewModel() {
    // 监听加载进度条的状态
    private val _loadingState = MutableResult(false)
    val loadingState = _loadingState

    // toast 信息
    private val _toastState = MutableResult("")
    val toastState = _toastState
    protected suspend fun <T> executeHttp(
        loadingEnable: Boolean = true,
        block: suspend () -> BaseResponse<T>
    ): BaseResponse<T> {
        runCatching {
            if (loadingEnable) {
                loadingState.value = true
            }
            block.invoke()
        }.onSuccess { data: BaseResponse<T> ->
            if (loadingEnable) {
                loadingState.value = false
            }
            return handleHttpOk(data)
        }.onFailure { e ->
            if (loadingEnable) {
                loadingState.value = false
            }
            toastState.value = e.msg
            return ErrorResponse(e)
        }
        return EmptyResponse()
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: BaseResponse<T>): BaseResponse<T> {
        return if (data.isSucceed) {
            getHttpSuccessResponse(data)
        } else {
            toastState.value = data.msg
            FailedResponse(data.code!!, data.msg!!)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(response: BaseResponse<T>): BaseResponse<T> {
        val data = response.data
        return if (data == null || data is List<*> && (data as List<*>).isEmpty()) {
            EmptyResponse()
        } else {
            SuccessResponse(data)
        }
    }

}

val Throwable.msg: String
    get() = when (this) {
        is UnknownHostException -> "当前无网络"
        is SocketTimeoutException,
        is TimeoutException,
        is TimeoutCancellationException -> "连接超时"
        // 网络异常
        is ConnectException -> "连接异常"
        // 请求失败
        is HttpException -> code().httpErrorMsg
        //请求成功，但Json语法异常,导致解析失败
        is JsonSyntaxException -> "数据解析失败"
        else -> "请求失败"
    }

val Int.httpErrorMsg: String
    get() = when (this) {
        500 -> "服务端异常"
        404 -> "接口地址未找到"
        else -> "请求异常,状态码：$this"
    }
