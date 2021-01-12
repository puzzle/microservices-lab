package ch.puzzle.mm.debezium.order.boundary;

import ch.puzzle.mm.debezium.order.control.ShopOrderService;
import ch.puzzle.mm.debezium.order.entity.ShopOrder;
import ch.puzzle.mm.debezium.order.entity.ShopOrderDTO;
import ch.puzzle.mm.debezium.order.entity.ShopOrderStatus;
import ch.puzzle.mm.debezium.order.entity.ShopOrderStatusDto;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/shop-orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShopOrderResource {

    private final Logger logger = LoggerFactory.getLogger(ShopOrderResource.class.getName());

    @Inject
    ShopOrderService shopOrderService;

    @GET
    public List<ShopOrder> listAll() {
        return shopOrderService.listAll();
    }

    @GET
    @Path("/{orderId}")
    public Response get(@PathParam("orderId") long orderId) {
        return Response.ok(ShopOrder.getByIdOrThrow(orderId)).build();
    }

    @POST
    @Transactional
    @Counted(name = "debezium_order_create_request", absolute = true, description = "number of orders requested", tags = {"application=debezium-order", "resource=ShopOrderResource"})
    @Timed(name = "debezium_order_create_timed", absolute = true, description = "timer for processing a order creation", tags = {"application=debezium-order", "resource=ShopOrderResource"})
    public Response create(ShopOrderDTO shopOrderDTO) {
        ShopOrder shopOrder = shopOrderService.createOrder(shopOrderDTO);
        return Response.ok(shopOrder).build();
    }

    @POST
    @Path("/{orderId}/status")
    @Transactional
    @Counted(name = "debezium_order_cancel_request", absolute = true, description = "number of orders cancellations", tags = {"application=debezium-order", "resource=ShopOrderResource"})
    @Timed(name = "debezium_order_cancel_timed", absolute = true, description = "timer for processing a order cancellation", tags = {"application=debezium-order", "resource=ShopOrderResource"})
    public Response cancel(@PathParam("orderId") long orderId, ShopOrderStatusDto status) {
        if (status == null || status.status != ShopOrderStatus.CANCELLED) {
            throw new IllegalStateException("Failed to change status of order " + orderId);
        }

        ShopOrder shopOrder = shopOrderService.cancelOrder(orderId);
        return Response.ok(shopOrder).build();
    }
}


