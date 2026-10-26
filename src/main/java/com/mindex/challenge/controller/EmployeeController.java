package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for employee operations and reporting-structure lookups.
 */
@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * Creates an employee through the employee service.
     *
     * @param employee employee details to create
     * @return the created employee, including its assigned employee ID
     */
    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    /**
     * Retrieves an employee by employee ID.
     *
     * @param id ID of the employee to retrieve
     * @return the matching employee
     */
    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    /**
     * Updates the employee identified by the URL using the supplied employee details.
     *
     * @param id ID of the employee to update
     * @param employee updated employee details
     * @return the saved employee
     */
    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
    
    /**
     * Computes the reporting structure for the specified employee.
     *
     * @param id ID of the employee whose reporting structure is requested
     * @return the employee and the number of distinct direct and indirect reports
     */
    @GetMapping("/reportingStructure/{id}")
    public ReportingStructure readReportingStructure(@PathVariable String id) {
        LOG.debug("Received reporting structure request for employee id [{}]", id);

        return employeeService.readReportingStructure(id);
    }
}
