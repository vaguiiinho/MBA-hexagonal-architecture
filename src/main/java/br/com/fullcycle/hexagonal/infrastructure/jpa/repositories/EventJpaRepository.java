package br.com.fullcycle.hexagonal.infrastructure.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.fullcycle.hexagonal.infrastructure.jpa.entities.EventEntity;

public interface EventJpaRepository extends CrudRepository<EventEntity, Long> {

}
