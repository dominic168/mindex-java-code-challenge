package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Override
    public ReportingStructure getEmployeeReportingStructure(String employeeId) {
        LOG.debug("Fetching employee reporting structure using id [{}]", employeeId);

        // declare local storage variables
        List<Employee> reportingStructureEmployeeList = new ArrayList<>();
        ReportingStructure reportingStructure = new ReportingStructure();
        int overallDirectReportsCounter = 0;

        // attempt to fetch employee data with provided employee id
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        // throw an error if no employee is found
        if (employee == null) {
            LOG.debug("No employee found with id [{}]", employeeId);
            throw new RuntimeException("No employee found with id " + employeeId);
        }

        // check if employee has any direct reports and if not return an empty array list
        if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            LOG.debug("No direct reports found for employee with id [{}]", employeeId);
            reportingStructure.setDirectReportEmployee(new ArrayList<>());
            reportingStructure.setNumberOfDirectReports(overallDirectReportsCounter);
        } else {
            // Employee has direct reports
            // loop over employee direct reports array and set the persisted data to the direct report employee data structure
            for (Employee directReport : employee.getDirectReports()) {
                // TODO: Possible optimization opportunity
                // TODO: Reduce I/O to database by persisting direct report employee data earlier when a new employee is created
                Employee directReportEmployee = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
                directReport.setDepartment(directReportEmployee.getDepartment());
                directReport.setPosition(directReportEmployee.getPosition());
                directReport.setFirstName(directReportEmployee.getFirstName());
                directReport.setLastName(directReportEmployee.getLastName());

                // increment counter when employee has direct report
                overallDirectReportsCounter++;

                // check if direct report employees have any direct report employees
                if (directReportEmployee.getDirectReports() == null || directReportEmployee.getDirectReports().isEmpty()) {
                    LOG.debug("Employee with id [{}] has no direct reports", directReportEmployee.getEmployeeId());
                    directReport.setDirectReports(new ArrayList<>());
                } else {
                    // direct report employee has direct report employees
                    // loop over direct reports array and set the persisted data to the nested direct report employee data structure
                    List<Employee> nestedDirectReportEmployeesList = new ArrayList<>();
                    for (Employee nestedDirectReportEmployee : directReportEmployee.getDirectReports()) {
                        Employee nestedEmployee = employeeRepository.findByEmployeeId(nestedDirectReportEmployee.getEmployeeId());
                        nestedDirectReportEmployee.setFirstName(nestedEmployee.getFirstName());
                        nestedDirectReportEmployee.setLastName(nestedEmployee.getLastName());
                        nestedDirectReportEmployee.setDepartment(nestedEmployee.getDepartment());
                        nestedDirectReportEmployee.setPosition(nestedEmployee.getPosition());

                        // increment counter when direct report employee has a direct report
                        overallDirectReportsCounter++;
                        nestedDirectReportEmployee.setDirectReports((nestedEmployee.getDirectReports() == null || nestedEmployee.getDirectReports().isEmpty())
                                ? new ArrayList<>() : nestedEmployee.getDirectReports());
                        nestedDirectReportEmployeesList.add(nestedDirectReportEmployee);
                    }
                    directReport.setDirectReports(nestedDirectReportEmployeesList);
                }
                reportingStructureEmployeeList.add(directReport);
            }

            reportingStructure.setNumberOfDirectReports(overallDirectReportsCounter);
            reportingStructure.setDirectReportEmployee(reportingStructureEmployeeList);
        }

        return reportingStructure;
    }
}
