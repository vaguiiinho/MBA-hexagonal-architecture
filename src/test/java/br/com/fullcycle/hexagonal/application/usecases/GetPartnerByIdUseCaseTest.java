package br.com.fullcycle.hexagonal.application.usecases;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.fullcycle.hexagonal.application.InMemoryPartnerRepository;
import br.com.fullcycle.hexagonal.application.domain.Partner;

public class GetPartnerByIdUseCaseTest {

    @Test
    @DisplayName("Deve obter um parceiro por id")
    public void testGetPartnerById() {

        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var aPartner = Partner.newPartner(expectedName, expectedCNPJ, expectedEmail);
        final var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);

        final var expectedId = aPartner.partnerId().value().toString();

        final var input = new GetPartnerByIdUseCase.Input(expectedId);

        // when

        final var useCase = new GetPartnerByIdUseCase(partnerRepository);
        final var output = useCase.execute(input).get();

        // then
        Assertions.assertEquals(expectedId, output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());

    }

    @Test
    @DisplayName("Deve obter vazio ao tentar recuperar  um parceiro  não existe por id")
    public void testGetPartnerByIdWithInvalidId() {

        // given
        final var expectedId = UUID.randomUUID().toString();

        final var input = new GetPartnerByIdUseCase.Input(expectedId);

        // when
        final var partnerRepository = new InMemoryPartnerRepository();

        final var useCase = new GetPartnerByIdUseCase(partnerRepository);
        final var output = useCase.execute(input);

        // then
        Assertions.assertTrue(output.isEmpty());

    }
}
