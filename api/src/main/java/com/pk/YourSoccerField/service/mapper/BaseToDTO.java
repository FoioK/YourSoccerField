package com.pk.YourSoccerField.service.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

@FunctionalInterface
public interface BaseToDTO<E, D> {

    D createFromEntity(E entity);

    default Collection<D> mapAllFromEntities(Collection<E> entities) {
        return entities.stream()
                .map(this::createFromEntity)
                .collect(Collectors.toList());
    }
}
