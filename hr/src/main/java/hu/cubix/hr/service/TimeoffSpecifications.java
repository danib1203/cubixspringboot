package hu.cubix.hr.service;

import hu.cubix.hr.model.Timeoff;
import hu.cubix.hr.model.Timeoff_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeoffSpecifications {

    public static Specification<Timeoff> statusMatch(String status) {
        return (root, cq, cb) -> cb.equal(root.get(Timeoff_.accepted.toString()), status);
    }

    public static Specification<Timeoff> requestedByNamePrefix(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Timeoff_.requestBy.getName())),
                prefix.toLowerCase() + "%");
    }

    public static Specification<Timeoff> acceptedByNamePrefix(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Timeoff_.acceptedBy.getName())),
                prefix.toLowerCase() + "%");
    }

    public static Specification<Timeoff> creationDateBetween(LocalDateTime from,
                                                             LocalDateTime until) {
        return (root, cq, cb) -> {
            if (from != null && until != null) {
                return cb.between(root.get(Timeoff_.REQUEST_DATE), from, until);
            } else if (from != null) {
                return cb.greaterThanOrEqualTo(root.get(Timeoff_.REQUEST_DATE), from);
            } else if (until != null) {
                return cb.lessThanOrEqualTo(root.get(Timeoff_.REQUEST_DATE), until);
            }
            return null;
        };
    }

    public static Specification<Timeoff> timeoffInterval(LocalDate filterFrom,
                                                         LocalDate filterUntil) {
        return (root, cq, cb) -> cb.and(
                cb.lessThanOrEqualTo(root.get(Timeoff_.START_DATE), filterUntil),
                cb.greaterThanOrEqualTo(root.get(Timeoff_.END_DATE), filterFrom));
    }
}
