package com.phone.PhoneService;

import com.phone.Interfaces.NumberChecker;
import com.phone.Interfaces.PhoneCallRepository;
import com.phone.dto.PhoneCallDTO;
import com.phone.phone.PhoneCall;
import com.phone.phone.PhoneStatistics;
import lombok.AllArgsConstructor;


import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class PhoneServise implements NumberChecker {

    private static final Logger logger = LoggerFactory.getLogger(PhoneServise.class);

    public final String CONTACTS_FILE_PATH = "C:\\Users\\David\\Desktop\\phonecall\\test\\temp\\contactList.csv";
    public final String BLACKLIST_FILE_PATH = "C:\\Users\\David\\Desktop\\phonecall\\test\\temp\\blackList.csv";
    private Set<String> contacts;
    private Set<String> blacklist;
    @Override
    public boolean isInContacts(String phoneNumber) {
        return contacts.contains(phoneNumber);
    }

    @Override
    public boolean isInBlacklist(String phoneNumber) {
        return blacklist.contains(phoneNumber);
    }
    
    public void PhoneNumberChecker() {
        this.contacts = loadSetContacsFromFile();
        this.blacklist = loadSetBlacklistFromFile();
    }

    private Set<String> loadSetContacsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONTACTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length>=2) {
                    contacts.add(parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }
    private Set<String> loadSetBlacklistFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BLACKLIST_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                blacklist.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blacklist;
    }


    private PhoneCallRepository phoneCallRepository;

    public PhoneCall create (PhoneCallDTO dto){
        PhoneNumberChecker();
        if (isInBlacklist(dto.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number is in the blacklist");
        }
        boolean isSavedContact = isInContacts(dto.getPhoneNumber());
        PhoneCall phoneCall = PhoneCall.builder()
                .time(dto.getTime())
                .callType(dto.getCallType())
                .duration(dto.getDuration())
                .phoneNumber(dto.getPhoneNumber())
                .savedContact(isSavedContact)
                .build();
        return phoneCallRepository.save(phoneCall);

    }

    public List<PhoneCall> readAll(){
        return phoneCallRepository.findAll();
    }

    public PhoneStatistics getPhoneStatistics(String phoneNumber) {
        List<PhoneCall> phoneCalls = phoneCallRepository.findByPhoneNumber(phoneNumber);

        int callCount = phoneCalls.size();
        long totalDuration = phoneCalls.stream()
                .mapToLong(PhoneCall::getDuration)
                .sum();

        return new PhoneStatistics(callCount, totalDuration);
    }



    public List<PhoneCall> getPhoneNumbersByDuration(long duration) {
        return phoneCallRepository.findAll().stream()
                .filter(phoneCall -> phoneCall.getDuration() >= duration)
                .collect(Collectors.toList());
    }


    public void updatePhoneNumber(String oldPhoneNumber, String newPhoneNumber) {
        String phonePattern = "\\d{10}";
        if(oldPhoneNumber.matches(phonePattern) & newPhoneNumber.matches(phonePattern))
            phoneCallRepository.updatePhoneNumber(oldPhoneNumber, newPhoneNumber);
        else{
            throw new IllegalArgumentException("Phone number is not correct");
        }
    }
}


