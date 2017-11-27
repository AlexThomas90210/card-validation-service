package teame;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import teame.models.Coordinates;
import teame.models.Event;
import teame.models.Location;
import teame.repositories.EventRepository;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(EventRepository repository) {
        return (args) -> {
//            Location firstLocation = new Location(new Coordinates(-8.533947,51.884827), 5.0, "Some Location");
//            Location secondLocation = new Location(new Coordinates( -0.133873,51.524885), 5.0, "Another Location");
//            Event currentEvent  = new Event("123-123-123", "123-123-123" , true, 1511627249000L, firstLocation);
//            Event previousEvent = new Event("123-123-123", "123-123-123" , true, 1511195249000L, secondLocation);
//
//            // save a couple of events
//            repository.save(currentEvent);
//            repository.save(previousEvent);
        };
    }
}
