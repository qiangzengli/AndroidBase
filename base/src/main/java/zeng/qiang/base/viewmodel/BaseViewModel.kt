package zeng.qiang.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import zeng.qiang.base.entity.BaseEntity
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * ViewModel 基类
 */
typealias  EmptyCallback = () -> Unit
typealias  ThrowableCallback = (t: Throwable) -> Unit

open class BaseViewModel : ViewModel() {

    fun <T> request(
        block: suspend () -> BaseEntity<T>,
        onCompletion: EmptyCallback? = null,
        onError: ThrowableCallback? = null,
        onStart: EmptyCallback? = null,
    ) {
        viewModelScope.launch {
            flow {
                emit(block)
            }
                .onStart {
                    onStart?.invoke()
                }
                .onCompletion {
                    onCompletion?.invoke()
                }.catch {
                    onError?.invoke(it)
                    // TODO: 网络异常上传处理
                }
                .collect()

        }
    }

}


val Throwable.code: Int
    get() =
        when (this) {
            is HttpStatusCodeException -> this.statusCode //Http状态码异常
            is ParseException -> this.errorCode.toIntOrNull() ?: -1     //业务code异常
            else -> -1
        }

val Throwable.msg: String
    get() = when (this) {
        is UnknownHostException -> "当前无网络，请检查你的网络设置"
        is SocketTimeoutException,
        is TimeoutException,
        is TimeoutCancellationException -> "连接超时,请稍后再试"
        // 网络异常
        is ConnectException -> "网络不给力，请稍候重试！"
        // 请求失败
        is HttpStatusCodeException -> statusCode.httpErrorMsg
        //请求成功，但Json语法异常,导致解析失败
        is JsonSyntaxException -> "数据解析失败,请检查数据是否正确"
        // ParseException异常表明请求成功，但是数据不正确
        is ParseException -> this.message ?: errorCode   //msg为空，显示code
        else -> "请求失败，请稍后再试"
    }

val Int.httpErrorMsg: String
    get() = when (this) {
        500 -> "服务端错误"
        404 -> "请求URL地址未找到"
        else -> "Http状态码异常,$this"
    }

