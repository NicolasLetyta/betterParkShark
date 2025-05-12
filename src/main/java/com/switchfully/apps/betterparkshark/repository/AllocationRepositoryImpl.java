package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.Allocation;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AllocationRepositoryImpl implements AllocationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Allocation> findFilteredAllocations(int limit, String statusFilter, String orderDirection) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Allocation> cq = cb.createQuery(Allocation.class);
        Root<Allocation> root = cq.from(Allocation.class);

        List<Predicate> predicates = new ArrayList<>();

        if (statusFilter != null) {
            switch (statusFilter.toUpperCase()) {
                case "ACTIVE" -> predicates.add(cb.isNull(root.get("endTime"))); // 'endTime' is null for active
                case "STOPPED" -> predicates.add(cb.isNotNull(root.get("endTime")));
                case "ALL" -> {} // No filter
            }
        }

        cq.where(predicates.toArray(new Predicate[0]));

        // Order by startTime
        if ("DESC".equalsIgnoreCase(orderDirection)) {
            cq.orderBy(cb.desc(root.get("startTime")));
        } else {
            cq.orderBy(cb.asc(root.get("startTime")));
        }

        return entityManager.createQuery(cq)
                .setMaxResults(limit)
                .getResultList();
    }
}
