package teame.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import teame.models.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * Event.java repository to abstract away database implementation
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    @Cacheable("Events")
    Event findFirstByCardIdOrderByTimestampDesc(String cardId);
}