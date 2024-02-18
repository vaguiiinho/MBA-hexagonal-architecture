package br.com.fullcycle.hexagonal.application.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

public class Event {
    private final EventId eventId;
    private Name name;
    private LocalDate date;
    private int totalSpots;
    private PartnerId partnerId;

    public Event(final EventId eventId, final String name, final String date, final Integer totalSpots,
            final PartnerId partnerId) {

        if (eventId == null) {
            throw new ValidationException("invalid eventId for event");
        }

        if (date == null) {
            throw new ValidationException("invalid date for event");
        }

        if (partnerId == null) {
            throw new ValidationException("invalid partnerId for event");
        }

        this.eventId = eventId;
        this.name = new Name(name);
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        this.totalSpots = totalSpots;
        this.partnerId = partnerId;
    }

    public static Event newEvent(final String name, final String date, final Integer totalSpots,
            final Partner partner) {
        return new Event(EventId.unique(), name, date, totalSpots, partner.partnerId());
    }

    public EventId eventId() {
        return eventId;
    }

    public Name name() {
        return name;
    }

    public LocalDate date() {
        return date;
    }

    public int totalSpots() {
        return totalSpots;
    }

    public PartnerId partnerId() {
        return partnerId;
    }
    
}
