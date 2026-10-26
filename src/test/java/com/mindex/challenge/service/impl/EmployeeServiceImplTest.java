package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for employee, reporting-structure, and compensation REST endpoints.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Builds endpoint URLs using the random port assigned to the test server.
     * No value is returned; the URL fields are initialized for the tests.
     */
    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
    }

    /**
     * Exercises employee creation, retrieval, and update through the REST API.
     * No value is returned; assertion failures indicate an endpoint behavior problem.
     */
    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);


        // Read checks
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);


        // Update checks
        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Employee>(readEmployee, headers),
                        Employee.class,
                        readEmployee.getEmployeeId()).getBody();

        assertEmployeeEquivalence(readEmployee, updatedEmployee);
    }

    /**
     * Verifies the reporting structure endpoint returns the sample employee's report count.
     * No value is returned; assertion failures indicate an incorrect response.
     */
    @Test
    public void testReadReportingStructure() {
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        ReportingStructure reportingStructure =
                restTemplate.getForEntity("/reportingStructure/{id}", ReportingStructure.class, employeeId).getBody();

        assertNotNull(reportingStructure);
        assertEquals(employeeId, reportingStructure.getEmployee().getEmployeeId());
        assertEquals(4, reportingStructure.getNumberOfReports());
    }

    /**
     * Verifies compensation creation, persistence, history ordering, and JSON formatting.
     * No value is returned; assertion failures indicate incorrect endpoint behavior.
     */
    @Test
    public void testCreateAndReadCompensation() {
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        Compensation firstCompensation = new Compensation();
        firstCompensation.setSalary(new BigDecimal("60000"));
        firstCompensation.setEffectiveDate(LocalDate.of(2024, 1, 15));

        Compensation createdCompensation = restTemplate.postForEntity(
                "/employee/{employeeId}/compensation",
                firstCompensation,
                Compensation.class,
                employeeId).getBody();

        assertNotNull(createdCompensation);
        assertNotNull(createdCompensation.getId());
        assertEquals(employeeId, createdCompensation.getEmployeeId());
        assertEquals(new BigDecimal("60000.00"), createdCompensation.getSalary());

        Compensation secondCompensation = new Compensation();
        secondCompensation.setSalary(new BigDecimal("62000.50"));
        secondCompensation.setEffectiveDate(LocalDate.of(2025, 2, 1));
        restTemplate.postForEntity(
                "/employee/{employeeId}/compensation",
                secondCompensation,
                Compensation.class,
                employeeId);

        Compensation[] compensations = restTemplate.getForObject(
                "/employee/{employeeId}/compensation",
                Compensation[].class,
                employeeId);

        assertEquals(4, compensations.length);
        assertEquals(LocalDate.of(2025, 2, 1), compensations[0].getEffectiveDate());
        assertEquals(new BigDecimal("62000.50"), compensations[0].getSalary());
        assertEquals(LocalDate.of(2024, 1, 15), compensations[1].getEffectiveDate());
        assertEquals(new BigDecimal("60000.00"), compensations[1].getSalary());

        String responseJson = restTemplate.getForObject(
                "/employee/{employeeId}/compensation",
                String.class,
                employeeId);
        org.junit.Assert.assertTrue(responseJson.contains("\"salary\":62000.50"));
        org.junit.Assert.assertTrue(responseJson.contains("\"salary\":60000.00"));
        org.junit.Assert.assertTrue(responseJson.contains("\"effectiveDate\":\"2025/02/01\""));
    }

    /**
     * Asserts that the employee profile fields relevant to these tests match.
     *
     * @param expected expected employee profile
     * @param actual actual employee profile
     */
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
