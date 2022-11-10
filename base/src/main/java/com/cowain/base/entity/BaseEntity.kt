package com.cowain.base.entity

/**
 * 响应体基类
 */
data class BaseEntity<T>(
    val msg: String?,
    val code: String?,
    val status: Int?,
    val data: T?,
) {
    /**
     * 是否请求成功
     */
    val isSucceed get() = code == "0"
}