package ch.puzzle.mm.debezium.event.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.time.Instant;
import java.util.UUID;

public class StockCompleteEvent implements ExportedEvent<String, JsonNode> {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String AGGREGATE_TYPE = "";
    private static final String EVENT_TYPE = "";

    private final UUID id;
    private final Long orderId;
    private final JsonNode jsonNode;
    private final Instant timestamp;

    public StockCompleteEvent(Instant created, Long orderId) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.timestamp = created;
        this.jsonNode = asJson(orderId);
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

    public ObjectNode asJson(Long orderId) {
        ObjectNode asJson = mapper.createObjectNode();

        // TODO: implementation

        return asJson;
    }
}
