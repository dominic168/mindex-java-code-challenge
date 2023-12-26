package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class CompensationServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CompensationRepository compensationRepository;

    @InjectMocks
    private CompensationService compensationService = new CompensationServiceImpl();

    private Employee employee;

    private Compensation compensation;

    @Before
    public void setup() {
        employee = new Employee();
        employee.setEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDepartment("Engineering");
        employee.setPosition("Developer II");

        compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setSalary(75000.00);
        compensation.setEffectiveDate(new Date(12/4/2023));

    }

    @Test
    public void createCompensation_whenSavingNewCompensation_compensationObjectIsReturned() {
        given(employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId())).willReturn(employee);
        given(compensationRepository.findByEmployee_EmployeeId(compensation.getEmployee().getEmployeeId())).willReturn(null);

        Compensation employeeCompensation = compensationService.createCompensation(compensation);
        assertNotNull(employeeCompensation);
    }

    @Test
    public void createCompensation_whenSavingCompensationThatAlreadyExists_ExceptionIsThrown() {
        given(employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId())).willReturn(employee);
        given(compensationRepository.findByEmployee_EmployeeId(compensation.getEmployee().getEmployeeId())).willReturn(compensation);

        assertThrows(RuntimeException.class, () -> {
            compensationService.createCompensation(compensation);
        });
    }

    @Test
    public void createCompensation_whenSavingNewCompensationWithNoEmployee_ExceptionIsThrown() {
        given(employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId())).willReturn(null);

        assertThrows(RuntimeException.class, () -> {
            compensationService.createCompensation(compensation);
        });
    }

    @Test
    public void readCompensation_whenGettingSavedCompensation_compensationObjectIsReturned() {
        given(compensationRepository.findByEmployee_EmployeeId(compensation.getEmployee().getEmployeeId())).willReturn(compensation);

        Compensation employeeCompensation = compensationService.readCompensation(employee.getEmployeeId());
        assertNotNull(employeeCompensation);
    }

    @Test
    public void readCompensation_whenGettingCompensationThatDoesNotExist_exceptionIsThrown() {
        String employeeId = "12344556776tg5tu6";

        given(compensationRepository.findByEmployee_EmployeeId(employeeId)).willReturn(null);

        assertThrows(RuntimeException.class, () -> {
            compensationService.readCompensation(employeeId);
        });
    }
}
