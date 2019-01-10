package com.pk.ysf.service.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

@FunctionalInterface
public interface BaseFromDTO<E, D> {

    E createFromDTO(D dto);

    default Collection<E> mapAllFromDTOS(Collection<D> dtos) {
        return dtos.stream()
                .map(this::createFromDTO)
                .collect(Collectors.toList());
    }
}
