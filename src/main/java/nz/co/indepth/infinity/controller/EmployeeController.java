package nz.co.indepth.infinity.controller;

import nz.co.indepth.infinity.po.EmployeePO;
import nz.co.indepth.infinity.serviceimpl.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping
    public ResponseEntity<EmployeePO> createMovie(@RequestBody EmployeePO employeePO) {
        EmployeePO createdEmployeePO = employeeService.createEmployee (employeePO);
        LOGGER.debug ("-------- created movie: " + createdEmployeePO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployeePO);
    }
}
