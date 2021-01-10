package ch.puzzle.mm.kafka.order.order.control;

import ch.puzzle.mm.kafka.order.order.entity.ArticleOrder;
import ch.puzzle.mm.kafka.order.order.entity.ShopOrder;
import ch.puzzle.mm.kafka.order.order.entity.ShopOrderDTO;
import ch.puzzle.mm.kafka.order.order.entity.ShopOrderStatus;
import ch.puzzle.mm.kafka.order.order.boundary.ShopOrderRequestProducer;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Traced
public class ShopOrderService {

    @Inject
    ShopOrderRequestProducer shopOrderRequestProducer;

    public List<ShopOrder> listAll() {
        return ShopOrder.listAll();
    }

    public ShopOrder createOrder(ShopOrderDTO shopOrderDTO) {
        List<ArticleOrder> articleOrders = shopOrderDTO.articleOrders.stream().map(s -> new ArticleOrder(s.articleId, s.amount)).collect(Collectors.toList());
        articleOrders.forEach(articleOrder -> articleOrder.persist());

        // store order to shopOrder table
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setStatus(ShopOrderStatus.NEW);
        shopOrder.setArticleOrders(articleOrders);
        shopOrder.persist();
        shopOrderDTO.id = shopOrder.id;

        // fire event
        shopOrderRequestProducer.createRequest(shopOrderDTO);

        return shopOrder;
    }

    @Transactional
    public void compensateOrder(Long id) {
        ShopOrder shopOrder = ShopOrder.findById(id);
        shopOrder.setStatus(ShopOrderStatus.INCOMPLETE);
        shopOrder.persistAndFlush();
    }

    @Transactional
    public void confirmOrder(Long id) {
        ShopOrder shopOrder = ShopOrder.findById(id);
        shopOrder.setStatus(ShopOrderStatus.COMPLETED);
        shopOrder.persistAndFlush();
    }
}
