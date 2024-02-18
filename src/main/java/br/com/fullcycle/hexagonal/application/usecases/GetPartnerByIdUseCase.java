package br.com.fullcycle.hexagonal.application.usecases;

import java.util.Objects;
import java.util.Optional;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.application.domain.PartnerId;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;

public class GetPartnerByIdUseCase
        extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerRepository partnerRepository;

    public GetPartnerByIdUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return partnerRepository.partnerOfId(PartnerId.with(input.id))
                .map(p -> new Output(
                        p.partnerId().value(),
                        p.cnpj().value(),
                        p.email().value(),
                        p.name().value()));

    }

    public record Input(String id) {
    }

    public record Output(String id, String cnpj, String email, String name) {
    }
}
