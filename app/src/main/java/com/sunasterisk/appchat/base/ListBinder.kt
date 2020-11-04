package com.sunasterisk.appchat.base

interface ListBinder<in T> {
    fun setData(data: T?)
}
