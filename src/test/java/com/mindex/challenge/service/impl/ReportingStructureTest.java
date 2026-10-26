package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

/**
 * Unit tests for distinct descendant counting in reporting structures.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReportingStructureTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    /**
     * Verifies distinct descendants are counted once and cycles do not inflate the count.
     * No value is returned; assertion failures indicate an incorrect report count.
     */
    @Test
    public void countsDistinctDescendantsWithoutCountingEmployeeOrCycles() {
        Employee root = employee("root");
        Employee first = employee("first");
        Employee second = employee("second");
        Employee third = employee("third");
        Employee fourth = employee("fourth");

        root.setDirectReports(Arrays.asList(reference("first"), reference("second")));
        first.setDirectReports(Arrays.asList(reference("second"), reference("third")));
        second.setDirectReports(Arrays.asList(reference("root"), reference("fourth")));
        third.setDirectReports(Collections.singletonList(reference("fourth")));

        when(employeeRepository.findByEmployeeId("root")).thenReturn(root);
        when(employeeRepository.findByEmployeeIdIn(Arrays.asList("first", "second")))
                .thenReturn(Arrays.asList(first, second));
        when(employeeRepository.findByEmployeeIdIn(Arrays.asList("third", "fourth")))
                .thenReturn(Arrays.asList(third, fourth));

        ReportingStructure result = employeeService.readReportingStructure("root");

        assertSame(root, result.getEmployee());
        assertEquals(4, result.getNumberOfReports());
    }

    /**
     * Verifies an employee without direct reports has a zero report count.
     * No value is returned; assertion failures indicate an incorrect report count.
     */
    @Test
    public void reportsZeroForEmployeeWithoutDirectReports() {
        Employee employee = employee("solo");
        when(employeeRepository.findByEmployeeId("solo")).thenReturn(employee);

        ReportingStructure result = employeeService.readReportingStructure("solo");

        assertSame(employee, result.getEmployee());
        assertEquals(0, result.getNumberOfReports());
    }

    /**
     * Creates an employee with the supplied ID for use in the test graph.
     *
     * @param id employee ID to assign
     * @return employee with the supplied ID
     */
    private static Employee employee(String id) {
        Employee employee = new Employee();
        employee.setEmployeeId(id);
        return employee;
    }

    /**
     * Creates a minimal employee reference for a direct-report relationship.
     *
     * @param id ID of the referenced employee
     * @return employee reference with the supplied ID
     */
    private static Employee reference(String id) {
        return employee(id);
    }
}
