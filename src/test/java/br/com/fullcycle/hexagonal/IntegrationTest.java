package br.com.fullcycle.hexagonal;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.fullcycle.hexagonal.infrastructure.Main;

@ActiveProfiles
@SpringBootTest(classes = Main.class)
public abstract class IntegrationTest {
    
}
