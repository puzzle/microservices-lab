package ch.puzzle.mm.kafka.order.order.boundary;

import ch.puzzle.mm.kafka.order.order.entity.ShopOrderDTO;
import ch.puzzle.mm.kafka.order.util.HeadersMapInjectAdapter;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.smallrye.reactive.messaging.kafka.OutgoingKafkaRecordMetadata;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ShopOrderRequestProducer {

    @Inject
    @Channel("shop-order-request")
    Emitter<ShopOrderDTO> emitter;

    @Inject
    Tracer tracer;

    @Traced
    public void createRequest(ShopOrderDTO shopOrderDTO) {
        HeadersMapInjectAdapter headersMapInjectAdapter = new HeadersMapInjectAdapter();
        try (Scope scope = tracer.buildSpan("create-request").startActive(true)) {
            tracer.inject(scope.span().context(), Format.Builtin.TEXT_MAP, headersMapInjectAdapter);
            OutgoingKafkaRecordMetadata metadata = OutgoingKafkaRecordMetadata.<ShopOrderDTO>builder()
                    .withKey(shopOrderDTO)
                    .withTopic("shop-order-request")
                    .withHeaders(headersMapInjectAdapter.getRecordHeaders())
                    .build();
            emitter.send(Message.of(shopOrderDTO, Metadata.of(metadata)));
        }
    }
}
