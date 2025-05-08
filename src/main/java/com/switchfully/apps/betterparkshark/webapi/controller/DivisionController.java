package com.switchfully.apps.betterparkshark.webapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/divisions")
public class DivisionController {

   // FIELDS
   private final DivisionService divisionService;
   private final DivisionMapper divisionMapper;

   // CONSTRUCTORS
   public DivisionController(DivisionService divisionService, DivisionMapper divisionMapper) {
       this.divisionService = divisionService;
       this.divisionMapper = divisionMapper;
   }

   // ENDPOINTS
   @PostMapping(consumes = "application/json", produces = "application/json")
   @ResponseStatus(HttpStatus.CREATED)
   public DivisionDtoOutput createDivision(@RequestBody DivisionDtoInput divisionDtoInput) {
       return divisionService.createDivision(divisionDtoInput);
   }
}
