package com.pk.ysf.api.service.util

import com.pk.ysf.api.model.dto.SearchModel
import com.pk.ysf.api.model.entity.SoccerField
import com.pk.ysf.api.model.exception.CriteriaSearchException
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
class SearchFactory {

    @PersistenceContext
    private val entityManager: EntityManager? = null

    fun getSoccerFieldByCustomCriteria(searchModel: SearchModel): List<SoccerField> {
        if(entityManager == null) {
            throw CriteriaSearchException("Not found entity manager")
        }

        val criteriaBuilder = entityManager!!.criteriaBuilder
        val query = criteriaBuilder.createQuery(SoccerField::class.java)
        val root = query.from(SoccerField::class.java)
        val defaultPredicate = criteriaBuilder.isNotNull(root.get<Any>("id"))

        var nameContains = defaultPredicate
        if (searchModel.name != null) {
            nameContains = criteriaBuilder.like(
                    root.get("name"),
                    searchModel.name!! + '%'
            )
        }

        var isPaid = defaultPredicate
        if (searchModel.paid != null) {
            isPaid = if (searchModel.paid == true)
                criteriaBuilder.isNotNull(root.get<Any>("price"))
            else
                criteriaBuilder.isNull(root.get<Any>("price"))
        }

        var isLighting = defaultPredicate
        if (searchModel.lighting != null) {
            isLighting = criteriaBuilder.equal(
                    root.get<Any>("isLighting"),
                    searchModel.lighting
            )
        }

        var isFenced = defaultPredicate
        if (searchModel.fenced != null) {
            isFenced = criteriaBuilder.equal(
                    root.get<Any>("isFenced"),
                    searchModel.fenced
            )
        }

        var isLockerRoom = defaultPredicate
        if (searchModel.lockerRoom != null) {
            isLockerRoom = criteriaBuilder.equal(
                    root.get<Any>("isLockerRoom"),
                    searchModel.lockerRoom
            )
        }

        var minWidthPredicate = defaultPredicate
        if (searchModel.minWidth != null) {
            minWidthPredicate = criteriaBuilder.greaterThanOrEqualTo<Int>(
                    root.get("width"),
                    searchModel.minWidth
            )
        }

        var maxWidthPredicate = defaultPredicate
        if (searchModel.maxWidth != null) {
            maxWidthPredicate = criteriaBuilder.lessThanOrEqualTo<Int>(
                    root.get("width"),
                    searchModel.maxWidth
            )
        }

        var minLengthPredicate = defaultPredicate
        if (searchModel.minLength != null) {
            minLengthPredicate = criteriaBuilder.greaterThanOrEqualTo<Int>(
                    root.get("length"),
                    searchModel.minLength
            )
        }

        var maxLengthPredicate = defaultPredicate
        if (searchModel.maxLength != null) {
            maxLengthPredicate = criteriaBuilder.lessThanOrEqualTo<Int>(
                    root.get("length"),
                    searchModel.maxLength
            )
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
        ))

        return entityManager.createQuery(query.select(root)).resultList
    }
}
