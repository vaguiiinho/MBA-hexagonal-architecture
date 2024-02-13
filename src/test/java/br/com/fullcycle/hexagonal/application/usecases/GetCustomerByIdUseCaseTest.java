package br.com.fullcycle.hexagonal.application.usecases;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.fullcycle.hexagonal.models.Customer;
import br.com.fullcycle.hexagonal.services.CustomerService;

public class GetCustomerByIdUseCaseTest {

    @Test
    @DisplayName("Deve obter um cliente por id")
    public void testGetCustomerById() {

        // given
        final var expectId = UUID.randomUUID().getMostSignificantBits();
        final var expectCPF = "12345678901";
        final var expectEmail = "john.doe@gmail.com";
        final var expectName = "John Doe";

        final var aCustomer = new Customer();
        aCustomer.setId(expectId);
        aCustomer.setCpf(expectCPF);
        aCustomer.setEmail(expectEmail);
        aCustomer.setName(expectName);

        final var input = new GetCustomerByIdUseCase.Input(expectId);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findById(expectId)).thenReturn(Optional.of(aCustomer));

        final var useCase = new GetCustomerByIdUseCase(customerService);
        final var output = useCase.execute(input).get();

        // then
        Assertions.assertEquals(expectId, output.id());
        Assertions.assertEquals(expectCPF, output.cpf());
        Assertions.assertEquals(expectEmail, output.email());
        Assertions.assertEquals(expectName, output.name());

    }

    @Test
    @DisplayName("Deve obter vazio ao tentar recuperar  um cliente  não existe por id")
    public void testGetCustomerByIdWithInvalidId() {

        // given
        final var expectId = UUID.randomUUID().getMostSignificantBits();

        final var input = new GetCustomerByIdUseCase.Input(expectId);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findById(expectId)).thenReturn(Optional.empty());

        final var useCase = new GetCustomerByIdUseCase(customerService);
        final var output = useCase.execute(input);

        // then
        Assertions.assertTrue(output.isEmpty());

    }
}
