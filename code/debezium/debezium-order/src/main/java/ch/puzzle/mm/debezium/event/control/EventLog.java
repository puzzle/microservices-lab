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

    public void processed(UUID eventId) {
        // TODO: implementation - store
    }

    public boolean alreadyProcessed(UUID eventId) {
        // TODO: implementation - check exists
        return false;
    }

}
