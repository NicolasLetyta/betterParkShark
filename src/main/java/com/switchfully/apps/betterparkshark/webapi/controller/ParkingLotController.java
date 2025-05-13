package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.service.AuthenticationService;
import com.switchfully.apps.betterparkshark.service.ParkingLotService;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoOutputList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="parking_lots")
public class ParkingLotController {
    private static final Logger logger = LoggerFactory.getLogger(ParkingLotController.class);

    private final ParkingLotService parkingLotService;
    private final AuthenticationService authenticationService;

    @Autowired
    public ParkingLotController(ParkingLotService parkingLotService, AuthenticationService authenticationService) {
        this.parkingLotService = parkingLotService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingLotDtoOutput> getAllParkingLots(@RequestHeader(value = "Authorization") String authToken) {
        logger.info("Inside getAllParkingLots");
        authenticationService.authenticateAdmin(authToken);
        return parkingLotService.findAllParkingLots();

    }

    @GetMapping(path = "/{id}",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ParkingLotDtoOutputList getParkingLotById(@PathVariable Long id,
                                                     @RequestHeader(value = "Authorization") String authToken) {
        logger.info("Inside getParkingLotById");
        authenticationService.authenticateAdmin(authToken);
        return parkingLotService.findParkingLotById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingLotDtoOutputList createParkingLot(@RequestBody ParkingLotDtoInput parkingLotDtoInput,
                                                    @RequestHeader(value = "Authorization") String authToken) {
        logger.info("Inside createParkingLot");
        authenticationService.authenticateAdmin(authToken);
        return parkingLotService.createNewParkingLot(parkingLotDtoInput);
    }
}
