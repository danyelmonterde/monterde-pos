package com.monterdev.monterdepos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_expiration")
public class ExpirationTag {

    @Column(name = "expiration_tag")
    @Id
    private String expirationTag;

    @Column(name = "date_of_expiration")
    private LocalDate dateOfExpiration;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_count")
    private int itemCount;
}
