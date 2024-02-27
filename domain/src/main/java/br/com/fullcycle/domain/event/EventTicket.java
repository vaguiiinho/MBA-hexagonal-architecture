package br.com.fullcycle.domain.event;

import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.ticket.TicketId;
import br.com.fullcycle.domain.exceptions.ValidationException;

public class EventTicket {

    private final EventTicketId eventTicketId;
    private final EventId eventId;
    private final CustomerId customerId;
    private TicketId ticketId;
    private int ordering;

    public EventTicket(
            final EventTicketId eventTicketId,
            final EventId eventId,
            final CustomerId customerId,
            final TicketId ticketId,
            final Integer ordering) {
        if (eventTicketId == null) {
            throw new ValidationException("Invalid ticketId for eventTicketId");
        }

        if (eventId == null) {
            throw new ValidationException("Invalid eventId for EventTicket");
        }

        if (customerId == null) {
            throw new ValidationException("Invalid customerId for EventTicket");
        }

        this.eventTicketId = eventTicketId;
        this.eventId = eventId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.setOrdering(ordering);
    }

    public static EventTicket newEventTicket(
            final EventId eventId,
            final CustomerId customerId,
            final Integer ordering) {
        return new EventTicket(EventTicketId.unique(), eventId, customerId, null, ordering);
    }

    public EventTicketId eventTicketId() {
        return eventTicketId;
    }

    public EventTicket associateTicket(final TicketId aTicketId) {
        this.ticketId = aTicketId;
        return this;
    }

    public TicketId ticketId() {
        return ticketId;
    }

    public EventId eventId() {
        return eventId;
    }

    public int ordering() {
        return ordering;
    }

    public CustomerId customerId() {
        return customerId;
    }

    private void setOrdering(final Integer ordering) {
        if (ordering == null) {
            throw new ValidationException("Invalid ordering for EventTicket");
        }
        this.ordering = ordering;
    }
}
