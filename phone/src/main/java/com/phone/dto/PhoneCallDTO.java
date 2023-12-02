package com.phone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PhoneCallDTO {
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private Date time;
    private String callType;
    private long duration;
    private String phoneNumber;
    private boolean savedContact;


    public boolean isSavedContact() {
        return savedContact;
    }

    public void setSavedContact(boolean savedContact) {
        this.savedContact = savedContact;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        if("Incoming".equals(callType) || "Outgoing".equals(callType)){
            this.callType = callType;}
        else {
            throw new IllegalArgumentException("Invalid call type");
        }
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        if(duration <= 0){
            throw new IllegalArgumentException("Invalid duration");
        }
        else {
            this.duration = duration;
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        String phonePattern = "\\d{10}";
        if(phoneNumber.matches(phonePattern))
            this.phoneNumber = phoneNumber;
        else{
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
