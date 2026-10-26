package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * An employee's compensation for a specific effective date.
 */
public class Compensation {
    @Id
    private String id;
    private String employeeId;
    private BigDecimal salary;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate effectiveDate;

    /**
     * Creates an empty compensation record for data binding.
     */
    public Compensation() {
    }

    /**
     * Returns the record's database identifier.
     *
     * @return compensation record ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the record's database identifier. No value is returned.
     *
     * @param id compensation record ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the ID of the employee associated with this record.
     *
     * @return employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the ID of the employee associated with this record. No value is returned.
     *
     * @param employeeId employee ID
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Returns the salary amount.
     *
     * @return salary as a decimal value
     */
    public BigDecimal getSalary() {
        return salary;
    }

    /**
     * Sets the salary amount. No value is returned.
     *
     * @param salary salary as a decimal value
     */
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    /**
     * Returns the date on which this compensation takes effect.
     *
     * @return effective date
     */
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the date on which this compensation takes effect. No value is returned.
     *
     * @param effectiveDate effective date
     */
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
