package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Implements employee persistence and on-demand reporting-structure calculations.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Persists a new employee after assigning a generated employee ID.
     *
     * @param employee employee details to create
     * @return the created employee with its generated ID
     */
    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    /**
     * Looks up an employee by employee ID.
     *
     * @param id ID of the employee to retrieve
     * @return the matching employee
     * @throws RuntimeException if no employee has the supplied ID
     */
    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    /**
     * Saves changes to an employee.
     *
     * @param employee employee data to save
     * @return the saved employee
     */
    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    /**
     * Counts distinct direct and indirect reports for an employee.
     *
     * @param id ID of the employee whose reports are counted
     * @return the employee and the number of distinct descendants in the reporting structure
     */
    @Override
    public ReportingStructure readReportingStructure(String id) {
        Employee employee = read(id);
        Set<String> visitedEmployeeIds = new HashSet<>();
        visitedEmployeeIds.add(employee.getEmployeeId());

        List<String> reportIds = getDirectReportIds(employee);
        int numberOfReports = 0;

        while (!reportIds.isEmpty()) {
            List<String> nextReportIds = new ArrayList<>();
            for (String reportId : reportIds) {
                if (reportId != null && visitedEmployeeIds.add(reportId)) {
                    numberOfReports++;
                    nextReportIds.add(reportId);
                }
            }

            if (nextReportIds.isEmpty()) {
                break;
            }

            List<String> followingReportIds = new ArrayList<>();
            for (Employee report : employeeRepository.findByEmployeeIdIn(nextReportIds)) {
                followingReportIds.addAll(getDirectReportIds(report));
            }
            reportIds = followingReportIds;
        }

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(numberOfReports);
        return reportingStructure;
    }

    /**
     * Extracts the employee IDs from the employee's direct report references.
     *
     * @param employee employee whose direct reports are inspected
     * @return IDs of direct reports with non-null employee IDs
     */
    private List<String> getDirectReportIds(Employee employee) {
        List<String> reportIds = new ArrayList<>();
        if (employee.getDirectReports() != null) {
            for (Employee report : employee.getDirectReports()) {
                if (report != null && report.getEmployeeId() != null) {
                    reportIds.add(report.getEmployeeId());
                }
            }
        }
        return reportIds;
    }
}
