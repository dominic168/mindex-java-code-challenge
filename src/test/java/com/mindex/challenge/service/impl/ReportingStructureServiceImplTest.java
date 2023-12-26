package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
@RunWith(SpringRunner.class)
public class ReportingStructureServiceImplTest {

    private Employee employee;

    private Employee directReport1;

    private Employee directReport2;

    private Employee directReport3;

    @InjectMocks
    private ReportingStructureService reportingStructureService = new ReportingStructureServiceImpl();

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        directReport1 = new Employee();
        directReport1.setEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f");
        directReport1.setFirstName("John");
        directReport1.setLastName("Doe");
        directReport1.setDepartment("Engineering");
        directReport1.setPosition("Developer II");

        directReport2 = new Employee();
        directReport2.setEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6fwfwafwer23");
        directReport2.setFirstName("James");
        directReport2.setLastName("Doe");
        directReport2.setDepartment("Engineering");
        directReport2.setPosition("Developer III");

        directReport3 = new Employee();
        directReport3.setEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6qdqddw");
        directReport3.setFirstName("Sue");
        directReport3.setLastName("Doe");
        directReport3.setDepartment("Engineering");
        directReport3.setPosition("Developer I");

        List<Employee> directReports = new ArrayList<>();
        directReports.add(directReport1);
        directReports.add(directReport2);
        directReports.add(directReport3);

        employee = new Employee();
        employee.setEmployeeId("f07f4599-022e-415a-a5f2-33e8a0606e93");
        employee.setPosition("Engineering Manager");
        employee.setFirstName("Steven");
        employee.setLastName("Tyler");
        employee.setDepartment("Engineering");
        employee.setDirectReports(directReports);
    }

    @Test
    public void getEmployeeReportingStructure_whenValidEmployeeId_ReportingStructureObjectIsReturned() {
        given(employeeRepository.findByEmployeeId(employee.getEmployeeId())).willReturn(employee);
        given(employeeRepository.findByEmployeeId(directReport1.getEmployeeId())).willReturn(directReport1);
        given(employeeRepository.findByEmployeeId(directReport2.getEmployeeId())).willReturn(directReport2);
        given(employeeRepository.findByEmployeeId(directReport3.getEmployeeId())).willReturn(directReport3);

        ReportingStructure reportingStructure = reportingStructureService.getEmployeeReportingStructure(employee.getEmployeeId());
        assertNotNull(reportingStructure);
    }

    @Test
    public void getEmployeeReportingStructure_whenInValidEmployeeId_exceptionIsThrow() {
        String employeeId = "1234565789897hr464rfe3r";

        given(employeeRepository.findByEmployeeId(employeeId)).willReturn(null);

        assertThrows(RuntimeException.class, () -> {
            reportingStructureService.getEmployeeReportingStructure(employeeId);
        });
    }
}
