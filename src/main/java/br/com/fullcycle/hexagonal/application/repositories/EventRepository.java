package br.com.fullcycle.hexagonal.application.repositories;

import java.util.Optional;

import br.com.fullcycle.hexagonal.application.domain.Event;
import br.com.fullcycle.hexagonal.application.domain.EventId;

public interface EventRepository {

    Optional<Event> eventOfId(EventId anId);

    Event create(Event event);

    Event update(Event event);

    void deleteAll();
}