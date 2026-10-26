package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

import java.util.List;

/**
 * Operations for creating compensation records and retrieving employee compensation history.
 */
public interface CompensationService {
    /**
     * Retrieves an employee's compensation history, newest effective date first.
     *
     * @param employeeId employee ID whose records are requested
     * @return the employee's compensation records
     */
    List<Compensation> read(String employeeId);

    /**
     * Associates and persists a compensation record for an employee.
     *
     * @param employeeId employee ID for the compensation record
     * @param compensation compensation details to save
     * @return the persisted compensation record
     */
    Compensation create(String employeeId, Compensation compensation);
}
