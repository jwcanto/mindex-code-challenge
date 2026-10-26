package com.mindex.challenge.data;

import java.util.List;

/**
 * Employee profile and its direct-report references.
 */
public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private List<Employee> directReports;

    /**
     * Creates an empty employee for data binding.
     */
    public Employee() {
    }

    /**
     * Returns the employee's unique identifier.
     *
     * @return employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee's unique identifier. No value is returned.
     *
     * @param employeeId employee ID to assign
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Returns the employee's first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the employee's first name. No value is returned.
     *
     * @param firstName first name to assign
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the employee's last name.
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the employee's last name. No value is returned.
     *
     * @param lastName last name to assign
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the employee's position.
     *
     * @return position title
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the employee's position. No value is returned.
     *
     * @param position position title to assign
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Returns the employee's department.
     *
     * @return department name
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the employee's department. No value is returned.
     *
     * @param department department name to assign
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Returns references to the employee's direct reports.
     *
     * @return direct reports, or {@code null} when none are set
     */
    public List<Employee> getDirectReports() {
        return directReports;
    }

    /**
     * Sets references to the employee's direct reports. No value is returned.
     *
     * @param directReports direct reports to assign
     */
    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }
}
