package br.com.fullcycle.hexagonal.application.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.infrastructure.models.Customer;
import br.com.fullcycle.hexagonal.infrastructure.services.CustomerService;

public class CreateCustomerUseCaseTest {

    @Test
    @DisplayName("Deve criar um cliente")
    public void testCreateCustomer() {

        // given
        final var expectCPF = "12345678901";
        final var expectEmail = "john.doe@gmail.com";
        final var expectName = "John Doe";
        final var createInput = new CreateCustomerUseCase.Input(expectCPF, expectEmail, expectName);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findByCpf(expectCPF)).thenReturn(Optional.empty());
        when(customerService.findByEmail(expectEmail)).thenReturn(Optional.empty());
        when(customerService.save(any())).thenAnswer(a -> {
            var customer = a.getArgument(0, Customer.class);
            customer.setId(UUID.randomUUID().getMostSignificantBits());
            return customer;
        });

        final var useCase = new CreateCustomerUseCase(customerService);
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

        final var createInput = new CreateCustomerUseCase.Input(expectCPF, expectEmail, expectName);

        final var aCustomer = new Customer();
        aCustomer.setId(UUID.randomUUID().getMostSignificantBits());
        aCustomer.setCpf(expectCPF);
        aCustomer.setEmail(expectEmail);
        aCustomer.setName(expectName);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findByCpf(expectCPF)).thenReturn(Optional.of(aCustomer));
       

        final var useCase = new CreateCustomerUseCase(customerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

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

        final var createInput = new CreateCustomerUseCase.Input(expectCPF, expectEmail, expectName);

        final var aCustomer = new Customer();
        aCustomer.setId(UUID.randomUUID().getMostSignificantBits());
        aCustomer.setCpf(expectCPF);
        aCustomer.setEmail(expectEmail);
        aCustomer.setName(expectName);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findByEmail(expectEmail)).thenReturn(Optional.of(aCustomer));
       

        final var useCase = new CreateCustomerUseCase(customerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
        

    }
}
