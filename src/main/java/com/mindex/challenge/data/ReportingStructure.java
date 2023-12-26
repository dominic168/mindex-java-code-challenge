package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {
    private List<Employee> directReportEmployees;
    private int numberOfDirectReports;

    public List<Employee> getDirectReportEmployees() {
        return this.directReportEmployees;
    }

    public void setDirectReportEmployee(List<Employee> directReportEmployees) {
        this.directReportEmployees = directReportEmployees;
    }

    public int getNumberOfDirectReports() {
        return numberOfDirectReports;
    }

    public void setNumberOfDirectReports(int numberOfDirectReports) {
        this.numberOfDirectReports = numberOfDirectReports;
    }
}
