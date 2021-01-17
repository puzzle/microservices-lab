package ch.puzzle.mm.debezium.order.control;

import ch.puzzle.mm.debezium.event.entity.OrderCancelledEvent;
import ch.puzzle.mm.debezium.order.entity.*;
import io.debezium.outbox.quarkus.ExportedEvent;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Traced
@ApplicationScoped
public class ShopOrderService {

    @Inject
    Event<ExportedEvent<?, ?>> event;

    public List<ShopOrder> listAll() {
        return ShopOrder.listAll();
    }

    public ShopOrder createOrder(ShopOrderDTO shopOrderDTO) {
        // TODO: implementation - create ArticleOrder list

        // TODO: implementation - create new shopOrder

        // TODO: fire OrderCreatedEvent
        return new ShopOrder();
    }

    @Counted(name = "debezium_order_stockevent_complete", absolute = true, description = "number of stockcomplete events from stock", tags = {"application=debezium-order", "resource=ShopOrderService"})
    public void onStockCompleteEvent(ShopOrderStockResponse stockComplete) {
        // TODO: implementation - set correct state
    }

    @Counted(name = "debezium_order_stockevent_incomplete", absolute = true, description = "number of stockincomplete events from stock", tags = {"application=debezium-order", "resource=ShopOrderService"})
    public void onStockIncompleteEvent(ShopOrderStockResponse stockIncomplete) {
        // TODO: implementation - set correct state
    }

    public ShopOrder cancelOrder(long orderId) {
        ShopOrder order = ShopOrder.getByIdOrThrow(orderId);
        if (order.getStatus().canCancel()) {
            order.setStatus(ShopOrderStatus.CANCELLED);
            event.fire(new OrderCancelledEvent(Instant.now(), order));
            return order;
        } else {
            throw new IllegalStateException("Cannot cancel Order " + orderId);
        }
    }
}
