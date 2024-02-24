package br.com.fullcycle.infrastructure.graphql;

import java.util.Objects;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import br.com.fullcycle.application.partner.CreatePartnerUseCase;
import br.com.fullcycle.application.partner.GetPartnerByIdUseCase;
import br.com.fullcycle.infrastructure.dtos.NewPartnerDTO;

// Adapter
@Controller
public class PartnerResolver {
    final private CreatePartnerUseCase createPartnerUseCase;
    final private GetPartnerByIdUseCase getPartnerByIdUseCase;

    public PartnerResolver(
            final CreatePartnerUseCase createPartnerUseCase,
            final GetPartnerByIdUseCase getPartnerByIdUseCase) {
        this.createPartnerUseCase = Objects.requireNonNull(createPartnerUseCase);
        this.getPartnerByIdUseCase = Objects.requireNonNull(getPartnerByIdUseCase);
    }

    @MutationMapping
    public CreatePartnerUseCase.Output createPartner(@Argument NewPartnerDTO input) {
        return createPartnerUseCase
                .execute(new CreatePartnerUseCase.Input(input.cnpj(), input.email(), input.name()));
    }

    @QueryMapping
    public GetPartnerByIdUseCase.Output partnerOfId(@Argument String id) {
        return getPartnerByIdUseCase.execute(new GetPartnerByIdUseCase.Input(id)).orElse(null);
    }
}