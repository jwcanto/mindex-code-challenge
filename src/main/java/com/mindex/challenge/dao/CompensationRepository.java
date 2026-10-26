package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MongoDB persistence operations for compensation documents.
 */
@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    /**
     * Retrieves compensation records for an employee, ordered by effective date descending.
     *
     * @param employeeId employee ID whose compensation records are requested
     * @return matching compensation records, newest effective date first
     */
    List<Compensation> findByEmployeeIdOrderByEffectiveDateDesc(String employeeId);
}
