package br.com.fullcycle.hexagonal.application.usecases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.fullcycle.hexagonal.application.InMemoryEventRepository;
import br.com.fullcycle.hexagonal.application.InMemoryPartnerRepository;
import br.com.fullcycle.hexagonal.application.domain.Partner;
import br.com.fullcycle.hexagonal.application.domain.PartnerId;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

public class CreateEventUseCaseTest {
    @Test
    @DisplayName("Deve criar um evento")
    public void testCreate() throws Exception {
        // given
        final var aPartner = Partner.newPartner("John doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 10;
        final var expectedPartnerId = aPartner.partnerId().value();

        final var createInput = new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId,
                expectedTotalSpots);
        // when

        final var partnerRepository = new InMemoryPartnerRepository();
        final var eventRepository = new InMemoryEventRepository();

        partnerRepository.create(aPartner);

        final var useCase = new CreateEventUseCase(partnerRepository, eventRepository);
        final var output = useCase.execute(createInput);

        // then

        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedDate, output.date());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedTotalSpots, output.totalSpots());
        Assertions.assertEquals(expectedPartnerId, output.partnerId());
    }

    @Test
    @DisplayName("Não deve  criar um evento quando o Partner não for encontrado")
    public void testCreate_whenPartnerDoesntExist_ShouldThrowError() throws Exception {
        // given
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 100;
        final var expectedPartnerId = PartnerId.unique().value();
        final var expectedError = "Partner not found";

        final var createInput = new CreateEventUseCase.Input(
                expectedDate,
                expectedName,
                expectedPartnerId,
                expectedTotalSpots);
        // when

        final var partnerRepository = new InMemoryPartnerRepository();
        final var eventRepository = new InMemoryEventRepository();

        final var useCase = new CreateEventUseCase(partnerRepository, eventRepository);
        final var actualException = Assertions.assertThrows(
                ValidationException.class, () -> useCase.execute(createInput));

        // then

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}
