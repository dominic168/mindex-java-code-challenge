package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    private final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public Compensation createCompensation(Compensation employeeCompensation) {
        LOG.debug("Creating new employee compensation record with the follow details [{}]", employeeCompensation);

        // check if the employee id field is null and throw an error
        if (employeeCompensation.getEmployee().getEmployeeId() == null) {
            LOG.debug("No employee id provided for [{}]", employeeCompensation.getEmployee());
            throw new RuntimeException("No employee id provided for " + employeeCompensation.getEmployee());
        }

        // check if an employee record exists
        // only current employees with active records should receive compensation
        if (employeeRepository.findByEmployeeId(employeeCompensation.getEmployee().getEmployeeId()) == null) {
            LOG.debug("No employee record found with id: [{}]", employeeCompensation.getEmployee().getEmployeeId());
            throw new RuntimeException("No employee record found with id: " + employeeCompensation.getEmployee().getEmployeeId() + "." + " Only current employees can receive compensation.");
        }

        // check if an employee compensation record has already been created for the given employee id
        if (compensationRepository.findByEmployee_EmployeeId(employeeCompensation.getEmployee().getEmployeeId()) != null) {
            LOG.debug("Compensation record already created for employee with id: [{}]", employeeCompensation.getEmployee().getEmployeeId());
            throw new RuntimeException("Compensation record already created for employee with id: " + employeeCompensation.getEmployee().getEmployeeId());
        }

        // save employee info with corresponding compensation record
        compensationRepository.insert(employeeCompensation);

        return employeeCompensation;
    }

    @Override
    public Compensation readCompensation(String employeeId) {
        LOG.debug("Attempting to fetch compensation record with employee id [{}]", employeeId);

        Compensation compensationRecord = compensationRepository.findByEmployee_EmployeeId(employeeId);

        // no employee compensation record was found
        if (compensationRecord == null) {
            throw new RuntimeException("No employee compensation record was found for employee id: " + employeeId);
        }

        return compensationRecord;
    }
}
