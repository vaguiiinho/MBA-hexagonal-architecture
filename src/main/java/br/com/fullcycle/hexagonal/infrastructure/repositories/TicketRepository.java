package br.com.fullcycle.hexagonal.infrastructure.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.fullcycle.hexagonal.infrastructure.models.Ticket;

import java.util.Optional;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Optional<Ticket> findByEventIdAndCustomerId(Long id, Long customerId);
}
