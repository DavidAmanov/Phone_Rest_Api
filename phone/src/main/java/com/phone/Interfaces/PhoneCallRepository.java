package com.phone.Interfaces;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.phone.phone.PhoneCall;

import java.util.List;


@Repository
public interface PhoneCallRepository extends JpaRepository<PhoneCall, String> {

    List<PhoneCall> findByPhoneNumber(String phoneNumber);
    @Modifying
    @Transactional
    @Query("UPDATE PhoneCall p SET p.phoneNumber = :newPhoneNumber WHERE p.phoneNumber = :oldPhoneNumber")
    void updatePhoneNumber(String oldPhoneNumber, String newPhoneNumber);

}
