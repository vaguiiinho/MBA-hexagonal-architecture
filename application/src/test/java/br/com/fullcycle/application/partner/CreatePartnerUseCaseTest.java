package br.com.fullcycle.application.partner;

import br.com.fullcycle.application.repository.InMemoryPartnerRepository;
import br.com.fullcycle.domain.partner.Partner;
import br.com.fullcycle.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreatePartnerUseCaseTest {

    @Test
    @DisplayName("Deve criar um parceiro")
    public void testCreatePartner() {

        // given
        final var expectCNPJ = "41.536.538/0001-00";
        final var expectEmail = "john.doe@gmail.com";
        final var expectName = "John Doe";

        final var createInput = new CreatePartnerUseCase.Input(expectCNPJ, expectEmail, expectName);

        // when
        final var partnerRepository = new InMemoryPartnerRepository();

        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectCNPJ, output.cnpj());
        Assertions.assertEquals(expectEmail, output.email());
        Assertions.assertEquals(expectName, output.name());

    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com CNPJ duplicado")
    public void testCreateWithDuplicatedCNPJShouldFail() throws Exception {

        // given
        final var expectCNPJ = "41.536.538/0001-00";
        final var expectEmail = "john.doe@gmail.com";
        final var expectName = "John Doe";
        final var expectedError = "Partner already exists";

        final var aPartner = Partner.newPartner(expectName, "41.536.538/0002-00", expectEmail);
        var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);
        final var createInput = new CreatePartnerUseCase.Input(expectCNPJ, expectEmail, expectName);

        // when

        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class,
                () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {

        // given
        final var expectCNPJ = "41.536.538/0001-00";
        final var expectEmail = "john.doe@gmail.com";
        final var expectName = "John Doe";
        final var expectedError = "Partner already exists";

        final var aPartner = Partner.newPartner(expectName, "41.536.538/0002-00", expectEmail);
        final var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);

        final var createInput = new CreatePartnerUseCase.Input(expectCNPJ, expectEmail, expectName);
        // when

        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class,
                () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }
}
