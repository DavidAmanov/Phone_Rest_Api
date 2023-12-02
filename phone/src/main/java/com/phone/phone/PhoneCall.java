package com.phone.phone;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneCall {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Date time; //дата звонка
    private String callType; // тип звонка входящий / исходящий
    private long duration; // продолжительность в секундах
    private String phoneNumber; // номер телефона
    @Column(name = "saved_contact")
    private boolean savedContact;

}
