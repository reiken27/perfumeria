package com.tienda.perfumeria.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tienda.perfumeria.entities.Cart;
import com.tienda.perfumeria.entities.OrderStatus;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {

    List<Cart> findByOrderStatus(OrderStatus orderStatus);

    List<Cart> findByOrderDate(LocalDate orderDate);
    
    List<Cart> findByTotalGreaterThan(double total);
}
