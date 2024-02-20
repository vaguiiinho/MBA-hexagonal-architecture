package br.com.fullcycle.hexagonal.infrastructure.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.fullcycle.hexagonal.infrastructure.jpa.entities.PartnerEntity;

public interface PartnerJpaRepository extends CrudRepository<PartnerEntity, UUID> {

    Optional<PartnerEntity> findByCnpj(String cnpj);

    Optional<PartnerEntity> findByEmail(String email);
}