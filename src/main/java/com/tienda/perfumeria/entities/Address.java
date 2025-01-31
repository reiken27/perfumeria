package com.tienda.perfumeria.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "street_number", nullable = false)
    private String streetNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Override
    public String toString() {
        return "Address{"
                + "id=" + id
                + ", street_address='" + streetAddress + '\''
                + ", street_number='" + streetNumber + '\''
                + ", city='" + city + '\''
                + ", zipCode='" + zipCode + '\''
                + '}';
    }
}
