package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST endpoints for creating compensation records and retrieving employee compensation history.
 */
@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    private final CompensationService compensationService;

    /**
     * Creates a controller that delegates compensation operations to the service.
     *
     * @param compensationService service used to create and retrieve compensation records
     */
    public CompensationController(CompensationService compensationService) {
        this.compensationService = compensationService;
    }

    /**
     * Retrieves an employee's compensation history, newest effective date first.
     *
     * @param employeeId ID of the employee whose compensation records are requested
     * @return the employee's compensation records
     */
    @GetMapping("/employee/{employeeId}/compensation")
    public List<Compensation> read(@PathVariable String employeeId) {
        LOG.debug("Received compensation read request for employee id [{}]", employeeId);
        return compensationService.read(employeeId);
    }

    /**
     * Creates a compensation record associated with the specified employee.
     *
     * @param employeeId ID of the employee receiving the compensation record
     * @param compensation compensation details to create
     * @return the persisted compensation record
     */
    @PostMapping("/employee/{employeeId}/compensation")
    public Compensation create(@PathVariable String employeeId, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for employee id [{}]", employeeId);
        return compensationService.create(employeeId, compensation);
    }
}
