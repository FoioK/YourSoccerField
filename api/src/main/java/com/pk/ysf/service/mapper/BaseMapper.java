package com.pk.ysf.service.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

@FunctionalInterface
public interface BaseMapper<F, T> {

    T map(F object);

    default Collection<T> mapAll(Collection<F> collections) {
        return collections.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
