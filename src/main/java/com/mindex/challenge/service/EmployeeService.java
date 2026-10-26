package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

/**
 * Operations for creating, retrieving, updating, and reporting on employees.
 */
public interface EmployeeService {
    /**
     * Creates and persists an employee.
     *
     * @param employee employee details to create
     * @return the created employee with its assigned ID
     */
    Employee create(Employee employee);

    /**
     * Retrieves an employee by ID.
     *
     * @param id employee ID to look up
     * @return the matching employee
     */
    Employee read(String id);

    /**
     * Saves changes to an employee.
     *
     * @param employee employee data to save
     * @return the saved employee
     */
    Employee update(Employee employee);

    /**
     * Computes the count of distinct direct and indirect reports for an employee.
     *
     * @param id employee ID whose reporting structure is requested
     * @return the employee and its total distinct report count
     */
    ReportingStructure readReportingStructure(String id);
}
