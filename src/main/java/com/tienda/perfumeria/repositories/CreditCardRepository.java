package com.tienda.perfumeria.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tienda.perfumeria.entities.CreditCard;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, Integer> {

    Optional<CreditCard> findByCardNumber(String cardNumber);

    List<CreditCard> findByCardHolderName(String cardHolderName);
}
