package com.residencia.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.residencia.ecommerce.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

}
