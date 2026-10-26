package com.mindex.challenge.dao;

import com.mindex.challenge.data.Employee;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB persistence operations for employee documents.
 */
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    /**
     * Finds an employee by the employeeId field.
     *
     * @param employeeId ID to search for
     * @return the matching employee, or {@code null} when no employee matches
     */
    Employee findByEmployeeId(String employeeId);

    /**
     * Finds employees whose employeeId is in the supplied collection.
     *
     * @param employeeIds IDs to search for
     * @return matching employees
     */
    List<Employee> findByEmployeeIdIn(Collection<String> employeeIds);
}
