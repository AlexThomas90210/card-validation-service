package repositories;

import models.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * Event repository to abstract away database implementation
 */
public interface EventRepository extends CrudRepository<Event, Long> {
    Event findFirstByCardId(String cardId);
}
