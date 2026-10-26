package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.RoundingMode;
import java.util.List;

/**
 * Implements compensation validation, employee association, and persistence operations.
 */
@Service
public class CompensationServiceImpl implements CompensationService {
    private final CompensationRepository compensationRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * Creates a compensation service with the repositories it needs.
     *
     * @param compensationRepository repository used to persist and query compensation records
     * @param employeeRepository repository used to verify employees
     */
    public CompensationServiceImpl(CompensationRepository compensationRepository,
                                   EmployeeRepository employeeRepository) {
        this.compensationRepository = compensationRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves an employee's compensation history in descending effective-date order.
     *
     * @param employeeId ID of the employee whose compensation history is requested
     * @return matching compensation records, newest effective date first
     * @throws ResponseStatusException with 404 status if the employee does not exist
     */
    @Override
    public List<Compensation> read(String employeeId) {
        if (employeeRepository.findByEmployeeId(employeeId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid employeeId: " + employeeId);
        }
        return compensationRepository.findByEmployeeIdOrderByEffectiveDateDesc(employeeId);
    }

    /**
     * Validates and persists a compensation record for an existing employee.
     *
     * @param employeeId ID of the employee to associate with the compensation
     * @param compensation compensation details to persist
     * @return the inserted compensation record
     * @throws ResponseStatusException with 404 status if the employee does not exist,
     *         or 400 status if required fields are missing or salary needs rounding
     */
    @Override
    public Compensation create(String employeeId, Compensation compensation) {
        if (employeeRepository.findByEmployeeId(employeeId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid employeeId: " + employeeId);
        }
        if (compensation.getSalary() == null || compensation.getEffectiveDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "salary and effectiveDate are required");
        }

        try {
            compensation.setSalary(compensation.getSalary().setScale(2, RoundingMode.UNNECESSARY));
        } catch (ArithmeticException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "salary must have no more than 2 decimal places", exception);
        }

        compensation.setId(null);
        compensation.setEmployeeId(employeeId);
        return compensationRepository.insert(compensation);
    }
}
