package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ReportingStructureController {
    @Autowired
    ReportingStructureService reportingStructureService;

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @GetMapping("/reportingStructure/{employeeId}")
    public ResponseEntity<ReportingStructure> getEmployeeReportingStructure(@PathVariable String employeeId) throws ResponseStatusException {
        LOG.debug("Request received to get employee reporting structure with id [{}]", employeeId);

        try {
            // invoke reporting structure service and return reporting structure object back to client
            return ResponseEntity.ok(reportingStructureService.getEmployeeReportingStructure(employeeId));
        }
        catch (Exception ex) {
            // throw not found exception if no employee was found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}