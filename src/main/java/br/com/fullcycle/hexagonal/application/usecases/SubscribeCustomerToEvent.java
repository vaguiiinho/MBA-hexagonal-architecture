package br.com.fullcycle.hexagonal.application.usecases;

import java.time.Instant;
import java.util.Objects;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.models.Ticket;
import br.com.fullcycle.hexagonal.models.TicketStatus;
import br.com.fullcycle.hexagonal.services.CustomerService;
import br.com.fullcycle.hexagonal.services.EventService;

public class SubscribeCustomerToEvent
        extends UseCase<SubscribeCustomerToEvent.Input, SubscribeCustomerToEvent.Output> {

    private final CustomerService customerService;
    private final EventService eventService;

    public SubscribeCustomerToEvent(final CustomerService customerService, final EventService eventService) {
        this.customerService = Objects.requireNonNull(customerService);
        this.eventService = Objects.requireNonNull(eventService);
    }

    @Override
    public Output execute(final Input input) {
        var customer = customerService.findById(input.customerId)
                .orElseThrow(() -> new ValidationException("Customer not found"));

        var event = eventService.findById(input.eventId)
                .orElseThrow(() -> new ValidationException("Event not found"));

        eventService.findTicketByEventIdAndCustomerId(input.eventId, input.customerId)
                .ifPresent(t -> {
                    throw new ValidationException("Email already registered");
                });

        if (event.getTotalSpots() < event.getTickets().size() + 1) {
            throw new ValidationException("Event sold out");
        }

        var ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setCustomer(customer);
        ticket.setReservedAt(Instant.now());
        ticket.setStatus(TicketStatus.PENDING);

        event.getTickets().add(ticket);

        eventService.save(event);

        return new Output(event.getId(), ticket.getStatus().name(), ticket.getReservedAt());
    }

    public record Input(Long customerId, Long eventId) {
    }

    public record Output(Long eventId, String ticketStatus, Instant reservationDate) {
    }
}
