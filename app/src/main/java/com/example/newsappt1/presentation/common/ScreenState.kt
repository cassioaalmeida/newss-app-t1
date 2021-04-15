package com.example.newsappt1.presentation.common

sealed class ScreenState<out T> {
    class Success<out T>(val data: T) : ScreenState<T>()
    class Error : ScreenState<Nothing>()
    class Loading : ScreenState<Nothing>()
}