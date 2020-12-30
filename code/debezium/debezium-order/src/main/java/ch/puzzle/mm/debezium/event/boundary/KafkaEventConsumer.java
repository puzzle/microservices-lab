package ch.puzzle.mm.debezium.event.boundary;

import ch.puzzle.mm.debezium.event.control.StockEventHandler;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.TracingKafkaUtils;
import io.smallrye.reactive.messaging.annotations.Merge;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.apache.kafka.common.header.Header;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class KafkaEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventConsumer.class);

    @Inject
    StockEventHandler stockEventHandler;

    @Inject
    Tracer tracer;

    @Incoming("stock")
    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) {
        return CompletableFuture.runAsync(() -> {
            try (final Scope span = tracer.buildSpan("handle-stock-message").asChildOf(TracingKafkaUtils.extractSpanContext(message.getHeaders(), tracer)).startActive(true)) {
                // TODO: implementation - message handling
            } catch (Exception e) {
                logger.error("Error while preparing articlestock", e);
                throw e;
            }
        }).thenRun(message::ack);
    }

    private String getHeaderAsString(KafkaRecord<?, ?> record, String name) {
        Header header = record.getHeaders().lastHeader(name);
        if (header == null) {
            throw new IllegalArgumentException("Expected record header '" + name + "' not present");
        }

        return new String(header.value(), StandardCharsets.UTF_8);
    }
}
