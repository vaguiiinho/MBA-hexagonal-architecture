package br.com.fullcycle.hexagonal.application.usecases;

import java.util.Objects;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.application.domain.Event;
import br.com.fullcycle.hexagonal.application.domain.PartnerId;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.EventRepository;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;

public class CreateEventUseCase extends UseCase<CreateEventUseCase.Input, CreateEventUseCase.Output> {

    private final PartnerRepository partnerRepository;
    private final EventRepository eventRepository;

    public CreateEventUseCase(final PartnerRepository partnerRepository, final EventRepository eventRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
        this.eventRepository = Objects.requireNonNull(eventRepository);
    }

    @Override
    public Output execute(final Input input) {

        final var aPartner = partnerRepository.partnerOfId(PartnerId.with(input.partnerId))
                .orElseThrow(() -> new ValidationException("Partner not found"));

        final var aEvent = eventRepository.create(Event.newEvent(input.name, input.date, input.totalSpots, aPartner));

        return new Output(
                aEvent.eventId().value(),
                input.date,
                aEvent.name().value(),
                aEvent.totalSpots(),
                aEvent.partnerId().value());
    }

    public record Input(String date, String name, String partnerId, Integer totalSpots) {
    }

    public record Output(String id, String date, String name, int totalSpots, String partnerId) {
    }
}