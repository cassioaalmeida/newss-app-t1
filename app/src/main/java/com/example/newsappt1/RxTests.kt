package com.example.newsappt1

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

val numbers: List<Int> = listOf(1, 2, 3, 4, 5)
val numbersObservable: Observable<Int> =
    Observable.fromIterable(numbers)

val numbers2: List<Int> = listOf(100, 200, 300)
val numbers2Observable: Observable<Int> =
    Observable.fromIterable(numbers2)
        .concatMap { Observable.just(it).delay(1, TimeUnit.SECONDS) }

val strings: List<String> = listOf("StringA", "StringB", "StringC")
val stringsObservable: Observable<String> = Observable.fromIterable(strings)

fun main() {
    numbersObservable
        .flatMap { number ->
            stringsObservable
                .map { string ->
                    "$number. $string"
                }
        }.subscribe {
            println(it)
        }


    // 1. StringA
    // 1. StringB
    // 1. StringC
    // 2. StringA
    // 2. StringB
    // 2. StringC
    // 3. StringA
    // ...
    // flatMap

}





//fun main() {
//
//    emitItems(
//        {
//            println("Emitted item $it")
//        },
//        {
//            10 * it
//        },
//        {
//            it + 5
//        }
//    )
//}
//
//fun emitItems(
//    printNumber: (Int) -> Unit,
//    multipli: (Int) -> Int,
//    sum: (Int) -> Int
//) {
//    numbers.forEach { number ->
//        val result1 = multipli(number)
//        val result2 = sum(result1)
//        printNumber(result2)
//    }
//}