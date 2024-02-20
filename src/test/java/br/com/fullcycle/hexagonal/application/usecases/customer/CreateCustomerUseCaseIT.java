package br.com.fullcycle.hexagonal.application.usecases.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fullcycle.hexagonal.IntegrationTest;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.usecases.customer.CreateCustomerUseCase;
import br.com.fullcycle.hexagonal.infrastructure.models.Customer;
import br.com.fullcycle.hexagonal.infrastructure.repositories.CustomerRepository;

public class CreateCustomerUseCaseIT extends IntegrationTest {

    @Autowired
    private CreateCustomerUseCase useCase;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um cliente")
    public void testCreateCustomer() {

        // given
        final var expectCPF = "12345678901";
        final var expectEmail = "john.doe@gmail.com";
        final var expectName = "John Doe";

        final var createInput = new CreateCustomerUseCase.Input(expectCPF, expectEmail, expectName);

        // when

        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectCPF, output.cpf());
        Assertions.assertEquals(expectEmail, output.email());
        Assertions.assertEquals(expectName, output.name());

    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    public void testCreateWithDuplicatedCPFShouldFail() throws Exception {

        // given
        final var expectCPF = "12345678901";
        final var expectEmail = "john.doe@gmail.com";
        final var expectName = "John Doe";
        final var expectedError = "Customer already exists";

        createCustomer(expectCPF, expectEmail, expectName);

        final var createInput = new CreateCustomerUseCase.Input(expectCPF, expectEmail, expectName);

        // when

        final var actualException = Assertions.assertThrows(ValidationException.class,
                () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {

        // given
        final var expectCPF = "12345678901";
        final var expectEmail = "john.doe@gmail.com";
        final var expectName = "John Doe";
        final var expectedError = "Customer already exists";

        createCustomer("12312312312", expectEmail, expectName);

        final var createInput = new CreateCustomerUseCase.Input(expectCPF, expectEmail, expectName);

        // when

        final var actualException = Assertions.assertThrows(ValidationException.class,
                () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    private Customer createCustomer(final String cpf, final String email, final String name) {
        final var aCustomer = new Customer();
        aCustomer.setCpf(cpf);
        aCustomer.setEmail(email);
        aCustomer.setName(name);

        return customerRepository.save(aCustomer);
    }

}
