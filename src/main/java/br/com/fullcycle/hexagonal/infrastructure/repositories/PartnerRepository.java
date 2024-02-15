package br.com.fullcycle.hexagonal.infrastructure.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.fullcycle.hexagonal.infrastructure.models.Partner;

import java.util.Optional;

public interface PartnerRepository extends CrudRepository<Partner, Long> {

    Optional<Partner> findByCnpj(String cnpj);

    Optional<Partner> findByEmail(String email);
}