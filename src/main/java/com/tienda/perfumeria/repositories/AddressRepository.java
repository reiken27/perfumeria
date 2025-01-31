package com.tienda.perfumeria.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tienda.perfumeria.entities.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {


    List<Address> findByCity(String city);

    List<Address> findByZipCode(String zipCode);

    List<Address> findByStreetNumber(String streetNumber);

    List<Address> findByStreetAddress(String streetAddress);
}
