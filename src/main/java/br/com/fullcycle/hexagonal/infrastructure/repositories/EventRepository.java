package br.com.fullcycle.hexagonal.infrastructure.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.fullcycle.hexagonal.infrastructure.models.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

}
