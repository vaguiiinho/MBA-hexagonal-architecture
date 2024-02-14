package br.com.fullcycle.hexagonal.application.usecases;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.fullcycle.hexagonal.models.Partner;
import br.com.fullcycle.hexagonal.services.PartnerService;

public class GetPartnerByIdUseCaseTest {

    @Test
    @DisplayName("Deve obter um parceiro por id")
    public void testGetPartnerById() {

        // given
        final var expectId = UUID.randomUUID().getMostSignificantBits();
        final var expectCNPJ = "41536538000100";
        final var expectEmail = "john.doe@gmail.com";
        final var expectName = "John Doe";

        final var aPartner = new Partner();
        aPartner.setId(expectId);
        aPartner.setCnpj(expectCNPJ);
        aPartner.setEmail(expectEmail);
        aPartner.setName(expectName);

        final var input = new GetPartnerByIdUseCase.Input(expectId);

        // when
        final var partnerService = Mockito.mock(PartnerService.class);
        when(partnerService.findById(expectId)).thenReturn(Optional.of(aPartner));

        final var useCase = new GetPartnerByIdUseCase(partnerService);
        final var output = useCase.execute(input).get();

        // then
        Assertions.assertEquals(expectId, output.id());
        Assertions.assertEquals(expectCNPJ, output.cnpj());
        Assertions.assertEquals(expectEmail, output.email());
        Assertions.assertEquals(expectName, output.name());

    }

    @Test
    @DisplayName("Deve obter vazio ao tentar recuperar  um parceiro  não existe por id")
    public void testGetPartnerByIdWithInvalidId() {

        // given
        final var expectId = UUID.randomUUID().getMostSignificantBits();

        final var input = new GetPartnerByIdUseCase.Input(expectId);

        // when
        final var customerService = Mockito.mock(PartnerService.class);
        when(customerService.findById(expectId)).thenReturn(Optional.empty());

        final var useCase = new GetPartnerByIdUseCase(customerService);
        final var output = useCase.execute(input);

        // then
        Assertions.assertTrue(output.isEmpty());

    }
}
