package br.com.fullcycle.infrastructure.jpa.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.fullcycle.infrastructure.jpa.entities.EventEntity;

public interface EventJpaRepository extends CrudRepository<EventEntity, UUID> {

}
