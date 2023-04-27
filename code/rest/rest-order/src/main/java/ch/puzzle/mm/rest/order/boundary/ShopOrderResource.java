package ch.puzzle.mm.rest.order.boundary;

import ch.puzzle.mm.rest.monkey.control.ChaosMonkey;
import ch.puzzle.mm.rest.order.control.ShopOrderService;
import ch.puzzle.mm.rest.order.entity.ShopOrder;
import ch.puzzle.mm.rest.order.entity.ShopOrderDTO;
import ch.puzzle.mm.rest.order.entity.ShopOrderStatus;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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

    @Inject
    @RestClient
    ArticleStockService articleStockService;

    @Inject
    ShopOrderService shopOrderService;

    @GET
    public List<ShopOrder> listAll() {
        return ShopOrder.listAll();
    }

    @POST
    @ChaosMonkey
    @Transactional
    public Response createShopOrder(ShopOrderDTO shopOrderDTO) {
        // create ShopOrder locally
        ShopOrder shopOrder = shopOrderService.createOrder(shopOrderDTO);

        // call remote service
        articleStockService.orderArticles(shopOrderDTO.articleOrders);

        shopOrder.setStatus(ShopOrderStatus.COMPLETED);

        return Response.ok(shopOrder).build();
    }
}


