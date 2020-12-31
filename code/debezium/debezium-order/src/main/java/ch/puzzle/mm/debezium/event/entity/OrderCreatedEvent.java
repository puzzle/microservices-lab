package ch.puzzle.mm.debezium.event.entity;

import ch.puzzle.mm.debezium.order.entity.ShopOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.time.Instant;
import java.util.UUID;

public class OrderCreatedEvent implements ExportedEvent<String, JsonNode> {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String AGGREGATE_TYPE = "";
    private static final String EVENT_TYPE = "";

    private final UUID id;
    private final Long orderId;
    private final JsonNode jsonNode;
    private final Instant timestamp;

    public OrderCreatedEvent(Instant created, ShopOrder shopOrder) {
        this.id = UUID.randomUUID();
        this.orderId = shopOrder.id;
        this.timestamp = created;
        this.jsonNode = asJson(shopOrder);
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(orderId);
    }

    @Override
    public String getAggregateType() {
        return AGGREGATE_TYPE;
    }

    @Override
    public String getType() {
        return EVENT_TYPE;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public JsonNode getPayload() {
        return jsonNode;
    }

    public ObjectNode asJson(ShopOrder order) {
        ObjectNode asJson = mapper.createObjectNode();

        // TODO: implementation

        return asJson;
    }
}
