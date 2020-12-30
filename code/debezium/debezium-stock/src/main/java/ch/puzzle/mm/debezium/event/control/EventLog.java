package ch.puzzle.mm.debezium.event.control;

import ch.puzzle.mm.debezium.event.entity.ConsumedEvent;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Traced
@ApplicationScoped
public class EventLog {

    private static final Logger logger = LoggerFactory.getLogger(EventLog.class);

    @Transactional(value = Transactional.TxType.MANDATORY)
    public void processed(UUID eventId) {
        ConsumedEvent.persist(new ConsumedEvent(eventId, Instant.now()));
    }

    @Transactional(value = Transactional.TxType.MANDATORY)
    public boolean alreadyProcessed(UUID eventId) {
        logger.info("Looking for event with id {} in message log", eventId);
        return ConsumedEvent.findByIdOptional(eventId).isPresent();
    }

}
