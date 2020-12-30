package ch.puzzle.mm.debezium.event.control;

import ch.puzzle.mm.debezium.order.control.ShopOrderService;
import ch.puzzle.mm.debezium.order.entity.ShopOrderStockResponse;
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
public class StockEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(StockEventHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    EventLog eventLog;

    @Inject
    ShopOrderService shopOrderService;

    @Counted(name = "debezium_order_stockevent_total", absolute = true, description = "number of events from stock", tags = {"application=debezium-order", "resource=StockEventHandler"})
    @Timed(name = "debezium_order_stockevent_timed", description = "timer for processing a stock event", tags = {"application=debezium-order", "resource=StockEventHandler"})
    @Transactional
    public void onStockEvent(UUID eventId, String eventType, String key, String event, Instant ts) {
        // TODO: implementation - check eventLog (alreadyProcessed)

        // TODO: implementation - check eventType, delegate handling to shopOrderService

        // TODO: implementation - store in eventLog (processed)
    }

    ShopOrderStockResponse deserialize(String escapedEvent) {
        try {
            String unescaped = objectMapper.readValue(escapedEvent, String.class);
            return objectMapper.readValue(unescaped, ShopOrderStockResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize message. " + escapedEvent, e);
        }
    }
}
