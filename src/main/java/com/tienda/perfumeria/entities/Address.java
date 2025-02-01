package com.tienda.perfumeria.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Table(name = "address")
@Entity
public class Address implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
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
