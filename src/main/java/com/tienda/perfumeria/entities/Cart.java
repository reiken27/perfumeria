package com.tienda.perfumeria.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = true)
    private LocalDate orderDate;

    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
    private List<CartProduct> products;

    @Column(nullable = false)
    private Address address;

}
