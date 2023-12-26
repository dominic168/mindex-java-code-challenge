package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CompensationController {

    @Autowired
    CompensationService compensationService;

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @PostMapping("/compensation")
    public ResponseEntity<Compensation> createNewCompensation(@RequestBody Compensation compensation) throws ResponseStatusException {
        LOG.debug("Request received to create new employee compensation record for [{}]", compensation);

        try {
            // invoke compensation service to create new compensation record
            return new ResponseEntity<>(compensationService.createCompensation(compensation), HttpStatus.CREATED);
        }
        catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @GetMapping("/compensation/{employeeId}")
    public ResponseEntity<Compensation> getEmployeeCompensation(@PathVariable String employeeId) throws ResponseStatusException {
        LOG.debug("Request received to get employee compensation record for employee id: [{}]", employeeId);

        try {
            // invoke read service to query the database for the employee compensation record
            // with the provided employee id
            return ResponseEntity.ok(compensationService.readCompensation(employeeId));
        }
        catch(Exception ex) {
            // throw not found if no records exist for the provided employee id
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
