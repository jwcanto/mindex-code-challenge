package com.mindex.challenge;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for employee and compensation fixture loading.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    /**
     * Verifies that the known sample employee is loaded from the employee fixture.
     * No value is returned; assertion failures indicate fixture loading problems.
     */
    @Test
    public void test() {
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Lennon", employee.getLastName());
        assertEquals("Development Manager", employee.getPosition());
        assertEquals("Engineering", employee.getDepartment());
    }

    /**
     * Verifies compensation fixture coverage, formatting, dates, and sample salaries.
     * No value is returned; assertion failures indicate invalid fixture data.
     */
    @Test
    public void testCompensationRecordsSeededForEveryEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        assertNotNull(employees);

        for (Employee employee : employees) {
            List<Compensation> compensations =
                    compensationRepository.findByEmployeeIdOrderByEffectiveDateDesc(employee.getEmployeeId());

            assertEquals(2, compensations.size());
            assertEquals(employee.getEmployeeId(), compensations.get(0).getEmployeeId());
            assertEquals(2, compensations.get(0).getSalary().scale());
            assertEquals(2, compensations.get(1).getSalary().scale());
            assertEquals("2024-01-01", compensations.get(0).getEffectiveDate().toString());
            assertEquals("2023-01-01", compensations.get(1).getEffectiveDate().toString());
        }

        assertCompensationSalary("16a596ae-edd3-4847-99fe-c4518e82c86f", "135000.00", "140400.00");
        assertCompensationSalary("b7839309-3348-463b-a7e3-5de1c168beb3", "75000.00", "78000.00");
        assertCompensationSalary("03aa1462-ffa9-4978-901b-7c001562cf6f", "115000.00", "119600.00");
        assertCompensationSalary("62c1084e-6e34-4630-93fd-9153afb65309", "85000.00", "88400.00");
        assertCompensationSalary("c0c2293d-16bd-4603-8e08-638a9d18b22c", "95000.00", "98800.00");
    }

    /**
     * Asserts that an employee has the expected sample salaries for both fixture years.
     *
     * @param employeeId ID of the employee whose compensation is checked
     * @param salary2023 expected salary effective in 2023
     * @param salary2024 expected salary effective in 2024
     */
    private void assertCompensationSalary(String employeeId, String salary2023, String salary2024) {
        List<Compensation> compensations =
                compensationRepository.findByEmployeeIdOrderByEffectiveDateDesc(employeeId);
        assertEquals(new BigDecimal(salary2024), compensations.get(0).getSalary());
        assertEquals(new BigDecimal(salary2023), compensations.get(1).getSalary());
    }
}