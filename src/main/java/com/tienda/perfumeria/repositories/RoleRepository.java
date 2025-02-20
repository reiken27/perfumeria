package com.tienda.perfumeria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tienda.perfumeria.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
