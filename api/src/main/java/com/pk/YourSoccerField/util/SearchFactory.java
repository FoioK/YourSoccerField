package com.pk.YourSoccerField.util;

import com.pk.YourSoccerField.service.dtoModel.SearchModel;
import com.pk.ysf.apimodels.model.SoccerField;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class SearchFactory {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SoccerField> getSoccerFieldByCustomCriteria(SearchModel searchModel) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SoccerField> query = criteriaBuilder.createQuery(SoccerField.class);
        Root<SoccerField> root = query.from(SoccerField.class);
        Predicate defaultPredicate = criteriaBuilder.isNotNull(root.get("id"));

        Predicate nameContains = defaultPredicate;
        if (searchModel.getName() != null) {
            nameContains = criteriaBuilder.like(
                    root.get("name"),
                    searchModel.getName() + '%'
            );
        }

        Predicate isPaid = defaultPredicate;
        if (searchModel.getPaid() != null) {
            isPaid = searchModel.getPaid() ?
                    criteriaBuilder.isNotNull(root.get("price")) :
                    criteriaBuilder.isNull(root.get("price"));
        }

        Predicate isLighting = defaultPredicate;
        if (searchModel.getLighting() != null) {
            isLighting = criteriaBuilder.equal(
                    root.get("isLighting"),
                    searchModel.getLighting()
            );
        }

        Predicate isFenced = defaultPredicate;
        if (searchModel.getFenced() != null) {
            isFenced = criteriaBuilder.equal(
                    root.get("isFenced"),
                    searchModel.getFenced()
            );
        }

        Predicate isLockerRoom = defaultPredicate;
        if (searchModel.getLockerRoom() != null) {
            isLockerRoom = criteriaBuilder.equal(
                    root.get("isLockerRoom"),
                    searchModel.getLockerRoom()
            );
        }

        Predicate minWidthPredicate = defaultPredicate;
        if (searchModel.getMinWidth() != null) {
            minWidthPredicate = criteriaBuilder.greaterThanOrEqualTo(
                    root.get("width"),
                    searchModel.getMinWidth()
            );
        }

        Predicate maxWidthPredicate = defaultPredicate;
        if (searchModel.getMaxWidth() != null) {
            maxWidthPredicate = criteriaBuilder.lessThanOrEqualTo(
                    root.get("width"),
                    searchModel.getMaxWidth()
            );
        }

        Predicate minLengthPredicate = defaultPredicate;
        if (searchModel.getMinLength() != null) {
            minLengthPredicate = criteriaBuilder.greaterThanOrEqualTo(
                    root.get("length"),
                    searchModel.getMinLength()
            );
        }

        Predicate maxLengthPredicate = defaultPredicate;
        if (searchModel.getMaxLength() != null) {
            maxLengthPredicate = criteriaBuilder.lessThanOrEqualTo(
                    root.get("length"),
                    searchModel.getMaxLength()
            );
        }

        query.where(criteriaBuilder.and(
                nameContains,
                isPaid,
                isLighting,
                isFenced,
                isLockerRoom,
                minWidthPredicate,
                maxWidthPredicate,
                minLengthPredicate,
                maxLengthPredicate
        ));

        return entityManager.createQuery(query.select(root)).getResultList();
    }
}
