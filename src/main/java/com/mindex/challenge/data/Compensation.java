package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Compensation {
    private Employee employee;

    @JsonFormat(pattern="MM/dd/yyyy")
    private Date effectiveDate;

    private double salary;

    public Compensation() {}

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
