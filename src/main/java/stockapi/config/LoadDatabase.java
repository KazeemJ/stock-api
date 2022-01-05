package stockapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import stockapi.model.Symbol;
import stockapi.repo.SymbolRepository;

import java.util.UUID;

//@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(SymbolRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Symbol(UUID.randomUUID().toString(), "X", 100.0f, 101.0f)));
            log.info("Preloading " + repository.save(new Symbol(UUID.randomUUID().toString(), "Y", 200.0f, 201.0f)));
        };
    }
}
