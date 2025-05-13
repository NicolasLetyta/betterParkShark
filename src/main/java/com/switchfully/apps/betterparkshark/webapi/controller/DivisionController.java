package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.service.AuthenticationService;
import com.switchfully.apps.betterparkshark.service.DivisionService;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.SubDivisionDtoInput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/divisions")
public class DivisionController {

    // FIELDS
    private final DivisionService divisionService;
    private final AuthenticationService authenticationService;

    // CONSTRUCTORS
    public DivisionController(DivisionService divisionService, AuthenticationService authenticationService) {
        this.divisionService = divisionService;
        this.authenticationService = authenticationService;
    }

    // ENDPOINTS
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public DivisionDtoOutput createDivision(@RequestBody DivisionDtoInput divisionDtoInput,
                                            @RequestHeader(value = "Authorization") String authToken) {
        authenticationService.authenticateAdmin(authToken);
        return divisionService.createDivision(divisionDtoInput);
    }

    @PostMapping(path = "/subdivision", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public DivisionDtoOutput createSubDivision(@RequestBody SubDivisionDtoInput subdivisionDtoInput,
                                               @RequestHeader(value = "Authorization") String authToken) {
        authenticationService.authenticateAdmin(authToken);
         return divisionService.createSubDivision(subdivisionDtoInput);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public DivisionDtoOutput getDivision(@PathVariable Long id,
                                         @RequestHeader(value = "Authorization") String authToken) {
        authenticationService.authenticateAdmin(authToken);
        return divisionService.getDivision(id);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<DivisionDtoOutput> getAllDivisions(@RequestHeader(value = "Authorization") String authToken) {
        authenticationService.authenticateAdmin(authToken);
        return divisionService.getAllDivisions();
    }
}