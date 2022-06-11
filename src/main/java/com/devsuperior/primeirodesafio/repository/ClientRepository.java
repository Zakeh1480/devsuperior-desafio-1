package com.devsuperior.primeirodesafio.repository;

import com.devsuperior.primeirodesafio.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
