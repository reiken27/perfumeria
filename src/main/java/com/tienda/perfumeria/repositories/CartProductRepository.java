package com.tienda.perfumeria.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tienda.perfumeria.entities.CartProduct;
import com.tienda.perfumeria.entities.Product;

@Repository
public interface CartProductRepository extends CrudRepository<CartProduct, Integer> {

    List<CartProduct> findByProduct(Product product);

    List<CartProduct> findByPriceLessThan(double price);
}


