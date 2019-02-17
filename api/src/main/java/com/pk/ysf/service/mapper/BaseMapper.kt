package com.pk.ysf.service.mapper

import java.util.stream.Collectors

interface BaseMapper<F, T> {

    fun map(from: F): T

    fun mapAll(collections: Collection<F>): Collection<T> {
        return collections.stream()
                .map { from -> map(from) }
                .collect(Collectors.toList())
    }
}