package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * Loads employee and compensation fixture data when the application starts.
 */
@Component
public class DataBootstrap {
    private static final String EMPLOYEE_DATASTORE_LOCATION = "/static/employee_database.json";
    private static final String COMPENSATION_DATASTORE_LOCATION = "/static/compensation_database.json";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Loads employee and compensation fixtures into the configured repositories.
     * No value is returned; initialization errors are raised if fixtures cannot be loaded.
     */
    @PostConstruct
    public void init() {
        Employee[] employees = readData(EMPLOYEE_DATASTORE_LOCATION, Employee[].class);
        for (Employee employee : employees) {
            employeeRepository.insert(employee);
        }

        Compensation[] compensations = readData(COMPENSATION_DATASTORE_LOCATION, Compensation[].class);
        for (Compensation compensation : compensations) {
            if (employeeRepository.findByEmployeeId(compensation.getEmployeeId()) == null) {
                throw new IllegalStateException(
                        "Compensation fixture references unknown employeeId: " + compensation.getEmployeeId());
            }
            compensationRepository.insert(compensation);
        }
    }

    /**
     * Reads and deserializes a classpath JSON fixture.
     *
     * @param location absolute classpath location of the fixture
     * @param type Java type to deserialize the fixture into
     * @param <T> result type
     * @return the deserialized fixture data
     * @throws IllegalStateException if the fixture is missing or cannot be read
     */
    private <T> T readData(String location, Class<T> type) {
        try (InputStream inputStream = getClass().getResourceAsStream(location)) {
            if (inputStream == null) {
                throw new IllegalStateException("Could not find bootstrap data: " + location);
            }
            return objectMapper.readValue(inputStream, type);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read bootstrap data: " + location, exception);
        }
    }
}
