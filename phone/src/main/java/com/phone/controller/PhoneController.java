package com.phone.controller;

import com.phone.PhoneService.PhoneServise;
import com.phone.dto.PhoneCallDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phone.phone.PhoneCall;
import com.phone.phone.PhoneStatistics;


import java.util.List;

@RestController
@RequestMapping("/com/phone")
@AllArgsConstructor
public class PhoneController{
    private static final Logger logger = LoggerFactory.getLogger(PhoneController.class);
    @Autowired
    private PhoneServise phoneServise;

    @PostMapping("/create")
    public ResponseEntity<PhoneCall> create(@RequestBody PhoneCallDTO dto){
        try {
            PhoneCall createdCall = phoneServise.create(dto);
            logger.info("Phone call created successfully");
            return new ResponseEntity<>(createdCall, HttpStatus.OK);
        }
        catch (IllegalStateException e){
            logger.error("Error creating phone call", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/statistics/phoneNumber/{phoneNumber}")
    public ResponseEntity<PhoneStatistics> getPhoneStatistics(@PathVariable String phoneNumber) {
        PhoneStatistics statistics = phoneServise.getPhoneStatistics(phoneNumber);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<PhoneCall>> readAll(){
        return new ResponseEntity<>(phoneServise.readAll(), HttpStatus.OK);
    }

    @GetMapping("/statistics/duration/{duration}")
    public ResponseEntity<List<PhoneCall>> getPhoneNumbersByDuration(@PathVariable long duration) {
        List<PhoneCall> phoneNumbers = phoneServise.getPhoneNumbersByDuration(duration);
        return new ResponseEntity<>(phoneNumbers, HttpStatus.OK);
    }

    @PutMapping("/update/oldNumber/{oldPhoneNumber}/newNumber/{newPhoneNumber}")
    public ResponseEntity<Void> updatePhoneNumber(@PathVariable String oldPhoneNumber, @PathVariable String newPhoneNumber){
        try{
            phoneServise.updatePhoneNumber(oldPhoneNumber, newPhoneNumber);
            logger.info("Phone number updated");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (IllegalStateException e){
            logger.error("Invalid arguments");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}