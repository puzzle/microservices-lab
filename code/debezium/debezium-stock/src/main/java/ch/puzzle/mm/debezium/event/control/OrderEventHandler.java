package ch.puzzle.mm.debezium.event.control;

import ch.puzzle.mm.debezium.stock.control.ArticleStockService;
import ch.puzzle.mm.debezium.stock.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class OrderEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    EventLog eventLog;

    @Inject
    ArticleStockService articleStockService;

    @Counted(name = "debezium_stock_orderevent_total", absolute = true, description = "number of events from order", tags = {"application=debezium-stock", "resource=OrderEventHandler"})
    @Timed(name = "debezium_stock_orderevent_timed", description = "timer for processing an order event", tags = {"application=debezium-stock", "resource=OrderEventHandler"})
    @Transactional
    public void onOrderEvent(UUID eventId, String eventType, String key, String event, Instant ts) {
        if (eventLog.alreadyProcessed(eventId)) {
            logger.info("Event with id {} was already processed, ignore.", eventId);
            return;
        }

        logger.info("Received '{}' event {} - OrderId: {}, ts: '{}'", eventType, eventId, key, ts);
        if (eventType.equalsIgnoreCase("OrderCreated")) {
            articleStockService.orderCreated(deserialize(event));
        } else if (eventType.equalsIgnoreCase("OrderCancelled")) {
            articleStockService.orderCanceled(deserialize(event));
        } else {
            logger.warn("Ignoring unknown event '{}'", eventType);
        }

        eventLog.processed(eventId);
    }

    Order deserialize(String escapedEvent) {
        try {
            String unescaped = objectMapper.readValue(escapedEvent, String.class);
            return objectMapper.readValue(unescaped, Order.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize message. " + escapedEvent, e);
        }
    }
}
