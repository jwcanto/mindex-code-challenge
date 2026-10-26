package com.mindex.challenge.data;

/**
 * An employee and the computed count of their distinct direct and indirect reports.
 */
public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    /**
     * Creates an empty reporting structure for data binding.
     */
    public ReportingStructure() {
    }

    /**
     * Returns the employee represented by this reporting structure.
     *
     * @return employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee represented by this reporting structure. No value is returned.
     *
     * @param employee employee to include
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Returns the total number of distinct direct and indirect reports.
     *
     * @return report count
     */
    public int getNumberOfReports() {
        return numberOfReports;
    }

    /**
     * Sets the total number of distinct direct and indirect reports. No value is returned.
     *
     * @param numberOfReports computed report count
     */
    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
