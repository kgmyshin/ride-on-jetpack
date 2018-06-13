package com.kgmyshin.todo.util.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

fun <T> List<Single<T>>.sequence(): Single<List<T>> = fold(Flowable.empty<T>(), { acc, n ->
    acc.concatWith(n.toFlowable())
}).toList()

fun <T> List<Maybe<T>>.sequence(): Maybe<List<T>> = fold(Maybe.just(mutableListOf()), { acc, n ->
    n.isEmpty.flatMapMaybe { isEmpty ->
        if (!isEmpty) {
            acc.flatMap { list ->
                n.map { t -> (list as MutableList<T>).apply { add(t) } }
            }
        } else {
            acc
        }
    }
})

fun List<Completable>.sequence(): Completable = if (isEmpty()) {
    Completable.complete()
} else {
    reduce { acc, completable ->
        acc.andThen(completable)
    }
}